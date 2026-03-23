<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Employee Management - Spring MVC</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            margin: 0;
            padding: 20px;
            min-height: 100vh;
        }
        .container {
            max-width: 800px;
            margin: 50px auto;
            background: white;
            padding: 30px;
            border-radius: 15px;
            box-shadow: 0 10px 40px rgba(0,0,0,0.1);
        }
        h1 {
            color: #333;
            text-align: center;
            margin-bottom: 30px;
            padding-bottom: 10px;
            border-bottom: 3px solid #667eea;
        }
        .mvc-flow {
            background: #f8f9fa;
            padding: 20px;
            border-radius: 10px;
            margin: 20px 0;
        }
        .step {
            margin: 15px 0;
            padding: 15px;
            background: white;
            border-left: 4px solid #667eea;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.05);
        }
        .step-number {
            background: #667eea;
            color: white;
            width: 30px;
            height: 30px;
            display: inline-block;
            text-align: center;
            line-height: 30px;
            border-radius: 50%;
            margin-right: 10px;
        }
        .nav-links {
            text-align: center;
            margin-top: 30px;
        }
        .nav-links a {
            display: inline-block;
            margin: 10px;
            padding: 12px 25px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            text-decoration: none;
            border-radius: 25px;
            font-size: 16px;
            transition: transform 0.3s, box-shadow 0.3s;
        }
        .nav-links a:hover {
            transform: translateY(-2px);
            box-shadow: 0 5px 20px rgba(102, 126, 234, 0.4);
        }
        .features {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 15px;
            margin-top: 30px;
        }
        .feature {
            text-align: center;
            padding: 15px;
            background: #e3f2fd;
            border-radius: 8px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>🏢 Employee Management System</h1>
        
        <div class="mvc-flow">
            <h3>📋 Spring MVC Flow (XML-less Configuration)</h3>
            
            <div class="step">
                <span class="step-number">1</span>
                <strong>Request:</strong> User clicks link → http://localhost:8080/EmployeeSpringMVC/employee
            </div>
            
            <div class="step">
                <span class="step-number">2</span>
                <strong>DispatcherServlet:</strong> Captures all requests (mapped to "/")
            </div>
            
            <div class="step">
                <span class="step-number">3</span>
                <strong>Handler Mapping:</strong> Finds @RequestMapping("/employee") in EmployeeController
            </div>
            
            <div class="step">
                <span class="step-number">4</span>
                <strong>Controller:</strong> showEmployee() method creates Employee object and adds to Model
            </div>
            
            <div class="step">
                <span class="step-number">5</span>
                <strong>View Resolver:</strong> Maps "employee" to /WEB-INF/views/employee.jsp
            </div>
            
            <div class="step">
                <span class="step-number">6</span>
                <strong>View:</strong> JSP renders employee data using ${emp} expression
            </div>
            
            <div class="step">
                <span class="step-number">7</span>
                <strong>Response:</strong> HTML page displayed in browser
            </div>
        </div>
        
        <div class="nav-links">
            <a href="${pageContext.request.contextPath}/employee">👤 View Single Employee</a>
            <a href="${pageContext.request.contextPath}/employee2">🔄 View Using ModelAndView</a>
            <a href="${pageContext.request.contextPath}/employees">👥 View All Employees</a>
            <a href="${pageContext.request.contextPath}/employeeById?id=102">🔍 Search Employee (ID=102)</a>
        </div>
        
        <div class="features">
            <div class="feature">✅ Java-based Configuration</div>
            <div class="feature">✅ No web.xml or XML files</div>
            <div class="feature">✅ Annotation-driven MVC</div>
            <div class="feature">✅ Clean Separation of Concerns</div>
        </div>
        
        <p style="text-align: center; margin-top: 20px; color: #666;">
            <strong>WebInitializer.java</strong> replaces web.xml • 
            <strong>@EnableWebMvc</strong> enables MVC • 
            <strong>View Resolver</strong> maps JSPs
        </p>
    </div>
</body>
</html>