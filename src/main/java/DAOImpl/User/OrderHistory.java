package DAOImpl.User;

import Model.OrderItems;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;


@WebServlet("/orderHistory")
public class OrderHistory extends HttpServlet {
      private Connection con;

      @Override
      public void init(ServletConfig config) throws ServletException {
            try {
                  Class.forName("com.mysql.cj.jdbc.Driver");
                  con = DriverManager.getConnection("jdbc:mysql://localhost:3306/foodapp", "root", "abishek@1");
            } catch (Exception e) {
                  e.printStackTrace();
            }
      }

      @Override
      protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            PrintWriter pw = resp.getWriter();
            try {
                  resp.setContentType("text/html");
                  String query = "SELECT o_id, date,restaurant_name FROM orderhistory WHERE user_id=?";
                  PreparedStatement stmt = con.prepareStatement(query);
                  stmt.setInt(1, (Integer) req.getSession().getAttribute("userId"));
                  ResultSet res = stmt.executeQuery();

                  HashMap<Integer, ArrayList<OrderItems>> historyList = new HashMap<>();
                  HashMap<Integer, Integer> orderTotals = new HashMap<>();

                  while (res.next()) {
                        int o_id = res.getInt("o_id");
                        Date dat = res.getDate("date");
                        String restaurantName=res.getString("restaurant_name");
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        String date = dateFormat.format(dat);

                        query = "SELECT * FROM orderitems WHERE o_id=?";
                        PreparedStatement stmtItems = con.prepareStatement(query);
                        stmtItems.setInt(1, o_id);
                        ResultSet result = stmtItems.executeQuery();


                        ArrayList<OrderItems> list = new ArrayList<>();
                        int totalAmount = 0;

                        while (result.next()) {
                              String dish_name = result.getString("dish_name");
                              int quantity = result.getInt("quantity");
                              int subtotal = result.getInt("subtotal");

                              totalAmount += subtotal;

                              OrderItems o = new OrderItems(dish_name, quantity, date, subtotal,restaurantName);
                              list.add(o);
                        }
                        historyList.put(o_id, list);
                        orderTotals.put(o_id, totalAmount);
                  }

                  req.getSession().setAttribute("historyList", historyList);
                  req.getSession().setAttribute("orderTotals", orderTotals);
                  req.getRequestDispatcher("order-history.jsp").forward(req, resp);

            } catch (Exception e) {
                  pw.println("<h1> Error occurred:" + e + "</h1>");
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