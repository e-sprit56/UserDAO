package pl.coderslab.userDao;

import java.sql.SQLException;
import java.util.Arrays;

public class Main {

    private static void createNewUser(String username, String email, String password){

        User user = new User(email, username, password);
        UserDAO userDAO = new UserDAO();
        userDAO.creatUser(user);
        System.out.println("User " + username + " was successfully created ");
        System.out.println(user);
    }

    private static void readUserById(int userid) {
        UserDAO userDAO = new UserDAO();
        User user =userDAO.readUserById(userid);
        System.out.println(user);
    }

    private static void readUsersList() {
        UserDAO userDAO = new UserDAO();
        User [] users = userDAO.readAllUsers();
        System.out.println(Arrays.toString(users));
    }

    private static void updateUser(int userId, String newEmail, String newName, String newPassword) {
        UserDAO userDAO = new UserDAO();
        User userToUpadate = userDAO.readUserById(userId);
        userToUpadate.setEmail(newEmail);
        userToUpadate.setUsername(newName);
        userToUpadate.setPassword(newPassword);
        userDAO.update(userToUpadate);
        System.out.println(Arrays.toString(userDAO.readAllUsers()));
    }

    private static void deleteUser(int userId) {
        UserDAO userDAO = new UserDAO();
        userDAO.delete(userId);
    }

    public static void main(String[] args) {

    









    }




}
