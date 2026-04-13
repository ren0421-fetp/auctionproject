<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="activeNav" value="packages" />
<c:set var="homePath" value="/app/bidder/home" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Bid Packages</title>

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
                <div class="workspace-section">
                    <div class="workspace-hero mb-4">
                        <div>
                            <p class="workspace-eyebrow">Package Requests</p>
                            <h1 class="workspace-title">Manage your bid access</h1>
                            <p class="workspace-subtitle">
                                Package requests require admin approval before additional bid credits are added to your account.
                            </p>
                        </div>

                        <div class="workspace-actions">
                            <div class="badge-soft">
                                Remaining Bid Count:
                                <c:choose>
                                    <c:when test="${not empty remainingBidCount}">
                                        &nbsp;<c:out value="${remainingBidCount}" />
                                    </c:when>
                                    <c:otherwise>&nbsp;0</c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>

                    <c:if test="${not empty purchaseSuccess}">
                        <div class="alert-soft-success"><c:out value="${purchaseSuccess}" /></div>
                    </c:if>

                    <c:if test="${not empty purchaseError}">
                        <div class="alert-soft-error"><c:out value="${purchaseError}" /></div>
                    </c:if>

                    <div class="panel-card mb-4">
                        <div class="panel-kicker">Request Flow</div>
                        <h2 class="panel-title">How package requests work</h2>
                        <p class="panel-copy mb-0">
                            Submit a request, wait for admin approval, and once approved your bid count will update automatically.
                        </p>
                    </div>

                    <div class="data-card">
                        <c:choose>
                            <c:when test="${empty availablePackages}">
                                <div class="empty-state">
                                    <h2 class="h5 mb-2">No packages available</h2>
                                    <p class="mb-0">There are no packages ready for request right now.</p>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="table-responsive">
                                    <table class="workspace-table">
                                        <thead>
                                            <tr>
                                                <th>Package</th>
                                                <th>Price</th>
                                                <th>Allowed Bid Count</th>
                                                <th>Photo</th>
                                                <th>Action</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="pkg" items="${availablePackages}">
                                                <tr>
                                                    <td>
                                                        <div class="fw-semibold"><c:out value="${pkg.packageName}" /></div>
                                                        <div class="activity-meta">Package #<c:out value="${pkg.packageId}" /></div>
                                                    </td>
                                                    <td><c:out value="${pkg.packagePrice}" /></td>
                                                    <td><c:out value="${pkg.allowedBidCount}" /></td>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${not empty pkg.photoPath}">
                                                                <img src="${pageContext.request.contextPath}${pkg.photoPath}"
                                                                     alt="Package Photo"
                                                                     class="table-thumb" />
                                                            </c:when>
                                                            <c:otherwise>
                                                                <div class="table-thumb d-flex align-items-center justify-content-center small text-muted">
                                                                    No image
                                                                </div>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                    <td>
                                                        <form method="post"
                                                              action="${pageContext.request.contextPath}/app/bidder/package/purchase"
                                                              class="m-0">
                                                            <input type="hidden" name="packageId" value="${pkg.packageId}" />
                                                            <button type="submit" class="btn btn-dark btn-sm rounded-pill px-3">
                                                                Request Purchase
                                                            </button>
                                                        </form>
                                                    </td>
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
