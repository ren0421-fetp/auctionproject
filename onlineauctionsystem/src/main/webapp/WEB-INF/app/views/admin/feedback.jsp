<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="activeNav" value="feedback" />
<c:set var="homePath" value="/app/admin/home" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>View Feedback</title>

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
                <div class="workspace-section">
                    <div class="workspace-hero mb-4">
                        <div>
                            <p class="workspace-eyebrow">View Feedback</p>
                            <h1 class="workspace-title">Read platform feedback</h1>
                            <p class="workspace-subtitle">
                                Review the comments and concerns users send through the public feedback flow.
                            </p>
                        </div>
                    </div>

                    <div class="data-card">
                        <c:choose>
                            <c:when test="${empty feedbackList}">
                                <div class="empty-state">
                                    <h2 class="h5 mb-2">No feedback found</h2>
                                    <p class="mb-0">Feedback will appear here once users start submitting messages.</p>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="table-responsive">
                                    <table class="workspace-table">
                                        <thead>
                                            <tr>
                                                <th>Feedback Id</th>
                                                <th>First Name</th>
                                                <th>Email</th>
                                                <th>Contact</th>
                                                <th>Subject</th>
                                                <th>Message</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="feedback" items="${feedbackList}">
                                                <tr>
                                                    <td><c:out value="${feedback.feedbackId}" /></td>
                                                    <td><c:out value="${feedback.firstName}" /></td>
                                                    <td><c:out value="${feedback.email}" /></td>
                                                    <td><c:out value="${feedback.contact}" /></td>
                                                    <td><c:out value="${feedback.subject}" /></td>
                                                    <td><c:out value="${feedback.msg}" /></td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </div>
    </div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
