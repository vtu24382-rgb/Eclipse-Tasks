<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Employee Details</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            margin: 0;
            padding: 20px;
            min-height: 100vh;
        }
        .container {
            max-width: 500px;
            margin: 50px auto;
            background: white;
            padding: 30px;
            border-radius: 15px;
            box-shadow: 0 10px 40px rgba(0,0,0,0.1);
        }
        h2 {
            color: #333;
            text-align: center;
            margin-bottom: 30px;
            padding-bottom: 10px;
            border-bottom: 3px solid #667eea;
        }
        .emp-detail {
            margin: 15px 0;
            padding: 10px;
            background: #f8f9fa;
            border-radius: 8px;
        }
        .label {
            font-weight: bold;
            color: #555;
            display: inline-block;
            width: 120px;
        }
        .value {
            color: #333;
            font-size: 16px;
        }
        .nav-links {
            text-align: center;
            margin-top: 25px;
        }
        .nav-links a {
            display: inline-block;
            margin: 5px 10px;
            padding: 8px 15px;
            background: #667eea;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            transition: background 0.3s;
        }
        .nav-links a:hover {
            background: #764ba2;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>👤 Employee Details</h2>
        
        <div class="emp-detail">
            <span class="label">Employee ID:</span>
            <span class="value">${emp.id}</span>
        </div>
        
        <div class="emp-detail">
            <span class="label">Name:</span>
            <span class="value">${emp.name}</span>
        </div>
        
        <div class="emp-detail">
            <span class="label">Department:</span>
            <span class="value">${emp.department}</span>
        </div>
        
        <div class="emp-detail">
            <span class="label">Salary:</span>
            <span class="value">₹${emp.salary}</span>
        </div>
        
        <div class="emp-detail">
            <span class="label">Email:</span>
            <span class="value">${emp.email}</span>
        </div>
        
        <div class="nav-links">
            <a href="${pageContext.request.contextPath}/">🏠 Home</a>
            <a href="${pageContext.request.contextPath}/employee">🔄 Refresh</a>
            <a href="${pageContext.request.contextPath}/employees">👥 All Employees</a>
        </div>
    </div>
</body>
</html>