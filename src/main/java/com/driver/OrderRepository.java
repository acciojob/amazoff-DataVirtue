package com.driver;


import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class OrderRepository {
    Map<String, Order> orderMap = new HashMap<>();
    Map<String,String>orderPartnerDatabase = new HashMap<>();

    Map<String,DeliveryPartner> partnerMap = new HashMap<>();
    Map<String, Set<String>> partnerOrderDatabase = new HashMap<>();




    public Order getOrder(String orderId) {
        if(!orderMap.containsKey(orderId))
            return null;
        return orderMap.get(orderId);
    }

    public void addPartner(DeliveryPartner deliveryPartner){

        partnerMap.put(deliveryPartner.getId(),deliveryPartner);

    }

    public Set<String> getOrdersByPartnerId(String partnerId){
        if(!partnerOrderDatabase.containsKey(partnerId))
            return null;
        return partnerOrderDatabase.get(partnerId);
    }

    public void addOrderPartnerPair(String partnerId, String orderId) {
        if(!partnerMap.containsKey(partnerId))
            return;
        if(!orderMap.containsKey(orderId))
            return;

        DeliveryPartner partner = partnerMap.get(partnerId);


        Set<String> set =  partnerOrderDatabase.getOrDefault(partnerId,new HashSet<>()); // add new order to the orderlist
        set.add(orderId);
        partner.setNumberOfOrders(set.size()); // update

        partnerOrderDatabase.put(partnerId,set);
        orderPartnerDatabase.put(orderId,partnerId);

    }



    public DeliveryPartner getPartner(String partnerId) {

        if(!partnerMap.containsKey(partnerId))
            return null;
        return partnerMap.get(partnerId);

    }

    public void addOrder(Order order){
        orderMap.put(order.getId(),order);
        orderPartnerDatabase.put(order.getId(),"unassigned");
    }

    public List<String> getAllOrders() {
        List<String> list = new ArrayList<>();

        for(Order order: orderMap.values())
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



    public void deletePartnerById(String partnerId) {

            for(String orderId: orderPartnerDatabase.keySet()){
                if(orderPartnerDatabase.get(orderId).equals(partnerId)){
                    orderPartnerDatabase.put(orderId,"unassigned");
                }
            }

        partnerMap.remove(partnerId);
        partnerOrderDatabase.remove(partnerId);
    }

    public void deleteOrderById(String orderId) {
        orderMap.remove(orderId);
        String partnerId = orderPartnerDatabase.get(orderId);

        Set<String> set = partnerOrderDatabase.get(partnerId);
        set.remove(orderId);
//        for(String oid: list ){
//            if(oid.equals(orderId)){
//                list.remove(orderId);
//            }
//        }
        DeliveryPartner partner = partnerMap.get(partnerId);
        partner.setNumberOfOrders(set.size());
        partnerOrderDatabase.put(partnerId,set);

        orderPartnerDatabase.remove(orderId);


    }
}
