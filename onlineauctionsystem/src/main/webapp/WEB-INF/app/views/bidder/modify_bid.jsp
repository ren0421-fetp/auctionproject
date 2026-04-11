<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Modify Bid</title>
</head>
<body>
    <h1>Modify Bid</h1>

    <p>
        <a href="${pageContext.request.contextPath}/app/bidder/auctions/my-bids">Back to My Bids</a>
    </p>

    <c:if test="${not empty bidError}">
        <p style="color:red;"><c:out value="${bidError}" /></p>
    </c:if>

    <h2><c:out value="${product.productName}" /></h2>

    <c:if test="${not empty product.photoPath}">
        <img src="${pageContext.request.contextPath}${product.photoPath}"
             alt="Product Photo"
             style="width:200px; height:200px;" />
    </c:if>

    <p><strong>Current Highest Bid:</strong>
        <c:choose>
            <c:when test="${not empty product.currentHighestBid}">
                <c:out value="${product.currentHighestBid}" />
            </c:when>
            <c:otherwise>No bids yet</c:otherwise>
        </c:choose>
    </p>

    <p><strong>Your Previous Bid:</strong> <c:out value="${existingBid.bidPrice}" /></p>
    <p><strong>Remaining Package Bid Count:</strong> <c:out value="${remainingBidCount}" /></p>

    <form:form modelAttribute="bidForm"
        method="post"
        action="${pageContext.request.contextPath}/app/bidder/auctions/modify">

        <form:hidden path="bidId"/>
        <form:hidden path="productId"/>

        <div>
            <form:label path="bidPrice">New Bid Amount</form:label>
            <form:input path="bidPrice"/>
            <form:errors path="bidPrice" cssStyle="color:red;"/>
        </div>

        <div>
            <input type="submit" value="Submit Modified Bid"/>
        </div>
    </form:form>
</body>
</html>
