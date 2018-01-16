package com.cool.cool.repositories;

import com.cool.cool.entities.core.User;

/**
 * Created by Dang Dim
 * Date     : 16-Jan-18, 3:35 PM
 * Email    : d.dim@gl-f.com
 */

public interface SecUserRepository extends  EntityDao {

    User loadUserByUsername(String username);
}
