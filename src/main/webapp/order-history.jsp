<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.ArrayList, java.util.HashMap, Model.OrderItems" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Order History</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        body {
            font-family: 'Arial', sans-serif;
            background: linear-gradient(135deg, #ffcc00, #ff6600);
            padding: 20px;
            display: flex;
            flex-direction: column;
            align-items: center;
            color: #333;
        }
        h2 {
            color: white;
            margin-top: 1rem;
            margin-bottom: 7rem;
            text-transform: uppercase;
            letter-spacing: 2px;
            font-size: 2.2rem;
            animation: fadeIn 1s ease-in-out;
        }

        /* Back Button Styles */
        .back-button {
            position: fixed;
            top: 30px;
            left: 30px;
            background-color: white;
            color: #ff6600;
            padding: 13px 40px;
            font-size: 18px;
            border-radius: 10px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
            transition: background-color 0.4s ease, color 0.4s ease, transform 0.3s;
            cursor: pointer;
            border: none;
            z-index: 2;
        }

        .back-button:hover {
            color: #fa6804;
            background-color: #393333;
            transform: scale(1.05); /* Scale up on hover */
        }

        /* Container Animation */
        .order-container {
            display: grid;
            grid-template-columns: repeat(3, 1fr);
            gap: 20px;
            width: 100%;
            max-width: 1300px;
            animation: slideUp 1s ease-in-out;
        }

        /* Order Box with Hover Effect */
        .order-box {
            background: #fff;
            border: 2px solid #f0f0f0;
            border-radius: 10px;
            padding: 20px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            transition: transform 0.3s ease, box-shadow 0.3s ease;
            position: relative;
            font-size: 1.1em;
        }

        .order-box:hover {
            transform: translateY(-10px);
            box-shadow: 0 12px 24px rgba(0, 0, 0, 0.3); /* Stronger shadow on hover */
            background: linear-gradient(135deg, #fff8e1, #fff); /* Gradient background on hover */
        }

        .order-box h3 {
            color: #ff6600;
            font-size: 1.5em;
            margin-bottom: 10px;
            transition: color 0.3s ease;
        }

        /* Restaurant Name Style */
        .restaurant-name {
            font-size: 1.8em;
            color: #333;
            font-weight: bold;
            margin-bottom: 15px;
            text-transform: uppercase;
            animation: fadeInUp 1s ease-in-out;
            letter-spacing: 1.5px;
        }

        /* Advanced Details Animation */
        .order-details p {
            margin: 10px 0;
            opacity: 0;
            transform: translateY(20px);
            animation: fadeInUp 0.5s forwards ease-in-out;
            animation-delay: 0.2s;
        }

        .order-details p strong {
            color: #ff6600;
        }

        .total-amount {
            color: #34a134;
            font-weight: bold;
            font-size: 1.2em;
            margin-top: 20px;
        }

        /* Empty Message Styling */
        .empty-message {
            font-size: 1.2em;
            color: #ff3333;
            text-align: center;
            margin-top: 100px;
            animation: fadeIn 1s ease-in-out;
        }

        /* Keyframes for Animations */
        @keyframes fadeIn {
            from {
                opacity: 0;
            }
            to {
                opacity: 1;
            }
        }

        @keyframes slideUp {
            from {
                transform: translateY(100px);
                opacity: 0;
            }
            to {
                transform: translateY(0);
                opacity: 1;
            }
        }

        @keyframes fadeInUp {
            from {
                opacity: 0;
                transform: translateY(20px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }

        /* Responsive Design Tweaks */
        @media (max-width: 900px) {
            .order-container {
                grid-template-columns: repeat(2, 1fr);
            }
        }

        @media (max-width: 600px) {
            .order-container {
                grid-template-columns: 1fr;
            }
        }
    </style>
</head>
<body>

<!-- Back Button -->
<a href="dashboard.jsp">
    <button class="back-button">Back</button>
</a>

<h2>Order History</h2>

<div class="order-container">
    <%
        HashMap<Integer, ArrayList<OrderItems>> historyList = (HashMap<Integer, ArrayList<OrderItems>>) session.getAttribute("historyList");
        HashMap<Integer, Integer> orderTotals = (HashMap<Integer, Integer>) session.getAttribute("orderTotals");

        if (historyList != null && !historyList.isEmpty()) {
            for (Integer o_id : historyList.keySet()) {
                ArrayList<OrderItems> items = historyList.get(o_id);
                int totalAmount = orderTotals.get(o_id);
                String restaurantName = items.get(0).getRestaurantName(); // Assuming restaurant name is part of OrderItems
    %>
    <div class="order-box">
        <h3>Order Date: <%= items.get(0).getDate() %></h3>
        <p class="restaurant-name"><%= restaurantName %></p> <!-- Restaurant Name Display -->
        <div class="order-details">
            <% for (OrderItems item : items) { %>
            <p><strong>Dish:</strong> <%= item.getDish_name() %>, <strong>Qty:</strong> <%= item.getQuantity() %>,
                <strong>Subtotal:</strong> ₹<%= item.getSubtotal() %></p>
            <% } %>
        </div>
        <p class="total-amount">Total Amount: ₹<%= totalAmount %></p>
    </div>
    <%
        }
    } else {
    %>
    <p class="empty-message">No order history available.</p>
    <%
        }
    %>
</div>

</body>
</html>
