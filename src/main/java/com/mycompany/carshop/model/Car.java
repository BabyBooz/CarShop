package com.mycompany.carshop.model;

/**
 * Car - Model đại diện cho sản phẩm xe
 * 
 * Các thuộc tính:
 * - carId: ID xe (primary key)
 * - title: Tên xe
 * - price: Giá xe
 * - description: Mô tả xe
 * - categoryId: ID danh mục
 * - categoryName: Tên danh mục (join từ bảng categories)
 * - status: Trạng thái (true = có sẵn, false = không có)
 * - imageUrl: Đường dẫn ảnh
 * - quantity: Số lượng còn lại trong kho
 */
public class Car {
    private int carId;
    private String title;
    private double price;
    private String description;
    private int categoryId;
    private String categoryName;
    private boolean status;
    private String imageUrl;
    private int quantity;
    
    // Constructor mặc định
    public Car() {}
    
    // Constructor đầy đủ
    public Car(int carId, String title, double price, String description, 
               int categoryId, boolean status, String imageUrl, int quantity) {
        this.carId = carId;
        this.title = title;
        this.price = price;
        this.description = description;
        this.categoryId = categoryId;
        this.status = status;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
    }
    
    // Getters và Setters
    public int getCarId() { 
        return carId; 
    }
    
    public void setCarId(int carId) { 
        this.carId = carId; 
    }
    
    public String getTitle() { 
        return title; 
    }
    
    public void setTitle(String title) { 
        this.title = title; 
    }
    
    public double getPrice() { 
        return price; 
    }
    
    public void setPrice(double price) { 
        this.price = price; 
    }
    
    public String getDescription() { 
        return description; 
    }
    
    public void setDescription(String description) { 
        this.description = description; 
    }
    
    public int getCategoryId() { 
        return categoryId; 
    }
    
    public void setCategoryId(int categoryId) { 
        this.categoryId = categoryId; 
    }
    
    public String getCategoryName() { 
        return categoryName; 
    }
    
    public void setCategoryName(String categoryName) { 
        this.categoryName = categoryName; 
    }
    
    public boolean isStatus() { 
        return status; 
    }
    
    public void setStatus(boolean status) { 
        this.status = status; 
    }
    
    public String getImageUrl() { 
        return imageUrl; 
    }
    
    public void setImageUrl(String imageUrl) { 
        this.imageUrl = imageUrl; 
    }
    
    public int getQuantity() { 
        return quantity; 
    }
    
    public void setQuantity(int quantity) { 
        this.quantity = quantity; 
    }
}
