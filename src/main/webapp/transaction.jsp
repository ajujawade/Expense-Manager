<%@ page import="java.sql.*" %>
<%
    // Set response encoding to UTF-8 to ensure proper rendering of special characters
    response.setCharacterEncoding("UTF-8");
    response.setContentType("text/html; charset=UTF-8");

    String username = (String) session.getAttribute("username");
    if (username == null) {
        response.sendRedirect("login.jsp?error=Please login first.");
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Transaction Summary</title>
    <link rel="stylesheet" href="css/transaction.css">
</head>
<body>
    <header>
        <h2>Welcome, <%= username %>!</h2>
    </header>

    <div class="container">
        <h3>Transaction Summary (Profit or Loss)</h3>
        <%
            double totalIncome = 0;
            double totalExpense = 0;

            try {
                String DB_URL = "jdbc:mysql://localhost:3306/expensemanager?useUnicode=true&characterEncoding=utf8";
                String DB_USERNAME = "root";
                String DB_PASSWORD = "Latari1234!";
                Class.forName("com.mysql.cj.jdbc.Driver");

                try (Connection con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
                    String query = "SELECT type, SUM(amount) as total FROM categories GROUP BY type";
                    try (PreparedStatement ps = con.prepareStatement(query);
                         ResultSet rs = ps.executeQuery()) {

                        while (rs.next()) {
                            String type = rs.getString("type");
                            double amount = rs.getDouble("total");

                            if ("Income".equalsIgnoreCase(type)) {
                                totalIncome += amount;
                            } else if ("Expense".equalsIgnoreCase(type)) {
                                totalExpense += amount;
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            double netBalance = totalIncome - totalExpense;
        %>
        <div class="summary">
            <p><strong>Total Income:</strong> RS <%= totalIncome %></p>
            <p><strong>Total Expense:</strong> RS <%= totalExpense %></p>
            <%
                if (netBalance > 0) {
            %>
                <p style="color: green;"><strong>Profit: RS <%= netBalance %></strong></p>
            <%
                } else if (netBalance < 0) {
            %>
                <p style="color: red;"><strong>Loss: RS <%= Math.abs(netBalance) %></strong></p>
            <%
                } else {
            %>
                <p style="color: blue;"><strong>Break Even</strong></p>
            <%
                }
            %>
        </div>
        
        <div class="actions">
            <a href="${pageContext.request.contextPath}/dashboard.jsp" class="back-btn">Back to Dashboard</a>
        </div>
    </div>
</body>
</html>
