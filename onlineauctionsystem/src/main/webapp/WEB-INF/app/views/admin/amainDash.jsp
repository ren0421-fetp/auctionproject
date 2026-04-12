<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin Dashboard</title>
</head>
<body>
    <h2>Welcome, ${loggedInUser.firstName} ${loggedInUser.lastName}!</h2>

    <div style="margin-top:20px;">
        <a href="${pageContext.request.contextPath}/app/admin/bids/confirmations">Confirm Winner</a>
    	<a href="${pageContext.request.contextPath}/app/admin/users">Manage User</a>
    	<a href="${pageContext.request.contextPath}/app/admin/categories">Manage Categories</a>
    	<a href="${pageContext.request.contextPath}/app/admin/packages">Manage Packages</a>
    	<a href="${pageContext.request.contextPath}/app/admin/user-packages">Manage User Package</a>
    	<a href="${pageContext.request.contextPath}/app/admin/products">Manage Product</a>
   		<a href="${pageContext.request.contextPath}/app/admin/locations">Manage Country State And City</a>
   		<a href="${pageContext.request.contextPath}/app/admin/news">Manage News</a>
		<a href="${pageContext.request.contextPath}/app/admin/feedback">View Feedback</a>
   		<a href="${pageContext.request.contextPath}/app/admin/reports">Reports</a>
   		
    </div>
</body>
</html>
