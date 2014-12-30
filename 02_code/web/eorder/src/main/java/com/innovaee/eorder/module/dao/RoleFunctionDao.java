/***********************************************
 * Filename        : RoleFunctionDao.java 
 * Copyright      : Copyright (c) 2014
 * Company        : Innovaee
 * Created        : 11/27/2014
 ************************************************/

package com.innovaee.eorder.module.dao;

import com.innovaee.eorder.module.entity.RoleFunction;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import java.sql.SQLException;
import java.util.List;

/**
 * @Title: RoleFunctionDao
 * @Description: 角色功能数据访问对象
 *
 * @version V1.0
 */
public class RoleFunctionDao extends BaseDao {

    
    public RoleFunctionDao() {
        super();

    }

    @SuppressWarnings("rawtypes")
    @Override
    protected Class getEntityClass() {
        return RoleFunction.class;
    }

    @SuppressWarnings("unchecked")
    public List<RoleFunction> findAllRoleFunctions() {
        return (List<RoleFunction>) super.getHibernateTemplate().find(
                "FROM RoleFunction");
    }

    public RoleFunction saveRoleFunction(RoleFunction roleFunction) {
        return (RoleFunction) save(roleFunction);
    }

    public void removeRoleFunction(RoleFunction roleFunction) {
        super.getHibernateTemplate().delete(roleFunction);
    }

    @SuppressWarnings("unchecked")
    public List<RoleFunction> findRoleFunctionsByRoleId(Integer roleId) {
        List<RoleFunction> list = (List<RoleFunction>) super
                .getHibernateTemplate().find(
                        "FROM RoleFunction rf WHERE rf.roleId=?", roleId);
        return list;
    }

    @SuppressWarnings("unchecked")
    public List<RoleFunction> findRoleFunctionsByFunctionId(Integer functionId) {
        List<RoleFunction> list = (List<RoleFunction>) super
                .getHibernateTemplate().find(
                        "FROM RoleFunction rf WHERE rf.functionId=?",
                        functionId);
        return list;
    }

    @SuppressWarnings("unchecked")
    public RoleFunction findRoleFunctionByIds(Integer roleId, Integer functionId) {
        List<RoleFunction> list = (List<RoleFunction>) super
                .getHibernateTemplate()
                .find("FROM RoleFunction rf WHERE rf.roleId=? and rf.functionId=?",
                        roleId, functionId);
        if (null != list && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public List<RoleFunction> findParentRoleFunctionByIds(Integer roleId,
            Integer parentFunctionId) {

        List<RoleFunction> list = (List<RoleFunction>) super
                .getHibernateTemplate()
                .find("FROM RoleFunction rf WHERE rf.roleId=? and rf.functionId=?",
                        roleId, parentFunctionId);
        return list;
    }

    /**
     * @param roleId
     * @param parentFunctionId
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<RoleFunction> findRoleFunctionsByFunctionIds(Integer roleId,
            final List<Integer> parentFunctionId) {

        return getHibernateTemplate().execute(
                new HibernateCallback<List<RoleFunction>>() {
                    public List<RoleFunction> doInHibernate(Session session)
                            throws HibernateException, SQLException {
                        String hql = "from RoleFunction rf where rf.functionId in(:typeids)";
                        Query query = session.createQuery(hql);
                        query.setParameterList("typeids", parentFunctionId);
                        List<RoleFunction> list = (List<RoleFunction>) query
                                .list();

                        return list;
                    }
                });
    }

}