package com.zju.edu.gcs.repository;

import com.zju.edu.gcs.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findAllByUsernameAndAndPassword(String username, String password);
}
