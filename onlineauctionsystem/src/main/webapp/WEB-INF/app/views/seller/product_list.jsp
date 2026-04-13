<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="activeNav" value="products" />
<c:set var="homePath" value="/app/seller/home" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>My Products</title>

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
                            <p class="workspace-eyebrow">My Products</p>
                            <h1 class="workspace-title">Manage your auction catalog</h1>
                            <p class="workspace-subtitle">
                                Review every listing, check timing, and keep your product presentation sharp.
                            </p>
                        </div>

                        <div class="workspace-actions">
                            <a class="btn btn-dark rounded-pill px-4"
                               href="${pageContext.request.contextPath}/app/seller/product/add">
                                Add Product
                            </a>
                            <a class="btn btn-light border rounded-pill px-4"
                               href="${pageContext.request.contextPath}/app/seller/product/bids">
                                View Bids
                            </a>
                        </div>
                    </div>

                    <div class="data-card">
                        <c:choose>
                            <c:when test="${empty sellerProducts}">
                                <div class="empty-state">
                                    <h2 class="h5 mb-2">No products found</h2>
                                    <p class="mb-0">Start by creating your first listing and it will appear here.</p>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="table-responsive">
                                    <table class="workspace-table">
                                        <thead>
                                            <tr>
                                                <th>Product</th>
                                                <th>Category</th>
                                                <th>Description</th>
                                                <th>Minimum Bid</th>
                                                <th>Status</th>
                                                <th>Schedule</th>
                                                <th>Photo</th>
                                                <th>Action</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="product" items="${sellerProducts}">
                                                <tr>
                                                    <td>
                                                        <div class="fw-semibold"><c:out value="${product.productName}" /></div>
                                                        <div class="activity-meta">Product #<c:out value="${product.productId}" /></div>
                                                    </td>
                                                    <td><c:out value="${product.categoryName}" /></td>
                                                    <td><c:out value="${product.description}" /></td>
                                                    <td><c:out value="${product.minBidPrice}" /></td>
                                                    <td><span class="badge-soft"><c:out value="${product.status}" /></span></td>
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
                                                        <div class="d-flex flex-column gap-2">
                                                            <a class="btn btn-sm btn-dark rounded-pill px-3"
                                                               href="${pageContext.request.contextPath}/app/seller/product/edit?productId=${product.productId}">
                                                                Edit
                                                            </a>

                                                            <form method="post"
                                                                  action="${pageContext.request.contextPath}/app/seller/product/delete"
                                                                  class="m-0">
                                                                <input type="hidden" name="productId" value="${product.productId}" />
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
