<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><c:choose><c:when test="${editMode}">Update Product</c:when><c:otherwise>Add Product</c:otherwise></c:choose></title>
</head>
<body>
    <h1><c:choose><c:when test="${editMode}">Update Auction Item</c:when><c:otherwise>Add Auction Item</c:otherwise></c:choose></h1>

    <p>
        <a href="${pageContext.request.contextPath}/app/seller/home">Seller Dashboard</a> |
        <a href="${pageContext.request.contextPath}/app/seller/product/list">My Products</a>
    </p>

    <c:if test="${not empty saveError}">
        <p style="color:red;"><c:out value="${saveError}" /></p>
    </c:if>

    <form:form modelAttribute="productForm" method="post"
        action="${pageContext.request.contextPath}/app/seller/product/save"
        enctype="multipart/form-data">

        <form:hidden path="productId"/>
        <form:hidden path="currentPhotoPath"/>
        <form:hidden path="photoPath"/>

        <div style="color:red;">
            <form:errors path="*"/>
        </div>

        <div>
            <form:label path="productName">product name</form:label>
            <form:input path="productName"/>
            <form:errors path="productName" cssStyle="color:red;"/>
        </div>

        <div>
            <form:label path="photoFile">product photo</form:label>
            <input type="file" name="photoFile" />
            <form:errors path="photoFile" cssStyle="color:red;"/>
            <c:if test="${not empty productForm.currentPhotoPath}">
                <div>
                    <img src="${pageContext.request.contextPath}${productForm.currentPhotoPath}"
                         alt="Current Product Photo"
                         style="width:120px; height:120px;" />
                </div>
            </c:if>
        </div>

        <div>
            <form:label path="description">description</form:label>
            <form:textarea path="description"/>
            <form:errors path="description" cssStyle="color:red;"/>
        </div>

        <div>
            <form:label path="catId">category</form:label>
            <form:select path="catId">
                <form:option value="" label="-- select category --"/>
                <form:options items="${categoryOpts}" itemValue="catId" itemLabel="catName"/>
            </form:select>
            <form:errors path="catId" cssStyle="color:red;"/>
        </div>

        <div>
            <form:label path="minBidPrice">minimum bid price</form:label>
            <form:input path="minBidPrice"/>
            <form:errors path="minBidPrice" cssStyle="color:red;"/>
        </div>

        <div>
            <form:label path="startDate">start date</form:label>
            <form:input path="startDate" type="datetime-local"/>
            <form:errors path="startDate" cssStyle="color:red;"/>
        </div>

        <div>
            <form:label path="endDate">end date</form:label>
            <form:input path="endDate" type="datetime-local"/>
            <form:errors path="endDate" cssStyle="color:red;"/>
        </div>

        <div>
            <form:label path="status">status</form:label>
            <form:radiobutton path="status" value="open"/> open
            <form:radiobutton path="status" value="closed"/> closed
            <form:errors path="status" cssStyle="color:red;"/>
        </div>

        <div>
            <input type="submit" value="<c:choose><c:when test='${editMode}'>Update Product</c:when><c:otherwise>Add Product</c:otherwise></c:choose>"/>
        </div>
    </form:form>
</body>
</html>
