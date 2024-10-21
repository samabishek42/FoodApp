<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Base64" %>
<%@ page import="Model.Dish" %>
<html>
<head>
    <title>Dishes List</title>
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
            background: linear-gradient(135deg, #ffcc00, #ff6600);
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.2);
            border-radius: 15px;
            position: relative;
            overflow: hidden;
            z-index: 2;
            animation: fadeIn 1s ease-in-out;
        }

        h1 {
            text-align: center;
            color: #fff;
            margin-bottom: 20px;
            font-size: 32px;
            letter-spacing: 2px;
            text-transform: uppercase;
            text-shadow: 1px 1px 5px rgba(0, 0, 0, 0.3);
        }

        .view-cart {
            position: absolute;
            top: 0;
            right: 90px;
            color: #ff6600;
            margin-top: 44px;
            padding: 13px 40px;
            background-color: white;
            font-size: 18px;
            border-radius: 10px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
            z-index: 2;
            transition: background-color 0.3s ease;
            cursor: pointer;
        }

        .view-cart:hover {
            color: #fa6804;
            background-color: #393333;
        }

        .back-button {
            position: absolute; /* Positioning absolute to the container */
            top: 0; /* Aligns with the top */
            left: 40px; /* Adjusts to the left corner */
            background-color: white; /* Same as the view-cart button */
            color: #ff6600; /* Match the view-cart text color */
            padding: 13px 40px;
            font-size: 18px;
            border-radius: 10px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
            transition: background-color 0.4s ease,color 0.4s ease;
            cursor: pointer;
            border: none;
            margin-top: 44px; /* Aligns with the top of the container */
            z-index: 2; /* Keep it on top */
        }

        .back-button:hover {
            color: #fa6804; /* Hover text color */
            background-color: #393333; /* Hover background color */
        }

        .dish-list {
            display: flex;
            flex-direction: column;
            align-items: center;
            margin: 0;
        }

        .dish-box {
            background-color: #ffffff;
            border-radius: 10px;
            box-shadow: 0 6px 18px rgba(0, 0, 0, 0.3);
            padding: 20px;
            width: calc(75%);
            height: calc(150px * 1.68 * 1.3);
            margin: 20px 0;
            display: flex;
            align-items: center;
            transition: transform 0.3s ease, box-shadow 0.3s ease;
            overflow: hidden;
        }

        .dish-box:hover {
            transform: scale(1.05);
            box-shadow: 0 12px 24px rgba(0, 0, 0, 0.4);
        }

        .dish-box img {
            width: 45%;
            height: auto;
            max-height: 280px;
            border-radius: 10px;
            object-fit: cover;
            object-position: center;
            margin-left: 5%;
            transition: transform 0.3s ease, filter 0.3s ease;
        }

        .dish-details {
            width: 35%;
            display: flex;
            flex-direction: column;
            justify-content: center;
            padding: 5%;
            position: relative;
            transition: color 0.3s ease;
        }

        .dish-details h2 {
            font-size: 28px;
            color: #ff6600;
            margin: 10px 0;
            text-align: left;
            text-transform: uppercase;
            font-weight: bold;
            letter-spacing: 1.5px;
            text-shadow: 2px 2px 5px rgba(0, 0, 0, 0.1);
            transition: color 0.3s ease, transform 0.3s ease;
        }

        .dish-details p {
            margin: 10px 0;
            color: #333;
            font-size: 20px;
            text-align: left;
            line-height: 1.6;
            letter-spacing: 0.75px;
            transition: color 0.3s ease, transform 0.3s ease;
        }

        /* Styling the quantity input */
        .quantity-input {
            width: 75px;
            font-size: 18px;
            padding: 5px;
            margin-right: 10px;
            text-align: center;
            border: 2px solid #ff6600;
            border-radius: 5px;
            margin-bottom: 15px;
        }

        /* Styling the Add to Cart button */
        .btn {
            background-color: #ff6600;
            color: white;
            border: none;
            border-radius: 5px;
            padding: 12px 18px;
            cursor: pointer;
            font-size: 18px;
            font-weight: bold;
            transition: background-color 0.4s ease, color 0.4s ease;
            display: block;
            margin-top: 15px;
            width: 85%;
            text-align: center;
        }

        .btn:hover {
            color: #fa6804;
            background-color: #393333;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Menu</h1>
    <a href="dashboard.jsp">
        <button class="back-button">Back</button>
    </a>
    <div style="margin-top: 6rem;" class="dish-list">
        <a href="cart.jsp">
            <div class="view-cart">View Cart</div>
        </a>
        <%
            ArrayList<Dish> dishList = (ArrayList<Dish>) session.getAttribute("dishListUser");

            if (dishList != null && !dishList.isEmpty()) {
                for (Dish dish : dishList) {
                    String base64Image1 = Base64.getEncoder().encodeToString(dish.getPhoto());
        %>
        <div class="dish-box">
            <div class="dish-details">
                <h2><%= dish.getDish_name() %></h2>
                <p>Description: <%= dish.getDish_description() %></p>
                <p>Price: ₹<%= dish.getPrice() %></p>
                <form action="cart?itemId=<%=dish.getId()%>" method="post">
                    Quantity: <input type="number" name="quantity" value="1" min="1" class="quantity-input"><br>
                    <input type="submit" value="Add to Cart" class="btn">
                    <input type="hidden" name="act" value="add">
                </form>
            </div>
            <img src="data:image/jpeg;base64,<%= base64Image1 %>" alt="<%= dish.getDish_name() %>">
        </div>
        <%
            }
        } else {
        %>
        <p>No dishes available</p>
        <%
            }
        %>
    </div>
</div>
</body>
</html>
