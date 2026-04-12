<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Manage User</title>
</head>
<body>
    <h1>Manage User</h1>

    <p>
        <a href="${pageContext.request.contextPath}/app/admin/home">Dashboard</a>
    </p>

    <form method="get" action="${pageContext.request.contextPath}/app/admin/users">
        <label for="userType">Filter by Role</label>
        <select id="userType" name="userType">
            <option value="">-- all users --</option>
            <option value="admin" <c:if test="${selectedUserType == 'admin'}">selected</c:if>>admin</option>
            <option value="seller" <c:if test="${selectedUserType == 'seller'}">selected</c:if>>seller</option>
            <option value="bidder" <c:if test="${selectedUserType == 'bidder'}">selected</c:if>>bidder</option>
        </select>
        <input type="submit" value="Filter" />
        <a href="${pageContext.request.contextPath}/app/admin/users">Clear</a>
    </form>

    <c:if test="${not empty userActionSuccess}">
        <p style="color:green;"><c:out value="${userActionSuccess}" /></p>
    </c:if>

    <c:if test="${not empty userActionError}">
        <p style="color:red;"><c:out value="${userActionError}" /></p>
    </c:if>

    <c:choose>
        <c:when test="${empty users}">
            <p>No users found.</p>
        </c:when>
        <c:otherwise>
            <table border="1" cellpadding="8">
                <thead>
                    <tr>
                        <th>Action</th>
                        <th>Username</th>
                        <th>Gender</th>
                        <th>Contact</th>
                        <th>Address</th>
                        <th>City</th>
                        <th>User Type</th>
                        <th>Lock Status</th>
                        <th>Photo</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="user" items="${users}">
                        <tr>
                            <td>
                                <c:choose>
                                    <c:when test="${user.isLocked}">
                                        <form method="post"
                                              action="${pageContext.request.contextPath}/app/admin/users/unlock"
                                              style="display:inline;">
                                            <input type="hidden" name="username" value="${user.username}" />
                                            <input type="submit" value="Unlock" />
                                        </form>
                                    </c:when>
                                    <c:otherwise>
                                        <form method="post"
                                              action="${pageContext.request.contextPath}/app/admin/users/lock"
                                              style="display:inline;">
                                            <input type="hidden" name="username" value="${user.username}" />
                                            <input type="submit" value="Lock" />
                                        </form>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td><c:out value="${user.username}" /></td>
                            <td><c:out value="${user.gender}" /></td>
                            <td><c:out value="${user.contactNo}" /></td>
                            <td><c:out value="${user.address}" /></td>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty user.cityName}">
                                        <c:out value="${user.cityName}" />
                                    </c:when>
                                    <c:otherwise>
                                        <c:out value="${user.cityId}" />
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td><c:out value="${user.userType}" /></td>
                            <td>
                                <c:choose>
                                    <c:when test="${user.isLocked}">Locked</c:when>
                                    <c:otherwise>Active</c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:if test="${not empty user.photoPath}">
                                    <img src="${pageContext.request.contextPath}${user.photoPath}"
                                         alt="User Photo"
                                         style="width:90px; height:90px;" />
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
