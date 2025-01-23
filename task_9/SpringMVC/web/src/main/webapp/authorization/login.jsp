<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f9;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        .container {
            text-align: center;
            background-color: #ffffff;
            padding: 40px;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 400px;
        }

        .container h1 {
            color: #333;
            margin-bottom: 20px;
        }

        .form-group {
            margin-bottom: 15px;
            text-align: left;
        }

        .form-group label {
            display: block;
            font-weight: bold;
            margin-bottom: 5px;
        }

        .form-group input {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
            font-size: 14px;
            box-sizing: border-box;
        }

        .form-group button {
            width: 100%;
            padding: 12px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s;
            font-size: 16px;
        }

        .form-group button:hover {
            background-color: #45a049;
        }

        .errors {
            color: red;
            font-size: 14px;
            margin-bottom: 15px;
            text-align: left;
        }

        .errors ul {
            padding: 0;
            margin: 0;
            list-style: none;
        }

        .errors li {
            margin-bottom: 5px;
        }

        .error-message {
            font-size: 14px;
            color: red;
            margin-bottom: 10px;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Login</h1>
    <form action="<%= request.getContextPath() %>/authorization/login" method="post">
        <div class="errors">
            <c:if test="${not empty authError}">
                <div class="error-message">${authError}</div>
            </c:if>

            <form:errors path="*" cssClass="error-message" />
        </div>

        <div class="form-group">
            <label for="username">Username</label>
            <input type="text" id="username" name="username" value="${loginDto.username}" required/>
            <form:fieldError path="username" cssClass="error-message" />
        </div>

        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" id="password" name="password" required />
            <form:fieldError path="password" cssClass="error-message" />
        </div>

        <div class="form-group">
            <button type="submit">Login</button>
        </div>
    </form>
</div>
</body>
</html>
