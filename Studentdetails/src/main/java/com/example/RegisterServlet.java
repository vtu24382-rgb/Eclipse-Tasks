package com.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private static final String URL = "jdbc:mysql://localhost:3306/studentdetails";
    private static final String USER = "root";
    private static final String PASSWORD = "root";   // 🔥 change to your MySQL password

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String dob = request.getParameter("dob");
        String department = request.getParameter("department");
        String phone = request.getParameter("phone");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);

            String sql = "INSERT INTO studentinfo (name, email, DOB, department, phone) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setDate(3, java.sql.Date.valueOf(dob)); // ✅ Proper Date handling
            ps.setString(4, department);
            ps.setString(5, phone);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                out.println("<h2>Student Registered Successfully!</h2>");
                out.println("<a href='index.jsp'>Go Back</a><br><br>");
                out.println("<a href='view'>View Students</a>");
            } else {
                out.println("<h2>Registration Failed</h2>");
            }

            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace(out);
        }
    }
}