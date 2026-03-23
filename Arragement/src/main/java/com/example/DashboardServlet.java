package com.example;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String sort = request.getParameter("sort");
        String dep = request.getParameter("dep");

        try {
            // If no parameters, show only the form
            if (sort == null && dep == null) {
                printHTML(out, null, null, null);
                return;
            }

            // Build query based on parameters
            String query = "SELECT * FROM students";
            String whereClause = "";
            String orderClause = "";

            // Add WHERE clause if department is specified and not "all"
            if (dep != null && !dep.equals("all")) {
                whereClause = " WHERE Dep='" + dep + "'";
            }

            // Add ORDER BY clause if sort is specified
            if (sort != null) {
                if (sort.equals("name")) {
                    orderClause = " ORDER BY Name ASC";
                } else if (sort.equals("date")) {
                    orderClause = " ORDER BY date ASC";
                }
            }

            // Complete query
            query = "SELECT * FROM students" + whereClause + orderClause;

            // Load MySQL driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish connection
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/assigning",
                    "root",
                    "root"
            );

            // Create statement and execute query
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            // Print HTML with results
            printHTML(out, rs, dep, sort);

            // Close connection
            rs.close();
            st.close();
            con.close();

        } catch (ClassNotFoundException e) {
            out.println("<h3 style='color:red'>MySQL JDBC Driver not found!</h3>");
            out.println("<p>" + e.getMessage() + "</p>");
            out.println("<p>Make sure mysql-connector-java.jar is in WEB-INF/lib</p>");
        } catch (SQLException e) {
            out.println("<h3 style='color:red'>Database Error!</h3>");
            out.println("<p>Error: " + e.getMessage() + "</p>");
            out.println("<p>Check if MySQL is running and database 'assigning' exists</p>");
        } catch (Exception e) {
            out.println("<h3 style='color:red'>Error!</h3>");
            out.println("<p>" + e.getMessage() + "</p>");
        }
    }

    private void printHTML(PrintWriter out, ResultSet rs, String selectedDep, String selectedSort) {
        
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Student Dashboard</title>");
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif; margin: 20px; }");
        out.println("h2 { color: #333; }");
        out.println("form { background: #f5f5f5; padding: 15px; border-radius: 5px; margin-bottom: 20px; }");
        out.println("select { padding: 5px; margin: 0 10px; }");
        out.println("input[type='submit'] { padding: 5px 15px; background: #4CAF50; color: white; border: none; border-radius: 3px; cursor: pointer; }");
        out.println("input[type='submit']:hover { background: #45a049; }");
        out.println("table { border-collapse: collapse; width: 100%; }");
        out.println("th { background: #333; color: white; padding: 10px; }");
        out.println("td { padding: 8px; text-align: left; }");
        out.println("tr:nth-child(even) { background: #f2f2f2; }");
        out.println("tr:hover { background: #ddd; }");
        out.println(".count { margin-top: 10px; font-weight: bold; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");

        out.println("<h2>🎓 Student Dashboard</h2>");

        // Form
        out.println("<form action='dashboard'>");
        
        out.println("Sort By:");
        out.println("<select name='sort'>");
        out.println("<option value='name' " + (selectedSort != null && selectedSort.equals("name") ? "selected" : "") + ">Name</option>");
        out.println("<option value='date' " + (selectedSort != null && selectedSort.equals("date") ? "selected" : "") + ">Date of Birth</option>");
        out.println("</select>");

        out.println("Department:");
        out.println("<select name='dep'>");
        out.println("<option value='all' " + (selectedDep == null || selectedDep.equals("all") ? "selected" : "") + ">All Departments</option>");
        out.println("<option value='CSE' " + (selectedDep != null && selectedDep.equals("CSE") ? "selected" : "") + ">CSE</option>");
        out.println("<option value='IT' " + (selectedDep != null && selectedDep.equals("IT") ? "selected" : "") + ">IT</option>");
        out.println("<option value='ECE' " + (selectedDep != null && selectedDep.equals("ECE") ? "selected" : "") + ">ECE</option>");
        out.println("</select>");

        out.println("<input type='submit' value='Show Students'>");
        out.println("</form>");

        // Results table if rs is not null
        if (rs != null) {
            out.println("<h3>Student List</h3>");
            out.println("<table border='1'>");
            out.println("<tr><th>ID</th><th>Name</th><th>Department</th><th>Date of Birth</th></tr>");

            try {
                boolean hasData = false;
                int count = 0;
                
                while (rs.next()) {
                    hasData = true;
                    count++;
                    out.println("<tr>");
                    out.println("<td>" + rs.getInt("id") + "</td>");
                    out.println("<td>" + rs.getString("Name") + "</td>");
                    out.println("<td>" + rs.getString("Dep") + "</td>");
                    out.println("<td>" + rs.getDate("date") + "</td>");
                    out.println("</tr>");
                }

                out.println("</table>");

                if (hasData) {
                    out.println("<p class='count'>Total students found: " + count + "</p>");
                } else {
                    out.println("<p style='color:red;'>No students found for the selected criteria.</p>");
                }
            } catch (SQLException e) {
                out.println("<p style='color:red'>Error reading data: " + e.getMessage() + "</p>");
            }
        }

        out.println("</body>");
        out.println("</html>");
    }
}