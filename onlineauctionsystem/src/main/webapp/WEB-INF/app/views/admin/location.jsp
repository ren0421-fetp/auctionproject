<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="activeNav" value="locations" />
<c:set var="homePath" value="/app/admin/home" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Manage Country State And City</title>

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
                            <p class="workspace-eyebrow">Manage Locations</p>
                            <h1 class="workspace-title">Control country, state, and city data</h1>
                            <p class="workspace-subtitle">
                                Keep registration and profile location options clean, consistent, and easy to maintain.
                            </p>
                        </div>
                    </div>

                    <c:if test="${not empty locationSuccess}">
                        <div class="alert-soft-success"><c:out value="${locationSuccess}" /></div>
                    </c:if>

                    <c:if test="${not empty locationError}">
                        <div class="alert-soft-error"><c:out value="${locationError}" /></div>
                    </c:if>

                    <div class="row g-4">
                        <div class="col-12">
                            <div class="panel-card">
                                <div class="panel-kicker">Manage Country</div>
                                <form:form modelAttribute="countryForm" method="post"
                                    action="${pageContext.request.contextPath}/app/admin/locations/country/save">
                                    <div class="row g-3 align-items-end">
                                        <div class="col-md-6">
                                            <form:hidden path="countryId"/>
                                            <form:label path="countryName" cssClass="filter-label">Country Name</form:label>
                                            <form:input path="countryName" cssClass="app-input"/>
                                            <form:errors path="countryName" cssClass="field-error" element="div"/>
                                        </div>
                                        <div class="col-md-auto">
                                            <button type="submit" class="btn btn-dark rounded-pill px-4">Save Country</button>
                                        </div>
                                    </div>
                                </form:form>

                                <div class="table-responsive mt-4">
                                    <table class="workspace-table">
                                        <thead>
                                            <tr>
                                                <th>Id</th>
                                                <th>Name</th>
                                                <th>Action</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="country" items="${countries}">
                                                <tr>
                                                    <td><c:out value="${country.countryId}" /></td>
                                                    <td><c:out value="${country.countryName}" /></td>
                                                    <td>
                                                        <div class="d-flex gap-2 flex-wrap">
                                                            <a class="btn btn-sm btn-dark rounded-pill px-3"
                                                               href="${pageContext.request.contextPath}/app/admin/locations?countryId=${country.countryId}">
                                                                Edit
                                                            </a>
                                                            <form method="post"
                                                                  action="${pageContext.request.contextPath}/app/admin/locations/country/delete"
                                                                  class="m-0">
                                                                <input type="hidden" name="countryId" value="${country.countryId}" />
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
                            </div>
                        </div>

                        <div class="col-12">
                            <div class="panel-card">
                                <div class="panel-kicker">Manage State</div>
                                <form:form modelAttribute="stateForm" method="post"
                                    action="${pageContext.request.contextPath}/app/admin/locations/state/save">
                                    <div class="row g-3 align-items-end">
                                        <div class="col-md-4">
                                            <form:hidden path="stateId"/>
                                            <form:label path="countryId" cssClass="filter-label">Country</form:label>
                                            <form:select path="countryId" cssClass="app-select">
                                                <form:option value="" label="-- select country --"/>
                                                <c:forEach var="country" items="${countries}">
                                                    <option value="${country.countryId}" <c:if test="${stateForm.countryId == country.countryId}">selected</c:if>>
                                                        <c:out value="${country.countryName}" />
                                                    </option>
                                                </c:forEach>
                                            </form:select>
                                            <form:errors path="countryId" cssClass="field-error" element="div"/>
                                        </div>
                                        <div class="col-md-4">
                                            <form:label path="stateName" cssClass="filter-label">State Name</form:label>
                                            <form:input path="stateName" cssClass="app-input"/>
                                            <form:errors path="stateName" cssClass="field-error" element="div"/>
                                        </div>
                                        <div class="col-md-auto">
                                            <button type="submit" class="btn btn-dark rounded-pill px-4">Save State</button>
                                        </div>
                                    </div>
                                </form:form>

                                <div class="table-responsive mt-4">
                                    <table class="workspace-table">
                                        <thead>
                                            <tr>
                                                <th>Id</th>
                                                <th>Country Id</th>
                                                <th>Name</th>
                                                <th>Action</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="state" items="${states}">
                                                <tr>
                                                    <td><c:out value="${state.stateId}" /></td>
                                                    <td><c:out value="${state.countryId}" /></td>
                                                    <td><c:out value="${state.stateName}" /></td>
                                                    <td>
                                                        <div class="d-flex gap-2 flex-wrap">
                                                            <a class="btn btn-sm btn-dark rounded-pill px-3"
                                                               href="${pageContext.request.contextPath}/app/admin/locations?stateId=${state.stateId}">
                                                                Edit
                                                            </a>
                                                            <form method="post"
                                                                  action="${pageContext.request.contextPath}/app/admin/locations/state/delete"
                                                                  class="m-0">
                                                                <input type="hidden" name="stateId" value="${state.stateId}" />
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
                            </div>
                        </div>

                        <div class="col-12">
                            <div class="panel-card">
                                <div class="panel-kicker">Manage City</div>
                                <form:form modelAttribute="cityForm" method="post"
                                    action="${pageContext.request.contextPath}/app/admin/locations/city/save">
                                    <div class="row g-3 align-items-end">
                                        <div class="col-md-4">
                                            <form:hidden path="cityId"/>
                                            <form:label path="stateId" cssClass="filter-label">State</form:label>
                                            <form:select path="stateId" cssClass="app-select">
                                                <form:option value="" label="-- select state --"/>
                                                <c:forEach var="state" items="${states}">
                                                    <option value="${state.stateId}" <c:if test="${cityForm.stateId == state.stateId}">selected</c:if>>
                                                        <c:out value="${state.stateName}" />
                                                    </option>
                                                </c:forEach>
                                            </form:select>
                                            <form:errors path="stateId" cssClass="field-error" element="div"/>
                                        </div>
                                        <div class="col-md-4">
                                            <form:label path="cityName" cssClass="filter-label">City Name</form:label>
                                            <form:input path="cityName" cssClass="app-input"/>
                                            <form:errors path="cityName" cssClass="field-error" element="div"/>
                                        </div>
                                        <div class="col-md-auto">
                                            <button type="submit" class="btn btn-dark rounded-pill px-4">Save City</button>
                                        </div>
                                    </div>
                                </form:form>

                                <div class="table-responsive mt-4">
                                    <table class="workspace-table">
                                        <thead>
                                            <tr>
                                                <th>Id</th>
                                                <th>State Id</th>
                                                <th>Name</th>
                                                <th>Action</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="city" items="${cities}">
                                                <tr>
                                                    <td><c:out value="${city.cityId}" /></td>
                                                    <td><c:out value="${city.stateId}" /></td>
                                                    <td><c:out value="${city.cityName}" /></td>
                                                    <td>
                                                        <div class="d-flex gap-2 flex-wrap">
                                                            <a class="btn btn-sm btn-dark rounded-pill px-3"
                                                               href="${pageContext.request.contextPath}/app/admin/locations?cityId=${city.cityId}">
                                                                Edit
                                                            </a>
                                                            <form method="post"
                                                                  action="${pageContext.request.contextPath}/app/admin/locations/city/delete"
                                                                  class="m-0">
                                                                <input type="hidden" name="cityId" value="${city.cityId}" />
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
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
