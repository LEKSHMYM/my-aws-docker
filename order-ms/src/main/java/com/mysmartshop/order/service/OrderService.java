package com.mysmartshop.order.service;

 

import java.util.List;

import com.mysmartshop.order.model.CartItem;
import com.mysmartshop.order.model.Order;

 

public interface OrderService {
    public Order placeOrder(List<CartItem> cartItems);

 

    public Order getOrderDetails(String orderId);

 

    public void updateOrderDetails(String orderId, String status);

 

    public void cancelOrder(String orderId);

 

    public List<Order> getAllOrders();
}