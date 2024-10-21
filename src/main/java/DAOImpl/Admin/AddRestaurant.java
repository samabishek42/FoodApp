
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

@WebServlet("/addRestaurant")
@MultipartConfig
public class AddRestaurant extends HttpServlet {
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
                  String query = "insert into restaurant(restaurant_name,cusionType,deliveryTime,ratings,is_active,photo) values (?,?,?,?,?,?)";
                  PreparedStatement stmt = con.prepareStatement(query);
                  String name=req.getParameter("restName");
                  String cusionType=req.getParameter("cuisine");
                  int deliveryTime= Integer.parseInt(req.getParameter("deliveryTime"));
                  int ratings=Integer.parseInt(req.getParameter("ratings"));
                  String isActive=req.getParameter("isActive");

                  Part imagePart = req.getPart("restaurantImage");
                  byte[] imageData = new byte[(int) imagePart.getSize()];
                  imagePart.getInputStream().read(imageData);

                  stmt.setString(1,name);
                  stmt.setString(2,cusionType);
                  stmt.setInt(3,deliveryTime);;
                  stmt.setInt(4,ratings);
                  stmt.setString(5,isActive);
                  stmt.setBytes(6,imageData);
                  int count=stmt.executeUpdate();
                  if(count!=0){
                        resp.sendRedirect("fetchRestaurantAdmin");
                  }
                  else {
                        pw.println("<h1> Failed </h1>");
                  }
            }
            catch (Exception e){
                  pw.println("<h1>"+e.getMessage()+"</h1>");
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
