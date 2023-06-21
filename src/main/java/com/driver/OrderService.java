package com.driver;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    public void addPartner(String deliveryPartnerId){
        DeliveryPartner deliveryPartner = new DeliveryPartner(deliveryPartnerId);
        orderRepository.addPartner(deliveryPartner);
//        System.out.println(deliveryPartner.getNumberOfOrders());

    }
    public DeliveryPartner getPartnerById(String partnerId) {

        return orderRepository.getPartner(partnerId);
    }

    public List<String> getOrdersByPartnerId(String partnerId) throws Exception {
        List<String> list = new ArrayList<>();
        Set<String> set = orderRepository.getOrdersByPartnerId(partnerId);

        for(String oid: set){
            list.add(getOrderById(oid).toString());
        }

        return list;
    }

    public void addOrderPartnerPair(String partnerId, String orderId) {
        orderRepository.addOrderPartnerPair(partnerId,orderId);
    }

    public void deletePartnerById(String partnerId) {
        orderRepository.deletePartnerById(partnerId);
    }


    public Order getOrderById(String orderId) throws Exception {

        return orderRepository.getOrder(orderId);
    }

    public void addOrder(Order order){
        orderRepository.addOrder(order);
    }


    public List<String> getAllOrders() {
        return orderRepository.getAllOrders();

    }

    public Integer getUnassignedOrderCount() {
        return orderRepository.getUnassignedOrderCount();
    }


    public void deleteOrderById(String orderId) {
        orderRepository.deleteOrderById(orderId);
    }

    public Integer getOrderCountByPartnerId(String partnerId) {

        DeliveryPartner deliveryPartner = orderRepository.getPartner(partnerId);
        if(deliveryPartner==null)
            return null;

        Integer orderCount = deliveryPartner.getNumberOfOrders();
        return orderCount;
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String partnerId, String time) throws Exception {

        Set<String> set = orderRepository.getOrdersByPartnerId(partnerId);
        Order dummy = new Order("1",time);

        Integer countOfOrders = 0;

        for(String orderId: set){
            Order order = orderRepository.getOrder(orderId);
            if(order.getDeliveryTime()>dummy.getDeliveryTime())
                countOfOrders++;
        }
        return countOfOrders;
    }
}
