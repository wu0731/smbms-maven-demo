package com.smbms.controller.bill;

import com.alibaba.fastjson.JSONArray;
import com.mysql.cj.util.StringUtils;
import com.smbms.pojo.SmbmsBill;
import com.smbms.pojo.SmbmsProvider;
import com.smbms.pojo.SmbmsUser;
import com.smbms.service.SmbmsBillService;
import com.smbms.service.SmbmsProviderService;
import com.smbms.tools.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @program: smbms-maven-demo
 * @description: 订单控制
 * @author: wu
 * @create: 2020-05-30 14:27
 **/
@Controller
@RequestMapping(value = "/bill")
public class SmbmsBillController {
    @Resource
    private SmbmsProviderService providerService;
    @Resource
    private SmbmsBillService billService;

    @RequestMapping(value = "/getProviderlist")
    public String getProviderlist(Model model) {
        List<SmbmsProvider> providerList = providerService.getProviderList("","");
        model.addAttribute(providerList);
        return "frame";
    }

    @RequestMapping(value = "/getBillById")
    public String getBillById(HttpServletRequest request, Model model, String url) {
        String id = request.getParameter("billid");
        if(!StringUtils.isNullOrEmpty(id)){
            SmbmsBill bill = null;
            bill = billService.getBillById(id);
            model.addAttribute("bill", bill);
        }
        return url;
    }

    @RequestMapping(value = "/modify")
    public String modify(HttpServletRequest request) {
        System.out.println("modify===============");
        String id = request.getParameter("id");
        String productName = request.getParameter("productName");
        String productDesc = request.getParameter("productDesc");
        String productUnit = request.getParameter("productUnit");
        String productCount = request.getParameter("productCount");
        String totalPrice = request.getParameter("totalPrice");
        String providerId = request.getParameter("providerId");
        String isPayment = request.getParameter("isPayment");

        SmbmsBill bill = new SmbmsBill();
        bill.setId(Integer.valueOf(id));
        bill.setProductName(productName);
        bill.setProductDesc(productDesc);
        bill.setProductUnit(productUnit);
        bill.setProductCount(new BigDecimal(productCount).setScale(2,BigDecimal.ROUND_DOWN));
        bill.setIsPayment(Integer.parseInt(isPayment));
        bill.setTotalPrice(new BigDecimal(totalPrice).setScale(2,BigDecimal.ROUND_DOWN));
        bill.setProviderId(Integer.parseInt(providerId));

        bill.setModifyBy(((SmbmsUser)request.getSession().getAttribute(Constants.USER_SESSION)).getId());
        bill.setModifyDate(new Date());
        boolean flag = false;
        flag = billService.modify(bill);
        if(flag){
            return "redirect:/bill/query";
        }else{
            return "billmodify";
        }
    }

    @RequestMapping(value = "/delBill")
    public void delBill(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("billid");
        HashMap<String, String> resultMap = new HashMap<String, String>();
        if(!StringUtils.isNullOrEmpty(id)){
            boolean flag = billService.deleteBillById(id);
            if(flag){//删除成功
                resultMap.put("delResult", "true");
            }else{//删除失败
                resultMap.put("delResult", "false");
            }
        }else{
            resultMap.put("delResult", "notexit");
        }
        //把resultMap转换成json对象输出
        response.setContentType("application/json");
        PrintWriter outPrintWriter = response.getWriter();
        outPrintWriter.write(JSONArray.toJSONString(resultMap));
        outPrintWriter.flush();
        outPrintWriter.close();
    }

    @RequestMapping(value = "/add")
    public String add(HttpServletRequest request){
        String billCode = request.getParameter("billCode");
        String productName = request.getParameter("productName");
        String productDesc = request.getParameter("productDesc");
        String productUnit = request.getParameter("productUnit");

        String productCount = request.getParameter("productCount");
        String totalPrice = request.getParameter("totalPrice");
        String providerId = request.getParameter("providerId");
        String isPayment = request.getParameter("isPayment");

        SmbmsBill bill = new SmbmsBill();
        bill.setBillCode(billCode);
        bill.setProductName(productName);
        bill.setProductDesc(productDesc);
        bill.setProductUnit(productUnit);
        bill.setProductCount(new BigDecimal(productCount).setScale(2,BigDecimal.ROUND_DOWN));
        bill.setIsPayment(Integer.parseInt(isPayment));
        bill.setTotalPrice(new BigDecimal(totalPrice).setScale(2,BigDecimal.ROUND_DOWN));
        bill.setProviderId(Integer.parseInt(providerId));
        bill.setCreatedBy(((SmbmsUser)request.getSession().getAttribute(Constants.USER_SESSION)).getId());
        bill.setCreationDate(new Date());
        boolean flag = false;
        flag = billService.add(bill);
        System.out.println("add flag -- > " + flag);
        if(flag){
            return  "redirect:/bill/query";
        }else{
            return "billadd";
        }
    }

    /**订单管理*/
    @RequestMapping(value = "/query")
    public String query(Model model,HttpServletRequest request){
        List<SmbmsProvider> providerList = new ArrayList<SmbmsProvider>();
        providerList = providerService.getProviderList("","");
        model.addAttribute("providerList", providerList);

        String queryProductName = request.getParameter("queryProductName");
        String queryProviderId = request.getParameter("queryProviderId");
        String queryIsPayment = request.getParameter("queryIsPayment");
        if(StringUtils.isNullOrEmpty(queryProductName)){
            queryProductName = "";
        }

        List<SmbmsBill> billList = new ArrayList<SmbmsBill>();
        SmbmsBill bill = new SmbmsBill();
        if(StringUtils.isNullOrEmpty(queryIsPayment)){
            bill.setIsPayment(0);
        }else{
            bill.setIsPayment(Integer.parseInt(queryIsPayment));
        }

        if(StringUtils.isNullOrEmpty(queryProviderId)){
            bill.setProviderId(0);
        }else{
            bill.setProviderId(Integer.parseInt(queryProviderId));
        }
        bill.setProductName(queryProductName);
        billList = billService.getBillList(bill);
        model.addAttribute("billList", billList);
        model.addAttribute("queryProductName", queryProductName);
        model.addAttribute("queryProviderId", queryProviderId);
        model.addAttribute("queryIsPayment", queryIsPayment);
        return "billlist";
    }
}
