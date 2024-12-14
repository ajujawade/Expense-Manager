<%@ page import="java.sql.*" %>
<%
    int id = Integer.parseInt(request.getParameter("id"));
    String name = "";
    String type = "";
    double amount = 0.0;

    // Fetch existing category details
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/expensemanager", "root", "Latari1234!")) {
            String query = "SELECT * FROM categories WHERE id = ?";
            try (PreparedStatement ps = con.prepareStatement(query)) {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        name = rs.getString("name");
                        type = rs.getString("type");
                        amount = rs.getDouble("amount");
                    }
                }
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Edit Category</title>
</head>
<body>
    <h2>Edit Category</h2>
    <form action="${pageContext.request.contextPath}/editCategory" method="post">
        <input type="hidden" name="id" value="<%= id %>">
        <label for="name">Name:</label>
        <input type="text" name="name" value="<%= name %>" required>
        <br><br>

        <label for="type">Type:</label>
        <select name="type" required>
            <option value="Income" <%= type.equals("Income") ? "selected" : "" %>>Income</option>
            <option value="Expense" <%= type.equals("Expense") ? "selected" : "" %>>Expense</option>
        </select>
        <br><br>

        <label for="amount">Amount:</label>
        <input type="number" step="0.01" name="amount" value="<%= amount %>" required>
        <br><br>

        <input type="submit" value="Update" >
    </form>
    <br>
    <a href="${pageContext.request.contextPath}/viewCategories.jsp">Cancel</a>
</body>
</html>
