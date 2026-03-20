package com.mycompany.carshop.servlet;

import com.mycompany.carshop.dao.CarDAO;
import com.mycompany.carshop.model.Car;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        CarDAO carDAO = new CarDAO();
        List<Car> cars = carDAO.getAllCars();
        request.setAttribute("cars", cars);
        request.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
    }
}
