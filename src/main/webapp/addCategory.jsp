<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add Category</title>
    <link rel="stylesheet" href="css/addCategory.css">
</head>
<body>
    <div class="container">
        <h2>Add Expense/Income Category</h2>
        <form action="${pageContext.request.contextPath}/addCategory" method="post">
            <label for="name">Description:</label>
            <input type="text" name="name" placeholder="Enter category name" required>
            
            <label for="type">Type:</label>
            <select name="type" required>
                <option value="Income">Income</option>
                <option value="Expense">Expense</option>
            </select>
            
            <label for="amount">Amount:</label>
            <input type="number" step="0.01" name="amount" placeholder="Enter amount" required>
            
            <input type="submit" value="Add">
        </form>
        <br>
        <a href="${pageContext.request.contextPath}/dashboard.jsp" class="back-link">Back to Dashboard</a>
    </div>
</body>
</html>
