package com.smbms.controller.providerController;

import com.alibaba.fastjson.JSONArray;
import com.mysql.cj.util.StringUtils;
import com.smbms.pojo.ProviderCondition;
import com.smbms.pojo.SmbmsProvider;
import com.smbms.pojo.SmbmsUser;
import com.smbms.service.SmbmsProviderService;
import com.smbms.tools.Constants;
import org.springframework.beans.BeanUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @program: smbms-maven-demo
 * @description: 供应商控制
 * @author: wu
 * @create: 2020-05-30 14:57
 **/
@Controller
public class SmbmsProviderController {
    @Resource
    private SmbmsProviderService providerService;

    @RequestMapping(value = "/delProvider",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String delProvider(String proid) {
        HashMap<String, String> resultMap = new HashMap<String, String>();
        if(!StringUtils.isNullOrEmpty(proid)){
            int flag = providerService.deleteProviderById(proid);
            if(flag == 0){//删除成功
                resultMap.put("delResult", "true");
            }else if(flag == -1){//删除失败
                resultMap.put("delResult", "false");
            }else if(flag > 0){//该供应商下有订单，不能删除，返回订单数
                resultMap.put("delResult", String.valueOf(flag));
            }
        }else{
            resultMap.put("delResult", "notexit");
        }
        //把resultMap转换成json对象输出
        return JSONArray.toJSONString(resultMap);
    }

    @RequestMapping(value = "/providerModify")
    public String modify(String id,HttpSession session, ProviderCondition providerCondition){
        SmbmsProvider provider = new SmbmsProvider();
        BeanUtils.copyProperties(providerCondition,provider);
        provider.setId(Integer.valueOf(id));
        provider.setModifyBy(((SmbmsUser)session.getAttribute(Constants.USER_SESSION)).getId());
        provider.setModifyDate(new Date());
        boolean flag = false;
        flag = providerService.modify(provider);
        if(flag){
            return "redirect:/ProviderQuery";
        }else{
            return "providermodify";
        }
    }

    @RequestMapping(value = "/getProviderById/{url}/{proid}")
    public String getProviderById(Model model,
                                  @PathVariable String proid, @PathVariable String url) {
        if(!StringUtils.isNullOrEmpty(proid)){
            SmbmsProvider provider = null;
            provider = providerService.getProviderById(proid);
            model.addAttribute("provider", provider);
        }
        return url;
    }

    @RequestMapping(value = "/ProviderAdd",method = RequestMethod.POST,produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String add(@RequestParam(value = "a_idPicPath",required = false) MultipartFile[] attchs,
                      ProviderCondition providerCondition, Model model, HttpSession session){
        //定义一个文件上传的相对路径
        String filePath=null;
        //先处理文件上传
        //1、先获取文件上传路径
        String realPath = session.getServletContext().getRealPath("fileUpload");
        //2、判断当前上传的文件是否为空
        for (int i = 0; i < attchs.length ; i++) {
            MultipartFile attch=attchs[i];
            if (attch != null) {
                //3、判断文件大小是否符合
                if (attch.getSize()>500000){
                    model.addAttribute("error","文件过大");
                    return "provideradd";
                }
                //4、判断文件类型是否符合
                String originalFilename = attch.getOriginalFilename();
                //获取上传文件类型
                String fileType = originalFilename.substring(originalFilename.lastIndexOf("."));
                //创建一个支持的类型集合
                List<String> fileTypeList = Arrays.asList(".jpg","jpeg", ".png", ".gif");
                if (!fileTypeList.contains(fileType)) {
                    model.addAttribute("error","文件类型不支持");
                    return "provideradd";
                }
                //处理文件名
                String fileName = System.currentTimeMillis() +String.valueOf(Math.abs(new Random().nextLong()))+fileType;
                //判断目录是否存在
                File file = new File(realPath + File.separator + fileName);
                //如果文件不存在就创建
                if (!file.exists()) {
                    file.mkdirs();
                }
                try {
                    attch.transferTo(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                filePath = "fileUpload" + File.separator + fileName;
            }
        }
        SmbmsProvider provider = new SmbmsProvider();
        BeanUtils.copyProperties(providerCondition,provider);
        provider.setCreatedBy(((SmbmsUser)session.getAttribute(Constants.USER_SESSION)).getId());
        provider.setCreationDate(new Date());
        //添加证件照路径
        provider.setIdPicPath(filePath);
        boolean flag = false;
        flag = providerService.add(provider);
        if(flag){
            return "redirect:/ProviderQuery";
        }else{
            return "provideradd";
        }
    }

    @RequestMapping(value = "/ProviderQuery")
    public String query(Model model, String queryProName,String queryProCode){
        if(StringUtils.isNullOrEmpty(queryProName)){
            queryProName = "";
        }
        if(StringUtils.isNullOrEmpty(queryProCode)){
            queryProCode = "";
        }
        List<SmbmsProvider> providerList=providerService.getProviderList(queryProName,queryProCode);

        model.addAttribute("providerList",providerList);
        model.addAttribute("queryProName",queryProName);
        model.addAttribute("queryProCode",queryProCode);
        return "providerlist";
    }
}
