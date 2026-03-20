package com.mycompany.carshop.dao;

import com.mycompany.carshop.model.Order;
import com.mycompany.carshop.model.OrderItem;
import com.mycompany.carshop.model.CartItem;
import com.mycompany.carshop.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    
    public boolean createOrder(int userId, List<CartItem> cartItems, double totalPrice) {
        String orderSql = "INSERT INTO orders (user_id, total_price, status) VALUES (?, ?, 'wait')";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(orderSql, Statement.RETURN_GENERATED_KEYS)) {
            conn.setAutoCommit(false);
            ps.setInt(1, userId);
            ps.setDouble(2, totalPrice);
            ps.executeUpdate();
            
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int orderId = rs.getInt(1);
                String itemSql = "INSERT INTO order_items (order_id, car_id, quantity, price, total_price) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement itemPs = conn.prepareStatement(itemSql)) {
                    for (CartItem item : cartItems) {
                        itemPs.setInt(1, orderId);
                        itemPs.setInt(2, item.getCarId());
                        itemPs.setInt(3, item.getQuantity());
                        itemPs.setDouble(4, item.getCarPrice());
                        itemPs.setDouble(5, item.getTotalPrice());
                        itemPs.addBatch();
                    }
                    itemPs.executeBatch();
                }
                conn.commit();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public List<Order> getOrdersByUserId(int userId) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE user_id = ? ORDER BY created_at DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Order order = new Order();
                order.setOrderId(rs.getInt("order_id"));
                order.setUserId(rs.getInt("user_id"));
                order.setTotalPrice(rs.getDouble("total_price"));
                order.setStatus(rs.getString("status"));
                order.setCreatedAt(rs.getTimestamp("created_at"));
                order.setUpdatedAt(rs.getTimestamp("updated_at"));
                order.setItems(getOrderItems(order.getOrderId()));
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }
    
    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders ORDER BY created_at DESC";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Order order = new Order();
                order.setOrderId(rs.getInt("order_id"));
                order.setUserId(rs.getInt("user_id"));
                order.setTotalPrice(rs.getDouble("total_price"));
                order.setStatus(rs.getString("status"));
                order.setCreatedAt(rs.getTimestamp("created_at"));
                order.setUpdatedAt(rs.getTimestamp("updated_at"));
                order.setItems(getOrderItems(order.getOrderId()));
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }
    
    public List<OrderItem> getOrderItems(int orderId) {
        List<OrderItem> items = new ArrayList<>();
        String sql = "SELECT oi.*, c.title FROM order_items oi JOIN cars c ON oi.car_id = c.car_id WHERE oi.order_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                OrderItem item = new OrderItem();
                item.setOrderItemId(rs.getInt("order_item_id"));
                item.setOrderId(rs.getInt("order_id"));
                item.setCarId(rs.getInt("car_id"));
                item.setCarTitle(rs.getString("title"));
                item.setQuantity(rs.getInt("quantity"));
                item.setPrice(rs.getDouble("price"));
                item.setTotalPrice(rs.getDouble("total_price"));
                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }
    
    public boolean updateOrderStatus(int orderId, String status) {
        String sql = "UPDATE orders SET status = ?, updated_at = GETDATE() WHERE order_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, orderId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
