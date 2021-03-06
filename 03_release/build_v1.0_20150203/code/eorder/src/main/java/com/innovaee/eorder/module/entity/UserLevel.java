/***********************************************
 * Filename        : UserLevel.java 
 * Copyright      : Copyright (c) 2014
 * Company        : Innovaee
 * Created        : 11/27/2014
 ************************************************/

package com.innovaee.eorder.module.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Title: UserLevel
 * @Description: 用户等级实体
 *
 * @version V1.0
 */
@Entity
@Table(name = "t_user_level")
public class UserLevel extends BaseEntity {

    /** 对象序列化ID */
    private static final long serialVersionUID = 2189941376177920282L;

    /**
     * 返回主键
     * 
     * @return 主键
     */
    @Override
    public Serializable getPK() {
        return levelId;
    }

    /** 等级ID */
    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer levelId;

    /** 等级名称 */
    @Column(name = "LEVEL_NAME")
    private String levelName;

    /** 折扣 */
    @Column(name = "DISCOUNT")
    private Float discount;

    /** 等级积分 */
    @Column(name = "level_score")
    private Integer levelScore;

    /** 等级状态 */
    @Column(name = "Level_STATUS")
    private Boolean levelStatus;

    public Integer getLevelId() {
        return levelId;
    }

    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public Float getDiscount() {
        return discount;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }

    public Integer getLevelScore() {
        return levelScore;
    }

    public void setLevelScore(Integer levelScore) {
        this.levelScore = levelScore;
    }

    public Boolean getLevelStatus() {
        return levelStatus;
    }

    public void setLevelStatus(Boolean levelStatus) {
        this.levelStatus = levelStatus;
    }

    /**
     * @return 返回该对象的字符串表示
     */
    @Override
    public String toString() {
        return "Level [levelId=" + levelId + ", levelName=" + levelName
                + ", discount=" + discount + ", levelScore=" + levelScore
                + ", levelStatus=" + levelStatus + ", createAt="
                + this.getCreateAt() + ", updateAt=" + this.getUpdateAt()
                + ", createAt=" + super.getCreateAt() + ", updateAt="
                + super.getUpdateAt() + "]";
    }

}