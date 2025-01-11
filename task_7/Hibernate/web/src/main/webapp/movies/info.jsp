<%@ page import="org.senla.dto.MovieInfoDto" %>
<%@ page import="org.senla.dto.ReviewDto" %>
<%@ page import="org.senla.dto.GenreDto" %>
<%@ page import="org.senla.dto.MemberDto" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>${movie.title} - Info</title>
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
    .movie-info {
      border: 1px solid #ddd;
      padding: 20px;
      background-color: #fff;
      max-width: 800px;
      margin: 0 auto;
    }
    .movie-info h2 {
      margin-top: 0;
    }
    .section {
      margin-top: 20px;
    }
    .section ul {
      padding-left: 20px;
    }
  </style>
</head>
<body>

<header>
  <h1>Movie Info</h1>
</header>

<div class="movie-info">
  <h2>${movie.title}</h2>
  <p><strong>Description:</strong> ${movie.description}</p>
  <p><strong>Duration:</strong> ${movie.duration.toHours()}h ${movie.duration.toMinutesPart()}m</p>
  <p><strong>Release Date:</strong> ${movie.releaseDate}</p>

  <div class="section">
    <h3>Genres</h3>
    <ul>
      <c:forEach var="genre" items="${movie.genres}">
        <li>${genre.name}</li>
      </c:forEach>
    </ul>
  </div>

  <div class="section">
    <h3>Reviews</h3>
    <ul>
      <c:forEach var="review" items="${movie.reviews}">
        <li>
          <strong>Rating:</strong> ${review.rating}/5<br>
          <strong>Comment:</strong> ${review.reviewText}
        </li>
      </c:forEach>
    </ul>
  </div>

  <div class="section">
    <h3>Cast & Crew</h3>
    <ul>
      <c:forEach var="member" items="${movie.members}">
        <li>
            ${member.firstName} ${member.lastName} (${member.type})
        </li>
      </c:forEach>
    </ul>
  </div>
</div>

</body>
</html>
