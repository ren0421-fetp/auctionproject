<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Auction News</title>

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/app/cssPath/public-theme.css">
</head>
<body class="auction-public-page auth-page">
	<div class="page-orb orb-left"></div>
	<div class="page-orb orb-right"></div>
	<div class="page-orb orb-bottom"></div>

	<nav class="navbar navbar-expand-lg bg-transparent pt-4">
		<div class="container">
			<a class="navbar-brand brand-mark"
				href="${pageContext.request.contextPath}/app/home"> Online
				Auction </a>

			<div class="d-flex gap-2 flex-wrap">
				<c:choose>
					<c:when test="${not empty loggedInUser}">
						<a class="btn btn-light border rounded-pill px-4 py-2 nav-action"
							href="${pageContext.request.contextPath}/app/feedback">
							Feedback </a>
						<a class="btn btn-light border rounded-pill px-4 py-2 nav-action"
							href="${pageContext.request.contextPath}/app/${loggedInUser.userType}/home">
							Workspace </a>
						<a class="btn btn-dark rounded-pill px-4 py-2 nav-action"
							href="${pageContext.request.contextPath}/app/logout"> Logout
						</a>
					</c:when>
					<c:otherwise>
						<a class="btn btn-light border rounded-pill px-4 py-2 nav-action"
							href="${pageContext.request.contextPath}/app/home"> Home </a>
						<a class="btn btn-light border rounded-pill px-4 py-2 nav-action"
							href="${pageContext.request.contextPath}/app/feedback">
							Feedback </a>
						<a class="btn btn-dark rounded-pill px-4 py-2 nav-action"
							href="${pageContext.request.contextPath}/app/login"> Sign In
						</a>
					</c:otherwise>
				</c:choose>
			</div>


		</div>
	</nav>

	<main class="pt-3 pb-5">
		<div class="container">
			<section class="auth-shell">
				<div class="row g-4 align-items-start">
					<div class="col-lg-5">
						<div class="auth-copy pe-lg-4">
							<span class="badge rounded-pill hero-badge px-4 py-2 mb-4">
								Auction Updates </span>

							<h1 class="auth-title mb-4">
								Stay in sync with the <span class="headline-accent">latest
									news</span>
							</h1>

							<p class="auth-lead mb-4">Read announcements, platform
								updates, and important auction notices published by the admin
								team.</p>

							<div class="auth-feature-list">
								<div class="auth-feature-card">
									<div class="auth-feature-title">Announcements</div>
									<div class="auth-feature-copy">Important notices that
										affect the marketplace.</div>
								</div>
								<div class="auth-feature-card auth-feature-card-shift">
									<div class="auth-feature-title">Auction Updates</div>
									<div class="auth-feature-copy">Changes that matter to
										sellers and bidders.</div>
								</div>
								<div class="auth-feature-card">
									<div class="auth-feature-title">Platform Flow</div>
									<div class="auth-feature-copy">Stay informed before you
										sign in and continue working.</div>
								</div>
							</div>
						</div>
					</div>

					<div class="col-lg-7">
						<div class="auth-panel auth-panel-register">
							<div class="auth-panel-header mb-4">
								<div class="workspace-kicker">NEWS FEED</div>
								<h2 class="workspace-title mb-2">Latest announcements</h2>
								<p class="preview-copy mb-0">Recent items from the platform
									team.</p>
							</div>

							<c:choose>
								<c:when test="${empty newsList}">
									<div class="alert-soft-info mb-0">No news available at
										the moment.</div>
								</c:when>
								<c:otherwise>
									<div class="d-grid gap-3">
										<c:forEach var="news" items="${newsList}">
											<div class="auth-feature-card">
												<div class="auth-feature-title mb-2">
													<c:out value="${news.newsTitle}" />
												</div>
												<div class="auth-feature-copy">
													<c:out value="${news.newsContent}" />
												</div>
											</div>
										</c:forEach>
									</div>
								</c:otherwise>
							</c:choose>

							<div class="auth-footer-note mt-4">
								Want to ask something directly? <a
									href="${pageContext.request.contextPath}/app/feedback"
									class="auth-link"> Send feedback here </a>
							</div>
						</div>
					</div>
				</div>
			</section>
		</div>
	</main>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
