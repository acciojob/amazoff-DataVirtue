package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryPartnerService {

    @Autowired
    DeliveryPartnerRepository deliveryPartnerRepository;
    public void add(String deliveryPartnerId){
        DeliveryPartner deliveryPartner = new DeliveryPartner(deliveryPartnerId);
        deliveryPartnerRepository.add(deliveryPartner);
//        System.out.println(deliveryPartner.getNumberOfOrders());

    }

    public DeliveryPartner get(String partnerId) {
        return deliveryPartnerRepository.get(partnerId);
    }
    public List<String> getOrdersByPartnerId(String partnerId){
        return deliveryPartnerRepository.getOrdersbyPartnerId(partnerId);
    }
}
