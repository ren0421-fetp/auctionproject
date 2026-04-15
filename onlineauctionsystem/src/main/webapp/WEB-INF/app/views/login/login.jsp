<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Sign In</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="${pageContext.request.contextPath}/app/cssPath/public-theme.css">
</head>
<body class="auction-public-page auth-page">
    <div class="page-orb orb-left"></div>
    <div class="page-orb orb-right"></div>
    <div class="page-orb orb-bottom"></div>

    <nav class="navbar navbar-expand-lg bg-transparent pt-4">
        <div class="container">
            <a class="navbar-brand brand-mark" href="${pageContext.request.contextPath}/app/home">
                Online Auction
            </a>

            <div class="d-flex gap-2">
                <a class="btn btn-light border rounded-pill px-4 py-2 nav-action active-auth-link"
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
            <section class="auth-shell">
                <div class="row g-4 align-items-center">
                    <div class="col-lg-6">
                        <div class="auth-copy pe-lg-4">
                            <span class="badge rounded-pill hero-badge px-4 py-2 mb-4">
                                Welcome Back
                            </span>

                            <h1 class="auth-title mb-4">
                                Step back into your
                                <span class="headline-accent">auction space</span>
                            </h1>

                            <p class="auth-lead mb-4">
                                Sign in to manage listings, monitor bids, review packages, and continue where you left off.
                            </p>

                            <div class="auth-feature-list">
                                <div class="auth-feature-card">
                                    <div class="auth-feature-title">Admin</div>
                                    <div class="auth-feature-copy">Oversee users, packages, reports, and confirmations.</div>
                                </div>
                                <div class="auth-feature-card auth-feature-card-shift">
                                    <div class="auth-feature-title">Seller</div>
                                    <div class="auth-feature-copy">Launch products, view bids, and manage your profile.</div>
                                </div>
                                <div class="auth-feature-card">
                                    <div class="auth-feature-title">Bidder</div>
                                    <div class="auth-feature-copy">Browse auctions, request packages, and track bids.</div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="col-lg-6">
                        <div class="auth-panel">
                            <div class="auth-panel-header mb-4">
                                <div class="workspace-kicker">SIGN IN</div>
                                <h2 class="workspace-title mb-2">Access your account</h2>
                                <p class="preview-copy mb-0">
                                    Enter your username and password to continue.
                                </p>
                            </div>

                            <form:form modelAttribute="loginForm" method="post">
                                <div class="mb-3">
                                    <form:label path="username" cssClass="form-label auth-label">Username</form:label>
                                    <form:input path="username" cssClass="form-control form-control-lg auth-input" />
                                    <form:errors path="username" cssClass="auth-error" element="div" />
                                </div>

                                <div class="mb-3">
                                    <form:label path="password" cssClass="form-label auth-label">Password</form:label>
                                    <form:password path="password" cssClass="form-control form-control-lg auth-input" />
                                    <form:errors path="password" cssClass="auth-error" element="div" />
                                </div>

                                <div class="d-grid gap-3 mt-4">
                                    <button type="submit" class="btn btn-dark btn-lg rounded-pill auth-submit">
                                        Sign In
                                    </button>
                                </div>
                            </form:form>

                            <div class="auth-footer-note mt-4">
                                No account yet?
                                <a href="${pageContext.request.contextPath}/app/registration" class="auth-link">
                                    Create one here
                                </a>
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
