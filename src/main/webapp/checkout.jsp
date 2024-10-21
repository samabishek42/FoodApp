<%@ page import="Model.Cart" %>
<%@ page import="Model.CartItem" %>
<%@ page import="java.util.Map" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Checkout</title>
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

        .checkout-container {
            width: 40%;
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

        .checkout-item {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 10px 0;
            border-bottom: 1px solid #ccc;
        }

        .checkout-item-name {
            font-family: 'Poppins', sans-serif;
            font-size: 18px;
            flex-grow: 1;
        }

        .checkout-item-price {
            font-size: 18px;
            font-weight: bold;
        }

        .total-amount {
            font-size: 24px;
            font-weight: bold;
            text-align: right;
            margin-top: 20px;
        }

        .address-section {
            margin-top: 20px;
        }

        .address-input {
            width: 97%;
            padding: 10px;
            font-size: 16px;
            border: 1px solid #ccc;
            border-radius: 5px;
            margin-top: 10px;
        }

        .payment-options {
            margin-top: 20px;
        }

        .payment-option {
            display: flex;
            align-items: center;
            margin-bottom: 10px;
        }

        .payment-option input {
            margin-right: 10px;
        }

        .btn-green {
            background-color: #28a745;
            border: none;
            color: white;
            padding: 10px 20px;
            cursor: pointer;
            border-radius: 4px;
            font-size: 16px;
            margin-top: 20px;
            transition: background-color 0.3s ease, transform 0.2s ease;
            width: 100%;
        }

        .btn-green:hover {
            background-color: #218838;
            transform: scale(1.05);
        }

        .back-button {
            position:fixed;
            top: 30px;
            left: 30px;
            background-color: white;
            color: #ff6600;
            padding: 13px 40px;
            font-size: 18px;
            border-radius: 10px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
            transition: background-color 0.4s ease, color 0.4s ease;
            cursor: pointer;
            border: none;
            z-index: 2;
        }

        .back-button:hover {
            color: #fa6804;
            background-color: #393333;
        }

        @keyframes fadeIn {
            from { opacity: 0; }
            to { opacity: 1; }
        }
    </style>
</head>
<body>

<div class="checkout-container">
    <a href="cart.jsp">
        <button class="back-button">Back</button>
    </a>
    <h1>Checkout</h1>

    <form action="checkout" method="post">
        <%
            Cart cart = (Cart) session.getAttribute("cart");
            double totalAmount = 0.0;
        %>

        <% if (cart != null) { %>
        <%
            Map<Integer, CartItem> items = cart.getItems();
            if (items != null && !items.isEmpty()) {
                for (CartItem item : items.values()) {
                    double itemTotal = item.getQuantity() * item.getPrice();
                    totalAmount += itemTotal;
        %>
        <div class="checkout-item">
            <span class="checkout-item-name"><%= item.getName() %> (x<%= item.getQuantity() %>)</span>
            <span class="checkout-item-price">₹<%= itemTotal %></span>

            <!-- Hidden fields for item details -->
            <input type="hidden" name="itemName" value="<%= item.getName() %>">
            <input type="hidden" name="itemQuantity" value="<%= item.getQuantity() %>">
            <input type="hidden" name="itemPrice" value="<%= item.getPrice() %>">
            <input type="hidden" name="itemTotal" value="<%= itemTotal %>">
        </div>
        <%
                }
            }
        %>
        <div class="total-amount">
            Total Amount: ₹<%= totalAmount %>
            <input type="hidden" name="totalAmount" value="<%= totalAmount %>">
        </div>
        <% } %>

        <!-- Address Section -->
        <div class="address-section">
            <label for="address">Delivery Address:</label>
            <input type="text" id="address" maxlength="255" name="address" class="address-input" placeholder="Enter your address" required>
        </div>

        <!-- Payment Options -->
        <div class="payment-options">
            <label style="margin-bottom: 20px;" for="payment-mode">Payment Mode:</label>
            <div class="payment-option">
                <input type="radio" id="cod" name="paymentMode" value="COD" required>
                <label for="cod">Cash on Delivery (COD)</label>
            </div>
            <div class="payment-option">
                <input type="radio" id="card" name="paymentMode" value="Card" required>
                <label for="card">Card</label>
            </div>
            <div class="payment-option">
                <input type="radio" id="upi" name="paymentMode" value="UPI" required>
                <label for="upi">UPI</label>
            </div>
        </div>

        <!-- Confirm Payment Button -->
        <button type="submit" class="btn-green">Confirm Payment</button>
    </form>
</div>

</body>
</html>
