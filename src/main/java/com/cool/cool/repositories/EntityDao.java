package com.cool.cool.repositories;

import com.cool.cool.hsql.Association;
import com.cool.cool.hsql.BaseCriteria;
import org.dom4j.tree.AbstractEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Dang Dim
 * Date     : 08-Jan-18, 11:34 AM
 * Email    : d.dim@gl-f.com
 */

public interface EntityDao {

    SessionFactory getSessionFactory();

    Session getCurrentSession();

    Connection getConnection() throws SQLException;

    <T> T getEntityById(Long id, Class<T> clazz);

    <T> T loadEntityById(Long id, Class<T> clazz);

    <T> List<T> getEntityByCode(String code, Class<T> clazz);

    <T> List<T> list(Class<T> clazz);

    <T extends AbstractEntity> void save(T entity);

    <T extends AbstractEntity> void update(T entity);

    <T> void saveOrUpdate(T entity);

    <T extends AbstractEntity> void save(List<T> list);

    <T extends AbstractEntity> void update(List<T> list);

    <T> void saveOrUpdate(List<T> list);

    <T> void delete(T entity);

    <T> List<T> getList(String sql, Class<T> clazz);

    <T> T getSingle(String sql, Class<T> clzz);

    int executeSQL(String sql);

    ResultSet executeQuery(String sql) throws Exception;

    <T> List<T> list(BaseCriteria<T> criteria);

    <T> List<T> list(Class<T> clazz, boolean isDistinctRootEntity, List<Association> associations, List<Criterion> criterions, List<Projection> projections, Integer firstResult, Integer maxResults, List<Order> orders);

}
