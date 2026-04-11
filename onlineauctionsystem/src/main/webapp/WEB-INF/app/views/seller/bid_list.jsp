<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Seller Bids</title>
</head>
<body>
    <h1>Bids On My Products</h1>

    <p>
        <a href="${pageContext.request.contextPath}/app/seller/home">Seller Dashboard</a> |
        <a href="${pageContext.request.contextPath}/app/seller/product/list">My Products</a> |
        <a href="${pageContext.request.contextPath}/app/seller/product/add">Add Product</a>
    </p>

    <c:choose>
        <c:when test="${empty sellerBids}">
            <p>No bids found for your products yet.</p>
        </c:when>
        <c:otherwise>
            <table border="1" cellpadding="8">
                <thead>
                    <tr>
                        <th>bid id</th>
                        <th>product id</th>
                        <th>product name</th>
                        <th>bidder</th>
                        <th>minimum bid</th>
                        <th>bid price</th>
                        <th>bid date</th>
                        <th>photo</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="bid" items="${sellerBids}">
                        <tr>
                            <td><c:out value="${bid.bidId}" /></td>
                            <td><c:out value="${bid.productId}" /></td>
                            <td><c:out value="${bid.productName}" /></td>
                            <td><c:out value="${bid.bidderUsername}" /></td>
                            <td><c:out value="${bid.minBidPrice}" /></td>
                            <td><c:out value="${bid.bidPrice}" /></td>
                            <td><c:out value="${bid.bidDate}" /></td>
                            <td>
                                <c:if test="${not empty bid.productPhotoPath}">
                                    <img src="${pageContext.request.contextPath}${bid.productPhotoPath}"
                                         alt="Product Photo"
                                         style="width:90px; height:90px;" />
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
