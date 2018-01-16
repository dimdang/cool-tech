package com.cool.cool.service.impl;

import com.cool.cool.entities.core.User;
import com.cool.cool.repositories.EntityDao;
import com.cool.cool.repositories.SecUserRepository;
import com.cool.cool.service.SecUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Dang Dim
 * Date     : 16-Jan-18, 3:27 PM
 * Email    : d.dim@gl-f.com
 */

@Service
public class SecUserServiceImpl extends AbstractEntityService implements SecUserService {

    @Autowired
    SecUserRepository secUserRepository;

    @Override
    public User loadUserByUsername(String username) {
        return secUserRepository.loadUserByUsername(username);
    }

    @Override
    public EntityDao getDao() {
        return secUserRepository;
    }
}
