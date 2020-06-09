package com.smbms.controller.user;

import com.smbms.pojo.SmbmsUser;
import com.smbms.service.SmbmsUserService;
import com.smbms.tools.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @program: smbms-maven-demo
 * @description: 登录控制
 * @author: wu
 * @create: 2020-05-30 13:52
 **/
@Controller
@RequestMapping(value = "/user")
public class LoginSmbmsUserController {
    private static Logger logger= LogManager.getLogger(LoginSmbmsUserController.class);
    @Resource
    private SmbmsUserService userService;

    /**
     * 登录方法
     */
    @RequestMapping(value = "/login")
    public String dologin(String userCode,String userPassword,
                          HttpServletRequest request){
        logger.info("进入login.do方法");
        System.out.println("进入login.do方法");
        /*调用service方法*/
        SmbmsUser user = userService.login(userCode,userPassword);
        if (user!=null){
            /*跳转页面（frame.jsp）*/
            request.getSession().setAttribute(Constants.USER_SESSION,user);
            return "redirect:/user/main";
        }else {
            /*页面跳转(login.jsp)*/
            request.setAttribute("error","用户名或者密码不正确");
            return "login";
        }
    }
    @RequestMapping(value = "/main")
    public String main(HttpServletRequest request){
        if (request.getSession().getAttribute(Constants.USER_SESSION)==null){
            return "redirect:/user/login";
        }
        return "frame";
    }
    @RequestMapping(value = "/logout")
    public String logout(HttpServletRequest request){
        request.getSession().removeAttribute(Constants.USER_SESSION);
        return "login";

    }

    @ExceptionHandler(value = {RuntimeException.class})
    public String wxceptionHandler(RuntimeException e, Model model){
        model.addAttribute("e",e);
        return "error";
    }
}
