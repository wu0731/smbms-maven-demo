package com.smbms.interceptor;

import com.smbms.tools.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * session校验拦截器实现处理器拦截器
 */
public class SysInterceptor implements HandlerInterceptor {
    private Logger logger = LogManager.getLogger(SysInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        logger.info("进入session校验拦截器实现处理器拦截器");
        //判断当前session中是否有数据
        Object attribute = request.getSession().getAttribute(Constants.USER_SESSION);
        if (attribute != null) {
            //有数据不拦截
            logger.info("用户处于登录状态");
            return true;
        }else {
            response.sendRedirect("../error.jsp");
            return false;
        }

    }
}
