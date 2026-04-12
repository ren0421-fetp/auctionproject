<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Bidder dashboard</title>
</head>
<body>
    <h2>Welcome, ${loggedInUser.firstName} ${loggedInUser.lastName}!</h2>

    <div class="profile-section">
        <img src="${pageContext.request.contextPath}${loggedInUser.photoPath}"
             alt="Profile Image"
             style="width:100px; height:100px; border-radius: 50%;" />
    </div>

    <div style="margin-top:20px;">
        <a href="${pageContext.request.contextPath}/app/bidder/auctions/list">Browse Auctions</a>
    	<a href="${pageContext.request.contextPath}/app/bidder/package/list">Purchase Package</a>
    	<a href="${pageContext.request.contextPath}/app/bidder/auctions/my-bids">Show Your Bid</a>
    	<a href="${pageContext.request.contextPath}/app/feedback">Feedback</a>
    	
    </div>
</body>
</html>
