package in.sp.backend;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/loginForm")
public class Login extends HttpServlet {
    
    private static final String DB_URL = "jdbc:mysql://localhost:3306/expensemanager";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "Latari1234!";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        // Basic empty field validation
        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            resp.sendRedirect("login.jsp?error=Both fields are required.");
            return;
        }

        try {
            // Load database driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
                String query = "SELECT * FROM users WHERE username = ? AND password = ?";
                
                try (PreparedStatement ps = con.prepareStatement(query)) {
                    ps.setString(1, username);
                    ps.setString(2, password);

                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            // Login successful - set session attributes
                            HttpSession session = req.getSession();
                            session.setAttribute("username", rs.getString("username"));
                            session.setAttribute("email", rs.getString("email"));
                            session.setAttribute("name", rs.getString("name"));

                            // Redirect to categories/dashboard page
                            resp.sendRedirect("dashboard.jsp");
                        } else {
                            // Invalid credentials
                            resp.sendRedirect("login.jsp?error=Invalid username or password.");
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("login.jsp?error=Internal server error. Please try again later.");
        }
    }
}
