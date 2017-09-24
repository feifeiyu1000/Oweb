package com.test.web.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by zdy on 2017/9/24.
 */
public class LoginServlet extends HttpServlet {
    public LoginServlet() {
    }



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        UsernamePasswordToken token = new UsernamePasswordToken(username,
                password);
        Subject subject = SecurityUtils.getSubject();
        String emsg = null;
        try {
            subject.login(token);
        } catch (UnknownAccountException uae) {
            emsg = "账号不存在";
        } catch (IncorrectCredentialsException ice) {
            emsg = "密码错误";
        } catch (LockedAccountException lae) {
            emsg = "账号已锁定";
        } catch (ExcessiveAttemptsException eae) {
            emsg = "重试次数超限";
        } catch (AuthenticationException ae) {
            emsg = "其他错误:" + ae.getMessage();
        }
        System.out.println(emsg);
        if(emsg == null) {
            req.getRequestDispatcher("index.jsp").forward(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
        }
    }
}
