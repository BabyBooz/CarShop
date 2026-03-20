package com.mycompany.carshop.servlet.admin;

import com.mycompany.carshop.dao.CarDAO;
import com.mycompany.carshop.dao.CategoryDAO;
import com.mycompany.carshop.model.Car;
import com.mycompany.carshop.model.Category;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@WebServlet("/admin/cars")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,  // 2MB
    maxFileSize = 1024 * 1024 * 10,       // 10MB
    maxRequestSize = 1024 * 1024 * 50     // 50MB
)
public class AdminCarServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");
        CarDAO carDAO = new CarDAO();
        CategoryDAO categoryDAO = new CategoryDAO();
        
        if ("edit".equals(action)) {
            int carId = Integer.parseInt(request.getParameter("id"));
            Car car = carDAO.getCarById(carId);
            List<Category> categories = categoryDAO.getAllCategories();
            request.setAttribute("car", car);
            request.setAttribute("categories", categories);
            request.getRequestDispatcher("/WEB-INF/views/admin/car-form.jsp").forward(request, response);
        } else if ("add".equals(action)) {
            List<Category> categories = categoryDAO.getAllCategories();
            request.setAttribute("categories", categories);
            request.getRequestDispatcher("/WEB-INF/views/admin/car-form.jsp").forward(request, response);
        } else {
            List<Car> cars = carDAO.getAllCarsForAdmin();
            request.setAttribute("cars", cars);
            request.getRequestDispatcher("/WEB-INF/views/admin/cars.jsp").forward(request, response);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        CarDAO carDAO = new CarDAO();
        String userId = request.getParameter("userId");
        
        if ("delete".equals(action)) {
            int carId = Integer.parseInt(request.getParameter("id"));
            carDAO.deleteCar(carId);
            response.sendRedirect(request.getContextPath() + "/admin/cars?userId=" + userId);
            return;
        }
        
        Car car = new Car();
        String carIdStr = request.getParameter("carId");
        if (carIdStr != null && !carIdStr.isEmpty()) {
            car.setCarId(Integer.parseInt(carIdStr));
        }
        
        car.setTitle(request.getParameter("title"));
        car.setPrice(Double.parseDouble(request.getParameter("price")));
        car.setDescription(request.getParameter("description"));
        car.setCategoryId(Integer.parseInt(request.getParameter("categoryId")));
        car.setStatus("1".equals(request.getParameter("status")));
        
        // Xử lý upload ảnh
        String imageUrl = uploadImage(request);
        
        // Nếu không upload ảnh mới, giữ ảnh cũ
        if (imageUrl == null || imageUrl.isEmpty()) {
            imageUrl = request.getParameter("currentImage");
        }
        
        car.setImageUrl(imageUrl);
        
        if (car.getCarId() > 0) {
            carDAO.updateCar(car);
        } else {
            carDAO.addCar(car);
        }
        
        response.sendRedirect(request.getContextPath() + "/admin/cars?userId=" + userId);
    }
    
    private String uploadImage(HttpServletRequest request) throws IOException, ServletException {
        Part filePart = request.getPart("image");
        
        if (filePart == null || filePart.getSize() == 0) {
            return null;
        }
        
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        
        // Kiểm tra định dạng file
        String fileExtension = "";
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0) {
            fileExtension = fileName.substring(dotIndex).toLowerCase();
        }
        
        if (!fileExtension.matches("\\.(jpg|jpeg|png|gif|webp)")) {
            return null;
        }
        
        // Tạo tên file unique
        String uniqueFileName = UUID.randomUUID().toString() + fileExtension;
        
        // Lấy đường dẫn thực tế của project
        String realPath = getServletContext().getRealPath("/images");
        
        // Tạo đường dẫn đến thư mục source (src/main/webapp/images)
        String projectPath = realPath;
        if (realPath.contains("target")) {
            projectPath = realPath.replace(
                "target" + File.separator + "CarShop-1", 
                "src" + File.separator + "main" + File.separator + "webapp"
            );
        }
        
        // Tạo thư mục nếu chưa tồn tại
        File sourceDir = new File(projectPath);
        if (!sourceDir.exists()) {
            sourceDir.mkdirs();
        }
        
        File targetDir = new File(realPath);
        if (!targetDir.exists()) {
            targetDir.mkdirs();
        }
        
        // Lưu file vào thư mục source
        String sourceFilePath = projectPath + File.separator + uniqueFileName;
        try (InputStream input = filePart.getInputStream()) {
            Files.copy(input, Paths.get(sourceFilePath), StandardCopyOption.REPLACE_EXISTING);
        }
        
        // Copy sang thư mục target để hiển thị ngay
        String targetFilePath = realPath + File.separator + uniqueFileName;
        Files.copy(Paths.get(sourceFilePath), Paths.get(targetFilePath), StandardCopyOption.REPLACE_EXISTING);
        
        System.out.println("Upload thành công:");
        System.out.println("- Source: " + sourceFilePath);
        System.out.println("- Target: " + targetFilePath);
        
        // Trả về tên file (không có đường dẫn)
        return uniqueFileName;
    }
}
