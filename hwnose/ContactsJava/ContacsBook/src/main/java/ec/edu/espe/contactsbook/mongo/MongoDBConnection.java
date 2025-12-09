package ec.edu.espe.contactsbook.mongo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

/**
 *
 * @author Joseph B. Medina
 */
public class MongoDBConnection {
        
    private static final String ENV_URI_NAME = "MONGODB_URI";
    private static final String DATABASE_NAME = "ContactsDB";

    private static MongoClient client;

    public static MongoDatabase getDatabase() {
        if (client == null) {
            String uri = System.getenv(ENV_URI_NAME);
            if (uri == null || uri.isEmpty()) {
                throw new IllegalStateException("La variable de entorno MONGODB_URI no está definida");
            }
            client = MongoClients.create(uri);
        }
        return client.getDatabase(DATABASE_NAME);
    }
    
}
