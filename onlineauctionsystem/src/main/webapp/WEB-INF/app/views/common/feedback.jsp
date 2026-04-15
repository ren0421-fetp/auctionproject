<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Feedback</title>

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/app/cssPath/public-theme.css">
</head>
<body class="auction-public-page auth-page auth-page-register">
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
							href="${pageContext.request.contextPath}/app/news"> News </a>
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
							href="${pageContext.request.contextPath}/app/news"> News </a>
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
			<section class="auth-shell auth-shell-register">
				<div class="row g-4 align-items-start">
					<div class="col-lg-5">
						<div class="auth-copy pe-lg-4 sticky-register-copy">
							<span class="badge rounded-pill hero-badge px-4 py-2 mb-4">
								Share Feedback </span>

							<h1 class="auth-title mb-4">
								Tell us how the <span class="headline-accent">platform
									feels</span>
							</h1>

							<p class="auth-lead mb-4">Share questions, suggestions, or
								issues so we can improve the experience for bidders, sellers,
								and admins.</p>

							<div class="auth-feature-list">
								<div class="auth-feature-card">
									<div class="auth-feature-title">Questions</div>
									<div class="auth-feature-copy">Ask about platform
										behavior or confusing flows.</div>
								</div>
								<div class="auth-feature-card auth-feature-card-shift">
									<div class="auth-feature-title">Suggestions</div>
									<div class="auth-feature-copy">Tell us what would make
										the experience better.</div>
								</div>
								<div class="auth-feature-card">
									<div class="auth-feature-title">Issues</div>
									<div class="auth-feature-copy">Report anything that feels
										broken, unclear, or inconsistent.</div>
								</div>
							</div>
						</div>
					</div>

					<div class="col-lg-7">
						<div class="auth-panel auth-panel-register">
							<div class="auth-panel-header mb-4">
								<div class="workspace-kicker">FEEDBACK</div>
								<h2 class="workspace-title mb-2">Send a message</h2>
								<p class="preview-copy mb-0">Fill in the form below and
									we’ll capture your message.</p>
							</div>

							<c:if test="${param.success == '1'}">
								<div class="alert-soft-success">Your message was sent
									successfully.</div>
							</c:if>

							<c:if test="${not empty feedbackError}">
								<div class="alert-soft-error">
									<c:out value="${feedbackError}" />
								</div>
							</c:if>

							<form:form modelAttribute="feedbackForm" method="post"
								action="${pageContext.request.contextPath}/app/feedback">

								<div class="row g-3">
									<div class="col-md-6">
										<form:label path="firstName" cssClass="form-label auth-label">First Name</form:label>
										<form:input path="firstName"
											cssClass="form-control auth-input" />
										<form:errors path="firstName" cssClass="auth-error"
											element="div" />
									</div>

									<div class="col-md-6">
										<form:label path="email" cssClass="form-label auth-label">Email</form:label>
										<form:input path="email" cssClass="form-control auth-input" />
										<form:errors path="email" cssClass="auth-error" element="div" />
									</div>

									<div class="col-md-6">
										<form:label path="contact" cssClass="form-label auth-label">Contact</form:label>
										<form:input path="contact" cssClass="form-control auth-input" />
										<form:errors path="contact" cssClass="auth-error"
											element="div" />
									</div>

									<div class="col-md-6">
										<form:label path="subject" cssClass="form-label auth-label">Subject</form:label>
										<form:input path="subject" cssClass="form-control auth-input" />
										<form:errors path="subject" cssClass="auth-error"
											element="div" />
									</div>

									<div class="col-12">
										<form:label path="msg" cssClass="form-label auth-label">Message</form:label>
										<form:textarea path="msg" rows="5"
											cssClass="form-control auth-input auth-textarea" />
										<form:errors path="msg" cssClass="auth-error" element="div" />
									</div>
								</div>

								<div class="d-grid gap-3 mt-4">
									<button type="submit"
										class="btn btn-dark btn-lg rounded-pill auth-submit">
										Submit Feedback</button>
								</div>
							</form:form>

							<div class="auth-footer-note mt-4">
								Want to review announcements first? <a
									href="${pageContext.request.contextPath}/app/news"
									class="auth-link"> View news here </a>
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
