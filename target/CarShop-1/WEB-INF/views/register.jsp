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
    <div class="container">
        <h2><i class="fas fa-user-plus"></i> Đăng ký tài khoản</h2>
        <% if (request.getAttribute("error") != null) { %>
            <div class="error"><%= request.getAttribute("error") %></div>
        <% } %>
        <form method="post">
            <div class="form-group">
                <label>Mã số</label>
                <input type="text" name="rollNumber" required>
            </div>
            <div class="form-group">
                <label>Họ và tên</label>
                <input type="text" name="fullName" required>
            </div>
            <div class="form-group">
                <label>Tên đăng nhập</label>
                <input type="text" name="username" required>
            </div>
            <div class="form-group">
                <label>Mật khẩu</label>
                <input type="password" name="password" required>
            </div>
            <div class="form-group">
                <label>Email</label>
                <input type="email" name="email" required>
            </div>
            <div class="form-group">
                <label>Số điện thoại</label>
                <input type="text" name="phone" required>
            </div>
            <div class="form-group">
                <label>Địa chỉ</label>
                <input type="text" name="address" required>
            </div>
            <button type="submit"><i class="fas fa-check"></i> Đăng ký</button>
        </form>
        <div class="link">
            Đã có tài khoản? <a href="login">Đăng nhập</a>
        </div>
    </div>
</body>
</html>
