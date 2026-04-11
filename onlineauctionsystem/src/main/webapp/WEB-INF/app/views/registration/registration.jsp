<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Registration</title>
</head>
<body>
    <h1>Create a New Account</h1>

    <form:form modelAttribute="registrationForm" method="post" enctype="multipart/form-data">

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
            <form:radiobuttons path="gender" items="${genderOpts}"/>
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
            <form:label path="username">username</form:label>
            <form:input path="username"/>
            <form:errors path="username" cssStyle="color:red;"/>
        </div>

        <div>
            <form:label path="password">password</form:label>
            <form:password path="password"/>
            <form:errors path="password" cssStyle="color:red;"/>
        </div>

        <div>
            <form:label path="confirmPassword">confirm password</form:label>
            <form:password path="confirmPassword"/>
            <form:errors path="confirmPassword" cssStyle="color:red;"/>
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
            <form:label path="photoFile">profile photo</form:label>
            <form:input path="photoFile" type="file" accept="image/*"/>
            <form:errors path="photoFile" cssStyle="color:red;"/>
        </div>

        <div>
            <form:label path="userType">account type</form:label>
            <form:select path="userType">
                <form:option value="" label="-- select type --"/>
                <form:options items="${userTypeOpts}"/>
            </form:select>
            <form:errors path="userType" cssStyle="color:red;"/>
        </div>

        <div>
            <input type="submit" value="register"/>
        </div>
    </form:form>
</body>
</html>