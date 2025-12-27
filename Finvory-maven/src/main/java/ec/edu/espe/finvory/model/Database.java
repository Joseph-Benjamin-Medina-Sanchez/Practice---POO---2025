package ec.edu.espe.finvory.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import ec.edu.espe.finvory.mongo.MongoDBConnection;
import org.bson.Document;
import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Locale;
import java.util.List;

/**
 * * @author Joseph Medina
 */
public class Database {

    private static final String ROOT_DATA_FOLDER = "data";
    private static final String UTILS_FOLDER = "utils";
    private static final String USERS_FILE = UTILS_FOLDER + File.separator + "users.json";
    private static final String DELIMITER = ";";

    private Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new TypeAdapter<LocalDate>() {
                @Override
                public void write(JsonWriter jsonWriter, LocalDate localDate) throws IOException {
                    jsonWriter.value(localDate == null ? null : localDate.toString());
                }

                @Override
                public LocalDate read(JsonReader jsonReader) throws IOException {
                    String string = jsonReader.nextString();
                    return (string == null || string.isEmpty()) ? null : LocalDate.parse(string);
                }
            })
            .setPrettyPrinting()
            .create();

    public FinvoryData loadCompanyData(String companyUsername) {
System.out.println("--- SINCRONIZACIÓN DE DATOS ---");
    FinvoryData cloudData = loadDataFromCloud(companyUsername);

    if (cloudData != null) {
        System.out.println("Datos descargados de MongoDB Atlas.");
        System.out.println("    -> Inventarios: " + cloudData.getInventories().size());
        System.out.println("    -> Productos: " + cloudData.getProducts().size());
        System.out.println("    -> Facturas: " + cloudData.getInvoices().size());

        saveCompanyData(cloudData, companyUsername);
        return cloudData;
    }

    System.out.println("No se pudo cargar de la nube. Usando datos locales.");
    String folder = ROOT_DATA_FOLDER + File.separator + companyUsername;
    FinvoryData localData = loadJson(folder);
    // Limpia duplicados que ya existan en el JSON (por ejecuciones anteriores)
    dedupeCustomers(localData);
    dedupeSuppliers(localData);

    for (Customer customer : loadCustomersCsv(folder)) {
        boolean exists = false;
        for (Customer customerdata : localData.getCustomers()) {
            if (customerdata.getIdentification().equals(customer.getIdentification())) {
                exists = true;
                break;
            }
        }
        if (!exists) {
            localData.addCustomer(customer);
        }
    }

    for (Supplier supplier : loadSuppliersCsv(folder)) {
        boolean exists = false;
        for (Supplier supplierData : localData.getSuppliers()) {
            if (supplierData.getId1().equals(supplier.getId1())) {
                exists = true;
                break;
            }
        }
        if (!exists) {
            localData.addSupplier(supplier);
        }
    }

    // Por seguridad, vuelve a limpiar después de hacer el merge desde CSV
    dedupeCustomers(localData);
    dedupeSuppliers(localData);
    return localData;
    }

    private static String norm(String s) {
        return s == null ? "" : s.trim();
    }

    private void dedupeCustomers(FinvoryData data) {
        if (data == null || data.getCustomers() == null) {
            return;
        }
        // Mantener orden de inserción
        Map<String, Customer> byId = new java.util.LinkedHashMap<>();
        for (Customer c : new ArrayList<>(data.getCustomers())) {
            if (c == null) {
                continue;
            }
            String id = norm(c.getIdentification());
            if (id.isEmpty()) {
                // si no hay id, no se puede deduplicar de forma segura
                byId.put(java.util.UUID.randomUUID().toString(), c);
            } else {
                // si hay repetido, se queda el primero (puedes cambiarlo a "último" si prefieres)
                byId.putIfAbsent(id, c);
            }
        }
        data.getCustomers().clear();
        data.getCustomers().addAll(byId.values());
    }

    private void dedupeSuppliers(FinvoryData data) {
        if (data == null || data.getSuppliers() == null) {
            return;
        }
        Map<String, Supplier> byId = new java.util.LinkedHashMap<>();
        for (Supplier s : new ArrayList<>(data.getSuppliers())) {
            if (s == null) {
                continue;
            }
            String id = norm(s.getId1());
            if (id.isEmpty()) {
                byId.put(java.util.UUID.randomUUID().toString(), s);
            } else {
                byId.putIfAbsent(id, s);
            }
        }
        data.getSuppliers().clear();
        data.getSuppliers().addAll(byId.values());
    }

    private FinvoryData loadDataFromCloud(String username) {
        try {
            FinvoryData data = new FinvoryData();

            MongoCollection<Document> prodCol = MongoDBConnection.getCollection("products");
            if (prodCol != null) {
                for (Document doc : prodCol.find(Filters.eq("companyUsername", username))) {
                    BigDecimal cost = BigDecimal.valueOf(doc.get("baseCostPrice") != null ? doc.getDouble("baseCostPrice") : 0.0);
                    data.addProduct(new Product(doc.getString("productId"), doc.getString("name"), doc.getString("description"), doc.getString("barcode"), cost, doc.getString("supplierId")));
                }
            }

            MongoCollection<Document> cliCol = MongoDBConnection.getCollection("customers");
            if (cliCol != null) {
                for (Document doc : cliCol.find(Filters.eq("companyUsername", username))) {
                    data.addCustomer(new Customer(doc.getString("name"), doc.getString("identification"), doc.getString("phone"), doc.getString("email"), doc.getString("clientType")));
                }
            }

            MongoCollection<Document> supCol = MongoDBConnection.getCollection("suppliers");
            if (supCol != null) {
                for (Document doc : supCol.find(Filters.eq("companyUsername", username))) {
                    Supplier s = new Supplier(doc.getString("fullName"), doc.getString("id1"), doc.getString("phone"), doc.getString("email"), doc.getString("description"));
                    s.setId2(doc.getString("id2"));
                    data.addSupplier(s);
                }
            }

            MongoCollection<Document> invCol = MongoDBConnection.getCollection("invoices");
            if (invCol != null) {
                for (Document doc : invCol.find(Filters.eq("companyUsername", username))) {
                    String id = doc.getString("invoiceId");

                    java.util.Date dateRaw = doc.getDate("date");
                    LocalDate date = (dateRaw != null) ? dateRaw.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate() : LocalDate.now();

                    Document custDoc = (Document) doc.get("customer");
                    Customer customer = new Customer(custDoc.getString("name"), custDoc.getString("identification"), custDoc.getString("phone"), custDoc.getString("email"), custDoc.getString("clientType"));

                    ArrayList<InvoiceLineSim> lines = new ArrayList<>();
                    List<Document> linesDoc = (List<Document>) doc.get("lines");
                    if (linesDoc != null) {
                        for (Document l : linesDoc) {
                            lines.add(new InvoiceLineSim(l.getString("productId"), l.getString("productName"), l.getInteger("quantity"), BigDecimal.valueOf(l.getDouble("price"))));
                        }
                    }

                    InvoiceSim invoice = new InvoiceSim(id, date, date, customer, lines, BigDecimal.valueOf(doc.getDouble("tax")), BigDecimal.ZERO);
                    invoice.complete();
                    data.addInvoice(invoice);
                }
            }
            // Por si ya existían duplicados en la nube o llegaron con espacios, limpiar.
            dedupeCustomers(data);
            dedupeSuppliers(data);
            return data;
        } catch (Exception e) {
            System.err.println("Error en Database: " + e.getMessage());
            return null;
        }
    }

    private void saveJson(FinvoryData data, String folder) {
        try (Writer writer = new FileWriter(folder + File.separator + "finvory_database.json")) {
            gson.toJson(data, writer);
        } catch (IOException e) {
            System.err.println("Error guardando JSON: " + e.getMessage());
        }
    }

    public SystemUsers loadUsers() {
        File file = new File(USERS_FILE);
        if (file.exists()) {
            try (Reader reader = new FileReader(file)) {
                SystemUsers users = gson.fromJson(reader, SystemUsers.class);
                if (users != null) {
                    return users;
                }
            } catch (IOException e) {
                System.err.println("Error leyendo usuarios: " + e.getMessage());
            }
        }
        return new SystemUsers();
    }

    private ArrayList<Customer> loadCustomersCsv(String folder) {
        ArrayList<Customer> list = new ArrayList<>();
        File file = new File(folder + File.separator + "clients.csv");
        if (!file.exists()) {
            return list;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(DELIMITER);
                if (parts.length == 5) {
                    // trim() para evitar duplicados por espacios al comparar IDs
                    list.add(new Customer(parts[1].trim(), parts[0].trim(), parts[2].trim(), parts[3].trim(), parts[4].trim()));
                }
            }
        } catch (IOException e) {
            System.err.println("Error leyendo CSV de clientes: " + e.getMessage());
        }
        return list;
    }

    private void saveCustomersCsv(List<Customer> customers, String folder) {
        try (PrintWriter printWritter = new PrintWriter(new FileWriter(folder + File.separator + "clients.csv"))) {
            printWritter.println("Identification" + DELIMITER + "FullName" + DELIMITER + "Phone" + DELIMITER + "Email" + DELIMITER + "ClientType");
            for (Customer customer : customers) {
                printWritter.println(customer.getIdentification() + DELIMITER + customer.getName() + DELIMITER + customer.getPhone() + DELIMITER + customer.getEmail() + DELIMITER + customer.getClientType());
            }
        } catch (IOException e) {
            System.err.println("Error guardando CSV de clientes: " + e.getMessage());
        }
    }

    private ArrayList<Supplier> loadSuppliersCsv(String folder) {
        ArrayList<Supplier> list = new ArrayList<>();
        File file = new File(folder + File.separator + "suppliers.csv");
        if (!file.exists()) {
            return list;
        }
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            bufferedReader.readLine();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(DELIMITER);
                if (parts.length == 6) {
                    // trim() para evitar duplicados por espacios al comparar IDs
                    Supplier supplier = new Supplier(parts[2].trim(), parts[0].trim(), parts[3].trim(), parts[4].trim(), parts[5].trim());
                    supplier.setId2(parts[1].trim());
                    list.add(supplier);
                }
            }
        } catch (IOException e) {
            System.err.println("Error leyendo CSV de proveedores: " + e.getMessage());
        }
        return list;
    }

    private void saveSuppliersCsv(List<Supplier> suppliers, String folder) {
        try (PrintWriter printWritter = new PrintWriter(new FileWriter(folder + File.separator + "suppliers.csv"))) {
            printWritter.println("ID1" + DELIMITER + "ID2" + DELIMITER + "FullName" + DELIMITER + "Phone" + DELIMITER + "Email" + DELIMITER + "Description");
            for (Supplier supplier : suppliers) {
                printWritter.println(supplier.getId1() + DELIMITER + supplier.getId2() + DELIMITER + supplier.getFullName() + DELIMITER + supplier.getPhone() + DELIMITER + supplier.getEmail() + DELIMITER + supplier.getDescription());
            }
        } catch (IOException e) {
            System.err.println("Error guardando CSV de proveedores: " + e.getMessage());
        }
    }

    public boolean exportToCsv(String title, HashMap<String, ? extends Object> data, String companyUsername) {
        String fileName = ROOT_DATA_FOLDER + File.separator + companyUsername + File.separator + title + ".csv";
        try {
            File file = new File(fileName);
            new File(file.getParent()).mkdirs();
            try (PrintWriter printWritter = new PrintWriter(new FileWriter(file))) {
                printWritter.println("Key" + DELIMITER + "Value");
                for (Map.Entry<String, ? extends Object> entry : data.entrySet()) {
                    String value = String.valueOf(entry.getValue());

                    if (entry.getValue() instanceof BigDecimal) {
                        value = String.format(Locale.US, "%.2f", (BigDecimal) entry.getValue());
                    } else if (entry.getValue() instanceof Float) {
                        value = String.format(Locale.US, "%.2f", (Float) entry.getValue());
                    }
                    printWritter.println(entry.getKey() + DELIMITER + value);
                }
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public void saveCompanyData(FinvoryData data, String companyUsername) {
        String folder = ROOT_DATA_FOLDER + File.separator + companyUsername;
        new File(folder).mkdirs();
        saveJson(data, folder);
        saveCustomersCsv(data.getCustomers(), folder);
        saveSuppliersCsv(data.getSuppliers(), folder);
    }

    private FinvoryData loadJson(String folder) {
        File file = new File(folder + File.separator + "finvory_database.json");
        if (file.exists()) {
            try (Reader reader = new FileReader(file)) {
                FinvoryData data = gson.fromJson(reader, FinvoryData.class);
                return (data != null) ? data : new FinvoryData();
            } catch (IOException e) {
                System.err.println("Error leyendo JSON local: " + e.getMessage());
            }
        }
        return new FinvoryData();
    }

    public void saveUsers(SystemUsers users) {
        File folder = new File(UTILS_FOLDER);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        try (Writer writer = new FileWriter(USERS_FILE)) {
            gson.toJson(users, writer);
        } catch (IOException e) {
            System.err.println("Error al guardar usuarios en JSON: " + e.getMessage());
        }
    }

}
