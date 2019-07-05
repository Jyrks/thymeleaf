package juust.model;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class EmailInfo {
    private String email;
    private List<PlatterOrder> platterOrders;
    private Long id;
    private Double price;
    private Date date;
    private String name;
}
