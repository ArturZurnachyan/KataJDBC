package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    SessionFactory sessionFactory = Util.getSessionFactory();

    public UserDaoHibernateImpl() {

    }

    private static final String CREATE_Table_SQL = """
            CREATE TABLE IF NOT EXISTS User  (
            id SERIAL primary key auto_increment,
            name VARCHAR(100) NOT NULL,
            last_name VARCHAR(100) NOT NULL,
            age SMALLINT NOT NULL);
            """;

    private static final String DropTable_SQL = "DROP TABLE IF EXISTS User";
    private static final String DElETE_ALL_SQL = "DELETE FROM User";
    private static final String GET_USERS_SQL = "FROM User";


    @Override
    public void createUsersTable() {
        Transaction tx = null;
        try(Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();

            session.createSQLQuery(CREATE_Table_SQL).addEntity(User.class).executeUpdate();
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            session.createSQLQuery(DropTable_SQL).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            session.remove(session.get(User.class, id));
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        Transaction transaction = null;
        List<User> users = null;
        try(Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            users = session.createQuery(GET_USERS_SQL).getResultList();
            transaction.commit();
            session.close();
            return users;
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction.isActive()) {
            }
            transaction.rollback();
            return users;
        }
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            session.createSQLQuery(DElETE_ALL_SQL).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
    }
}
