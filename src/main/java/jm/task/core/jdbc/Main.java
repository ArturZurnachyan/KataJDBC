package jm.task.core.jdbc;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;


import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Андрей","Иванов", (byte) 25);
        userService.saveUser("Дмитрий","Лиханов", (byte) 38);
        userService.saveUser("Иван","Шурпатов", (byte) 21);
        userService.saveUser("Вадим","Козаков", (byte) 28);
        List<User> users = userService.getAllUsers();
        System.out.println(users);
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
        }




