package com.untitledev.untitledev_module.entities;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Cipriano on 2/8/2018.
 */

public class MonitoringHistory implements Serializable {
    private int id;
    private int tblUserId;
    private double latitude;
    private double longitude;
    private Date creationDate;
    private int status;

    public MonitoringHistory(){

    }

    public MonitoringHistory(int id, int tblUserId, double latitude, double longitude, Date creationDate, int status) {
        this.id = id;
        this.tblUserId = tblUserId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.creationDate = creationDate;
        this.status = status;
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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "MonitoringHistory{" +
                "id=" + id +
                ", tblUserId=" + tblUserId +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", creationDate=" + creationDate +
                ", status=" + status +
                '}';
    }
}
