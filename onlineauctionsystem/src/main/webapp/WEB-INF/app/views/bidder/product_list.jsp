<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Auction Products</title>
</head>
<body>
    <h1>Auction Products</h1>
    <a href="${pageContext.request.contextPath}/app/bidder/auctions/my-bids">Show Your Bid</a>

    <form method="get" action="${pageContext.request.contextPath}/app/bidder/auctions/list">
        <div>
            <label for="keyword">Search Product</label>
            <input type="text" id="keyword" name="keyword" value="${keyword}" />
        </div>

        <div>
            <label for="catId">Category</label>
            <select id="catId" name="catId">
                <option value="">-- all categories --</option>
                <c:forEach var="cat" items="${categoryOpts}">
                    <option value="${cat.catId}" <c:if test="${selectedCatId == cat.catId}">selected</c:if>>
                        <c:out value="${cat.catName}" />
                    </option>
                </c:forEach>
            </select>
        </div>

        <div>
            <label for="minPrice">Min Bid From</label>
            <input type="text" id="minPrice" name="minPrice" value="${minPrice}" />
        </div>

        <div>
            <label for="maxPrice">Min Bid To</label>
            <input type="text" id="maxPrice" name="maxPrice" value="${maxPrice}" />
        </div>

        <div>
            <input type="submit" value="Search" />
            <a href="${pageContext.request.contextPath}/app/bidder/auctions/list">Clear</a>
        </div>
    </form>

    <c:choose>
        <c:when test="${empty openProducts}">
            <p>No auctions found.</p>
        </c:when>
        <c:otherwise>
            <table border="1" cellpadding="8">
                <thead>
                    <tr>
                        <th>product id</th>
                        <th>name</th>
                        <th>category</th>
                        <th>phase</th>
                        <th>minimum bid</th>
                        <th>current highest bid</th>
                        <th>start date</th>
                        <th>end date</th>
                        <th>photo</th>
                        <th>action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="product" items="${openProducts}">
                        <tr>
                            <td><c:out value="${product.productId}" /></td>
                            <td><c:out value="${product.productName}" /></td>
                            <td><c:out value="${product.categoryName}" /></td>
                            <td><c:out value="${product.auctionPhase}" /></td>
                            <td><c:out value="${product.minBidPrice}" /></td>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty product.currentHighestBid}">
                                        <c:out value="${product.currentHighestBid}" />
                                    </c:when>
                                    <c:otherwise>No bids yet</c:otherwise>
                                </c:choose>
                            </td>
                            <td><c:out value="${product.startDate}" /></td>
                            <td><c:out value="${product.endDate}" /></td>
                            <td>
                                <c:if test="${not empty product.photoPath}">
                                    <img src="${pageContext.request.contextPath}${product.photoPath}"
                                         alt="Product Photo"
                                         style="width:90px; height:90px;" />
                                </c:if>
                            </td>
                            <td>
                                <a href="${pageContext.request.contextPath}/app/bidder/auctions/detail?productId=${product.productId}">
                                    Detail
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>
</body>
</html>
