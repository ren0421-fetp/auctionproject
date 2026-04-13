<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="activeNav" value="profile" />
<c:set var="homePath" value="/app/seller/home" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Seller Profile</title>

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
                            <p class="workspace-eyebrow">Seller Profile</p>
                            <h1 class="workspace-title">Manage your seller account</h1>
                            <p class="workspace-subtitle">
                                Keep your profile details accurate and secure so bidders can trust the account behind each listing.
                            </p>
                        </div>

                        <div class="workspace-actions">
                            <a class="btn btn-light border rounded-pill px-4"
                               href="${pageContext.request.contextPath}/app/seller/product/list">
                                My Products
                            </a>
                        </div>
                    </div>

                    <c:if test="${not empty profileSuccess}">
                        <div class="alert-soft-success"><c:out value="${profileSuccess}" /></div>
                    </c:if>

                    <c:if test="${not empty profileError}">
                        <div class="alert-soft-error"><c:out value="${profileError}" /></div>
                    </c:if>

                    <div class="row g-4">
                        <div class="col-xl-8">
                            <div class="panel-card">
                                <div class="panel-kicker">Profile Details</div>
                                <h2 class="panel-title">Update account information</h2>

                                <form:form modelAttribute="sellerProfileForm"
                                    method="post"
                                    enctype="multipart/form-data"
                                    action="${pageContext.request.contextPath}/app/seller/profile/update">

                                    <form:hidden path="username"/>
                                    <form:hidden path="photoPath"/>
                                    <form:hidden path="currentPhotoPath"/>

                                    <div class="row g-3">
                                        <div class="col-md-6">
                                            <label class="filter-label">Profile Photo</label>
                                            <input class="app-file" type="file" name="photoFile" />
                                            <c:if test="${not empty sellerProfileForm.currentPhotoPath}">
                                                <div class="mt-3">
                                                    <img src="${pageContext.request.contextPath}${sellerProfileForm.currentPhotoPath}"
                                                         alt="Profile Image"
                                                         class="table-thumb"
                                                         style="width:120px;height:120px;" />
                                                </div>
                                            </c:if>
                                        </div>

                                        <div class="col-md-6">
                                            <form:label path="firstName" cssClass="filter-label">First Name</form:label>
                                            <form:input path="firstName" cssClass="app-input"/>
                                            <form:errors path="firstName" cssClass="field-error" element="div"/>
                                        </div>

                                        <div class="col-md-6">
                                            <form:label path="lastName" cssClass="filter-label">Last Name</form:label>
                                            <form:input path="lastName" cssClass="app-input"/>
                                            <form:errors path="lastName" cssClass="field-error" element="div"/>
                                        </div>

                                        <div class="col-md-6">
                                            <label class="filter-label d-block">Gender</label>
                                            <div class="d-flex flex-wrap gap-3">
                                                <label class="badge-soft">
                                                    <form:radiobutton path="gender" value="male"/> &nbsp;Male
                                                </label>
                                                <label class="badge-soft">
                                                    <form:radiobutton path="gender" value="female"/> &nbsp;Female
                                                </label>
                                            </div>
                                            <form:errors path="gender" cssClass="field-error" element="div"/>
                                        </div>

                                        <div class="col-12">
                                            <form:label path="address" cssClass="filter-label">Address</form:label>
                                            <form:textarea path="address" cssClass="app-textarea"/>
                                            <form:errors path="address" cssClass="field-error" element="div"/>
                                        </div>

                                        <div class="col-md-4">
                                            <form:label path="countryId" cssClass="filter-label">Country</form:label>
                                            <form:select path="countryId" cssClass="app-select">
                                                <form:option value="" label="-- select country --"/>
                                                <form:options items="${countryOpts}" itemValue="countryId" itemLabel="countryName"/>
                                            </form:select>
                                            <form:errors path="countryId" cssClass="field-error" element="div"/>
                                        </div>

                                        <div class="col-md-4">
                                            <form:label path="stateId" cssClass="filter-label">State</form:label>
                                            <form:select path="stateId" cssClass="app-select">
                                                <form:option value="" label="-- select state --"/>
                                                <form:options items="${stateOpts}" itemValue="stateId" itemLabel="stateName"/>
                                            </form:select>
                                            <form:errors path="stateId" cssClass="field-error" element="div"/>
                                        </div>

                                        <div class="col-md-4">
                                            <form:label path="cityId" cssClass="filter-label">City</form:label>
                                            <form:select path="cityId" cssClass="app-select">
                                                <form:option value="" label="-- select city --"/>
                                                <form:options items="${cityOpts}" itemValue="cityId" itemLabel="cityName"/>
                                            </form:select>
                                            <form:errors path="cityId" cssClass="field-error" element="div"/>
                                        </div>

                                        <div class="col-md-6">
                                            <form:label path="email" cssClass="filter-label">Email</form:label>
                                            <form:input path="email" cssClass="app-input"/>
                                            <form:errors path="email" cssClass="field-error" element="div"/>
                                        </div>

                                        <div class="col-md-6">
                                            <form:label path="contactNo" cssClass="filter-label">Contact Number</form:label>
                                            <form:input path="contactNo" cssClass="app-input"/>
                                            <form:errors path="contactNo" cssClass="field-error" element="div"/>
                                        </div>
                                    </div>

                                    <div class="mt-4">
                                        <button type="submit" class="btn btn-dark rounded-pill px-4">
                                            Update Profile
                                        </button>
                                    </div>
                                </form:form>
                            </div>
                        </div>

                        <div class="col-xl-4">
                            <div class="panel-card">
                                <div class="panel-kicker">Security</div>
                                <h2 class="panel-title">Change password</h2>
                                <p class="panel-copy mb-4">
                                    Keep your account protected with a fresh password when needed.
                                </p>

                                <c:if test="${not empty passwordSuccess}">
                                    <div class="alert-soft-success"><c:out value="${passwordSuccess}" /></div>
                                </c:if>

                                <c:if test="${not empty passwordError}">
                                    <div class="alert-soft-error"><c:out value="${passwordError}" /></div>
                                </c:if>

                                <form:form modelAttribute="changePasswordForm"
                                    method="post"
                                    action="${pageContext.request.contextPath}/app/seller/profile/change-password">

                                    <div class="mb-3">
                                        <form:label path="currentPassword" cssClass="filter-label">Current Password</form:label>
                                        <form:password path="currentPassword" cssClass="app-input"/>
                                        <form:errors path="currentPassword" cssClass="field-error" element="div"/>
                                    </div>

                                    <div class="mb-3">
                                        <form:label path="newPassword" cssClass="filter-label">New Password</form:label>
                                        <form:password path="newPassword" cssClass="app-input"/>
                                        <form:errors path="newPassword" cssClass="field-error" element="div"/>
                                    </div>

                                    <div class="mb-3">
                                        <form:label path="confirmPassword" cssClass="filter-label">Confirm Password</form:label>
                                        <form:password path="confirmPassword" cssClass="app-input"/>
                                        <form:errors path="confirmPassword" cssClass="field-error" element="div"/>
                                    </div>

                                    <button type="submit" class="btn btn-dark rounded-pill px-4">
                                        Change Password
                                    </button>
                                </form:form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
