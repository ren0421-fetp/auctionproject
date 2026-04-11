<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>My Products</title>
</head>
<body>
    <h1>My Products</h1>

    <p>
        <a href="${pageContext.request.contextPath}/app/seller/home">Seller Dashboard</a> |
        <a href="${pageContext.request.contextPath}/app/seller/product/add">Add Product</a>
    	<a href="${pageContext.request.contextPath}/app/seller/product/bids">View Product Bids</a>
    	
    </p>

    <c:choose>
        <c:when test="${empty sellerProducts}">
            <p>No products found.</p>
        </c:when>
        <c:otherwise>
            <table border="1" cellpadding="8">
                <thead>
                    <tr>
                        <th>product id</th>
                        <th>name</th>
                        <th>category</th>
                        <th>description</th>
                        <th>minimum bid</th>
                        <th>status</th>
                        <th>start date</th>
                        <th>end date</th>
                        <th>photo</th>
                        <th>action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="product" items="${sellerProducts}">
                        <tr>
                            <td><c:out value="${product.productId}" /></td>
                            <td><c:out value="${product.productName}" /></td>
                            <td><c:out value="${product.categoryName}" /></td>
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
                            <td>
                                <a href="${pageContext.request.contextPath}/app/seller/product/edit?productId=${product.productId}">
                                    Edit
                                </a>
                                
                                <form method="post"
							          action="${pageContext.request.contextPath}/app/seller/product/delete"
							          style="display:inline;">
							        <input type="hidden" name="productId" value="${product.productId}" />
							        <input type="submit" value="Delete" />
							    </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>
</body>
</html>
