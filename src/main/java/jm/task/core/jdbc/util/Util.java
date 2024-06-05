package jm.task.core.jdbc.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Util {

    private static final String PASSWORD_KEY = "jpapwd";
    private static final String USERNAME_KEY = "jpauser";
    private static final String URL_KEY = "jdbc:mysql://localhost:3306/test1";

    private Util() {
    }

    public static Connection open() {
        try {
            return DriverManager.getConnection(
                    URL_KEY,
                    USERNAME_KEY,
                    PASSWORD_KEY);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
