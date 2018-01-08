package com.cool.cool.hsql;

import com.cool.cool.entities.core.AbstractEntity;
import org.hibernate.criterion.*;
import org.hibernate.sql.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.*;

/**
 * Created by Dang Dim
 * Date     : 08-Jan-18, 11:49 AM
 * Email    : d.dim@gl-f.com
 */

public class BaseCriteria<T> implements Serializable {

    private static final long serialVersionUID = -6547635621642103878L;

    protected static Logger logger = LoggerFactory.getLogger(BaseCriteria.class.getName());

    protected static final String DOT = ".";

    private Class<T> entityClass;
    private List<Association> associations;
    private List<Criterion> criterions;
    private Integer firstResult;
    private Integer maxResults;
    private List<Order> orders;
    private Boolean orderBySortIndex;
    private Boolean orderBySortIndexAndId;
    private Boolean orderById = true;

    private boolean ignoreStatusRecord = false;

    private Map<String, Object> mapCriterias = new HashMap<String, Object>();

    private List<Projection> projections;
    private boolean isDistinctRootEntity = false;

    public BaseCriteria(Class<T> entityClass) {
        this.entityClass = entityClass;
        associations = new ArrayList<Association>();
        criterions = new ArrayList<Criterion>();
        orders = new ArrayList<Order>();
    }

    public Association addAssociation(Class<? extends AbstractEntity> entityClass) {
        Association assoc = new Association(entityClass);
        if (associations.contains(assoc)) {
            throw new IllegalArgumentException("The alias [" + assoc.getAlias() + "] is already used.");
        }
        associations.add(assoc);
        return (assoc);
    }

    public Association addAssociation(Class<? extends AbstractEntity> entityClass, String alias, JoinType joinType) {
        Association assoc = new Association(entityClass, alias, joinType);
        if (associations.contains(assoc)) {
            throw new IllegalArgumentException("The alias [" + alias + "] is already used.");
        }
        associations.add(assoc);
        return (assoc);
    }

    public Association addAssociation(String associationPath) {
        return addAssociation(associationPath, associationPath, JoinType.INNER_JOIN);
    }

    public Association addAssociation(String associationPath, JoinType joinType) {
        return addAssociation(associationPath, associationPath, joinType);
    }

    public Association addAssociation(String associationPath, String alias, JoinType joinType) {
        Association assoc = new Association(associationPath, alias, joinType);
        if (associations.contains(assoc)) {
            logger.warn("The alias [" + alias + "] has been already added.");
        } else {
            associations.add(assoc);
        }
        return (assoc);
    }

    public Association addAssociation(String associationPath, String alias, JoinType joinType, Criterion withClause) {
        Association assoc = new Association(associationPath, alias, joinType, withClause);
        if (associations.contains(assoc)) {
            logger.warn("The alias [" + alias + "] has been already added.");
        } else {
            associations.add(assoc);
        }
        return (assoc);
    }

    public Association addAssociation(Class<? extends AbstractEntity>... entityClasses) {
        return addAssociation(entityClasses[entityClasses.length - 1].getSimpleName().toLowerCase(), JoinType.INNER_JOIN, entityClasses);
    }

    public Association addAssociation(String alias, Class<? extends AbstractEntity>... entityClasses) {
        return addAssociation(alias, JoinType.INNER_JOIN, entityClasses);
    }

    public Association addAssociation(String alias, JoinType joinType, Class<? extends AbstractEntity>... entityClasses) {
        String associationPath = entityClasses[0].getSimpleName().toLowerCase();
        for (int i = 1; i < entityClasses.length; i++) {
            associationPath += DOT + entityClasses[i].getSimpleName().toLowerCase();
        }
        Association assoc = new Association(associationPath, alias, joinType);
        if (associations.contains(assoc)) {
            throw new IllegalArgumentException("The alias [" + alias + "] is already used.");
        }
        associations.add(assoc);
        return assoc;
    }

    public void addCriterion(Criterion criterion) {
        criterions.add(criterion);
    }

    public Class<T> getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public List<Association> getAssociations() {
        return associations;
    }

    public void setAssociations(List<Association> associations) {
        this.associations = associations;
    }

    public List<Criterion> getCriterions() {
        return criterions;
    }

    public void setCriterions(List<Criterion> criterions) {
        this.criterions = criterions;
    }

    public Integer getFirstResult() {
        return firstResult;
    }

    public void setFirstResult(Integer firstResult) {
        this.firstResult = firstResult;
    }

    public Integer getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(Integer maxResults) {
        this.maxResults = maxResults;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public void addOrder(Order order) {
        if (orders == null) {
            orders = new ArrayList<Order>();
        }
        orders.add(order);
    }

    public void setOrder(Order order) {
        orders = new ArrayList<Order>();
        orders.add(order);
    }

    public Order getOrder() {
        if (orders == null || orders.size() == 0) {
            return null;
        }
        return orders.get(0);
    }

    public void addCriterion(String columname, FilterMode comparator, Object... values) {
        mapCriterias.put(columname, new Object[]{comparator, values});
    }


    public void addCriterion(String columname, Serializable... values) {
        FilterMode comparator = new FilterMode();
        mapCriterias.put(columname, new Object[]{comparator.getDefaultFilterMode(), values});
    }

    private void addCriterion(FieldFilter filter) {
        Criterion criterion = null;
        if (filter.getFilterMode().equals(FilterMode.BETWEEN)) {
            criterion = Restrictions.between(filter.getFieldName(), filter.getFieldValue(), filter.getField2Value());
            criterions.add(criterion);
        } else if (filter.getFilterMode().equals(FilterMode.IN)) {
            if (filter.getFieldValues()[0] instanceof Collection) {
                criterion = Restrictions.in(filter.getFieldName(), (Collection) filter.getFieldValues()[0]);
            } else if (filter.getFieldValues()[0] instanceof Object[]) {
                criterion = Restrictions.in(filter.getFieldName(), (Object[]) filter.getFieldValues()[0]);
            } else {
                criterion = Restrictions.in(filter.getFieldName(), filter.getFieldValues());
            }
            criterions.add(criterion);
        } else if (filter.getFilterMode().equals(FilterMode.BLANK)) {
            criterion = Restrictions.eq(filter.getFieldName(), "");
            criterions.add(criterion);
        } else if (filter.getFilterMode().equals(FilterMode.NULL)) {
            criterion = Restrictions.isNull(filter.getFieldName());
            criterions.add(criterion);
        } else if (filter.getFilterMode().equals(FilterMode.EMPTY)) {
            criterion = Restrictions.isEmpty(filter.getFieldName());
            criterions.add(criterion);
        } else if (filter.getFilterMode().equals(FilterMode.NOT_EMPTY)) {
            criterion = Restrictions.isNotEmpty(filter.getFieldName());
            criterions.add(criterion);
        } else {
            Criterion[] criteria = new Criterion[filter.getFieldValues().length];
            for (int i = 0; i < filter.getFieldValues().length; i++) {
                criterion = null;
                Object value = filter.getFieldValues()[i];

                if (filter.getFilterMode().equals(FilterMode.EQUALS)) {
                    criterion = Restrictions.eq(filter.getFieldName(), value);
                } else if (filter.getFilterMode().equals(FilterMode.GREATER_THAN)) {
                    criterion = Restrictions.gt(filter.getFieldName(), value);
                } else if (filter.getFilterMode().equals(FilterMode.GREATER_OR_EQUALS)) {
                    criterion = Restrictions.ge(filter.getFieldName(), value);
                } else if (filter.getFilterMode().equals(FilterMode.LESS_THAN)) {
                    criterion = Restrictions.lt(filter.getFieldName(), value);
                } else if (filter.getFilterMode().equals(FilterMode.LESS_OR_EQUALS)) {
                    criterion = Restrictions.le(filter.getFieldName(), value);
                } else if (filter.getFilterMode().equals(FilterMode.BLANK)) {
                    criterion = Restrictions.eq(filter.getFieldName(), "");
                } else if (filter.getFilterMode().equals(FilterMode.NULL)) {
                    criterion = Restrictions.isNull(filter.getFieldName());
                } else if (filter.getFilterMode().equals(FilterMode.EMPTY)) {
                    criterion = Restrictions.isEmpty(filter.getFieldName());
                } else if (filter.getFilterMode().equals(FilterMode.NOT_EMPTY)) {
                    criterion = Restrictions.isNotEmpty(filter.getFieldName());
                } else if (filter.getFilterMode().equals(StringFilterMode.CONTAINS)) {
                    criterion = Restrictions.ilike(filter.getFieldName(), (String) value, MatchMode.ANYWHERE);
                } else if (filter.getFilterMode().equals(StringFilterMode.START_WITH)) {
                    criterion = Restrictions.ilike(filter.getFieldName(), (String) value, MatchMode.START);
                } else if (filter.getFilterMode().equals(StringFilterMode.END_WITH)) {
                    criterion = Restrictions.ilike(filter.getFieldName(), (String) value, MatchMode.END);
                } else if (filter.getFilterMode().equals(StringFilterMode.DOES_NOT_CONTAIN)) {
                    criterion = Restrictions.not(Restrictions.ilike(filter.getFieldName(), (String) value, MatchMode.ANYWHERE));
                }
                criteria[i] = criterion;
            }

            criterions.add(Restrictions.or(criteria));
        }

    }

    public void setOrderBySortIndex() {
        this.orderBySortIndex = true;
    }

    public void setOrderBySortIndexAndId() {
        this.orderBySortIndexAndId = true;
    }

    public void setOrderById() {
        this.orderById = true;
    }

    public boolean isIgnoreStatusRecord() {
        return ignoreStatusRecord;
    }

    public void setIgnoreStatusRecord(boolean ignoreStatusRecord) {
        this.ignoreStatusRecord = ignoreStatusRecord;
    }

    public void ignoreStatusRecord() {
        this.ignoreStatusRecord = true;
    }

    public List<Projection> getProjections() {
        return projections;
    }

    public void setProjections(List<Projection> projections) {
        this.projections = projections;
    }

    public void addProjection(Projection projection) {
        if (projections == null) {
            projections = new ArrayList<Projection>();
        }
        this.projections.add(projection);
    }

    public boolean isDistinctRootEntity() {
        return isDistinctRootEntity;
    }

    public void setDistinctRootEntity(boolean isDistinctRootEntity) {
        this.isDistinctRootEntity = isDistinctRootEntity;
    }
}
