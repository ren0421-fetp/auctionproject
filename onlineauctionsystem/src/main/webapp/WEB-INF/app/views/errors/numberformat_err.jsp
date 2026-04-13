<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${errorTitle}</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f7f8fa;
            color: #1f2937;
            margin: 0;
            padding: 40px 20px;
        }
        .error-card {
            max-width: 720px;
            margin: 0 auto;
            background: #ffffff;
            border: 1px solid #e5e7eb;
            border-radius: 12px;
            padding: 32px;
            box-shadow: 0 8px 24px rgba(0, 0, 0, 0.06);
        }
        h1 {
            margin-top: 0;
            color: #b45309;
        }
        p {
            line-height: 1.6;
        }
        a {
            display: inline-block;
            margin-top: 16px;
            color: #2563eb;
            text-decoration: none;
        }
    </style>
</head>
<body>
    <div class="error-card">
        <h1>${errorTitle}</h1>
        <p>${errorMessage}</p>
        <a href="/app/login">Go to Login</a>
    </div>
</body>
</html>
