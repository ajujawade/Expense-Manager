document.addEventListener("DOMContentLoaded", () => {
  const textElement = document.querySelector(".typing-container");
  const textToType = "Welcome to Expense Manager";
  let index = 0;
  const typingSpeed = 100; // Typing delay (in milliseconds)

  // Function to simulate typing effect
  function typeText() {
    if (index < textToType.length) {
      textElement.textContent += textToType[index];
      index++;
      setTimeout(typeText, typingSpeed);
    } else {
      // Once typing finishes, stop the cursor blinking
      textElement.style.borderRight = "none";
    }
  }

  // Wait for the slide-down effect to finish and then start the typing effect
  setTimeout(typeText, 1000);
});

// Navigation function for the Login button
function navigateToLogin() {
  window.location.href = "login.jsp";
}
