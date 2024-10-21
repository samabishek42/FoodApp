
package DAOImpl.Admin;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

@WebServlet("/addRestaurantDish")
public class AddRestaurantDish extends HttpServlet {
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
      protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            PrintWriter pw=resp.getWriter();
            try {
                  resp.setContentType("text/html");
                  String query="INSERT INTO map( restaurant_id,dish_id ) values(?,?)";
                  PreparedStatement stmt = con.prepareStatement(query);
                  stmt.setInt(1, Integer.parseInt(req.getParameter("id")));
                  stmt.setInt(2,Integer.parseInt(req.getParameter("itemId")));
                  int count=stmt.executeUpdate();
                  if(count!=0){
                        req.getRequestDispatcher("fetchDishes").forward(req,resp);
                  }
                  else {
                        pw.println("<h1> Failed </h1>");
                  }
            }
            catch (Exception e){
                  e.printStackTrace();
                  pw.println("<h1>"+e+"</h1>");
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
