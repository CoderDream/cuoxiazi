/***********************************************
 * Filename        : BaseEntity.java
 * Copyright      : Copyright (c) 2014
 * Company        : Innovaee
 * Created        : 11/27/2014
 ************************************************/

package com.innovaee.eorder.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.innovaee.eorder.util.TimestampAdapter;

/**
 * @Title: BaseEntity
 * @Description: 实体类的基类
 * 
 * @version V1.0
 */
public abstract class BaseEntity implements Serializable {

    /** 对象序列化ID */
    private static final long serialVersionUID = 5575073954535097972L;

   

    /**
     * 返回主键
     * 
     * @return 主键
     */
    public abstract Serializable getPK();

    public String toString() {
        return ToStringBuilder.reflectionToString(this,
                ToStringStyle.SIMPLE_STYLE);
    }

    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

}