<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String username = (String) session.getAttribute("username");
    String fullName = (String) session.getAttribute("fullName");
    
    if (username == null) {
        response.sendRedirect("login.html");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard</title>
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
            max-width: 600px;
            padding: 20px;
        }

        .dashboard-box {
            background: white;
            border-radius: 10px;
            padding: 40px;
            box-shadow: 0 10px 25px rgba(0,0,0,0.1);
        }

        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 30px;
            padding-bottom: 20px;
            border-bottom: 2px solid #edf2f7;
        }

        .header h2 {
            color: #333;
            margin: 0;
        }

        .logout-btn {
            padding: 8px 16px;
            background: #e53e3e;
            color: white;
            border: none;
            border-radius: 5px;
            font-size: 14px;
            font-weight: 600;
            cursor: pointer;
            transition: background 0.3s;
            text-decoration: none;
        }

        .logout-btn:hover {
            background: #c53030;
        }

        .welcome-message {
            background: #f7fafc;
            padding: 30px;
            border-radius: 5px;
            margin-bottom: 20px;
            text-align: center;
        }

        .welcome-message h3 {
            color: #4a5568;
            margin-bottom: 15px;
            font-size: 24px;
        }

        .welcome-message p {
            color: #718096;
            font-size: 16px;
            margin: 10px 0;
        }

        .content {
            padding: 20px;
            background: #edf2f7;
            border-radius: 5px;
        }

        .content h3 {
            color: #4a5568;
            margin-bottom: 10px;
        }

        .content p {
            color: #718096;
        }
        
        .user-icon {
            font-size: 80px;
            margin-bottom: 20px;
            color: #667eea;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="dashboard-box">
            <div class="header">
                <h2>Dashboard</h2>
                <a href="logout.jsp" class="logout-btn">Logout</a>
            </div>
            
            <div class="welcome-message">
                <div class="user-icon">👤</div>
                <h3>Welcome, <%= fullName != null ? fullName : username %>!</h3>
                <p>You have successfully logged in.</p>
                <p><strong>Username:</strong> <%= username %></p>
            </div>

            <div class="content">
                <h3>Dashboard Content</h3>
                <p>This is your protected dashboard area. Only authenticated users can see this.</p>
                <p>You can add your application features here.</p>
            </div>
        </div>
    </div>
</body>
</html>