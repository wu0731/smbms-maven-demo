package com.smbms.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.smbms.mapper.SmbmsBillMapper;
import com.smbms.pojo.SmbmsBill;
import com.smbms.service.SmbmsBillService;
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
public class SmbmsBillServiceImpl implements SmbmsBillService{
        private Logger logger = LogManager.getLogger(SmbmsBillServiceImpl.class);
        @Resource
        private SmbmsBillMapper billDao;

        @Override
        public boolean add(SmbmsBill bill) {
            // TODO Auto-generated method stub
            boolean flag = false;
            try {
                if(billDao.add(bill) > 0) {
                    flag = true;
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
        public List<SmbmsBill> getBillList(SmbmsBill bill) {
            // TODO Auto-generated method stub
            List<SmbmsBill> billList = null;
            System.out.println("query productName ---- > " + bill.getProductName());
            System.out.println("query providerId ---- > " + bill.getProviderId());
            System.out.println("query isPayment ---- > " + bill.getIsPayment());

            try {
                billList = billDao.getBillList(bill);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }finally{
            }
            return billList;
        }

        @Override
        public boolean deleteBillById(String delId) {
            // TODO Auto-generated method stub
            boolean flag = false;
            try {
                if(billDao.deleteBillById(delId) > 0) {
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
        public SmbmsBill getBillById(String id) {
            // TODO Auto-generated method stub
            SmbmsBill bill = null;
            try{
                bill = billDao.getBillById(id);
            }catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
                bill = null;
            }finally{
            }
            return bill;
        }

        @Override
        public boolean modify(SmbmsBill bill) {
            // TODO Auto-generated method stub
            boolean flag = false;
            try {
                if(billDao.modify(bill) > 0) {
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
