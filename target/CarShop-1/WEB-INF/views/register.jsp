<%-- 
    register.jsp - Trang đăng ký tài khoản mới
    
    Chức năng:
    - Hiển thị form đăng ký (username, password, họ tên, email, điện thoại, địa chỉ)
    - Hiển thị thông báo thành công/lỗi
    - Link đến trang đăng nhập cho người dùng đã có tài khoản
    
    Luồng:
    1. Người dùng truy cập /register (GET)
    2. RegisterServlet hiển thị form trống
    3. Người dùng nhập thông tin, submit (POST)
    4. RegisterServlet kiểm tra username có tồn tại không
    5. Nếu không tồn tại, tạo tài khoản mới, hiển thị thông báo thành công
    6. Nếu tồn tại, hiển thị thông báo lỗi
    
    Dữ liệu từ request:
    - request.getAttribute("success"): Thông báo đăng ký thành công
    - request.getAttribute("error"): Thông báo lỗi đăng ký
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Đăng ký</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: Arial, sans-serif; background: #f5f5f5; }
        .container { max-width: 500px; margin: 50px auto; background: white; padding: 30px; border: 1px solid #ddd; border-radius: 4px; }
        h2 { text-align: center; margin-bottom: 20px; color: #333; }
        .form-group { margin-bottom: 15px; }
        label { display: block; margin-bottom: 5px; color: #555; }
        input { width: 100%; padding: 10px; border: 1px solid #ddd; border-radius: 4px; }
        button { width: 100%; padding: 12px; background: #28a745; color: white; border: none; border-radius: 4px; cursor: pointer; }
        button:hover { background: #218838; }
        .error { color: red; text-align: center; margin-bottom: 10px; }
        .link { text-align: center; margin-top: 15px; }
        .link a { color: #007bff; text-decoration: none; }
    </style>
</head>
<body>
    <!-- Container chính - Form đăng ký -->
    <div class="container">
        <!-- Tiêu đề trang -->
        <h2><i class="fas fa-user-plus"></i> Đăng ký tài khoản</h2>
        
        <!-- Hiển thị thông báo thành công (nếu đăng ký thành công) -->
        <% if (request.getAttribute("success") != null) { %>
            <div style="color: green; text-align: center; margin-bottom: 10px;">
                <%= request.getAttribute("success") %>
            </div>
        <% } %>
        
        <!-- Hiển thị thông báo lỗi (nếu đăng ký thất bại) -->
        <% if (request.getAttribute("error") != null) { %>
            <div class="error"><%= request.getAttribute("error") %></div>
        <% } %>
        
        <!-- Form POST để đăng ký tài khoản mới -->
        <form method="post" accept-charset="UTF-8">
            <!-- Nhập tên đăng nhập (phải unique) -->
            <div class="form-group">
                <label>Tên đăng nhập</label>
                <input type="text" name="username" required>
            </div>
            
            <!-- Nhập mật khẩu -->
            <div class="form-group">
                <label>Mật khẩu</label>
                <input type="password" name="password" required>
            </div>
            
            <!-- Nhập họ và tên -->
            <div class="form-group">
                <label>Họ và tên</label>
                <input type="text" name="fullName" required>
            </div>
            
            <!-- Nhập email -->
            <div class="form-group">
                <label>Email</label>
                <input type="email" name="email" required>
            </div>
            
            <!-- Nhập số điện thoại -->
            <div class="form-group">
                <label>Số điện thoại</label>
                <input type="text" name="phone" required>
            </div>
            
            <!-- Nhập địa chỉ -->
            <div class="form-group">
                <label>Địa chỉ</label>
                <input type="text" name="address" required>
            </div>
            
            <!-- Nút submit để đăng ký -->
            <button type="submit"><i class="fas fa-check"></i> Đăng ký</button>
        </form>
        
        <!-- Link đến trang đăng nhập cho người dùng đã có tài khoản -->
        <div class="link">
            Đã có tài khoản? <a href="login">Đăng nhập</a>
        </div>
    </div>
</body>
</html>
