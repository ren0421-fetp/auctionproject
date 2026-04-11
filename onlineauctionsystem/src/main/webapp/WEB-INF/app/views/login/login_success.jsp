<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login Success</title>
</head>
<body>
    <h1>login successful</h1>
    <p>welcome, <c:out value="${loggedInUser.firstName}" />!</p>
    <p>username: <c:out value="${loggedInUser.username}" /></p>
    <p>role: <c:out value="${loggedInUser.userType}" /></p>
</body>
</html>
