<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Trang chủ</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: Arial, sans-serif; background: #f5f5f5; }
        .header { background: #333; color: white; padding: 15px 0; }
        .header-content { max-width: 1200px; margin: 0 auto; display: flex; justify-content: space-between; align-items: center; padding: 0 20px; }
        .header h1 { font-size: 24px; }
        .nav a { color: white; text-decoration: none; margin-left: 20px; }
        .nav a:hover { text-decoration: underline; }
        .container { max-width: 1200px; margin: 30px auto; padding: 0 20px; }
        .car-grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(280px, 1fr)); gap: 20px; }
        .car-card { background: white; border: 1px solid #ddd; border-radius: 4px; overflow: hidden; }
        .car-image { width: 100%; height: 200px; object-fit: cover; }
        .car-info { padding: 15px; }
        .car-title { font-size: 18px; font-weight: bold; margin-bottom: 10px; }
        .car-price { color: #e74c3c; font-size: 20px; font-weight: bold; margin-bottom: 10px; }
        .car-category { color: #666; font-size: 14px; margin-bottom: 10px; }
        .btn { display: inline-block; padding: 10px 20px; background: #007bff; color: white; text-decoration: none; border-radius: 4px; border: none; cursor: pointer; }
        .btn:hover { background: #0056b3; }
        .add-cart-form { display: flex; gap: 10px; align-items: center; }
        .add-cart-form input { width: 60px; padding: 8px; border: 1px solid #ddd; border-radius: 4px; }
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
        <h2 style="margin-bottom: 20px;">Danh sách xe</h2>
        <div class="car-grid">
            <c:forEach var="car" items="${cars}">
                <div class="car-card">
                    <img src="${pageContext.request.contextPath}/images/${car.imageUrl}" class="car-image" alt="${car.title}">
                    <div class="car-info">
                        <div class="car-title">${car.title}</div>
                        <div class="car-category"><i class="fas fa-tag"></i> ${car.categoryName}</div>
                        <div class="car-price"><fmt:formatNumber value="${car.price}" pattern="#,###"/> VNĐ</div>
                        <p>${car.description}</p>
                        <form action="cart?userId=${param.userId}" method="post" class="add-cart-form">
                            <input type="hidden" name="action" value="add">
                            <input type="hidden" name="carId" value="${car.carId}">
                            <input type="number" name="quantity" value="1" min="1">
                            <button type="submit" class="btn"><i class="fas fa-cart-plus"></i> Thêm</button>
                        </form>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</body>
</html>
