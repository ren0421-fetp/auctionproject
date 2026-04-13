<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="activeNav" value="auctions" />
<c:set var="homePath" value="/app/bidder/home" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Auction Products</title>

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
                            <p class="workspace-eyebrow">Browse Auctions</p>
                            <h1 class="workspace-title">Live and upcoming listings</h1>
                            <p class="workspace-subtitle">
                                Filter the current auction stream, compare bidding conditions, and open the items that deserve attention.
                            </p>
                        </div>

                        <div class="workspace-actions">
                            <a class="btn btn-dark rounded-pill px-4"
                               href="${pageContext.request.contextPath}/app/bidder/auctions/my-bids">
                                My Bids
                            </a>
                        </div>
                    </div>

                    <div class="filter-card mb-4">
                        <form method="get" action="${pageContext.request.contextPath}/app/bidder/auctions/list">
                            <div class="filter-toolbar">
                                <div class="filter-group">
                                    <label for="keyword" class="filter-label">Search Product</label>
                                    <input class="app-input" type="text" id="keyword" name="keyword" value="${keyword}" />
                                </div>

                                <div class="filter-group">
                                    <label for="catId" class="filter-label">Category</label>
                                    <select class="app-select" id="catId" name="catId">
                                        <option value="">-- all categories --</option>
                                        <c:forEach var="cat" items="${categoryOpts}">
                                            <option value="${cat.catId}" <c:if test="${selectedCatId == cat.catId}">selected</c:if>>
                                                <c:out value="${cat.catName}" />
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <div class="filter-group">
                                    <label for="minPrice" class="filter-label">Min Bid From</label>
                                    <input class="app-input" type="text" id="minPrice" name="minPrice" value="${minPrice}" />
                                </div>

                                <div class="filter-group">
                                    <label for="maxPrice" class="filter-label">Min Bid To</label>
                                    <input class="app-input" type="text" id="maxPrice" name="maxPrice" value="${maxPrice}" />
                                </div>

                                <div class="d-flex gap-2 flex-wrap">
                                    <button type="submit" class="btn btn-dark rounded-pill px-4">Search</button>
                                    <a class="btn btn-light border rounded-pill px-4"
                                       href="${pageContext.request.contextPath}/app/bidder/auctions/list">
                                        Clear
                                    </a>
                                </div>
                            </div>
                        </form>
                    </div>

                    <div class="data-card">
                        <c:choose>
                            <c:when test="${empty openProducts}">
                                <div class="empty-state">
                                    <h2 class="h5 mb-2">No auctions found</h2>
                                    <p class="mb-0">Try adjusting your filters or come back once new listings are available.</p>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="table-responsive">
                                    <table class="workspace-table">
                                        <thead>
                                            <tr>
                                                <th>Product</th>
                                                <th>Category</th>
                                                <th>Phase</th>
                                                <th>Minimum Bid</th>
                                                <th>Current Highest</th>
                                                <th>Schedule</th>
                                                <th>Photo</th>
                                                <th>Action</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="product" items="${openProducts}">
                                                <tr>
                                                    <td>
                                                        <div class="fw-semibold"><c:out value="${product.productName}" /></div>
                                                        <div class="activity-meta">ID #<c:out value="${product.productId}" /></div>
                                                    </td>
                                                    <td><c:out value="${product.categoryName}" /></td>
                                                    <td><span class="badge-soft"><c:out value="${product.auctionPhase}" /></span></td>
                                                    <td><c:out value="${product.minBidPrice}" /></td>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${not empty product.currentHighestBid}">
                                                                <c:out value="${product.currentHighestBid}" />
                                                            </c:when>
                                                            <c:otherwise>No bids yet</c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                    <td>
                                                        <div class="fw-semibold"><c:out value="${product.startDate}" /></div>
                                                        <div class="activity-meta">to <c:out value="${product.endDate}" /></div>
                                                    </td>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${not empty product.photoPath}">
                                                                <img src="${pageContext.request.contextPath}${product.photoPath}"
                                                                     alt="Product Photo"
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
                                                        <a class="btn btn-sm btn-dark rounded-pill px-3"
                                                           href="${pageContext.request.contextPath}/app/bidder/auctions/detail?productId=${product.productId}">
                                                            Detail
                                                        </a>
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
