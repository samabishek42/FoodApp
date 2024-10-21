<%@ page import="Model.Cart" %>
<%@ page import="Model.CartItem" %>
<%@ page import="java.util.List" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@ page import="java.util.Map" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Cart</title>
    <style>
        @import url('https://fonts.googleapis.com/css2?family=Poppins:wght@400;700&family=Roboto&display=swap');

        body {
            background: linear-gradient(135deg, #ffcc00, #ff6600);
            font-family: 'Roboto', sans-serif;
            font-size: 18px;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
        }

        .cart-container {
            width: 36%;
            padding: 20px;
            background-color: white;
            box-shadow: 0px 4px 12px rgba(0, 0, 0, 0.1);
            border-radius: 10px;
            position: relative;
            animation: fadeIn 1s ease-in-out;
        }

        h1 {
            text-align: center;
            font-size: 30px;
            color: #333;
            margin-bottom: 20px;
        }

        .cart-item {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 10px 0;
            border-bottom: 1px solid #ccc;
        }

        .cart-item-name {
            font-family: 'Poppins', sans-serif;
            font-size: 18px;
            max-width: 200px;
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
            flex-grow: 1;
        }

        .cart-item form {
            display: flex;
            align-items: center;
        }

        .cart-item input.quantity-input {
            width: 50px;
            text-align: center;
            margin-right: 10px;
            font-size: 16px;
            flex-shrink: 0;
        }

        .btn {
            background-color: orange;
            border: none;
            color: white;
            padding: 8px 16px;
            cursor: pointer;
            border-radius: 4px;
            font-size: 16px;
            margin-left: 10px;
            transition: background-color 0.3s ease, transform 0.2s ease;
        }

        .btn:hover {
            background-color: #cc5500;
            transform: scale(1.05);
        }

        .btn-green {
            background-color: #28a745;
        }

        .btn-green:hover {
            background-color: #218838;
            transform: scale(1.05);
        }

        .cart-actions {
            margin-top: 20px;
            text-align: right;
            animation: slideIn 1s ease-in-out;
        }

        /* Back button styles */
        .back-button {
            position: fixed; /* Positioning absolute to the container */
            top: 30px; /* Aligns with the top */
            left: 40px; /* Adjusts to the left corner */
            background-color: white; /* Same as the view-cart button */
            color: #ff6600; /* Match the view-cart text color */
            padding: 13px 40px;
            font-size: 18px;
            border-radius: 10px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
            transition: background-color 0.4s ease, color 0.4s ease;
            cursor: pointer;
            border: none;
            z-index: 2; /* Keep it on top */
        }

        .back-button:hover {
            color: #fa6804; /* Hover text color */
            background-color: #393333; /* Hover background color */
        }

        /* Advanced Styles */
        .cart-item:hover {
            background-color: #f7f7f7;
            transition: background-color 0.3s ease-in-out;
        }

        input.quantity-input:focus {
            border: 2px solid #ff6600;
            outline: none;
            transition: border 0.2s ease-in-out;
        }

        /* Animations */
        @keyframes fadeIn {
            from { opacity: 0; }
            to { opacity: 1; }
        }

        @keyframes slideIn {
            from { transform: translateY(20px); opacity: 0; }
            to { transform: translateY(0); opacity: 1; }
        }

        /* Empty cart message style */
        .empty-cart-message {
            font-size: 24px; /* Increase font size */
            text-align: center;
            margin-top: 20px;
        }

        /* Ensuring consistent button alignment */
        .cart-item form {
            width: 150px;
        }

        .btn-wrapper {
            display: flex;
            justify-content: flex-end;
        }
        .grab-food-btn {
            display: block;
            margin: 20px auto; /* Center the button */
            padding: 12px 24px;
            background-color: #ff6600;
            color: white;
            border: none;
            font-size: 18px;
            border-radius: 8px;
            cursor: pointer;
            text-align: center;
            transition: background-color 0.3s ease, color 0.3s ease;
        }

        .grab-food-btn:hover {
            background-color: #393333;
            color: #fa6804;
        }

    </style>
</head>
<body>

<div class="cart-container">
    <h1>Your Cart</h1>

    <!-- Back Button -->
    <a href="Restaurant.jsp">
        <button class="back-button">Back</button>
    </a>

    <% Cart cart = (Cart) session.getAttribute("cart"); %>
    <% if (cart != null) { %>
    <%
        Map<Integer, CartItem> items = cart.getItems();
        if (items != null && !items.isEmpty()) {
            for (CartItem item : items.values()) {
    %>
    <div class="cart-item">
        <div class="cart-item-name"><%= item.getName() %></div>
        <div class="btn-wrapper">
            <form action="cart?itemId=<%= item.getItemId() %>" method="post">
                <input type="number" name="quantity" value="<%= item.getQuantity() %>" min="1" class="quantity-input">
                <input type="submit" value="Update" class="btn">
                <input type="hidden" name="act" value="update">
            </form>
            <form action="cart?itemId=<%= item.getItemId() %>" method="post">
                <input type="submit" value="Remove" class="btn" style="margin-left: 30px;"> <!-- Set margin for right spacing -->
                <input type="hidden" name="act" value="remove">
            </form>
        </div>
    </div>
    <%
        }
    } else {
    %>
    <p class="empty-cart-message">Your cart is empty.</p> <!-- Add class for increased font size -->
    <form action="Restaurant.jsp">
        <input type="submit" value="Grab a Food" class="grab-food-btn">
    </form>

    <%
        }
    } else {
    %>
    <p class="empty-cart-message">Your cart is empty.</p> <!-- Add class for increased font size -->
    <form action="dashboard.jsp">
        <input type="submit" value="Grab a Food" class="grab-food-btn">
    </form>

    <% } %>

    <%-- Only show the checkout button if there are items in the cart --%>
    <% if (cart != null && !cart.getItems().isEmpty()) { %>
    <div class="cart-actions">
        <form action="checkout.jsp">
            <input type="submit" value="Proceed to Checkout" class="btn btn-green">
        </form>
    </div>
    <% } %>
</div>

</body>
</html>
