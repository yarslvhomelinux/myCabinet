package com.company.mycabinet.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

@NamePattern("%s|name")
@Table(name = "MYCABINET_PRODUCT_CATEGORY")
@Entity(name = "mycabinet$ProductCategory")
public class ProductCategory extends StandardEntity {
    private static final long serialVersionUID = 4839846255305426503L;

    @Column(name = "NAME", length = 500)
    protected String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


}