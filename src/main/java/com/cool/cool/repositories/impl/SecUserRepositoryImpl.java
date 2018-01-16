package com.cool.cool.repositories.impl;

import com.cool.cool.entities.core.User;
import com.cool.cool.hsql.BaseCriteria;
import com.cool.cool.repositories.SecUserRepository;
import com.cool.cool.service.SecUserService;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Dang Dim
 * Date     : 16-Jan-18, 3:37 PM
 * Email    : d.dim@gl-f.com
 */

@Service
public class SecUserRepositoryImpl extends AbstractEntityDao implements SecUserRepository {

    @Autowired
    SecUserService service ;

    @Override
    public User loadUserByUsername(String username) {
        User user = null;
        if (username != null){
            BaseCriteria<User> criteria = new BaseCriteria(User.class);
            criteria.addCriterion(Restrictions.eq("username", username));
            List<User> userList = service.list(criteria);

            if (!userList.isEmpty()) {
                user = userList.get(0);
            }
        }
        return user;
    }
}
