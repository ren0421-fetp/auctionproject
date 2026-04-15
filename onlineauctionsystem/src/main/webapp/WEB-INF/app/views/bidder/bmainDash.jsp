<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="activeNav" value="dashboard" />
<c:set var="homePath" value="/app/bidder/home" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Bidder Dashboard</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="${pageContext.request.contextPath}/app/cssPath/app-layout.css">
</head>
<body class="app-workspace">
    <%@ include file="/WEB-INF/app/views/fragments/app_header.jspf" %>

    <div class="container-fluid app-shell">
        <div class="row g-4">
            <div class="col-xl-3 col-xxl-2 sidebar-col">
                <%@ include file="/WEB-INF/app/views/fragments/sidebar_bidder.jspf" %>
            </div>

            <div class="col-xl-9 col-xxl-10">
                <div class="dashboard-surface">
                    <div class="dashboard-hero mb-4">
                        <div>
                            <p class="dashboard-eyebrow">Bidder Workspace</p>
                            <h1 class="dashboard-title">
                                Welcome back, ${loggedInUser.firstName}.
                            </h1>
                            <p class="dashboard-subtitle">
                                Track live opportunities, keep an eye on your package request flow,
                                and move quickly when the next item is worth chasing.
                            </p>
                        </div>

                        <div class="dashboard-hero-actions">
                            <a class="btn btn-dark rounded-pill px-4"
                               href="${pageContext.request.contextPath}/app/bidder/auctions/list">
                                Browse Auctions
                            </a>
                            <a class="btn btn-light border rounded-pill px-4"
                               href="${pageContext.request.contextPath}/app/bidder/package/list">
                                View Packages
                            </a>
                        </div>
                    </div>

                    <%@ include file="/WEB-INF/app/views/fragments/flash_messages.jspf" %>

                    <div class="row g-3 mb-4">
                        <div class="col-md-6 col-xl-4">
                            <div class="stat-card stat-accent">
                                <div class="stat-label">Open Opportunities</div>
                                <div class="stat-value">24</div>
                                <div class="stat-meta">Fresh auction listings worth watching this cycle.</div>
                                <div class="mini-chart">
                                    <span style="height:28%;"></span>
                                    <span style="height:40%;"></span>
                                    <span style="height:58%;"></span>
                                    <span style="height:48%;"></span>
                                    <span style="height:72%;"></span>
                                    <span style="height:88%;"></span>
                                </div>
                            </div>
                        </div>

                        <div class="col-md-6 col-xl-4">
                            <div class="stat-card stat-soft">
                                <div class="stat-label">Package Request Status</div>
                                <div class="stat-value">Pending</div>
                                <div class="stat-meta">Your latest request is in review. Stay ready for approval updates.</div>
                                <div class="accent-line"></div>
                            </div>
                        </div>

                        <div class="col-md-6 col-xl-4">
                            <div class="stat-card stat-warning">
                                <div class="stat-label">My Active Bids</div>
                                <div class="stat-value">08</div>
                                <div class="stat-meta">Listings where your attention matters most right now.</div>
                                <div class="mini-chart">
                                    <span style="height:22%;"></span>
                                    <span style="height:30%;"></span>
                                    <span style="height:44%;"></span>
                                    <span style="height:39%;"></span>
                                    <span style="height:52%;"></span>
                                    <span style="height:66%;"></span>
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
