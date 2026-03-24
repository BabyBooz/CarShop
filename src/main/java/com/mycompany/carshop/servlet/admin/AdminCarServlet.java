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

/**
 * AdminCarServlet - Quản lý sản phẩm xe cho admin
 * 
 * Chức năng:
 * - GET: Hiển thị danh sách xe, form thêm xe, form sửa xe
 * - POST: Thêm xe mới, sửa xe, xóa xe (bao gồm upload ảnh)
 * 
 * Luồng:
 * 1. Admin truy cập /admin/cars (GET) - xem danh sách
 * 2. Admin click "Thêm xe" - hiển thị form trống
 * 3. Admin điền thông tin, upload ảnh, submit (POST)
 * 4. Servlet xử lý upload ảnh, lưu vào database
 * 5. Admin click "Sửa" - hiển thị form với dữ liệu cũ
 * 6. Admin thay đổi, submit (POST) - cập nhật database
 * 7. Admin click "Xóa" - xóa khỏi database
 * 
 * Xử lý ảnh:
 * - Lưu vào 2 nơi: src/main/webapp/images (source) và target/CarShop-1/images (runtime)
 * - Tạo tên file unique bằng UUID để tránh trùng lặp
 * - Kiểm tra định dạng file (jpg, jpeg, png, gif, webp)
 */
@WebServlet("/admin/cars")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,  // 2MB - ngưỡng lưu vào bộ nhớ
    maxFileSize = 1024 * 1024 * 10,       // 10MB - kích thước file tối đa
    maxRequestSize = 1024 * 1024 * 50     // 50MB - kích thước request tối đa
)
public class AdminCarServlet extends HttpServlet {
    
    /**
     * Xử lý GET request - Hiển thị danh sách xe hoặc form thêm/sửa
     * 
     * Bước:
     * 1. Kiểm tra action parameter:
     *    - "edit": Lấy thông tin xe cũ, hiển thị form sửa
     *    - "add": Hiển thị form trống để thêm xe mới
     *    - null: Hiển thị danh sách tất cả xe
     * 2. Lấy danh sách danh mục từ database
     * 3. Gửi dữ liệu đến JSP tương ứng
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Lấy action từ URL parameter (edit, add, hoặc null)
        String action = request.getParameter("action");
        
        // Tạo DAO để truy cập database
        CarDAO carDAO = new CarDAO();
        CategoryDAO categoryDAO = new CategoryDAO();
        
        // Nếu action = "edit" - hiển thị form sửa xe
        if ("edit".equals(action)) {
            // Lấy ID xe cần sửa
            int carId = Integer.parseInt(request.getParameter("id"));
            
            // Lấy thông tin xe từ database
            Car car = carDAO.getCarById(carId);
            
            // Lấy danh sách danh mục để dropdown
            List<Category> categories = categoryDAO.getAllCategories();
            
            // Gửi dữ liệu đến form
            request.setAttribute("car", car);
            request.setAttribute("categories", categories);
            
            // Hiển thị form sửa
            request.getRequestDispatcher("/WEB-INF/views/admin/car-form.jsp").forward(request, response);
        } 
        // Nếu action = "add" - hiển thị form thêm xe mới
        else if ("add".equals(action)) {
            // Lấy danh sách danh mục để dropdown
            List<Category> categories = categoryDAO.getAllCategories();
            
            // Gửi danh sách danh mục đến form
            request.setAttribute("categories", categories);
            
            // Hiển thị form thêm (car = null, form sẽ trống)
            request.getRequestDispatcher("/WEB-INF/views/admin/car-form.jsp").forward(request, response);
        } 
        // Nếu không có action - hiển thị danh sách xe
        else {
            // Lấy tất cả xe từ database (bao gồm tên danh mục)
            List<Car> cars = carDAO.getAllCarsForAdmin();
            
            // Gửi danh sách xe đến JSP
            request.setAttribute("cars", cars);
            
            // Hiển thị danh sách xe
            request.getRequestDispatcher("/WEB-INF/views/admin/cars.jsp").forward(request, response);
        }
    }
    
    /**
     * Xử lý POST request - Thêm/sửa/xóa xe
     * 
     * Bước:
     * 1. Kiểm tra action:
     *    - "delete": Xóa xe khỏi database
     *    - null: Thêm xe mới hoặc sửa xe cũ
     * 2. Lấy thông tin xe từ form
     * 3. Xử lý upload ảnh (nếu có)
     * 4. Lưu vào database (insert hoặc update)
     * 5. Redirect về danh sách xe
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Đặt encoding UTF-8 để xử lý tiếng Việt
        request.setCharacterEncoding("UTF-8");
        
        // Lấy action từ form (delete, hoặc null)
        String action = request.getParameter("action");
        
        // Tạo DAO để truy cập database
        CarDAO carDAO = new CarDAO();
        
        // Lấy userId để redirect về trang admin
        String userId = request.getParameter("userId");
        
        // Nếu action = "delete" - xóa xe
        if ("delete".equals(action)) {
            // Lấy ID xe cần xóa
            int carId = Integer.parseInt(request.getParameter("id"));
            
            // Xóa xe khỏi database
            carDAO.deleteCar(carId);
            
            // Redirect về danh sách xe
            response.sendRedirect(request.getContextPath() + "/admin/cars?userId=" + userId);
            return;
        }
        
        // Tạo object Car để lưu thông tin
        Car car = new Car();
        
        // Lấy carId từ form (nếu là sửa, sẽ có giá trị; nếu là thêm, sẽ trống)
        String carIdStr = request.getParameter("carId");
        if (carIdStr != null && !carIdStr.isEmpty()) {
            car.setCarId(Integer.parseInt(carIdStr));
        }
        
        // Lấy thông tin xe từ form
        car.setTitle(request.getParameter("title"));
        car.setPrice(Double.parseDouble(request.getParameter("price")));
        car.setDescription(request.getParameter("description"));
        car.setCategoryId(Integer.parseInt(request.getParameter("categoryId")));
        car.setStatus("1".equals(request.getParameter("status")));
        car.setQuantity(Integer.parseInt(request.getParameter("quantity")));
        
        // Xử lý upload ảnh (nếu có file được chọn)
        String imageUrl = uploadImage(request);
        
        // Nếu không upload ảnh mới, giữ ảnh cũ (từ form hidden input)
        if (imageUrl == null || imageUrl.isEmpty()) {
            imageUrl = request.getParameter("currentImage");
        }
        
        // Gán URL ảnh vào object Car
        car.setImageUrl(imageUrl);
        
        // Nếu carId > 0 là sửa, ngược lại là thêm mới
        if (car.getCarId() > 0) {
            // Cập nhật xe cũ
            carDAO.updateCar(car);
        } else {
            // Thêm xe mới
            carDAO.addCar(car);
        }
        
        // Redirect về danh sách xe (giữ userId trong URL)
        response.sendRedirect(request.getContextPath() + "/admin/cars?userId=" + userId);
    }
    
    /**
     * Xử lý upload ảnh
     * 
     * Bước:
     * 1. Lấy file từ request (multipart/form-data)
     * 2. Kiểm tra file có tồn tại và định dạng hợp lệ
     * 3. Tạo tên file unique bằng UUID
     * 4. Lưu vào 2 nơi:
     *    - src/main/webapp/images (source code)
     *    - target/CarShop-1/images (runtime)
     * 5. Trả về tên file
     * 
     * @param request HttpServletRequest chứa file upload
     * @return Tên file (UUID + extension), hoặc null nếu không upload
     */
    private String uploadImage(HttpServletRequest request) throws IOException, ServletException {
        // Lấy file từ request (name="image" trong form)
        Part filePart = request.getPart("image");
        
        // Nếu không có file hoặc file trống, trả về null
        if (filePart == null || filePart.getSize() == 0) {
            return null;
        }
        
        // Lấy tên file gốc từ client
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        
        // Kiểm tra định dạng file (lấy extension)
        String fileExtension = "";
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0) {
            fileExtension = fileName.substring(dotIndex).toLowerCase();
        }
        
        // Chỉ cho phép các định dạng ảnh nhất định
        if (!fileExtension.matches("\\.(jpg|jpeg|png|gif|webp)")) {
            return null;
        }
        
        // Tạo tên file unique bằng UUID để tránh trùng lặp
        // Ví dụ: 550e8400-e29b-41d4-a716-446655440000.jpg
        String uniqueFileName = UUID.randomUUID().toString() + fileExtension;
        
        // Lấy đường dẫn thực tế của thư mục /images trong runtime
        String realPath = getServletContext().getRealPath("/images");
        
        // Tạo đường dẫn đến thư mục source (src/main/webapp/images)
        // Nếu đang chạy từ target, convert đường dẫn về source
        String projectPath = realPath;
        if (realPath.contains("target")) {
            projectPath = realPath.replace(
                "target" + File.separator + "CarShop-1", 
                "src" + File.separator + "main" + File.separator + "webapp"
            );
        }
        
        // Tạo thư mục source nếu chưa tồn tại
        File sourceDir = new File(projectPath);
        if (!sourceDir.exists()) {
            sourceDir.mkdirs();
        }
        
        // Tạo thư mục target nếu chưa tồn tại
        File targetDir = new File(realPath);
        if (!targetDir.exists()) {
            targetDir.mkdirs();
        }
        
        // Lưu file vào thư mục source (src/main/webapp/images)
        String sourceFilePath = projectPath + File.separator + uniqueFileName;
        try (InputStream input = filePart.getInputStream()) {
            Files.copy(input, Paths.get(sourceFilePath), StandardCopyOption.REPLACE_EXISTING);
        }
        
        // Copy file sang thư mục target (target/CarShop-1/images) để hiển thị ngay
        String targetFilePath = realPath + File.separator + uniqueFileName;
        Files.copy(Paths.get(sourceFilePath), Paths.get(targetFilePath), StandardCopyOption.REPLACE_EXISTING);
        
        // In log để debug
        System.out.println("Upload thành công:");
        System.out.println("- Source: " + sourceFilePath);
        System.out.println("- Target: " + targetFilePath);
        
        // Trả về tên file (không có đường dẫn, chỉ tên file)
        return uniqueFileName;
    }
}
