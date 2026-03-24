package com.mycompany.carshop.servlet;

import com.mycompany.carshop.dao.UserDAO;
import com.mycompany.carshop.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

/**
 * LoginServlet - Xử lý đăng nhập
 * URL: /login
 * 
 * Chức năng:
 * - GET: Hiển thị form đăng nhập
 * - POST: Xử lý đăng nhập (kiểm tra username/password)
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    
    /**
     * Xử lý GET request - Hiển thị trang đăng nhập
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Forward đến trang login.jsp để hiển thị form
        request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
    }
    
    /**
     * Xử lý POST request - Kiểm tra thông tin đăng nhập
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Lấy username từ form
        String username = request.getParameter("username");
        // Lấy password từ form
        String password = request.getParameter("password");
        
        // Tạo UserDAO để truy vấn database
        UserDAO userDAO = new UserDAO();
        // Gọi hàm login để kiểm tra username/password
        User user = userDAO.login(username, password);
        
        // Nếu tìm thấy user (đăng nhập thành công)
        if (user != null) {
            // Kiểm tra xem user có phải admin không
            if (user.isAdmin()) {
                // Nếu là admin, redirect đến trang quản lý xe
                response.sendRedirect(request.getContextPath() + "/admin/cars?userId=" + user.getUserId());
            } else {
                // Nếu là customer, redirect đến trang chủ
                response.sendRedirect(request.getContextPath() + "/home?userId=" + user.getUserId());
            }
        } else {
            // Nếu không tìm thấy user (đăng nhập thất bại)
            // Set thông báo lỗi
            request.setAttribute("error", "Tên đăng nhập hoặc mật khẩu không đúng");
            // Forward lại trang login để hiển thị lỗi
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
        }
    }
}
