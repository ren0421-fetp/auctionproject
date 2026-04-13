<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="activeNav" value="reports" />
<c:set var="homePath" value="/app/admin/home" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Reports</title>

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
                            <p class="workspace-eyebrow">Reports</p>
                            <h1 class="workspace-title">Download platform summaries</h1>
                            <p class="workspace-subtitle">
                                Generate PDF reports for user registrations, products, bids, and confirmed outcomes.
                            </p>
                        </div>
                    </div>

                    <div class="row g-3">
                        <div class="col-md-6 col-xl-3">
                            <a class="quick-link-tile h-100 d-block"
                               href="${pageContext.request.contextPath}/app/admin/reports/pdf?type=users">
                                <div class="quick-link-title">User Registration Report</div>
                                <div class="quick-link-copy">Export user registration details to PDF.</div>
                            </a>
                        </div>

                        <div class="col-md-6 col-xl-3">
                            <a class="quick-link-tile h-100 d-block"
                               href="${pageContext.request.contextPath}/app/admin/reports/pdf?type=products">
                                <div class="quick-link-title">Auction Item Report</div>
                                <div class="quick-link-copy">Generate the current product and listing report.</div>
                            </a>
                        </div>

                        <div class="col-md-6 col-xl-3">
                            <a class="quick-link-tile h-100 d-block"
                               href="${pageContext.request.contextPath}/app/admin/reports/pdf?type=bids">
                                <div class="quick-link-title">Bid Report</div>
                                <div class="quick-link-copy">Download a PDF of the bidding activity report.</div>
                            </a>
                        </div>

                        <div class="col-md-6 col-xl-3">
                            <a class="quick-link-tile h-100 d-block"
                               href="${pageContext.request.contextPath}/app/admin/reports/pdf?type=confirmed-bids">
                                <div class="quick-link-title">Confirmed Bid Report</div>
                                <div class="quick-link-copy">Export confirmed winning bid results.</div>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
