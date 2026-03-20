package com.mycompany.carshop.servlet.admin;

import com.mycompany.carshop.dao.OrderDAO;
import com.mycompany.carshop.model.Order;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/orders")
public class AdminOrderServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        OrderDAO orderDAO = new OrderDAO();
        List<Order> orders = orderDAO.getAllOrders();
        request.setAttribute("orders", orders);
        request.getRequestDispatcher("/WEB-INF/views/admin/orders.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        String status = request.getParameter("status");
        String userId = request.getParameter("userId");
        
        OrderDAO orderDAO = new OrderDAO();
        orderDAO.updateOrderStatus(orderId, status);
        
        response.sendRedirect(request.getContextPath() + "/admin/orders?userId=" + userId);
    }
}
