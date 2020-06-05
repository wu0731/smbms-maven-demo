package com.smbms.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.smbms.pojo.SmbmsRole;
import com.smbms.mapper.SmbmsRoleMapper;
import com.smbms.service.SmbmsRoleService;
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
public class SmbmsRoleServiceImpl implements SmbmsRoleService{
    private Logger logger = LogManager.getLogger(SmbmsRoleServiceImpl.class);
    @Resource
    private SmbmsRoleMapper roleDao;
    @Override
    public List<SmbmsRole> getRoleList() {
        // TODO Auto-generated method stub
        List<SmbmsRole> roleList = null;
        try {
            roleList = roleDao.getRoleList();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
        }
        return roleList;
    }
}
