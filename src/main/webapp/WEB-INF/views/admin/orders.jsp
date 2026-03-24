<%-- 
    admin/orders.jsp - Trang quản lý đơn hàng cho admin
    
    Chức năng:
    - Hiển thị danh sách tất cả đơn hàng từ tất cả khách hàng
    - Hiển thị chi tiết từng đơn hàng (sản phẩm, số lượng, giá)
    - Cho phép admin cập nhật trạng thái đơn hàng (chờ xử lý, đang xử lý, hoàn thành)
    - Hiển thị tổng tiền cho mỗi đơn hàng
    
    Luồng:
    1. Admin truy cập /admin/orders?userId=X
    2. AdminOrderServlet lấy tất cả đơn hàng từ database
    3. JSP hiển thị danh sách đơn hàng
    4. Admin chọn trạng thái mới, submit form (POST)
    5. AdminOrderServlet cập nhật trạng thái, redirect về trang danh sách
    
    Dữ liệu từ request:
    - ${orders}: List<Order> chứa tất cả đơn hàng
    - ${order.items}: List<OrderItem> chứa chi tiết sản phẩm trong đơn hàng
    - ${param.userId}: ID admin từ URL
    
    JSTL Tags:
    - <c:forEach>: Lặp qua danh sách đơn hàng
    - <fmt:formatNumber>: Định dạng số tiền
--%>
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
    <!-- Header - Thanh điều hướng admin -->
    <div class="header">
        <div class="header-content">
            <!-- Logo và tên ứng dụng -->
            <h1><i class="fas fa-car"></i> Admin - Car Shop</h1>
            
            <!-- Menu điều hướng admin -->
            <div class="nav">
                <!-- Link quản lý xe -->
                <a href="cars?userId=${param.userId}"><i class="fas fa-car"></i> Quản lý xe</a>
                
                <!-- Link quản lý đơn hàng (trang hiện tại) -->
                <a href="orders?userId=${param.userId}"><i class="fas fa-box"></i> Đơn hàng</a>
                
                <!-- Link đăng xuất -->
                <a href="${pageContext.request.contextPath}/login"><i class="fas fa-sign-out-alt"></i> Đăng xuất</a>
            </div>
        </div>
    </div>
    
    <!-- Container chính - Danh sách đơn hàng -->
    <div class="container">
        <!-- Tiêu đề trang -->
        <h2 style="margin-bottom: 20px;"><i class="fas fa-box"></i> Quản lý đơn hàng</h2>
        
        <!-- Lặp qua từng đơn hàng -->
        <c:forEach var="order" items="${orders}">
            <!-- Card cho mỗi đơn hàng -->
            <div class="order-card">
                <!-- Header của đơn hàng: ID, khách hàng, ngày đặt, form cập nhật trạng thái -->
                <div class="order-header">
                    <div>
                        <!-- ID đơn hàng -->
                        <strong>Đơn hàng #${order.orderId}</strong><br>
                        <!-- ID khách hàng -->
                        <small>Khách hàng ID: ${order.userId}</small><br>
                        <!-- Ngày đặt hàng -->
                        <small>Ngày đặt: ${order.createdAt}</small>
                    </div>
                    <div>
                        <!-- Form cập nhật trạng thái đơn hàng -->
                        <form action="orders?userId=${param.userId}" method="post" style="display: flex; gap: 10px; align-items: center;">
                            <!-- Hidden input: ID đơn hàng -->
                            <input type="hidden" name="orderId" value="${order.orderId}">
                            
                            <!-- Dropdown chọn trạng thái mới -->
                            <select name="status">
                                <!-- Chờ xử lý -->
                                <option value="wait" ${order.status == 'wait' ? 'selected' : ''}>Chờ xử lý</option>
                                <!-- Đang xử lý -->
                                <option value="process" ${order.status == 'process' ? 'selected' : ''}>Đang xử lý</option>
                                <!-- Hoàn thành -->
                                <option value="done" ${order.status == 'done' ? 'selected' : ''}>Hoàn thành</option>
                            </select>
                            
                            <!-- Nút submit để cập nhật trạng thái -->
                            <button type="submit" class="btn"><i class="fas fa-save"></i> Cập nhật</button>
                        </form>
                    </div>
                </div>
                
                <!-- Bảng chi tiết sản phẩm trong đơn hàng -->
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
                        <!-- Lặp qua từng sản phẩm trong đơn hàng -->
                        <c:forEach var="item" items="${order.items}">
                            <tr>
                                <!-- Tên sản phẩm -->
                                <td>${item.carTitle}</td>
                                <!-- Số lượng mua -->
                                <td>${item.quantity}</td>
                                <!-- Giá đơn vị (định dạng số tiền) -->
                                <td><fmt:formatNumber value="${item.price}" pattern="#,###"/> VNĐ</td>
                                <!-- Tổng tiền (số lượng x giá) -->
                                <td><fmt:formatNumber value="${item.totalPrice}" pattern="#,###"/> VNĐ</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                
                <!-- Tổng cộng của đơn hàng -->
                <div style="text-align: right; margin-top: 10px; font-size: 18px; font-weight: bold;">
                    Tổng cộng: <fmt:formatNumber value="${order.totalPrice}" pattern="#,###"/> VNĐ
                </div>
            </div>
        </c:forEach>
    </div>
</body>
</html>
