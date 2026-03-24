package com.mycompany.carshop.servlet;

import com.mycompany.carshop.dao.CarDAO;
import com.mycompany.carshop.model.Car;
import com.mycompany.carshop.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

/**
 * HomeServlet - Trang chủ hiển thị danh sách xe
 * URL: /home
 * 
 * Chức năng:
 * - GET: Lấy danh sách xe từ database và hiển thị
 */
@WebServlet("/home")
public class HomeServlet extends HttpServlet {
    
    /**
     * Xử lý GET request - Hiển thị trang chủ với danh sách xe
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Lấy user hiện tại từ request attribute (được set bởi AuthFilter)
        User user = (User) request.getAttribute("currentUser");
        
        // Tạo CarDAO để truy vấn database
        CarDAO carDAO = new CarDAO();
        // Lấy danh sách tất cả xe có status = 1 (available)
        List<Car> cars = carDAO.getAllCars();
        
        // Set danh sách xe vào request để JSP có thể sử dụng
        request.setAttribute("cars", cars);
        // Set user vào request để JSP có thể hiển thị thông tin user
        request.setAttribute("user", user);
        
        // Forward đến trang home.jsp để render HTML
        request.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
    }
}
