<%@ page import="java.util.List" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Watched Movies</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f9;
            margin: 0;
            padding: 20px;
        }

        header {
            text-align: center;
            margin-bottom: 20px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        table th, table td {
            padding: 10px;
            border: 1px solid #ddd;
            text-align: left;
        }

        table th {
            background-color: #4CAF50;
            color: white;
        }

        .actions button {
            background-color: #f44336;
            color: white;
            border: none;
            padding: 5px 10px;
            border-radius: 3px;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        .actions button:hover {
            background-color: #d32f2f;
        }

        .actions-button {
            background-color: #4CAF50;
            color: white;
            border: none;
            padding: 10px 15px;
            border-radius: 3px;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        .actions-button:hover {
            background-color: #45a049;
        }

        .username {
            margin-bottom: 20px;
            display: flex;
            justify-content: flex-start;
            align-items: center;
        }

        .username label {
            margin-right: 10px;
            font-weight: bold;
        }

        .username input {
            padding: 8px;
            font-size: 14px;
            border: 1px solid #ccc;
            border-radius: 4px;
            width: 300px;
        }
    </style>
</head>
<body>

<header>
    <h1>Watched Movies</h1>
</header>

<div class="username">
    <label for="username">Username:</label>
    <input type="text" id="username" name="username" value="${username}" required>
</div>

<div class="go-to-home">
    <form method="get" action="${pageContext.request.contextPath}/home">
        <button type="submit" class="actions-button">Back to Home</button>
    </form>
</div>

<table>
    <thead>
    <tr>
        <th>Title</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <c:if test="${not empty watchedMovies}">
        <c:forEach var="movie" items="${watchedMovies}">
            <tr>
                <td>${movie.title}</td>
                <td class="actions">
                    <form method="post" action="${pageContext.request.contextPath}/playlists/viewingHistory">
                        <input type="hidden" name="movieId" value="${movie.id}">
                        <input type="hidden" name="username" value="${username}">
                        <input type="hidden" name="action" value="removeFromWatched">
                        <button type="submit">Remove from Watched</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </c:if>

    <c:if test="${empty watchedMovies}">
        <tr>
            <td colspan="2">No movies in Watched</td>
        </tr>
    </c:if>
    </tbody>
</table>

</body>
</html>
