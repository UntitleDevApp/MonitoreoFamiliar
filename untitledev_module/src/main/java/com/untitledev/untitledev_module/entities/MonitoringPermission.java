package com.untitledev.untitledev_module.entities;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Cipriano on 2/8/2018.
 */

public class MonitoringPermission implements Serializable {
    private int id;
    private int tblUserId;
    private int tblUserIdShareLocation;
    private int statusPermission;
    private int status;
    private Date creationDate;
    private Date updateDate;

    public MonitoringPermission(){

    }

    public MonitoringPermission(int id, int tblUserId, int tblUserIdShareLocation, int statusPermission, int status, Date creationDate, Date updateDate) {
        this.id = id;
        this.tblUserId = tblUserId;
        this.tblUserIdShareLocation = tblUserIdShareLocation;
        this.statusPermission = statusPermission;
        this.status = status;
        this.creationDate = creationDate;
        this.updateDate = updateDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTblUserId() {
        return tblUserId;
    }

    public void setTblUserId(int tblUserId) {
        this.tblUserId = tblUserId;
    }

    public int getTblUserIdShareLocation() {
        return tblUserIdShareLocation;
    }

    public void setTblUserIdShareLocation(int tblUserIdShareLocation) {
        this.tblUserIdShareLocation = tblUserIdShareLocation;
    }

    public int getStatusPermission() {
        return statusPermission;
    }

    public void setStatusPermission(int statusPermission) {
        this.statusPermission = statusPermission;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    @Override
    public String toString() {
        return "MonitoringPermission{" +
                "id=" + id +
                ", tblUserId=" + tblUserId +
                ", tblUserIdShareLocation=" + tblUserIdShareLocation +
                ", statusPermission=" + statusPermission +
                ", status=" + status +
                ", creationDate=" + creationDate +
                ", updateDate=" + updateDate +
                '}';
    }
}
