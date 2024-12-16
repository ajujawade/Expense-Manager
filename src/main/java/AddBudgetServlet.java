import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;

@WebServlet("/AddBudgetServlet")
public class AddBudgetServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the current logged-in user
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        if (username == null) {
            response.sendRedirect("login.jsp?error=Please login to continue.");
            return;
        }

        // Get form data
        String amount = request.getParameter("amount");
        int month = Integer.parseInt(request.getParameter("month"));
        int year = Integer.parseInt(request.getParameter("year"));

        // You should probably validate the input here

        // Get user ID from the database (assuming you have a User table)
        int userId = getUserId(username); // A helper method to fetch user ID

        if (userId == -1) {
            // Handle error - user not found
            response.sendRedirect("dashboard.jsp?error=User not found.");
            return;
        }

        // Save the budget to the database
        boolean success = saveBudget(userId, month, year, amount);

        if (success) {
            response.sendRedirect("dashboard.jsp?success=Budget added successfully.");
        } else {
            response.sendRedirect("dashboard.jsp?error=Failed to add budget.");
        }
    }

    private int getUserId(String username) {
        // Database logic to get the user ID from the 'users' table
        int userId = -1;
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/your_db", "root", "password");
             PreparedStatement ps = conn.prepareStatement("SELECT id FROM users WHERE username = ?")) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                userId = rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userId;
    }

    private boolean saveBudget(int userId, int month, int year, String amount) {
        // Save the budget to the 'budgets' table
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/your_db", "root", "password");
             PreparedStatement ps = conn.prepareStatement("INSERT INTO budgets (user_id, month, year, amount) VALUES (?, ?, ?, ?)")) {
            ps.setInt(1, userId);
            ps.setInt(2, month);
            ps.setInt(3, year);
            ps.setBigDecimal(4, new java.math.BigDecimal(amount));
            
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
