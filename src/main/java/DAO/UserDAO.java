package DAO;

import Model.User;

import java.util.ArrayList;

public interface UserDAO {

      void insertUser(User u);

      ArrayList<User> getAllUsers();

      User getUserById();

      void deleteUser(int id);

}
