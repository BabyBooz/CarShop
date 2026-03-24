package com.mycompany.carshop.servlet;

import com.mycompany.carshop.dao.OrderDAO;
import com.mycompany.carshop.model.Order;
import com.mycompany.carshop.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

/**
 * OrderServlet - Hiển thị lịch sử đơn hàng của user
 * URL: /orders
 * 
 * Chức năng:
 * - GET: Lấy danh sách đơn hàng của user từ database và hiển thị
 */
@WebServlet("/orders")
public class OrderServlet extends HttpServlet {
    
    /**
     * Xử lý GET request - Hiển thị lịch sử đơn hàng
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Lấy user hiện tại từ request attribute (được set bởi AuthFilter)
        User user = (User) request.getAttribute("currentUser");
        
        // Tạo OrderDAO để truy vấn database
        OrderDAO orderDAO = new OrderDAO();
        // Lấy danh sách tất cả đơn hàng của user
        // Mỗi order sẽ có danh sách order_items (chi tiết sản phẩm)
        List<Order> orders = orderDAO.getOrdersByUserId(user.getUserId());
        
        // Set danh sách đơn hàng vào request để JSP có thể sử dụng
        request.setAttribute("orders", orders);
        
        // Forward đến trang orders.jsp để render HTML
        request.getRequestDispatcher("/WEB-INF/views/orders.jsp").forward(request, response);
    }
}
