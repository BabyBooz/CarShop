<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Quản lý xe</title>
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
        .btn { padding: 10px 20px; background: #007bff; color: white; text-decoration: none; border-radius: 4px; display: inline-block; border: none; cursor: pointer; }
        .btn:hover { background: #0056b3; }
        .btn-success { background: #28a745; }
        .btn-success:hover { background: #218838; }
        .btn-danger { background: #dc3545; }
        .btn-danger:hover { background: #c82333; }
        table { width: 100%; background: white; border-collapse: collapse; margin-top: 20px; }
        th, td { padding: 12px; text-align: left; border-bottom: 1px solid #ddd; }
        th { background: #f8f9fa; }
        img { border-radius: 4px; }
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
        <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px;">
            <h2>Quản lý xe</h2>
            <a href="cars?action=add&userId=${param.userId}" class="btn btn-success"><i class="fas fa-plus"></i> Thêm xe mới</a>
        </div>
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Hình ảnh</th>
                    <th>Tên xe</th>
                    <th>Giá</th>
                    <th>Danh mục</th>
                    <th>Trạng thái</th>
                    <th>Thao tác</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="car" items="${cars}">
                    <tr>
                        <td>${car.carId}</td>
                        <td><img src="${pageContext.request.contextPath}/images/${car.imageUrl}" width="80" height="60" style="object-fit: cover;"></td>
                        <td>${car.title}</td>
                        <td><fmt:formatNumber value="${car.price}" pattern="#,###"/> VNĐ</td>
                        <td>${car.categoryName}</td>
                        <td>${car.status ? 'Có sẵn' : 'Không có'}</td>
                        <td>
                            <a href="cars?action=edit&id=${car.carId}&userId=${param.userId}" class="btn"><i class="fas fa-edit"></i></a>
                            <form action="cars?userId=${param.userId}" method="post" style="display: inline;">
                                <input type="hidden" name="action" value="delete">
                                <input type="hidden" name="id" value="${car.carId}">
                                <button type="submit" class="btn btn-danger" onclick="return confirm('Xác nhận xóa?')"><i class="fas fa-trash"></i></button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>
