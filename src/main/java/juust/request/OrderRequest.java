package juust.request;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    private List<String> platters;
    private String personName;
    private String email;
    private String phoneNumber;
    private String time;
    private String date;
}
