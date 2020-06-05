package com.smbms.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.smbms.mapper.SmbmsUserMapper;
import com.smbms.pojo.SmbmsUser;
import com.smbms.service.SmbmsUserService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
@Service
@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
public class SmbmsUserServiceImpl implements SmbmsUserService{
    private Logger logger = LogManager.getLogger(SmbmsUserServiceImpl.class);
    @Resource
    private SmbmsUserMapper userMapper;
    @Override
    public boolean add(SmbmsUser user) {
        boolean flag = false;
        try {
            int updateRows = userMapper.add(user);
            if(updateRows > 0){
                flag = true;
                System.out.println("add success!");
            }else{
                System.out.println("add failed!");
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("rollback==================");
        }finally{
        }
        return flag;
    }

    @Override
    public SmbmsUser login(String userCode, String userPassword) {
        SmbmsUser user = null;
        /*int test=20/0;*/
        try {
            user = userMapper.getLoginUser(userCode);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
        }

        //匹配密码
        if(null != user){
            if(!user.getUserPassword().equals(userPassword)) {
                user = null;
            }
        }
        return user;
    }

    @Override
    public List<SmbmsUser> getUserList(String queryUserName, int queryUserRole, int currentPageNo, int pageSize) {
        List<SmbmsUser> userList = null;
        System.out.println("queryUserName ---- > " + queryUserName);
        System.out.println("queryUserRole ---- > " + queryUserRole);
        System.out.println("currentPageNo ---- > " + currentPageNo);
        System.out.println("pageSize ---- > " + pageSize);
        try {
            currentPageNo = (currentPageNo-1)*pageSize;
            userList = userMapper.getUserList(queryUserName,queryUserRole,currentPageNo,pageSize);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
        }
        return userList;
    }

    @Override
    public int getUserCount(String queryUserName, int queryUserRole) {
        int count = 0;
        System.out.println("queryUserName ---- > " + queryUserName);
        System.out.println("queryUserRole ---- > " + queryUserRole);
        try {
            count = userMapper.getUserCount(queryUserName,queryUserRole);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
        }
        return count;
    }

    @Override
    public SmbmsUser selectUserCodeExist(String userCode) {
        SmbmsUser user = null;
        try {
            user = userMapper.getLoginUser(userCode);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{

        }
        return user;
    }

    @Override
    public boolean deleteUserById(Integer delId) {
        boolean flag = false;
        try {
            if(userMapper.deleteUserById(delId) > 0) {
                flag = true;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{

        }
        return flag;
    }

    @Override
    public SmbmsUser getUserById(String id) {
        SmbmsUser user = null;
        try{
            user = userMapper.getUserById(id);
        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            user = null;
        }finally{
        }
        return user;
    }

    @Override
    public boolean modify(SmbmsUser user) {
        boolean flag = false;
        try {
            if(userMapper.modify(user) > 0) {
                flag = true;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
        }
        return flag;
    }

    @Override
    public boolean updatePwd(int id, String pwd) {
        boolean flag = false;
        try{
            if(userMapper.updatePwd(id,pwd) > 0) {
                flag = true;
            }
        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }finally{
        }
        return flag;
    }
}
