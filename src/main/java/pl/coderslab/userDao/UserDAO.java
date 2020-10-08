package pl.coderslab.userDao;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDAO {

    private static final String CREATE = "INSERT INTO users (email, username, password) values (?, ?, ? );";

    private static final String READ_ALL = "SELECT * FROM users";

    private static  final String COUNT_USERS = "SELECT COUNT(*) FROM users";

    private static final String READ_BY_ID = "SELECT * FROM users where id= ?";

    private static final String UPDATE = "UPDATE users SET email = ?, username= ?, password= ? where id=?;";

    private static final String DELETE = "DELETE FROM users WHERE id = ?;";

    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }



    public User creatUser(User user) {

        try (PreparedStatement statement = DBUtil.connect().prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getUsername());
            statement.setString(3, hashPassword(user.getPassword()));

            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();

            while(resultSet.next()){
                user.setId(resultSet.getInt(1));
            }return user;

        } catch (SQLException e) {
            e.printStackTrace();

        }return null;
    }

    public User [] readAllUsers(){

        try (PreparedStatement statement = DBUtil.connect().prepareStatement(READ_ALL);
            PreparedStatement statement2 = DBUtil.connect().prepareStatement(COUNT_USERS)){

            statement2.executeQuery();

            ResultSet resultSet=statement2.executeQuery();

            int usersCount=0;

            if(resultSet.next()) usersCount = resultSet.getInt(1);

            statement.executeQuery();

            ResultSet resultSet2 = statement.executeQuery();

            User [] users = new  User[usersCount];

            for(int i=0; i<users.length; i++){

                resultSet2.next();

                User user = new User();
                user.setId(resultSet2.getInt(1));
                user.setEmail(resultSet2.getString(2));
                user.setUsername(resultSet2.getString(3));
                user.setPassword(resultSet2.getString(4));

                users [i] = user;
            } return users;

        }catch (SQLException e){
            e.printStackTrace();
        }
        System.out.println("Upssss  something went terribly wrong :(");
        return null;
    }

    public User readUserById(int userId) {

        try(PreparedStatement statement = DBUtil.connect().prepareStatement(READ_BY_ID)){
            statement.setInt(1,userId);

            statement.executeQuery();

            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()){

            User user = new User();
            user.setId(resultSet.getInt(1));
            user.setEmail(resultSet.getString(2));
            user.setUsername(resultSet.getString(3));
            user.setPassword(resultSet.getString(4));

            return user;}

        }catch (SQLException e){
            e.printStackTrace();
        }return null;
    }

    public void update(User user){


        try(PreparedStatement statement = DBUtil.connect().prepareStatement(UPDATE)){

            statement.setString(1, user.getEmail());
            statement.setString(2, user.getUsername());
            statement.setString(3, hashPassword(user.getPassword()));
            statement.setInt(4,user.getId());

            statement.executeUpdate();


        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void delete(int userId){

        try(PreparedStatement statement=DBUtil.connect().prepareStatement(DELETE)){
            statement.setInt(1,userId);
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }

    }
}





