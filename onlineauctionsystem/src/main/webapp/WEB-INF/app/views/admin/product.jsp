<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="activeNav" value="products" />
<c:set var="homePath" value="/app/admin/home" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Manage Product</title>

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
                            <p class="workspace-eyebrow">Manage Product</p>
                            <h1 class="workspace-title">Oversee all auction items</h1>
                            <p class="workspace-subtitle">
                                Add listings on behalf of sellers, review product states, and close items when platform control is needed.
                            </p>
                        </div>
                    </div>

                    <c:if test="${not empty productActionSuccess}">
                        <div class="alert-soft-success"><c:out value="${productActionSuccess}" /></div>
                    </c:if>

                    <c:if test="${not empty productActionError}">
                        <div class="alert-soft-error"><c:out value="${productActionError}" /></div>
                    </c:if>

                    <div class="panel-card mb-4">
                        <div class="panel-kicker">
                            <c:choose>
                                <c:when test="${editMode}">Update Product</c:when>
                                <c:otherwise>Add Product</c:otherwise>
                            </c:choose>
                        </div>
                        <h2 class="panel-title">
                            <c:choose>
                                <c:when test="${editMode}">Update auction item</c:when>
                                <c:otherwise>Add auction item</c:otherwise>
                            </c:choose>
                        </h2>

                        <form:form modelAttribute="productForm"
                            method="post"
                            enctype="multipart/form-data"
                            action="${pageContext.request.contextPath}/app/admin/products/save">

                            <form:hidden path="productId"/>
                            <form:hidden path="photoPath"/>
                            <form:hidden path="currentPhotoPath"/>

                            <div class="row g-3">
                                <div class="col-md-6">
                                    <form:label path="sellerUsername" cssClass="filter-label">Seller</form:label>
                                    <form:select path="sellerUsername" cssClass="app-select">
                                        <form:option value="" label="-- select seller --"/>
                                        <c:forEach var="seller" items="${sellerOpts}">
                                            <option value="${seller.username}"
                                                <c:if test="${productForm.sellerUsername == seller.username}">selected</c:if>>
                                                <c:out value="${seller.username}" />
                                            </option>
                                        </c:forEach>
                                    </form:select>
                                    <form:errors path="sellerUsername" cssClass="field-error" element="div"/>
                                </div>

                                <div class="col-md-6">
                                    <form:label path="productName" cssClass="filter-label">Item Name</form:label>
                                    <form:input path="productName" cssClass="app-input"/>
                                    <form:errors path="productName" cssClass="field-error" element="div"/>
                                </div>

                                <div class="col-md-6">
                                    <label for="photoFile" class="filter-label">Item Photo</label>
                                    <input type="file" id="photoFile" name="photoFile" class="app-file" />
                                    <form:errors path="photoFile" cssClass="field-error" element="div"/>

                                    <c:if test="${not empty productForm.currentPhotoPath}">
                                        <div class="mt-3">
                                            <img src="${pageContext.request.contextPath}${productForm.currentPhotoPath}"
                                                 alt="Current Product Photo"
                                                 class="table-thumb"
                                                 style="width:90px; height:90px;" />
                                        </div>
                                    </c:if>
                                </div>

                                <div class="col-md-6">
                                    <form:label path="catId" cssClass="filter-label">Category</form:label>
                                    <form:select path="catId" cssClass="app-select">
                                        <form:option value="" label="-- select category --"/>
                                        <c:forEach var="cat" items="${categoryOpts}">
                                            <option value="${cat.catId}"
                                                <c:if test="${productForm.catId == cat.catId}">selected</c:if>>
                                                <c:out value="${cat.catName}" />
                                            </option>
                                        </c:forEach>
                                    </form:select>
                                    <form:errors path="catId" cssClass="field-error" element="div"/>
                                </div>

                                <div class="col-12">
                                    <form:label path="description" cssClass="filter-label">Item Description</form:label>
                                    <form:textarea path="description" cssClass="app-textarea"/>
                                    <form:errors path="description" cssClass="field-error" element="div"/>
                                </div>

                                <div class="col-md-4">
                                    <form:label path="minBidPrice" cssClass="filter-label">Starting Bid Price</form:label>
                                    <form:input path="minBidPrice" cssClass="app-input"/>
                                    <form:errors path="minBidPrice" cssClass="field-error" element="div"/>
                                </div>

                                <div class="col-md-4">
                                    <form:label path="startDate" cssClass="filter-label">Starting Date</form:label>
                                    <form:input path="startDate" type="datetime-local" cssClass="app-input"/>
                                    <form:errors path="startDate" cssClass="field-error" element="div"/>
                                </div>

                                <div class="col-md-4">
                                    <form:label path="endDate" cssClass="filter-label">Ending Date</form:label>
                                    <form:input path="endDate" type="datetime-local" cssClass="app-input"/>
                                    <form:errors path="endDate" cssClass="field-error" element="div"/>
                                </div>

                                <div class="col-12">
                                    <label class="filter-label d-block">Status</label>
                                    <div class="d-flex flex-wrap gap-3">
                                        <label class="badge-soft">
                                            <form:radiobutton path="status" value="open"/> &nbsp;Open
                                        </label>
                                        <label class="badge-soft">
                                            <form:radiobutton path="status" value="closed"/> &nbsp;Closed
                                        </label>
                                    </div>
                                    <form:errors path="status" cssClass="field-error" element="div"/>
                                </div>
                            </div>

                            <div class="mt-4">
                                <button type="submit" class="btn btn-dark rounded-pill px-4">
                                    <c:choose>
                                        <c:when test="${editMode}">Update Item</c:when>
                                        <c:otherwise>Add Item</c:otherwise>
                                    </c:choose>
                                </button>
                            </div>
                        </form:form>
                    </div>

                    <div class="filter-card mb-4">
                        <form method="get" action="${pageContext.request.contextPath}/app/admin/products">
                            <div class="filter-toolbar">
                                <div class="filter-group">
                                    <label for="status" class="filter-label">Status</label>
                                    <select id="status" name="status" class="app-select">
                                        <option value="">-- all --</option>
                                        <option value="open" <c:if test="${selectedStatus == 'open'}">selected</c:if>>open</option>
                                        <option value="closed" <c:if test="${selectedStatus == 'closed'}">selected</c:if>>closed</option>
                                    </select>
                                </div>

                                <div class="filter-group">
                                    <label for="sellerUsernameFilter" class="filter-label">Seller</label>
                                    <input type="text" id="sellerUsernameFilter" name="sellerUsername"
                                           value="${selectedSellerUsername}" class="app-input" />
                                </div>

                                <div class="d-flex gap-2 flex-wrap">
                                    <button type="submit" class="btn btn-dark rounded-pill px-4">Filter</button>
                                    <a class="btn btn-light border rounded-pill px-4"
                                       href="${pageContext.request.contextPath}/app/admin/products">
                                        Clear
                                    </a>
                                </div>
                            </div>
                        </form>
                    </div>

                    <div class="data-card">
                        <c:choose>
                            <c:when test="${empty products}">
                                <div class="empty-state">
                                    <h2 class="h5 mb-2">No products found</h2>
                                    <p class="mb-0">Try adjusting the filters or add a new product above.</p>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="table-responsive">
                                    <table class="workspace-table">
                                        <thead>
                                            <tr>
                                                <th>Action</th>
                                                <th>Product</th>
                                                <th>Category Id</th>
                                                <th>Seller</th>
                                                <th>Description</th>
                                                <th>Minimum Bid</th>
                                                <th>Status</th>
                                                <th>Schedule</th>
                                                <th>Photo</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="product" items="${products}">
                                                <tr>
                                                    <td>
                                                        <div class="d-flex flex-column gap-2">
                                                            <a class="btn btn-sm btn-dark rounded-pill px-3"
                                                               href="${pageContext.request.contextPath}/app/admin/products?productId=${product.productId}">
                                                                Edit
                                                            </a>

                                                            <form method="post"
                                                                  action="${pageContext.request.contextPath}/app/admin/products/delete"
                                                                  class="m-0">
                                                                <input type="hidden" name="productId" value="${product.productId}" />
                                                                <button type="submit" class="btn btn-sm btn-light border rounded-pill px-3">
                                                                    Delete
                                                                </button>
                                                            </form>

                                                            <c:if test="${product.status != 'closed'}">
                                                                <form method="post"
                                                                      action="${pageContext.request.contextPath}/app/admin/products/close"
                                                                      class="m-0">
                                                                    <input type="hidden" name="productId" value="${product.productId}" />
                                                                    <button type="submit" class="btn btn-sm btn-light border rounded-pill px-3">
                                                                        Close
                                                                    </button>
                                                                </form>
                                                            </c:if>
                                                        </div>
                                                    </td>
                                                    <td>
                                                        <div class="fw-semibold"><c:out value="${product.productName}" /></div>
                                                        <div class="activity-meta">Product #<c:out value="${product.productId}" /></div>
                                                    </td>
                                                    <td><c:out value="${product.catId}" /></td>
                                                    <td><c:out value="${product.sellerUsername}" /></td>
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
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>

                                <p class="inline-note mt-3 mb-0">
                                    Delete should fail if bids already exist. In that case, use Close instead.
                                </p>
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
