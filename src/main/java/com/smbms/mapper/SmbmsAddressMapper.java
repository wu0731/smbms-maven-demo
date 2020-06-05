package com.smbms.mapper;

import com.smbms.pojo.SmbmsAddress;

    /**
    * @program: smbms-maven-demo
    *
    * @description: ${description}
    *
    * @author: wu
    *
    * @create: 2020-05-30 10:20
    **/
public interface SmbmsAddressMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SmbmsAddress record);

    int insertSelective(SmbmsAddress record);

    SmbmsAddress selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SmbmsAddress record);

    int updateByPrimaryKey(SmbmsAddress record);
}