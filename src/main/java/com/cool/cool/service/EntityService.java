package com.cool.cool.service;

import com.cool.cool.entities.core.AbstractEntity;
import com.cool.cool.hsql.Association;
import com.cool.cool.hsql.BaseCriteria;
import com.cool.cool.repositories.EntityDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Dang Dim
 * Date     : 13-Jan-18, 1:16 PM
 * Email    : d.dim@gl-f.com
 */

public interface EntityService {

    EntityDao getDao();

    SessionFactory getSessionFactory();

    Session getCurrentSession();

    Connection getConnection() throws SQLException;

    <T> List<T> list(BaseCriteria<T> criteria);

    <T> List<T> list(Class<T> clazz, boolean isDistinctRootEntity, List<Association> associations, List<Criterion> criterions, List<Projection> projections, Integer firstResult, Integer maxResults, List<Order> orders);

    <T> void saveOrUpdate(T entity);

    <T extends AbstractEntity> void save(List<T> list);

    <T extends AbstractEntity> void update(List<T> list);

    <T> List<T> list(Class<T> clazz);

    <T> T findByField(String field, Object value, Class<T> clazz);
}
