package net.projectsync.junits.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.projectsync.junits.model.User;
import net.projectsync.junits.service.UserService;
import net.projectsync.junits.util.UserHelper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.nio.charset.StandardCharsets;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class) // ensures that only beans relevant to the Web layer are loaded into the ApplicationContext
public class UserControllerTest {

    @MockBean
    private UserService userService;
    
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreateUser() throws Exception {
        Mockito.when(userService.createUser(Mockito.any(User.class))).thenReturn(UserHelper.getUser());
        String reqBody = new ObjectMapper().writeValueAsString(UserHelper.getUser());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .header("Content-Type", "application/json")
                        .header("Accept", "application/json")
                        .content(reqBody))
                .andExpect(MockMvcResultMatchers.status().isCreated());
                //.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                //.andExpect(MockMvcResultMatchers.header().string("custom-header", "some-value"));

        Mockito.verify(userService, Mockito.times(1)).createUser(Mockito.any(User.class));
    }

    // GET /api/users/{id}
    // X-API-VERSION=1
    @Test
    public void testGetUserByIdV1() throws Exception {
        Mockito.when(userService.getUserById(Mockito.anyLong())).thenReturn(UserHelper.getUpdatedUser());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .header("X-API-VERSION", "1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));

        Mockito.verify(userService, Mockito.times(1)).getUserById(Mockito.anyLong());
    }

    // GET /api/users/{id}
    // X-API-VERSION=2
    @Test
    public void testGetUserByIdV2() throws Exception {
        Mockito.when(userService.getUserById(Mockito.anyLong())).thenReturn(UserHelper.getUpdatedUser());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .header("X-API-VERSION", "2"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));

        Mockito.verify(userService, Mockito.times(1)).getUserById(Mockito.anyLong());
    }

    @Test
    public void testGetAllUsers() throws Exception {
        Mockito.when(userService.getAllUsers()).thenReturn(UserHelper.getUsers());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(UserHelper.getUsers().get(0).getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(UserHelper.getUsers().get(1).getId()));

        Mockito.verify(userService, Mockito.times(1)).getAllUsers();
    }

    @Test
    public void testDeleteUser() throws Exception {

        Mockito.doNothing().when(userService).deleteUser(Mockito.anyLong());

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/users/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isNoContent());

        Mockito.verify(userService, Mockito.times(1)).deleteUser(Mockito.anyLong());
    }

    @Test
    public void testDeleteUserByParam() throws Exception {

        Mockito.doNothing().when(userService).deleteUser(Mockito.anyLong());

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/users")
                        .param("id", String.valueOf(1L))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isNoContent());

        Mockito.verify(userService, Mockito.times(1)).deleteUser(Mockito.anyLong());
    }

    @Test
    public void testDeleteUserByParamException() throws Exception {

        Mockito.doNothing().when(userService).deleteUser(Mockito.anyLong());

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/users")
                        .param("id", "invalidId") // invalid id
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof Exception));

        Mockito.verify(userService, Mockito.times(0)).deleteUser(Mockito.anyLong());
    }

    @Test
    public void testUpdateUser() throws Exception {

        Mockito.when(userService.updateUser(Mockito.any(User.class))).thenReturn(UserHelper.getUpdatedUser());
        String reqBody = new ObjectMapper().writeValueAsString(UserHelper.getUser());

        mockMvc.perform(MockMvcRequestBuilders.put("/api/users/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(reqBody))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Rahul1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Dravid1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("rahul.dravid@gmail.com"));

        Mockito.verify(userService, Mockito.times(1)).updateUser(Mockito.any(User.class));
    }

    @Test
    public void testCreateUserException() throws Exception {
        Mockito.when(userService.createUser(Mockito.any(User.class))).thenReturn(UserHelper.getUser());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(new String())) // empty request body
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof Exception))
                .andExpect(result -> assertEquals("Required request body is missing: public org.springframework.http.ResponseEntity<net.projectsync.junits.model.User> net.projectsync.junits.controller.UserController.createUser(net.projectsync.junits.model.User)", result.getResolvedException().getMessage()));

        Mockito.verify(userService, Mockito.times(0)).updateUser(Mockito.any(User.class));
    }
}
