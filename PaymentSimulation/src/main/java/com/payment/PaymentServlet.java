package com.payment;

import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/processPayment")
public class PaymentServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    
    @Override
    public void init() throws ServletException {
        super.init();
        // Test database connection on startup
        try {
            Connection conn = DBConnection.getConnection();
            if (conn != null) {
                System.out.println("✅ Database connection pool initialized");
                conn.close();
            }
        } catch (SQLException e) {
            System.err.println("❌ Failed to initialize database connection");
            e.printStackTrace();
        }
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String amountStr = request.getParameter("amount");
        String fromAccount = request.getParameter("fromAccount");
        String toAccount = request.getParameter("toAccount");
        
        double amount = 0;
        String message = "";
        String status = "FAILED";
        
        // Validate inputs
        if (fromAccount == null || toAccount == null || fromAccount.equals(toAccount)) {
            message = "Invalid account selection. Source and destination must be different.";
            request.setAttribute("message", message);
            request.setAttribute("status", status);
            request.setAttribute("amount", amountStr);
            request.setAttribute("fromAccount", fromAccount);
            request.setAttribute("toAccount", toAccount);
            request.getRequestDispatcher("result.jsp").forward(request, response);
            return;
        }
        
        try {
            amount = Double.parseDouble(amountStr);
            if (amount <= 0) {
                throw new NumberFormatException("Amount must be positive");
            }
        } catch (NumberFormatException e) {
            message = "Invalid amount format. Please enter a valid positive number.";
            request.setAttribute("message", message);
            request.setAttribute("status", status);
            request.setAttribute("amount", amountStr);
            request.setAttribute("fromAccount", fromAccount);
            request.setAttribute("toAccount", toAccount);
            request.getRequestDispatcher("result.jsp").forward(request, response);
            return;
        }
        
        Connection con = null;
        
        try {
            // Get database connection
            con = DBConnection.getConnection();
            
            if (con == null) {
                throw new SQLException("Could not establish database connection");
            }
            
            // Disable auto-commit for transaction management
            con.setAutoCommit(false);
            
            // Check current balances
            double fromBalance = getBalance(con, fromAccount);
            double toBalance = getBalance(con, toAccount);
            
            System.out.println("Initial balances - " + fromAccount + ": $" + fromBalance + 
                             ", " + toAccount + ": $" + toBalance);
            
            // Check if source account has sufficient balance
            if (fromBalance < amount) {
                message = String.format("Insufficient balance! %s has $%.2f, but you tried to transfer $%.2f", 
                                       fromAccount, fromBalance, amount);
                status = "FAILED";
                con.rollback();
            } else {
                // Deduct from source account
                boolean deducted = updateBalance(con, fromAccount, -amount);
                
                if (deducted) {
                    // Add to destination account
                    boolean added = updateBalance(con, toAccount, amount);
                    
                    if (added) {
                        // If both operations succeed, commit the transaction
                        con.commit();
                        message = String.format("✅ Payment successful! $%.2f transferred from %s to %s", 
                                               amount, fromAccount, toAccount);
                        status = "SUCCESS";
                        
                        // Get updated balances
                        double newFromBalance = getBalance(con, fromAccount);
                        double newToBalance = getBalance(con, toAccount);
                        
                        request.setAttribute("fromBalance", newFromBalance);
                        request.setAttribute("toBalance", newToBalance);
                        
                        System.out.println("Transaction committed successfully");
                        System.out.println("New balances - " + fromAccount + ": $" + newFromBalance + 
                                         ", " + toAccount + ": $" + newToBalance);
                    } else {
                        message = "Failed to credit destination account";
                        status = "FAILED";
                        con.rollback();
                        System.out.println("Transaction rolled back - failed to credit");
                    }
                } else {
                    message = "Failed to debit source account";
                    status = "FAILED";
                    con.rollback();
                    System.out.println("Transaction rolled back - failed to debit");
                }
            }
            
        } catch (SQLException e) {
            message = "Transaction failed: " + e.getMessage();
            status = "FAILED";
            System.err.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
            
            try {
                if (con != null) {
                    con.rollback();
                    System.out.println("Transaction rolled back due to error");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                if (con != null) {
                    con.setAutoCommit(true);
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        // Set attributes for JSP
        request.setAttribute("amount", amount);
        request.setAttribute("fromAccount", fromAccount);
        request.setAttribute("toAccount", toAccount);
        request.setAttribute("message", message);
        request.setAttribute("status", status);
        
        // Get original balances for display in case of failure
        if ("FAILED".equals(status)) {
            try (Connection testCon = DBConnection.getConnection()) {
                double originalFromBalance = getBalance(testCon, fromAccount);
                double originalToBalance = getBalance(testCon, toAccount);
                request.setAttribute("originalFromBalance", originalFromBalance);
                request.setAttribute("originalToBalance", originalToBalance);
            } catch (SQLException e) {
                // Ignore, use defaults
            }
        }
        
        request.getRequestDispatcher("result.jsp").forward(request, response);
    }
    
    private double getBalance(Connection con, String accountName) throws SQLException {
        String query = "SELECT balance FROM accounts WHERE name = ?";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, accountName);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("balance");
                } else {
                    throw new SQLException("Account not found: " + accountName);
                }
            }
        }
    }
    
    private boolean updateBalance(Connection con, String accountName, double amount) throws SQLException {
        String update = "UPDATE accounts SET balance = balance + ? WHERE name = ?";
        try (PreparedStatement ps = con.prepareStatement(update)) {
            ps.setDouble(1, amount);
            ps.setString(2, accountName);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        }
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.sendRedirect("index.html");
    }
}