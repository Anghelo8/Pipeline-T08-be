package pe.edu.vallegrande.losreyes.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "sales")
public class Sale {

    @Id
    private String id;

    @Field("sale_id")
    private int saleId;

    private Customer customer;
    private Comprobante comprobante;
    private String payment_method;
    private Date sale_date;
    private List<Product> products;
    private double total;
    private int status;
    private Date created_at;

    @Data
    public static class Customer {
        private int customer_id;
        private String name;
    }

    @Data
    public static class Comprobante {
        private String type;
        private String series;
        private int number;
    }

    @Data
    public static class Product {
        private int product_id;
        private String product_name;
        private int quantity;
        private double unit_price;
        private Discount discount;
        private double subtotal;
    }

    @Data
    public static class Discount {
        private String description;
        private double percentage;
        private double amount;
    }
}