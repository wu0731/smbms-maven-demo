package com.smbms.service;

import com.smbms.pojo.SmbmsUser;

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
public interface SmbmsUserService{
            /**
             * 增加用户信息
             * @param user
             * @return
             */
             boolean add(SmbmsUser user);

            /**
             * 用户登录
             * @param userCode
             * @param userPassword
             * @return
             */
             SmbmsUser login(String userCode,String userPassword);

            /**
             * 根据条件查询用户列表
             * @param queryUserName
             * @param queryUserRole
             * @return
             */
             List<SmbmsUser> getUserList(String queryUserName, int queryUserRole, int currentPageNo, int pageSize);
            /**
             * 根据条件查询用户表记录数
             * @param queryUserName
             * @param queryUserRole
             * @return
             */
             int getUserCount(String queryUserName,int queryUserRole);

            /**
             * 根据userCode查询出User
             * @param userCode
             * @return
             */
             SmbmsUser selectUserCodeExist(String userCode);

            /**
             * 根据ID删除user
             * @param delId
             * @return
             */
             boolean deleteUserById(Integer delId);

            /**
             * 根据ID查找user
             * @param id
             * @return
             */
             SmbmsUser getUserById(String id);

            /**
             * 修改用户信息
             * @param user
             * @return
             */
             boolean modify(SmbmsUser user);

            /**
             * 根据userId修改密码
             * @param id
             * @param pwd
             * @return
             */
             boolean updatePwd(int id,String pwd);
}
