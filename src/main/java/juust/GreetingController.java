package juust;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import model.Platter;

@Controller
public class GreetingController {

    private static List<Platter> availablePlatters = new ArrayList<>();

    static {
        availablePlatters.add(new Platter("Mahe"));
        availablePlatters.add(new Platter("Tore"));
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

    @PostMapping("/vaagen-tellitud")
    public String vaagenTellitud(Model model) {
        model.addAttribute("availablePlatters", availablePlatters);
        return "vaagen-tellitud";
    }
}