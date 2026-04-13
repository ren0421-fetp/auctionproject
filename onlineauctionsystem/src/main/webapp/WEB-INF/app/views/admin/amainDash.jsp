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

                    <div class="row g-3">
                        <div class="col-xl-4">
                            <div class="panel-card h-100">
                                <div class="panel-kicker">Marketplace Snapshot</div>
                                <h2 class="panel-title">Balance review speed with system trust.</h2>
                                <p class="panel-copy mb-4">
                                    The admin role shapes how reliable the platform feels for bidders and sellers alike.
                                </p>

                                <div class="info-grid">
                                    <div class="info-pill">
                                        <span class="info-pill-label">Current focus</span>
                                        <span class="info-pill-value">Request approvals</span>
                                    </div>
                                    <div class="info-pill">
                                        <span class="info-pill-label">High-priority area</span>
                                        <span class="info-pill-value">Bidding confirmations</span>
                                    </div>
                                    <div class="info-pill">
                                        <span class="info-pill-label">System mode</span>
                                        <span class="status-chip status-open">Admin active</span>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="col-xl-4">
                            <div class="panel-card h-100">
                                <div class="d-flex justify-content-between align-items-start mb-3">
                                    <div>
                                        <div class="panel-kicker">Recent Admin Activity</div>
                                        <h2 class="panel-title mb-0">What needs attention</h2>
                                    </div>
                                    <a class="btn btn-sm btn-light border rounded-pill px-3"
                                       href="${pageContext.request.contextPath}/app/admin/user-packages">
                                        Open queue
                                    </a>
                                </div>

                                <div class="activity-row">
                                    <div>
                                        <div class="activity-title">User package requests waiting</div>
                                        <div class="activity-meta">Approval queue updated recently</div>
                                    </div>
                                    <span class="status-chip status-pending">Pending</span>
                                </div>

                                <div class="activity-row">
                                    <div>
                                        <div class="activity-title">Auction confirmations</div>
                                        <div class="activity-meta">Review products ready for winner confirmation</div>
                                    </div>
                                    <span class="status-chip status-watch">Watch</span>
                                </div>

                                <div class="activity-row">
                                    <div>
                                        <div class="activity-title">Platform catalog health</div>
                                        <div class="activity-meta">Categories, locations, and news stay aligned</div>
                                    </div>
                                    <span class="status-chip status-open">Open</span>
                                </div>
                            </div>
                        </div>

                        <div class="col-xl-4">
                            <div class="panel-card panel-card-dark h-100">
                                <div class="panel-kicker">Command Focus</div>
                                <h2 class="panel-title">Strong admin flow keeps the marketplace stable.</h2>
                                <p class="panel-copy mb-4">
                                    Approvals, lock actions, and listing reviews all compound into marketplace trust.
                                </p>

                                <div class="highlight-bid">4 priority lanes</div>
                                <div class="muted-light mb-3">worth reviewing before moving to deeper tasks</div>

                                <ul class="mini-list">
                                    <li>Package request approvals and rejections.</li>
                                    <li>Bid confirmation outcomes.</li>
                                    <li>User status and access control.</li>
                                    <li>Products and announcements consistency.</li>
                                </ul>
                            </div>
                        </div>

                        <div class="col-xl-7">
                            <div class="panel-card h-100">
                                <div class="d-flex justify-content-between align-items-start mb-3">
                                    <div>
                                        <div class="panel-kicker">Admin Flow</div>
                                        <h2 class="panel-title mb-0">Operate the platform from one organized surface</h2>
                                    </div>
                                    <span class="status-chip status-watch">System view</span>
                                </div>

                                <p class="panel-copy mb-4">
                                    Move between users, packages, products, reports, and confirmations without losing context.
                                    The goal is a calm operational rhythm, not reactive cleanup.
                                </p>

                                <div class="row g-3">
                                    <div class="col-md-4">
                                        <div class="quick-link-tile">
                                            <div class="quick-link-title">User Control</div>
                                            <div class="quick-link-copy">Review access, roles, and lock state.</div>
                                        </div>
                                    </div>
                                    <div class="col-md-4">
                                        <div class="quick-link-tile">
                                            <div class="quick-link-title">Package Governance</div>
                                            <div class="quick-link-copy">Maintain offerings and approvals.</div>
                                        </div>
                                    </div>
                                    <div class="col-md-4">
                                        <div class="quick-link-tile">
                                            <div class="quick-link-title">Auction Oversight</div>
                                            <div class="quick-link-copy">Review product and bid outcomes.</div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="col-xl-5">
                            <div class="panel-card h-100">
                                <div class="panel-kicker">Quick Access</div>
                                <h2 class="panel-title">Jump into core admin tools</h2>

                                <div class="quick-links-grid mt-4">
                                    <a class="quick-link-tile"
                                       href="${pageContext.request.contextPath}/app/admin/users">
                                        <div class="quick-link-title">Manage User</div>
                                        <div class="quick-link-copy">Review accounts and role states.</div>
                                    </a>

                                    <a class="quick-link-tile"
                                       href="${pageContext.request.contextPath}/app/admin/products">
                                        <div class="quick-link-title">Manage Product</div>
                                        <div class="quick-link-copy">See listing quality and control status.</div>
                                    </a>

                                    <a class="quick-link-tile"
                                       href="${pageContext.request.contextPath}/app/admin/user-packages">
                                        <div class="quick-link-title">User Package</div>
                                        <div class="quick-link-copy">Approve requests and assign balances.</div>
                                    </a>

                                    <a class="quick-link-tile"
                                       href="${pageContext.request.contextPath}/app/admin/reports">
                                        <div class="quick-link-title">Reports</div>
                                        <div class="quick-link-copy">Check system-level summaries.</div>
                                    </a>
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

