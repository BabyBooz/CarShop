package com.mycompany.carshop.model;

public class CartItem {
    private int cartItemId;
    private int cartId;
    private int carId;
    private String carTitle;
    private double carPrice;
    private String imageUrl;
    private int quantity;
    private double totalPrice;
    
    public CartItem() {}
    
    public int getCartItemId() { return cartItemId; }
    public void setCartItemId(int cartItemId) { this.cartItemId = cartItemId; }
    
    public int getCartId() { return cartId; }
    public void setCartId(int cartId) { this.cartId = cartId; }
    
    public int getCarId() { return carId; }
    public void setCarId(int carId) { this.carId = carId; }
    
    public String getCarTitle() { return carTitle; }
    public void setCarTitle(String carTitle) { this.carTitle = carTitle; }
    
    public double getCarPrice() { return carPrice; }
    public void setCarPrice(double carPrice) { this.carPrice = carPrice; }
    
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    
    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }
}
