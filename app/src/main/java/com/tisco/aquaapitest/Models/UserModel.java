package com.tisco.aquaapitest.Models;

import java.util.Date;

public class UserModel {
    Integer id;
    String email;
    Boolean confirmed;
    String created;

    public UserModel(Integer id, String email, Boolean confirmed, String created) {
        this.id = id;
        this.email = email;
        this.confirmed = confirmed;
        this.created = created;
    }

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public Boolean getConfirmed() {
        return confirmed;
    }

    public String getCreated() {
        return created;
    }
}
