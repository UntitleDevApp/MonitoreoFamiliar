package com.untitledev.untitledev_module.entities;

import java.io.Serializable;

/**
 * Created by Cipriano on 2/8/2018.
 */

public class UserLocation implements Serializable {
    private int tblUserId;
    private double latitude;
    private double longitude;
    private int status;
    private String creationDate;
    private String updateDate;

    public UserLocation(){

    }

    public UserLocation(int tblUserId, double latitude, double longitude, int status, String creationDate, String updateDate) {
        this.tblUserId = tblUserId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.status = status;
        this.creationDate = creationDate;
        this.updateDate = updateDate;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public String toString() {
        return "UserLocation{" +
                "tblUserId=" + tblUserId +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", status=" + status +
                '}';
    }
}
