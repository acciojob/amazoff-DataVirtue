package com.driver;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository = new OrderRepository();

    public OrderService(){


    }

    public void addPartner(String deliveryPartnerId){


        orderRepository.addPartner(deliveryPartnerId);
//        System.out.println(deliveryPartner.getNumberOfOrders());

    }
    public DeliveryPartner getPartnerById(String partnerId) {

        return orderRepository.getPartner(partnerId);
    }

    public List<String> getOrdersByPartnerId(String partnerId)  {
        List<String> list = new ArrayList<>();
        Set<String> set = orderRepository.getOrdersByPartnerId(partnerId);
        if(set==null)
            return list;
        for(String oid: set){
            list.add(getOrderById(oid).toString());
        }

        return list;
    }

    public void addOrderPartnerPair(String orderId, String partnerId)  {
        orderRepository.addOrderPartnerPair(orderId,partnerId);
    }

    public void deletePartnerById(String partnerId) {
        orderRepository.deletePartnerById(partnerId);
    }


    public Order getOrderById(String orderId)  {

        return orderRepository.getOrder(orderId);
    }

    public void addOrder(Order order)  {

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

    public Integer getOrderCountByPartnerId(String partnerId)  {

        DeliveryPartner deliveryPartner = orderRepository.getPartner(partnerId);

        Integer orderCount = deliveryPartner.getNumberOfOrders();
        return orderCount;
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String partnerId, String time) {

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

    public String getLastDeliveryTimeByPartnerId(String partnerId) {

        List<String> list = this.getOrdersByPartnerId(partnerId);

        int maxTime = 0;
        for(String orderId: list){
            Order order = null;
            order = this.getOrderById(orderId);
            maxTime = Math.max(maxTime,order.getDeliveryTime());
        }
        String minutes = maxTime%60>10?maxTime%60 + "":"0" +maxTime%60;
        String hours = maxTime/60>10?maxTime/60+"":"0" + maxTime/60;

        String time = hours + ":" + minutes;
        //Return the time when that partnerId will deliver his last delivery order.
        return time;

    }
}
