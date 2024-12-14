<%@ page import="java.text.DecimalFormat" %>
<%
    double income = (Double) request.getAttribute("totalIncome");
    double expenses = (Double) request.getAttribute("totalExpense");
    double profitLoss = (Double) request.getAttribute("profitLoss");
    DecimalFormat df = new DecimalFormat("#,###.00");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Profit and Loss Dashboard</title>
    <link rel="stylesheet" href="css/profitLoss.css">
</head>
<body>
    <header>
        <h2>Profit & Loss Dashboard</h2>
    </header>
    
    <div class="container">
        <div class="stats">
            <div class="stat">
                <h3>Total Income</h3>
                <p class="value">$<%= df.format(income) %></p>
            </div>
            <div class="stat">
                <h3>Total Expenses</h3>
                <p class="value">$<%= df.format(expenses) %></p>
            </div>
            <div class="stat profit">
                <h3>Profit/Loss</h3>
                <p class="value">$<%= df.format(profitLoss) %></p>
            </div>
        </div>
        
        <a href="categories.jsp" class="back-link">Back to Dashboard</a>
    </div>
</body>
</html>
