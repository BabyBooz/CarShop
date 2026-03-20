<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Giỏ hàng</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: Arial, sans-serif; background: #f5f5f5; }
        .header { background: #333; color: white; padding: 15px 0; }
        .header-content { max-width: 1200px; margin: 0 auto; display: flex; justify-content: space-between; align-items: center; padding: 0 20px; }
        .header h1 { font-size: 24px; }
        .nav a { color: white; text-decoration: none; margin-left: 20px; }
        .nav a:hover { text-decoration: underline; }
        .container { max-width: 1200px; margin: 30px auto; padding: 0 20px; background: white; border: 1px solid #ddd; border-radius: 4px; padding: 20px; }
        table { width: 100%; border-collapse: collapse; }
        th, td { padding: 12px; text-align: left; border-bottom: 1px solid #ddd; }
        th { background: #f8f9fa; }
        .btn { padding: 8px 15px; background: #007bff; color: white; text-decoration: none; border-radius: 4px; border: none; cursor: pointer; }
        .btn:hover { background: #0056b3; }
        .btn-danger { background: #dc3545; }
        .btn-danger:hover { background: #c82333; }
        .btn-success { background: #28a745; }
        .btn-success:hover { background: #218838; }
        .total { text-align: right; font-size: 20px; font-weight: bold; margin-top: 20px; }
        input[type="number"] { width: 60px; padding: 5px; border: 1px solid #ddd; border-radius: 4px; }
        .empty { text-align: center; padding: 40px; color: #666; }
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
        <h2 style="margin-bottom: 20px;"><i class="fas fa-shopping-cart"></i> Giỏ hàng của bạn</h2>
        <c:if test="${empty cartItems}">
            <div class="empty">
                <i class="fas fa-shopping-cart" style="font-size: 48px; color: #ccc;"></i>
                <p>Giỏ hàng trống</p>
            </div>
        </c:if>
        <c:if test="${not empty cartItems}">
            <table>
                <thead>
                    <tr>
                        <th>Hình ảnh</th>
                        <th>Tên xe</th>
                        <th>Đơn giá</th>
                        <th>Số lượng</th>
                        <th>Tổng</th>
                        <th>Thao tác</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="item" items="${cartItems}">
                        <tr>
                            <td><img src="${pageContext.request.contextPath}/images/${item.imageUrl}" width="80" height="60" style="object-fit: cover;"></td>
                            <td>${item.carTitle}</td>
                            <td><fmt:formatNumber value="${item.carPrice}" pattern="#,###"/> VNĐ</td>
                            <td>
                                <form action="cart?userId=${param.userId}" method="post" style="display: inline;">
                                    <input type="hidden" name="action" value="update">
                                    <input type="hidden" name="cartItemId" value="${item.cartItemId}">
                                    <input type="hidden" name="price" value="${item.carPrice}">
                                    <input type="number" name="quantity" value="${item.quantity}" min="1">
                                    <button type="submit" class="btn"><i class="fas fa-sync"></i></button>
                                </form>
                            </td>
                            <td><fmt:formatNumber value="${item.totalPrice}" pattern="#,###"/> VNĐ</td>
                            <td>
                                <form action="cart?userId=${param.userId}" method="post" style="display: inline;">
                                    <input type="hidden" name="action" value="remove">
                                    <input type="hidden" name="cartItemId" value="${item.cartItemId}">
                                    <button type="submit" class="btn btn-danger"><i class="fas fa-trash"></i></button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <div class="total">
                Tổng cộng: <fmt:formatNumber value="${total}" pattern="#,###"/> VNĐ
            </div>
            <div style="text-align: right; margin-top: 20px;">
                <form action="checkout?userId=${param.userId}" method="post" style="display: inline;">
                    <button type="submit" class="btn btn-success"><i class="fas fa-check"></i> Thanh toán</button>
                </form>
            </div>
        </c:if>
    </div>
</body>
</html>
