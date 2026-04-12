<!--
theres a tag here
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Seller dashboard</title>
</head>
<body>
	<h2>Welcome, ${loggedInUser.firstName} ${loggedInUser.lastName}!</h2>

<div class="profile-section">
    <img src="${pageContext.request.contextPath}${loggedInUser.photoPath}" 
         alt="Profile Image" 
         style="width:100px; height:100px; border-radius: 50%;" />
</div>

 <div style="margin-top:20px;">
        <a href="${pageContext.request.contextPath}/app/seller/product/add">Add Product</a>
        <a href="${pageContext.request.contextPath}/app/seller/product/list">My Products</a>
        <a href="${pageContext.request.contextPath}/app/seller/profile">My Profile</a>
        <a href="${pageContext.request.contextPath}/app/seller/home">Refresh Dashboard</a>
		<a href="${pageContext.request.contextPath}/app/seller/product/bids">View Bids</a>
    	<a href="${pageContext.request.contextPath}/app/feedback">Feedback</a>
    	
    </div>
</body>
</html>
-->

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="activeNav" value="dashboard" />
<c:set var="homePath" value="/app/seller/home" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Seller Dashboard</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="${pageContext.request.contextPath}/cssPath/app-layout.css">
</head>
<body>
    <%@ include file="/WEB-INF/app/views/fragments/app_header.jspf" %>

    <div class="container-fluid py-4 app-shell">
        <div class="row g-4">
            <div class="col-lg-3 col-xl-2 sidebar-col">
                <%@ include file="/WEB-INF/app/views/fragments/sidebar_seller.jspf" %>
            </div>

            <div class="col-lg-9 col-xl-10">
                <div class="content-panel">
                    <div class="d-flex flex-column flex-md-row justify-content-between align-items-md-center gap-3 mb-4">
                        <div>
                            <h1 class="h3 page-title">Seller Dashboard</h1>
                            <p class="page-subtitle mb-0">
                                Manage your auction items, profile, and bidding activity.
                            </p>
                        </div>

                        <c:if test="${not empty loggedInUser.photoPath}">
                            <img src="${pageContext.request.contextPath}${loggedInUser.photoPath}"
                                 alt="Profile Image"
                                 class="rounded-circle border"
                                 style="width:88px; height:88px; object-fit:cover;" />
                        </c:if>
                    </div>

                    <%@ include file="/WEB-INF/app/views/fragments/flash_messages.jspf" %>

                    <div class="row g-3">
                        <div class="col-md-6 col-xl-3">
                            <div class="card h-100 border-0 bg-light">
                                <div class="card-body">
                                    <h2 class="h6 text-muted">Add Product</h2>
                                    <p class="mb-3">Create a new auction listing.</p>
                                    <a class="btn btn-sm btn-danger"
                                       href="${pageContext.request.contextPath}/app/seller/product/add">Open</a>
                                </div>
                            </div>
                        </div>

                        <div class="col-md-6 col-xl-3">
                            <div class="card h-100 border-0 bg-light">
                                <div class="card-body">
                                    <h2 class="h6 text-muted">My Products</h2>
                                    <p class="mb-3">View and manage your active items.</p>
                                    <a class="btn btn-sm btn-danger"
                                       href="${pageContext.request.contextPath}/app/seller/product/list">Open</a>
                                </div>
                            </div>
                        </div>

                        <div class="col-md-6 col-xl-3">
                            <div class="card h-100 border-0 bg-light">
                                <div class="card-body">
                                    <h2 class="h6 text-muted">View Bids</h2>
                                    <p class="mb-3">Review bids placed on your products.</p>
                                    <a class="btn btn-sm btn-danger"
                                       href="${pageContext.request.contextPath}/app/seller/product/bids">Open</a>
                                </div>
                            </div>
                        </div>

                        <div class="col-md-6 col-xl-3">
                            <div class="card h-100 border-0 bg-light">
                                <div class="card-body">
                                    <h2 class="h6 text-muted">Profile</h2>
                                    <p class="mb-3">Update your seller account details.</p>
                                    <a class="btn btn-sm btn-danger"
                                       href="${pageContext.request.contextPath}/app/seller/profile">Open</a>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="mt-4">
                        <div class="card border-0 bg-light">
                            <div class="card-body">
                                <h2 class="h5 mb-3">Quick Start</h2>
                                <p class="mb-2">Use this dashboard as your seller workspace.</p>
                                <ul class="mb-0">
                                    <li>Add a product to start a new auction.</li>
                                    <li>Open My Products to edit listings and review schedules.</li>
                                    <li>Open View Bids to monitor bidder activity.</li>
                                </ul>
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
