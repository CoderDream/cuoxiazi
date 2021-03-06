/***********************************************
 * Filename        : LoginAction.java
 * Copyright      : Copyright (c) 2014
 * Company        : Innovaee
 * Created        : 11/27/2014
 ************************************************/

package com.innovaee.eorder.web.action.login;

import com.innovaee.eorder.module.utils.MenuUtil;
import com.innovaee.eorder.web.action.BaseAction;

import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Title: LoginAction
 * @Description: 登录Action
 *
 * @version V1.0
 */
public class LoginAction extends BaseAction {

    /** 对象序列化ID */
    private static final long serialVersionUID = 6040009827802629154L;

    /**
     * 进入登录页面
     * 
     * @return
     */
    public String login() {
        return SUCCESS;
    }

    /**
     * 进入主页
     * 
     * @return
     */
    public String doLogin() {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        request.setAttribute("menulist", MenuUtil.getRoleLinkVOList());
        session.setAttribute("menulist", MenuUtil.getRoleLinkVOList());

        return SUCCESS;
    }

    /**
     * 退出系统
     * 
     * @return
     */
    public String doLogout() {
        return SUCCESS;
    }

}