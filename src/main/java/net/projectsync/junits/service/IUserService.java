package net.projectsync.junits.service;

import java.util.List;
import net.projectsync.junits.model.User;

public interface IUserService {

	User createUser(User user);

	User getUserById(Long userId);

	List<User> getAllUsers();

	User updateUser(User user);

	void deleteUser(Long userId);
}
