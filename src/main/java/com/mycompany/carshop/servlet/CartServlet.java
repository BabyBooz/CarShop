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

/**
 * CartServlet - Quản lý giỏ hàng
 * URL: /cart
 * 
 * Chức năng:
 * - GET: Hiển thị giỏ hàng của user
 * - POST: Xử lý các hành động: thêm, cập nhật, xóa sản phẩm trong giỏ
 */
@WebServlet("/cart")
public class CartServlet extends HttpServlet {
    
    /**
     * Xử lý GET request - Hiển thị giỏ hàng
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Lấy user hiện tại từ request attribute (được set bởi AuthFilter)
        User user = (User) request.getAttribute("currentUser");
        
        // Tạo CartDAO để truy vấn database
        CartDAO cartDAO = new CartDAO();
        // Lấy danh sách sản phẩm trong giỏ hàng của user
        List<CartItem> cartItems = cartDAO.getCartItems(user.getUserId());
        
        // Tính tổng tiền trong giỏ hàng
        double total = 0;
        for (CartItem item : cartItems) {
            total += item.getTotalPrice();
        }
        
        // Set danh sách sản phẩm vào request
        request.setAttribute("cartItems", cartItems);
        // Set tổng tiền vào request
        request.setAttribute("total", total);
        
        // Forward đến trang cart.jsp để render HTML
        request.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(request, response);
    }
    
    /**
     * Xử lý POST request - Thêm/cập nhật/xóa sản phẩm trong giỏ
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Lấy user hiện tại từ request attribute
        User user = (User) request.getAttribute("currentUser");
        
        // Lấy action từ form (add, update, remove)
        String action = request.getParameter("action");
        
        // Tạo CartDAO để thao tác với giỏ hàng
        CartDAO cartDAO = new CartDAO();
        
        // Kiểm tra action để thực hiện hành động tương ứng
        if ("add".equals(action)) {
            // Hành động: Thêm sản phẩm vào giỏ
            // Lấy ID sản phẩm từ form
            int carId = Integer.parseInt(request.getParameter("carId"));
            // Lấy số lượng từ form
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            
            // Tạo CarDAO để lấy thông tin sản phẩm
            CarDAO carDAO = new CarDAO();
            // Lấy thông tin sản phẩm từ database
            Car car = carDAO.getCarById(carId);
            
            // Tính tổng tiền = giá × số lượng
            double totalPrice = car.getPrice() * quantity;
            
            // Thêm sản phẩm vào giỏ hàng
            cartDAO.addToCart(user.getUserId(), carId, quantity, totalPrice);
            
        } else if ("update".equals(action)) {
            // Hành động: Cập nhật số lượng sản phẩm
            // Lấy ID item trong giỏ từ form
            int cartItemId = Integer.parseInt(request.getParameter("cartItemId"));
            // Lấy số lượng mới từ form
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            // Lấy giá sản phẩm từ form
            double price = Double.parseDouble(request.getParameter("price"));
            
            // Tính tổng tiền mới = giá × số lượng mới
            double totalPrice = price * quantity;
            
            // Cập nhật item trong giỏ hàng
            cartDAO.updateCartItem(cartItemId, quantity, totalPrice);
            
        } else if ("remove".equals(action)) {
            // Hành động: Xóa sản phẩm khỏi giỏ
            // Lấy ID item trong giỏ từ form
            int cartItemId = Integer.parseInt(request.getParameter("cartItemId"));
            
            // Xóa item khỏi giỏ hàng
            cartDAO.removeCartItem(cartItemId);
        }
        
        // Sau khi xử lý xong, redirect lại trang giỏ hàng để hiển thị dữ liệu mới
        response.sendRedirect(request.getContextPath() + "/cart?userId=" + user.getUserId());
    }
}
