package com.mycompany.carshop.servlet;

import com.mycompany.carshop.dao.UserDAO;
import com.mycompany.carshop.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

/**
 * ProfileServlet - Quản lý thông tin cá nhân của user
 * URL: /profile
 * 
 * Chức năng:
 * - GET: Hiển thị form cập nhật thông tin cá nhân
 * - POST: Xử lý cập nhật thông tin cá nhân (tên, email, phone, address, password)
 */
@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
    
    /**
     * Xử lý GET request - Hiển thị trang cập nhật thông tin
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Lấy user hiện tại từ request attribute (được set bởi AuthFilter)
        User user = (User) request.getAttribute("currentUser");
        
        // Set user vào request để JSP có thể hiển thị thông tin hiện tại
        request.setAttribute("user", user);
        
        // Forward đến trang profile.jsp để render form
        request.getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(request, response);
    }
    
    /**
     * Xử lý POST request - Cập nhật thông tin cá nhân
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Set encoding UTF-8 để hỗ trợ tiếng Việt
        request.setCharacterEncoding("UTF-8");
        // Set response encoding UTF-8
        response.setCharacterEncoding("UTF-8");
        
        // Lấy user hiện tại từ request attribute
        User currentUser = (User) request.getAttribute("currentUser");
        
        // Lấy dữ liệu từ form
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String newPassword = request.getParameter("newPassword");
        
        // Cập nhật thông tin user object
        currentUser.setFullName(fullName);
        currentUser.setEmail(email);
        currentUser.setPhone(phone);
        currentUser.setAddress(address);
        
        // Nếu user nhập mật khẩu mới, cập nhật; nếu không thì giữ mật khẩu cũ
        if (newPassword != null && !newPassword.isEmpty()) {
            currentUser.setPassword(newPassword);
        }
        
        // Tạo UserDAO để cập nhật database
        UserDAO userDAO = new UserDAO();
        // Gọi hàm updateProfile để cập nhật thông tin user
        boolean updated = userDAO.updateProfile(currentUser);
        
        // Kiểm tra kết quả cập nhật
        if (updated) {
            // Nếu cập nhật thành công
            request.setAttribute("success", "Cập nhật thông tin thành công");
            request.setAttribute("user", currentUser);
        } else {
            // Nếu cập nhật thất bại
            request.setAttribute("error", "Cập nhật thất bại");
            request.setAttribute("user", currentUser);
        }
        
        // Forward lại trang profile.jsp để hiển thị thông báo
        request.getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(request, response);
    }
}
