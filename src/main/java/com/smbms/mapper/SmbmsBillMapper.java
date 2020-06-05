package com.smbms.mapper;

import com.smbms.pojo.SmbmsBill;

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
public interface SmbmsBillMapper {
        /**
         * 增加订单
         * @param bill
         * @return
         * @throws Exception
         */
        public int add(SmbmsBill bill)throws Exception;


        /**
         * 通过查询条件获取供应商列表-模糊查询-getBillList
         * @param bill
         * @return
         * @throws Exception
         */
        public List<SmbmsBill> getBillList(SmbmsBill bill)throws Exception;

        /**
         * 通过delId删除Bill
         * @param delId
         * @return
         * @throws Exception
         */
        public int deleteBillById(String delId)throws Exception;


        /**
         * 通过billId获取Bill
         * @param id
         * @return
         * @throws Exception
         */
        public SmbmsBill getBillById(String id)throws Exception;

        /**
         * 修改订单信息
         * @param bill
         * @return
         * @throws Exception
         */
        public int modify(SmbmsBill bill)throws Exception;

        /**
         * 根据供应商ID查询订单数量
         * @param providerId
         * @return
         * @throws Exception
         */
        public int getBillCountByProviderId(String providerId)throws Exception;
}