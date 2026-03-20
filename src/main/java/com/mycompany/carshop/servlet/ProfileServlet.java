package com.mycompany.carshop.servlet;

import com.mycompany.carshop.dao.UserDAO;
import com.mycompany.carshop.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        User user = (User) request.getAttribute("currentUser");
        request.setAttribute("user", user);
        request.getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        User currentUser = (User) request.getAttribute("currentUser");
        
        currentUser.setFullName(request.getParameter("fullName"));
        currentUser.setEmail(request.getParameter("email"));
        currentUser.setPhone(request.getParameter("phone"));
        currentUser.setAddress(request.getParameter("address"));
        
        UserDAO userDAO = new UserDAO();
        if (userDAO.updateProfile(currentUser)) {
            request.setAttribute("success", "Cập nhật thông tin thành công");
        } else {
            request.setAttribute("error", "Cập nhật thất bại");
        }
        request.setAttribute("user", currentUser);
        request.getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(request, response);
    }
}
