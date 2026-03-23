package com.login;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        // Server-side validation
        if (username == null || username.trim().isEmpty() || 
            password == null || password.trim().isEmpty()) {
            
            response.sendRedirect("login.html?error=empty");
            return;
        }
        
        // Check credentials from database
        boolean isValid = DBConnection.validateUser(username, password);
        
        if (isValid) {
            // Login successful
            HttpSession session = request.getSession();
            session.setAttribute("username", username);
            session.setAttribute("fullName", DBConnection.getUserName(username));
            session.setMaxInactiveInterval(30 * 60); // 30 minutes
            
            response.sendRedirect("dashboard.jsp");
        } else {
            // Login failed
            response.sendRedirect("login.html?error=invalid");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("login.html");
    }
}