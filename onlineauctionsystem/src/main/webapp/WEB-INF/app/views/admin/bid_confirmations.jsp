<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="activeNav" value="bidding" />
<c:set var="homePath" value="/app/admin/home" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Confirm Winning Bids</title>

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
                            <p class="workspace-eyebrow">Confirm Winning Bids</p>
                            <h1 class="workspace-title">Review and lock auction outcomes</h1>
                            <p class="workspace-subtitle">
                                Confirm winning bids once listings are ready and keep the result trail clear for sellers and bidders.
                            </p>
                        </div>
                    </div>

                    <c:if test="${not empty confirmSuccess}">
                        <div class="alert-soft-success"><c:out value="${confirmSuccess}" /></div>
                    </c:if>

                    <c:if test="${not empty confirmError}">
                        <div class="alert-soft-error"><c:out value="${confirmError}" /></div>
                    </c:if>

                    <div class="data-card">
                        <c:choose>
                            <c:when test="${empty allBids}">
                                <div class="empty-state">
                                    <h2 class="h5 mb-2">No bids available</h2>
                                    <p class="mb-0">There are no bids ready for confirmation right now.</p>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="table-responsive">
                                    <table class="workspace-table">
                                        <thead>
                                            <tr>
                                                <th>Bid</th>
                                                <th>Product</th>
                                                <th>Seller</th>
                                                <th>Bidder</th>
                                                <th>Bid Amount</th>
                                                <th>Bid Date</th>
                                                <th>Window</th>
                                                <th>Auction State</th>
                                                <th>Photo</th>
                                                <th>Confirmation Status</th>
                                                <th>Action</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="bid" items="${allBids}">
                                                <tr>
                                                    <td>#<c:out value="${bid.bidId}" /></td>
                                                    <td>
                                                        <div class="fw-semibold"><c:out value="${bid.productName}" /></div>
                                                        <div class="activity-meta">Product #<c:out value="${bid.productId}" /></div>
                                                    </td>
                                                    <td><c:out value="${bid.sellerUsername}" /></td>
                                                    <td><c:out value="${bid.bidderUsername}" /></td>
                                                    <td><c:out value="${bid.bidPrice}" /></td>
                                                    <td><c:out value="${bid.bidDate}" /></td>
                                                    <td>
                                                        <div class="fw-semibold"><c:out value="${bid.productStartDate}" /></div>
                                                        <div class="activity-meta">to <c:out value="${bid.productEndDate}" /></div>
                                                    </td>
                                                    <td><span class="badge-soft"><c:out value="${bid.auctionState}" /></span></td>
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
                                                                <span class="status-chip status-win">Confirmed</span>
                                                                <div class="activity-meta mt-2">
                                                                    <c:out value="${bid.confirmedWinnerUsername}" /> at <c:out value="${bid.confirmedPrice}" />
                                                                </div>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <span class="status-chip status-pending">Not confirmed</span>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${bid.productConfirmed}">
                                                                <span class="badge-soft">Locked</span>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <form method="post"
                                                                      action="${pageContext.request.contextPath}/app/admin/bids/confirm"
                                                                      class="m-0">
                                                                    <input type="hidden" name="bidId" value="${bid.bidId}" />
                                                                    <button type="submit" class="btn btn-sm btn-dark rounded-pill px-3">
                                                                        Confirm Winner
                                                                    </button>
                                                                </form>
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
