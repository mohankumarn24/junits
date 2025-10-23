package net.projectsync.junits.controller;

import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import net.projectsync.junits.model.User;
import net.projectsync.junits.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

	private final UserService userService;

    /* All TCD's works for below constructor as well
    public UserController(UserService userService) {
        this.userService = userService;
    }
    */
    
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
     *
     * Call this method only if the HTTP request contains a header named X-API-VERSION with value 1
     *
     * headers = "X-API-VERSION=1"      → “Only consider me if the request has this header”
     * @RequestHeader("X-XSRF-TOKEN")   → “Give me the value of this header to use in my logic”
     */
	@GetMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE, headers = "X-API-VERSION=1")
	public ResponseEntity<User> getUserByIdv1(@PathVariable("id") Long userId) {
		User user = userService.getUserById(userId);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

    /**
     * Call this method only if the HTTP request contains a header named X-API-VERSION with value 2
     * @param userId
     * @return
     */
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
	// http://localhost:8080/api/users/1
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
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

    /**
     * DELETE → usually neither, sometimes produces if it returns a body
     * Build Delete User REST API
     * DELETE api/v1/users?id=1
     */
	@DeleteMapping
	public ResponseEntity<String> deleteUserByParam(@RequestParam(name = "id", required = true, defaultValue = "1") @NonNull Long userId) {
		userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}

/* Note the arguments used for each endpoint

// Example 1: Explicit Multiple @RequestParam Values
// URL example: GET /users/search?name=John&email=john@example.com&active=true
@GetMapping("/search")
public List<User> searchUsers(
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String email,
        @RequestParam(defaultValue = "false") boolean active) {

    List<User> result = new ArrayList<>();
    for (User u : userStore.values()) {
        boolean matches = true;
        if (name != null && !u.getName().equalsIgnoreCase(name)) matches = false;
        if (email != null && !u.getEmail().equalsIgnoreCase(email)) matches = false;
        if (active && !u.getEmail().endsWith("@example.com")) matches = false; // dummy check
        if (matches) result.add(u);
    }
    return result;
}


// Example 2: Using Map<String, String> for Dynamic Parameters  <-- IMPORTANT
// URL example: GET /users/search?name=John&city=Delhi&role=admin
@GetMapping("/search")
public List<User> searchUsers(@RequestParam Map<String, String> params) {
    params.forEach((key, value) -> {
        System.out.println(key + " = " + value);
    });
    // You can apply filters based on params dynamically
    return new ArrayList<>(userStore.values());
}


// Example 3: Multiple @PathVariable Values
// Example: GET /users/101/posts/55
@GetMapping("/users/{userId}/posts/{postId}")
public String getPostByUser(
        @PathVariable int userId,
        @PathVariable int postId) {
    return "Fetching Post ID " + postId + " for User ID " + userId;
}


// Example 4: Different Variable Names in URL and Method
// Example: GET /departments/10/employees/501
@GetMapping("/departments/{deptId}/employees/{empId}")
public String getEmployeeDetails(
        @PathVariable("deptId") int departmentId,
        @PathVariable("empId") int employeeId) {

    return "Employee ID " + employeeId + " belongs to Department ID " + departmentId;
}


// Example 5: Mix of @PathVariable + @RequestParam
// Example: GET /users/101/posts/55/comments?sort=asc&limit=10
@GetMapping("/users/{userId}/posts/{postId}/comments")
public String getComments(
        @PathVariable int userId,
        @PathVariable int postId,
        @RequestParam(defaultValue = "asc") String sort,
        @RequestParam(defaultValue = "10") int limit) {

    return String.format(
        "Fetching %d comments for post %d of user %d sorted %s",
        limit, postId, userId, sort);
}

@PostMapping("/refresh")      <-- IMPORTANT
public ResponseEntity<ApiResponse<TokenResponse>> refresh(
												HttpServletRequest httpServletRequest,
												HttpServletResponse httpServletResponse,
												// @CookieValue(name = REFRESH_COOKIE_NAME, required = false) String oldRefreshToken,
												// @CookieValue(name = CSRF_COOKIE_NAME, required = false) String csrfCookieValue,
												// @RequestParam @Min(1) @Max(100) int version, 	// @Validated triggers validation on request params ie., /api/auth/refresh?version=0
												@RequestHeader(value = "X-XSRF-TOKEN", required = false) String csrfHeaderValue) {

	AuthCookies authCookies = CookieUtils.getAuthCookies(httpServletRequest, cookieProperties);
	return authService.refresh(
			httpServletRequest,
			httpServletResponse,
			authCookies.getRefreshToken(),
			authCookies.getCsrfToken(),
			csrfHeaderValue);
}
 */