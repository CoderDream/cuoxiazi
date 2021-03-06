/***********************************************
 * Filename        : FloatConverter.java 
 * Copyright      : Copyright (c) 2014
 * Company        : Innovaee
 * Created        : 04/04/2015
 ************************************************/

package com.innovaee.eorder.utils;

import java.util.Map;

import org.apache.struts2.util.StrutsTypeConverter;

/**
 * @Title: FloatConverter
 * @Description: 表单浮点字段转换器
 *
 * @version V1.0
 */
public class FloatConverter extends StrutsTypeConverter {

    @SuppressWarnings("rawtypes")
    @Override
    public Object convertFromString(Map context, String[] values, Class toType) {
        Float result = 0.00f;

        if (Float.class == toType || float.class == toType) {
            String strVal = values[0];

            try {
                if (null != strVal && !"".equals(strVal)) {
                    result = Float.parseFloat(strVal);
                }
            } catch (Exception exception) {
                result = -0.01f;
            }
        }

        return result;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public String convertToString(Map context, Object val) {
        return "" + val;
    }

}
