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
<link rel="stylesheet" href="${pageContext.request.contextPath}/app/cssPath/app-layout.css">
</head>
<body class="app-workspace">
    <%@ include file="/WEB-INF/app/views/fragments/app_header.jspf" %>

    <div class="container-fluid app-shell">
        <div class="row g-4">
            <div class="col-xl-3 col-xxl-2 sidebar-col">
                <%@ include file="/WEB-INF/app/views/fragments/sidebar_seller.jspf" %>
            </div>

            <div class="col-xl-9 col-xxl-10">
                <div class="dashboard-surface">
                    <div class="dashboard-hero mb-4">
                        <div>
                            <p class="dashboard-eyebrow">Seller Workspace</p>
                            <h1 class="dashboard-title">Welcome back, ${loggedInUser.firstName}.</h1>
                            <p class="dashboard-subtitle">
                                Stay on top of your listings, watch incoming bids, and keep your seller account ready for the next auction cycle.
                            </p>
                        </div>

                        <div class="dashboard-hero-actions">
                            <a class="btn btn-dark rounded-pill px-4"
                               href="${pageContext.request.contextPath}/app/seller/product/add">
                                Add Product
                            </a>
                            <a class="btn btn-light border rounded-pill px-4"
                               href="${pageContext.request.contextPath}/app/seller/product/list">
                                My Products
                            </a>
                        </div>
                    </div>

                    <%@ include file="/WEB-INF/app/views/fragments/flash_messages.jspf" %>

                    <div class="row g-3 mb-4">
                        <div class="col-md-6 col-xl-4">
                            <div class="stat-card stat-accent">
                                <div class="stat-label">Live Listings</div>
                                <div class="stat-value">12</div>
                                <div class="stat-meta">Auction items currently running or ready for attention.</div>
                                <div class="mini-chart">
                                    <span style="height:24%;"></span>
                                    <span style="height:42%;"></span>
                                    <span style="height:50%;"></span>
                                    <span style="height:61%;"></span>
                                    <span style="height:74%;"></span>
                                    <span style="height:88%;"></span>
                                </div>
                            </div>
                        </div>

                        <div class="col-md-6 col-xl-4">
                            <div class="stat-card stat-soft">
                                <div class="stat-label">Bid Activity</div>
                                <div class="stat-value">27</div>
                                <div class="stat-meta">Recent bidder actions across the products you’re tracking.</div>
                                <div class="accent-line"></div>
                            </div>
                        </div>

                        <div class="col-md-6 col-xl-4">
                            <div class="stat-card stat-warning">
                                <div class="stat-label">Listings to Review</div>
                                <div class="stat-value">05</div>
                                <div class="stat-meta">Products that may need updates, timing checks, or bid review.</div>
                                <div class="mini-chart">
                                    <span style="height:18%;"></span>
                                    <span style="height:32%;"></span>
                                    <span style="height:41%;"></span>
                                    <span style="height:53%;"></span>
                                    <span style="height:60%;"></span>
                                    <span style="height:72%;"></span>
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
