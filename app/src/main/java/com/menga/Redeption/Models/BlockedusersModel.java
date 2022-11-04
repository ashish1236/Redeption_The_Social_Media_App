package com.menga.Redeption.Models;

public class BlockedusersModel {
    private String blockedto;
private long blockedat;
    private String blockedby;
    private String blockedid;

    public BlockedusersModel() {
    }

    public String getBlockedto() {
        return blockedto;
    }

    public void setBlockedto(String blockedto) {
        this.blockedto = blockedto;
    }




    public String getBlockedby() {
        return blockedby;
    }

    public void setBlockedby(String blockedby) {
        this.blockedby = blockedby;
    }


    public long getBlockedat() {
        return blockedat;
    }

    public void setBlockedat(long blockedat) {
        this.blockedat = blockedat;
    }

    public String getBlockedid() {
        return blockedid;
    }

    public void setBlockedid(String blockedid) {
        this.blockedid = blockedid;
    }
}
