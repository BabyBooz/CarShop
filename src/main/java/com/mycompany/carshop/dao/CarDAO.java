package com.mycompany.carshop.dao;

import com.mycompany.carshop.model.Car;
import com.mycompany.carshop.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarDAO {
    
    public List<Car> getAllCars() {
        List<Car> cars = new ArrayList<>();
        String sql = "SELECT c.*, cat.name as category_name FROM cars c LEFT JOIN categories cat ON c.category_id = cat.category_id WHERE c.status = 1";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Car car = new Car();
                car.setCarId(rs.getInt("car_id"));
                car.setTitle(rs.getString("title"));
                car.setPrice(rs.getDouble("price"));
                car.setDescription(rs.getString("description"));
                car.setCategoryId(rs.getInt("category_id"));
                car.setCategoryName(rs.getString("category_name"));
                car.setStatus(rs.getBoolean("status"));
                car.setImageUrl(rs.getString("image_url"));
                cars.add(car);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }
    
    public List<Car> getAllCarsForAdmin() {
        List<Car> cars = new ArrayList<>();
        String sql = "SELECT c.*, cat.name as category_name FROM cars c LEFT JOIN categories cat ON c.category_id = cat.category_id";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Car car = new Car();
                car.setCarId(rs.getInt("car_id"));
                car.setTitle(rs.getString("title"));
                car.setPrice(rs.getDouble("price"));
                car.setDescription(rs.getString("description"));
                car.setCategoryId(rs.getInt("category_id"));
                car.setCategoryName(rs.getString("category_name"));
                car.setStatus(rs.getBoolean("status"));
                car.setImageUrl(rs.getString("image_url"));
                cars.add(car);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }
    
    public Car getCarById(int carId) {
        String sql = "SELECT c.*, cat.name as category_name FROM cars c LEFT JOIN categories cat ON c.category_id = cat.category_id WHERE c.car_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, carId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Car car = new Car();
                car.setCarId(rs.getInt("car_id"));
                car.setTitle(rs.getString("title"));
                car.setPrice(rs.getDouble("price"));
                car.setDescription(rs.getString("description"));
                car.setCategoryId(rs.getInt("category_id"));
                car.setCategoryName(rs.getString("category_name"));
                car.setStatus(rs.getBoolean("status"));
                car.setImageUrl(rs.getString("image_url"));
                return car;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public boolean addCar(Car car) {
        String sql = "INSERT INTO cars (title, price, description, category_id, status, image_url) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, car.getTitle());
            ps.setDouble(2, car.getPrice());
            ps.setString(3, car.getDescription());
            ps.setInt(4, car.getCategoryId());
            ps.setBoolean(5, car.isStatus());
            ps.setString(6, car.getImageUrl());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean updateCar(Car car) {
        String sql = "UPDATE cars SET title = ?, price = ?, description = ?, category_id = ?, status = ?, image_url = ? WHERE car_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, car.getTitle());
            ps.setDouble(2, car.getPrice());
            ps.setString(3, car.getDescription());
            ps.setInt(4, car.getCategoryId());
            ps.setBoolean(5, car.isStatus());
            ps.setString(6, car.getImageUrl());
            ps.setInt(7, car.getCarId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean deleteCar(int carId) {
        String sql = "DELETE FROM cars WHERE car_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, carId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
