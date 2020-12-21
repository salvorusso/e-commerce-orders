package com.unict.dsbd.orders.heartBeater;

public class HeartBeat {

   public final String service = "Order";
   public String status;
   public String dbStatus;


    public HeartBeat(){

    }

    public HeartBeat(String status, String dbStatus) {
        this.status = status;
        this.dbStatus = dbStatus;
    }

    public String getService() {
        return service;
    }

    public String getStatus() {
        return status;
    }

    public HeartBeat setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getDbStatus() {
        return dbStatus;
    }

    public HeartBeat setDbStatus(String dbStatus) {
        this.dbStatus = dbStatus;
        return this;
    }

    @Override
    public String toString() {
        return "{" +
                "service:\"" + service + '\"' +
                ", status:\"" + status + '\"' +
                ", dbStatus:\"" + dbStatus + '\"' +
                '}';
    }
}
