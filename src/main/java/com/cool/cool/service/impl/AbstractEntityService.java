package com.cool.cool.service.impl;

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

    public abstract EntityDao geDao();

    @Override
    public SessionFactory getSessionFactory() {
        return geDao().getSessionFactory();
    }

    @Override
    public Session getCurrentSession() {
        return geDao().getCurrentSession();
    }

    @Override
    public Connection getConnection() throws SQLException {
        return geDao().getConnection();
    }

    @Override
    public <T> List<T> list(BaseCriteria<T> criteria) {
        return geDao().list(criteria);
    }

    @Override
    public <T> List<T> list(Class<T> clazz, boolean isDistinctRootEntity, List<Association> associations, List<Criterion> criterions, List<Projection> projections, Integer firstResult, Integer maxResults, List<Order> orders) {
        return geDao().list(clazz, isDistinctRootEntity, associations, criterions, projections, firstResult, maxResults, orders);
    }
}
