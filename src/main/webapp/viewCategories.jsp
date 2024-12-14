<%@ page import="java.sql.*" %>
<%
    String username = (String) session.getAttribute("username");
    if (username == null) {
        response.sendRedirect("login.jsp?error=Please login first.");
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>View Expenses</title>
    <link rel="stylesheet" href="css/viewCategories.css">
    <style>
        /* Simple button styles */
        .action-btn {
            padding: 5px 10px;
            margin: 0 5px;
            border: none;
            cursor: pointer;
            border-radius: 4px;
            font-size: 14px;
            transition: background-color 0.3s;
        }
        .delete-btn {
            background-color: #ff4d4d;
            color: white;
        }
        .delete-btn:hover {
            background-color: #e60000;
        }
        .edit-btn {
            background-color: #4caf50;
            color: white;
        }
        .edit-btn:hover {
            background-color: #45a049;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin: 20px 0;
        }
        th, td {
            padding: 10px;
            text-align: left;
            border: 1px solid #ddd;
        }
        th {
            background-color: #f4f4f4;
        }
        h2, h3 {
            text-align: center;
        }
        a {
            text-decoration: none;
        }
    </style>
</head>
<body>
    <h2>Welcome, <%= username %>!</h2>
    <h3>View Financial Categories</h3>

    <table>
        <tr>
            <th>Category ID</th>
            <th>Category Name</th>
            <th>Type</th>
            <th>Amount</th>
            <th>Actions</th>
        </tr>
        <%
            try {
                // Database connection settings
                String DB_URL = "jdbc:mysql://localhost:3306/expensemanager";
                String DB_USERNAME = "root";
                String DB_PASSWORD = "Latari1234!";
                
                // Load JDBC driver
                Class.forName("com.mysql.cj.jdbc.Driver");

                // Establish connection
                try (Connection con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
                    String query = "SELECT * FROM categories";
                    try (PreparedStatement ps = con.prepareStatement(query)) {
                        try (ResultSet rs = ps.executeQuery()) {
                            while (rs.next()) {
        %>
                                <tr>
                                    <td><%= rs.getInt("id") %></td>
                                    <td><%= rs.getString("name") %></td>
                                    <td><%= rs.getString("type") %></td>
                                    <td><%= rs.getDouble("amount") %></td>
                                    <td>
                                        <a href="deleteCategory?id=<%= rs.getInt("id") %>" 
                                           onclick="return confirm('Are you sure you want to delete this category?');">
                                            <button class="action-btn delete-btn">Delete</button>
                                        </a>
                                        <a href="editCategory.jsp?id=<%= rs.getInt("id") %>">
                                            <button class="action-btn edit-btn">Edit</button>
                                        </a>
                                    </td>
                                </tr>
        <%
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
        %>
                <p style="color:red;">Error fetching categories. Please try again later.</p>
        <%
            }
        %>
    </table>

    <br>
    <div style="text-align: center;">
        <a href="dashboard.jsp" style="text-decoration:none;">
            <button class="action-btn" style="background-color: #008cba; color: white;">Back to Dashboard</button>
        </a>
    </div>
</body>
</html>
