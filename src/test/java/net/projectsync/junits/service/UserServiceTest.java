package net.projectsync.junits.service;

import org.hibernate.mapping.Any;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import net.projectsync.junits.model.User;
import net.projectsync.junits.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    
    @InjectMocks
    private UserService userService;
    
    @Test
    public void testCreateUser() {
        User user = getUser();
        Mockito.when(userRepository.save(user)).thenReturn(user);
        User newUser = userService.createUser(user);

        Assertions.assertNotNull(newUser);
        Assertions.assertEquals(user.getId(), newUser.getId());
        Assertions.assertTrue(user.getFirstName().equalsIgnoreCase(newUser.getFirstName()));
        Assertions.assertTrue(user.getLastName().equalsIgnoreCase(newUser.getLastName()));
        Assertions.assertTrue(user.getEmail().equalsIgnoreCase(newUser.getEmail()));
    }

    @Test
    public void testGetUserById() {
        Long id = 1L;
        Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(getUser()));
        User newUser = userService.getUserById(id);

        Assertions.assertNotNull(newUser);
        Assertions.assertEquals(1L, newUser.getId());
        Assertions.assertEquals("Rahul", newUser.getFirstName());
        Assertions.assertEquals("Dravid", newUser.getLastName());
        Assertions.assertEquals("rahul@gmail.com", newUser.getEmail());
    }

    @Test
    public void testGetAllUsers() {
        List<User> users = getUsers();
        Mockito.when(userRepository.findAll()).thenReturn(getUsers());
        List<User> newUsers = userService.getAllUsers();

        Assertions.assertNotNull(newUsers);
        Assertions.assertEquals(2, newUsers.size());

        Assertions.assertEquals(1, newUsers.get(0).getId());
        Assertions.assertEquals("Rahul", newUsers.get(0).getFirstName());
        Assertions.assertEquals("Dravid", newUsers.get(0).getLastName());
        Assertions.assertEquals("rahul@gmail.com", newUsers.get(0).getEmail());

        Assertions.assertEquals(2, newUsers.get(1).getId());
        Assertions.assertEquals("Sachin", newUsers.get(1).getFirstName());
        Assertions.assertEquals("Tendulkar", newUsers.get(1).getLastName());
        Assertions.assertEquals("sachin.rt@gmail.com", newUsers.get(1).getEmail());
    }

    @Test
    public void testUpdateUser() {

        User user = getUser();
        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(getUser()));
        Mockito.when(userRepository.save(any(User.class))).thenReturn(getUpdatedUser());
        User updatedUser = userService.updateUser(user);

        Assertions.assertNotNull(updatedUser);
        Assertions.assertEquals(user.getId(), updatedUser.getId());
        Assertions.assertTrue("Rahul1".equalsIgnoreCase(updatedUser.getFirstName()));
        Assertions.assertTrue("Dravid1".equalsIgnoreCase(updatedUser.getLastName()));
        Assertions.assertTrue("rahul.dravid@gmail.com".equalsIgnoreCase(updatedUser.getEmail()));
    }

    @Test
    public void testDeleteById() {

        Mockito.doNothing().when(userRepository).deleteById(anyLong());
        userService.deleteUser(5L);

        // Mockito.verify(userRepository).deleteById(5L);
        Mockito.verify(userRepository, Mockito.times(1)).deleteById(5L);
    }

    private User getUser() {
        User user = new User();
        user.setId(1L);
        user.setFirstName("Rahul");
        user.setLastName("Dravid");
        user.setEmail("rahul@gmail.com");

        return user;
    }

    private List<User> getUsers() {
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

    private User getUpdatedUser() {
        User user = new User();
        user.setId(1L);
        user.setFirstName("Rahul1");
        user.setLastName("Dravid1");
        user.setEmail("rahul.dravid@gmail.com");

        return user;
    }
}
