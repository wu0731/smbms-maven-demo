package com.smbms.service;

import com.smbms.pojo.SmbmsBill;
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
public interface SmbmsBillService{

            /**
             * 增加订单
             * @param bill
             * @return
             */
             boolean add(SmbmsBill bill);


            /**
             * 通过条件获取订单列表-模糊查询-billList
             * @param bill
             * @return
             */
             List<SmbmsBill> getBillList(SmbmsBill bill);

            /**
             * 通过billId删除Bill
             * @param delId
             * @return
             */
             boolean deleteBillById(String delId);


            /**
             * 通过billId获取Bill
             * @param id
             * @return
             */
             SmbmsBill getBillById(String id);

    /**
     * 修改订单信息
     *
     * @param bill
     * @return
     */
    default boolean modify(SmbmsBill bill) {
        return false;
    }

}
