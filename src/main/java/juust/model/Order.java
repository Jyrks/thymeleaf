package juust.model;

import lombok.Data;

import java.util.Date;

@Data
public class Order {
    private Double price;
    private String orderName;
    private String personName;
    private String email;
    private String phone;
    private Date date;
    private Date created;
}
