package com.cool.cool.service;

import com.cool.cool.entities.core.User;

/**
 * Created by Dang Dim
 * Date     : 16-Jan-18, 3:18 PM
 * Email    : d.dim@gl-f.com
 */

public interface SecUserService extends EntityService {

    User loadUserByUsername(String username);
}
