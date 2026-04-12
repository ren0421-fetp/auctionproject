<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Confirm Winning Bids</title>
</head>
<body>
    <h1>Confirm Winning Bids</h1>

    <p>
        <a href="${pageContext.request.contextPath}/app/admin/home">Dashboard</a>
    </p>

    <c:if test="${not empty confirmSuccess}">
        <p style="color:green;"><c:out value="${confirmSuccess}" /></p>
    </c:if>

    <c:if test="${not empty confirmError}">
        <p style="color:red;"><c:out value="${confirmError}" /></p>
    </c:if>

    <c:choose>
        <c:when test="${empty allBids}">
            <p>No bids available for confirmation.</p>
        </c:when>
        <c:otherwise>
            <table border="1" cellpadding="8">
                <thead>
                    <tr>
					    <th>bid id</th>
					    <th>product id</th>
					    <th>product name</th>
					    <th>seller</th>
					    <th>bidder</th>
					    <th>bid amount</th>
					    <th>bid date</th>
					    <th>start date</th>
					    <th>end date</th>
					    <th>auction state</th>
					    <th>photo</th>
					    <th>confirmation status</th>
					    <th>action</th>
					</tr>
                </thead>
                <tbody>
                    <c:forEach var="bid" items="${allBids}">
                        <tr>
                            <td><c:out value="${bid.bidId}" /></td>
                            <td><c:out value="${bid.productId}" /></td>
                            <td><c:out value="${bid.productName}" /></td>
                            <td><c:out value="${bid.sellerUsername}" /></td>
                            <td><c:out value="${bid.bidderUsername}" /></td>
                            <td><c:out value="${bid.bidPrice}" /></td>
                            <td><c:out value="${bid.bidDate}" /></td>
                            <td><c:out value="${bid.productStartDate}" /></td>
							<td><c:out value="${bid.productEndDate}" /></td>
							<td><c:out value="${bid.auctionState}" /></td>
                            <td>
                                <c:if test="${not empty bid.productPhotoPath}">
                                    <img src="${pageContext.request.contextPath}${bid.productPhotoPath}"
                                         alt="Product Photo"
                                         style="width:90px; height:90px;" />
                                </c:if>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${bid.productConfirmed}">
                                        Confirmed:
                                        <c:out value="${bid.confirmedWinnerUsername}" />
                                        at
                                        <c:out value="${bid.confirmedPrice}" />
                                    </c:when>
                                    <c:otherwise>
                                        Not confirmed
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${bid.productConfirmed}">
                                        <span>Locked</span>
                                    </c:when>
                                    <c:otherwise>
                                        <form method="post"
                                              action="${pageContext.request.contextPath}/app/admin/bids/confirm">
                                            <input type="hidden" name="bidId" value="${bid.bidId}" />
                                            <input type="submit" value="Confirm Winner" />
                                        </form>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>
</body>
</html>
