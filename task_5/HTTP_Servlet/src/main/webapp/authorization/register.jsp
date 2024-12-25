<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register</title>
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
            margin-bottom: 30px;
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
        }

        .errors ul {
            padding: 0;
            margin: 0;
            list-style: none;
        }

        .errors li {
            margin-bottom: 5px;
        }
    </style>
</head>
<body>

<div class="container">
    <h1>Register</h1>
    <form action="<%= request.getContextPath() %>/authorization/register" method="post">
        <%-- Отображение ошибок --%>
        <div class="errors">
            <% if (request.getAttribute("errors") != null) { %>
            <ul>
                <% List<String> errors = (List<String>) request.getAttribute("errors"); %>
                <% for (String error : errors) { %>
                <li><%= error %>
                </li>
                <% } %>
            </ul>
            <% } %>
        </div>

        <%-- Поля формы с сохранением ранее введённых данных --%>
        <div class="form-group">
            <label for="username">Username</label>
            <input
                    type="text"
                    id="username"
                    name="username"
                    placeholder="Enter your username"
                    value="<%= request.getAttribute("username") != null ? request.getAttribute("username") : "" %>"
                    required>
        </div>
        <div class="form-group">
            <label for="email">Email</label>
            <input
                    type="email"
                    id="email"
                    name="email"
                    placeholder="Enter your email"
                    value="<%= request.getAttribute("email") != null ? request.getAttribute("email") : "" %>"
                    required>
        </div>
        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" id="password" name="password" placeholder="Enter your password" required>
        </div>
        <div class="form-group">
            <label for="confirmPassword">Confirm Password</label>
            <input type="password" id="confirmPassword" name="confirmPassword" placeholder="Confirm your password"
                   required>
        </div>
        <div class="form-group">
            <button type="submit">Register</button>
        </div>
    </form>
</div>

</body>
</html>
