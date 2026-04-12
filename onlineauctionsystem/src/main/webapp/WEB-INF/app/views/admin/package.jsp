<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Manage Packages</title>
</head>
<body>
    <h1>Manage Packages</h1>

    <p>
        <a href="${pageContext.request.contextPath}/app/admin/home">Dashboard</a>
    </p>

    <c:if test="${not empty saveSuccess}">
        <p style="color:green;"><c:out value="${saveSuccess}" /></p>
    </c:if>

    <c:if test="${not empty saveError}">
        <p style="color:red;"><c:out value="${saveError}" /></p>
    </c:if>

    <h2>
        <c:choose>
            <c:when test="${editMode}">Update Package Information</c:when>
            <c:otherwise>Add Package Information</c:otherwise>
        </c:choose>
    </h2>

    <form:form modelAttribute="packageForm"
        method="post"
        enctype="multipart/form-data"
        action="${pageContext.request.contextPath}/app/admin/packages/save">

        <form:hidden path="packageId"/>
        <form:hidden path="photoPath"/>
        <form:hidden path="currentPhotoPath"/>

        <div>
            <form:label path="packageName">Package Name</form:label>
            <form:input path="packageName"/>
            <form:errors path="packageName" cssStyle="color:red;"/>
        </div>

        <div>
            <form:label path="packagePrice">Package Price</form:label>
            <form:input path="packagePrice"/>
            <form:errors path="packagePrice" cssStyle="color:red;"/>
        </div>

        <div>
            <form:label path="allowedBidCount">Allowed Bid Count</form:label>
            <form:input path="allowedBidCount"/>
            <form:errors path="allowedBidCount" cssStyle="color:red;"/>
        </div>

        <div>
            <label for="photoFile">Photo</label>
            <input type="file" id="photoFile" name="photoFile" />
            <form:errors path="photoFile" cssStyle="color:red;"/>

            <c:if test="${not empty packageForm.currentPhotoPath}">
                <div>
                    <img src="${pageContext.request.contextPath}${packageForm.currentPhotoPath}"
                         alt="Current Package Photo"
                         style="width:90px; height:120px;" />
                </div>
            </c:if>
        </div>

        <div>
            <input type="submit"
                   value="<c:choose><c:when test='${editMode}'>Update Package</c:when><c:otherwise>Add Package</c:otherwise></c:choose>"/>
        </div>
    </form:form>

    <hr/>

    <h2>Manage Package Information</h2>

    <c:choose>
        <c:when test="${empty packages}">
            <p>No packages found.</p>
        </c:when>
        <c:otherwise>
            <table border="1" cellpadding="8">
                <thead>
                    <tr>
                        <th>Manipulation</th>
                        <th>Package Name</th>
                        <th>Package Price</th>
                        <th>Allowed Bid Count</th>
                        <th>Photo</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="pkg" items="${packages}">
                        <tr>
                            <td>
                                <a href="${pageContext.request.contextPath}/app/admin/packages/edit?packageId=${pkg.packageId}">
                                    Edit
                                </a>

                                <form method="post"
                                      action="${pageContext.request.contextPath}/app/admin/packages/delete"
                                      style="display:inline;">
                                    <input type="hidden" name="packageId" value="${pkg.packageId}" />
                                    <input type="submit" value="Delete" />
                                </form>
                            </td>
                            <td><c:out value="${pkg.packageName}" /></td>
                            <td><c:out value="${pkg.packagePrice}" /></td>
                            <td><c:out value="${pkg.allowedBidCount}" /> bids</td>
                            <td>
                                <c:if test="${not empty pkg.photoPath}">
                                    <img src="${pageContext.request.contextPath}${pkg.photoPath}"
                                         alt="Package Photo"
                                         style="width:70px; height:110px;" />
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>
</body>
</html>
