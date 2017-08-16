package com.atcons.bclient.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(schema = "bclient", name = "dim_user")
public class DimUser {
    @Id
    private Long user_id;
    @Column(unique = true, name = "userLogin")
    private String userLogin;
    private String user_full_name;
    private String user_password;

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getUser_full_name() {
        return user_full_name;
    }

    public void setUser_full_name(String user_full_name) {
        this.user_full_name = user_full_name;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    @Override
    public String toString() {
        return "DimUser{" +
                "user_id=" + user_id +
                ", userLogin='" + userLogin + '\'' +
                ", user_full_name='" + user_full_name + '\'' +
                ", user_password='" + user_password + '\'' +
                '}';
    }
}
