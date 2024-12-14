<%@ page import="java.util.*" %>
<%
    // Ensure only logged-in users can access this page
    String username = (String) session.getAttribute("username");
    if (username == null) {
        response.sendRedirect("login.jsp?error=Please login to continue.");
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Dashboard</title>
    <link rel="stylesheet" href="css/dashboardstyles.css">
</head>
<body>
    <!-- Header Section -->
    <header>
        <h2 class="welcome-text">Welcome, <%= username %>!</h2>
        <a href="logout" class="logout-btn">Logout</a>
    </header>
    
    <!-- Main Dashboard Section -->
    <div class="dashboard-container">
        <h3 class="dashboard-heading">Manage Your Expenses</h3>
        <ul class="dashboard-menu">
            <li><a href="addCategory.jsp" class="menu-item">Add Expenses</a></li>
            <li><a href="viewCategories.jsp" class="menu-item">View Expenses</a></li>
            <li><a href="transaction.jsp" class="menu-item">View Profit/Loss</a></li>
           
        </ul>
    </div>
</body>
</html>
