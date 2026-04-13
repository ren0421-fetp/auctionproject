<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="activeNav" value="auctions" />
<c:set var="homePath" value="/app/bidder/home" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Product Detail</title>

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
                            <p class="workspace-eyebrow">Bid On Product</p>
                            <h1 class="workspace-title"><c:out value="${product.productName}" /></h1>
                            <p class="workspace-subtitle">
                                Review the listing details carefully before placing your bid.
                            </p>
                        </div>

                        <div class="workspace-actions">
                            <a class="btn btn-light border rounded-pill px-4"
                               href="${pageContext.request.contextPath}/app/bidder/auctions/list">
                                Back to Auctions
                            </a>
                        </div>
                    </div>

                    <c:if test="${not empty bidSuccess}">
                        <div class="alert-soft-success"><c:out value="${bidSuccess}" /></div>
                    </c:if>

                    <c:if test="${not empty bidError}">
                        <div class="alert-soft-error"><c:out value="${bidError}" /></div>
                    </c:if>

                    <div class="row g-4">
                        <div class="col-lg-5">
                            <div class="panel-card h-100">
                                <div class="panel-kicker">Listing Preview</div>

                                <c:choose>
                                    <c:when test="${not empty product.photoPath}">
                                        <img src="${pageContext.request.contextPath}${product.photoPath}"
                                             alt="Product Photo"
                                             class="detail-media mb-4" />
                                    </c:when>
                                    <c:otherwise>
                                        <div class="detail-media d-flex align-items-center justify-content-center mb-4 text-muted">
                                            No image available
                                        </div>
                                    </c:otherwise>
                                </c:choose>

                                <div class="meta-grid">
                                    <div class="meta-item">
                                        <div class="meta-label">Category</div>
                                        <div class="meta-value"><c:out value="${product.categoryName}" /></div>
                                    </div>
                                    <div class="meta-item">
                                        <div class="meta-label">Status</div>
                                        <div class="meta-value"><c:out value="${product.status}" /></div>
                                    </div>
                                    <div class="meta-item">
                                        <div class="meta-label">Auction Window</div>
                                        <div class="meta-value">
                                            <c:out value="${product.startDate}" />
                                            <br>
                                            to
                                            <br>
                                            <c:out value="${product.endDate}" />
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="col-lg-7">
                            <div class="panel-card mb-4">
                                <div class="panel-kicker">Bid Summary</div>
                                <div class="row g-3">
                                    <div class="col-md-4">
                                        <div class="meta-item">
                                            <div class="meta-label">Minimum Bid</div>
                                            <div class="meta-value"><c:out value="${product.minBidPrice}" /></div>
                                        </div>
                                    </div>
                                    <div class="col-md-4">
                                        <div class="meta-item">
                                            <div class="meta-label">Current Highest</div>
                                            <div class="meta-value">
                                                <c:choose>
                                                    <c:when test="${not empty product.currentHighestBid}">
                                                        <c:out value="${product.currentHighestBid}" />
                                                    </c:when>
                                                    <c:otherwise>No bids yet</c:otherwise>
                                                </c:choose>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-4">
                                        <div class="meta-item">
                                            <div class="meta-label">Remaining Count</div>
                                            <div class="meta-value"><c:out value="${remainingBidCount}" /></div>
                                        </div>
                                    </div>
                                </div>

                                <div class="mt-4">
                                    <div class="meta-label">Description</div>
                                    <div class="inline-note"><c:out value="${product.description}" /></div>
                                </div>
                            </div>

                            <div class="panel-card">
                                <div class="panel-kicker">Place Bid</div>
                                <h2 class="panel-title">Submit your offer</h2>
                                <p class="panel-copy mb-4">
                                    Make sure your bid amount is valid for the current listing conditions.
                                </p>

                                <form:form modelAttribute="bidForm"
                                    method="post"
                                    action="${pageContext.request.contextPath}/app/bidder/auctions/bid">

                                    <form:hidden path="productId"/>

                                    <div class="mb-3">
                                        <form:label path="bidPrice" cssClass="filter-label">Your Bid</form:label>
                                        <form:input path="bidPrice" cssClass="app-input"/>
                                        <form:errors path="bidPrice" cssClass="field-error" element="div"/>
                                    </div>

                                    <button type="submit" class="btn btn-dark rounded-pill px-4">
                                        Place Bid
                                    </button>
                                </form:form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
