package com.menga.Redeption.Models;

public class LikedModel {
    String likedby;
   Long likedat;
   String likedid;

    public LikedModel() {
    }

    public String getLikedby() {
        return likedby;
    }

    public void setLikedby(String likedby) {
        this.likedby = likedby;
    }

    public Long getLikedat() {
        return likedat;
    }

    public void setLikedat(Long likedat) {
        this.likedat = likedat;
    }

    public String getLikedid() {
        return likedid;
    }

    public void setLikedid(String likedid) {
        this.likedid = likedid;
    }
}
