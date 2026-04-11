<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Seller dashboard</title>
</head>
<body>
	<h2>Welcome, ${loggedInUser.firstName} ${loggedInUser.lastName}!</h2>

<div class="profile-section">
    <img src="${pageContext.request.contextPath}${loggedInUser.photoPath}" 
         alt="Profile Image" 
         style="width:100px; height:100px; border-radius: 50%;" />
</div>
</body>
</html>