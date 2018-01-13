package com.cool.cool.repositories.impl;

import com.cool.cool.entities.core.User;
import com.cool.cool.repositories.UserRepository;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityExistsException;
import javax.transaction.Transactional;

/**
 * Created by Dang Dim
 * Date     : 08-Jan-18, 11:06 AM
 * Email    : d.dim@gl-f.com
 */

@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private SessionFactory sessionFactory;
    Session session = null;
    Transaction transaction = null;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User findByEmail(String username) {
        User user = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            if (username != null && !username.equals("")) {
                Criteria criteria = session.createCriteria(User.class);
                criteria.add(Restrictions.eq("username", username));
                user = (User) criteria.list().get(0);
            }
        }catch (Exception e){
            e.printStackTrace();
            if (transaction != null){
                transaction.commit();
            }
        }finally {
            if (transaction != null)
                transaction.commit();
        }
        return user;
    }

    @Override
    public boolean createUser(User user) {
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            Criteria criteria = session.createCriteria(User.class);
            criteria.add(Restrictions.eq("username", user));
            if (user.getUsername().equals(criteria.list().get(0))){
                throw new EntityExistsException("User " + user.getUsername() + " was found in the database");
            }else {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                session.persist(user);
                transaction.commit();
                return true;
            }

        } catch (HibernateException e) {
            e.printStackTrace();
            transaction.rollback();
            return false;
        } finally {

            if (session != null) {
                session.close();
            }
        }
    }
}
