package com.tisco.aquaapitest.Models;

import java.util.Date;

public class UserModel {
    Integer id;
    String email;
    Boolean confirmed, password_reset, send_announcements, send_scan_results, send_new_plugins, send_new_risks, account_admin, provider, multiaccount;
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

    public Boolean getPassword_reset() {
        return password_reset;
    }

    public Boolean getSend_announcements() {
        return send_announcements;
    }

    public Boolean getSend_scan_results() {
        return send_scan_results;
    }

    public Boolean getSend_new_plugins() {
        return send_new_plugins;
    }

    public Boolean getSend_new_risks() {
        return send_new_risks;
    }

    public Boolean getAccount_admin() {
        return account_admin;
    }

    public Boolean getProvider() {
        return provider;
    }

    public Boolean getMultiaccount() {
        return multiaccount;
    }

    public String getCreated() {
        return created;
    }
}
