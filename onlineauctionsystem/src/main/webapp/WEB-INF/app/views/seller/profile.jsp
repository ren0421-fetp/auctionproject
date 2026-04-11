<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Seller Profile</title>
</head>
<body>
    <h1>Seller Profile</h1>

    <p>
        <a href="${pageContext.request.contextPath}/app/seller/home">Seller Dashboard</a> |
        <a href="${pageContext.request.contextPath}/app/seller/product/add">Add Product</a> |
        <a href="${pageContext.request.contextPath}/app/seller/product/list">My Products</a>
    </p>

    <c:if test="${not empty profileSuccess}">
        <p style="color:green;"><c:out value="${profileSuccess}" /></p>
    </c:if>

    <c:if test="${not empty profileError}">
        <p style="color:red;"><c:out value="${profileError}" /></p>
    </c:if>

    <form:form modelAttribute="sellerProfileForm"
        method="post"
        enctype="multipart/form-data"
        action="${pageContext.request.contextPath}/app/seller/profile/update">

        <form:hidden path="username"/>
        <form:hidden path="photoPath"/>
        <form:hidden path="currentPhotoPath"/>

        <div>
            <form:label path="photoFile">photo</form:label>
            <input type="file" name="photoFile" />
            <c:if test="${not empty sellerProfileForm.currentPhotoPath}">
                <div>
                    <img src="${pageContext.request.contextPath}${sellerProfileForm.currentPhotoPath}"
                         alt="Profile Image"
                         style="width:120px;height:120px;" />
                </div>
            </c:if>
        </div>

        <div>
            <form:label path="firstName">first name</form:label>
            <form:input path="firstName"/>
            <form:errors path="firstName" cssStyle="color:red;"/>
        </div>

        <div>
            <form:label path="lastName">last name</form:label>
            <form:input path="lastName"/>
            <form:errors path="lastName" cssStyle="color:red;"/>
        </div>

        <div>
            <form:label path="gender">gender</form:label>
            <form:radiobutton path="gender" value="male"/> male
            <form:radiobutton path="gender" value="female"/> female
            <form:errors path="gender" cssStyle="color:red;"/>
        </div>

        <div>
            <form:label path="address">address</form:label>
            <form:textarea path="address"/>
            <form:errors path="address" cssStyle="color:red;"/>
        </div>

        <div>
            <form:label path="countryId">country</form:label>
            <form:select path="countryId">
                <form:option value="" label="-- select country --"/>
                <form:options items="${countryOpts}" itemValue="countryId" itemLabel="countryName"/>
            </form:select>
            <form:errors path="countryId" cssStyle="color:red;"/>
        </div>

        <div>
            <form:label path="stateId">state</form:label>
            <form:select path="stateId">
                <form:option value="" label="-- select state --"/>
                <form:options items="${stateOpts}" itemValue="stateId" itemLabel="stateName"/>
            </form:select>
            <form:errors path="stateId" cssStyle="color:red;"/>
        </div>

        <div>
            <form:label path="cityId">city</form:label>
            <form:select path="cityId">
                <form:option value="" label="-- select city --"/>
                <form:options items="${cityOpts}" itemValue="cityId" itemLabel="cityName"/>
            </form:select>
            <form:errors path="cityId" cssStyle="color:red;"/>
        </div>

        <div>
            <form:label path="email">email</form:label>
            <form:input path="email"/>
            <form:errors path="email" cssStyle="color:red;"/>
        </div>

        <div>
            <form:label path="contactNo">contact no</form:label>
            <form:input path="contactNo"/>
            <form:errors path="contactNo" cssStyle="color:red;"/>
        </div>

        <div>
            <input type="submit" value="Update Profile"/>
        </div>
    </form:form>

    <hr/>

    <h2>Change Password</h2>

    <c:if test="${not empty passwordSuccess}">
        <p style="color:green;"><c:out value="${passwordSuccess}" /></p>
    </c:if>

    <c:if test="${not empty passwordError}">
        <p style="color:red;"><c:out value="${passwordError}" /></p>
    </c:if>

    <form:form modelAttribute="changePasswordForm"
        method="post"
        action="${pageContext.request.contextPath}/app/seller/profile/change-password">

        <div>
            <form:label path="currentPassword">current password</form:label>
            <form:password path="currentPassword"/>
            <form:errors path="currentPassword" cssStyle="color:red;"/>
        </div>

        <div>
            <form:label path="newPassword">new password</form:label>
            <form:password path="newPassword"/>
            <form:errors path="newPassword" cssStyle="color:red;"/>
        </div>

        <div>
            <form:label path="confirmPassword">confirm password</form:label>
            <form:password path="confirmPassword"/>
            <form:errors path="confirmPassword" cssStyle="color:red;"/>
        </div>

        <div>
            <input type="submit" value="Change Password"/>
        </div>
    </form:form>
</body>
</html>
