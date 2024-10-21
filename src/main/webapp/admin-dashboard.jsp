<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Model.Restaurant" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Restaurant List</title>
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
            animation: fadeIn 1s ease-in-out; /* Page fade-in animation */
        }

        @keyframes fadeIn {
            from {
                opacity: 0;
            }
            to {
                opacity: 1;
            }
        }

        h1 {
            color: #ff6600;
            margin-bottom: 30px;
            text-transform: uppercase;
            letter-spacing: 2px;
            animation: slideDown 0.8s ease-out; /* Heading animation */
        }

        @keyframes slideDown {
            from {
                transform: translateY(-50px);
                opacity: 0;
            }
            to {
                transform: translateY(0);
                opacity: 1;
            }
        }

        .button-container {
            display: flex;
            gap: 70px;
            margin-bottom: 30px;
            justify-content: center;
        }

        .restaurant-container {
            display: grid;
            grid-template-columns: repeat(3, 1fr);
            gap: 20px;
            width: 100%;
            max-width: 1300px;
            padding: 0 20px;
        }

        .restaurant-box {
            background: linear-gradient(135deg, #ffffff, #f9f9f9);
            border: 2px solid transparent;
            border-radius: 15px;
            padding: 20px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1), 0 8px 16px rgba(0, 0, 0, 0.1);
            position: relative;
            overflow: hidden;
            transition: all 0.4s ease;
            z-index: 1;
        }

        .restaurant-box:before {
            content: '';
            position: absolute;
            top: -5px;
            left: -5px;
            right: -5px;
            bottom: -5px;
            background: linear-gradient(45deg, rgba(255, 102, 0, 0.5), rgba(52, 161, 52, 0.5));
            z-index: -1;
            border-radius: 20px;
            opacity: 0;
            transition: opacity 0.4s ease, transform 0.4s ease;
            transform: scale(0.95);
        }

        .restaurant-box:hover:before {
            opacity: 1;
            transform: scale(1);
        }

        .restaurant-box:hover {
            transform: translateY(-10px);
            box-shadow: 0 12px 24px rgba(0, 0, 0, 0.2);
            border-color: #ff6600;
        }

        .restaurant-box h2 {
            color: #ff6600;
            margin: 0 0 15px;
            font-size: 1.7em;
            font-weight: bold;
            letter-spacing: 1px;
        }

        .restaurant-box h2:after {
            content: '';
            display: block;
            width: 60px;
            height: 3px;
            background-color: #ff6600;
            margin: 8px auto 0;
        }

        .restaurant-box p {
            margin: 10px 0;
            color: #333;
            line-height: 1.6;
            font-weight: bold;
        }

        .strong {
            font-weight: bold;
            color: #34a134;
        }

        .restaurant-box button {
            padding: 10px 20px;
            background-color: #ff6600;
            color: #fff;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: all 0.3s ease;
            text-transform: uppercase;
            font-size: 1.1em;
            font-weight: bold;
        }

        .restaurant-box button:hover {
            background-color: #cc5200;
            transform: scale(1.05);
        }

        .restaurant-box .button-container {
            display: flex;
            gap: 20px;
            justify-content: center;
            margin-top: 20px;
        }

        .restaurant-box button.edit-btn {
            background-color: #ff6600;
        }

        .restaurant-box button.delete-btn {
            background-color: #ff6600;
        }

        .restaurant-box button.delete-btn:hover {
            background-color: #cc5200;
        }

        .add-restaurant-btn, .add-dishes-btn, .add-admin-btn {
            padding: 10px 30px;
            background-color: #34a134;
            color: #fff;
            border: 2px solid #28a828;
            border-radius: 10px;
            font-size: 1.2em;
            font-weight: bold;
            transition: all 0.3s ease;
            text-transform: uppercase;
        }

        .add-restaurant-btn:hover, .add-dishes-btn:hover, .add-admin-btn:hover {
            background-color: #ff6600;
            transform: scale(1.2, 1.1);
            border-color: #cc5200;
        }

        a {
            text-decoration: none;
        }

        .no-data {
            color: #ff3333;
            font-size: 1.2em;
            margin: 20px;
        }

        @media (max-width: 900px) {
            .restaurant-container {
                grid-template-columns: repeat(2, 1fr);
            }
        }

        @media (max-width: 600px) {
            .restaurant-container {
                grid-template-columns: 1fr;
            }
        }

        .back-button {
            position: absolute;
            top: 25px;
            right: 50px;
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
        .logout-button {
            color: #ff6600;
            background-color: #e88643;
            width: 5%;
            transition: background-color 0.4s ease,color 0.4s ease;
            position: absolute;
            top: 1.8rem;
            right: 4rem;
            /* Position to the right */
             /* Ensure it's aligned to the right */
        }
        .logout-button:hover {
            color: #fa6804;
            background-color: #393333;
        }

    </style>
</head>
<body>

<h1 style="margin-bottom: 2rem;">Restaurant List</h1>

<a href="logOut">
    <button class="logout-button">
        <img src="logout.png" alt="Logout" style="width: 30px; height: 30px; "/>
    </button>
</a>

<div class="button-container" style="margin-top: 1rem;">
    <a href="add-admin.html">
        <button class="add-admin-btn">Add Admin</button>
    </a>
    <a href="add-restaurant.html">
        <button class="add-restaurant-btn">Add Restaurant</button>
    </a>
    <a href="add-dish.html">
        <button class="add-dishes-btn">Add Dishes</button>
    </a>
</div>

<div class="restaurant-container">
    <%
        ArrayList<Restaurant> list = (ArrayList<Restaurant>) session.getAttribute("list");
        if (list != null && !list.isEmpty()) {
            for (Restaurant restaurant : list) {
    %>
    <div class="restaurant-box">
        <h2><%= restaurant.getName() %>
        </h2>
        <p><span class="strong">Cuisine:</span> <%= restaurant.getCusionType() %>
        </p>
        <p><span class="strong">Delivery Time:</span> <%= restaurant.getDeliveryTime() %> mins</p>
        <p><span class="strong">Ratings:</span> <%= restaurant.getRatings() %> stars</p>
        <p class="<%= restaurant.getIs_active().equalsIgnoreCase("active") ? "status-active" : "status-inactive" %>">
            Status: <%= restaurant.getIs_active().equalsIgnoreCase("active") ? "Active" : "Inactive" %>
        </p>
        <div class="button-container">
            <a href="fetchDishes?id=<%=restaurant.getId()%>">
                <button class="edit-btn">Edit</button>
            </a>
            <form action="deleteRestaurant" method="post" style="display:inline;"
                  onsubmit="return confirm('Are you sure you want to delete this restaurant?');">
                <input type="hidden" name="id" value="<%=restaurant.getId()%>">
                <button type="submit" class="delete-btn">Delete</button>
            </form>
        </div>
    </div>
    <%
        }
    } else {
    %>
    <p class="no-data">No restaurants available.</p>
    <%
        }
    %>
</div>

</body>
</html>
