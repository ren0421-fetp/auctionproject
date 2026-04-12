<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Manage Product</title>
</head>
<body>
    <h1>Manage Product</h1>

    <p>
        <a href="${pageContext.request.contextPath}/app/admin/home">Dashboard</a>
    </p>

    <c:if test="${not empty productActionSuccess}">
        <p style="color:green;"><c:out value="${productActionSuccess}" /></p>
    </c:if>

    <c:if test="${not empty productActionError}">
        <p style="color:red;"><c:out value="${productActionError}" /></p>
    </c:if>

    <h2>
        <c:choose>
            <c:when test="${editMode}">Update Auction Item</c:when>
            <c:otherwise>Add Auction Item</c:otherwise>
        </c:choose>
    </h2>

    <form:form modelAttribute="productForm"
        method="post"
        enctype="multipart/form-data"
        action="${pageContext.request.contextPath}/app/admin/products/save">

        <form:hidden path="productId"/>
        <form:hidden path="photoPath"/>
        <form:hidden path="currentPhotoPath"/>

        <div>
            <form:label path="sellerUsername">Seller</form:label>
            <form:select path="sellerUsername">
                <form:option value="" label="-- select seller --"/>
                <c:forEach var="seller" items="${sellerOpts}">
                    <option value="${seller.username}"
                        <c:if test="${productForm.sellerUsername == seller.username}">selected</c:if>>
                        <c:out value="${seller.username}" />
                    </option>
                </c:forEach>
            </form:select>
            <form:errors path="sellerUsername" cssStyle="color:red;"/>
        </div>

        <div>
            <form:label path="productName">Item Name</form:label>
            <form:input path="productName"/>
            <form:errors path="productName" cssStyle="color:red;"/>
        </div>

        <div>
            <label for="photoFile">Item Photo</label>
            <input type="file" id="photoFile" name="photoFile" />
            <form:errors path="photoFile" cssStyle="color:red;"/>
            <c:if test="${not empty productForm.currentPhotoPath}">
                <div>
                    <img src="${pageContext.request.contextPath}${productForm.currentPhotoPath}"
                         alt="Current Product Photo"
                         style="width:90px; height:90px;" />
                </div>
            </c:if>
        </div>

        <div>
            <form:label path="description">Item Description</form:label>
            <form:input path="description"/>
            <form:errors path="description" cssStyle="color:red;"/>
        </div>

        <div>
            <form:label path="catId">Category</form:label>
            <form:select path="catId">
                <form:option value="" label="-- select category --"/>
                <c:forEach var="cat" items="${categoryOpts}">
                    <option value="${cat.catId}"
                        <c:if test="${productForm.catId == cat.catId}">selected</c:if>>
                        <c:out value="${cat.catName}" />
                    </option>
                </c:forEach>
            </form:select>
            <form:errors path="catId" cssStyle="color:red;"/>
        </div>

        <div>
            <form:label path="minBidPrice">Starting Bid Price</form:label>
            <form:input path="minBidPrice"/>
            <form:errors path="minBidPrice" cssStyle="color:red;"/>
        </div>

        <div>
            <form:label path="startDate">Starting Date For Bidding</form:label>
            <form:input path="startDate" type="datetime-local"/>
            <form:errors path="startDate" cssStyle="color:red;"/>
        </div>

        <div>
            <form:label path="endDate">Ending Date For Bidding</form:label>
            <form:input path="endDate" type="datetime-local"/>
            <form:errors path="endDate" cssStyle="color:red;"/>
        </div>

        <div>
            <form:label path="status">Status</form:label>
            <form:radiobutton path="status" value="open"/> Open
            <form:radiobutton path="status" value="closed"/> Close
            <form:errors path="status" cssStyle="color:red;"/>
        </div>

        <div>
            <input type="submit"
                   value="<c:choose><c:when test='${editMode}'>Update Item</c:when><c:otherwise>Add Item</c:otherwise></c:choose>"/>
        </div>
    </form:form>

    <hr/>

    <h2>Manage Product Information</h2>

    <form method="get" action="${pageContext.request.contextPath}/app/admin/products">
        <label for="status">Status</label>
        <select id="status" name="status">
            <option value="">-- all --</option>
            <option value="open" <c:if test="${selectedStatus == 'open'}">selected</c:if>>open</option>
            <option value="closed" <c:if test="${selectedStatus == 'closed'}">selected</c:if>>closed</option>
        </select>

        <label for="sellerUsernameFilter">Seller</label>
        <input type="text" id="sellerUsernameFilter" name="sellerUsername" value="${selectedSellerUsername}" />

        <input type="submit" value="Filter"/>
        <a href="${pageContext.request.contextPath}/app/admin/products">Clear</a>
    </form>

    <c:choose>
        <c:when test="${empty products}">
            <p>No products found.</p>
        </c:when>
        <c:otherwise>
            <table border="1" cellpadding="8">
                <thead>
                    <tr>
                        <th>Action</th>
                        <th>Product Id</th>
                        <th>Category Id</th>
                        <th>Username</th>
                        <th>Product Name</th>
                        <th>Description</th>
                        <th>Minimum Bid Price</th>
                        <th>Status</th>
                        <th>Start Date</th>
                        <th>End Date</th>
                        <th>Photo</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="product" items="${products}">
                        <tr>
                            <td>
                                <a href="${pageContext.request.contextPath}/app/admin/products?productId=${product.productId}">
                                    Edit
                                </a>

                                <form method="post"
                                      action="${pageContext.request.contextPath}/app/admin/products/delete"
                                      style="display:inline;">
                                    <input type="hidden" name="productId" value="${product.productId}" />
                                    <input type="submit" value="Delete" />
                                </form>

                                <c:if test="${product.status != 'closed'}">
                                    <form method="post"
                                          action="${pageContext.request.contextPath}/app/admin/products/close"
                                          style="display:inline;">
                                        <input type="hidden" name="productId" value="${product.productId}" />
                                        <input type="submit" value="Close" />
                                    </form>
                                </c:if>
                            </td>
                            <td><c:out value="${product.productId}" /></td>
                            <td><c:out value="${product.catId}" /></td>
                            <td><c:out value="${product.sellerUsername}" /></td>
                            <td><c:out value="${product.productName}" /></td>
                            <td><c:out value="${product.description}" /></td>
                            <td><c:out value="${product.minBidPrice}" /></td>
                            <td><c:out value="${product.status}" /></td>
                            <td><c:out value="${product.startDate}" /></td>
                            <td><c:out value="${product.endDate}" /></td>
                            <td>
                                <c:if test="${not empty product.photoPath}">
                                    <img src="${pageContext.request.contextPath}${product.photoPath}"
                                         alt="Product Photo"
                                         style="width:90px; height:90px;" />
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <p>Delete should fail if bids already exist. In that case, use Close instead.</p>
        </c:otherwise>
    </c:choose>
</body>
</html>
