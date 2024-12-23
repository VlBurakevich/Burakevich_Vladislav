<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Movies</title>
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

    .actions button, .comment-actions button {
      background-color: #4CAF50;
      color: white;
      border: none;
      padding: 5px 10px;
      border-radius: 3px;
      cursor: pointer;
      transition: background-color 0.3s;
    }

    .actions button:hover, .comment-actions button:hover {
      background-color: #45a049;
    }

    .add-movie-form {
      margin-top: 20px;
    }

    .add-movie-form button {
      background-color: #4CAF50;
      color: white;
      border: none;
      padding: 10px 15px;
      border-radius: 3px;
      cursor: pointer;
    }

    .add-movie-form button:hover {
      background-color: #45a049;
    }

    .comment-form textarea {
      width: 95%;
      height: 40px;
      resize: none;
      margin-bottom: 5px;
      border: 1px solid #ccc;
      border-radius: 4px;
      padding: 5px;
    }

    .comment-form select {
      padding: 3px;
    }
  </style>
</head>
<body>

<header>
  <h1>Movies</h1>
</header>

<div class="username">
  <label for="username">Username:</label>
  <input type="text" id="username" name="username" value="${username}" required readonly>
</div>

<div class="add-movie-form">
  <form method="get" action="${pageContext.request.contextPath}/movies/add">
    <button type="submit">Add New Movie</button>
  </form>
</div>

<c:if test="${not empty sessionScope.successMessage}">
  <div style="color: green;">${sessionScope.successMessage}</div>
  <c:set var="successMessage" value="" scope="session"/>
</c:if>

<table>
  <thead>
  <tr>
    <th>Title</th>
    <th>Actions</th>
    <th>Leave a Comment</th>
  </tr>
  </thead>
  <tbody>
  <c:if test="${not empty movies}">
    <c:forEach var="movie" items="${movies}">
      <tr>
        <td>${movie.title}</td>
        <td class="actions">
          <form method="post" action="${pageContext.request.contextPath}/movies/list">
            <input type="hidden" name="movieId" value="${movie.id}">
            <input type="hidden" name="username" value="${username}">
            <input type="hidden" name="action" value="watch">
            <button type="submit">Watch</button>
          </form>
          <form method="post" action="${pageContext.request.contextPath}/movies/list">
            <input type="hidden" name="movieId" value="${movie.id}">
            <input type="hidden" name="username" value="${username}">
            <input type="hidden" name="action" value="watchLater">
            <button type="submit">Add to Watch Later</button>
          </form>
          <form method="get" action="${pageContext.request.contextPath}/movies/info">
            <input type="hidden" name="movieId" value="${movie.id}">
            <button type="submit">Search Info</button>
          </form>
          <form method="post" action="${pageContext.request.contextPath}/movies/delete">
            <input type="hidden" name="movieId" value="${movie.id}">
            <button type="submit" onclick="return confirm('Are you sure you want to delete this movie?');">Delete</button>
          </form>
        </td>
        <td class="comment-actions">
          <form method="post" action="${pageContext.request.contextPath}/movies/review" class="comment-form">
            <textarea id="review-text-${movie.id}" name="review" placeholder="Leave a comment"></textarea>
            <select name="rating" id="rating-${movie.id}" required>
              <c:forEach var="i" begin="1" end="5">
                <option value="${i}">${i}</option>
              </c:forEach>
            </select>
            <input type="hidden" name="movieId" value="${movie.id}">
            <input type="hidden" name="username" value="${username}">
            <button type="submit">Submit</button>
          </form>
        </td>
      </tr>
    </c:forEach>
  </c:if>

  <c:if test="${empty movies}">
    <tr>
      <td colspan="3">No movies available</td>
    </tr>
  </c:if>
  </tbody>
</table>

</body>
</html>
