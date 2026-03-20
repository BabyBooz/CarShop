package com.mycompany.carshop.servlet;

import com.mycompany.carshop.dao.OrderDAO;
import com.mycompany.carshop.model.Order;
import com.mycompany.carshop.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/orders")
public class OrderServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        User user = (User) request.getAttribute("currentUser");
        OrderDAO orderDAO = new OrderDAO();
        List<Order> orders = orderDAO.getOrdersByUserId(user.getUserId());
        request.setAttribute("orders", orders);
        request.getRequestDispatcher("/WEB-INF/views/orders.jsp").forward(request, response);
    }
}
