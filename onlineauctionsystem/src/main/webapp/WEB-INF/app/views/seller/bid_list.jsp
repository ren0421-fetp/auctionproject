<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="activeNav" value="bids" />
<c:set var="homePath" value="/app/seller/home" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Seller Bids</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="${pageContext.request.contextPath}/app/cssPath/app-layout.css">
</head>
<body class="app-workspace">
    <%@ include file="/WEB-INF/app/views/fragments/app_header.jspf" %>

    <div class="container-fluid app-shell">
        <div class="row g-4">
            <div class="col-xl-3 col-xxl-2 sidebar-col">
                <%@ include file="/WEB-INF/app/views/fragments/sidebar_seller.jspf" %>
            </div>

            <div class="col-xl-9 col-xxl-10">
                <div class="workspace-section">
                    <div class="workspace-hero mb-4">
                        <div>
                            <p class="workspace-eyebrow">Bid Activity</p>
                            <h1 class="workspace-title">Bids on your products</h1>
                            <p class="workspace-subtitle">
                                Monitor bidder interest, follow outcomes, and keep track of winning confirmations.
                            </p>
                        </div>

                        <div class="workspace-actions">
                            <a class="btn btn-light border rounded-pill px-4"
                               href="${pageContext.request.contextPath}/app/seller/product/list">
                                My Products
                            </a>
                            <a class="btn btn-dark rounded-pill px-4"
                               href="${pageContext.request.contextPath}/app/seller/product/add">
                                Add Product
                            </a>
                        </div>
                    </div>

                    <div class="data-card">
                        <c:choose>
                            <c:when test="${empty sellerBids}">
                                <div class="empty-state">
                                    <h2 class="h5 mb-2">No bids yet</h2>
                                    <p class="mb-0">Once bidders interact with your listings, the activity will appear here.</p>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="table-responsive">
                                    <table class="workspace-table">
                                        <thead>
                                            <tr>
                                                <th>Product</th>
                                                <th>Bidder</th>
                                                <th>Minimum Bid</th>
                                                <th>Bid Price</th>
                                                <th>Bid Date</th>
                                                <th>Photo</th>
                                                <th>Result</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="bid" items="${sellerBids}">
                                                <tr>
                                                    <td>
                                                        <div class="fw-semibold"><c:out value="${bid.productName}" /></div>
                                                        <div class="activity-meta">
                                                            Product #<c:out value="${bid.productId}" /> | Bid #<c:out value="${bid.bidId}" />
                                                        </div>
                                                    </td>
                                                    <td><c:out value="${bid.bidderUsername}" /></td>
                                                    <td><c:out value="${bid.minBidPrice}" /></td>
                                                    <td><c:out value="${bid.bidPrice}" /></td>
                                                    <td><c:out value="${bid.bidDate}" /></td>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${not empty bid.productPhotoPath}">
                                                                <img src="${pageContext.request.contextPath}${bid.productPhotoPath}"
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
                                                        <c:choose>
                                                            <c:when test="${bid.productConfirmed}">
                                                                <c:choose>
                                                                    <c:when test="${bid.bidderUsername == bid.confirmedWinnerUsername}">
                                                                        <span class="status-chip status-win">Winner</span>
                                                                        <div class="activity-meta mt-2">
                                                                            <c:out value="${bid.confirmedPrice}" />
                                                                        </div>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <span class="status-chip status-loss">Lost</span>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                                <div class="activity-meta mt-2">
                                                                    Confirmed:
                                                                    <c:out value="${bid.confirmedAt}" />
                                                                </div>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <span class="status-chip status-pending">Pending</span>
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
