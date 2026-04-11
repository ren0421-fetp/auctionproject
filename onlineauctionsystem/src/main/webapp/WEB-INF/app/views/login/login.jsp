<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
</head>
<body>
    <h1>Login</h1>

    <form:form modelAttribute="loginForm" method="post">
        <div style="color:red;">
            <form:errors path="*"/>
        </div>

        <div>
            <form:label path="username">username</form:label>
            <form:input path="username"/>
            <form:errors path="username" cssStyle="color:red;"/>
        </div>

        <div>
            <form:label path="password">password</form:label>
            <form:password path="password"/>
            <form:errors path="password" cssStyle="color:red;"/>
        </div>

        <div>
            <input type="submit" value="login"/>
        </div>
    </form:form>
</body>
</html>
