package com.order;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/orders")
public class OrderServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        if ("analytics".equals(action)) {
            showAnalytics(request, response);
        } else {
            showOrderHistory(request, response);
        }
    }
    
    private void showOrderHistory(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        List<OrderDetail> orderDetails = new ArrayList<>();
        String message = null;
        
        try (Connection con = DBConnection.getConnection()) {
            
            String query = 
                "SELECT c.customer_name, p.product_name, p.price, o.quantity, " +
                "(p.price * o.quantity) AS total " +
                "FROM Orders o " +
                "JOIN Customers c ON o.customer_id = c.customer_id " +
                "JOIN Products p ON o.product_id = p.product_id " +
                "ORDER BY o.order_date DESC";
            
            try (Statement st = con.createStatement();
                 ResultSet rs = st.executeQuery(query)) {
                
                while(rs.next()) {
                    OrderDetail detail = new OrderDetail();
                    detail.setCustomerName(rs.getString("customer_name"));
                    detail.setProductName(rs.getString("product_name"));
                    detail.setPrice(rs.getDouble("price"));
                    detail.setQuantity(rs.getInt("quantity"));
                    detail.setTotal(rs.getDouble("total"));
                    orderDetails.add(detail);
                }
            }
            
            request.setAttribute("orders", orderDetails);
            
        } catch(Exception e) {
            message = "Error: " + e.getMessage();
            request.setAttribute("message", message);
        }
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("orders.jsp");
        dispatcher.forward(request, response);
    }
    
    private void showAnalytics(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try (Connection con = DBConnection.getConnection()) {
            
            // Subquery: Find highest quantity order
            String highestOrderQuery = 
                "SELECT o.*, c.customer_name, p.product_name, p.price, " +
                "(p.price * o.quantity) AS total_value " +
                "FROM Orders o " +
                "JOIN Customers c ON o.customer_id = c.customer_id " +
                "JOIN Products p ON o.product_id = p.product_id " +
                "WHERE o.quantity = (SELECT MAX(quantity) FROM Orders)";
            
            try (Statement st = con.createStatement();
                 ResultSet rs = st.executeQuery(highestOrderQuery)) {
                
                if(rs.next()) {
                    OrderDetail highestOrder = new OrderDetail();
                    highestOrder.setCustomerName(rs.getString("customer_name"));
                    highestOrder.setProductName(rs.getString("product_name"));
                    highestOrder.setPrice(rs.getDouble("price"));
                    highestOrder.setQuantity(rs.getInt("quantity"));
                    highestOrder.setTotal(rs.getDouble("total_value"));
                    request.setAttribute("highestOrder", highestOrder);
                }
            }
            
            // Find most active customer
            String activeCustomerQuery = 
                "SELECT c.customer_name, c.email, COUNT(*) AS total_orders, " +
                "SUM(p.price * o.quantity) AS total_spent " +
                "FROM Orders o " +
                "JOIN Customers c ON o.customer_id = c.customer_id " +
                "JOIN Products p ON o.product_id = p.product_id " +
                "GROUP BY o.customer_id " +
                "ORDER BY total_orders DESC, total_spent DESC " +
                "LIMIT 1";
            
            try (Statement st = con.createStatement();
                 ResultSet rs = st.executeQuery(activeCustomerQuery)) {
                
                if(rs.next()) {
                    request.setAttribute("activeCustomer", rs.getString("customer_name"));
                    request.setAttribute("activeCustomerOrders", rs.getInt("total_orders"));
                    request.setAttribute("activeCustomerSpent", rs.getDouble("total_spent"));
                }
            }
            
        } catch(Exception e) {
            request.setAttribute("message", "Error: " + e.getMessage());
        }
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("analytics.jsp");
        dispatcher.forward(request, response);
    }
    
    // Inner class for order details
    public static class OrderDetail {
        private String customerName;
        private String productName;
        private double price;
        private int quantity;
        private double total;
        
        // Getters and setters
        public String getCustomerName() { return customerName; }
        public void setCustomerName(String customerName) { this.customerName = customerName; }
        
        public String getProductName() { return productName; }
        public void setProductName(String productName) { this.productName = productName; }
        
        public double getPrice() { return price; }
        public void setPrice(double price) { this.price = price; }
        
        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }
        
        public double getTotal() { return total; }
        public void setTotal(double total) { this.total = total; }
    }
}