<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="activeNav" value="user-packages" />
<c:set var="homePath" value="/app/admin/home" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Manage User Package</title>

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
                            <p class="workspace-eyebrow">User Packages</p>
                            <h1 class="workspace-title">Approve requests and assign packages</h1>
                            <p class="workspace-subtitle">
                                Review pending bidder requests, handle admin assignment, and track granted package balances.
                            </p>
                        </div>
                    </div>

                    <c:if test="${not empty assignSuccess}">
                        <div class="alert-soft-success"><c:out value="${assignSuccess}" /></div>
                    </c:if>

                    <c:if test="${not empty assignError}">
                        <div class="alert-soft-error"><c:out value="${assignError}" /></div>
                    </c:if>

                    <div class="panel-card mb-4">
                        <div class="panel-kicker">Pending Package Requests</div>
                        <h2 class="panel-title">Approval queue</h2>

                        <c:choose>
                            <c:when test="${empty packagePurchaseRequests}">
                                <div class="empty-state">
                                    <p class="mb-0">No package purchase requests found.</p>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="table-responsive">
                                    <table class="workspace-table">
                                        <thead>
                                            <tr>
                                                <th>Request</th>
                                                <th>User</th>
                                                <th>Package</th>
                                                <th>Price</th>
                                                <th>Allowed Bid Count</th>
                                                <th>Status</th>
                                                <th>Requested At</th>
                                                <th>Reviewed By</th>
                                                <th>Reviewed At</th>
                                                <th>Remarks</th>
                                                <th>Action</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="request" items="${packagePurchaseRequests}">
                                                <tr>
                                                    <td>#<c:out value="${request.requestId}" /></td>
                                                    <td><c:out value="${request.username}" /></td>
                                                    <td><c:out value="${request.packageName}" /></td>
                                                    <td><c:out value="${request.packagePrice}" /></td>
                                                    <td><c:out value="${request.allowedBidCount}" /></td>
                                                    <td><span class="badge-soft"><c:out value="${request.requestStatus}" /></span></td>
                                                    <td><c:out value="${request.requestedAt}" /></td>
                                                    <td><c:out value="${request.reviewedBy}" /></td>
                                                    <td><c:out value="${request.reviewedAt}" /></td>
                                                    <td><c:out value="${request.remarks}" /></td>
                                                    <td>
                                                        <c:if test="${request.requestStatus == 'PENDING'}">
                                                            <div class="d-flex flex-column gap-2">
                                                                <form method="post"
                                                                      action="${pageContext.request.contextPath}/app/admin/user-packages/requests/approve"
                                                                      class="m-0">
                                                                    <input type="hidden" name="requestId" value="${request.requestId}" />
                                                                    <button type="submit" class="btn btn-sm btn-dark rounded-pill px-3">
                                                                        Approve
                                                                    </button>
                                                                </form>

                                                                <form method="post"
                                                                      action="${pageContext.request.contextPath}/app/admin/user-packages/requests/reject"
                                                                      class="m-0">
                                                                    <input type="hidden" name="requestId" value="${request.requestId}" />
                                                                    <input type="text" name="remarks" placeholder="Reject reason (optional)" class="app-input mb-2" />
                                                                    <button type="submit" class="btn btn-sm btn-light border rounded-pill px-3">
                                                                        Reject
                                                                    </button>
                                                                </form>
                                                            </div>
                                                        </c:if>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <div class="panel-card mb-4">
                        <div class="panel-kicker">Assign Package Manually</div>
                        <h2 class="panel-title">Direct admin assignment</h2>

                        <form:form modelAttribute="userPackageForm"
                            method="post"
                            action="${pageContext.request.contextPath}/app/admin/user-packages/assign">

                            <div class="row g-3">
                                <div class="col-md-6">
                                    <form:label path="username" cssClass="filter-label">Username</form:label>
                                    <form:select path="username" cssClass="app-select">
                                        <form:option value="" label="-- select bidder --"/>
                                        <c:forEach var="bidder" items="${bidders}">
                                            <option value="${bidder.username}"
                                                <c:if test="${userPackageForm.username == bidder.username}">selected</c:if>>
                                                <c:out value="${bidder.username}" />
                                            </option>
                                        </c:forEach>
                                    </form:select>
                                    <form:errors path="username" cssClass="field-error" element="div"/>
                                </div>

                                <div class="col-md-6">
                                    <form:label path="packageId" cssClass="filter-label">Package</form:label>
                                    <form:select path="packageId" cssClass="app-select">
                                        <form:option value="" label="-- select package --"/>
                                        <c:forEach var="pkg" items="${packages}">
                                            <option value="${pkg.packageId}"
                                                <c:if test="${userPackageForm.packageId == pkg.packageId}">selected</c:if>>
                                                <c:out value="${pkg.packageName}" /> - <c:out value="${pkg.packagePrice}" />
                                            </option>
                                        </c:forEach>
                                    </form:select>
                                    <form:errors path="packageId" cssClass="field-error" element="div"/>
                                </div>
                            </div>

                            <div class="mt-4">
                                <button type="submit" class="btn btn-dark rounded-pill px-4">
                                    Assign Package
                                </button>
                            </div>
                        </form:form>
                    </div>

                    <div class="data-card">
                        <div class="panel-kicker">Granted User Packages</div>
                        <h2 class="panel-title">Approved package history</h2>

                        <c:choose>
                            <c:when test="${empty userPackageInfos}">
                                <div class="empty-state">
                                    <p class="mb-0">No granted user package records found.</p>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="table-responsive">
                                    <table class="workspace-table">
                                        <thead>
                                            <tr>
                                                <th>Action</th>
                                                <th>Package Name</th>
                                                <th>Price</th>
                                                <th>User Package Id</th>
                                                <th>Package Id</th>
                                                <th>Username</th>
                                                <th>Remaining Bid Balance</th>
                                                <th>Photo</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="info" items="${userPackageInfos}">
                                                <tr>
                                                    <td>
                                                        <a class="btn btn-sm btn-dark rounded-pill px-3"
                                                           href="${pageContext.request.contextPath}/app/admin/user-packages?userPackageId=${info.userPackageId}">
                                                            Select
                                                        </a>
                                                    </td>
                                                    <td><c:out value="${info.packageName}" /></td>
                                                    <td><c:out value="${info.packagePrice}" /></td>
                                                    <td><c:out value="${info.userPackageId}" /></td>
                                                    <td><c:out value="${info.packageId}" /></td>
                                                    <td><c:out value="${info.username}" /></td>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${not empty info.remainingBidCount}">
                                                                <c:out value="${info.remainingBidCount}" />
                                                            </c:when>
                                                            <c:otherwise>0</c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${not empty info.photoPath}">
                                                                <img src="${pageContext.request.contextPath}${info.photoPath}"
                                                                     alt="Package Photo"
                                                                     class="table-thumb"
                                                                     style="width:70px; height:110px;" />
                                                            </c:when>
                                                            <c:otherwise>
                                                                <div class="table-thumb d-flex align-items-center justify-content-center small text-muted"
                                                                     style="width:70px; height:110px;">
                                                                    No image
                                                                </div>
                                                            </c:otherwise>
                                                        </c:choose>
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
