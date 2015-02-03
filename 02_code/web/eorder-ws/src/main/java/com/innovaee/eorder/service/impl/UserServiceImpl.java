/***********************************************
 * Filename       : UserServiceImpl.java
 * Copyright      : Copyright (c) 2014
 * Company        : Innovaee
 * Created        : 11/27/2014
 ************************************************/
package com.innovaee.eorder.service.impl;

import com.innovaee.eorder.dao.UserDao;
import com.innovaee.eorder.entity.User;
import com.innovaee.eorder.service.UserService;

/**
 * @Title: UserServiceImpl
 * @Description: 用户服务实现类
 * @version V1.0
 */
public class UserServiceImpl implements UserService {

    /** 用户数据访问实现类对象 */
    private UserDao userDao;

    /**
     * 根据用户ID查找用户
     * 
     * @param id
     *            用户ID
     * @return 用户实体
     */
    @Override
    public User getUserById(Integer userId) {
        return userDao.get(userId);
    }

    /**
     * 根据手机号码得到用户
     * 
     * @param cellphone
     *            手机号码
     * @return 用户
     */
    @Override
    public User getUserByCellphone(String cellphone) {
        return userDao.getUserByCellphone(cellphone);
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }
}