package in.sp.backend;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/addCategory")
public class AddCategory extends HttpServlet {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/expensemanager";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "Latari1234!";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        
        String name = req.getParameter("name");
        String type = req.getParameter("type");
        String amountStr = req.getParameter("amount");

        try {
            // Convert amount to a double
            double amount = Double.parseDouble(amountStr);

            // Load JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
                String query = "INSERT INTO categories (name, type, amount) VALUES (?, ?, ?)";

                try (PreparedStatement ps = con.prepareStatement(query)) {
                    ps.setString(1, name);
                    ps.setString(2, type);
                    ps.setDouble(3, amount);

                    int result = ps.executeUpdate();
                    if (result > 0) {
                        resp.sendRedirect("addCategory.jsp?success=Category added successfully.");
                    } else {
                        out.print("<h3 style='color:red'>Failed to add category. Please try again.</h3>");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.print("<h3 style='color:red'>Error: " + e.getMessage() + "</h3>");
        }
    }
}
