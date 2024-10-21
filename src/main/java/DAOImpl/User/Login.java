package DAOImpl.User;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/login")
public class Login extends HttpServlet {
      private Connection con;
      @Override
      public void init(ServletConfig config) throws ServletException {
            try {
                  Class.forName("com.mysql.cj.jdbc.Driver");
                  con= DriverManager.getConnection("jdbc:mysql://localhost:3306/foodapp","root","abishek@1");
            } catch (Exception e) {
                  e.printStackTrace();
            }

      }
      public static boolean checkPassword(String plainPassword, String hashedPassword) {
            return BCrypt.checkpw(plainPassword, hashedPassword);
      }

      @Override
      protected void doGet (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            PrintWriter pw=resp.getWriter();
            try {
                  resp.setContentType("text/html");
                  String email=req.getParameter("email");
                  String pwd=req.getParameter("password");
                  String query="select * from user where email=?";
                  PreparedStatement stmt=con.prepareStatement(query);
                  stmt.setString(1,email);
                  ResultSet res=stmt.executeQuery();
                  if(res.next()){
                        String dbPWD=res.getString("password");
                        if(checkPassword(pwd,dbPWD)){
                              HttpSession session=req.getSession();
                              session.setAttribute("userId",res.getInt("id"));
                              resp.sendRedirect("fetchRestaurant");
                        }
                        else {
                              pw.println("<h1> Password Invalid");
                        }
                  }
                  else {
                        pw.println("<h1>A user does not exist </h1>");
                  }
            } catch (Exception e) {
                  pw.println("<h1> Error occurred:"+e.getMessage()+"</h1>");
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
