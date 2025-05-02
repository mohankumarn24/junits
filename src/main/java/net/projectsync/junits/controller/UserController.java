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

	// build create User REST API
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, headers = {"Content-Type=application/json", "Accept=application/json"})
	public ResponseEntity<User> createUser(@RequestBody User user) {
		User savedUser = userService.createUser(user);
		return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
	}

	// build get user by id REST API
	// http://localhost:5000/api/users/1
	@GetMapping(value="/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, headers = {"Content-Type=application/json", "Accept=application/json"})
	public ResponseEntity<User> getUserById(@PathVariable("id") Long userId) {
		User user = userService.getUserById(userId);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	// Build Get All Users REST API
	// http://localhost:5000/api/users
	@GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE, headers = {"Content-Type=application/json", "Accept=application/json"})
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> users = userService.getAllUsers();
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	// Build Update User REST API
	@PutMapping(value="/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, headers = {"Content-Type=application/json", "Accept=application/json"})
	// http://localhost:5000/api/users/1
	public ResponseEntity<User> updateUser(@PathVariable("id") Long userId, @RequestBody User user) {
		user.setId(userId);
		User updatedUser = userService.updateUser(user);
		return new ResponseEntity<>(updatedUser, HttpStatus.OK);
	}

	// Build Delete User REST API
	// http://localhost:5000/api/users/1
	@DeleteMapping(value="/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, headers = {"Content-Type=application/json", "Accept=application/json"})
	public ResponseEntity<String> deleteUser(@PathVariable("id") Long userId) {
		userService.deleteUser(userId);
		// return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<>("User successfully deleted!", HttpStatus.NO_CONTENT);
	}

	// Build Delete User REST API
	// http://localhost:5000/api/users?id=1
	@DeleteMapping(params = {"id"}, consumes = MediaType.APPLICATION_JSON_VALUE, headers = {"Content-Type=application/json", "Accept=application/json"})
	public ResponseEntity<String> deleteUserByParam(@RequestParam(name = "id") @NonNull Long userId) {
		userService.deleteUser(userId);
		// return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<>("User successfully deleted!", HttpStatus.NO_CONTENT);
	}
}
