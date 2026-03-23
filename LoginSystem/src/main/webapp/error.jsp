<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Error - Login System</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .container {
            width: 100%;
            max-width: 500px;
            padding: 20px;
        }

        .error-box {
            background: white;
            border-radius: 10px;
            padding: 40px;
            box-shadow: 0 10px 25px rgba(0,0,0,0.1);
            text-align: center;
        }

        .error-icon {
            font-size: 80px;
            color: #e53e3e;
            margin-bottom: 20px;
        }

        h2 {
            color: #333;
            margin-bottom: 15px;
        }

        .error-message {
            background: #fee;
            color: #c33;
            padding: 15px;
            border-radius: 5px;
            margin: 20px 0;
            border-left: 4px solid #c33;
            text-align: left;
        }

        .error-details {
            background: #f7fafc;
            padding: 15px;
            border-radius: 5px;
            margin: 20px 0;
            text-align: left;
            font-family: monospace;
            color: #718096;
        }

        .btn {
            display: inline-block;
            padding: 12px 24px;
            background: #667eea;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            font-weight: 600;
            transition: background 0.3s;
            margin-top: 20px;
        }

        .btn:hover {
            background: #5a67d8;
        }

        .btn-home {
            background: #48bb78;
        }

        .btn-home:hover {
            background: #38a169;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="error-box">
            <div class="error-icon">⚠️</div>
            <h2>Oops! Something Went Wrong</h2>
            
            <div class="error-message">
                <strong>Error:</strong> 
                <% if (exception != null) { %>
                    <%= exception.getMessage() %>
                <% } else { %>
                    An unexpected error occurred.
                <% } %>
            </div>
            
            <div class="error-details">
                <strong>Details:</strong><br>
                <% if (request.getAttribute("javax.servlet.error.status_code") != null) { %>
                    Status Code: <%= request.getAttribute("javax.servlet.error.status_code") %><br>
                <% } %>
                <% if (request.getAttribute("javax.servlet.error.request_uri") != null) { %>
                    Request URI: <%= request.getAttribute("javax.servlet.error.request_uri") %><br>
                <% } %>
                Timestamp: <%= new java.util.Date() %>
            </div>
            
            <a href="login.html" class="btn btn-home">Go to Login Page</a>
        </div>
    </div>
</body>
</html>