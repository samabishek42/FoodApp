package DAOImpl.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

//@WebServlet("logOut")
public class LogOut extends HttpServlet {

      @Override
      protected void doGet(HttpServletRequest request, HttpServletResponse response)
              throws ServletException, IOException {

            // Invalidate the current session if it exists
            HttpSession session = request.getSession(false); // Don't create a new session if it doesn't exist
            if (session != null) {
                  session.invalidate(); // This will clear the session data
            }

            // Redirect the user back to the login page or wherever you want
            response.sendRedirect("login.html");
      }
}
