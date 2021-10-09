package com.zju.edu.gcs.service;

import com.zju.edu.gcs.common.exception.NirException;
import com.zju.edu.gcs.common.exception.NirExceptionEnum;
import com.zju.edu.gcs.dto.RegisterDTO;
import com.zju.edu.gcs.dto.RoleDTO;
import com.zju.edu.gcs.model.Role;
import com.zju.edu.gcs.model.User;
import com.zju.edu.gcs.repository.RoleRepository;
import com.zju.edu.gcs.repository.UserRepository;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created on 2021/9/24
 *
 * @author Patric Tian
 */
@Service
@Transactional
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    public void addRole(RoleDTO roleDTO) {
        Role role = roleRepository.findByName(roleDTO.getName());
        if (role != null) {
            throw new NirException(NirExceptionEnum.ROLE_EXIST);
        }
        roleRepository.save(RoleDTO.toEntity(roleDTO));
    }

    public void deleteRole(String name) {
        roleRepository.deleteByName(name);
    }

    public List<RoleDTO> listRoles() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream().map(RoleDTO::fromEntity).collect(Collectors.toList());
    }

    public void updateRole(RoleDTO roleDTO) {
        Role role = roleRepository.findByName(roleDTO.getName());
        if (role == null) {
            throw new NirException(NirExceptionEnum.ROlE_NOT_EXIST);
        }
        roleRepository.save(RoleDTO.toEntity(roleDTO));

    }

    public void addUserIntoRole(String username, String roleName) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new NirException(NirExceptionEnum.USER_NOT_EXIST);
        }
        Role role = roleRepository.findByName(roleName);
        if (role == null) {
            throw new NirException(NirExceptionEnum.ROlE_NOT_EXIST);
        }
        if (role.getContainUserId() == null) {
            role.setContainUserId(user.getId().toString());
        } else {
            List<String> userIdList = Arrays.asList(role.getContainUserId().split(","));
            if (!userIdList.contains(user.getId().toString())) {
                role.setContainUserId(role.getContainUserId() + "," + user.getId().toString());

            }
        }
        roleRepository.save(role);
    }

    public void deleteUserFromRole(String username, String roleName) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new NirException(NirExceptionEnum.USER_NOT_EXIST);
        }
        Role role = roleRepository.findByName(roleName);
        if (role == null) {
            throw new NirException(NirExceptionEnum.ROlE_NOT_EXIST);
        }
        List<String> userIdList = role.getContainUserId() == null ? new ArrayList<>() : Arrays.asList(role.getContainUserId().split(","));
        userIdList = new ArrayList<>(userIdList);
        userIdList.remove(user.getId().toString());
        role.setContainUserId(CollectionUtils.isEmpty(userIdList) ? null :
                StringUtils.join(userIdList, ','));
        roleRepository.save(role);
    }

    public List<RegisterDTO> listUserFromRole(String roleName) {
        Role role = roleRepository.findByName(roleName);
        if (role == null) {
            throw new NirException(NirExceptionEnum.ROlE_NOT_EXIST);
        }
        List<String> userIdList = role.getContainUserId() == null ? new ArrayList<>() : Arrays.asList(role.getContainUserId().split(","));
        List<Integer> integerList = userIdList.stream().map(Integer::valueOf).collect(Collectors.toList());
        List<User> userList = userRepository.findAllByIdIn(integerList);
        return userList.stream().map(user -> RegisterDTO.fromEntity(user)).collect(Collectors.toList());
    }

    public Map<Integer, String> getUserNameById(List<Integer> idList) {
        List<User> users = userRepository.findAllByIdIn(idList);
        return users.stream().collect(Collectors.toMap(User::getId, User::getTrueName));
    }
}
