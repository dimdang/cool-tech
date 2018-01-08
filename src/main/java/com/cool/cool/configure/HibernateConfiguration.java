package com.cool.cool.configure;

import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by Dang Dim
 * Date     : 08-Jan-18, 10:21 AM
 * Email    : d.dim@gl-f.com
 */

@Configuration
@EnableTransactionManagement
public class HibernateConfiguration {

    HibernateJpaSessionFactoryBean sessionFactoryBean() {
        return new HibernateJpaSessionFactoryBean();
    }
}
