package com.zju.edu.gcs.dto;

import com.zju.edu.gcs.model.Role;
import lombok.Data;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created on 2021/9/24
 *
 * @author Patric Tian
 */
@Data
public class RoleDTO {
    private Integer id;

    private String name;

    private String chineseName;

    private String patientCreateAuthority;

    private String recordQueryAuthority;

    private String recordComparisonAuthority;

    private String identifyAuthorityAuthority;

    private String effectEvaluationAuthority;

    private String statAnalysisAuthority;

    private String authorityManagement;

    private List<Integer> containUserId;

    private Date createTime;


    public static Role toEntity(RoleDTO roleDTO) {
        Role role = new Role();
        role.setId(roleDTO.getId());
        role.setName(roleDTO.getName());
        role.setChineseName(roleDTO.getChineseName());
        role.setPatientCreateAuthority(roleDTO.getPatientCreateAuthority());
        role.setRecordQueryAuthority(roleDTO.getRecordQueryAuthority());
        role.setRecordComparisonAuthority(roleDTO.getRecordComparisonAuthority());
        role.setIdentifyAuthorityAuthority(roleDTO.getIdentifyAuthorityAuthority());
        role.setEffectEvaluationAuthority(roleDTO.getEffectEvaluationAuthority());
        role.setStatAnalysisAuthority(roleDTO.getStatAnalysisAuthority());
        role.setAuthorityManagement(roleDTO.getAuthorityManagement());
        role.setContainUserId(CollectionUtils.isEmpty(roleDTO.getContainUserId()) ? null :
                StringUtils.join(roleDTO.getContainUserId().stream().map(String::valueOf).collect(Collectors.toList()), ','));
        role.setCreateTime(roleDTO.getCreateTime());
        return role;
    }

    public static RoleDTO fromEntity(Role role) {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(role.getId());
        roleDTO.setName(role.getName());
        roleDTO.setChineseName(role.getChineseName());
        roleDTO.setPatientCreateAuthority(role.getPatientCreateAuthority());
        roleDTO.setRecordQueryAuthority(role.getRecordQueryAuthority());
        roleDTO.setRecordComparisonAuthority(role.getRecordComparisonAuthority());
        roleDTO.setIdentifyAuthorityAuthority(role.getIdentifyAuthorityAuthority());
        roleDTO.setEffectEvaluationAuthority(role.getEffectEvaluationAuthority());
        roleDTO.setStatAnalysisAuthority(roleDTO.getStatAnalysisAuthority());
        roleDTO.setAuthorityManagement(role.getAuthorityManagement());
        roleDTO.setContainUserId(role.getContainUserId() == null ? new ArrayList<>() :
                Arrays.asList(role.getContainUserId().split(",")).stream().map(Integer::valueOf).collect(Collectors.toList()));
        roleDTO.setCreateTime(role.getCreateTime());
        return roleDTO;
    }
}
