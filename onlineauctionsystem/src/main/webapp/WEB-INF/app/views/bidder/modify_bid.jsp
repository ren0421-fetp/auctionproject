<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="activeNav" value="my-bids" />
<c:set var="homePath" value="/app/bidder/home" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Modify Bid</title>

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
                            <p class="workspace-eyebrow">Modify Bid</p>
                            <h1 class="workspace-title">Update your submitted offer</h1>
                            <p class="workspace-subtitle">
                                Review the current listing status before committing a new bid amount.
                            </p>
                        </div>

                        <div class="workspace-actions">
                            <a class="btn btn-light border rounded-pill px-4"
                               href="${pageContext.request.contextPath}/app/bidder/auctions/my-bids">
                                Back to My Bids
                            </a>
                        </div>
                    </div>

                    <c:if test="${not empty bidError}">
                        <div class="alert-soft-error"><c:out value="${bidError}" /></div>
                    </c:if>

                    <div class="row g-4">
                        <div class="col-lg-5">
                            <div class="panel-card h-100">
                                <div class="panel-kicker">Listing Snapshot</div>

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

                                <h2 class="panel-title"><c:out value="${product.productName}" /></h2>

                                <div class="meta-grid mt-4">
                                    <div class="meta-item">
                                        <div class="meta-label">Current Highest Bid</div>
                                        <div class="meta-value">
                                            <c:choose>
                                                <c:when test="${not empty product.currentHighestBid}">
                                                    <c:out value="${product.currentHighestBid}" />
                                                </c:when>
                                                <c:otherwise>No bids yet</c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                    <div class="meta-item">
                                        <div class="meta-label">Your Previous Bid</div>
                                        <div class="meta-value"><c:out value="${existingBid.bidPrice}" /></div>
                                    </div>
                                    <div class="meta-item">
                                        <div class="meta-label">Remaining Package Bid Count</div>
                                        <div class="meta-value"><c:out value="${remainingBidCount}" /></div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="col-lg-7">
                            <div class="panel-card">
                                <div class="panel-kicker">Submit Update</div>
                                <h2 class="panel-title">Place a new amount</h2>
                                <p class="panel-copy mb-4">
                                    Your modified bid must still satisfy the current auction rules.
                                </p>

                                <form:form modelAttribute="bidForm"
                                    method="post"
                                    action="${pageContext.request.contextPath}/app/bidder/auctions/modify">

                                    <form:hidden path="bidId"/>
                                    <form:hidden path="productId"/>

                                    <div class="mb-3">
                                        <form:label path="bidPrice" cssClass="filter-label">New Bid Amount</form:label>
                                        <form:input path="bidPrice" cssClass="app-input"/>
                                        <form:errors path="bidPrice" cssClass="field-error" element="div"/>
                                    </div>

                                    <button type="submit" class="btn btn-dark rounded-pill px-4">
                                        Submit Modified Bid
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
