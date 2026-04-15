<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="activeNav" value="dashboard" />
<c:set var="homePath" value="/app/admin/home" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin Dashboard</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="${pageContext.request.contextPath}/app/cssPath/app-layout.css">
</head>
<body class="app-workspace">
    <%@ include file="/WEB-INF/app/views/fragments/app_header.jspf" %>

    <div class="container-fluid app-shell">
        <div class="row g-4">
            <div class="col-xl-3 col-xxl-2 sidebar-col">
                <%@ include file="/WEB-INF/app/views/fragments/sidebar_admin.jspf" %>
            </div>

            <div class="col-xl-9 col-xxl-10">
                <div class="dashboard-surface">
                    <div class="dashboard-hero mb-4">
                        <div>
                            <p class="dashboard-eyebrow">Admin Workspace</p>
                            <h1 class="dashboard-title">Control the marketplace with clarity.</h1>
                            <p class="dashboard-subtitle">
                                Oversee users, packages, bidding outcomes, products, locations, announcements, and reporting in one command space.
                            </p>
                        </div>

                        <div class="dashboard-hero-actions">
                            <a class="btn btn-dark rounded-pill px-4"
                               href="${pageContext.request.contextPath}/app/admin/user-packages">
                                Review Requests
                            </a>
                            <a class="btn btn-light border rounded-pill px-4"
                               href="${pageContext.request.contextPath}/app/admin/reports">
                                Open Reports
                            </a>
                        </div>
                    </div>

                    <%@ include file="/WEB-INF/app/views/fragments/flash_messages.jspf" %>

                    <div class="row g-3 mb-4">
                        <div class="col-md-6 col-xl-4">
                            <div class="stat-card stat-accent">
                                <div class="stat-label">Pending Package Requests</div>
                                <div class="stat-value">14</div>
                                <div class="stat-meta">Requests that may need approval or rejection soon.</div>
                                <div class="mini-chart">
                                    <span style="height:24%;"></span>
                                    <span style="height:38%;"></span>
                                    <span style="height:46%;"></span>
                                    <span style="height:55%;"></span>
                                    <span style="height:70%;"></span>
                                    <span style="height:84%;"></span>
                                </div>
                            </div>
                        </div>

                        <div class="col-md-6 col-xl-4">
                            <div class="stat-card stat-soft">
                                <div class="stat-label">Auction Oversight</div>
                                <div class="stat-value">Live</div>
                                <div class="stat-meta">Products, bids, and confirmations need steady review.</div>
                                <div class="accent-line"></div>
                            </div>
                        </div>

                        <div class="col-md-6 col-xl-4">
                            <div class="stat-card stat-warning">
                                <div class="stat-label">Admin Focus Areas</div>
                                <div class="stat-value">07</div>
                                <div class="stat-meta">Core management surfaces worth checking this session.</div>
                                <div class="mini-chart">
                                    <span style="height:18%;"></span>
                                    <span style="height:28%;"></span>
                                    <span style="height:42%;"></span>
                                    <span style="height:56%;"></span>
                                    <span style="height:63%;"></span>
                                    <span style="height:76%;"></span>
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

