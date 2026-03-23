package com.example;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("")
public class IndexServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Student Dashboard</title>");
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif; margin: 40px; text-align: center; }");
        out.println("h2 { color: #333; }");
        out.println("a { display: inline-block; padding: 10px 20px; background: #4CAF50; color: white; text-decoration: none; border-radius: 5px; margin-top: 20px; }");
        out.println("a:hover { background: #45a049; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h2>🎓 Student Dashboard</h2>");
        out.println("<p>Welcome to the Student Management System</p>");
        out.println("<a href='dashboard'>Go to Dashboard</a>");
        out.println("</body>");
        out.println("</html>");
    }
}