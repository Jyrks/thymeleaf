package juust.request;

import lombok.Data;

@Data
public class OrderRequest {
    private String platterName;
    private String personName;
    private String email;
    private String phoneNumber;
    private String time;
    private String date;
}
