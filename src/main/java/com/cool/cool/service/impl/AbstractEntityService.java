package com.cool.cool.service.impl;

import com.cool.cool.entities.core.AbstractEntity;
import com.cool.cool.hsql.Association;
import com.cool.cool.hsql.BaseCriteria;
import com.cool.cool.repositories.EntityDao;
import com.cool.cool.service.EntityService;
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
 * Date     : 13-Jan-18, 1:13 PM
 * Email    : d.dim@gl-f.com
 */

public abstract class AbstractEntityService implements EntityService {

    public abstract EntityDao getDao();

    @Override
    public SessionFactory getSessionFactory() {
        return getDao().getSessionFactory();
    }

    @Override
    public Session getCurrentSession() {
        return getDao().getCurrentSession();
    }

    @Override
    public Connection getConnection() throws SQLException {
        return getDao().getConnection();
    }

    @Override
    public <T> List<T> list(BaseCriteria<T> criteria) {
        return getDao().list(criteria);
    }

    @Override
    public <T> List<T> list(Class<T> clazz, boolean isDistinctRootEntity, List<Association> associations, List<Criterion> criterions, List<Projection> projections, Integer firstResult, Integer maxResults, List<Order> orders) {
        return getDao().list(clazz, isDistinctRootEntity, associations, criterions, projections, firstResult, maxResults, orders);
    }

    @Override
    public <T> void saveOrUpdate(T entity) {
        getDao().saveOrUpdate(entity);
    }

    @Override
    public <T extends AbstractEntity> void save(List<T> list) {
        getDao().save(list);
    }

    @Override
    public <T extends AbstractEntity> void update(List<T> list) {
        getDao().update(list);
    }

    @Override
    public <T> List<T> list(Class<T> clazz) {
        return getDao().list(clazz);
    }

    @Override
    public <T> T findByField(String field, Object value, Class<T> clazz) {
        return getDao().findByField(field, value, clazz);
    }
}
