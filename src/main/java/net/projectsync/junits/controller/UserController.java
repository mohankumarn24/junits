package net.projectsync.junits.controller;

import java.util.List;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.AllArgsConstructor;
import net.projectsync.junits.model.User;
import net.projectsync.junits.service.UserService;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserController {

	private final UserService userService;

    /**
     * POST/PUT/PATCH → consumes + produces
     * build create User REST API
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> createUser(@RequestBody User user) {
		User savedUser = userService.createUser(user);
		return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
	}

    /**
     * GET → only produces
     * build get user by id REST API
     * build get user by id REST API
     */
	@GetMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE, headers = "X-API-VERSION=1")
	public ResponseEntity<User> getUserByIdv1(@PathVariable("id") Long userId) {
		User user = userService.getUserById(userId);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

    @GetMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE, headers = "X-API-VERSION=2")
    public ResponseEntity<User> getUserByIdv2(@PathVariable("id") Long userId) {
        User user = userService.getUserById(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * Build Get All Users REST API
     * GET → only produces
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> users = userService.getAllUsers();
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

    /**
     * POST/PUT/PATCH → consumes + produces
     * Build Update User REST API
     */
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	// http://localhost:5000/api/users/1
	public ResponseEntity<User> updateUser(@PathVariable("id") Long userId, @RequestBody User user) {
		user.setId(userId);
		User updatedUser = userService.updateUser(user);
		return new ResponseEntity<>(updatedUser, HttpStatus.OK);
	}

    /**
     * DELETE → usually neither, sometimes produces if it returns a body
     * Build Delete User REST API
     */
	@DeleteMapping(value="/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable("id") Long userId) {
		userService.deleteUser(userId);
		// return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<>("User successfully deleted!", HttpStatus.NO_CONTENT);
	}

    /**
     * DELETE → usually neither, sometimes produces if it returns a body
     * Build Delete User REST API
     */
	@DeleteMapping(params = {"id"})
	public ResponseEntity<String> deleteUserByParam(@RequestParam(name = "id") @NonNull Long userId) {
		userService.deleteUser(userId);
		// return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<>("User successfully deleted!", HttpStatus.NO_CONTENT);
	}
}
