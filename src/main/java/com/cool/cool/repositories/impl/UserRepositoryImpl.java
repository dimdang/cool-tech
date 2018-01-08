package com.cool.cool.repositories.impl;

import com.cool.cool.entities.core.User;
import com.cool.cool.repositories.UserRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * Created by Dang Dim
 * Date     : 08-Jan-18, 11:06 AM
 * Email    : d.dim@gl-f.com
 */

@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {

    @Override
    public User findByEmail(String email) {
        return null;
    }

    @Override
    public boolean createUser(User user) {
        return false;
    }
}
