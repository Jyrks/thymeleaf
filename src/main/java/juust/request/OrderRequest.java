package juust.request;

import lombok.Data;

@Data
public class OrderRequest {
    private String name;
    private String email;
    private String phoneNumber;
    private String time;
    private String date;
}
