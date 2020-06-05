package com.smbms.service.impl;

import com.smbms.mapper.SmbmsBillMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.smbms.mapper.SmbmsProviderMapper;
import com.smbms.pojo.SmbmsProvider;
import com.smbms.service.SmbmsProviderService;
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
public class SmbmsProviderServiceImpl implements SmbmsProviderService{
        private Logger logger = LogManager.getLogger(SmbmsProviderServiceImpl.class);
        @Resource
        private SmbmsProviderMapper providerDao;
        @Resource
        private SmbmsBillMapper billDao ;

        @Override
        public boolean add(SmbmsProvider provider) {
            // TODO Auto-generated method stub
            boolean flag = false;
            try {
                if(providerDao.add(provider) > 0) {
                    flag = true;
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                System.out.println("rollback==================");
            }finally{
                //在service层进行connection连接的关闭
            }
            return flag;
        }

        @Override
        public List<SmbmsProvider> getProviderList(String proName, String proCode) {
            // TODO Auto-generated method stub
            List<SmbmsProvider> providerList = null;
            System.out.println("query proName ---- > " + proName);
            System.out.println("query proCode ---- > " + proCode);
            try {
                providerList = providerDao.getProviderList(proName,proCode);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }finally{
            }
            return providerList;
        }

        /**
         * 业务：根据ID删除供应商表的数据之前，需要先去订单表里进行查询操作
         * 若订单表中无该供应商的订单数据，则可以删除
         * 若有该供应商的订单数据，则不可以删除
         * 返回值billCount
         * 1> billCount == 0  删除---1 成功 （0） 2 不成功 （-1）
         * 2> billCount > 0    不能删除 查询成功（0）查询不成功（-1）
         *
         * ---判断
         * 如果billCount = -1 失败
         * 若billCount >= 0 成功
         */
        @Override
        public int deleteProviderById(String delId) {
            // TODO Auto-generated method stub
            int billCount = -1;
            try {
                billCount = billDao.getBillCountByProviderId(delId);
                if(billCount == 0){
                    providerDao.deleteProviderById(delId);
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                billCount = -1;

            }finally{
            }
            return billCount;
        }

        @Override
        public SmbmsProvider getProviderById(String id) {
            // TODO Auto-generated method stub
            SmbmsProvider provider = null;
            try{
                provider = providerDao.getProviderById(id);
            }catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
                provider = null;
            }finally{
            }
            return provider;
        }

        @Override
        public boolean modify(SmbmsProvider provider) {
            // TODO Auto-generated method stub
            boolean flag = false;
            try {
                if(providerDao.modify(provider) > 0) {
                    flag = true;
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }finally{
            }
            return flag;
        }
}
