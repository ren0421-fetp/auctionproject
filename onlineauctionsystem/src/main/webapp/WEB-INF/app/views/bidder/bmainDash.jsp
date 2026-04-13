 
<!-- tag here, core and lib
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
-->

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

                    <div class="row g-3">
                        <div class="col-xl-4">
                            <div class="panel-card h-100">
                                <div class="panel-kicker">Package Snapshot</div>
                                <h2 class="panel-title">Stay ready before the next bid opens.</h2>
                                <p class="panel-copy mb-4">
                                    Keep your request status visible and make sure your balance strategy fits the listings you want.
                                </p>

                                <div class="info-grid">
                                    <div class="info-pill">
                                        <span class="info-pill-label">Current focus</span>
                                        <span class="info-pill-value">Bid package review</span>
                                    </div>
                                    <div class="info-pill">
                                        <span class="info-pill-label">Latest request</span>
                                        <span class="info-pill-value">Starter package</span>
                                    </div>
                                    <div class="info-pill">
                                        <span class="info-pill-label">Status</span>
                                        <span class="status-chip status-pending">Awaiting approval</span>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="col-xl-4">
                            <div class="panel-card h-100">
                                <div class="d-flex justify-content-between align-items-start mb-3">
                                    <div>
                                        <div class="panel-kicker">Recent Bid Activity</div>
                                        <h2 class="panel-title mb-0">Latest movement</h2>
                                    </div>
                                    <a class="btn btn-sm btn-light border rounded-pill px-3"
                                       href="${pageContext.request.contextPath}/app/bidder/auctions/my-bids">
                                        Open list
                                    </a>
                                </div>

                                <div class="activity-row">
                                    <div>
                                        <div class="activity-title">Vintage camera listing</div>
                                        <div class="activity-meta">Updated 18 minutes ago</div>
                                    </div>
                                    <span class="status-chip status-open">Watching</span>
                                </div>

                                <div class="activity-row">
                                    <div>
                                        <div class="activity-title">Mechanical keyboard auction</div>
                                        <div class="activity-meta">Your bid is still active</div>
                                    </div>
                                    <span class="status-chip status-watch">In play</span>
                                </div>

                                <div class="activity-row">
                                    <div>
                                        <div class="activity-title">Collector item bundle</div>
                                        <div class="activity-meta">Bid window closes later today</div>
                                    </div>
                                    <span class="status-chip status-open">Open</span>
                                </div>
                            </div>
                        </div>

                        <div class="col-xl-4">
                            <div class="panel-card panel-card-dark h-100">
                                <div class="panel-kicker">Next Move</div>
                                <h2 class="panel-title">Focus on the listings with the best timing.</h2>
                                <p class="panel-copy mb-4">
                                    Move from discovery to action quickly once your package is ready and the right listing appears.
                                </p>

                                <div class="highlight-bid">3 priority items</div>
                                <div class="muted-light mb-3">worth checking before the next refresh</div>

                                <ul class="mini-list">
                                    <li>Review newly opened listings this afternoon.</li>
                                    <li>Watch approval status before placing higher bids.</li>
                                    <li>Keep your active bids monitored in one place.</li>
                                </ul>
                            </div>
                        </div>

                        <div class="col-xl-7">
                            <div class="panel-card h-100">
                                <div class="d-flex justify-content-between align-items-start mb-3">
                                    <div>
                                        <div class="panel-kicker">Auction Spotlight</div>
                                        <h2 class="panel-title mb-0">A cleaner way to keep your bidder flow moving</h2>
                                    </div>
                                    <span class="status-chip status-open">Live market</span>
                                </div>

                                <p class="panel-copy mb-4">
                                    Use the bidder dashboard as your launch point: browse fresh listings, track the bids that matter,
                                    and move into your package request workflow without losing context.
                                </p>

                                <div class="row g-3">
                                    <div class="col-md-4">
                                        <div class="quick-link-tile">
                                            <div class="quick-link-title">Browse Live Listings</div>
                                            <div class="quick-link-copy">Jump into the auction feed and review available items.</div>
                                        </div>
                                    </div>
                                    <div class="col-md-4">
                                        <div class="quick-link-tile">
                                            <div class="quick-link-title">Check My Bids</div>
                                            <div class="quick-link-copy">See where your attention is needed right now.</div>
                                        </div>
                                    </div>
                                    <div class="col-md-4">
                                        <div class="quick-link-tile">
                                            <div class="quick-link-title">Manage Packages</div>
                                            <div class="quick-link-copy">Follow request approvals and prepare your next step.</div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="col-xl-5">
                            <div class="panel-card h-100">
                                <div class="panel-kicker">Quick Access</div>
                                <h2 class="panel-title">Jump straight into the bidder tools</h2>

                                <div class="quick-links-grid mt-4">
                                    <a class="quick-link-tile"
                                       href="${pageContext.request.contextPath}/app/bidder/auctions/list">
                                        <div class="quick-link-title">Browse Auctions</div>
                                        <div class="quick-link-copy">Explore the current market window.</div>
                                    </a>

                                    <a class="quick-link-tile"
                                       href="${pageContext.request.contextPath}/app/bidder/package/list">
                                        <div class="quick-link-title">Package Requests</div>
                                        <div class="quick-link-copy">Track request-based bid access.</div>
                                    </a>

                                    <a class="quick-link-tile"
                                       href="${pageContext.request.contextPath}/app/bidder/auctions/my-bids">
                                        <div class="quick-link-title">My Bids</div>
                                        <div class="quick-link-copy">Review what is active, open, or worth updating.</div>
                                    </a>

                                    <a class="quick-link-tile"
                                       href="${pageContext.request.contextPath}/app/news">
                                        <div class="quick-link-title">Announcements</div>
                                        <div class="quick-link-copy">Check platform and auction updates.</div>
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
