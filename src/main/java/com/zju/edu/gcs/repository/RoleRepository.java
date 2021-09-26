package com.zju.edu.gcs.repository;

import com.zju.edu.gcs.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created on 2021/9/24
 *
 * @author Patric Tian
 */
@Component
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByName(String name);

    void deleteByName(String name);
}
