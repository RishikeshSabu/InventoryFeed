CREATE DATABASE retail_database;
USE retail_database;

-- Creating table
CREATE TABLE producs(
product_id INT PRIMARY KEY,
product_name VARCHAR(20),
category_id INT NULL,
supplier_id INT NULL,
price DECIMAL(5,2)
);

ALTER TABLE producs rename to  products;

-- Creating Categories table
CREATE TABLE categories(
category_id INT PRIMARY KEY,
category_name VARCHAR(20)
);

-- Creating Suppliers table
CREATE TABLE suppliers(
supplier_id INT PRIMARY KEY, 
supplier_name VARCHAR(20),
contact_email VARCHAR(50)
);


-- updating columns category id and supplier id as foreign key of table products
ALTER TABLE products
ADD CONSTRAINT fk_category FOREIGN KEY(category_id) REFERENCES categories(category_id) ON DELETE SET NULL,
ADD CONSTRAINT fk_supplier FOREIGN KEY(supplier_id) REFERENCES suppliers(supplier_id) ON DELETE SET NULL;

-- Creating Inventory table
CREATE TABLE inventory(
inventory_id INT PRIMARY KEY,
product_id INT,
quantity INT,
last_updated DATE,
CONSTRAINT fk_product FOREIGN KEY(product_id) REFERENCES products(product_id) ON DELETE SET NULL
);
ALTER TABLE inventory MODIFY product_id INT NULL;

-- Creating Orders table
CREATE TABLE orders(
order_id INT PRIMARY KEY,
product_id INT NULL, 
order_date DATE,
quantity_ordered INT,
total_price DECIMAL (7,2),
CONSTRAINT fk_productId FOREIGN KEY(product_id) REFERENCES products(product_id) ON DELETE SET NULL
);
-- Finished creating tables

-- Insering datas into tables with the help of google
INSERT INTO categories (category_id, category_name) VALUES
(1, 'Electronics'),
(2, 'Clothing'),
(3, 'Books'),
(4, 'Furniture'),
(5, 'Football'),
(6, 'Arts');

INSERT INTO suppliers (supplier_id, supplier_name, contact_email) VALUES
(1, 'Matha', 'matha@suppliers.com'),
(2, 'Vaibhav', 'vaibhav@distributors.com'),
(3, 'Rohith', 'rohith@supplies.com'),
(4, 'Fresh Foods', 'fresh@foods.com'),
(5, 'Tech World', 'tech@world.com'),
(6, 'Style Hub', 'style@hub.com');

INSERT INTO products (product_id, product_name, category_id, supplier_id, price) VALUES
(101, 'Smartphone',   1, 1, 299.99),
(102, 'Laptop',       1, 5, 799.50),
(103, 'T-Shirt',      2, 6, 19.99),
(104, 'Novel',        3, 3, 9.50),
(105, 'Sofa',         4, 2, 450.00),
(106, 'Football',     5, 2, 25.00);

INSERT INTO inventory (inventory_id, product_id, quantity, last_updated) VALUES
(1, 101, 50, '2025-09-01'),
(2, 102, 30, '2025-09-01'),
(3, 103, 100, '2025-09-01'),
(4, 104, 70, '2025-09-01'),
(5, 105, 10, '2025-09-01'),
(6, 106, 60, '2025-09-01');

INSERT INTO orders (order_id, product_id, order_date, quantity_ordered, total_price) VALUES
(1, 101, '2025-09-02', 2, 599.98),
(2, 102, '2025-09-02', 1, 799.50),
(3, 103, '2025-09-03', 5, 99.95),
(4, 104, '2025-09-03', 3, 28.50),
(5, 105, '2025-09-04', 1, 450.00),
(6, 106, '2025-09-04', 4, 100.00);

-- Retrieve a list of all products along with their category names.
SELECT p.product_name,c.category_name
FROM products as p
INNER JOIN categories as c 
ON p.category_id=c.category_id;

-- Include only products that belong to a category.
SELECT p.product_name,c.category_name
FROM products as p
INNER JOIN categories as c 
ON p.category_id=c.category_id
WHERE lower(c.category_name)='electronics';

-- Retrieve all products along with their supplier names and Include products even if they don’t have a supplier assigned.
SELECT p.product_name,s.supplier_name
FROM products as p
LEFT JOIN suppliers as s
ON p.supplier_id = s.supplier_id;

-- Retrieve all suppliers and the products they supply. Include suppliers even if they currently supply no products.
SELECT s.supplier_name,p.product_name
FROM products as p
RIGHT JOIN suppliers as s
ON p.supplier_id = s.supplier_id;

-- Retrieve all products and all suppliers in a single list, showing which products are supplied by which suppliers. Include products with no suppliers and suppliers with no products.
SELECT p.product_name,s.supplier_name
FROM products AS p
LEFT JOIN suppliers as s
ON p.supplier_id = s.supplier_id
UNION 
SELECT p.product_name,s.supplier_name
FROM products as p
RIGHT JOIN suppliers as s
ON p.supplier_id = s.supplier_id;

-- Retrieve product name, supplier name, and current stock quantity.Include only products that are in stock.
SELECT p.product_name,s.supplier_name,i.quantity
FROM products as p
INNER JOIN suppliers as s
ON p.supplier_id=s.supplier_id
JOIN inventory as i
ON i.product_id=p.product_id
WHERE i.quantity>0;

-- Retrieve product name, total quantity ordered, and total revenue per product.Sort the result by total revenue descending.
SELECT p.product_name,SUM(o.quantity_ordered) AS quantity,SUM(o.total_price) AS total_revenue
FROM products AS p
JOIN orders as o
ON p.product_id=o.product_id
GROUP BY(p.product_name)
ORDER BY total_revenue DESC;

-- Retrieve order details: order\_id, order\_date, product\_name, category\_name, supplier\_name, quantity\_ordered, total\_price. Include all orders in the result.
SELECT o.order_id,o.order_date,p.product_name,c.category_name,s.supplier_name,o.quantity_ordered,o.total_price
FROM orders as o
LEFT JOIN products as p
ON o.product_id=p.product_id
LEFT JOIN suppliers as s
ON s.supplier_id=p.supplier_id
LEFT JOIN categories as c
ON c.category_id=p.category_id;

-- Retrieve product name, supplier name, and quantity in stock for products with less than 10 items in inventory.
SELECT p.product_name,s.supplier_name,i.quantity
FROM products as p
LEFT JOIN suppliers as s
ON p.supplier_id=s.supplier_id
LEFT JOIN inventory as i
ON p.product_id=i.product_id
WHERE i.quantity<10;

-- Find suppliers who supply products in multiple categories.
SELECT s.supplier_name,COUNT(DISTINCT p.category_id) as category_count
FROM products as p
JOIN suppliers as s
ON s.supplier_id=p.supplier_id
GROUP BY s.supplier_id,s.supplier_name
HAVING COUNT(DISTINCT p.category_id)>1;

-- Find products that have never been ordered.
SELECT p.product_name
FROM products as p
LEFT JOIN orders as o
ON p.product_id=o.product_id
WHERE o.order_id IS NULL;

-- Find the category with the highest total sales.
SELECT c.category_id,c.category_name,SUM(o.quantity_ordered) as total_sales
FROM products as p
JOIN orders as o
ON o.product_id=p.product_id
JOIN categories as c
ON c.category_id=p.category_id
GROUP BY c.category_id,c.category_name
ORDER BY total_sales DESC
LIMIT 1;



