package com.driver;


import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OrderRepository {
    Map<String, Order> database = new HashMap<>();
    Map<Order,String>orderPartnerDatabase = new HashMap<>();


    public Order get(String orderId) {
        if(!database.containsKey(orderId))
            return null;
        return database.get(orderId);
    }

    public void add(Order order){
        database.put(order.getId(),order);
        orderPartnerDatabase.put(order,"unassigned");
    }

    public List<String> getAllOrders() {
        List<String> list = new ArrayList<>();

        for(Order order: database.values())
            list.add(order.toString());
        return list;
    }

}
