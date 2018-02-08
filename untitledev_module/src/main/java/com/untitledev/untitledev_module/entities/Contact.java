package com.untitledev.untitledev_module.entities;

import android.content.Context;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Cipriano on 2/7/2018.
 */

public class Contact implements Serializable {
    private int id;
    private String name;
    private String lastName;
    private String phone;
    private int tblUserId;
    private Date creationDate;
    private Date updateDate;
    private int status;

    public Contact(){

    }

    public Contact(int id, String name, String lastName, String phone, int tblUserId, Date creationDate, Date updateDate, int status) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.phone = phone;
        this.tblUserId = tblUserId;
        this.creationDate = creationDate;
        this.updateDate = updateDate;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getTblUserId() {
        return tblUserId;
    }

    public void setTblUserId(int tblUserId) {
        this.tblUserId = tblUserId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", tblUserId=" + tblUserId +
                ", creationDate=" + creationDate +
                ", updateDate=" + updateDate +
                ", status=" + status +
                '}';
    }
}
