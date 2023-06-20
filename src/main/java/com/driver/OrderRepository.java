package com.driver;


import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OrderRepository {
    Map<String, Order> database = new HashMap<>();
    Map<String,String>orderPartnerDatabase = new HashMap<>();


    public Order getOrder(String orderId) {
        if(!database.containsKey(orderId))
            return null;
        return database.get(orderId);
    }

    public void addOrder(Order order){
        database.put(order.getId(),order);
        orderPartnerDatabase.put(order.getId(),"unassigned");
    }

    public List<String> getAllOrders() {
        List<String> list = new ArrayList<>();

        for(Order order: database.values())
            list.add(order.toString());
        return list;
    }

    public void addPair(String partnerId, String orderId) {
        orderPartnerDatabase.put(orderId,partnerId);
    }

    public int getUnassignedOrderCount() {
        int count = 0;
        for(String s: orderPartnerDatabase.values())
            if(s.equals("unassigned"))
                count++;
        return count;

    }

    public void changeOrderAssignment(String partnerId) {
        for(String orderId: orderPartnerDatabase.keySet()){
            if(orderPartnerDatabase.get(orderId).equals(partnerId)){
                orderPartnerDatabase.put(orderId,"unassigned");
            }
        }
    }

    public void deleteOrder(String orderId) {
        database.remove(orderId);
        orderPartnerDatabase.remove(orderId);


    }
}
