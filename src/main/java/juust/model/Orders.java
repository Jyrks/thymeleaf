package juust.model;

import lombok.Data;

import java.util.List;

@Data
public class Orders {
    private List<String> orders;
    private List<String> blockedDates;
}
