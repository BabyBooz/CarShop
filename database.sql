-- Tạo cơ sở dữ liệu
CREATE DATABASE car_store_system;
GO

USE car_store_system;
GO

-- Create `users` table
CREATE TABLE users (
    user_id INT IDENTITY(1,1) PRIMARY KEY,
    full_name NVARCHAR(100) NOT NULL,
    username NVARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100),
    phone VARCHAR(15),
    address NVARCHAR(MAX) NOT NULL,
    role VARCHAR(10) CHECK (role IN ('customer', 'admin')) NOT NULL
);
GO

-- Create `categories` table (Car Categories)
CREATE TABLE categories (
    category_id INT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(100) NOT NULL,
    description NVARCHAR(MAX)
);
GO

-- Create `cars` table (Cars)
CREATE TABLE cars (
    car_id INT IDENTITY(1,1) PRIMARY KEY,
    title NVARCHAR(100) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    description NVARCHAR(MAX),
    category_id INT,
    status BIT NOT NULL,
    image_url VARCHAR(255),
    FOREIGN KEY (category_id) REFERENCES categories(category_id)
);
GO

-- Create `cart` table (Shopping Cart)
CREATE TABLE cart (
    cart_id INT IDENTITY(1,1) PRIMARY KEY,
    user_id INT,
    created_at DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);
GO

-- Create `cart_items` table (Items in the cart)
CREATE TABLE cart_items (
    cart_item_id INT IDENTITY(1,1) PRIMARY KEY,
    cart_id INT,
    car_id INT,
    quantity INT NOT NULL,
    total_price DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (cart_id) REFERENCES cart(cart_id),
    FOREIGN KEY (car_id) REFERENCES cars(car_id)
);
GO

-- Create `orders` table (Orders)
CREATE TABLE orders (
    order_id INT IDENTITY(1,1) PRIMARY KEY,
    user_id INT,
    total_price DECIMAL(10, 2) NOT NULL,
    status VARCHAR(10) CHECK (status IN ('wait', 'process', 'done')) NOT NULL DEFAULT 'wait',
    created_at DATETIME DEFAULT GETDATE(),
    updated_at DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);
GO

-- Create `order_items` table (Order details)
CREATE TABLE order_items (
    order_item_id INT IDENTITY(1,1) PRIMARY KEY,
    order_id INT,
    car_id INT,
    quantity INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    total_price DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(order_id),
    FOREIGN KEY (car_id) REFERENCES cars(car_id)
);
GO

-- Insert data into `users` table
INSERT INTO users (full_name, username, password, email, phone, address, role) VALUES
(N'Nguyễn Văn Quản Trị', 'admin', '123456', 'admin@carstore.com', '0901234567', N'Hà Nội', 'admin'),
(N'Trần Thị Khách Hàng', 'khachhang1', '123456', 'khachhang1@gmail.com', '0987654321', N'Hồ Chí Minh', 'customer'),
(N'Lê Văn Mua Sắm', 'khachhang2', '123456', 'khachhang2@gmail.com', '0911222333', N'Đà Nẵng', 'customer');
GO

-- Insert data into `categories` table
INSERT INTO categories (name) VALUES
(N'SUV'),
(N'Sedan'),
(N'Coupe');
GO

-- Insert data into `cars` table
INSERT INTO cars (title, price, description, category_id, status, image_url) VALUES
(N'Toyota RAV4', 700000000.00, N'SUV với khả năng vận hành mạnh mẽ và tiết kiệm nhiên liệu.', 1, 1, 'toyota_rav4.jpg'),
(N'Honda Accord', 800000000.00, N'Sedan cao cấp với thiết kế sang trọng và công nghệ tiên tiến.', 2, 1, 'honda_accord.jpg'),
(N'BMW 3 Series', 1200000000.00, N'Coupe thể thao với động cơ mạnh mẽ và thiết kế hiện đại.', 3, 1, 'bmw_3_series.jpg');
GO

-- Insert data into `cart` table
INSERT INTO cart (user_id) VALUES
(2),
(3);
GO

-- Insert data into `cart_items` table
INSERT INTO cart_items (cart_id, car_id, quantity, total_price) VALUES
(1, 1, 1, 700000000.00),
(1, 2, 2, 1600000000.00);
GO

-- Insert data into `orders` table
INSERT INTO orders (user_id, total_price, status) VALUES
(3, 2400000000.00, 'wait');
GO

-- Insert data into `order_items` table
INSERT INTO order_items (order_id, car_id, quantity, price, total_price) VALUES
(1, 3, 1, 1200000000.00, 1200000000.00);
GO
