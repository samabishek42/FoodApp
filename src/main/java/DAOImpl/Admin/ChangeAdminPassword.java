
package DAOImpl.Admin;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

@WebServlet("/changeAdminPassword")
public class ChangeAdminPassword extends HttpServlet {
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
      public static String hashPassword(String password) {
            // Generate a salt and hash the password

            String salt = BCrypt.gensalt();
            String hashedPassword = BCrypt.hashpw(password, salt);
            return hashedPassword;
      }
      protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            PrintWriter pw=resp.getWriter();
            try {
                  resp.setContentType("text/html");
                  String password = req.getParameter("password");
                  int id= (int) req.getSession().getAttribute("id");
                  String query="update admin set admin_password=? where admin_id=? ";
                  PreparedStatement stmt= con.prepareStatement(query);
                  String hash=hashPassword(password);
                  stmt.setString(1,hash);
                  stmt.setInt(2,id);
                  int count= stmt.executeUpdate();
                  if(count!=0) pw.println("<h1>Successfully Changed the password</h1>");
                  else  pw.println("<h1>Password not changed</h1>");
            } catch (Exception e) {
                  pw.println("<h1>Error occurred:"+e.getMessage()+"</h1>");
            }
      }
}
