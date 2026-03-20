package com.mycompany.carshop.servlet;

import com.mycompany.carshop.dao.CartDAO;
import com.mycompany.carshop.dao.CarDAO;
import com.mycompany.carshop.model.CartItem;
import com.mycompany.carshop.model.Car;
import com.mycompany.carshop.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        User user = (User) request.getAttribute("currentUser");
        CartDAO cartDAO = new CartDAO();
        List<CartItem> cartItems = cartDAO.getCartItems(user.getUserId());
        
        double total = 0;
        for (CartItem item : cartItems) {
            total += item.getTotalPrice();
        }
        
        request.setAttribute("cartItems", cartItems);
        request.setAttribute("total", total);
        request.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        User user = (User) request.getAttribute("currentUser");
        String action = request.getParameter("action");
        CartDAO cartDAO = new CartDAO();
        
        if ("add".equals(action)) {
            int carId = Integer.parseInt(request.getParameter("carId"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            CarDAO carDAO = new CarDAO();
            Car car = carDAO.getCarById(carId);
            double totalPrice = car.getPrice() * quantity;
            cartDAO.addToCart(user.getUserId(), carId, quantity, totalPrice);
        } else if ("update".equals(action)) {
            int cartItemId = Integer.parseInt(request.getParameter("cartItemId"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            double price = Double.parseDouble(request.getParameter("price"));
            double totalPrice = price * quantity;
            cartDAO.updateCartItem(cartItemId, quantity, totalPrice);
        } else if ("remove".equals(action)) {
            int cartItemId = Integer.parseInt(request.getParameter("cartItemId"));
            cartDAO.removeCartItem(cartItemId);
        }
        
        response.sendRedirect(request.getContextPath() + "/cart?userId=" + user.getUserId());
    }
}
