package com.smbms.mapper;

import com.smbms.pojo.SmbmsProvider;
import org.apache.ibatis.annotations.Param;

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
public interface SmbmsProviderMapper {

        /**
         * 增加供应商
         * @param provider
         * @return
         * @throws Exception
         */
        public int add(SmbmsProvider provider)throws Exception;


        /**
         * 通过供应商名称、编码获取供应商列表-模糊查询-providerList
         * @param proName
         * @return
         * @throws Exception
         */
        public List<SmbmsProvider> getProviderList(@Param("proName") String proName, @Param("proCode") String proCode)throws Exception;

        /**
         * 通过proId删除Provider
         * @param delId
         * @return
         * @throws Exception
         */
        public int deleteProviderById(String delId)throws Exception;


        /**
         * 通过proId获取Provider
         * @param id
         * @return
         * @throws Exception
         */
        public SmbmsProvider getProviderById(String id)throws Exception;

        /**
         * 修改用户信息
         * @return
         * @throws Exception
         */
        public int modify(SmbmsProvider provider)throws Exception;
}