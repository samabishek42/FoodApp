package Model;

import java.sql.*;

public class MyConnection {
      private static Connection conn;
      public static Connection getConnection(){
            try {
                  Class.forName("com.mysql.cj.jdbc.Driver");
                  conn=  DriverManager.getConnection("jdbc:mysql://localhost:3306/foodapp","root","abishek@1");
            } catch (Exception e) {
                  e.printStackTrace();
            }
            return  conn;
      }

      public static void closeConnection(ResultSet res, Statement stmt,Connection con){
            try{
                  if(res!=null)res.close();
                  if (stmt!=null)stmt.close();
                  if(con!=null)con.close();
            }
            catch (SQLException e){
                  System.out.println(e.getMessage());
            }
      }



}
