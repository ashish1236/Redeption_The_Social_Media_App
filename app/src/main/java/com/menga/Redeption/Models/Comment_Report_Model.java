package com.menga.Redeption.Models;

public class Comment_Report_Model {
    private String Reportedby;
  private long Reported_at;
    private String Report_Type;
    private String Reported_Comment_By;
    private String Postid_of_Rrported_comment;
    private String Reported_Comment_id;
    private String Status;
    private String Comment_report_model_Id;

    public Comment_Report_Model() {
    }

    public String getReportedby() {
        return Reportedby;
    }

    public void setReportedby(String reportedby) {
        Reportedby = reportedby;
    }



    public String getReport_Type() {
        return Report_Type;
    }

    public void setReport_Type(String report_Type) {
        Report_Type = report_Type;
    }

    public String getReported_Comment_By() {
        return Reported_Comment_By;
    }

    public void setReported_Comment_By(String reported_Comment_By) {
        Reported_Comment_By = reported_Comment_By;
    }

    public String getPostid_of_Rrported_comment() {
        return Postid_of_Rrported_comment;
    }

    public void setPostid_of_Rrported_comment(String postid_of_Rrported_comment) {
        Postid_of_Rrported_comment = postid_of_Rrported_comment;
    }

    public String getReported_Comment_id() {
        return Reported_Comment_id;
    }

    public void setReported_Comment_id(String reported_Comment_id) {
        Reported_Comment_id = reported_Comment_id;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public long getReported_at() {
        return Reported_at;
    }

    public void setReported_at(long reported_at) {
        Reported_at = reported_at;
    }

    public String getComment_report_model_Id() {
        return Comment_report_model_Id;
    }

    public void setComment_report_model_Id(String comment_report_model_Id) {
        Comment_report_model_Id = comment_report_model_Id;
    }
}
