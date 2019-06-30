package juust;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import juust.model.Platter;
import juust.request.OrderRequest;
import juust.service.OrderService;

@Controller
public class PageController {

    @Autowired
    private OrderService orderService;

    private static List<Platter> availablePlatters = new ArrayList<>();

    static {
        availablePlatters.add(new Platter("Väike", 65.00));
        availablePlatters.add(new Platter("Suur", 85.00));
    }

    @GetMapping("/")
    public String index(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "index";
    }

    @GetMapping("/koosta-vaagen.html")
    public String koostaVaagen(Model model) {
        model.addAttribute("availablePlatters", availablePlatters);
        return "koosta-vaagen";
    }

    @GetMapping("/tellimus")
    public String vaagenTellitud(Model model) {
        model.addAttribute("availablePlatters", availablePlatters);
        return "vaagen-tellitud";
    }

    @PostMapping("/order")
    public ResponseEntity tellimus(@RequestBody OrderRequest request) {
        orderService.createOrder(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
