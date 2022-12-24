package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Repository
public class OrderRepository {

    private HashMap<String, Order> orderHashMap = new HashMap<String, Order>();
    private HashMap<String,DeliveryPartner> partnerHashMap = new HashMap<String, DeliveryPartner>();

    private HashMap<String, List<String>> pairHashMap = new HashMap<String, List<String>>();

    public void addOrderRepository(Order order)
    {
        orderHashMap.put(order.getId(), order);
    }

    public void addPartnerRepository(String partnerId) {
        DeliveryPartner dl = new DeliveryPartner();
        //dl.setNumberOfOrders(1);
        partnerHashMap.put(partnerId, dl);
    }

    public void addOrderPartnerPairRepository(String orderId, String partnerId) {
        if(orderHashMap.containsKey(orderId) && partnerHashMap.containsKey(partnerId))
        {
            List<String> newpair = new ArrayList<String>();
            if(pairHashMap.containsKey(partnerId))
                newpair = pairHashMap.get(partnerId);
            newpair.add(orderId);
            pairHashMap.put(partnerId,newpair);
        }
    }

    public Order getOrderByIdRepository(String orderId) {
        return orderHashMap.get(orderId);
    }

    public DeliveryPartner getPartnerByIdRepository(String partnerId) {
        return partnerHashMap.get(partnerId);
    }

    public Integer getOrderCountByPartnerIdRepository(String partnerId) {
        Integer orderCount = 0;
        if(pairHashMap.containsKey(partnerId))
            orderCount++;
        return orderCount;
    }

    public List<String> getOrdersByPartnerIdRepository(String partnerId) {
        List<String> ordersList = new ArrayList<String>();
        if(orderHashMap.containsKey(partnerId))
            ordersList = pairHashMap.get(partnerId);
        return ordersList;
    }


    public List<String> getAllOrdersRepository() {
        return new ArrayList<>(orderHashMap.keySet());
    }

    public void getCountOfUnassignedOrdersRepository() {
        Integer countOfOrders = 0;
        if(orderHashMap.containsKey()
    }

    public void getOrdersLeftAfterGivenTimeByPartnerIdRepository(String time, String partnerId) {

    }
    public void getLastDeliveryTimeByPartnerIdRepository(String partnerId) {

    }


    public void deletePartnerByIdRepository(String partnerId) {
        if(pairHashMap.containsKey(partnerId))
        {
            pairHashMap.remove(partnerId);
        }
        if(partnerHashMap.containsKey(partnerId))
            partnerHashMap.remove(partnerId);
    }

    public void deleteOrderByIdRepository(String orderId) {
        List<String> ord = new ArrayList<String>();
        if(pairHashMap.containsKey(orderId))
        {
            ord = pairHashMap.get(orderId);
            for (String order:ord) {
                if(orderHashMap.containsKey(orderId))
                    orderHashMap.remove(orderId);
            }
            pairHashMap.remove(orderId);
        }
        if(partnerHashMap.containsKey(orderId))
            partnerHashMap.remove(orderId);
    }



}
