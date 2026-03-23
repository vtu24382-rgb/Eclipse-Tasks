<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.order.OrderServlet.OrderDetail" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Customer Order History</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <div class="container">
        <h2>📋 Customer Order History</h2>
        
        <a href="index.jsp" class="btn-small">← Back to Home</a>
        
        <% 
        List<OrderDetail> orders = (List<OrderDetail>)request.getAttribute("orders");
        String message = (String)request.getAttribute("message");
        
        if(message != null) {
        %>
            <div class="error"><%= message %></div>
        <% 
        } else if(orders != null && !orders.isEmpty()) {
        %>
            <table class="order-table">
                <thead>
                    <tr>
                        <th>Customer</th>
                        <th>Product</th>
                        <th>Price (₹)</th>
                        <th>Quantity</th>
                        <th>Total (₹)</th>
                    </tr>
                </thead>
                <tbody>
                <% 
                double grandTotal = 0;
                for(OrderDetail order : orders) { 
                    grandTotal += order.getTotal();
                %>
                    <tr>
                        <td><%= order.getCustomerName() %></td>
                        <td><%= order.getProductName() %></td>
                        <td class="amount">₹<%= String.format("%,.2f", order.getPrice()) %></td>
                        <td class="center"><%= order.getQuantity() %></td>
                        <td class="amount">₹<%= String.format("%,.2f", order.getTotal()) %></td>
                    </tr>
                <% } %>
                </tbody>
                <tfoot>
                    <tr>
                        <td colspan="4" class="total-label"><strong>Grand Total:</strong></td>
                        <td class="total-amount"><strong>₹<%= String.format("%,.2f", grandTotal) %></strong></td>
                    </tr>
                </tfoot>
            </table>
        <% } else { %>
            <p class="no-data">No orders found.</p>
        <% } %>
    </div>
</body>
</html>