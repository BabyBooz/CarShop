<%-- 
    orders.jsp - Trang lịch sử mua hàng của người dùng
    
    Chức năng:
    - Hiển thị danh sách tất cả đơn hàng của người dùng
    - Hiển thị chi tiết từng đơn hàng (sản phẩm, số lượng, giá)
    - Hiển thị trạng thái đơn hàng (chờ xử lý, đang xử lý, hoàn thành)
    - Hiển thị tổng tiền cho mỗi đơn hàng
    
    Luồng:
    1. Người dùng truy cập /orders?userId=X
    2. OrderServlet lấy tất cả đơn hàng của người dùng từ database
    3. JSP hiển thị danh sách đơn hàng với chi tiết
    
    Dữ liệu từ request:
    - ${orders}: List<Order> chứa tất cả đơn hàng của người dùng
    - ${order.items}: List<OrderItem> chứa chi tiết sản phẩm trong đơn hàng
    - ${param.userId}: ID người dùng từ URL
    - ${param.success}: Thông báo thành công (nếu vừa checkout)
    
    JSTL Tags:
    - <c:if>: Kiểm tra điều kiện
    - <c:forEach>: Lặp qua danh sách
    - <c:choose>: Switch case cho trạng thái
    - <fmt:formatNumber>: Định dạng số tiền
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Lịch sử mua hàng</title>
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
        .status { padding: 5px 10px; border-radius: 4px; font-size: 14px; }
        .status-wait { background: #ffc107; color: white; }
        .status-process { background: #17a2b8; color: white; }
        .status-done { background: #28a745; color: white; }
        table { width: 100%; border-collapse: collapse; }
        th, td { padding: 10px; text-align: left; border-bottom: 1px solid #eee; }
        .empty { text-align: center; padding: 40px; color: #666; }
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
                <!-- Link trang chủ -->
                <a href="home?userId=${param.userId}"><i class="fas fa-home"></i> Trang chủ</a>
                
                <!-- Link giỏ hàng -->
                <a href="cart?userId=${param.userId}"><i class="fas fa-shopping-cart"></i> Giỏ hàng</a>
                
                <!-- Link lịch sử mua hàng (trang hiện tại) -->
                <a href="orders?userId=${param.userId}"><i class="fas fa-history"></i> Lịch sử mua</a>
                
                <!-- Link thông tin cá nhân -->
                <a href="profile?userId=${param.userId}"><i class="fas fa-user"></i> Thông tin</a>
                
                <!-- Link đăng xuất -->
                <a href="login"><i class="fas fa-sign-out-alt"></i> Đăng xuất</a>
            </div>
        </div>
    </div>
    
    <!-- Container chính - Danh sách đơn hàng -->
    <div class="container">
        <!-- Tiêu đề trang -->
        <h2 style="margin-bottom: 20px;"><i class="fas fa-history"></i> Lịch sử mua hàng</h2>
        
        <!-- Hiển thị thông báo thành công nếu vừa checkout -->
        <c:if test="${param.success != null}">
            <div style="background: #d4edda; color: #155724; padding: 15px; border-radius: 4px; margin-bottom: 20px;">
                Đặt hàng thành công!
            </div>
        </c:if>
        
        <!-- Hiển thị thông báo nếu chưa có đơn hàng nào -->
        <c:if test="${empty orders}">
            <div class="empty">
                <i class="fas fa-box-open" style="font-size: 48px; color: #ccc;"></i>
                <p>Chưa có đơn hàng nào</p>
            </div>
        </c:if>
        
        <!-- Lặp qua từng đơn hàng -->
        <c:forEach var="order" items="${orders}">
            <!-- Card cho mỗi đơn hàng -->
            <div class="order-card">
                <!-- Header của đơn hàng: ID, ngày đặt, trạng thái -->
                <div class="order-header">
                    <div>
                        <!-- ID đơn hàng -->
                        <strong>Đơn hàng #${order.orderId}</strong><br>
                        <!-- Ngày đặt hàng -->
                        <small>Ngày đặt: ${order.createdAt}</small>
                    </div>
                    <div>
                        <!-- Hiển thị trạng thái với màu khác nhau -->
                        <c:choose>
                            <!-- Trạng thái: Chờ xử lý (vàng) -->
                            <c:when test="${order.status == 'wait'}">
                                <span class="status status-wait">Chờ xử lý</span>
                            </c:when>
                            <!-- Trạng thái: Đang xử lý (xanh dương) -->
                            <c:when test="${order.status == 'process'}">
                                <span class="status status-process">Đang xử lý</span>
                            </c:when>
                            <!-- Trạng thái: Hoàn thành (xanh lá) -->
                            <c:otherwise>
                                <span class="status status-done">Hoàn thành</span>
                            </c:otherwise>
                        </c:choose>
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
