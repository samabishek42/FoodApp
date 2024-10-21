package DAOImpl.User;


import Model.Dish;
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

@WebServlet("/fetchDishesUser")
public class FetchDishesUser extends HttpServlet {
      private Connection con;
      private ArrayList<Dish> list = new ArrayList<Dish>();

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
            String query = "SELECT *" +
                    " from restaurant r " +
                    "  join map m on r.restaurant_id=m.restaurant_id" +
                    "  join dish d on m.dish_id=d.dish_id where r.restaurant_id=? ";
            PrintWriter pw = resp.getWriter();
            try {
                  resp.setContentType("text/html");
                  PreparedStatement stmt = con.prepareStatement(query);
                  stmt.setInt(1, Integer.parseInt(req.getParameter("id")));
                  ResultSet res = stmt.executeQuery();
                  list.clear();
                  while (res.next()) {
                        // Fetch the photo as a byte array
                        byte[] photo = res.getBytes("dish_photo");
                        list.add(new Dish(
                                res.getInt("d.dish_id"),
                                res.getString("d.dish_name"),
                                res.getString("d.dish_description"),
                                res.getInt("d.price"),
                                photo // Add photo to the restaurant object
                        ));
                  }
                  HttpSession session = req.getSession();
                  session.setAttribute("restaurant_name",req.getParameter("restaurant_name"));
                  session.setAttribute("id",Integer.parseInt(req.getParameter("id")));
                  session.setAttribute("dishListUser", list);

                  req.getRequestDispatcher("Restaurant.jsp").forward(req, resp);
            } catch (Exception e) {
                  e.printStackTrace();
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
