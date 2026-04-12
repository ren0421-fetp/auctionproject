<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Auction News</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="${pageContext.request.contextPath}/cssPath/app-layout.css">
</head>
<body>
    <div class="container py-4">
        <div class="content-panel">
            <div class="d-flex justify-content-between align-items-center mb-4">
                <div>
                    <h1 class="h3 page-title">Auction News</h1>
                    <p class="page-subtitle mb-0">Latest announcements and updates from admin.</p>
                </div>
                <a class="btn btn-outline-secondary btn-sm" href="javascript:history.back()">Back</a>
            </div>

            <c:choose>
                <c:when test="${empty newsList}">
                    <div class="alert alert-light border">No news available.</div>
                </c:when>
                <c:otherwise>
                    <div class="list-group">
                        <c:forEach var="news" items="${newsList}">
                            <div class="list-group-item py-3">
                                <h2 class="h6 mb-1"><c:out value="${news.newsTitle}" /></h2>
                                <p class="mb-0 text-muted"><c:out value="${news.newsContent}" /></p>
                            </div>
                        </c:forEach>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</body>
</html>
