<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Bid Packages</title>
</head>
<body>
    <h1>Purchase Bid Package</h1>

    <p>
        <a href="${pageContext.request.contextPath}/app/bidder/auctions/list">Browse Auctions</a>
    </p>

    <p>
        <strong>Current Remaining Bid Count:</strong>
        <c:choose>
            <c:when test="${not empty remainingBidCount}">
                <c:out value="${remainingBidCount}" />
            </c:when>
            <c:otherwise>0</c:otherwise>
        </c:choose>
    </p>

    <c:if test="${not empty purchaseSuccess}">
        <p style="color:green;"><c:out value="${purchaseSuccess}" /></p>
    </c:if>

    <c:if test="${not empty purchaseError}">
        <p style="color:red;"><c:out value="${purchaseError}" /></p>
    </c:if>

    <c:choose>
        <c:when test="${empty availablePackages}">
            <p>No packages available.</p>
        </c:when>
        <c:otherwise>
            <table border="1" cellpadding="8">
                <thead>
                    <tr>
                        <th>package id</th>
                        <th>package name</th>
                        <th>price</th>
                        <th>allowed bid count</th>
                        <th>photo</th>
                        <th>action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="pkg" items="${availablePackages}">
                        <tr>
                            <td><c:out value="${pkg.packageId}" /></td>
                            <td><c:out value="${pkg.packageName}" /></td>
                            <td><c:out value="${pkg.packagePrice}" /></td>
                            <td><c:out value="${pkg.allowedBidCount}" /></td>
                            <td><c:out value="${pkg.photoPath}" /></td>
                            <td>
                                <form method="post"
                                      action="${pageContext.request.contextPath}/app/bidder/package/purchase">
                                    <input type="hidden" name="packageId" value="${pkg.packageId}" />
                                    <input type="submit" value="Purchase" />
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
