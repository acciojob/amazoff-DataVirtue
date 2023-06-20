package com.driver;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    public Order get(String orderId) {
        return orderRepository.get(orderId);
    }

    public void add(Order order){
        orderRepository.add(order);
    }


    public List<String> getAllOrders() {
        return orderRepository.getAllOrders();

    }
}
