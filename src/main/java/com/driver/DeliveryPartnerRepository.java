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

    public DeliveryPartnerRepository(){
        partnerOrderDatabase.put("unassigned",new ArrayList<>());
    }

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
}
