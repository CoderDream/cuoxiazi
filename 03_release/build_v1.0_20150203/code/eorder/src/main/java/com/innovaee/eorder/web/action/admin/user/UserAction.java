/***********************************************
 * Filename        : UserAction.java 
 * Copyright      : Copyright (c) 2014
 * Company        : Innovaee
 * Created        : 11/27/2014
 ************************************************/

package com.innovaee.eorder.web.action.admin.user;

import com.innovaee.eorder.module.entity.Role;
import com.innovaee.eorder.module.entity.User;
import com.innovaee.eorder.module.service.UserRoleService;
import com.innovaee.eorder.module.service.UserService;
import com.innovaee.eorder.module.utils.MenuUtil;
import com.innovaee.eorder.module.vo.RoleLinkVo;
import com.innovaee.eorder.module.vo.UserDetailsVo;
import com.innovaee.eorder.module.vo.UserVO;
import com.innovaee.eorder.web.action.BaseAction;

import org.apache.struts2.ServletActionContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Title: UserAction
 * @Description: 用户Action（查找和删除）
 *
 * @version V1.0
 */
public class UserAction extends BaseAction {

    /** 对象序列化ID */
    private static final long serialVersionUID = 714474963089126216L;

    /** 用户ID */
    private String userId;

    /** 用户名称 */
    private String username;

    /** 密码 */
    private String password;

    /** 电话号码 */
    private String cellphone;

    /** 用户值对象列表 */
    private List<UserVO> uservos = new ArrayList<UserVO>();

    /** 用户服务类对象 */
    @Resource
    private UserService userService;

    /** 用户角色服务类对象 */
    @Resource
    private UserRoleService userRoleService;

    /** 已有的角色列表 */
    private List<Role> myRoles = new ArrayList<Role>();

    /** 剩余的角色列表 */
    private List<Role> leftRoles = new ArrayList<Role>();

    /** 已有的角色数组 */
    private String myRolesArray;

    /** 剩余的角色数组 */
    private String leftRolesArray;

    /**
     * 进入用户管理页面
     * 
     * @return
     */
    public String doUser() {
        // 更新页面数据
        refreshData();
        return SUCCESS;
    }

    /**
     * 加载单个用户信息
     * 
     * @return
     */
    public String doLoad() {
        if (null != userId) {
            User user = userService.loadUser(Integer.parseInt(userId));
            username = user.getUsername();
            password = user.getPassword();
            cellphone = user.getCellphone();

            // 加载用户角色信息
            myRoles = userRoleService.findRolesByUserId(Integer
                    .parseInt(userId));
            leftRoles = userRoleService.findLeftRolesByUserId(Integer
                    .parseInt(userId));
        }

        // 更新页面数据
        refreshData();
        return SUCCESS;
    }

    /**
     * 刷新页面数据
     */
    @SuppressWarnings("unchecked")
    public void refreshData() {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        request.setAttribute("menulist", MenuUtil.getRoleLinkVOList());
        List<RoleLinkVo> sessionMenulist = (List<RoleLinkVo>) session
                .getAttribute("menulist");
        this.setMenulist(sessionMenulist);
        uservos = userService.findAllUserVOs();
        UserDetailsVo userDetail = (UserDetailsVo) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        this.setLoginName(userDetail.getUser().getUsername());
    }

    /**
     * 删除用户
     * 
     * @return
     */
    public String doRemove() {
        if (null != userId) {
            userService.removeUser(Integer.parseInt(userId));
        }

        this.setMessage("删除成功！");

        // 清空删除时传入的Id，防止返回后页面取到
        this.setUserId("");
        // 更新页面数据
        refreshData();
        return SUCCESS;
    }

    public List<UserVO> getUservos() {
        return uservos;
    }

    public void setUservos(List<UserVO> uservos) {
        this.uservos = uservos;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public UserRoleService getUserRoleService() {
        return userRoleService;
    }

    public void setUserRoleService(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    public List<Role> getMyRoles() {
        return myRoles;
    }

    public void setMyRoles(List<Role> myRoles) {
        this.myRoles = myRoles;
    }

    public List<Role> getLeftRoles() {
        return leftRoles;
    }

    public void setLeftRoles(List<Role> leftRoles) {
        this.leftRoles = leftRoles;
    }

    public String getMyRolesArray() {
        return myRolesArray;
    }

    public void setMyRolesArray(String myRolesArray) {
        this.myRolesArray = myRolesArray;
    }

    public String getLeftRolesArray() {
        return leftRolesArray;
    }

    public void setLeftRolesArray(String leftRolesArray) {
        this.leftRolesArray = leftRolesArray;
    }

}