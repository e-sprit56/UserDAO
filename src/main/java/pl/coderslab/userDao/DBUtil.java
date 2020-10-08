package pl.coderslab.userDao;

import java.sql.*;

public class DBUtil {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/workshop2?serverTimezone=UTC&useSSL=false";

   // private static final String DB_URL_CINEMAS = "jdbc:mysql://localhost:3306/cinemas_ex?serverTimezone=UTC&useSSL=false";


    private static final String DB_USER = "root";

    private static final String DB_PASS = "coderslab";

    public static Connection connect() throws SQLException {

        return DriverManager.getConnection(DB_URL,DB_USER,DB_PASS);
    }

//    public static Connection connectCinemas() throws SQLException {
//
//        return DriverManager.getConnection(DB_URL_CINEMAS,DB_USER,DB_PASS);
//    }

    public static void insert(Connection conn, String query, String... params) {
        try ( PreparedStatement statement = conn.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                statement.setString(i + 1, params[i]);
            }
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void printData(Connection conn, String query, String... columnNames) throws SQLException {

        try (PreparedStatement statement = conn.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery();) {
            while (resultSet.next()) {
                for (String param : columnNames) {
                    System.out.println(resultSet.getString(param));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static final String DELETE_QUERY = "DELETE FROM tableName where id = ?";

    public static void remove(Connection conn, String tableName, int id) {
        try (PreparedStatement statement =
                     conn.prepareStatement(DELETE_QUERY.replace("tableName", tableName));) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }






}
