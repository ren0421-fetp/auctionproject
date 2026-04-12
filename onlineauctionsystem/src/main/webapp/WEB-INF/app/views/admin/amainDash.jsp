<!--
merong page and taglib here
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
-->

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="activeNav" value="dashboard" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin Dashboard</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="${pageContext.request.contextPath}/cssPath/app-layout.css">
</head>
<body>
    <%@ include file="/WEB-INF/app/views/fragments/app_header.jspf" %>

    <div class="container-fluid py-4 app-shell">
        <div class="row g-4">
            <div class="col-lg-3 col-xl-2 sidebar-col">
                <%@ include file="/WEB-INF/app/views/fragments/sidebar_admin.jspf" %>
            </div>

            <div class="col-lg-9 col-xl-10">
                <div class="content-panel">
                    <h1 class="h3 page-title">Admin Dashboard</h1>
                    <p class="page-subtitle">
                        Manage users, auctions, packages, locations, news, feedback, and reports from one place.
                    </p>

                    <%@ include file="/WEB-INF/app/views/fragments/flash_messages.jspf" %>

                    <div class="row g-3">
                        <div class="col-md-6 col-xl-3">
                            <div class="card h-100 border-0 bg-light">
                                <div class="card-body">
                                    <h2 class="h6 text-muted">Users</h2>
                                    <p class="mb-3">Manage system users and lock/unlock accounts.</p>
                                    <a class="btn btn-sm btn-danger"
                                       href="${pageContext.request.contextPath}/app/admin/users">Open</a>
                                </div>
                            </div>
                        </div>

                        <div class="col-md-6 col-xl-3">
                            <div class="card h-100 border-0 bg-light">
                                <div class="card-body">
                                    <h2 class="h6 text-muted">Bidding</h2>
                                    <p class="mb-3">Review bids and confirm winners.</p>
                                    <a class="btn btn-sm btn-danger"
                                       href="${pageContext.request.contextPath}/app/admin/bids/confirmations">Open</a>
                                </div>
                            </div>
                        </div>

                        <div class="col-md-6 col-xl-3">
                            <div class="card h-100 border-0 bg-light">
                                <div class="card-body">
                                    <h2 class="h6 text-muted">Products</h2>
                                    <p class="mb-3">Manage auction items across the system.</p>
                                    <a class="btn btn-sm btn-danger"
                                       href="${pageContext.request.contextPath}/app/admin/products">Open</a>
                                </div>
                            </div>
                        </div>

                        <div class="col-md-6 col-xl-3">
                            <div class="card h-100 border-0 bg-light">
                                <div class="card-body">
                                    <h2 class="h6 text-muted">Reports</h2>
                                    <p class="mb-3">Generate PDF reports for admin review.</p>
                                    <a class="btn btn-sm btn-danger"
                                       href="${pageContext.request.contextPath}/app/admin/reports">Open</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
