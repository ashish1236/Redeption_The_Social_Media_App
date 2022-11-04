package com.menga.Redeption.Models;

public class UsersStories {
    private String image;
    private long postedat;
    private String text;
    private String Pollquestion;

    public UsersStories(String image, long postedat) {
        this.image = image;
        this.postedat = postedat;
    }

    public UsersStories() {
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public long getPostedat() {
        return postedat;
    }

    public void setPostedat(long postedat) {
        this.postedat = postedat;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPollquestion() {
        return Pollquestion;
    }

    public void setPollquestion(String pollquestion) {
        Pollquestion = pollquestion;
    }
}
