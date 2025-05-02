package net.projectsync.junits.service;

import net.projectsync.junits.model.User;
import net.projectsync.junits.repository.UserRepository;
import net.projectsync.junits.util.UserHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    
    @InjectMocks
    private UserService userService;
    
    @Test
    public void testCreateUser() {
        User user = UserHelper.getUser();
        Mockito.when(userService.createUser(Mockito.any(User.class))).thenReturn(UserHelper.getUser());
        User newUser = userService.createUser(user);

        Assertions.assertNotNull(newUser);
        Assertions.assertEquals(user.getId(), newUser.getId());
        Assertions.assertTrue(user.getFirstName().equalsIgnoreCase(newUser.getFirstName()));
        Assertions.assertTrue(user.getLastName().equalsIgnoreCase(newUser.getLastName()));
        Assertions.assertTrue(user.getEmail().equalsIgnoreCase(newUser.getEmail()));
    }

    @Test
    public void testGetUserById() {
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(UserHelper.getUser()));
        User newUser = userService.getUserById(1L);

        Assertions.assertNotNull(newUser);
        Assertions.assertEquals(1L, newUser.getId());
        Assertions.assertEquals("Rahul", newUser.getFirstName());
        Assertions.assertEquals("Dravid", newUser.getLastName());
        Assertions.assertEquals("rahul@gmail.com", newUser.getEmail());
    }

    @Test
    public void testGetAllUsers() {
        Mockito.when(userRepository.findAll()).thenReturn(UserHelper.getUsers());
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

        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(UserHelper.getUser()));
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(UserHelper.getUpdatedUser());
        User updatedUser = userService.updateUser(UserHelper.getUser());

        Assertions.assertNotNull(updatedUser);
        Assertions.assertEquals(1L, updatedUser.getId());
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
}
