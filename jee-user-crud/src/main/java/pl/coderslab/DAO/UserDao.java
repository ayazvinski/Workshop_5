package pl.coderslab.DAO;

import org.mindrot.jbcrypt.BCrypt;
import pl.coderslab.utils.DbUtil;

import java.sql.*;
import java.util.Arrays;

public class UserDao {

    private static final String CREATE_USER_QUERY = "INSERT INTO users(id, username,email, password) VALUE (DEFAULT,?,?,?)";
    private static final String READ_USER_QUERY = "SELECT * FROM users Where id = ?";
    private static final String UPDATE_USER_QUERY = "UPDATE users SET username = ?, email = ?, password = ? WHERE id = ?";
    private static final String DELETE_USER_QUERY = "DELETE from users where id = ?";
    private static final String FINDALL_USERS_QUERY = "SELECT * FROM users";


    public User create(User user) {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement statement =
                    conn.prepareStatement(CREATE_USER_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getEmail());
            statement.setString(3, hashPassword(user.getPassword()));
            statement.executeUpdate();
            System.out.println("Użytkownik został dodany!");

            //Pobieramy wstawiony do bazy identyfikator, a następnie ustawiamy id obiektu user.
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getInt(1));
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public User read(int userId) {
        try (PreparedStatement statement = DbUtil.getConnection().prepareStatement(READ_USER_QUERY)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUserName(resultSet.getString("username"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update(User user) {
        try (PreparedStatement statement = DbUtil.getConnection().prepareStatement(UPDATE_USER_QUERY)) {
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getEmail());
            statement.setString(3, hashPassword(user.getPassword()));
            statement.setInt(4, user.getId());
            statement.executeUpdate();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public void delete(int userId) {
        try (PreparedStatement statement = DbUtil.getConnection().prepareStatement(DELETE_USER_QUERY)) {
            statement.setInt(1, userId);
            statement.executeUpdate();
        } catch (SQLException se) {
            se.printStackTrace();
        }

    }

    public User[] findAll() {
        try (PreparedStatement statement = DbUtil.getConnection().prepareStatement(FINDALL_USERS_QUERY)) {
            User[] usersArray = new User[0];
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUserName(resultSet.getString("username"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                usersArray = addToArray(user, usersArray);
            }
            return usersArray;

        } catch (SQLException se) {
            se.printStackTrace();
            return null;

        }
    }


    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    private User[] addToArray(User u, User[] users) {
        User[] tmpUsers = Arrays.copyOf(users, users.length + 1); // Tworzymy kopię tablicy powiększoną o 1.
        tmpUsers[users.length] = u; // Dodajemy obiekt na ostatniej pozycji.
        return tmpUsers; // Zwracamy nową tablicę.
    }


}
