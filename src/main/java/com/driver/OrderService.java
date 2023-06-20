package com.driver;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    public Order getOrder(String orderId) {

        return orderRepository.getOrder(orderId);
    }

    public void addOrder(Order order){
        orderRepository.addOrder(order);
    }


    public List<String> getAllOrders() {
        return orderRepository.getAllOrders();

    }

    public void addPair(String partnerId, String orderId) {
        orderRepository.addPair(partnerId,orderId);
    }

    public Integer getUnassignedOrderCount() {
        return orderRepository.getUnassignedOrderCount();
    }

    public void changeOrderAssigenment(String partnerId) {
        orderRepository.changeOrderAssignment(partnerId);

    }

    public void deleteOrder(String orderId) {
        orderRepository.deleteOrder(orderId);
    }
}
