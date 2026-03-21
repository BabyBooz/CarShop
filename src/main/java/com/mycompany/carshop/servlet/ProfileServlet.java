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
        response.setCharacterEncoding("UTF-8");
        
        User currentUser = (User) request.getAttribute("currentUser");
        
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String newPassword = request.getParameter("newPassword");
        
        currentUser.setFullName(fullName);
        currentUser.setEmail(email);
        currentUser.setPhone(phone);
        currentUser.setAddress(address);
        
        // Nếu nhập mật khẩu mới, cập nhật; nếu không thì giữ mật khẩu cũ
        if (newPassword != null && !newPassword.isEmpty()) {
            currentUser.setPassword(newPassword);
        }
        
        UserDAO userDAO = new UserDAO();
        boolean updated = userDAO.updateProfile(currentUser);
        
        if (updated) {
            request.setAttribute("success", "Cập nhật thông tin thành công");
            request.setAttribute("user", currentUser);
        } else {
            request.setAttribute("error", "Cập nhật thất bại");
            request.setAttribute("user", currentUser);
        }
        
        request.getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(request, response);
    }
}
