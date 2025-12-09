package ec.edu.espe.contactsbook.mongo;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import ec.edu.espe.contactsbook.model.Contact;
import java.util.List;
import org.bson.Document;

/**
 *
 * @author Joseph B. Medina
 */
public class ContactRepository {
    
    private final MongoCollection<Document> collection;

    public ContactRepository() {
        MongoDatabase database = MongoDBConnection.getDatabase();
        this.collection = database.getCollection("contacts");
    }

    public void save(Contact contact) {
        Document document = new Document();
        document.append("id", contact.getId());
        document.append("firstName", contact.getFirstName());
        document.append("lastName", contact.getLastName());
        document.append("age", contact.getAge());
        document.append("typeOfContact", contact.getTypeOfContact());
        document.append("sex", contact.getSex());

        List<String> hobbies = contact.getHobbies();
        document.append("hobbies", hobbies);
        document.append("comments", contact.getComments());

        collection.insertOne(document);
    }
    
}
