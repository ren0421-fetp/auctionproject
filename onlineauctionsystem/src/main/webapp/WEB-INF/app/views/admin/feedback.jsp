<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>View Feedback</title>
</head>
<body>
    <h1>View Feedback</h1>

    <p>
        <a href="${pageContext.request.contextPath}/app/admin/home">Dashboard</a>
    </p>

    <c:choose>
        <c:when test="${empty feedbackList}">
            <p>No feedback found.</p>
        </c:when>
        <c:otherwise>
            <table border="1" cellpadding="8">
                <thead>
                    <tr>
                        <th>Feedback Id</th>
                        <th>First Name</th>
                        <th>Email</th>
                        <th>Contact</th>
                        <th>Subject</th>
                        <th>Message</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="feedback" items="${feedbackList}">
                        <tr>
                            <td><c:out value="${feedback.feedbackId}" /></td>
                            <td><c:out value="${feedback.firstName}" /></td>
                            <td><c:out value="${feedback.email}" /></td>
                            <td><c:out value="${feedback.contact}" /></td>
                            <td><c:out value="${feedback.subject}" /></td>
                            <td><c:out value="${feedback.msg}" /></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>
</body>
</html>
