package com.zju.edu.gcs.controller;

/**
 * Created on 2021/9/26
 *
 * @author Patric Tian
 */

import com.zju.edu.gcs.common.exception.NirException;
import com.zju.edu.gcs.common.exception.NirExceptionEnum;
import com.zju.edu.gcs.common.result.Result;
import com.zju.edu.gcs.common.result.ResultEnum;
import com.zju.edu.gcs.dto.ModifyRoleDTO;
import com.zju.edu.gcs.dto.RegisterDTO;
import com.zju.edu.gcs.dto.RoleDTO;
import com.zju.edu.gcs.service.RoleService;
import com.zju.edu.gcs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/authority")
public class AuthorityController {
    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @PostMapping("/role")
    public Result<Void> addRole(@RequestBody RoleDTO roleDTO) {
        Result<Void> result = new Result<>();
        try {
            roleService.addRole(roleDTO);
        } catch (NirException e) {
            result.setCode(e.getCode());
            result.setMsg(e.getMessage());
            return result;
        } catch (Exception e) {
            result.setCode(NirExceptionEnum.GENERAL_EXCEPTION.getCode());
            result.setMsg(e.getMessage());
            return result;
        }
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        return result;
    }

    @DeleteMapping("/role")
    public Result<Void> deleteRole(@RequestBody ModifyRoleDTO modifyRoleDTO) {
        Result<Void> result = new Result<>();
        try {
            roleService.deleteRole(modifyRoleDTO.getRoleName());
        } catch (NirException e) {
            result.setCode(e.getCode());
            result.setMsg(e.getMessage());
            return result;
        } catch (Exception e) {
            result.setCode(NirExceptionEnum.GENERAL_EXCEPTION.getCode());
            result.setMsg(e.getMessage());
            return result;
        }
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        return result;
    }

    @PutMapping("/role")
    public Result<Void> updateRole(@RequestBody RoleDTO roleDTO) {
        Result<Void> result = new Result<>();
        try {
            roleService.updateRole(roleDTO);
        } catch (NirException e) {
            result.setCode(e.getCode());
            result.setMsg(e.getMessage());
            return result;
        } catch (Exception e) {
            result.setCode(NirExceptionEnum.GENERAL_EXCEPTION.getCode());
            result.setMsg(e.getMessage());
            return result;
        }
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        return result;
    }

    @GetMapping("/role")
    public Result<List<RoleDTO>> listRoles() {
        Result<List<RoleDTO>> result = new Result<>();
        List<RoleDTO> roleDTOS;
        try {
            roleDTOS = roleService.listRoles();
        } catch (NirException e) {
            result.setCode(e.getCode());
            result.setMsg(e.getMessage());
            return result;
        } catch (Exception e) {
            result.setCode(NirExceptionEnum.GENERAL_EXCEPTION.getCode());
            result.setMsg(e.getMessage());
            return result;
        }
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        result.setData(roleDTOS);
        return result;
    }

    @PostMapping("/user-in-role")
    public Result<Void> addUserIntoRole(@RequestBody ModifyRoleDTO modifyRoleDTO ) {
        Result<Void> result = new Result<>();
        try {
            roleService.addUserIntoRole(modifyRoleDTO.getUsername(), modifyRoleDTO.getRoleName());
        } catch (NirException e) {
            result.setCode(e.getCode());
            result.setMsg(e.getMessage());
            return result;
        } catch (Exception e) {
            result.setCode(NirExceptionEnum.GENERAL_EXCEPTION.getCode());
            result.setMsg(e.getMessage());
            return result;
        }
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        return result;
    }

    @DeleteMapping("/user-in-role")
    public Result<Void> deleteUserFromRole(@RequestBody ModifyRoleDTO modifyRoleDTO) {
        Result<Void> result = new Result<>();
        try {
            roleService.deleteUserFromRole(modifyRoleDTO.getUsername(), modifyRoleDTO.getRoleName());
        } catch (NirException e) {
            result.setCode(e.getCode());
            result.setMsg(e.getMessage());
            return result;
        } catch (Exception e) {
            result.setCode(NirExceptionEnum.GENERAL_EXCEPTION.getCode());
            result.setMsg(e.getMessage());
            return result;
        }
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        return result;
    }

    @GetMapping("/user-in-role")
    public Result<List<RegisterDTO>> listUserFromRole(@RequestParam("roleName") String roleName) {
        Result<List<RegisterDTO>> result = new Result<>();
        List<RegisterDTO> registerDTOS;
        try {
            registerDTOS = roleService.listUserFromRole(roleName);
        } catch (NirException e) {
            result.setCode(e.getCode());
            result.setMsg(e.getMessage());
            return result;
        } catch (Exception e) {
            result.setCode(NirExceptionEnum.GENERAL_EXCEPTION.getCode());
            result.setMsg(e.getMessage());
            return result;
        }
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        result.setData(registerDTOS);
        return result;
    }
}
