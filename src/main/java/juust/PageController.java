package juust;

import juust.model.Platter;
import juust.request.EmailRequest;
import juust.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PageController {

    @Autowired
    private EmailService emailService;

    private static List<Platter> availablePlatters = new ArrayList<>();

    static {
        availablePlatters.add(new Platter("Väike", 60.00));
        availablePlatters.add(new Platter("Suur", 80.00));
    }

    @GetMapping("/")
    public String index(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "pealeht";
    }

    @GetMapping("/telli")
    public String telli(Model model) {
        model.addAttribute("availablePlatters", availablePlatters);
        return "telli";
    }

    @GetMapping("/tellitud")
    public String vaagenTellitud(Model model) {
        model.addAttribute("availablePlatters", availablePlatters);
        return "tellitud";
    }

    @GetMapping("/email-saadetud")
    public String emailSaadetud(Model model) {
        model.addAttribute("availablePlatters", availablePlatters);
        return "email-saadetud";
    }

    @GetMapping("/kontakt")
    public String contactPage(Model model) {
        model.addAttribute("availablePlatters", availablePlatters);
        return "kontakt";
    }

    @GetMapping("/info")
    public String infotPage(Model model) {
        return "info";
    }

    @PostMapping("/sendEmail")
    public ResponseEntity email(@RequestBody EmailRequest request) {
        emailService.sendUserEmail(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
