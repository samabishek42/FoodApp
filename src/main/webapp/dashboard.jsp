<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Base64" %>
<%@ page import="Model.Restaurant" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.nio.charset.StandardCharsets" %>
<html>
<head>
    <title>Restaurants List</title>
    <style>
        body {
            font-family: 'Helvetica Neue', Arial, sans-serif;
            background-color: #f5f5f5;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            overflow-x: hidden;
        }

        .container {
            width: 100%;

            padding: 30px;
            background: linear-gradient(135deg, #ffcc00, #ff6600); /* Gradient background */
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.2);
            border-radius: 15px;
            position: relative;
            overflow: hidden;
            z-index: 1;
            animation: fadeIn 1s ease-in-out;
        }

        h1 {
            text-align: center;
            color: #fff; /* White color for better contrast */
            margin-bottom: 40px;
            font-size: 32px;
            letter-spacing: 2px;
            text-transform: uppercase;
            text-shadow: 1px 1px 5px rgba(0, 0, 0, 0.3); /* Text shadow for depth */
        }

        .restaurant-list {
            display: flex;
            flex-direction: column;
            align-items: center;
            margin: 0;
        }

        .restaurant-box {
            background-color: #ffffff; /* White background for restaurant box */
            border-radius: 10px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
            padding: 20px;
            width: 75%;
            height: calc(200px * 1.68); /* Increased height */
            margin: 20px 0;
            display: flex;
            align-items: center; /* Center vertically */
            transition: transform 0.3s ease; /* Smooth hover effect for the box */
            overflow: hidden; /* Prevents overflow */
        }

        .restaurant-box:hover {
            transform: translateY(-5px); /* Lift effect on hover */
        }

        .restaurant-details {
            width: 35%; /* Details take 35% of the width */
            display: flex;
            flex-direction: column; /* Stack details vertically */
            justify-content: center; /* Center content vertically */
            padding: 5%; /* Added padding for space around content */
            position: relative; /* Relative positioning for content */
        }

        .restaurant-details h2 {
            font-size: 28px; /* Increased font size for heading */
            color: #ff6600; /* Changed heading color to orange */
            margin: 5px 0; /* Reduced margin for headings */
            text-align: left;
            text-transform: uppercase; /* Uppercase for headings */
            font-weight: bold; /* Bold font for restaurant name */
            letter-spacing: 1.5px; /* Slightly increased letter spacing */
            text-shadow: 2px 2px 5px rgba(0, 0, 0, 0.1); /* Added text shadow for depth */
            animation: fadeInUp 1s ease-in-out; /* Fade-in animation */
        }

        .restaurant-details p {
            margin: 5px 0; /* Smaller margin for paragraphs */
            color: #333;
            font-size: 20px; /* Increased font size for paragraphs */
            text-align: left;
            line-height: 1.6; /* Increased line height for better readability */
            letter-spacing: 0.75px; /* Slightly increased letter spacing */
            transition: color 0.3s ease, transform 0.3s ease; /* Smooth transitions */
            animation: slideIn 0.7s ease-in-out; /* Slide-in effect for paragraph */
        }

        .restaurant-details p:hover {
            color: #ff6600;
            transform: scale(1.05); /* Slightly scale up on hover */
        }

        .restaurant-box img {
            width: 50%; /* Image width */
            height: auto; /* Maintain aspect ratio */
            max-height: 280px; /* Increased max height of image */
            border-radius: 10px;
            object-fit: cover;
            object-position: center; /* Center the image */
            margin-left: 5%; /* Added space between image and details */
            transition: transform 0.3s ease; /* Smooth zoom effect for image */
        }

        button {
            background-color: #ff6600; /* Button color */
            color: white; /* Button text color */
            border: none;
            border-radius: 5px;
            padding: 10px 15px;
            cursor: pointer;
            width: 46%;
            font-size: 18px; /* Button text size */
            transition: background-color 0.4s ease,color 0.4s ease; /* Transition effect */
            margin-top: 10px; /* Space above button */
        }

        button:hover {
            color: #fa6804;
            background-color: #393333;
        }

        .back-button {
            color: #ff6600;
            background-color: white;
            width: 10%;
            transition: background-color 0.4s ease,color 0.4s ease;
            position: relative;
            bottom: 5rem;
            left: 3rem;
        }

        .back-button:hover{
            color: #fa6804;
            background-color: #393333;
        }

        /* Logout button styles */
        .logout-button {
            color: #ff6600;
            background-color: #e88643;
            width: 5%;
            transition: background-color 0.4s ease,color 0.4s ease;
            position: relative;
            bottom: 6rem;
            right: 3rem; /* Position to the right */
            float: right; /* Ensure it's aligned to the right */
        }

        .logout-button:hover {
            color: #fa6804;
            background-color: #393333;
        }

        @keyframes fadeInUp {
            from {
                opacity: 0;
                transform: translateY(10px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }

        @keyframes slideIn {
            from {
                opacity: 0;
                transform: translateX(-10px);
            }
            to {
                opacity: 1;
                transform: translateX(0);
            }
        }
    </style>
</head>
<body style="background-color: #f7f2e7;">
<div class="container">
    <h1>Restaurants</h1>

    <!-- Back Button -->
    <a href="orderHistory">
        <button class="back-button" style="width: 180px;">Order History</button>
    </a>

    <!-- Logout Button -->
    <a href="logOut">
        <button class="logout-button" >
            <img src="logout.png" alt="Logout" style="width: 30px; height: 30px; "/>
        </button>
    </a>


    <div class="restaurant-list">
        <%
            ArrayList<Restaurant> list = (ArrayList<Restaurant>) session.getAttribute("list");

            if (list != null) {
                for (Restaurant restaurant : list) {
                    // Convert photo byte array to Base64 string
                    String base64Image = Base64.getEncoder().encodeToString(restaurant.getPhoto());
        %>
        <div class="restaurant-box">
            <div class="restaurant-details">
                <h2><%= restaurant.getName() %></h2>
                <p>Cuisine: <%= restaurant.getCusionType() %></p>
                <p>Delivery Time: <%= restaurant.getDeliveryTime() %> mins</p>
                <p>Ratings: <%= restaurant.getRatings() %> stars</p>
                <p class="<%= restaurant.getIs_active().equalsIgnoreCase("yes") ? "status-active" : "status-inactive" %>">
                    Status: <%= restaurant.getIs_active().equalsIgnoreCase("yes") ? "Active" : "Inactive" %>
                </p>
                <a href="fetchDishesUser?id=<%=restaurant.getId()%>&restaurant_name=<%= URLEncoder.encode(restaurant.getName(),  StandardCharsets.UTF_8) %>">
                    <button>Order Now</button>
                </a>
            </div>
            <img src="data:image/jpeg;base64,<%= base64Image %>" alt="<%= restaurant.getName() %>">
        </div>
        <%
            }
        } else {
        %>
        <p>No restaurants available</p>
        <%
            }
        %>
    </div>
</div>
</body>
</html>
