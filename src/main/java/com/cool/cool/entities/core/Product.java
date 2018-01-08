package com.cool.cool.entities.core;

import org.springframework.stereotype.Component;

import javax.persistence.*;

/**
 * Created by Dang Dim
 * Date     : 08-Jan-18, 2:58 PM
 * Email    : d.dim@gl-f.com
 */

@Entity
@Table(name = "td_product")
public class Product extends AbstractEntity{

    String name;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pro_id")
    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    @Column(name = "pro_code")
    public String getCode() {
        return null;
    }

    @Override
    @Column(name = "pro_des")
    public String getDesc() {
        return null;
    }

    @Column(name = "pro_name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
}
