

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@WebServlet("/regForm")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2, // 2MB
    maxFileSize = 1024 * 1024 * 10,      // 10MB
    maxRequestSize = 1024 * 1024 * 50    // 50MB
)
public class Register extends HttpServlet {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/expensemanager";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "Latari1234!";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        

  

        try {
            // Load MySQL Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish connection
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {

                // Check if the username already exists
                String checkUserQuery = "SELECT * FROM users WHERE username = ?";
                try (PreparedStatement psCheck = conn.prepareStatement(checkUserQuery)) {
                    psCheck.setString(1, username);
                    ResultSet rs = psCheck.executeQuery();
                    if (rs.next()) {
                        out.print("<h3 style='color:red;'>Username already exists. Please choose another.</h3>");
                        RequestDispatcher rd = req.getRequestDispatcher("/register.jsp");
                        rd.include(req, resp);
                        return;
                    }
                }


                // Insert user details into the database
                String insertQuery = "INSERT INTO users (name, username, email, password) VALUES (?, ?, ?, ?)";
                try (PreparedStatement psInsert = conn.prepareStatement(insertQuery)) {
                    psInsert.setString(1, name);
                    psInsert.setString(2, username);
                    psInsert.setString(3, email);
                    psInsert.setString(4, password);
        

                    int result = psInsert.executeUpdate();
                    if (result > 0) {
                        out.print("<h3 style='color:green;'>User Registered Successfully!</h3>");
                        resp.sendRedirect("login.jsp");
                    } else {
                        out.print("<h3 style='color:red;'>User could not be registered. Please try again.</h3>");
                        RequestDispatcher rd = req.getRequestDispatcher("/register.jsp");
                        rd.include(req, resp);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.print("<h3 style='color:red;'>An error occurred: " + e.getMessage() + "</h3>");
            RequestDispatcher rd = req.getRequestDispatcher("/register.jsp");
            rd.include(req, resp);
        }
    }

    private String extractFileName(Part part) {
        String contentDisposition = part.getHeader("content-disposition");
        for (String content : contentDisposition.split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf("=") + 2, content.length() - 1);
            }
        }
        return null;
    }
}
