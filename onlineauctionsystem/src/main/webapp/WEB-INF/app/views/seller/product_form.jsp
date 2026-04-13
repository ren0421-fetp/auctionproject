<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="activeNav" value="${editMode ? 'products' : 'add-product'}" />
<c:set var="homePath" value="/app/seller/home" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><c:choose><c:when test="${editMode}">Update Product</c:when><c:otherwise>Add Product</c:otherwise></c:choose></title>

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
                            <p class="workspace-eyebrow"><c:choose><c:when test="${editMode}">Update Listing</c:when><c:otherwise>Add Listing</c:otherwise></c:choose></p>
                            <h1 class="workspace-title"><c:choose><c:when test="${editMode}">Update Auction Item</c:when><c:otherwise>Add Auction Item</c:otherwise></c:choose></h1>
                            <p class="workspace-subtitle">
                                Build a clear, trustworthy product page with the right timing, price floor, and supporting image.
                            </p>
                        </div>

                        <div class="workspace-actions">
                            <a class="btn btn-light border rounded-pill px-4"
                               href="${pageContext.request.contextPath}/app/seller/product/list">
                                Back to My Products
                            </a>
                        </div>
                    </div>

                    <c:if test="${not empty saveError}">
                        <div class="alert-soft-error"><c:out value="${saveError}" /></div>
                    </c:if>

                    <div class="panel-card">
                        <form:form modelAttribute="productForm" method="post"
                            action="${pageContext.request.contextPath}/app/seller/product/save"
                            enctype="multipart/form-data">

                            <form:hidden path="productId"/>
                            <form:hidden path="currentPhotoPath"/>
                            <form:hidden path="photoPath"/>

                            <form:errors path="*" cssClass="alert-soft-error mb-3" element="div"/>

                            <div class="row g-3">
                                <div class="col-md-6">
                                    <form:label path="productName" cssClass="filter-label">Product Name</form:label>
                                    <form:input path="productName" cssClass="app-input"/>
                                    <form:errors path="productName" cssClass="field-error" element="div"/>
                                </div>

                                <div class="col-md-6">
                                    <form:label path="catId" cssClass="filter-label">Category</form:label>
                                    <form:select path="catId" cssClass="app-select">
                                        <form:option value="" label="-- select category --"/>
                                        <form:options items="${categoryOpts}" itemValue="catId" itemLabel="catName"/>
                                    </form:select>
                                    <form:errors path="catId" cssClass="field-error" element="div"/>
                                </div>

                                <div class="col-12">
                                    <form:label path="description" cssClass="filter-label">Description</form:label>
                                    <form:textarea path="description" cssClass="app-textarea"/>
                                    <form:errors path="description" cssClass="field-error" element="div"/>
                                </div>

                                <div class="col-md-6">
                                    <form:label path="minBidPrice" cssClass="filter-label">Minimum Bid Price</form:label>
                                    <form:input path="minBidPrice" cssClass="app-input"/>
                                    <form:errors path="minBidPrice" cssClass="field-error" element="div"/>
                                </div>

                                <div class="col-md-6">
                                    <label class="filter-label">Product Photo</label>
                                    <input class="app-file" type="file" name="photoFile" />
                                    <form:errors path="photoFile" cssClass="field-error" element="div"/>

                                    <c:if test="${not empty productForm.currentPhotoPath}">
                                        <div class="mt-3">
                                            <img src="${pageContext.request.contextPath}${productForm.currentPhotoPath}"
                                                 alt="Current Product Photo"
                                                 class="table-thumb"
                                                 style="width:120px; height:120px;" />
                                        </div>
                                    </c:if>
                                </div>

                                <div class="col-md-6">
                                    <form:label path="startDate" cssClass="filter-label">Start Date</form:label>
                                    <form:input path="startDate" type="datetime-local" cssClass="app-input"/>
                                    <form:errors path="startDate" cssClass="field-error" element="div"/>
                                </div>

                                <div class="col-md-6">
                                    <form:label path="endDate" cssClass="filter-label">End Date</form:label>
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

                            <div class="d-flex gap-2 flex-wrap mt-4">
                                <button type="submit" class="btn btn-dark rounded-pill px-4">
                                    <c:choose><c:when test="${editMode}">Update Product</c:when><c:otherwise>Add Product</c:otherwise></c:choose>
                                </button>
                                <a class="btn btn-light border rounded-pill px-4"
                                   href="${pageContext.request.contextPath}/app/seller/product/list">
                                    Cancel
                                </a>
                            </div>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>
    </div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
