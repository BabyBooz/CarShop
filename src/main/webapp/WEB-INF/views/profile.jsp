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
    <div class="header">
        <div class="header-content">
            <h1><i class="fas fa-car"></i> Car Shop</h1>
            <div class="nav">
                <a href="home?userId=${param.userId}"><i class="fas fa-home"></i> Trang chủ</a>
                <a href="cart?userId=${param.userId}"><i class="fas fa-shopping-cart"></i> Giỏ hàng</a>
                <a href="orders?userId=${param.userId}"><i class="fas fa-history"></i> Lịch sử mua</a>
                <a href="profile?userId=${param.userId}"><i class="fas fa-user"></i> Thông tin</a>
                <a href="login"><i class="fas fa-sign-out-alt"></i> Đăng xuất</a>
            </div>
        </div>
    </div>
    
    <div class="container">
        <h2 style="margin-bottom: 20px;"><i class="fas fa-user-edit"></i> Cập nhật thông tin cá nhân</h2>
        <% if (request.getAttribute("success") != null) { %>
            <div class="success"><%= request.getAttribute("success") %></div>
        <% } %>
        <% if (request.getAttribute("error") != null) { %>
            <div class="error"><%= request.getAttribute("error") %></div>
        <% } %>
        <form method="post">
            <div class="form-group">
                <label>Họ và tên</label>
                <input type="text" name="fullName" value="${user.fullName}" required>
            </div>
            <div class="form-group">
                <label>Email</label>
                <input type="email" name="email" value="${user.email}" required>
            </div>
            <div class="form-group">
                <label>Số điện thoại</label>
                <input type="text" name="phone" value="${user.phone}" required>
            </div>
            <div class="form-group">
                <label>Địa chỉ</label>
                <input type="text" name="address" value="${user.address}" required>
            </div>
            <button type="submit"><i class="fas fa-save"></i> Cập nhật</button>
        </form>
    </div>
</body>
</html>
