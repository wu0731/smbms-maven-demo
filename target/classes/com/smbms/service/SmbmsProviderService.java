package com.smbms.service;

import com.smbms.pojo.SmbmsProvider;

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
public interface SmbmsProviderService{

            /**
             * 增加供应商
             * @param provider
             * @return
             */
            public boolean add(SmbmsProvider provider);


            /**
             * 通过供应商名称、编码获取供应商列表-模糊查询-providerList
             * @param proName
             * @return
             */
            public List<SmbmsProvider> getProviderList(String proName, String proCode);

            /**
             * 通过proId删除Provider
             * @param delId
             * @return
             */
            public int deleteProviderById(String delId);


            /**
             * 通过proId获取Provider
             * @param id
             * @return
             */
            public SmbmsProvider getProviderById(String id);

            /**
             * 修改用户信息
             * @param user
             * @return
             */
            public boolean modify(SmbmsProvider provider);

}
