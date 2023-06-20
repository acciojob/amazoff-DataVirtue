package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("orders")
public class OrderController {

@Autowired
OrderService orderService;

@Autowired
DeliveryPartnerService deliveryPartnerService;

    @PostMapping("/add-order") // working fine
    public ResponseEntity<String> addOrder(@RequestBody Order order){

        orderService.add(order);
        return new ResponseEntity<>("New order added successfully", HttpStatus.CREATED);
    }

    @PostMapping("/add-partner/{partnerId}") // working fine
    public ResponseEntity<String> addPartner(@PathVariable String partnerId){
        deliveryPartnerService.add(partnerId);
        return new ResponseEntity<>("New delivery partner added successfully", HttpStatus.CREATED);
    }

    @PutMapping("/add-order-partner-pair") // working fine
    public ResponseEntity<String> addOrderPartnerPair(@RequestParam String orderId, @RequestParam String partnerId){

        //This is basically assigning that order to that partnerId


        deliveryPartnerService.addPair(partnerId,orderId);
        orderService.addPair(partnerId,orderId);

        return new ResponseEntity<>("New order-partner pair added successfully", HttpStatus.CREATED);
    }

    @GetMapping("/get-order-by-id/{orderId}") // working fine
    public ResponseEntity<Order> getOrderById(@PathVariable String orderId){
        Order order = orderService.get(orderId);
        //order should be returned with an orderId.

        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @GetMapping("/get-partner-by-id/{partnerId}") // working fine
    public ResponseEntity<DeliveryPartner> getPartnerById(@PathVariable String partnerId){

        DeliveryPartner deliveryPartner = deliveryPartnerService.get(partnerId);

        //deliveryPartner should contain the value given by partnerId

        return new ResponseEntity<>(deliveryPartner, HttpStatus.CREATED);
    }

    @GetMapping("/get-order-count-by-partner-id/{partnerId}")
    public ResponseEntity<Integer> getOrderCountByPartnerId(@PathVariable String partnerId){

        DeliveryPartner deliveryPartner = deliveryPartnerService.get(partnerId);
        Integer orderCount = deliveryPartner.getNumberOfOrders();

        //orderCount should denote the orders given by a partner-id

        return new ResponseEntity<>(orderCount, HttpStatus.CREATED);
    }

    @GetMapping("/get-orders-by-partner-id/{partnerId}") // working fine
    public ResponseEntity<List<String>> getOrdersByPartnerId(@PathVariable String partnerId){

        List<String> orderIds = deliveryPartnerService.getOrdersByPartnerId(partnerId);

        List<String> orders = new ArrayList<>();
        for(String orderId: orderIds){
            orders.add(orderService.get(orderId).toString());
        }

        //orders should contain a list of orders by PartnerId

        return new ResponseEntity<>(orders, HttpStatus.CREATED);
    }

    @GetMapping("/get-all-orders") // working fine
    public ResponseEntity<List<String>> getAllOrders(){
        List<String> orders = orderService.getAllOrders();

        //Get all orders
        return new ResponseEntity<>(orders, HttpStatus.CREATED);
    }

    @GetMapping("/get-count-of-unassigned-orders") // working
    public ResponseEntity<Integer> getCountOfUnassignedOrders(){
        Integer countOfOrders = orderService.getUnassignedOrderCount();

        //Count of orders that have not been assigned to any DeliveryPartner

        return new ResponseEntity<>(countOfOrders, HttpStatus.CREATED);
    }

    @GetMapping("/get-count-of-orders-left-after-given-time/{partnerId}")
    public ResponseEntity<Integer> getOrdersLeftAfterGivenTimeByPartnerId(@PathVariable String time, @PathVariable String partnerId){
        List<String> list = deliveryPartnerService.getOrdersByPartnerId(partnerId);
        Order dummy = new Order("1",time);

        Integer countOfOrders = 0;

        for(String orderId: list){
            Order order = orderService.get(orderId);
            if(order.getDeliveryTime()>dummy.getDeliveryTime())
                countOfOrders++;
        }

        //countOfOrders that are left after a particular time of a DeliveryPartner

        return new ResponseEntity<>(countOfOrders, HttpStatus.CREATED);
    }

    @GetMapping("/get-last-delivery-time/{partnerId}")
    public ResponseEntity<String> getLastDeliveryTimeByPartnerId(@PathVariable String partnerId){
         List<String> list = deliveryPartnerService.getOrdersByPartnerId(partnerId);
         int maxTime = 0;
         for(String orderId: list){
             Order order = orderService.get(orderId);
             maxTime = Math.max(maxTime,order.getDeliveryTime());
         }
         String minutes = maxTime%60>10?maxTime%60 + "":maxTime%60+"0";
        String time = maxTime/60 + ":" + minutes;
        //Return the time when that partnerId will deliver his last delivery order.


        return new ResponseEntity<>(time, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete-partner-by-id/{partnerId}")
    public ResponseEntity<String> deletePartnerById(@PathVariable String partnerId){

        //Delete the partnerId
        //And push all his assigned orders to unassigned orders.
        deliveryPartnerService.deletePartner(partnerId);
        orderService.changeOrderAssigenment(partnerId);

        return new ResponseEntity<>(partnerId + " removed successfully", HttpStatus.CREATED);
    }

    @DeleteMapping("/delete-order-by-id/{orderId}")
    public ResponseEntity<String> deleteOrderById(@PathVariable String orderId){

        //Delete an order and also
        // remove it from the assigned order of that partnerId
        orderService.deleteOrder(orderId);
        deliveryPartnerService.removeAssignedOrder(orderId);


        return new ResponseEntity<>(orderId + " removed successfully", HttpStatus.CREATED);
    }
}
