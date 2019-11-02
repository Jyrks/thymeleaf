package juust.resource;

import juust.model.Orders;
import juust.request.OrderRequest;
import juust.service.OrderService;
import juust.service.RestrictionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrderResource {

    @Autowired
    private OrderService orderService;

    @Autowired
    private RestrictionService restrictionService;

    @PostMapping("/createOrder")
    public ResponseEntity createOrder(@RequestBody OrderRequest request) {
        log.info(request.getTime());
        log.info(request.getDate());
        orderService.createOrder(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/getOrders")
    public ResponseEntity<Orders> getOrders() {
        return new ResponseEntity<>(orderService.getOrders(), HttpStatus.OK);
    }

    @PostMapping("/blockDate")
    public ResponseEntity blockDate(@RequestBody String date) {
        restrictionService.addRestriction(date);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
