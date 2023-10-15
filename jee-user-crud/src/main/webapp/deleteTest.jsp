<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Yes or No Popup</title>
</head>
<body>
<!-- Your page content goes here -->

<!-- Create a link that triggers the confirmation popup -->
<a href="#" id="confirmationLink">Click Me</a>

<!-- Include JavaScript to show the popup and handle user choice -->
<script>
  document.getElementById("confirmationLink").addEventListener("click", function(e) {
    e.preventDefault(); // Prevent the link from navigating

    var userChoice = confirm("Do you want to proceed?"); // Show the confirmation dialog

    if (userChoice) {
      // User clicked "OK" (equivalent to "Yes")
      alert("You chose 'Yes.'");
      // You can add code here to perform the action for "Yes"
    } else {
      // User clicked "Cancel" (equivalent to "No")
      alert("You chose 'No.'");
      // You can add code here for "No" or to prevent the action
    }
  });
</script>
</body>
</html>
