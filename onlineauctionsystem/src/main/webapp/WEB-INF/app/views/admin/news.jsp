<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Manage News</title>
</head>
<body>
    <h1>Manage News</h1>

    <p>
        <a href="${pageContext.request.contextPath}/app/admin/home">Dashboard</a>
    </p>

    <c:if test="${not empty newsSuccess}">
        <p style="color:green;"><c:out value="${newsSuccess}" /></p>
    </c:if>

    <c:if test="${not empty newsError}">
        <p style="color:red;"><c:out value="${newsError}" /></p>
    </c:if>

    <h2>
        <c:choose>
            <c:when test="${editMode}">Update News</c:when>
            <c:otherwise>Add News Here</c:otherwise>
        </c:choose>
    </h2>

    <form:form modelAttribute="newsForm"
        method="post"
        action="${pageContext.request.contextPath}/app/admin/news/save">
        <form:hidden path="newsId"/>

        <div>
            <form:label path="newsTitle">News Title</form:label>
            <form:input path="newsTitle"/>
            <form:errors path="newsTitle" cssStyle="color:red;"/>
        </div>

        <div>
            <form:label path="newsContent">News Content</form:label>
            <form:textarea path="newsContent" rows="4" cols="60"/>
            <form:errors path="newsContent" cssStyle="color:red;"/>
        </div>

        <input type="submit"
               value="<c:choose><c:when test='${editMode}'>Update News</c:when><c:otherwise>Add News</c:otherwise></c:choose>"/>
    </form:form>

    <hr/>

    <h2>Manage News</h2>
    <table border="1" cellpadding="8">
        <thead>
            <tr>
                <th>Action</th>
                <th>News Id</th>
                <th>News Title</th>
                <th>News Content</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="news" items="${newsList}">
                <tr>
                    <td>
                        <a href="${pageContext.request.contextPath}/app/admin/news?newsId=${news.newsId}">Update</a>
                        <form method="post"
                              action="${pageContext.request.contextPath}/app/admin/news/delete"
                              style="display:inline;">
                            <input type="hidden" name="newsId" value="${news.newsId}" />
                            <input type="submit" value="Delete" />
                        </form>
                    </td>
                    <td><c:out value="${news.newsId}" /></td>
                    <td><c:out value="${news.newsTitle}" /></td>
                    <td><c:out value="${news.newsContent}" /></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>
