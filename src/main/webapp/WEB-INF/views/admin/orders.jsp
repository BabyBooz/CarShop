<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Quản lý đơn hàng</title>
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
        .order-card { background: white; border: 1px solid #ddd; border-radius: 4px; padding: 20px; margin-bottom: 20px; }
        .order-header { display: flex; justify-content: space-between; margin-bottom: 15px; padding-bottom: 10px; border-bottom: 1px solid #eee; }
        table { width: 100%; border-collapse: collapse; }
        th, td { padding: 10px; text-align: left; border-bottom: 1px solid #eee; }
        select { padding: 5px 10px; border: 1px solid #ddd; border-radius: 4px; }
        .btn { padding: 8px 15px; background: #007bff; color: white; border: none; border-radius: 4px; cursor: pointer; }
        .btn:hover { background: #0056b3; }
        .status { padding: 5px 10px; border-radius: 4px; font-size: 14px; }
        .status-wait { background: #ffc107; color: white; }
        .status-process { background: #17a2b8; color: white; }
        .status-done { background: #28a745; color: white; }
    </style>
</head>
<body>
    <div class="header">
        <div class="header-content">
            <h1><i class="fas fa-car"></i> Admin - Car Shop</h1>
            <div class="nav">
                <a href="cars?userId=${param.userId}"><i class="fas fa-car"></i> Quản lý xe</a>
                <a href="orders?userId=${param.userId}"><i class="fas fa-box"></i> Đơn hàng</a>
                <a href="${pageContext.request.contextPath}/login"><i class="fas fa-sign-out-alt"></i> Đăng xuất</a>
            </div>
        </div>
    </div>
    
    <div class="container">
        <h2 style="margin-bottom: 20px;"><i class="fas fa-box"></i> Quản lý đơn hàng</h2>
        <c:forEach var="order" items="${orders}">
            <div class="order-card">
                <div class="order-header">
                    <div>
                        <strong>Đơn hàng #${order.orderId}</strong><br>
                        <small>Khách hàng ID: ${order.userId}</small><br>
                        <small>Ngày đặt: ${order.createdAt}</small>
                    </div>
                    <div>
                        <form action="orders?userId=${param.userId}" method="post" style="display: flex; gap: 10px; align-items: center;">
                            <input type="hidden" name="orderId" value="${order.orderId}">
                            <select name="status">
                                <option value="wait" ${order.status == 'wait' ? 'selected' : ''}>Chờ xử lý</option>
                                <option value="process" ${order.status == 'process' ? 'selected' : ''}>Đang xử lý</option>
                                <option value="done" ${order.status == 'done' ? 'selected' : ''}>Hoàn thành</option>
                            </select>
                            <button type="submit" class="btn"><i class="fas fa-save"></i> Cập nhật</button>
                        </form>
                    </div>
                </div>
                <table>
                    <thead>
                        <tr>
                            <th>Sản phẩm</th>
                            <th>Số lượng</th>
                            <th>Đơn giá</th>
                            <th>Tổng</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="item" items="${order.items}">
                            <tr>
                                <td>${item.carTitle}</td>
                                <td>${item.quantity}</td>
                                <td><fmt:formatNumber value="${item.price}" pattern="#,###"/> VNĐ</td>
                                <td><fmt:formatNumber value="${item.totalPrice}" pattern="#,###"/> VNĐ</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <div style="text-align: right; margin-top: 10px; font-size: 18px; font-weight: bold;">
                    Tổng cộng: <fmt:formatNumber value="${order.totalPrice}" pattern="#,###"/> VNĐ
                </div>
            </div>
        </c:forEach>
    </div>
</body>
</html>
