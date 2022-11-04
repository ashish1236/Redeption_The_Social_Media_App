package com.menga.Redeption.Models;

public class PostModel {
    private String postid;
    private String postimage;
    private String postdiscription;
    private String postby;
    private Long postAt;
    private String savedby;
    private String posttype;

    private String Pollquestion;
    private String video;

    public PostModel(String postid, String postimage, String postdiscription, String postby, Long postAt) {
        this.postid = postid;
        this.postimage = postimage;
        this.postdiscription = postdiscription;
        this.postby = postby;
        this.postAt = postAt;

    }

    public PostModel() {
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getPostimage() {
        return postimage;
    }

    public void setPostimage(String postimage) {
        this.postimage = postimage;
    }

    public String getPostdiscription() {
        return postdiscription;
    }

    public void setPostdiscription(String postdiscription) {
        this.postdiscription = postdiscription;
    }

    public String getPostby() {
        return postby;
    }

    public void setPostby(String postby) {
        this.postby = postby;
    }

    public Long getPostAt() {
        return postAt;
    }

    public void setPostAt(Long postAt) {
        this.postAt = postAt;
    }

    public String getSavedby() {
        return savedby;
    }

    public void setSavedby(String savedby) {
        this.savedby = savedby;
    }

    public String getPosttype() {
        return posttype;
    }

    public void setPosttype(String posttype) {
        this.posttype = posttype;
    }

    public String getPollquestion() {
        return Pollquestion;
    }

    public void setPollquestion(String pollquestion) {
        Pollquestion = pollquestion;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }
}