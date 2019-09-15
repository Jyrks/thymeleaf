package juust.resource;

import juust.request.OrderRequest;
import juust.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrderResource {

    @Autowired
    private OrderService orderService;

    @PostMapping("/createOrder")
    public ResponseEntity createOrder(@RequestBody OrderRequest request) {
        log.info(request.getTime());
        log.info(request.getDate());
        orderService.createOrder(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/getOrders")
    public ResponseEntity<List<String>> getOrders() {
        return new ResponseEntity<>(orderService.getOrderDates(), HttpStatus.OK);
    }
}
