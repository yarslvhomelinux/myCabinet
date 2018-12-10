package com.company.mycabinet.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Lob;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

@NamePattern("Отзыв %s %s|createdBy,createTs")
@Table(name = "MYCABINET_USER_COMMENT")
@Entity(name = "mycabinet$UserComment")
public class UserComment extends StandardEntity {
    private static final long serialVersionUID = 2872655051488043470L;

    @Lob
    @Column(name = "COMMENT_")
    protected String comment;

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }


}