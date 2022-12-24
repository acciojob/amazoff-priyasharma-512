package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;


@Repository
public class OrderRepository {

    private HashMap<String, Order> orderHashMap = new HashMap<String, Order>();
    private HashMap<String,DeliveryPartner> partnerHashMap = new HashMap<String, DeliveryPartner>();

    private HashMap<String, List<String>> pairHashMap = new HashMap<String, List<String>>();

    HashSet<String> unassignedordersHashSet = new HashSet<String>();

    public void addOrderRepository(Order order)
    {
        orderHashMap.put(order.getId(), order);
        unassignedordersHashSet.add(order.getId());
    }

    public void addPartnerRepository(String partnerId) {
        DeliveryPartner dl = new DeliveryPartner(partnerId);
        partnerHashMap.put(partnerId, dl);
    }

    public void addOrderPartnerPairRepository(String orderId, String partnerId) {
        if(orderHashMap.containsKey(orderId) && partnerHashMap.containsKey(partnerId))
        {
            List<String> newpair = new ArrayList<String>();
            if(pairHashMap.containsKey(partnerId))
                newpair = pairHashMap.get(partnerId);
            newpair.add(orderId);
            partnerHashMap.get(partnerId).setNumberOfOrders(newpair.size());
            pairHashMap.put(partnerId,newpair);
            unassignedordersHashSet.remove(orderId);
        }
    }

    public Order getOrderByIdRepository(String orderId) {
        return orderHashMap.get(orderId);
    }

    public DeliveryPartner getPartnerByIdRepository(String partnerId) {
        return partnerHashMap.get(partnerId);
    }

    public Integer getOrderCountByPartnerIdRepository(String partnerId) {

        return partnerHashMap.get(partnerId).getNumberOfOrders();
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

    public Integer getCountOfUnassignedOrdersRepository() {
        return unassignedordersHashSet.size();
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerIdRepository(String time, String partnerId) {
        Integer countOfOrders = 0;
        List<String> order = pairHashMap.get(partnerId);
        for (String orderId: order) {
            if(orderHashMap.get(orderId).getDeliveryTime() > Integer.parseInt(time))
                countOfOrders++;
        }
        return countOfOrders;
    }
    public String getLastDeliveryTimeByPartnerIdRepository(String partnerId) {
        String timed = "0";
        List<String> order = pairHashMap.get(partnerId);
        for(String orderId: order)
        {
            timed = String.valueOf(Math.max(Integer.parseInt(timed), orderHashMap.get(orderId).getDeliveryTime()));

        }
        return timed;
    }


    public void deletePartnerByIdRepository(String partnerId) {

        pairHashMap.remove(partnerId);
        List<String> orderList1;
        if(pairHashMap.containsKey(partnerId)) {
            orderList1 = pairHashMap.get(partnerId);
            for (String ord: orderList1) {
                unassignedordersHashSet.add(ord);
            }
            partnerHashMap.remove(partnerId);

        }
    }

    public void deleteOrderByIdRepository(String orderId) {
        List<String> ord;

        for (String partnerId: pairHashMap.keySet()) {
            ord = pairHashMap.get(partnerId);
            for (String order : ord) {
                if (orderId.equals(order)) {
                    ord.remove(orderId);
                    pairHashMap.put(partnerId,ord);
                    break;
                }
            }
        }
        unassignedordersHashSet.remove(orderId);
        orderHashMap.remove(orderId);
    }



}
