<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Manage Categories</title>
</head>
<body>
    <h1>Manage Categories</h1>

    <p>
        <a href="${pageContext.request.contextPath}/app/admin/home">Dashboard</a>
    </p>

    <c:if test="${not empty saveSuccess}">
        <p style="color:green;"><c:out value="${saveSuccess}" /></p>
    </c:if>

    <c:if test="${not empty saveError}">
        <p style="color:red;"><c:out value="${saveError}" /></p>
    </c:if>

    <h2>
        <c:choose>
            <c:when test="${editMode}">Update Category</c:when>
            <c:otherwise>Add Category</c:otherwise>
        </c:choose>
    </h2>

    <form:form modelAttribute="categoryForm"
        method="post"
        action="${pageContext.request.contextPath}/app/admin/categories/save">

        <form:hidden path="catId"/>

        <div>
            <form:label path="catName">Category Name</form:label>
            <form:input path="catName"/>
            <form:errors path="catName" cssStyle="color:red;"/>
        </div>

        <div>
            <input type="submit"
                   value="<c:choose><c:when test='${editMode}'>Update Category</c:when><c:otherwise>Add Category</c:otherwise></c:choose>"/>
        </div>
    </form:form>

    <hr/>

    <h2>Existing Categories</h2>

    <c:choose>
        <c:when test="${empty categories}">
            <p>No categories found.</p>
        </c:when>
        <c:otherwise>
            <table border="1" cellpadding="8">
                <thead>
                    <tr>
                        <th>category id</th>
                        <th>category name</th>
                        <th>action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="cat" items="${categories}">
                        <tr>
                            <td><c:out value="${cat.catId}" /></td>
                            <td><c:out value="${cat.catName}" /></td>
                            <td>
                                <a href="${pageContext.request.contextPath}/app/admin/categories/edit?catId=${cat.catId}">
                                    Edit
                                </a>

                                <form method="post"
                                      action="${pageContext.request.contextPath}/app/admin/categories/delete"
                                      style="display:inline;">
                                    <input type="hidden" name="catId" value="${cat.catId}" />
                                    <input type="submit" value="Delete" />
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>
</body>
</html>
