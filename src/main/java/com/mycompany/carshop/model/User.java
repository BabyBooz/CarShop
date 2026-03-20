package com.mycompany.carshop.model;

public class User {
    private int userId;
    private String rollNumber;
    private String fullName;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String address;
    private String role;
    
    public User() {}
    
    public User(int userId, String rollNumber, String fullName, String username, 
                String email, String phone, String address, String role) {
        this.userId = userId;
        this.rollNumber = rollNumber;
        this.fullName = fullName;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.role = role;
    }
    
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    
    public String getRollNumber() { return rollNumber; }
    public void setRollNumber(String rollNumber) { this.rollNumber = rollNumber; }
    
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    
    public boolean isAdmin() {
        return "admin".equals(role);
    }
}
