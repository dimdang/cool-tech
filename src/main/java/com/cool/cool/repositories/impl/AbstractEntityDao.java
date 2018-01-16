package com.cool.cool.repositories.impl;

import com.cool.cool.entities.core.AbstractEntity;
import com.cool.cool.hsql.Association;
import com.cool.cool.hsql.BaseCriteria;
import com.cool.cool.repositories.EntityDao;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.*;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Dang Dim
 * Date     : 13-Jan-18, 1:08 PM
 * Email    : d.dim@gl-f.com
 */

@Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {java.lang.Exception.class})
public abstract class AbstractEntityDao implements EntityDao {

    @Autowired
    private SessionFactory sessionFactory;

    /*@PersistenceContext
    private EntityManager entityManager;*/

    @Override
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Override
    public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public Connection getConnection() throws SQLException {
        return getSessionFactory().getSessionFactoryOptions().getServiceRegistry().
                getService(ConnectionProvider.class).getConnection();
    }


    @Override
    public <T> List<T> list(BaseCriteria<T> criteria) {
        List<T> lst = list(
                criteria.getEntityClass(),
                criteria.isDistinctRootEntity(),
                criteria.getAssociations(),
                criteria.getCriterions(),
                criteria.getProjections(),
                criteria.getFirstResult(),
                criteria.getMaxResults(),
                criteria.getOrders());
        return lst;
    }

    @Override
    public <T> List<T> list(Class<T> clazz, boolean isDistinctRootEntity, List<Association> associations, List<Criterion> criterions, List<Projection> projections, Integer firstResult, Integer maxResults, List<Order> orders) {

        Criteria criteria = getCurrentSession().createCriteria(clazz);
        if (isDistinctRootEntity) {
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        }
        if (associations != null) {
            for (Association association : associations) {
                if (association != null) {
                    criteria.createAlias(
                            association.getAssociationPath(),
                            association.getAlias(),
                            association.getJoinType(),
                            association.getWithClause());
                }
            }
        }

        if (criterions != null) {
            for (Criterion criterion : criterions) {
                if (criterion != null) {
                    criteria = criteria.add(criterion);
                }
            }
        }

        if (projections != null) {
            ProjectionList projList = Projections.projectionList();
            for (Projection proj : projections) {
                if (proj != null) {
                    projList.add(proj);
                }
            }
            if (projList != null && projList.getLength() > 0) {
                criteria.setProjection(projList);
            }
        }


        if (orders != null) {
            for (Order order : orders) {
                if (order != null) {
                    criteria = criteria.addOrder(order);
                }
            }
        }

        if (firstResult != null) {
            criteria.setFirstResult(firstResult);
        }

        if (maxResults != null) {
            criteria.setMaxResults(maxResults);
        }

        return criteria.list();
    }

    @Override
    public <T> List<T> getEntityByCode(String code, Class<T> clazz) {
        if (code != null && !code.isEmpty() && clazz != null) {
            Criteria criteria = getCurrentSession().createCriteria(clazz);
            criteria.add(Restrictions.eq("code", code));
            return criteria.list();
        }
        return null;
    }

    @Override
    public <T> void saveOrUpdate(T entity) {
        if (entity != null)
            getCurrentSession().saveOrUpdate(entity);
    }

    @Override
    public <T extends AbstractEntity> void save(List<T> list) {
        if (list != null)
            for (T entity : list)
                save((List<T>) entity);
    }

    @Override
    public <T extends AbstractEntity> void update(List<T> list) {
        if (list != null)
            for (T entity : list)
                update((List<T>) entity);
    }

    @Override
    public <T> List<T> list(Class<T> clazz) {
        if (clazz != null) {
            Criteria criteria = getCurrentSession().createCriteria(clazz);
            criteria.addOrder(Order.desc("updateDate"));
            return criteria.list();
        }
        return null;
    }

    public <T> T findByField(String field, Object value, Class<T> clazz) {
        Criteria criteria = getCurrentSession().createCriteria(clazz);
        criteria.add(Restrictions.eq(field, value));
        return (T) criteria.uniqueResult();
    }

    public <T> List<T> listByField(String field, Object value, Class<T> clazz) {
        Criteria criteria = getCurrentSession().createCriteria(clazz);
        criteria.add(Restrictions.eq(field, value));
        return  criteria.list();
    }
}
