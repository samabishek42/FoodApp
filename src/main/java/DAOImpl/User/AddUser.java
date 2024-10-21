package DAOImpl.User;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import org.mindrot.jbcrypt.BCrypt;

@WebServlet("/addUser")
public class AddUser extends HttpServlet {
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

      public static String hashPassword(String password) {
            // Generate a salt and hash the password

            String salt = BCrypt.gensalt();
            String hashedPassword = BCrypt.hashpw(password, salt);
            return hashedPassword;
      }
      @Override
      protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            PrintWriter pw=resp.getWriter();
            try {
                  resp.setContentType("text/html");
                  String query = "insert into user(name,email,password,mobile) values (?,?,?,?)";
                  PreparedStatement stmt = con.prepareStatement(query);
                  String name=req.getParameter("name");
                  String email=req.getParameter("email");
                  String password=req.getParameter("password");
                  String hash=hashPassword(password);
                  String mobile=req.getParameter("mobile");
                  stmt.setString(1,name);
                  stmt.setString(2,email);
                  stmt.setString(3,hash);;
                  stmt.setString(4,mobile);
                  int count=stmt.executeUpdate();
                  if(count!=0){
                        resp.sendRedirect("fetchRestaurant");
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
