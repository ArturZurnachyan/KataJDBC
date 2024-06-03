package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    Connection connection = Util.open();

    private static final String CREATE_USER_SQL = """
                    CREATE TABLE IF NOT EXISTS User  (
            id SERIAL primary key auto_increment,
            name VARCHAR(100) NOT NULL,
            last_name VARCHAR(100) NOT NULL,
            age SMALLINT NOT NULL);
                    """;

    private static final String SAVE_SQL = """
                            INSERT INTO User(name,last_name,age)
            values (?,?,?);
                            """;

    private static final String DELETE_TABLE_SQL = """
            DROP TABLE IF EXISTS User
            """;

    private static final String DELETE_USERS_SQL = """
            DELETE FROM user
            where id;
            """;

    private static final String DELETE_SQL = """
            DELETE FROM User
            WHERE id = ?
            """;

    private static final String FIND_All_SQL = """
            SELECT id,
            name,
            last_name,
            age
            FROM User
            """;

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (PreparedStatement statement = connection.prepareStatement(CREATE_USER_SQL)) {
            connection.getTransactionIsolation();
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_TABLE_SQL)) {
            connection.getTransactionIsolation();
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            User user = new User();
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setObject(3, age);
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                user.setId(generatedKeys.getLong("id"));
            }
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_All_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<User> tickets = new ArrayList<>();
            while (resultSet.next()) {
                tickets.add(buildTicket(resultSet));
            }
            return tickets;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void cleanUsersTable() {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USERS_SQL)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private User buildTicket(ResultSet resultSet) throws SQLException {
        return new User(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("last_name"),
                resultSet.getByte("age"));
    }
}
