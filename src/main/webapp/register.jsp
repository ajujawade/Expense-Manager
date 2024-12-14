<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Register</title>
  <!-- Link to external CSS -->
  <link rel="stylesheet" href="css/registerstyles.css">
</head>
<body>
  <div class="form-container">
    <h2>Register</h2>

    <!-- Error message dynamically populated -->
    <c:if test="${not empty error}">
      <div class="error-message">${error}</div>
    </c:if>

    <!-- Registration Form -->
    <form action="regForm" method="post" enctype="multipart/form-data">
      <label for="name">Name:</label>
      <input type="text" id="name" name="name" placeholder="Enter your full name" required>

      <label for="username">Username:</label>
      <input type="text" id="username" name="username" placeholder="Choose a username" required>

      <label for="email">Email:</label>
      <input type="email" id="email" name="email" placeholder="Enter your email address" required>

      <label for="password">Password:</label>
      <input type="password" id="password" name="password" placeholder="Enter a secure password" required>


      <input type="submit" value="Register">
    </form>
  </div>
</body>
</html>

