package com.mycompany.carshop.filter;

import com.mycompany.carshop.dao.UserDAO;
import com.mycompany.carshop.model.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String path = req.getRequestURI(); // lấy ra đường dẫn local../.../
        
        String[] publicPaths = {"/login", "/register", "/images/", "/css/", "/js/"};
        boolean isPublic = false;
        for (String publicPath : publicPaths) {
            if (path.contains(publicPath)) {
                isPublic = true;
                break;
            }
        }
        
        if (isPublic || path.endsWith("/")) {
            chain.doFilter(request, response); // chuyển request đến servlet tiếp theo ở public path
            return;
        }
        
        // lấu userId từ path để phân quyền
        // ví dụ http://localhost:9999/CarShop/profile?userId=2
        String userId = req.getParameter("userId");
        if (userId == null || userId.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        
        try {
            int uid = Integer.parseInt(userId);
            UserDAO userDAO = new UserDAO();
            User user = userDAO.getUserById(uid);
            
        
            if (user == null) {
                resp.sendRedirect(req.getContextPath() + "/login");
                return;
            }
            
            // lưu user vào req để lấy ra trong servlet
            req.setAttribute("currentUser", user);
            
            // nếu url có admin nhưng user đó kp là admin thì về home

            //http://localhost:9999/CarShop/admin/cars?userId=1 tk là user 
            if (path.contains("/admin/") && !user.isAdmin()) {
                resp.sendRedirect(req.getContextPath() + "/home?userId=" + userId);
                return;
            }
            
            chain.doFilter(request, response);
        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/login");
        }
    }
}
