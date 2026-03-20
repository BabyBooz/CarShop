package com.mycompany.carshop.dao;

import com.mycompany.carshop.model.CartItem;
import com.mycompany.carshop.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartDAO {
    
    public int getOrCreateCart(int userId) {
        String checkSql = "SELECT cart_id FROM cart WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(checkSql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("cart_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        String insertSql = "INSERT INTO cart (user_id) VALUES (?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, userId);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    
    public List<CartItem> getCartItems(int userId) {
        List<CartItem> items = new ArrayList<>();
        int cartId = getOrCreateCart(userId);
        String sql = "SELECT ci.*, c.title, c.price, c.image_url FROM cart_items ci " +
                     "JOIN cars c ON ci.car_id = c.car_id WHERE ci.cart_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cartId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CartItem item = new CartItem();
                item.setCartItemId(rs.getInt("cart_item_id"));
                item.setCartId(rs.getInt("cart_id"));
                item.setCarId(rs.getInt("car_id"));
                item.setCarTitle(rs.getString("title"));
                item.setCarPrice(rs.getDouble("price"));
                item.setImageUrl(rs.getString("image_url"));
                item.setQuantity(rs.getInt("quantity"));
                item.setTotalPrice(rs.getDouble("total_price"));
                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }
    
    public boolean addToCart(int userId, int carId, int quantity, double totalPrice) {
        int cartId = getOrCreateCart(userId);
        String checkSql = "SELECT * FROM cart_items WHERE cart_id = ? AND car_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(checkSql)) {
            ps.setInt(1, cartId);
            ps.setInt(2, carId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String updateSql = "UPDATE cart_items SET quantity = quantity + ?, total_price = total_price + ? WHERE cart_id = ? AND car_id = ?";
                try (PreparedStatement updatePs = conn.prepareStatement(updateSql)) {
                    updatePs.setInt(1, quantity);
                    updatePs.setDouble(2, totalPrice);
                    updatePs.setInt(3, cartId);
                    updatePs.setInt(4, carId);
                    return updatePs.executeUpdate() > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        String insertSql = "INSERT INTO cart_items (cart_id, car_id, quantity, total_price) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(insertSql)) {
            ps.setInt(1, cartId);
            ps.setInt(2, carId);
            ps.setInt(3, quantity);
            ps.setDouble(4, totalPrice);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean updateCartItem(int cartItemId, int quantity, double totalPrice) {
        String sql = "UPDATE cart_items SET quantity = ?, total_price = ? WHERE cart_item_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, quantity);
            ps.setDouble(2, totalPrice);
            ps.setInt(3, cartItemId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean removeCartItem(int cartItemId) {
        String sql = "DELETE FROM cart_items WHERE cart_item_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cartItemId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean clearCart(int userId) {
        int cartId = getOrCreateCart(userId);
        String sql = "DELETE FROM cart_items WHERE cart_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cartId);
            return ps.executeUpdate() >= 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
