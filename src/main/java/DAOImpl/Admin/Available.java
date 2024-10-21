
package DAOImpl.Admin;

import Model.Dish;
import Model.Restaurant;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;

@WebServlet("/available")
public class Available extends HttpServlet {
      private Connection con;

      @Override
      public void init(ServletConfig config) throws ServletException {
            try {
                  Class.forName("com.mysql.cj.jdbc.Driver");
                  con=  DriverManager.getConnection("jdbc:mysql://localhost:3306/foodapp","root","abishek@1");
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

            PrintWriter pw=resp.getWriter();
            try {
                  resp.setContentType("text/html");
                  PreparedStatement stmt = con.prepareStatement(query);
                  stmt.setInt(1, Integer.parseInt(req.getParameter("id")));
                  ResultSet res = stmt.executeQuery();

                  HttpSession session= req.getSession();
                  ArrayList<Dish> totalLists= (ArrayList<Dish>) session.getAttribute("dishList");
                  ArrayList<Dish> availableList=new ArrayList<>();
                  availableList.clear();
                  while (res.next()) {
                        // Fetch the photo as a byte array
                        byte[] photo = res.getBytes("dish_photo");
                        availableList.add(new Dish(
                                res.getInt("dish_id"),
                                res.getString("dish_name"),
                                res.getString("dish_description"),
                                res.getInt("price"),
                                photo // Add photo to the restaurant object
                        ));
                  }
                  ArrayList<Dish> unAvailableList=new ArrayList<>(totalLists);
                  unAvailableList.removeAll(availableList);
                  session.setAttribute("available",availableList);
                  session.setAttribute("unavailable",unAvailableList);

                  req.getRequestDispatcher("edit.jsp").forward(req, resp);

            }
            catch (Exception e){
                  pw.println("<h1>"+e+"</h1>");
            }

      }

      @Override
      protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            doGet(req, resp);
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
