package DAOImpl.User;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.time.LocalDate;
import java.util.Map;

import Model.Cart;
import Model.CartItem;
import jakarta.servlet.http.HttpSession;

@WebServlet("/checkout")
public class Checkout extends HttpServlet {
      private Connection con;

      @Override
      public void init(ServletConfig config) throws ServletException {
            try {
                  Class.forName("com.mysql.cj.jdbc.Driver");
                  con = DriverManager.getConnection("jdbc:mysql://localhost:3306/foodapp", "root", "abishek@1");

            } catch (SQLException e) {
                  System.err.println("SQL Exception: " + e.getMessage());
                  e.printStackTrace();
            } catch (ClassNotFoundException e) {
                  System.err.println("JDBC Driver not found: " + e.getMessage());
                  e.printStackTrace();
            } catch (Exception e) {
                  System.err.println("General Exception: " + e.getMessage());
                  e.printStackTrace();
            }
      }


      @Override
      protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            PrintWriter pw = resp.getWriter();
            resp.setContentType("text/html");

            PreparedStatement stmtOrder = null;
            PreparedStatement stmtOrderItems = null;
            PreparedStatement stmtOrderHistory = null;

            try {

                  String orderQuery = "INSERT INTO orders (user_id, restaurant_name,totalAmount, status, date, paymentOptions,address) VALUES (?, ?, ?, ?, ?, ?, ?)";

                  if(con!=null)pw.println("<h1>Database connection established.</h1>");
                  stmtOrder = con.prepareStatement(orderQuery, Statement.RETURN_GENERATED_KEYS);

                  HttpSession session = req.getSession();
                  Integer userId = (Integer) session.getAttribute("userId");
                  String restaurantName = (String) session.getAttribute("restaurant_name");
                  if (userId == null || restaurantName == null) {
                        pw.println("<h1>Session has expired. Please log in again.</h1>");
                        return;
                  }

                  String totalAmountStr = req.getParameter("totalAmount").trim();

                  // Parse the String to a Double first
                  double tempTotalAmount = Double.parseDouble(totalAmountStr);

                  // Cast to Integer (will truncate the decimal)
                  int totalAmount = (int) tempTotalAmount;
                  String paymentMode = req.getParameter("paymentMode");
                  String address=req.getParameter("address");

                  stmtOrder.setInt(1, userId);
                  stmtOrder.setString(2, restaurantName);
                  stmtOrder.setDouble(3, totalAmount);
                  stmtOrder.setString(4, "order placed");
                  stmtOrder.setDate(5, Date.valueOf(LocalDate.now()));
                  stmtOrder.setString(6, paymentMode);
                  stmtOrder.setString(7,address);

                  int countOrder = stmtOrder.executeUpdate();


                  ResultSet generatedKeys = stmtOrder.getGeneratedKeys();
                  int o_id = -1;
                  if (generatedKeys.next()) {
                        o_id = generatedKeys.getInt(1);
                  }


                  Cart cart = (Cart) session.getAttribute("cart");
                  if (cart != null) {
                        Map<Integer, CartItem> items = cart.getItems();
                        if (items != null && !items.isEmpty()) {
                              String orderItemQuery = "INSERT INTO orderitems (o_id, dish_id,dish_name, quantity, subtotal) VALUES (?,?,?,?,?)";
                              stmtOrderItems = con.prepareStatement(orderItemQuery);

                              for (CartItem item : items.values()) {
                                    stmtOrderItems.setInt(1, o_id);
                                    stmtOrderItems.setInt(2, item.getItemId());
                                    stmtOrderItems.setString(3,item.getName());
                                    stmtOrderItems.setInt(4, item.getQuantity());
                                    stmtOrderItems.setInt(5, (int) (item.getQuantity() * item.getPrice()));
                                    stmtOrderItems.addBatch();
                              }
                              stmtOrderItems.executeBatch();
                        }
                  }


                  String orderHistoryQuery = "INSERT INTO orderhistory (user_id, o_id, total, status, date,restaurant_name) VALUES (?, ?, ?, ?, ?, ?)";
                  stmtOrderHistory = con.prepareStatement(orderHistoryQuery);

                  stmtOrderHistory.setInt(1, userId);
                  stmtOrderHistory.setInt(2, o_id);
                  stmtOrderHistory.setInt(3, totalAmount);
                  stmtOrderHistory.setString(4, "Order Placed");
                  stmtOrderHistory.setDate(5, Date.valueOf(LocalDate.now()));
                  stmtOrderHistory.setString(6,restaurantName);
                  stmtOrderHistory.executeUpdate();

                  // Redirect to success page if everything is fine
                  if (countOrder > 0) {
                        session.removeAttribute("id");
                        session.removeAttribute("cart");
                        resp.sendRedirect("dashboard.jsp");
                  } else {
                        pw.println("<h1> Order failed </h1>");
                  }

            } catch (Exception e) {
                  pw.println("<h1>" + e.getMessage() + "</h1>");
                  e.printStackTrace();
            } finally {
                  try {
                        if (stmtOrder != null) stmtOrder.close();
                        if (stmtOrderItems != null) stmtOrderItems.close();
                        if (stmtOrderHistory != null) stmtOrderHistory.close();
                  } catch (SQLException e) {
                        e.printStackTrace();
                  }
            }
      }

      @Override
      public void destroy() {
            try {
                  if (con != null && !con.isClosed()) {
                        con.close();
                        System.out.println("Database connection closed.");
                  }
            } catch (Exception e) {
                  e.printStackTrace();
            }
      }
}
