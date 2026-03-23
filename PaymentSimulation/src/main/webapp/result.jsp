<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Payment Result</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            margin: 0;
            padding: 20px;
            min-height: 100vh;
        }
        
        .container {
            max-width: 600px;
            margin: 50px auto;
            background: white;
            padding: 30px;
            border-radius: 15px;
            box-shadow: 0 10px 40px rgba(0,0,0,0.1);
        }
        
        h1 {
            text-align: center;
            color: #333;
            margin-bottom: 30px;
        }
        
        .result-box {
            padding: 25px;
            border-radius: 10px;
            margin: 20px 0;
            text-align: center;
        }
        
        .success {
            background: #d4edda;
            border: 2px solid #28a745;
            color: #155724;
        }
        
        .failed {
            background: #f8d7da;
            border: 2px solid #dc3545;
            color: #721c24;
        }
        
        .transaction-details {
            background: #f8f9fa;
            padding: 20px;
            border-radius: 8px;
            margin: 20px 0;
        }
        
        .transaction-details p {
            margin: 10px 0;
            font-size: 16px;
            color: #555;
        }
        
        .balance-info {
            background: #e3f2fd;
            padding: 15px;
            border-radius: 8px;
            margin: 20px 0;
            border-left: 4px solid #2196f3;
        }
        
        .balance-info p {
            margin: 8px 0;
            font-size: 16px;
        }
        
        .balance {
            font-weight: bold;
            color: #28a745;
            font-size: 18px;
        }
        
        .button {
            display: inline-block;
            padding: 12px 30px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            text-decoration: none;
            border-radius: 8px;
            font-size: 16px;
            font-weight: bold;
            margin-top: 20px;
            transition: transform 0.3s, box-shadow 0.3s;
        }
        
        .button:hover {
            transform: translateY(-2px);
            box-shadow: 0 5px 20px rgba(102, 126, 234, 0.4);
        }
        
        .icon {
            font-size: 50px;
            margin-bottom: 15px;
        }
        
        .error-details {
            background: #fff3cd;
            border: 1px solid #ffeeba;
            color: #856404;
            padding: 15px;
            border-radius: 8px;
            margin: 15px 0;
            font-family: monospace;
            text-align: left;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>💳 Payment Result</h1>
        
        <% 
            String status = (String)request.getAttribute("status");
            String message = (String)request.getAttribute("message");
            Object amountObj = request.getAttribute("amount");
            String amount = amountObj != null ? amountObj.toString() : "N/A";
            String fromAccount = (String)request.getAttribute("fromAccount");
            String toAccount = (String)request.getAttribute("toAccount");
            Double fromBalance = (Double)request.getAttribute("fromBalance");
            Double toBalance = (Double)request.getAttribute("toBalance");
            Double originalFromBalance = (Double)request.getAttribute("originalFromBalance");
            Double originalToBalance = (Double)request.getAttribute("originalToBalance");
        %>
        
        <div class="result-box <%= "SUCCESS".equals(status) ? "success" : "failed" %>">
            <div class="icon">
                <%= "SUCCESS".equals(status) ? "✅" : "❌" %>
            </div>
            <h2><%= "SUCCESS".equals(status) ? "Payment Successful!" : "Payment Failed!" %></h2>
            <p><%= message != null ? message : "No message available" %></p>
        </div>
        
        <% if(message != null && message.contains("No suitable driver found")) { %>
        <div class="error-details">
            <h3>🔧 Troubleshooting Steps:</h3>
            <ol>
                <li>Verify mysql-connector-j-9.2.0.jar is in <strong>WEB-INF/lib</strong> folder</li>
                <li>Clean and rebuild the project</li>
                <li>Restart Tomcat server</li>
                <li>Check if the JAR file is corrupted (try re-downloading)</li>
            </ol>
            <p><strong>Current JAR location should be:</strong> /WEB-INF/lib/mysql-connector-j-9.2.0.jar</p>
        </div>
        <% } %>
        
        <div class="transaction-details">
            <h3>📋 Transaction Details:</h3>
            <p><strong>Amount:</strong> $<%= amount %></p>
            <p><strong>From:</strong> <%= fromAccount != null ? fromAccount : "N/A" %></p>
            <p><strong>To:</strong> <%= toAccount != null ? toAccount : "N/A" %></p>
        </div>
        
        <% if("SUCCESS".equals(status) && fromBalance != null && toBalance != null) { %>
        <div class="balance-info">
            <h3>💰 Updated Balances:</h3>
            <p><%= fromAccount %> Balance: <span class="balance">$<%= String.format("%.2f", fromBalance) %></span></p>
            <p><%= toAccount %> Balance: <span class="balance">$<%= String.format("%.2f", toBalance) %></span></p>
        </div>
        
        <div class="balance-info" style="background: #d4edda; border-left-color: #28a745;">
            <p>✅ Transaction Committed Successfully</p>
        </div>
        
        <% } else if("FAILED".equals(status)) { 
            if(originalFromBalance != null && originalToBalance != null) {
        %>
        <div class="balance-info" style="background: #f8d7da; border-left-color: #dc3545;">
            <h3>⚠️ Transaction Rolled Back</h3>
            <p>No changes were made to any accounts</p>
            <p>Current balances preserved:</p>
            <p><%= fromAccount %>: $<%= String.format("%.2f", originalFromBalance) %></p>
            <p><%= toAccount %>: $<%= String.format("%.2f", originalToBalance) %></p>
        </div>
        <% } else { %>
        <div class="balance-info" style="background: #f8d7da; border-left-color: #dc3545;">
            <h3>⚠️ Transaction Rolled Back</h3>
            <p>No changes were made to any accounts</p>
            <p>Original balances preserved:</p>
            <p>UserAccount: $5000 | MerchantAccount: $1000</p>
        </div>
        <% } } %>
        
        <div style="text-align: center;">
            <a href="index.html" class="button">Make Another Payment</a>
        </div>
    </div>
</body>
</html>