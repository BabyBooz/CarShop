package com.mycompany.carshop.servlet;

import com.mycompany.carshop.dao.CartDAO;
import com.mycompany.carshop.dao.OrderDAO;
import com.mycompany.carshop.dao.CarDAO;
import com.mycompany.carshop.model.CartItem;
import com.mycompany.carshop.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

/**
 * CheckoutServlet - Xử lý thanh toán (chuyển giỏ hàng thành đơn hàng)
 * URL: /checkout
 * 
 * Chức năng:
 * - POST: Tạo đơn hàng từ giỏ hàng, trừ số lượng sản phẩm, xóa giỏ hàng
 */
@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {
    
    /**
     * Xử lý POST request - Thanh toán giỏ hàng
     * 
     * Bước:
     * 1. Lấy giỏ hàng của user
     * 2. Kiểm tra giỏ hàng có trống không
     * 3. Tính tổng tiền
     * 4. Tạo đơn hàng (INSERT vào orders và order_items)
     * 5. Trừ số lượng sản phẩm trong kho
     * 6. Xóa giỏ hàng
     * 7. Redirect đến trang lịch sử đơn hàng
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Lấy user hiện tại từ request attribute (được set bởi AuthFilter)
        User user = (User) request.getAttribute("currentUser");
        
        // Tạo CartDAO để lấy giỏ hàng
        CartDAO cartDAO = new CartDAO();
        
        // Lấy danh sách sản phẩm trong giỏ hàng của user
        List<CartItem> cartItems = cartDAO.getCartItems(user.getUserId());
        
        // Kiểm tra xem giỏ hàng có trống không
        if (cartItems.isEmpty()) {
            // Nếu giỏ hàng trống, redirect lại trang giỏ hàng
            response.sendRedirect(request.getContextPath() + "/cart?userId=" + user.getUserId());
            return;
        }
        
        // Tính tổng tiền của đơn hàng
        double total = 0;
        for (CartItem item : cartItems) {
            // Cộng tổng tiền của từng sản phẩm
            total += item.getTotalPrice();
        }
        
        // Tạo OrderDAO để tạo đơn hàng
        OrderDAO orderDAO = new OrderDAO();
        
        // Tạo CarDAO để trừ số lượng sản phẩm
        CarDAO carDAO = new CarDAO();
        
        // Gọi hàm createOrder để tạo đơn hàng mới
        // Hàm này sẽ:
        // 1. INSERT vào bảng orders (tạo đơn hàng)
        // 2. INSERT vào bảng order_items (thêm chi tiết sản phẩm)
        if (orderDAO.createOrder(user.getUserId(), cartItems, total)) {
            // Nếu tạo đơn hàng thành công
            
            // Trừ số lượng sản phẩm trong kho
            for (CartItem item : cartItems) {
                // Giảm số lượng sản phẩm theo số lượng đã mua
                carDAO.reduceQuantity(item.getCarId(), item.getQuantity());
            }
            
            // Xóa giỏ hàng của user (xóa tất cả cart_items)
            cartDAO.clearCart(user.getUserId());
            
            // Redirect đến trang lịch sử đơn hàng với thông báo thành công
            response.sendRedirect(request.getContextPath() + "/orders?userId=" + user.getUserId() + "&success=true");
        } else {
            // Nếu tạo đơn hàng thất bại
            // Redirect lại trang giỏ hàng với thông báo lỗi
            response.sendRedirect(request.getContextPath() + "/cart?userId=" + user.getUserId() + "&error=true");
        }
    }
}
