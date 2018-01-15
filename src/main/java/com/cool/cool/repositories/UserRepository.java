package com.cool.cool.repositories;

import com.cool.cool.entities.core.User;

/**
 * Created by Dang Dim
 * Date     : 05-Jan-18, 8:46 AM
 * Email    : d.dim@gl-f.com
 */


public interface UserRepository {

    User findByEmail(String email);

    void createUser(User user);

}
