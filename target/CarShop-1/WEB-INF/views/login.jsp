<%-- 
    login.jsp - Trang đăng nhập
    
    Chức năng:
    - Hiển thị form đăng nhập (tên đăng nhập, mật khẩu)
    - Hiển thị thông báo lỗi nếu đăng nhập thất bại
    - Link đến trang đăng ký cho người dùng mới
    
    Luồng:
    1. Người dùng truy cập /login (GET)
    2. LoginServlet hiển thị form trống
    3. Người dùng nhập username, password, submit (POST)
    4. LoginServlet kiểm tra database, nếu đúng redirect đến /home?userId=X
    5. Nếu sai, gửi lại form với thông báo lỗi
    
    Dữ liệu từ request:
    - request.getAttribute("error"): Thông báo lỗi đăng nhập
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Đăng nhập</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: Arial, sans-serif; background: #f5f5f5; }
        .container { max-width: 400px; margin: 100px auto; background: white; padding: 30px; border: 1px solid #ddd; border-radius: 4px; }
        h2 { text-align: center; margin-bottom: 20px; color: #333; }
        .form-group { margin-bottom: 15px; }
        label { display: block; margin-bottom: 5px; color: #555; }
        input { width: 100%; padding: 10px; border: 1px solid #ddd; border-radius: 4px; }
        button { width: 100%; padding: 12px; background: #007bff; color: white; border: none; border-radius: 4px; cursor: pointer; }
        button:hover { background: #0056b3; }
        .error { color: red; text-align: center; margin-bottom: 10px; }
        .link { text-align: center; margin-top: 15px; }
        .link a { color: #007bff; text-decoration: none; }
    </style>
</head>
<body>
    <!-- Container chính - Form đăng nhập -->
    <div class="container">
        <!-- Tiêu đề trang -->
        <h2><i class="fas fa-car"></i> Đăng nhập</h2>
        
        <!-- Hiển thị thông báo lỗi nếu đăng nhập thất bại -->
        <% if (request.getAttribute("error") != null) { %>
            <div class="error"><%= request.getAttribute("error") %></div>
        <% } %>
        
        <!-- Form POST để đăng nhập -->
        <form method="post">
            <!-- Nhập tên đăng nhập -->
            <div class="form-group">
                <label>Tên đăng nhập</label>
                <input type="text" name="username" required>
            </div>
            
            <!-- Nhập mật khẩu -->
            <div class="form-group">
                <label>Mật khẩu</label>
                <input type="password" name="password" required>
            </div>
            
            <!-- Nút submit để đăng nhập -->
            <button type="submit"><i class="fas fa-sign-in-alt"></i> Đăng nhập</button>
        </form>
        
        <!-- Link đến trang đăng ký cho người dùng mới -->
        <div class="link">
            Chưa có tài khoản? <a href="register">Đăng ký ngay</a>
        </div>
    </div>
</body>
</html>
