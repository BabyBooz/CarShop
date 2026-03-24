package com.mycompany.carshop.servlet.admin;

import com.mycompany.carshop.dao.OrderDAO;
import com.mycompany.carshop.model.Order;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

/**
 * AdminOrderServlet - Quản lý đơn hàng cho admin
 * 
 * Chức năng:
 * - GET: Hiển thị danh sách tất cả đơn hàng từ database
 * - POST: Cập nhật trạng thái đơn hàng (chờ xử lý, đang xử lý, hoàn thành)
 * 
 * Luồng:
 * 1. Admin truy cập /admin/orders (GET)
 * 2. Servlet lấy tất cả đơn hàng từ OrderDAO
 * 3. Gửi danh sách đơn hàng đến admin/orders.jsp để hiển thị
 * 4. Admin chọn trạng thái mới và submit form (POST)
 * 5. Servlet cập nhật trạng thái trong database
 * 6. Redirect về trang danh sách đơn hàng
 */
@WebServlet("/admin/orders")
public class AdminOrderServlet extends HttpServlet {
    
    /**
     * Xử lý GET request - Hiển thị danh sách đơn hàng
     * 
     * Bước:
     * 1. Tạo OrderDAO để truy cập database
     * 2. Lấy tất cả đơn hàng từ database (bao gồm chi tiết từng sản phẩm)
     * 3. Gửi danh sách đơn hàng vào request attribute
     * 4. Forward đến admin/orders.jsp để hiển thị
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Tạo DAO để truy cập bảng orders
        OrderDAO orderDAO = new OrderDAO();
        
        // Lấy tất cả đơn hàng từ database
        List<Order> orders = orderDAO.getAllOrders();
        
        // Gửi danh sách đơn hàng đến JSP
        request.setAttribute("orders", orders);
        
        // Chuyển hướng đến trang quản lý đơn hàng
        request.getRequestDispatcher("/WEB-INF/views/admin/orders.jsp").forward(request, response);
    }
    
    /**
     * Xử lý POST request - Cập nhật trạng thái đơn hàng
     * 
     * Bước:
     * 1. Lấy orderId từ form (ID của đơn hàng cần cập nhật)
     * 2. Lấy status mới từ form (wait, process, done)
     * 3. Lấy userId để redirect về trang admin
     * 4. Cập nhật trạng thái trong database
     * 5. Redirect về trang danh sách đơn hàng
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Lấy ID của đơn hàng cần cập nhật
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        
        // Lấy trạng thái mới: wait (chờ xử lý), process (đang xử lý), done (hoàn thành)
        String status = request.getParameter("status");
        
        // Lấy userId để duy trì trong URL khi redirect
        String userId = request.getParameter("userId");
        
        // Tạo DAO để cập nhật database
        OrderDAO orderDAO = new OrderDAO();
        
        // Cập nhật trạng thái đơn hàng trong database
        orderDAO.updateOrderStatus(orderId, status);
        
        // Redirect về trang danh sách đơn hàng (giữ userId trong URL)
        response.sendRedirect(request.getContextPath() + "/admin/orders?userId=" + userId);
    }
}
