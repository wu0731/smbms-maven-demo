package com.smbms.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.smbms.mapper.SmbmsAddressMapper;
import com.smbms.pojo.SmbmsAddress;
import com.smbms.service.SmbmsAddressService;
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
public class SmbmsAddressServiceImpl implements SmbmsAddressService{

    @Resource
    private SmbmsAddressMapper smbmsAddressMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return smbmsAddressMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(SmbmsAddress record) {
        return smbmsAddressMapper.insert(record);
    }

    @Override
    public int insertSelective(SmbmsAddress record) {
        return smbmsAddressMapper.insertSelective(record);
    }

    @Override
    public SmbmsAddress selectByPrimaryKey(Long id) {
        return smbmsAddressMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(SmbmsAddress record) {
        return smbmsAddressMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(SmbmsAddress record) {
        return smbmsAddressMapper.updateByPrimaryKey(record);
    }

}
