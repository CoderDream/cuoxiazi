package com.innovaee.eorder.resources;

import org.junit.Test;

/**
 * @Title: OrderResourceTest
 * @Description: 订单资源测试类
 * @version V1.0
 */
public class OrderResourceTest extends BaseTestCase {

    /**
     * 根据orderId查询订单明细
     */
    @Test
    public void getOrderItemsByOrderIdOne() {
        String orderId = "1";
        target = client.target(SERVER_URI + "/orders/" + orderId);
        response = target.request().get();
        String orderitems = response.readEntity(String.class);
        LOGGER.debug("orderitems: " + orderitems);
    }

    /**
     * 根据orderId查询订单明细
     */
    @Test
    public void getOrderItemsByOrderIdTwo() {
        String orderId = "2";
        target = client.target(SERVER_URI + "/orders/" + orderId);
        response = target.request().get();
        String orderitems = response.readEntity(String.class);
        LOGGER.debug("orderitems: " + orderitems);
    }

}