<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Online Auction</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="${pageContext.request.contextPath}/app/cssPath/public-theme.css">
</head>
<body class="auction-public-page">
    <div class="page-orb orb-left"></div>
    <div class="page-orb orb-right"></div>
    <div class="page-orb orb-bottom"></div>

    <nav class="navbar navbar-expand-lg bg-transparent pt-4">
        <div class="container">
            <a class="navbar-brand brand-mark" href="${pageContext.request.contextPath}/app/home">
                Online Auction System
            </a>

            <div class="d-flex gap-2">
                <a class="btn btn-light border rounded-pill px-4 py-2 nav-action"
                   href="${pageContext.request.contextPath}/app/login">
                    Sign In
                </a>
                <a class="btn btn-dark rounded-pill px-4 py-2 nav-action"
                   href="${pageContext.request.contextPath}/app/registration">
                    Sign Up
                </a>
            </div>
        </div>
    </nav>

    <main class="pt-3 pb-4">
        <div class="container">
            <section class="hero-shell">
                <div class="row align-items-center g-4">
                    <div class="col-lg-7">
                        <span class="badge rounded-pill hero-badge px-4 py-2 mb-4">
                            Premium Auction Space
                        </span>

                        <h1 class="display-1 fw-semibold hero-title mb-4">
                            Discover, Sell, and
                            <br>
                            Manage Auctions with
                            <span class="headline-accent">Clarity</span>
                        </h1>

                        <p class="lead hero-copy mb-4">
                            A polished online auction platform for bidders, sellers, and admins.
                            Track listings, manage packages, review bids, and keep the whole marketplace moving.
                        </p>

                        <div class="d-flex flex-wrap gap-3 mb-5">
                            <a class="btn btn-dark btn-lg rounded-pill px-4 py-3 hero-btn"
                               href="${pageContext.request.contextPath}/app/login">
                                Start with Login
                            </a>
                            <a class="btn btn-light btn-lg rounded-pill px-4 py-3 border hero-btn"
                               href="${pageContext.request.contextPath}/app/registration">
                                Create an Account
                            </a>
                        </div>

                        <div class="row g-4 hero-metrics">
                            <div class="col-4">
                                <div class="metric-number">3</div>
                                <div class="metric-label">User Roles</div>
                            </div>
                            <div class="col-4">
                                <div class="metric-number">Live</div>
                                <div class="metric-label">Auction Flow</div>
                            </div>
                            <div class="col-4">
                                <div class="metric-number">Fast</div>
                                <div class="metric-label">Bid Tracking</div>
                            </div>
                        </div>
                    </div>

                    <div class="col-lg-5">
                        <div class="workspace-panel">
                            <div class="d-flex justify-content-between align-items-start mb-4">
                                <div>
                                    <div class="workspace-kicker">WORKSPACE PREVIEW</div>
                                    <h2 class="workspace-title mb-0">Auction Operations</h2>
                                </div>
                                <span class="workspace-state">Active</span>
                            </div>

                            <div class="preview-stack">
                                <div class="preview-card preview-card-primary">
                                    <div class="d-flex justify-content-between align-items-start mb-3">
                                        <div class="preview-role">Admin Panel</div>
                                        <div class="preview-tag">Admin</div>
                                    </div>
                                    <h3 class="preview-title">Manage packages, users, and reports</h3>
                                    <p class="preview-copy mb-0">
                                        Centralized control for approvals, listings, and auction oversight.
                                    </p>
                                </div>

                                <div class="preview-card preview-card-secondary">
                                    <div class="d-flex justify-content-between align-items-start mb-3">
                                        <div class="preview-role">Seller Panel</div>
                                        <div class="preview-tag">Seller</div>
                                    </div>
                                    <h3 class="preview-title">Launch products and monitor bids</h3>
                                    <p class="preview-copy mb-0">
                                        Keep products organized, check bidder activity, and update your profile.
                                    </p>
                                </div>

                                <div class="preview-card preview-card-tertiary">
                                    <div class="d-flex justify-content-between align-items-start mb-3">
                                        <div class="preview-role">Bidder Panel</div>
                                        <div class="preview-tag">Bidder</div>
                                    </div>
                                    <h3 class="preview-title">Browse auctions and track bids</h3>
                                    <p class="preview-copy mb-0">
                                        Discover items, manage request-based packages, and follow your bidding history.
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </div>
    </main>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
