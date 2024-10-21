package DAOImpl.User;

import Model.Restaurant;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

@WebServlet("/fetchRestaurant")
public class FetchRestaurant extends HttpServlet {
      private Connection con;
      private ArrayList<Restaurant> list = new ArrayList<>();

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
            String query = "SELECT * FROM restaurant";
            PrintWriter pw = resp.getWriter();
            try {
                  resp.setContentType("text/html");
                  PreparedStatement stmt = con.prepareStatement(query);
                  ResultSet res = stmt.executeQuery();
                  list.clear();
                  while (res.next()) {
                        // Fetch the photo as a byte array
                        byte[] photo = res.getBytes("photo");
                        list.add(new Restaurant(
                                res.getInt("restaurant_id"),
                                res.getString("restaurant_name"),
                                res.getString("cusionType"),
                                res.getInt("deliveryTime"),
                                res.getInt("ratings"),
                                res.getString("is_active"),
                                photo // Add photo to the restaurant object
                        ));
                  }
                  HttpSession session = req.getSession();
                  session.setAttribute("list", list);

                  req.getRequestDispatcher("dashboard.jsp").forward(req, resp);
            } catch (Exception e) {
                  pw.println("<h1>Error:" + e.getMessage() + "</h1>");
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
