/***********************************************
 * Filename        : OrderResource.java
 * Copyright      : Copyright (c) 2014
 * Company        : Innovaee
 * Created        : 11/27/2014
 ************************************************/
package com.innovaee.eorder.resource;

import com.innovaee.eorder.entity.Order;
import com.innovaee.eorder.entity.OrderItem;
import com.innovaee.eorder.service.OrderService;
import com.innovaee.eorder.vo.OrderItemVO;

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
 * @Title: OrderResource
 * @Description: 订单资源
 * @version V1.0
 */
@Path("/orders")
public class OrderResource {

    private Logger logger = Logger.getLogger(this.getClass());

    /** 订单服务类对象 */
    private OrderService orderService;

    /**
     * 根据orderId查询订单明细
     * 
     * @param orderId
     *            订单ID
     * @return 订单明细列表
     */
    @GET
    @Path("/{orderId}")
    @Scope("request")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, List<OrderItemVO>> getOrderItemsByOrderId(
            @PathParam("orderId") Integer orderId) {
        logger.info("[REST_CALL= getOrderItemsByOrderId, orderId=" + orderId
                + "]");

        List<OrderItemVO> orderItemVOs = new ArrayList<OrderItemVO>();
        Order order = orderService.getOrderById(orderId);
        List<OrderItem> orderItems = new ArrayList<OrderItem>(
                order.getOrderItems());
        OrderItemVO orderItemVO = null;
        for (OrderItem orderItem : orderItems) {
            orderItemVO = new OrderItemVO();
            orderItemVO.setId(orderItem.getId());
            orderItemVO.setDishId(orderItem.getDish().getId());
            orderItemVO.setDishName(orderItem.getDish().getName());
            orderItemVO.setDishPrice(orderItem.getDish().getPrice());
            orderItemVO.setDishPicture(orderItem.getDish().getPicPath());
            orderItemVO.setDishAmount(orderItem.getDishAmount());
            orderItemVOs.add(orderItemVO);
        }

        Map<String, List<OrderItemVO>> result = new HashMap<String, List<OrderItemVO>>();
        result.put("orders", orderItemVOs);

        return result;
    }

    public OrderService getOrderService() {
        return orderService;
    }

    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    public OrderResource() {
        super();
    }
}