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

                    <div class="row g-3">
                        <div class="col-xl-4">
                            <div class="panel-card h-100">
                                <div class="panel-kicker">Seller Snapshot</div>
                                <h2 class="panel-title">Keep your listings presentation-ready.</h2>
                                <p class="panel-copy mb-4">
                                    A well-maintained auction page improves trust, response, and bidding momentum.
                                </p>

                                <div class="info-grid">
                                    <div class="info-pill">
                                        <span class="info-pill-label">Current focus</span>
                                        <span class="info-pill-value">Listing quality</span>
                                    </div>
                                    <div class="info-pill">
                                        <span class="info-pill-label">Next action</span>
                                        <span class="info-pill-value">Review active bids</span>
                                    </div>
                                    <div class="info-pill">
                                        <span class="info-pill-label">Account area</span>
                                        <span class="status-chip status-open">Seller ready</span>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="col-xl-4">
                            <div class="panel-card h-100">
                                <div class="d-flex justify-content-between align-items-start mb-3">
                                    <div>
                                        <div class="panel-kicker">Recent Activity</div>
                                        <h2 class="panel-title mb-0">Latest movement</h2>
                                    </div>
                                    <a class="btn btn-sm btn-light border rounded-pill px-3"
                                       href="${pageContext.request.contextPath}/app/seller/product/bids">
                                        Open bids
                                    </a>
                                </div>

                                <div class="activity-row">
                                    <div>
                                        <div class="activity-title">Vintage console listing</div>
                                        <div class="activity-meta">New bid activity this morning</div>
                                    </div>
                                    <span class="status-chip status-open">Open</span>
                                </div>

                                <div class="activity-row">
                                    <div>
                                        <div class="activity-title">Camera bundle auction</div>
                                        <div class="activity-meta">Needs a quick schedule review</div>
                                    </div>
                                    <span class="status-chip status-watch">Watch</span>
                                </div>

                                <div class="activity-row">
                                    <div>
                                        <div class="activity-title">Mechanical watch item</div>
                                        <div class="activity-meta">Listing still drawing attention</div>
                                    </div>
                                    <span class="status-chip status-open">Active</span>
                                </div>
                            </div>
                        </div>

                        <div class="col-xl-4">
                            <div class="panel-card panel-card-dark h-100">
                                <div class="panel-kicker">Next Move</div>
                                <h2 class="panel-title">Your catalog is only as strong as its timing.</h2>
                                <p class="panel-copy mb-4">
                                    Keep your best listings visible and make it easy for bidders to trust what they see.
                                </p>

                                <div class="highlight-bid">3 listings</div>
                                <div class="muted-light mb-3">worth reviewing before the next bidding window</div>

                                <ul class="mini-list">
                                    <li>Refresh products with older schedules.</li>
                                    <li>Review products with active bidder interest.</li>
                                    <li>Keep your seller profile complete and current.</li>
                                </ul>
                            </div>
                        </div>

                        <div class="col-xl-7">
                            <div class="panel-card h-100">
                                <div class="d-flex justify-content-between align-items-start mb-3">
                                    <div>
                                        <div class="panel-kicker">Seller Flow</div>
                                        <h2 class="panel-title mb-0">Build momentum from listing to bid review</h2>
                                    </div>
                                    <span class="status-chip status-watch">Seller focus</span>
                                </div>

                                <p class="panel-copy mb-4">
                                    Use the dashboard as your launch point: publish products, monitor bidder activity,
                                    and keep each listing aligned with the right auction schedule.
                                </p>

                                <div class="row g-3">
                                    <div class="col-md-4">
                                        <div class="quick-link-tile">
                                            <div class="quick-link-title">Create New Listing</div>
                                            <div class="quick-link-copy">Add products with photos, timing, and bid thresholds.</div>
                                        </div>
                                    </div>
                                    <div class="col-md-4">
                                        <div class="quick-link-tile">
                                            <div class="quick-link-title">Review My Products</div>
                                            <div class="quick-link-copy">See which listings are active or need updates.</div>
                                        </div>
                                    </div>
                                    <div class="col-md-4">
                                        <div class="quick-link-tile">
                                            <div class="quick-link-title">Check Bid Activity</div>
                                            <div class="quick-link-copy">Follow what bidders are doing across your products.</div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="col-xl-5">
                            <div class="panel-card h-100">
                                <div class="panel-kicker">Quick Access</div>
                                <h2 class="panel-title">Jump into seller tools</h2>

                                <div class="quick-links-grid mt-4">
                                    <a class="quick-link-tile"
                                       href="${pageContext.request.contextPath}/app/seller/product/add">
                                        <div class="quick-link-title">Add Product</div>
                                        <div class="quick-link-copy">Start a new auction listing.</div>
                                    </a>

                                    <a class="quick-link-tile"
                                       href="${pageContext.request.contextPath}/app/seller/product/list">
                                        <div class="quick-link-title">My Products</div>
                                        <div class="quick-link-copy">Manage your current catalog.</div>
                                    </a>

                                    <a class="quick-link-tile"
                                       href="${pageContext.request.contextPath}/app/seller/product/bids">
                                        <div class="quick-link-title">View Bids</div>
                                        <div class="quick-link-copy">Monitor bidder activity and outcomes.</div>
                                    </a>

                                    <a class="quick-link-tile"
                                       href="${pageContext.request.contextPath}/app/seller/profile">
                                        <div class="quick-link-title">Profile</div>
                                        <div class="quick-link-copy">Keep account details and photo updated.</div>
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
