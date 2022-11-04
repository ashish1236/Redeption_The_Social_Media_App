package com.menga.Redeption.Models;

public class Saved_Posts_Model {
    private String Snap_id;
    private String Saved_by;
    private Long Saved_at;
    private String Post_id;
    private String postedby;



    private long posttime;


    public Saved_Posts_Model() {
    }

    public String getSnap_id() {
        return Snap_id;
    }

    public void setSnap_id(String snap_id) {
        Snap_id = snap_id;
    }

    public String getSaved_by() {
        return Saved_by;
    }

    public void setSaved_by(String saved_by) {
        Saved_by = saved_by;
    }

    public String getPost_id() {
        return Post_id;
    }

    public void setPost_id(String post_id) {
        Post_id = post_id;
    }

    public Long getSaved_at() {
        return Saved_at;
    }

    public void setSaved_at(Long saved_at) {
        Saved_at = saved_at;
    }

    public String getPostedby() {
        return postedby;
    }

    public void setPostedby(String postedby) {
        this.postedby = postedby;
    }
}
