<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Model.Dish" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Dishes List</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f7f2e7;
            margin: 0;
            padding: 20px;
            display: flex;
            flex-direction: column;
            align-items: center;
            color: #333;
            animation: fadeIn 1s ease-in-out;
        }
        @keyframes fadeIn {
            from { opacity: 0; }
            to { opacity: 1; }
        }

        /* Back Button */
        .back-button {
            position: absolute;
            top: 20px;
            left: 35px;
            background-color: #ff6600;
            color: #fff;
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            text-transform: uppercase;
            font-size: 1em;
            font-weight: bold;
            text-decoration: none;
            transition: background-color 0.3s ease;
        }
        .back-button:hover {
            background-color: #cc5200;
        }

        h1, h2 {
            color: #ff6600;
            margin-bottom: 30px;
            text-transform: uppercase;
            letter-spacing: 2px;
            animation: slideDown 0.8s ease-out;
        }
        @keyframes slideDown {
            from { transform: translateY(-50px); opacity: 0; }
            to { transform: translateY(0); opacity: 1; }
        }

        /* Extra margin between heading and boxes */
        .heading-margin {
            margin-bottom: 50px; /* Extra space added */
        }

        .dish-list {
            display: grid;
            grid-template-columns: repeat(3, 1fr);
            gap: 20px;
            width: 100%;
            max-width: 1300px;
            padding: 0 20px;
        }
        .dish-box {
            background: linear-gradient(135deg, #ffffff, #f9f9f9);
            border: 2px solid transparent;
            border-radius: 15px;
            padding: 20px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1), 0 8px 16px rgba(0, 0, 0, 0.1);
            position: relative;
            overflow: hidden;
            transition: all 0.4s ease;
        }
        .dish-box h2 {
            margin: 0 0 15px;
            font-size: 1.7em;
            font-weight: bold;
            letter-spacing: 1px;
        }
        .dish-box p {
            margin: 10px 0;
            color: #333;
            line-height: 1.6;
            font-weight: bold;
            font-size: 1.1em;
        }
        .dish-box button {
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: all 0.3s ease;
            text-transform: uppercase;
            font-size: 1.1em;
            font-weight: bold;
            margin-top: 10px;
        }
        .dish-box button.add {
            background-color: #ff6600;
            color: #fff;
        }
        .dish-box button.add:hover {
            background-color: #cc5200;
            transform: scale(1.05);
            font-size: 1.2em;
        }
        .dish-box button.delete {
            background-color: #d9534f;
            color: #fff;
        }
        .dish-box button.delete:hover {
            background-color: #c12e2c;
            transform: scale(1.05);
            font-size: 1.2em;
        }
        .no-data {
            color: #ff3333;
            font-size: 1.2em;
            margin: 20px;
        }

        @media (max-width: 900px) {
            .dish-list {
                grid-template-columns: repeat(2, 1fr);
            }
        }
        @media (max-width: 600px) {
            .dish-list {
                grid-template-columns: 1fr;
            }
        }
    </style>
</head>
<body>
<a href="fetchRestaurantAdmin" class="back-button">Back</a> <!-- Back button -->

<h1 class="heading-margin">Dishes Available</h1> <!-- Heading with extra margin -->
<div class="dish-list">
    <%
        ArrayList<Dish> availableList = (ArrayList<Dish>) session.getAttribute("available");
        ArrayList<Dish> unavailableList = (ArrayList<Dish>) session.getAttribute("unavailable");

        // Display available dishes
        if (availableList != null && !availableList.isEmpty()) {
            for (Dish dish : availableList) {
    %>
    <div style="margin-bottom: 7rem;" class="dish-box">
        <h2><%= dish.getDish_name() %></h2>
        <p>Description: <%= dish.getDish_description() %></p>
        <p>Price: ₹<%= dish.getPrice() %></p>
        <form action="deleteRestaurantDish" method="post">
            <input type="hidden" name="itemId" value="<%= dish.getId() %>">
            <input type="hidden" name="id" value="<%= request.getParameter("id")%>">
            <input type="hidden" name="act" value="delete">
            <button type="submit" class="delete">Delete</button> <!-- Red Delete Button -->
        </form>
    </div>
    <%
        }
    } else {
    %>
    <p class="no-data">No dishes available</p>
    <%
        }
    %>
</div>

<h1 class="heading-margin">Dishes Unavailable</h1> <!-- Heading with extra margin -->
<div class="dish-list">
    <%
        // Display unavailable dishes
        if (unavailableList != null && !unavailableList.isEmpty()) {
            for (Dish dish : unavailableList) {
    %>
    <div class="dish-box">
        <h2><%= dish.getDish_name() %></h2>
        <p>Description: <%= dish.getDish_description() %></p>
        <p>Price: ₹<%= dish.getPrice() %></p>
        <form action="addRestaurantDish" method="post">
            <input type="hidden" name="itemId" value="<%= dish.getId() %>">
            <input type="hidden" name="id" value="<%= request.getParameter("id")%>">
            <input type="hidden" name="act" value="add">
            <button type="submit" class="add">Add</button> <!-- Default Add Button -->
        </form>
    </div>
    <%
        }
    } else {
    %>
    <p class="no-data">No unavailable dishes</p>
    <%
        }
    %>
</div>
</body>
</html>
