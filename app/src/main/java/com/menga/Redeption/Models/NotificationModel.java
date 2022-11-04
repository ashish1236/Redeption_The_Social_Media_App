package com.menga.Redeption.Models;

public class NotificationModel {
    private String notificationtype;
    private long time;
    private String notificationby;
    private boolean checkopen;
    private String postid;
    private String postedby;
    private String notificationid;
    private String notifiacationfollowid;

    public NotificationModel() {
    }

    public String getNotificationtype() {
        return notificationtype;
    }

    public void setNotificationtype(String notificationtype) {
        this.notificationtype = notificationtype;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getNotificationby() {
        return notificationby;
    }

    public void setNotificationby(String notificationby) {
        this.notificationby = notificationby;
    }

    public boolean isCheckopen() {
        return checkopen;
    }

    public void setCheckopen(boolean checkopen) {
        this.checkopen = checkopen;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getPostedby() {
        return postedby;
    }

    public void setPostedby(String postedby) {
        this.postedby = postedby;
    }

    public String getNotificationid() {
        return notificationid;
    }

    public void setNotificationid(String notificationid) {
        this.notificationid = notificationid;
    }

    public String getNotifiacationfollowid() {
        return notifiacationfollowid;
    }

    public void setNotifiacationfollowid(String notifiacationfollowid) {
        this.notifiacationfollowid = notifiacationfollowid;
    }
}
