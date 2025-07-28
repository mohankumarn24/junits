package net.projectsync.junits.service;

import net.projectsync.junits.model.User;
import net.projectsync.junits.repository.UserRepository;
import net.projectsync.junits.util.UserHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mockStatic;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    
    @InjectMocks
    private UserService userService;
    
    @Test
    public void testCreateUser() {

        User user = UserHelper.getUser();
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(UserHelper.getUser());
        User newUser = userService.createUser(user);

        Assertions.assertNotNull(newUser);
        Assertions.assertEquals(user.getId(), newUser.getId());
        Assertions.assertTrue(user.getFirstName().equalsIgnoreCase(newUser.getFirstName()));
        Assertions.assertTrue(user.getLastName().equalsIgnoreCase(newUser.getLastName()));
        Assertions.assertTrue(user.getEmail().equalsIgnoreCase(newUser.getEmail()));

        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any(User.class));
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

        Mockito.verify(userRepository, Mockito.times(1)).findById(Mockito.anyLong());
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

        Mockito.verify(userRepository, Mockito.times(1)).findAll();
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

        Mockito.verify(userRepository, Mockito.times(1)).findById(Mockito.anyLong());
        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any(User.class));
    }

    @Test
    public void testDeleteById() {

        Mockito.doNothing().when(userRepository).deleteById(anyLong());
        userService.deleteUser(5L);

        // Mockito.verify(userRepository).deleteById(5L);
        Mockito.verify(userRepository, Mockito.times(1)).deleteById(5L);
    }

    // throw unchecked exception for non-void method
    @Test
    public void testGetUserByIdThrowUncheckedException() {

        Mockito.when(userRepository.findById(Mockito.anyLong())).thenThrow(new ArithmeticException("Division by zero"));
        // Mockito.when(userRepository.findById(Mockito.anyLong())).thenThrow(new IllegalArgumentException("Invalid Argument"));
        // Mockito.when(userRepository.findById(Mockito.anyLong())).thenThrow(new IllegalStateException("Processing failed"));
        // Mockito.when(userRepository.findById(Mockito.anyLong())).thenThrow(new RuntimeException("Non-void method failed"));
        RuntimeException runtimeException = Assertions.assertThrows(RuntimeException.class, () -> {
            userService.getUserById(1L);
        });
        Assertions.assertEquals("Division by zero", runtimeException.getMessage());

        Mockito.verify(userRepository, Mockito.times(1)).findById(Mockito.anyLong());
    }

    // throw checked exception for non-void method
    @Test
    public void testGetUserByIdThrowCheckedException() {

        // Mockito.when(userRepository.findById(Mockito.anyLong())).thenThrow(new IOException("File not found")); // Getting error "Checked exception is invalid for this method!"
        Mockito.doAnswer(invocation -> {
            throw new IOException("File not found");
        }).when(userRepository).findById(Mockito.anyLong());
        Exception exception = Assertions.assertThrows(IOException.class, () -> {
            userService.getUserById(1L);
        });
        Assertions.assertEquals("File not found", exception.getMessage());

        Mockito.verify(userRepository, Mockito.times(1)).findById(Mockito.anyLong());
    }


    // throw unchecked exception for void method
    @Test
    public void testDeleteByIdThrowUncheckedException() {

        Mockito.doThrow(new ArithmeticException("Division by zero")).when(userRepository).deleteById(Mockito.anyLong());
        // Mockito.doThrow(new IllegalArgumentException("Invalid Argument")).when(userRepository).deleteById(Mockito.anyLong());
        // Mockito.doThrow(new IllegalStateException("Processing failed")).when(userRepository).deleteById(Mockito.anyLong());
        // Mockito.doThrow(new RuntimeException("Void method failed")).when(userRepository).deleteById(Mockito.anyLong());
        RuntimeException runtimeException = Assertions.assertThrows(RuntimeException.class, () -> {
            userService.deleteUser(1L);
        });
        Assertions.assertEquals("Division by zero", runtimeException.getMessage());

        Mockito.verify(userRepository, Mockito.times(1)).deleteById(Mockito.anyLong());
    }

    // throw checked exception for void method
    @Test
    public void testDeleteByIdThrowCheckedException() {

        // Mockito.doThrow(new IOException("File not found")).when(userRepository).deleteById(Mockito.anyLong()); // Getting error "Checked exception is invalid for this method!"
        Mockito.doAnswer(invocation -> {
            throw new IOException("File not found");
        }).when(userRepository).deleteById(Mockito.anyLong());
        Exception exception = Assertions.assertThrows(Exception.class, () -> {
            userService.deleteUser(1L);
        });
        Assertions.assertEquals("File not found", exception.getMessage());

        Mockito.verify(userRepository, Mockito.times(1)).deleteById(Mockito.anyLong());
    }

    /**
     * Can we mock static method UserRepository.getString()? No
     * - In Java, static methods like getString() in your UserRepository interface cannot be mocked directly using classic Mockito (prior to version 3.4.0).
     * - However, starting with Mockito 3.4.0, you can use mocking of static methods via mockito-inline
     * - Here’s how to mock the getString() static method of UserRepository using Mockito 3.4.0+ with the mockito-inline dependency
     *  -- Add "mockito-inline" to Your pom.xml (for Maven)
     *  -- Mock the Static Method in Your Test testMockStaticGetString()
     */
    @Test
    void testGetStringUsingMockStatic() {

        try (MockedStatic<UserRepository> mockedStatic = Mockito.mockStatic(UserRepository.class)) {
            mockedStatic.when(UserRepository::getString).thenReturn("mocked string");
            String result = userService.getString();
            Assertions.assertEquals("mocked string", result);
        }
    }

    @Test
    void testUserModelJustForCodeCoverage() {

        User user = new User(1L, "Sachin", "Tendulkar", "sachin.rt@gmail.com");
        Assertions.assertNotNull(user);
    }
}


