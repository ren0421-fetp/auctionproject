<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Manage Country State And City</title>
</head>
<body>
    <h1>Manage Country State And City</h1>

    <p>
        <a href="${pageContext.request.contextPath}/app/admin/home">Dashboard</a>
    </p>

    <c:if test="${not empty locationSuccess}">
        <p style="color:green;"><c:out value="${locationSuccess}" /></p>
    </c:if>

    <c:if test="${not empty locationError}">
        <p style="color:red;"><c:out value="${locationError}" /></p>
    </c:if>

    <h2>Manage Country</h2>
    <form:form modelAttribute="countryForm" method="post"
        action="${pageContext.request.contextPath}/app/admin/locations/country/save">
        <form:hidden path="countryId"/>
        <form:input path="countryName"/>
        <form:errors path="countryName" cssStyle="color:red;"/>
        <input type="submit" value="Save Country"/>
    </form:form>

    <table border="1" cellpadding="6">
        <tr><th>Id</th><th>Name</th><th>Action</th></tr>
        <c:forEach var="country" items="${countries}">
            <tr>
                <td><c:out value="${country.countryId}" /></td>
                <td><c:out value="${country.countryName}" /></td>
                <td>
                    <a href="${pageContext.request.contextPath}/app/admin/locations?countryId=${country.countryId}">Edit</a>
                    <form method="post" action="${pageContext.request.contextPath}/app/admin/locations/country/delete" style="display:inline;">
                        <input type="hidden" name="countryId" value="${country.countryId}" />
                        <input type="submit" value="Delete" />
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>

    <hr/>

    <h2>Manage State</h2>
    <form:form modelAttribute="stateForm" method="post"
        action="${pageContext.request.contextPath}/app/admin/locations/state/save">
        <form:hidden path="stateId"/>
        <form:select path="countryId">
            <form:option value="" label="-- select country --"/>
            <c:forEach var="country" items="${countries}">
                <option value="${country.countryId}" <c:if test="${stateForm.countryId == country.countryId}">selected</c:if>>
                    <c:out value="${country.countryName}" />
                </option>
            </c:forEach>
        </form:select>
        <form:errors path="countryId" cssStyle="color:red;"/>
        <form:input path="stateName"/>
        <form:errors path="stateName" cssStyle="color:red;"/>
        <input type="submit" value="Save State"/>
    </form:form>

    <table border="1" cellpadding="6">
        <tr><th>Id</th><th>Country Id</th><th>Name</th><th>Action</th></tr>
        <c:forEach var="state" items="${states}">
            <tr>
                <td><c:out value="${state.stateId}" /></td>
                <td><c:out value="${state.countryId}" /></td>
                <td><c:out value="${state.stateName}" /></td>
                <td>
                    <a href="${pageContext.request.contextPath}/app/admin/locations?stateId=${state.stateId}">Edit</a>
                    <form method="post" action="${pageContext.request.contextPath}/app/admin/locations/state/delete" style="display:inline;">
                        <input type="hidden" name="stateId" value="${state.stateId}" />
                        <input type="submit" value="Delete" />
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>

    <hr/>

    <h2>Manage City</h2>
    <form:form modelAttribute="cityForm" method="post"
        action="${pageContext.request.contextPath}/app/admin/locations/city/save">
        <form:hidden path="cityId"/>
        <form:select path="stateId">
            <form:option value="" label="-- select state --"/>
            <c:forEach var="state" items="${states}">
                <option value="${state.stateId}" <c:if test="${cityForm.stateId == state.stateId}">selected</c:if>>
                    <c:out value="${state.stateName}" />
                </option>
            </c:forEach>
        </form:select>
        <form:errors path="stateId" cssStyle="color:red;"/>
        <form:input path="cityName"/>
        <form:errors path="cityName" cssStyle="color:red;"/>
        <input type="submit" value="Save City"/>
    </form:form>

    <table border="1" cellpadding="6">
        <tr><th>Id</th><th>State Id</th><th>Name</th><th>Action</th></tr>
        <c:forEach var="city" items="${cities}">
            <tr>
                <td><c:out value="${city.cityId}" /></td>
                <td><c:out value="${city.stateId}" /></td>
                <td><c:out value="${city.cityName}" /></td>
                <td>
                    <a href="${pageContext.request.contextPath}/app/admin/locations?cityId=${city.cityId}">Edit</a>
                    <form method="post" action="${pageContext.request.contextPath}/app/admin/locations/city/delete" style="display:inline;">
                        <input type="hidden" name="cityId" value="${city.cityId}" />
                        <input type="submit" value="Delete" />
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
