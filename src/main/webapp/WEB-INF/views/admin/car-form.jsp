<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>${car != null ? 'Sửa xe' : 'Thêm xe'}</title>
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
        input, select, textarea { width: 100%; padding: 10px; border: 1px solid #ddd; border-radius: 4px; }
        textarea { min-height: 100px; }
        button { padding: 12px 30px; background: #007bff; color: white; border: none; border-radius: 4px; cursor: pointer; }
        button:hover { background: #0056b3; }
        .btn-secondary { background: #6c757d; margin-left: 10px; }
        .btn-secondary:hover { background: #5a6268; }
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
        <h2 style="margin-bottom: 20px;">${car != null ? 'Sửa xe' : 'Thêm xe mới'}</h2>
        <form method="post" enctype="multipart/form-data">
            <input type="hidden" name="carId" value="${car.carId}">
            <input type="hidden" name="currentImage" value="${car.imageUrl}">
            
            <div class="form-group">
                <label>Tên xe</label>
                <input type="text" name="title" value="${car.title}" required>
            </div>
            
            <div class="form-group">
                <label>Giá</label>
                <input type="number" name="price" value="${car.price}" step="0.01" required>
            </div>
            
            <div class="form-group">
                <label>Mô tả</label>
                <textarea name="description" required>${car.description}</textarea>
            </div>
            
            <div class="form-group">
                <label>Danh mục</label>
                <select name="categoryId" required>
                    <c:forEach var="category" items="${categories}">
                        <option value="${category.categoryId}" ${car.categoryId == category.categoryId ? 'selected' : ''}>
                            ${category.name}
                        </option>
                    </c:forEach>
                </select>
            </div>
            
            <div class="form-group">
                <label>Trạng thái</label>
                <select name="status">
                    <option value="1" ${car.status ? 'selected' : ''}>Có sẵn</option>
                    <option value="0" ${!car.status ? 'selected' : ''}>Không có</option>
                </select>
            </div>
            
            <div class="form-group">
                <label>Hình ảnh</label>
                <input type="file" name="image" accept="image/*">
                <c:if test="${car.imageUrl != null}">
                    <div style="margin-top: 10px;">
                        <p style="color: #666; font-size: 14px;">Ảnh hiện tại:</p>
                        <img src="${pageContext.request.contextPath}/images/${car.imageUrl}" width="150" style="border-radius: 4px; border: 1px solid #ddd;">
                        <p style="color: #666; font-size: 12px; margin-top: 5px;">${car.imageUrl}</p>
                    </div>
                </c:if>
                <small style="color: #666; display: block; margin-top: 5px;">
                    Chọn file mới để thay đổi ảnh, bỏ trống để giữ ảnh cũ
                </small>
            </div>
            
            <button type="submit"><i class="fas fa-save"></i> Lưu</button>
            <a href="cars?userId=${param.userId}" class="btn-secondary" style="display: inline-block; padding: 12px 30px; text-decoration: none; color: white; border-radius: 4px;">
                <i class="fas fa-times"></i> Hủy
            </a>
        </form>
    </div>
</body>
</html>
