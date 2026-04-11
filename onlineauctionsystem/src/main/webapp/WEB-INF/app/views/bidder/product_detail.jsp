<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Product Detail</title>
</head>
<body>
    <h1>Bid On Product</h1>

    <p>
        <a href="${pageContext.request.contextPath}/app/bidder/auctions/list">Back to Auctions</a>
    </p>

    <c:if test="${not empty bidSuccess}">
        <p style="color:green;"><c:out value="${bidSuccess}" /></p>
    </c:if>

    <c:if test="${not empty bidError}">
        <p style="color:red;"><c:out value="${bidError}" /></p>
    </c:if>

    <h2><c:out value="${product.productName}" /></h2>

    <c:if test="${not empty product.photoPath}">
        <img src="${pageContext.request.contextPath}${product.photoPath}"
             alt="Product Photo"
             style="width:200px; height:200px;" />
    </c:if>

    <p><strong>Category:</strong> <c:out value="${product.categoryName}" /></p>
    <p><strong>Description:</strong> <c:out value="${product.description}" /></p>
    <p><strong>Minimum Bid:</strong> <c:out value="${product.minBidPrice}" /></p>
    <p><strong>Current Highest Bid:</strong>
        <c:choose>
            <c:when test="${not empty product.currentHighestBid}">
                <c:out value="${product.currentHighestBid}" />
            </c:when>
            <c:otherwise>No bids yet</c:otherwise>
        </c:choose>
    </p>
    <p><strong>Status:</strong> <c:out value="${product.status}" /></p>
    <p><strong>Start Date:</strong> <c:out value="${product.startDate}" /></p>
    <p><strong>End Date:</strong> <c:out value="${product.endDate}" /></p>
    <p><strong>Remaining Package Bid Count:</strong> <c:out value="${remainingBidCount}" /></p>

    <form:form modelAttribute="bidForm"
        method="post"
        action="${pageContext.request.contextPath}/app/bidder/auctions/bid">

        <form:hidden path="productId"/>

        <div>
            <form:label path="bidPrice">Your Bid</form:label>
            <form:input path="bidPrice"/>
            <form:errors path="bidPrice" cssStyle="color:red;"/>
        </div>

        <div>
            <input type="submit" value="Place Bid"/>
        </div>
    </form:form>
</body>
</html>
