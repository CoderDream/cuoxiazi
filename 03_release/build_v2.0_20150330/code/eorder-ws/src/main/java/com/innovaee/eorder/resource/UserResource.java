/***********************************************
 * Filename        : UserResource.java
 * Copyright      : Copyright (c) 2014
 * Company        : Innovaee
 * Created        : 11/27/2014
 ************************************************/
package com.innovaee.eorder.resource;

import com.innovaee.eorder.entity.MemberShip;
import com.innovaee.eorder.entity.Order;
import com.innovaee.eorder.entity.User;
import com.innovaee.eorder.exception.UserNotFoundException;
import com.innovaee.eorder.service.UserService;
import com.innovaee.eorder.support.Constants;
import com.innovaee.eorder.vo.OrderVO;
import com.innovaee.eorder.vo.UserVO;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @Title: UserResource
 * @Description: 用户资源
 * @version V1.0
 */
@Path("/users")
public class UserResource {

    private Logger logger = Logger.getLogger(this.getClass());

    /** 用户服务类对象 */
    private UserService userService;

    /**
     * 根据手机号码查询用户信息
     * 
     * @param cellphone
     *            手机号码
     * @return 用户值对象
     */
    @GET
    @Path("/{cellphone}")
    @Scope("request")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> getUserByCellphone(
            @PathParam("cellphone") String cellphone) {
        logger.info("[REST_CALL= getUserById, cellphone=" + cellphone + "]");

        Map<String, Object> result = new HashMap<String, Object>();
        User user = null;

        try {
            user = userService.getUserByCellphone(cellphone);
            MemberShip memberShip = user.getMemberShip();
            UserVO userVO = new UserVO();

            if (null != user) {
                userVO.setId(user.getId());
                userVO.setUsername(user.getUsername());
                userVO.setCellphone(user.getCellphone());
            }

            if (null != memberShip) {
                // 设置用户等级名称
                userVO.setLevelName(memberShip.getLevel().getLevelName());
                // 设置用户折扣信息
                userVO.setDiscount(memberShip.getLevel().getDiscount());
            } else {
                userVO.setLevelName(Constants.DEFAULT_USR_LEVEL);
                userVO.setDiscount(10f);
            }

            result.put("user", userVO);
        } catch (UserNotFoundException exception) {
            result.put("exception", exception.getMessage());
        }

        return result;
    }

    /**
     * 根据手机号码查询该用户的订单信息
     * 
     * @param cellphone
     *            手机号码
     * @return 订单值对象列表
     */
    @GET
    @Path("/{cellphone}/orders")
    @Scope("request")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> getOrderesByCellphone(
            @PathParam("cellphone") String cellphone) {

        User user = null;
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            // 1. 通过手机号码查找用户信息
            user = userService.getUserByCellphone(cellphone);
            List<OrderVO> orderVOs = new ArrayList<OrderVO>();
            // 2. 根据用户ID查找用户的订单信息
            List<Order> orders = new ArrayList<Order>(user.getOrders());
            for (Order order : orders) {
                OrderVO orderVO = new OrderVO();
                // 将订单对象的信息复制到订单值对象中，用于返回给客户端
                orderVO.setId(order.getId());
                orderVO.setTotalPrice(order.getTotalPrice());
                orderVO.setCreateAt(order.getCreateDate());
                orderVOs.add(orderVO);
            }
            
            result.put("orders", orderVOs);
        } catch (UserNotFoundException exception) {
            result.put("exception", exception.getMessage());
        }

        return result;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public UserResource() {
        super();
    }
}