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
        String path = req.getRequestURI();
        
        String[] publicPaths = {"/login", "/register", "/images/", "/css/", "/js/"};
        boolean isPublic = false;
        for (String publicPath : publicPaths) {
            if (path.contains(publicPath)) {
                isPublic = true;
                break;
            }
        }
        
        if (isPublic || path.endsWith("/")) {
            chain.doFilter(request, response);
            return;
        }
        
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
            
            req.setAttribute("currentUser", user);
            
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
