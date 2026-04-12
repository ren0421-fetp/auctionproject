<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Feedback</title>
</head>
<body>
    <h1>Feedback Here</h1>

    <c:if test="${param.success == '1'}">
        <p style="color:green;">Your message is sent.</p>
    </c:if>

    <c:if test="${not empty feedbackError}">
        <p style="color:red;"><c:out value="${feedbackError}" /></p>
    </c:if>

    <form:form modelAttribute="feedbackForm"
        method="post"
        action="${pageContext.request.contextPath}/app/feedback">

        <div>
            <form:label path="firstName">First name</form:label>
            <form:input path="firstName"/>
            <form:errors path="firstName" cssStyle="color:red;"/>
        </div>

        <div>
            <form:label path="email">Email</form:label>
            <form:input path="email"/>
            <form:errors path="email" cssStyle="color:red;"/>
        </div>

        <div>
            <form:label path="contact">Contact</form:label>
            <form:input path="contact"/>
            <form:errors path="contact" cssStyle="color:red;"/>
        </div>

        <div>
            <form:label path="subject">Subject</form:label>
            <form:input path="subject"/>
            <form:errors path="subject" cssStyle="color:red;"/>
        </div>

        <div>
            <form:label path="msg">Message</form:label>
            <form:textarea path="msg" rows="5" cols="40"/>
            <form:errors path="msg" cssStyle="color:red;"/>
        </div>

        <div>
            <input type="submit" value="Submit"/>
        </div>
    </form:form>

    <hr/>
</body>
</html>
