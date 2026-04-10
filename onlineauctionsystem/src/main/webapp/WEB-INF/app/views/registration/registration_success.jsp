<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>registration success</title>
</head>
<body>
    <h1>registration successful</h1>
    <p>username: <c:out value="${registeredUsername}" /></p>
    <p><a href="${pageContext.request.contextPath}/app/registration">register another user</a></p>
</body>
</html>
