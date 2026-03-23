<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.order.OrderServlet.OrderDetail" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Order Analytics</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <div class="container">
        <h2>📊 Order Analytics</h2>
        
        <a href="index.jsp" class="btn-small">← Back to Home</a>
        
        <% 
        OrderDetail highestOrder = (OrderDetail)request.getAttribute("highestOrder");
        String activeCustomer = (String)request.getAttribute("activeCustomer");
        Integer activeCustomerOrders = (Integer)request.getAttribute("activeCustomerOrders");
        Double activeCustomerSpent = (Double)request.getAttribute("activeCustomerSpent");
        String message = (String)request.getAttribute("message");
        
        if(message != null) {
        %>
            <div class="error"><%= message %></div>
        <% } %>
        
        <div class="analytics-cards">
            <% if(highestOrder != null) { %>
            <div class="card">
                <h3>🏆 Highest Value Order</h3>
                <div class="card-content">
                    <p><strong>Customer:</strong> <%= highestOrder.getCustomerName() %></p>
                    <p><strong>Product:</strong> <%= highestOrder.getProductName() %></p>
                    <p><strong>Quantity:</strong> <%= highestOrder.getQuantity() %></p>
                    <p><strong>Total Value:</strong> ₹<%= String.format("%,.2f", highestOrder.getTotal()) %></p>
                </div>
            </div>
            <% } %>
            
            <% if(activeCustomer != null) { %>
            <div class="card">
                <h3>⭐ Most Active Customer</h3>
                <div class="card-content">
                    <p><strong>Name:</strong> <%= activeCustomer %></p>
                    <p><strong>Total Orders:</strong> <%= activeCustomerOrders %></p>
                    <p><strong>Total Spent:</strong> ₹<%= String.format("%,.2f", activeCustomerSpent) %></p>
                </div>
            </div>
            <% } %>
        </div>
        
        <div class="query-info">
            <h3>SQL Queries Used:</h3>
            <pre>
-- JOIN Query for Order History
SELECT c.customer_name, p.product_name, p.price, o.quantity 
FROM Orders o
JOIN Customers c ON o.customer_id = c.customer_id
JOIN Products p ON o.product_id = p.product_id;

-- Subquery for Highest Value Order
SELECT *
FROM Orders
WHERE quantity = (SELECT MAX(quantity) FROM Orders);

-- Query for Most Active Customer
SELECT customer_id, COUNT(*) total_orders
FROM Orders
GROUP BY customer_id
ORDER BY total_orders DESC
LIMIT 1;
            </pre>
        </div>
    </div>
</body>
</html>