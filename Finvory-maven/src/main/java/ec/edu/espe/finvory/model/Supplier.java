package ec.edu.espe.finvory.model;
import com.google.gson.Gson;
import java.util.Objects;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author @author Arelys Otavalo, The POOwer Rangers Of Programming
 */
public class Supplier {
    private String fullName;
    private String id1;
    private String id2;
    private String phone;
    private String email;
    private String description;

    public Supplier() {}

    public Supplier(String fullName, String id1, String phone, String email, String description) {
        this.fullName = fullName;
        this.id1 = id1;
        this.phone = phone;
        this.email = email;
        this.description = description;
        this.id2 = ""; 
    }

    public String getFullName() { 
        return fullName; 
    }
    
    public String getId1() { 
        return id1; 
    }
    
    public String getId2() { 
        return id2; 
    }
    
    public String getPhone() { 
        return phone; 
    }
    
    public String getEmail() { 
        return email; 
    }
    
    public String getDescription() { 
        return description; 
    }

    public void setFullName(String fullName) { 
        this.fullName = fullName; 
    }
    
    public void setId2(String id2) { 
        this.id2 = id2; 
    }
    
    public void setPhone(String phone) { 
        this.phone = phone; 
    }
    
    public void setEmail(String email) { 
        this.email = email; 
    }
    
    public void setDescription(String description) { 
        this.description = description; 
    }

    private static String norm(String s) {
        return s == null ? "" : s.trim();
    }

    /**
     * Evita duplicados: la identidad de Supplier es su ID1.
     * Se normaliza con trim() para que "1790" y "1790 " sean lo mismo.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Supplier)) {
            return false;
        }
        Supplier other = (Supplier) o;
        return norm(this.id1).equals(norm(other.id1));
    }

    @Override
    public int hashCode() {
        return Objects.hash(norm(this.id1));
    }
}
