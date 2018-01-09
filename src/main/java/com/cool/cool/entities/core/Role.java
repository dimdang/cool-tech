package com.cool.cool.entities.core;

import javax.persistence.*;

/**
 * Created by Dang Dim
 * Date     : 05-Jan-18, 9:00 AM
 * Email    : d.dim@gl-f.com
 */

@Entity
@Table(name = "table_role")
public class Role extends AbstractEntity{

    private String roleName;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    @Override
    public Long getId() {
        return id;
    }

    @Override
    @Column(name = "rol_code")
    public String getCode() {
        return code;
    }

    @Override
    @Column(name = "rol_des")
    public String getDesc() {
        return desc;
    }

    @Column(name = "role_name", nullable = true)
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Role authority = (Role) obj;

        if (!roleName.equals(authority.roleName)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return roleName.hashCode();
    }

    @Override
    public String toString() {
        return "Role{" +
                "roleName='" + roleName + '\'' +
                '}';
    }

}

