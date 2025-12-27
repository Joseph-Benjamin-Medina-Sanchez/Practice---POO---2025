package ec.edu.espe.finvory.model;

import com.google.gson.Gson;
import java.util.Objects;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Arelys Otavalo, The POOwer Rangers Of Programming
 */
public class Customer {

    private String name;
    private String identification;
    private String phone;
    private String email;
    private String clientType;

    public Customer() {
    }

    public Customer(String name, String identification, String phone, String email, String clientType) {
        this.name = name;
        this.identification = identification;
        this.phone = phone;
        this.email = email;
        this.clientType = clientType;
    }

    public String getName() {
        return name;
    }

    public String getIdentification() {
        return identification;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public BigDecimal getDiscountRate(BigDecimal discountStandard, BigDecimal discountPremium, BigDecimal discountVip) {
        String type = getClientType();
        
        if (type == null) {
            return BigDecimal.ZERO;
        }

        if ("PREMIUM".equalsIgnoreCase(type)) {
            return discountPremium;
        } else if ("VIP".equalsIgnoreCase(type)) {
            return discountVip;
        } else {
            return discountStandard;
        }
    }
    
    public String getClientType() {
        return this.clientType; 
    }

    private static String norm(String s) {
        return s == null ? "" : s.trim();
    }

    /**
     * Evita duplicados: la identidad de Customer es su identificación.
     * Se normaliza con trim() para que "0102" y "0102 " sean lo mismo.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) o;
        return norm(this.identification).equals(norm(other.identification));
    }

    @Override
    public int hashCode() {
        return Objects.hash(norm(this.identification));
    }

    @Override
    public String toString() {
        return "Customer{"
                + "name='" + name + '\''
                + ", identification='" + identification + '\''
                + ", phone='" + phone + '\''
                + ", email='" + email + '\''
                + ", clientType='" + clientType + '\''
                + '}';
    }

}
