package jm.task.core.jdbc.util;
import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


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


        private static SessionFactory sessionFactory;
        public static SessionFactory getSessionFactory () {
            if (sessionFactory == null)
                try {
                    Configuration configuration = new Configuration();
                    Properties properties = new Properties();
                    properties.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                    properties.put(Environment.URL, "jdbc:mysql://localhost:3306/test");
                    properties.put(Environment.USER, "jpauser");
                    properties.put(Environment.PASS, "jpapwd");
                    properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
                    properties.put(Environment.SHOW_SQL, "true");
                    properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                    configuration.setProperties(properties);
                    configuration.addAnnotatedClass(User.class);
                    ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                            .applySettings(configuration.getProperties()).build();
                    sessionFactory = configuration.buildSessionFactory(serviceRegistry);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            return sessionFactory;
        }
}
