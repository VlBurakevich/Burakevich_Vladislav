<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add New Movie</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f9;
            margin: 0;
            padding: 20px;
        }

        form {
            max-width: 600px;
            margin: 0 auto;
            background-color: #fff;
            padding: 20px;
            border: 1px solid #ddd;
        }

        label {
            display: block;
            margin-bottom: 10px;
            font-weight: bold;
        }

        input, textarea, select {
            width: 100%;
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #ddd;
        }

        button {
            padding: 10px 20px;
            background-color: #4CAF50;
            color: white;
            border: none;
            cursor: pointer;
        }

        button:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>

<header>
    <h1>Add New Movie</h1>
</header>

<
<form action="<%=request.getContextPath()%>/movies/add" method="POST">
    <input type="text" name="title" placeholder="Movie Title">
    <textarea name="description" placeholder="Movie Description"></textarea>
    <input type="text" name="duration" placeholder="Duration (e.g., PT1H30M)">
    <input type="date" name="releaseDate">
    <input type="text" name="genreNames" placeholder="Genres (comma separated)">

    <input type="text" name="memberFirstName" placeholder="Member First Name">
    <input type="text" name="memberLastName" placeholder="Member Last Name">
    <input type="text" name="memberNationality" placeholder="Member Nationality">
    <input type="text" name="memberType" placeholder="Member Type (ACTOR, PRODUCER)">
    <input type="text" name="memberGender" placeholder="Member Gender (MALE, FEMALE)">

    <button type="submit">Add Movie</button>
</form>
</body>
</html>
