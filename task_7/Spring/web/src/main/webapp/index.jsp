<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Welcome</title>
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

        .action-button {
            display: block;
            width: 100%;
            padding: 15px;
            margin: 10px 0;
            font-size: 16px;
            color: #ffffff;
            background-color: #4CAF50;
            text-decoration: none;
            text-align: center;
            border-radius: 5px;
            transition: background-color 0.3s;
        }

        .action-button:hover {
            background-color: #45a049;
        }

        .action-button:active {
            background-color: #3d8c43;
        }
    </style>
</head>
<body>

<div class="container">
    <h1>Welcome</h1>
    <a href="<%= request.getContextPath() %>authorization/login" class="action-button">Login</a>
    <a href="<%= request.getContextPath() %>authorization/register" class="action-button">Register</a>
</div>

</body>
</html>
