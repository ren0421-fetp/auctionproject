<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="activeNav" value="categories" />
<c:set var="homePath" value="/app/admin/home" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Manage Categories</title>

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
                            <p class="workspace-eyebrow">Manage Categories</p>
                            <h1 class="workspace-title">Keep product grouping organized</h1>
                            <p class="workspace-subtitle">
                                Create and maintain the category structure sellers and bidders rely on across the marketplace.
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
                        <div class="panel-kicker">
                            <c:choose>
                                <c:when test="${editMode}">Update Category</c:when>
                                <c:otherwise>Add Category</c:otherwise>
                            </c:choose>
                        </div>
                        <h2 class="panel-title">
                            <c:choose>
                                <c:when test="${editMode}">Update category</c:when>
                                <c:otherwise>Add category</c:otherwise>
                            </c:choose>
                        </h2>

                        <form:form modelAttribute="categoryForm"
                            method="post"
                            action="${pageContext.request.contextPath}/app/admin/categories/save">

                            <form:hidden path="catId"/>

                            <div class="row g-3">
                                <div class="col-md-6">
                                    <form:label path="catName" cssClass="filter-label">Category Name</form:label>
                                    <form:input path="catName" cssClass="app-input"/>
                                    <form:errors path="catName" cssClass="field-error" element="div"/>
                                </div>
                            </div>

                            <div class="mt-4">
                                <button type="submit" class="btn btn-dark rounded-pill px-4">
                                    <c:choose>
                                        <c:when test="${editMode}">Update Category</c:when>
                                        <c:otherwise>Add Category</c:otherwise>
                                    </c:choose>
                                </button>
                            </div>
                        </form:form>
                    </div>

                    <div class="data-card">
                        <c:choose>
                            <c:when test="${empty categories}">
                                <div class="empty-state">
                                    <h2 class="h5 mb-2">No categories found</h2>
                                    <p class="mb-0">Create a category to start organizing listings.</p>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="table-responsive">
                                    <table class="workspace-table">
                                        <thead>
                                            <tr>
                                                <th>Category Id</th>
                                                <th>Category Name</th>
                                                <th>Action</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="cat" items="${categories}">
                                                <tr>
                                                    <td><c:out value="${cat.catId}" /></td>
                                                    <td><c:out value="${cat.catName}" /></td>
                                                    <td>
                                                        <div class="d-flex gap-2 flex-wrap">
                                                            <a class="btn btn-sm btn-dark rounded-pill px-3"
                                                               href="${pageContext.request.contextPath}/app/admin/categories/edit?catId=${cat.catId}">
                                                                Edit
                                                            </a>

                                                            <form method="post"
                                                                  action="${pageContext.request.contextPath}/app/admin/categories/delete"
                                                                  class="m-0">
                                                                <input type="hidden" name="catId" value="${cat.catId}" />
                                                                <button type="submit" class="btn btn-sm btn-light border rounded-pill px-3">
                                                                    Delete
                                                                </button>
                                                            </form>
                                                        </div>
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
