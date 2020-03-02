package juust.request;

import lombok.Data;

@Data
public class EmailRequest {
    private String emailAddress;
    private String email;
    private String phoneNr;
    private String name;
}
