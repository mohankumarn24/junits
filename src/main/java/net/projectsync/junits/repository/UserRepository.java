package net.projectsync.junits.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.projectsync.junits.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
