<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="activeNav" value="users" />
<c:set var="homePath" value="/app/admin/home" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Manage User</title>

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
                            <p class="workspace-eyebrow">Manage User</p>
                            <h1 class="workspace-title">Review platform accounts</h1>
                            <p class="workspace-subtitle">
                                Filter users by role, inspect current status, and lock or unlock access when needed.
                            </p>
                        </div>
                    </div>

                    <div class="filter-card mb-4">
                        <form method="get" action="${pageContext.request.contextPath}/app/admin/users">
                            <div class="filter-toolbar">
                                <div class="filter-group">
                                    <label for="userType" class="filter-label">Filter by Role</label>
                                    <select id="userType" name="userType" class="app-select">
                                        <option value="">-- all users --</option>
                                        <option value="admin" <c:if test="${selectedUserType == 'admin'}">selected</c:if>>admin</option>
                                        <option value="seller" <c:if test="${selectedUserType == 'seller'}">selected</c:if>>seller</option>
                                        <option value="bidder" <c:if test="${selectedUserType == 'bidder'}">selected</c:if>>bidder</option>
                                    </select>
                                </div>

                                <div class="d-flex gap-2 flex-wrap">
                                    <button type="submit" class="btn btn-dark rounded-pill px-4">Filter</button>
                                    <a class="btn btn-light border rounded-pill px-4"
                                       href="${pageContext.request.contextPath}/app/admin/users">
                                        Clear
                                    </a>
                                </div>
                            </div>
                        </form>
                    </div>

                    <c:if test="${not empty userActionSuccess}">
                        <div class="alert-soft-success"><c:out value="${userActionSuccess}" /></div>
                    </c:if>

                    <c:if test="${not empty userActionError}">
                        <div class="alert-soft-error"><c:out value="${userActionError}" /></div>
                    </c:if>

                    <div class="data-card">
                        <c:choose>
                            <c:when test="${empty users}">
                                <div class="empty-state">
                                    <h2 class="h5 mb-2">No users found</h2>
                                    <p class="mb-0">Try changing the role filter or return when more accounts are available.</p>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="table-responsive">
                                    <table class="workspace-table">
                                        <thead>
                                            <tr>
                                                <th>Action</th>
                                                <th>Username</th>
                                                <th>Gender</th>
                                                <th>Contact</th>
                                                <th>Address</th>
                                                <th>City</th>
                                                <th>User Type</th>
                                                <th>Lock Status</th>
                                                <th>Photo</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="user" items="${users}">
                                                <tr>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${user.isLocked}">
                                                                <form method="post"
                                                                      action="${pageContext.request.contextPath}/app/admin/users/unlock"
                                                                      class="m-0">
                                                                    <input type="hidden" name="username" value="${user.username}" />
                                                                    <button type="submit" class="btn btn-sm btn-dark rounded-pill px-3">
                                                                        Unlock
                                                                    </button>
                                                                </form>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <form method="post"
                                                                      action="${pageContext.request.contextPath}/app/admin/users/lock"
                                                                      class="m-0">
                                                                    <input type="hidden" name="username" value="${user.username}" />
                                                                    <button type="submit" class="btn btn-sm btn-light border rounded-pill px-3">
                                                                        Lock
                                                                    </button>
                                                                </form>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                    <td><c:out value="${user.username}" /></td>
                                                    <td><c:out value="${user.gender}" /></td>
                                                    <td><c:out value="${user.contactNo}" /></td>
                                                    <td><c:out value="${user.address}" /></td>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${not empty user.cityName}">
                                                                <c:out value="${user.cityName}" />
                                                            </c:when>
                                                            <c:otherwise>
                                                                <c:out value="${user.cityId}" />
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                    <td><span class="badge-soft"><c:out value="${user.userType}" /></span></td>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${user.isLocked}">
                                                                <span class="status-chip status-loss">Locked</span>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <span class="status-chip status-open">Active</span>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${not empty user.photoPath}">
                                                                <img src="${pageContext.request.contextPath}${user.photoPath}"
                                                                     alt="User Photo"
                                                                     class="table-thumb" />
                                                            </c:when>
                                                            <c:otherwise>
                                                                <div class="table-thumb d-flex align-items-center justify-content-center small text-muted">
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
