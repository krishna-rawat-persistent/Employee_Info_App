package com.psl.permission_app.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Permission {
    @Id
    private String id;
    private boolean flag;

    public Permission(){}

    public Permission(String id, boolean flag) {
        this.id = id;
        this.flag = flag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
