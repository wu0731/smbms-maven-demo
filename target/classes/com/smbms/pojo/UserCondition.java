package com.smbms.pojo;

/**
 * @program: SMBMS
 * @description: 用户新增表单
 * @author: wu
 * @create: 2020-05-27 11:03
 **/
public class UserCondition {
    private String userCode;

    private String userName;

    private String userPassword;

    private String ruserPassword;

    private Integer gender;

    private String birthday;

    private String phone;

    private String address;

    private String userRole;

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getRuserPassword() {
        return ruserPassword;
    }

    public void setRuserPassword(String ruserPassword) {
        this.ruserPassword = ruserPassword;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
}