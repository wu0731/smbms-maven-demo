package com.smbms.controller.user;

import com.alibaba.fastjson.JSONArray;
import com.mysql.cj.util.StringUtils;
import com.smbms.pojo.SmbmsRole;
import com.smbms.pojo.SmbmsUser;
import com.smbms.pojo.UserCondition;
import com.smbms.service.SmbmsRoleService;
import com.smbms.service.SmbmsUserService;
import com.smbms.tools.Constants;
import com.smbms.tools.PageSupport;
import org.springframework.beans.BeanUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: smbms-maven-demo
 * @description: 用户操作控制
 * @author: wu
 * @create: 2020-05-30 14:51
 **/
@Controller
@RequestMapping(value = "/userCon")
public class SmbmsUserController {
    @Resource
    private SmbmsUserService userService;
    @Resource
    private SmbmsRoleService roleService;

    @RequestMapping(value = "/userAdd")
    public String add(UserCondition userCondition, Model model, HttpServletRequest request)
            throws ParseException {
        //进行数据类型转换
        SmbmsUser user=new SmbmsUser();
        BeanUtils.copyProperties(userCondition,user);
        //处理不能被复制的字段
        user.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(userCondition.getBirthday()));
        SmbmsUser  logUser = (SmbmsUser) request.getSession().getAttribute(Constants.USER_SESSION);
        if (logUser == null){
            return "redirect:/user/login";
        }
        user.setCreatedBy(logUser.getId());
        user.setCreationDate(new Date());
        if (userService.add(user)){
            return "redirect:/userCon/userQuery";
        }
        return "/userAdd";
    }

    @RequestMapping(value = "/userQuery")
    public String query(HttpServletRequest request) {
        //查询用户列表
        String queryUserName = request.getParameter("queryname");
        String temp = request.getParameter("queryUserRole");
        String pageIndex = request.getParameter("pageIndex");
        int queryUserRole = 0;

        List<SmbmsUser> userList = null;
        //设置页面容量
        int pageSize = Constants.pageSize;
        //当前页码
        int currentPageNo = 1;
        /**
         * http://localhost:8090/SMBMS/userlist.do
         * ----queryUserName --NULL
         * http://localhost:8090/SMBMS/userlist.do?queryname=
         * --queryUserName ---""
         */
        System.out.println("queryUserName servlet--------"+queryUserName);
        System.out.println("queryUserRole servlet--------"+queryUserRole);
        System.out.println("query pageIndex--------- > " + pageIndex);
        if(queryUserName == null){
            queryUserName = "";
        }
        if(temp != null && !temp.equals("")){
            queryUserRole = Integer.parseInt(temp);
        }

        if(pageIndex != null){
            try{
                currentPageNo = Integer.valueOf(pageIndex);
            }catch(NumberFormatException e){
                return "redirect:error";
            }
        }
        //总数量（表）
        int totalCount	= userService.getUserCount(queryUserName,queryUserRole);
        //总页数
        PageSupport pages=new PageSupport();
        pages.setCurrentPageNo(currentPageNo);
        pages.setPageSize(pageSize);
        pages.setTotalCount(totalCount);

        int totalPageCount = pages.getTotalPageCount();

        //控制首页和尾页
        if(currentPageNo < 1){
            currentPageNo = 1;
        }else if(currentPageNo > totalPageCount){
            currentPageNo = totalPageCount;
        }


        userList = userService.getUserList(queryUserName,queryUserRole,currentPageNo, pageSize);
        request.setAttribute("userList", userList);
        List<SmbmsRole> roleList = null;
        try {
            roleList = roleService.getRoleList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.setAttribute("roleList", roleList);
        request.setAttribute("queryUserName", queryUserName);
        request.setAttribute("queryUserRole", queryUserRole);
        request.setAttribute("totalPageCount", totalPageCount);
        request.setAttribute("totalCount", totalCount);
        request.setAttribute("currentPageNo", currentPageNo);
        return "userlist";
    }

    @RequestMapping(value = "/userDel",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String delUser(HttpServletRequest request) {
        String id = request.getParameter("uid");
        Integer delId = 0;
        try{
            delId = Integer.parseInt(id);
        }catch (Exception e) {
            // TODO: handle exception
            delId = 0;
        }
        HashMap<String, String> resultMap = new HashMap<String, String>();
        if(delId <= 0){
            resultMap.put("delResult", "notexist");
        }else{
            if(userService.deleteUserById(delId)){
                resultMap.put("delResult", "true");
            }else{
                resultMap.put("delResult", "false");
            }
        }
        //把resultMap转换成json对象输出
        return JSONArray.toJSONString(resultMap);
    }

    @RequestMapping(value = "/userById")
    public String getUserById(HttpServletRequest request,String url) {
        String id = request.getParameter("uid");
        if(!StringUtils.isNullOrEmpty(id)){
            //调用后台方法得到user对象
            SmbmsUser user = userService.getUserById(id);
            request.setAttribute("user", user);
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
            request.setAttribute("userBirthday",simpleDateFormat.format(user.getBirthday()));
        }
        return url;
    }

    @RequestMapping(value = "/userModify")
    public String modify(HttpServletRequest request,UserCondition userCondition) throws ParseException {
        String id = request.getParameter("uid");
        SmbmsUser user=new SmbmsUser();
        BeanUtils.copyProperties(userCondition,user);
        //处理不能被复制的字段
        user.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(userCondition.getBirthday()));
        SmbmsUser  logUser = (SmbmsUser) request.getSession().getAttribute(Constants.USER_SESSION);
        if (logUser == null){
            return "redirect:/user/login";
        }
        user.setCreatedBy(logUser.getId());
        user.setCreationDate(new Date());
        user.setId(Integer.valueOf(id));
        user.setModifyBy(((SmbmsUser)request.getSession().getAttribute(Constants.USER_SESSION)).getId());
        user.setModifyDate(new Date());
        if(userService.modify(user)){
            return "redirect:/userCon/userQuery";
        }else{
            return "usermodify";
        }

    }

    @RequestMapping(value = "/userRoleList",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String getRoleList(){
        List<SmbmsRole> roleList  = null;
        try {
            roleList = roleService.getRoleList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //把roleList转换成json对象输出
        return JSONArray.toJSONString(roleList);
    }

    @RequestMapping(value = "/userPwdById",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String getPwdByUserId(HttpServletRequest request) {
        Object o = request.getSession().getAttribute(Constants.USER_SESSION);
        String oldpassword = request.getParameter("oldpassword");
        Map<String, String> resultMap = new HashMap<String, String>();
        if(null == o ){//session过期
            resultMap.put("result", "sessionerror");
        }else if(StringUtils.isNullOrEmpty(oldpassword)){//旧密码输入为空
            resultMap.put("result", "error");
        }else{
            String sessionPwd = ((SmbmsUser)o).getUserPassword();
            if(oldpassword.equals(sessionPwd)){
                resultMap.put("result", "true");
            }else{//旧密码输入不正确
                resultMap.put("result", "false");
            }
        }
        return JSONArray.toJSONString(resultMap);
    }

    @RequestMapping(value = "/userUpdatePwd")
    public String updatePwd(HttpServletRequest request) {
        Object o = request.getSession().getAttribute(Constants.USER_SESSION);
        String newpassword = request.getParameter("newpassword");
        boolean flag = false;
        if(o != null && !StringUtils.isNullOrEmpty(newpassword)){
            flag = userService.updatePwd(((SmbmsUser)o).getId(),newpassword);
            if(flag){
                request.setAttribute(Constants.SYS_MESSAGE, "修改密码成功,请退出并使用新密码重新登录！");
                request.getSession().removeAttribute(Constants.USER_SESSION);//session注销
            }else{
                request.setAttribute(Constants.SYS_MESSAGE, "修改密码失败！");
            }
        }else{
            request.setAttribute(Constants.SYS_MESSAGE, "修改密码失败！");
        }
        return "pwdmodify";
    }

    @RequestMapping(value = "/userCodeExist",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String userCodeExist(HttpServletRequest request){
        //判断用户账号是否可用
        String userCode = request.getParameter("userCode");
        HashMap<String, String> resultMap = new HashMap<String, String>();
        if(StringUtils.isNullOrEmpty(userCode)){
            //userCode == null || userCode.equals("")
            resultMap.put("userCode", "exist");
        }else{
            SmbmsUser user = userService.selectUserCodeExist(userCode);
            if(null != user){
                resultMap.put("userCode","exist");
            }else{
                resultMap.put("userCode", "notexist");
            }
        }
        //把resultMap转为json字符串以json的形式输出
        return JSONArray.toJSONString(resultMap);
    }

    @RequestMapping(value = "/AjaxSelectUser",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String AjaxSelectUser(Model model, @RequestParam(value = "uid") String Uid){
        if(!StringUtils.isNullOrEmpty(Uid)){
            //调用后台方法得到user对象
            SmbmsUser user = userService.getUserById(Uid);
            System.out.println("出生日期："+user.getBirthday());
            model.addAttribute("user", user);
            /*SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
            model.addAttribute("userBirthday",simpleDateFormat.format(user.getBirthday()));*/
            System.out.println("json数据处理后："+JSONArray.toJSONString(model));
        }
        return JSONArray.toJSONString(model);
    }
}
