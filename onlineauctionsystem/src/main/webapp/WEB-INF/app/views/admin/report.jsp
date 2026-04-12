<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Reports</title>
</head>
<body>
    <h1>Reports</h1>

    <p>
        <a href="${pageContext.request.contextPath}/app/admin/home">Dashboard</a>
    </p>

    <ul>
        <li>
            <a href="${pageContext.request.contextPath}/app/admin/reports/pdf?type=users">
                User Registration Report (PDF)
            </a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/app/admin/reports/pdf?type=products">
                Auction Item Report (PDF)
            </a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/app/admin/reports/pdf?type=bids">
                Bid Report (PDF)
            </a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/app/admin/reports/pdf?type=confirmed-bids">
                Confirmed Bid Report (PDF)
            </a>
        </li>
    </ul>
</body>
</html>
