package juust.service;

import org.springframework.stereotype.Service;

import juust.request.OrderRequest;

@Service
public class OrderService {

    public void createOrder(OrderRequest request) {
        System.out.println(request);
    }
}
