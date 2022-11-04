package com.menga.Redeption.Models;

public class Posts_Report_Model {
    private String potid;
    private String Reportid;
    private String Reportedby;
    private String Report_Type;
    private String Status;
    private Long Time;

    public Posts_Report_Model() {
    }

    public String getPotid() {
        return potid;
    }

    public void setPotid(String potid) {
        this.potid = potid;
    }

    public Long getTime() {
        return Time;
    }

    public void setTime(Long time) {
        Time = time;
    }

    public String getReportid() {
        return Reportid;
    }

    public void setReportid(String reportid) {
        Reportid = reportid;
    }

    public String getReportedby() {
        return Reportedby;
    }

    public void setReportedby(String reportedby) {
        Reportedby = reportedby;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getReport_Type() {
        return Report_Type;
    }

    public void setReport_Type(String report_Type) {
        Report_Type = report_Type;
    }
}
