

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/deleteCategory")
public class DeleteCategory extends HttpServlet {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/expensemanager";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "Latari1234!";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        boolean isDeleted = false;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
                String query = "DELETE FROM categories WHERE id = ?";
                try (PreparedStatement ps = con.prepareStatement(query)) {
                    ps.setInt(1, Integer.parseInt(id));
                    int rowsAffected = ps.executeUpdate();
                    isDeleted = rowsAffected > 0; // Check if deletion was successful
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Redirect based on deletion result
        if (isDeleted) {
            resp.sendRedirect(req.getContextPath() + "/viewCategories.jsp");
        } else {
            resp.getWriter().write("<h3>Error: Unable to delete category.</h3>");
            resp.getWriter().write("<a href='" + req.getContextPath() + "/viewCategories.jsp'>Back to Categories</a>");
        }
    }
}
