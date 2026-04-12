<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Show Your Bid</title>
</head>
<body>
    <h1>Show Your Bid</h1>

    <p>
        <a href="${pageContext.request.contextPath}/app/bidder/auctions/list">Browse Auctions</a>
        |
        <a href="${pageContext.request.contextPath}/app/bidder/package/list">Package</a>
    </p>

    <c:choose>
        <c:when test="${empty myBids}">
            <p>You have not placed any bids yet.</p>
        </c:when>
        <c:otherwise>
            <table border="1" cellpadding="8">
                <thead>
                  <tr>
					    <th>bid id</th>
					    <th>product id</th>
					    <th>product name</th>
					    <th>seller</th>
					    <th>minimum bid</th>
					    <th>your bid</th>
					    <th>bid date</th>
					    <th>photo</th>
					    <th>result</th>
					    <th>action</th>
					</tr>
                </thead>
                <tbody>
                    <c:forEach var="bid" items="${myBids}">
                        <tr>
                            <td><c:out value="${bid.bidId}" /></td>
                            <td><c:out value="${bid.productId}" /></td>
                            <td><c:out value="${bid.productName}" /></td>
                            <td><c:out value="${bid.sellerUsername}" /></td>
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
							<td>
							    <c:choose>
							        <c:when test="${bid.productConfirmed}">
							            <span>Locked</span>
							        </c:when>
							        <c:otherwise>
							            <a href="${pageContext.request.contextPath}/app/bidder/auctions/modify?bidId=${bid.bidId}">
							                Modify Bid
							            </a>
							        </c:otherwise>
							    </c:choose>
							</td>

							    <c:choose>
							        <c:when test="${bid.productConfirmed}">
							            <c:choose>
							                <c:when test="${bid.bidderUsername == bid.confirmedWinnerUsername}">
							                    You Won
							                </c:when>
							                <c:otherwise>
							                    You Lost
							                </c:otherwise>
							            </c:choose>
							            <br/>
							            <small>Confirmed at <c:out value="${bid.confirmedPrice}" /></small>
							        </c:when>
							        <c:otherwise>
							            Pending
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
