package org.example.kurakani.repo;

import org.example.kurakani.dto.UserDto;
import org.example.kurakani.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    void deleteByUsername(String name);

    Optional<User> findByUsernameIgnoreCase(String username);


    boolean existsByEmail(String Email);
}
