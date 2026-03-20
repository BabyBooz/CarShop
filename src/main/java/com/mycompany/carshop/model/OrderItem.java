package com.mycompany.carshop.model;

public class OrderItem {
    private int orderItemId;
    private int orderId;
    private int carId;
    private String carTitle;
    private int quantity;
    private double price;
    private double totalPrice;
    
    public OrderItem() {}
    
    public int getOrderItemId() { return orderItemId; }
    public void setOrderItemId(int orderItemId) { this.orderItemId = orderItemId; }
    
    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }
    
    public int getCarId() { return carId; }
    public void setCarId(int carId) { this.carId = carId; }
    
    public String getCarTitle() { return carTitle; }
    public void setCarTitle(String carTitle) { this.carTitle = carTitle; }
    
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    
    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }
}
