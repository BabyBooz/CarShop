package com.mycompany.carshop.servlet;

import com.mycompany.carshop.dao.UserDAO;
import com.mycompany.carshop.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

/**
 * RegisterServlet - Xử lý đăng ký tài khoản
 * URL: /register
 * 
 * Chức năng:
 * - GET: Hiển thị form đăng ký
 * - POST: Xử lý đăng ký (tạo tài khoản mới)
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    
    /**
     * Xử lý GET request - Hiển thị trang đăng ký
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Forward đến trang register.jsp để hiển thị form
        request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
    }
    
    /**
     * Xử lý POST request - Tạo tài khoản mới
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Set encoding UTF-8 để hỗ trợ tiếng Việt
        request.setCharacterEncoding("UTF-8");
        
        // Tạo object User mới
        User user = new User();
        // Lấy dữ liệu từ form và set vào user object
        user.setFullName(request.getParameter("fullName"));
        user.setUsername(request.getParameter("username"));
        user.setPassword(request.getParameter("password"));
        user.setEmail(request.getParameter("email"));
        user.setPhone(request.getParameter("phone"));
        user.setAddress(request.getParameter("address"));
        
        // Tạo UserDAO để lưu user vào database
        UserDAO userDAO = new UserDAO();
        // Gọi hàm register để thêm user mới
        if (userDAO.register(user)) {
            // Nếu đăng ký thành công
            request.setAttribute("success", "Đăng ký thành công! Vui lòng đăng nhập.");
            // Forward lại trang register để hiển thị thông báo thành công
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
        } else {
            // Nếu đăng ký thất bại (có thể username đã tồn tại)
            request.setAttribute("error", "Đăng ký thất bại. Tên đăng nhập có thể đã tồn tại.");
            // Forward lại trang register để hiển thị lỗi
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
        }
    }
}
