package DAOImpl.Admin;

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
import java.sql.ResultSet;

@WebServlet("/confirmAdmin")
public class ConfirmAdmin extends HttpServlet {
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


      @Override
      protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            PrintWriter pw=resp.getWriter();
            try {
                  resp.setContentType("text/html");
                  String email = req.getParameter("email");
                  String query="select * from admin where admin_email=?";
                  PreparedStatement stmt=con.prepareStatement(query);
                  stmt.setString(1,email);
                  ResultSet res=stmt.executeQuery();
                  if(res.next()){
                        req.getSession().setAttribute("id",res.getInt("admin_id"));
                        resp.sendRedirect("change-admin-password.html");
                  }
                  else {
                        resp.sendRedirect("confirmation-admin.html");
                  }
            } catch (Exception e) {
                  pw.println("<h1>Error occurred:"+e.getMessage()+"</h1>");
            }
      }
}
