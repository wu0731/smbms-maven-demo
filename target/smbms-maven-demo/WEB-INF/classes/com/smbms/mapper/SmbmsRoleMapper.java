package com.smbms.mapper;

import com.smbms.pojo.SmbmsRole;

import java.util.List;

/**
    * @program: smbms-maven-demo
    *
    * @description: ${description}
    *
    * @author: wu
    *
    * @create: 2020-05-30 10:20
    **/
public interface SmbmsRoleMapper {
        public List<SmbmsRole> getRoleList()throws Exception;
}