package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DeliveryPartnerRepository {

    Map<String,DeliveryPartner> database = new HashMap<>();
    Map<String, List<String>> partnerOrderDatabase = new HashMap<>();



    public void add(DeliveryPartner deliveryPartner){

        database.put(deliveryPartner.getId(),deliveryPartner);

    }
    public List<String> getOrdersbyPartnerId(String partnerId){
        if(!partnerOrderDatabase.containsKey(partnerId))
            return null;
        return partnerOrderDatabase.get(partnerId);
    }

    public DeliveryPartner get(String partnerId) {

        if(!database.containsKey(partnerId))
            return null;
        return database.get(partnerId);

    }

    public void addPair(String partnerId, String orderId) {
        if(!database.containsKey(partnerId))
            return;
        DeliveryPartner partner = database.get(partnerId);


        List<String> list =  partnerOrderDatabase.getOrDefault(partnerId,new ArrayList<>()); // add new order to the orderlist
        list.add(orderId);
        partner.setNumberOfOrders(list.size()); // update

        partnerOrderDatabase.put(partnerId,list);

    }

    public void deletePartner(String partnerId) {
        database.remove(partnerId);
        partnerOrderDatabase.remove(partnerId);
    }

    public void removeAssignedOrder(String orderId) {
        for(String partnerId: partnerOrderDatabase.keySet()){
            for(String s: partnerOrderDatabase.get(partnerId)){
                if(s.equals(orderId)){
                    partnerOrderDatabase.get(partnerId).remove(orderId);
                    break;
                }
            }
        }
    }
}
