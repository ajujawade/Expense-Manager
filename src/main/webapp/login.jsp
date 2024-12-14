<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login Form</title>
    <link rel="stylesheet" href="css/loginstyles.css">
</head>
<body>
    <!-- Login Form Container -->
    <div class="login-container">
        <h2>Login</h2>

        <!-- Check if error message exists -->
        <%
            String errorMessage = request.getParameter("error");
            if (errorMessage != null) {
        %>
            <p style="color: red;"><%= errorMessage %></p>
        <%
            }
        %>

        <!-- Login Form -->
        <form action="loginForm" method="post">
            <label for="username">Username:</label>
            <input type="text" id="username" name="username" placeholder="Enter your username" required>

            <label for="password">Password:</label>
            <input type="password" id="password" name="password" placeholder="Enter your password" required>

            <input type="submit" value="Login" class="btn-login">
        </form>

        <!-- Register Button -->
        <form action="register.jsp" method="get">
            <input type="submit" class="btn-register" value="Register">
        </form>
    </div>
</body>
</html>
