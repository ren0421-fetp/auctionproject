<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="activeNav" value="news" />
<c:set var="homePath" value="/app/admin/home" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Manage News</title>

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
                            <p class="workspace-eyebrow">Manage News</p>
                            <h1 class="workspace-title">Publish platform announcements</h1>
                            <p class="workspace-subtitle">
                                Keep bidders and sellers informed with clear news items and important updates.
                            </p>
                        </div>
                    </div>

                    <c:if test="${not empty newsSuccess}">
                        <div class="alert-soft-success"><c:out value="${newsSuccess}" /></div>
                    </c:if>

                    <c:if test="${not empty newsError}">
                        <div class="alert-soft-error"><c:out value="${newsError}" /></div>
                    </c:if>

                    <div class="panel-card mb-4">
                        <div class="panel-kicker">
                            <c:choose>
                                <c:when test="${editMode}">Update News</c:when>
                                <c:otherwise>Add News</c:otherwise>
                            </c:choose>
                        </div>
                        <h2 class="panel-title">
                            <c:choose>
                                <c:when test="${editMode}">Update news</c:when>
                                <c:otherwise>Add news here</c:otherwise>
                            </c:choose>
                        </h2>

                        <form:form modelAttribute="newsForm"
                            method="post"
                            action="${pageContext.request.contextPath}/app/admin/news/save">
                            <form:hidden path="newsId"/>

                            <div class="row g-3">
                                <div class="col-12">
                                    <form:label path="newsTitle" cssClass="filter-label">News Title</form:label>
                                    <form:input path="newsTitle" cssClass="app-input"/>
                                    <form:errors path="newsTitle" cssClass="field-error" element="div"/>
                                </div>

                                <div class="col-12">
                                    <form:label path="newsContent" cssClass="filter-label">News Content</form:label>
                                    <form:textarea path="newsContent" rows="5" cssClass="app-textarea"/>
                                    <form:errors path="newsContent" cssClass="field-error" element="div"/>
                                </div>
                            </div>

                            <div class="mt-4">
                                <button type="submit" class="btn btn-dark rounded-pill px-4">
                                    <c:choose>
                                        <c:when test="${editMode}">Update News</c:when>
                                        <c:otherwise>Add News</c:otherwise>
                                    </c:choose>
                                </button>
                            </div>
                        </form:form>
                    </div>

                    <div class="data-card">
                        <div class="panel-kicker">Current News</div>
                        <h2 class="panel-title">Published items</h2>

                        <div class="table-responsive">
                            <table class="workspace-table">
                                <thead>
                                    <tr>
                                        <th>Action</th>
                                        <th>News Id</th>
                                        <th>News Title</th>
                                        <th>News Content</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="news" items="${newsList}">
                                        <tr>
                                            <td>
                                                <div class="d-flex gap-2 flex-wrap">
                                                    <a class="btn btn-sm btn-dark rounded-pill px-3"
                                                       href="${pageContext.request.contextPath}/app/admin/news?newsId=${news.newsId}">
                                                        Update
                                                    </a>
                                                    <form method="post"
                                                          action="${pageContext.request.contextPath}/app/admin/news/delete"
                                                          class="m-0">
                                                        <input type="hidden" name="newsId" value="${news.newsId}" />
                                                        <button type="submit" class="btn btn-sm btn-light border rounded-pill px-3">
                                                            Delete
                                                        </button>
                                                    </form>
                                                </div>
                                            </td>
                                            <td><c:out value="${news.newsId}" /></td>
                                            <td><c:out value="${news.newsTitle}" /></td>
                                            <td><c:out value="${news.newsContent}" /></td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
