package net.projectsync.junits.util;

import net.projectsync.junits.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserHelper {

    private UserHelper() {}

    public static User getUser() {
        User user = new User();
        user.setId(1L);
        user.setFirstName("Rahul");
        user.setLastName("Dravid");
        user.setEmail("rahul@gmail.com");

        return user;
    }

    public static List<User> getUsers() {
        List<User> users = new ArrayList<>();

        User user1 = new User();
        user1.setId(1L);
        user1.setFirstName("Rahul");
        user1.setLastName("Dravid");
        user1.setEmail("rahul@gmail.com");

        User user2 = new User();
        user2.setId(2L);
        user2.setFirstName("Sachin");
        user2.setLastName("Tendulkar");
        user2.setEmail("sachin.rt@gmail.com");

        users.add(user1);
        users.add(user2);

        return users;
    }

    public static User getUpdatedUser() {
        User user = new User();
        user.setId(1L);
        user.setFirstName("Rahul1");
        user.setLastName("Dravid1");
        user.setEmail("rahul.dravid@gmail.com");

        return user;
    }
}
