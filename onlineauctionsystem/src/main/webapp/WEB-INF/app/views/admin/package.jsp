<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="activeNav" value="packages" />
<c:set var="homePath" value="/app/admin/home" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Manage Packages</title>

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
                            <p class="workspace-eyebrow">Manage Packages</p>
                            <h1 class="workspace-title">Control package offerings</h1>
                            <p class="workspace-subtitle">
                                Add, update, and maintain the package options that power bidder access and approvals.
                            </p>
                        </div>
                    </div>

                    <c:if test="${not empty saveSuccess}">
                        <div class="alert-soft-success"><c:out value="${saveSuccess}" /></div>
                    </c:if>

                    <c:if test="${not empty saveError}">
                        <div class="alert-soft-error"><c:out value="${saveError}" /></div>
                    </c:if>

                    <div class="panel-card mb-4">
                        <div class="panel-kicker"><c:choose><c:when test="${editMode}">Update Package</c:when><c:otherwise>Add Package</c:otherwise></c:choose></div>
                        <h2 class="panel-title">
                            <c:choose>
                                <c:when test="${editMode}">Update package information</c:when>
                                <c:otherwise>Add package information</c:otherwise>
                            </c:choose>
                        </h2>

                        <form:form modelAttribute="packageForm"
                            method="post"
                            enctype="multipart/form-data"
                            action="${pageContext.request.contextPath}/app/admin/packages/save">

                            <form:hidden path="packageId"/>
                            <form:hidden path="photoPath"/>
                            <form:hidden path="currentPhotoPath"/>

                            <div class="row g-3">
                                <div class="col-md-6">
                                    <form:label path="packageName" cssClass="filter-label">Package Name</form:label>
                                    <form:input path="packageName" cssClass="app-input"/>
                                    <form:errors path="packageName" cssClass="field-error" element="div"/>
                                </div>

                                <div class="col-md-3">
                                    <form:label path="packagePrice" cssClass="filter-label">Package Price</form:label>
                                    <form:input path="packagePrice" cssClass="app-input"/>
                                    <form:errors path="packagePrice" cssClass="field-error" element="div"/>
                                </div>

                                <div class="col-md-3">
                                    <form:label path="allowedBidCount" cssClass="filter-label">Allowed Bid Count</form:label>
                                    <form:input path="allowedBidCount" cssClass="app-input"/>
                                    <form:errors path="allowedBidCount" cssClass="field-error" element="div"/>
                                </div>

                                <div class="col-md-6">
                                    <label for="photoFile" class="filter-label">Photo</label>
                                    <input type="file" id="photoFile" name="photoFile" class="app-file" />
                                    <form:errors path="photoFile" cssClass="field-error" element="div"/>

                                    <c:if test="${not empty packageForm.currentPhotoPath}">
                                        <div class="mt-3">
                                            <img src="${pageContext.request.contextPath}${packageForm.currentPhotoPath}"
                                                 alt="Current Package Photo"
                                                 class="table-thumb"
                                                 style="width:90px; height:120px;" />
                                        </div>
                                    </c:if>
                                </div>
                            </div>

                            <div class="mt-4">
                                <button type="submit" class="btn btn-dark rounded-pill px-4">
                                    <c:choose>
                                        <c:when test="${editMode}">Update Package</c:when>
                                        <c:otherwise>Add Package</c:otherwise>
                                    </c:choose>
                                </button>
                            </div>
                        </form:form>
                    </div>

                    <div class="data-card">
                        <c:choose>
                            <c:when test="${empty packages}">
                                <div class="empty-state">
                                    <h2 class="h5 mb-2">No packages found</h2>
                                    <p class="mb-0">Create a package and it will appear here for admin review and bidder requests.</p>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="table-responsive">
                                    <table class="workspace-table">
                                        <thead>
                                            <tr>
                                                <th>Action</th>
                                                <th>Package Name</th>
                                                <th>Package Price</th>
                                                <th>Allowed Bid Count</th>
                                                <th>Photo</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="pkg" items="${packages}">
                                                <tr>
                                                    <td>
                                                        <div class="d-flex flex-column gap-2">
                                                            <a class="btn btn-sm btn-dark rounded-pill px-3"
                                                               href="${pageContext.request.contextPath}/app/admin/packages/edit?packageId=${pkg.packageId}">
                                                                Edit
                                                            </a>

                                                            <form method="post"
                                                                  action="${pageContext.request.contextPath}/app/admin/packages/delete"
                                                                  class="m-0">
                                                                <input type="hidden" name="packageId" value="${pkg.packageId}" />
                                                                <button type="submit" class="btn btn-sm btn-light border rounded-pill px-3">
                                                                    Delete
                                                                </button>
                                                            </form>
                                                        </div>
                                                    </td>
                                                    <td><c:out value="${pkg.packageName}" /></td>
                                                    <td><c:out value="${pkg.packagePrice}" /></td>
                                                    <td><c:out value="${pkg.allowedBidCount}" /> bids</td>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${not empty pkg.photoPath}">
                                                                <img src="${pageContext.request.contextPath}${pkg.photoPath}"
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
