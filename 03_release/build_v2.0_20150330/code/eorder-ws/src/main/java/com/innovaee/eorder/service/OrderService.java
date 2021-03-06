/***********************************************
 * Filename       : OrderService.java
 * Copyright      : Copyright (c) 2014
 * Company        : Innovaee
 * Created        : 11/27/2014
 ************************************************/
package com.innovaee.eorder.service;

import com.innovaee.eorder.entity.Order;
import com.innovaee.eorder.exception.DishNotFoundException;
import com.innovaee.eorder.exception.InvalidPageSizeException;
import com.innovaee.eorder.exception.PageIndexOutOfBoundExcpeiton;
import com.innovaee.eorder.exception.UserNotFoundException;
import com.innovaee.eorder.exception.ZeroOrderItemException;
import com.innovaee.eorder.vo.NewOrderVO;

import java.util.List;
import java.util.Map;

/**
 * @Title: OrderService
 * @Description: 订单服务
 * @version V1.0
 */
public interface OrderService {

    /**
     * 根据订单ID得到订单
     * 
     * @param orderId
     *            订单ID
     * @return 订单
     */
    public Order getOrderById(Long orderId);

    /**
     * 根据用户ID得到订单列表
     * 
     * @param memberId
     *            用户ID
     * @return 订单列表
     */
    public List<Order> getOrdersByMemberId(Long memberId);

    /**
     * 创建新订单
     * 
     * @param newOrder
     *            新订单信息
     * @return 如果创建成功，返回新订单id；如果失败，返回-1
     * @throws UserNotFoundException
     * @throws ZeroOrderItemException
     * @throws DishNotFoundException
     */
    public Long placeOrder(NewOrderVO newOrder) throws UserNotFoundException,
            DishNotFoundException,
            com.innovaee.eorder.exception.ZeroOrderItemException;

    /**
     * 返回桌号和当前订单的关联关系，如果当前桌号有订单， 则在Map中取得Order；反之，则为NULL
     * 
     * @return
     */
    public Map<Integer, Order> getTableStatus();

    /**
     * 根据查询条件查找订单
     * 
     * @param orderCriteria
     * @param curPage
     * @param pageSize
     * @return
     * @throws InvalidPageSizeException 
     * @throws PageIndexOutOfBoundExcpeiton 
     */
    public List<Order> queryOrders(final NewOrderVO orderCriteria,
            final int curPage, final int pageSize) throws InvalidPageSizeException, PageIndexOutOfBoundExcpeiton;

    /**
     * 根据查询条件查找订单分页总数
     * 
     * @param orderCriteria
     * @param pageSize
     * @return
     * @throws InvalidPageSizeException 
     */
    public int queryOrdersPageCount(final NewOrderVO orderCriteria,
            final int pageSize) throws InvalidPageSizeException;
    
    /**
     * 根据查询条件查找订单总数
     * @param orderCriteria
     * @return
     */
    public int getOrderCount(final NewOrderVO orderCriteria);
}