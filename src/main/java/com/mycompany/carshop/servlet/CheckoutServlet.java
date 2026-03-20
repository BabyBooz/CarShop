package com.mycompany.carshop.servlet;

import com.mycompany.carshop.dao.CartDAO;
import com.mycompany.carshop.dao.OrderDAO;
import com.mycompany.carshop.model.CartItem;
import com.mycompany.carshop.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        User user = (User) request.getAttribute("currentUser");
        CartDAO cartDAO = new CartDAO();
        List<CartItem> cartItems = cartDAO.getCartItems(user.getUserId());
        
        if (cartItems.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/cart?userId=" + user.getUserId());
            return;
        }
        
        double total = 0;
        for (CartItem item : cartItems) {
            total += item.getTotalPrice();
        }
        
        OrderDAO orderDAO = new OrderDAO();
        if (orderDAO.createOrder(user.getUserId(), cartItems, total)) {
            cartDAO.clearCart(user.getUserId());
            response.sendRedirect(request.getContextPath() + "/orders?userId=" + user.getUserId() + "&success=true");
        } else {
            response.sendRedirect(request.getContextPath() + "/cart?userId=" + user.getUserId() + "&error=true");
        }
    }
}
