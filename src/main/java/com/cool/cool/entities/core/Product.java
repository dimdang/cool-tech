package com.cool.cool.entities.core;

import javax.persistence.*;

/**
 * Created by Dang Dim
 * Date     : 08-Jan-18, 2:58 PM
 * Email    : d.dim@gl-f.com
 */

@Entity
@Table(name = "td_product")
public class Product extends AbstractEntity{

    private String productName;
    private Category category;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pro_id")
    @Override
    public Long getId() {
        return id;
    }

    @Override
    @Column(name = "pro_code")
    public String getCode() {
        return code;
    }

    @Override
    @Column(name = "pro_des")
    public String getDesc() {
        return desc;
    }

    @Column(name = "pro_name")
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id", nullable = false)
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
