<%-- 
    profile.jsp - Trang cập nhật thông tin cá nhân của người dùng
    
    Chức năng:
    - Hiển thị form cập nhật thông tin cá nhân (họ tên, email, điện thoại, địa chỉ)
    - Cho phép cập nhật mật khẩu mới (tùy chọn)
    - Hiển thị thông báo thành công/lỗi
    
    Luồng:
    1. Người dùng truy cập /profile?userId=X
    2. ProfileServlet lấy thông tin từ database, gửi vào request attribute "user"
    3. JSP hiển thị form với dữ liệu hiện tại
    4. Người dùng thay đổi thông tin, submit form (POST)
    5. ProfileServlet cập nhật database, gửi lại thông báo
    6. JSP hiển thị thông báo thành công/lỗi
    
    Dữ liệu từ request:
    - ${user}: Object User chứa thông tin người dùng
    - ${param.userId}: ID người dùng từ URL
    - request.getAttribute("success"): Thông báo thành công
    - request.getAttribute("error"): Thông báo lỗi
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Thông tin cá nhân</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: Arial, sans-serif; background: #f5f5f5; }
        .header { background: #333; color: white; padding: 15px 0; }
        .header-content { max-width: 1200px; margin: 0 auto; display: flex; justify-content: space-between; align-items: center; padding: 0 20px; }
        .header h1 { font-size: 24px; }
        .nav a { color: white; text-decoration: none; margin-left: 20px; }
        .nav a:hover { text-decoration: underline; }
        .container { max-width: 600px; margin: 30px auto; background: white; padding: 30px; border: 1px solid #ddd; border-radius: 4px; }
        .form-group { margin-bottom: 15px; }
        label { display: block; margin-bottom: 5px; color: #555; font-weight: bold; }
        input { width: 100%; padding: 10px; border: 1px solid #ddd; border-radius: 4px; }
        button { padding: 12px 30px; background: #007bff; color: white; border: none; border-radius: 4px; cursor: pointer; }
        button:hover { background: #0056b3; }
        .success { color: green; margin-bottom: 10px; }
        .error { color: red; margin-bottom: 10px; }
    </style>
</head>
<body>
    <!-- Header - Thanh điều hướng chính -->
    <div class="header">
        <div class="header-content">
            <!-- Logo và tên ứng dụng -->
            <h1><i class="fas fa-car"></i> Car Shop</h1>
            
            <!-- Menu điều hướng -->
            <div class="nav">
                <!-- Link trang chủ (truyền userId để AuthFilter kiểm tra quyền) -->
                <a href="home?userId=${param.userId}"><i class="fas fa-home"></i> Trang chủ</a>
                
                <!-- Link giỏ hàng -->
                <a href="cart?userId=${param.userId}"><i class="fas fa-shopping-cart"></i> Giỏ hàng</a>
                
                <!-- Link lịch sử mua hàng -->
                <a href="orders?userId=${param.userId}"><i class="fas fa-history"></i> Lịch sử mua</a>
                
                <!-- Link thông tin cá nhân (trang hiện tại) -->
                <a href="profile?userId=${param.userId}"><i class="fas fa-user"></i> Thông tin</a>
                
                <!-- Link đăng xuất (không cần userId vì là public path) -->
                <a href="login"><i class="fas fa-sign-out-alt"></i> Đăng xuất</a>
            </div>
        </div>
    </div>
    
    <!-- Container chính - Form cập nhật thông tin -->
    <div class="container">
        <!-- Tiêu đề trang -->
        <h2 style="margin-bottom: 20px;"><i class="fas fa-user-edit"></i> Cập nhật thông tin cá nhân</h2>
        
        <!-- Hiển thị thông báo thành công (nếu có) -->
        <% if (request.getAttribute("success") != null) { %>
            <div class="success"><%= request.getAttribute("success") %></div>
        <% } %>
        
        <!-- Hiển thị thông báo lỗi (nếu có) -->
        <% if (request.getAttribute("error") != null) { %>
            <div class="error"><%= request.getAttribute("error") %></div>
        <% } %>
        
        <!-- Form POST để cập nhật thông tin -->
        <form method="post" accept-charset="UTF-8">
            <!-- Tên đăng nhập (không thể sửa, chỉ hiển thị) -->
            <div class="form-group">
                <label>Tên đăng nhập</label>
                <input type="text" value="${user.username}" disabled style="background: #f5f5f5;">
            </div>
            
            <!-- Họ và tên (có thể sửa) -->
            <div class="form-group">
                <label>Họ và tên</label>
                <input type="text" name="fullName" value="${user.fullName}" required>
            </div>
            
            <!-- Email (có thể sửa) -->
            <div class="form-group">
                <label>Email</label>
                <input type="email" name="email" value="${user.email}" required>
            </div>
            
            <!-- Số điện thoại (có thể sửa) -->
            <div class="form-group">
                <label>Số điện thoại</label>
                <input type="text" name="phone" value="${user.phone}" required>
            </div>
            
            <!-- Địa chỉ (có thể sửa) -->
            <div class="form-group">
                <label>Địa chỉ</label>
                <input type="text" name="address" value="${user.address}" required>
            </div>
            
            <!-- Mật khẩu mới (tùy chọn - chỉ cập nhật nếu người dùng nhập) -->
            <div class="form-group">
                <label>Mật khẩu mới</label>
                <input type="password" name="newPassword">
            </div>
            
            <!-- Nút submit để gửi form -->
            <button type="submit"><i class="fas fa-save"></i> Cập nhật</button>
        </form>
    </div>
</body>
</html>
