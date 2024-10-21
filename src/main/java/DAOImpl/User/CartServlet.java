package DAOImpl.User;

import Model.Cart;
import Model.CartItem;
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
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {

      private Connection con;

      @Override
      public void init(ServletConfig config) throws ServletException {
            try {
                  Class.forName("com.mysql.cj.jdbc.Driver");
                  con = DriverManager.getConnection("jdbc:mysql://localhost:3306/foodapp", "root", "abishek@1");
            } catch (Exception e) {
                  e.printStackTrace();
            }
      }

      @Override
      protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            HttpSession session = req.getSession();
            Cart cart = (Cart) session.getAttribute("cart");

            if (cart == null) {
                  cart = new Cart();
                  session.setAttribute("cart", cart);
            }

            String action = req.getParameter("act");
            if ("add".equals(action)) {
                  addItemToCart(req, cart, resp);
            } else if ("update".equals(action)) {
                  updateItemToCart(req, cart);
            } else if ("remove".equals(action)) {
                  removeItemFromCart(req, cart);
            }
            session.setAttribute("cart", cart);
            if("add".equals(action))
                  req.getRequestDispatcher("Restaurant.jsp").forward(req,resp);
            else
                  req.getRequestDispatcher("cart.jsp").forward(req,resp);
      }

      private void removeItemFromCart(HttpServletRequest req, Cart cart) {
            int itemId = Integer.parseInt(req.getParameter("itemId"));
            cart.removeItem(itemId);  // Remove the item from the cart using its ID
      }

      private void updateItemToCart(HttpServletRequest req, Cart cart) {
            int itemId = Integer.parseInt(req.getParameter("itemId"));
            int quantity = Integer.parseInt(req.getParameter("quantity"));


            if (quantity <= 0) {
                  cart.removeItem(itemId);
            } else {
                  cart.update(itemId, quantity);
            }
      }

      public void addItemToCart(HttpServletRequest req, Cart cart, HttpServletResponse resp) throws IOException {
            PrintWriter pw = resp.getWriter();
            try {
                  resp.setContentType("text/html");
                  int itemId = Integer.parseInt(req.getParameter("itemId"));
                  int quantity = Integer.parseInt(req.getParameter("quantity"));
                  Dish dishItem = getMenu(itemId, resp);
                  HttpSession session = req.getSession();
                  int restaurant_id = (int) session.getAttribute("id");

                  if (dishItem != null) {
                        CartItem item = new CartItem(dishItem.getId(), restaurant_id, dishItem.getDish_name(),
                                quantity, dishItem.getPrice());
                        cart.addItem(item);
                  }
            }
            catch (Exception e){
                  pw.println("<h1>Error:" + e + "</h1>");
            }
      }

      public Dish getMenu(int id, HttpServletResponse resp) throws IOException {
            PrintWriter pw = resp.getWriter();
            try {
                  resp.setContentType("text/html");
                  String query = "select * from dish where dish_id=?";
                  PreparedStatement stmt = con.prepareStatement(query);
                  stmt.setInt(1, id);
                  ResultSet res = stmt.executeQuery();

                  while (res.next()) {
                        return new Dish(
                                res.getInt("dish_id"),
                                res.getString("dish_name"),
                                res.getString("dish_description"),
                                res.getInt("price"));
                  }

            } catch (Exception e) {
                  e.printStackTrace();
                  pw.println("<h1>Error:" + e.getMessage() + "</h1>");
            }

            return null;
      }
}
