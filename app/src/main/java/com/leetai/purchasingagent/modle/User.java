package com.leetai.purchasingagent.modle;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    public User(){

    }
    public  User(int userId){
        this.userId = userId;
    }
    private Integer userId;

    private String username;

    private String namedes;

    private String password;

    private String telphone;

    private String email;

    private Date addTime;

    private String useFlag;

    private String type;

    private String country;

    private Integer level;

    private Integer dealCount;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getNamedes() {
        return namedes;
    }

    public void setNamedes(String namedes) {
        this.namedes = namedes == null ? null : namedes.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone == null ? null : telphone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getUseFlag() {
        return useFlag;
    }

    public void setUseFlag(String useFlag) {
        this.useFlag = useFlag == null ? null : useFlag.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country == null ? null : country.trim();
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getDealCount() {
        return dealCount;
    }

    public void setDealCount(Integer dealCount) {
        this.dealCount = dealCount;
    }
}