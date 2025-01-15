<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Netflix Home</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f9;
        }

        header {
            background-color: #4CAF50;
            color: white;
            padding: 15px 20px;
            text-align: center;
        }

        .container {
            text-align: center;
            padding: 50px 20px;
        }

        .action-button {
            display: inline-block;
            padding: 15px 30px;
            margin: 10px;
            font-size: 18px;
            color: #ffffff;
            background-color: #4CAF50;
            text-decoration: none;
            border-radius: 5px;
            transition: background-color 0.3s;
        }

        .action-button:hover {
            background-color: #45a049;
        }

        footer {
            background-color: #4CAF50;
            color: white;
            text-align: center;
            padding: 10px 0;
            position: fixed;
            bottom: 0;
            width: 100%;
        }
    </style>
</head>
<body>

<header>
    <h1>Welcome to Netflix</h1>
</header>

<div class="container">
    <h2>What would you like to do?</h2>
    <a href="<%= request.getContextPath() %>/movies/list" class="action-button">Explore Movies</a>
    <a href="<%= request.getContextPath() %>/playlists/watchLater" class="action-button">View Watch Later</a>
    <a href="<%= request.getContextPath() %>/playlists/viewingHistory" class="action-button">View Watch History</a>
</div>

<footer>
    <p>&copy; 2024 Netflix. All Rights Reserved.</p>
</footer>

</body>
</html>
