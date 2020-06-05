package com.smbms.pojo;

import java.util.Date;

    /**
    * @program: smbms-maven-demo
    *
    * @description: ${description}
    *
    * @author: wu
    *
    * @create: 2020-05-30 10:20
    **/
public class SmbmsAddress {
    /**
    * 主键ID
    */
    private Long id;

    /**
    * 联系人姓名
    */
    private String contact;

    /**
    * 收货地址明细
    */
    private String addressdesc;

    /**
    * 邮编
    */
    private String postcode;

    /**
    * 联系人电话
    */
    private String tel;

    /**
    * 创建者
    */
    private Long createdby;

    /**
    * 创建时间
    */
    private Date creationdate;

    /**
    * 修改者
    */
    private Long modifyby;

    /**
    * 修改时间
    */
    private Date modifydate;

    /**
    * 用户ID
    */
    private Long userid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddressdesc() {
        return addressdesc;
    }

    public void setAddressdesc(String addressdesc) {
        this.addressdesc = addressdesc;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Long getCreatedby() {
        return createdby;
    }

    public void setCreatedby(Long createdby) {
        this.createdby = createdby;
    }

    public Date getCreationdate() {
        return creationdate;
    }

    public void setCreationdate(Date creationdate) {
        this.creationdate = creationdate;
    }

    public Long getModifyby() {
        return modifyby;
    }

    public void setModifyby(Long modifyby) {
        this.modifyby = modifyby;
    }

    public Date getModifydate() {
        return modifydate;
    }

    public void setModifydate(Date modifydate) {
        this.modifydate = modifydate;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }
}