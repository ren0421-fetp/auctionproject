<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Manage User Package</title>
</head>
<body>
    <h1>Manage User Package</h1>

    <p>
        <a href="${pageContext.request.contextPath}/app/admin/home">Dashboard</a>
    </p>

    <c:if test="${not empty assignSuccess}">
        <p style="color:green;"><c:out value="${assignSuccess}" /></p>
    </c:if>

    <c:if test="${not empty assignError}">
        <p style="color:red;"><c:out value="${assignError}" /></p>
    </c:if>

    <h2>Assign Package</h2>

    <form:form modelAttribute="userPackageForm"
        method="post"
        action="${pageContext.request.contextPath}/app/admin/user-packages/assign">

        <div>
            <form:label path="username">Username</form:label>
            <form:select path="username">
                <form:option value="" label="-- select bidder --"/>
                <c:forEach var="bidder" items="${bidders}">
                    <option value="${bidder.username}"
                        <c:if test="${userPackageForm.username == bidder.username}">selected</c:if>>
                        <c:out value="${bidder.username}" />
                    </option>
                </c:forEach>
            </form:select>
            <form:errors path="username" cssStyle="color:red;"/>
        </div>

        <div>
            <form:label path="packageId">Package</form:label>
            <form:select path="packageId">
                <form:option value="" label="-- select package --"/>
                <c:forEach var="pkg" items="${packages}">
                    <option value="${pkg.packageId}"
                        <c:if test="${userPackageForm.packageId == pkg.packageId}">selected</c:if>>
                        <c:out value="${pkg.packageName}" /> - <c:out value="${pkg.packagePrice}" />
                    </option>
                </c:forEach>
            </form:select>
            <form:errors path="packageId" cssStyle="color:red;"/>
        </div>

        <div>
            <input type="submit" value="Assign Package"/>
        </div>
    </form:form>

    <hr/>

    <h2>Manage User Package</h2>

    <c:choose>
        <c:when test="${empty userPackageInfos}">
            <p>No user package records found.</p>
        </c:when>
        <c:otherwise>
            <table border="1" cellpadding="8">
                <thead>
                    <tr>
                        <th>Manipulation</th>
                        <th>Package Name</th>
                        <th>Package Price</th>
                        <th>User Package Id</th>
                        <th>Package Id</th>
                        <th>Username</th>
                        <th>Remaining Bid Balance</th>
                        <th>Photo</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="info" items="${userPackageInfos}">
                        <tr>
                            <td>
                                <a href="${pageContext.request.contextPath}/app/admin/user-packages?userPackageId=${info.userPackageId}">
                                    Select
                                </a>
                            </td>
                            <td><c:out value="${info.packageName}" /></td>
                            <td><c:out value="${info.packagePrice}" /></td>
                            <td><c:out value="${info.userPackageId}" /></td>
                            <td><c:out value="${info.packageId}" /></td>
                            <td><c:out value="${info.username}" /></td>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty info.remainingBidCount}">
                                        <c:out value="${info.remainingBidCount}" />
                                    </c:when>
                                    <c:otherwise>0</c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:if test="${not empty info.photoPath}">
                                    <img src="${pageContext.request.contextPath}${info.photoPath}"
                                         alt="Package Photo"
                                         style="width:70px; height:110px;" />
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>
</body>
</html>
