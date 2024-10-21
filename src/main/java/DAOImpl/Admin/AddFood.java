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

@WebServlet("/addFood")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1 MB
        maxFileSize = 5 * 1024 * 1024,    // 5 MB
        maxRequestSize = 10 * 1024 * 1024  // 10 MB
)
public class AddFood extends HttpServlet {
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
            PrintWriter pw = resp.getWriter();
            resp.setContentType("text/html");

            try {
                  String name = req.getParameter("dish");
                  String description = req.getParameter("dishDescription");
                  String priceParam = req.getParameter("price");

                  // Validate inputs
                  if (name == null || description == null || priceParam == null || priceParam.isEmpty()) {
                        pw.println("<h1>Invalid input. Please fill all fields.</h1>");
                        return;
                  }

               // Ensure price is parsed after validation

                  Part imagePart = req.getPart("foodImage");

                  if (imagePart == null || imagePart.getSize() == 0) {
                        pw.println("<h1>Please upload an image.</h1>");
                        return;
                  }

                  int price = Integer.parseInt(priceParam);
                  byte[] imageData = new byte[(int) imagePart.getSize()];
                  imagePart.getInputStream().read(imageData);

                  String query = "INSERT INTO dish(dish_name, dish_description, price, dish_photo) VALUES (?, ?, ?, ?)";
                  PreparedStatement stmt = con.prepareStatement(query);
                  stmt.setString(1, name);
                  stmt.setString(2, description);
                  stmt.setInt(3, price);
                  stmt.setBytes(4, imageData);

                  int count = stmt.executeUpdate();
                  if (count != 0) {
                        resp.sendRedirect("admin-dashboard.jsp");
                  } else {
                        pw.println("<h1>Failed to add food.</h1>");
                  }
            } catch (Exception e) {
                  e.printStackTrace();
                  pw.println("<h1>Error: " + e + "</h1>");
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
