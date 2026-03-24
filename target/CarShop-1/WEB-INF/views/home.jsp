<%-- 
    home.jsp - Trang chủ hiển thị danh sách sản phẩm xe
    
    Chức năng:
    - Hiển thị danh sách xe có sẵn (status = 1)
    - Hiển thị số lượng còn lại của mỗi xe
    - Cho phép thêm vào giỏ hàng
    - Disable nút thêm nếu hết hàng
    
    Dữ liệu từ request:
    - ${cars}: List<Car> danh sách xe
    - ${param.userId}: ID người dùng từ URL
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Trang chủ - Car Shop</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: Arial, sans-serif; background: #f5f5f5; }
        
        /* Header */
        .header { background: #333; color: white; padding: 15px 0; }
        .header-content { max-width: 1200px; margin: 0 auto; display: flex; justify-content: space-between; align-items: center; padding: 0 20px; }
        .header h1 { font-size: 24px; }
        .nav a { color: white; text-decoration: none; margin-left: 20px; }
        .nav a:hover { text-decoration: underline; }
        
        /* Container */
        .container { max-width: 1200px; margin: 30px auto; padding: 0 20px; }
        
        /* Car Grid */
        .car-grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(300px, 1fr)); gap: 20px; }
        .car-card { background: white; border: 1px solid #ddd; border-radius: 4px; overflow: hidden; transition: box-shadow 0.3s; }
        .car-card:hover { box-shadow: 0 4px 8px rgba(0,0,0,0.1); }
        
        /* Car Image */
        .car-image { width: 100%; height: 200px; object-fit: cover; }
        
        /* Car Info */
        .car-info { padding: 15px; }
        .car-title { font-size: 18px; font-weight: bold; margin-bottom: 10px; color: #333; }
        .car-price { color: #e74c3c; font-size: 20px; font-weight: bold; margin-bottom: 10px; }
        .car-category { color: #666; font-size: 14px; margin-bottom: 8px; }
        .car-quantity { color: #27ae60; font-size: 14px; margin-bottom: 10px; font-weight: bold; }
        .car-quantity.low-stock { color: #e67e22; }
        .car-quantity.out-of-stock { color: #e74c3c; }
        .car-description { color: #666; font-size: 14px; margin-bottom: 15px; line-height: 1.4; }
        
        /* Form */
        .add-cart-form { display: flex; gap: 10px; align-items: center; margin-top: 10px; }
        .add-cart-form input[type="number"] { width: 70px; padding: 8px; border: 1px solid #ddd; border-radius: 4px; }
        
        /* Button */
        .btn { display: inline-block; padding: 10px 20px; background: #007bff; color: white; text-decoration: none; border-radius: 4px; border: none; cursor: pointer; font-size: 14px; }
        .btn:hover { background: #0056b3; }
        .btn:disabled { background: #ccc; cursor: not-allowed; }
        .btn-out-of-stock { background: #e74c3c; }
        .btn-out-of-stock:hover { background: #c0392b; }
    </style>
</head>
<body>
    <!-- Header - Thanh điều hướng chính -->
    <div class="header">
        <div class="header-content">
            <!-- Logo -->
            <h1><i class="fas fa-car"></i> Car Shop</h1>
            
            <!-- Menu điều hướng -->
            <div class="nav">
                <a href="home?userId=${param.userId}"><i class="fas fa-home"></i> Trang chủ</a>
                <a href="cart?userId=${param.userId}"><i class="fas fa-shopping-cart"></i> Giỏ hàng</a>
                <a href="orders?userId=${param.userId}"><i class="fas fa-history"></i> Lịch sử mua</a>
                <a href="profile?userId=${param.userId}"><i class="fas fa-user"></i> Thông tin</a>
                <a href="login"><i class="fas fa-sign-out-alt"></i> Đăng xuất</a>
            </div>
        </div>
    </div>
    
    <!-- Nội dung chính -->
    <div class="container">
        <h2 style="margin-bottom: 20px;"><i class="fas fa-car"></i> Danh sách xe</h2>
        
        <!-- Lưới hiển thị sản phẩm -->
        <div class="car-grid">
            <!-- Lặp qua danh sách xe -->
            <c:forEach var="car" items="${cars}">
                <div class="car-card">
                    <!-- Hình ảnh xe -->
                    <img src="${pageContext.request.contextPath}/images/${car.imageUrl}" class="car-image" alt="${car.title}">
                    
                    <!-- Thông tin xe -->
                    <div class="car-info">
                        <!-- Tên xe -->
                        <div class="car-title">${car.title}</div>
                        
                        <!-- Danh mục xe -->
                        <div class="car-category">
                            <i class="fas fa-tag"></i> ${car.categoryName}
                        </div>
                        
                        <!-- Giá xe -->
                        <div class="car-price">
                            <fmt:formatNumber value="${car.price}" pattern="#,###"/> VNĐ
                        </div>
                        
                        <!-- Số lượng còn lại -->
                        <c:choose>
                            <c:when test="${car.quantity == 0}">
                                <div class="car-quantity out-of-stock">
                                    <i class="fas fa-times-circle"></i> Hết hàng
                                </div>
                            </c:when>
                            <c:when test="${car.quantity <= 5}">
                                <div class="car-quantity low-stock">
                                    <i class="fas fa-exclamation-triangle"></i> Còn lại: ${car.quantity} xe
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="car-quantity">
                                    <i class="fas fa-box"></i> Còn lại: ${car.quantity} xe
                                </div>
                            </c:otherwise>
                        </c:choose>
                        
                        <!-- Mô tả xe -->
                        <div class="car-description">${car.description}</div>
                        
                        <!-- Form thêm vào giỏ hàng -->
                        <c:choose>
                            <c:when test="${car.quantity > 0}">
                                <form action="cart?userId=${param.userId}" method="post" class="add-cart-form">
                                    <input type="hidden" name="action" value="add">
                                    <input type="hidden" name="carId" value="${car.carId}">
                                    <input type="number" name="quantity" value="1" min="1" max="${car.quantity}" required>
                                    <button type="submit" class="btn">
                                        <i class="fas fa-cart-plus"></i> Thêm vào giỏ
                                    </button>
                                </form>
                            </c:when>
                            <c:otherwise>
                                <button class="btn btn-out-of-stock" disabled>
                                    <i class="fas fa-ban"></i> Hết hàng
                                </button>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</body>
</html>
