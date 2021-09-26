package com.zju.edu.gcs.repository;

import com.zju.edu.gcs.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsernameAndAndPassword(String username, String password);
    List<User> findAllByUsername(String username);
    User findByUsername(String username);
    List<User> findAllByIdIn(List<Integer> userIdList);
}
