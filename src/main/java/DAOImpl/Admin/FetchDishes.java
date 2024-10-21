package DAOImpl.Admin;

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
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

@WebServlet("/fetchDishes")
public class FetchDishes extends HttpServlet {
      private Connection con;
      private ArrayList<Dish> list=new ArrayList<>();
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
      protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            String query="select * from dish ";
            PrintWriter pw=resp.getWriter();
            try {
                  resp.setContentType("text/html");
                  Statement stmt = con.createStatement();
                  ResultSet res=stmt.executeQuery(query);
                  list. clear();
                  while (res.next()){

                        list.add(new Dish(res.getInt("dish_id"),res.getString("dish_name"),
                                res.getString("dish_description"),res.getInt("price")));
                  }
                  HttpSession session= req.getSession();
                  session.setAttribute("dishList",list);

                  req.getRequestDispatcher("available").forward(req, resp);


            }
            catch (Exception e){
                  e.printStackTrace();
                  pw.println("<h1>Error:"+e+"</h1>");
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
