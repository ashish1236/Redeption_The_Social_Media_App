package com.menga.Redeption.Models;

public class Userdata {
    private String name,email,password;
    private String Bio;
    private String Country;
    private String City;
    private String Number;
    private String Birthday;
    private String Gender;
    private String Website;
  private   String background_Pohoto;
    private  String profile_Pohoto;//>>>>>>>>>>>>>>>>>>>> Note this here must same that name of our child who we gave in firebase Realtime database<<<<<<<<<<<<<<<<<<<<<<<//
    private  String UserID;                              // because we get that data here from database//
private String Status;
    // Make emty constracter
    public Userdata(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;

    }

    public Userdata() {
    }

    public Userdata(String name, String email, String password, String bio, String country, String city, String number, String birthday, String gender, String website) {
        this.name = name;
        this.email = email;
        this.password = password;
        Bio = bio;
        Country = country;
        City = city;
        Number = number;
        Birthday = birthday;
        Gender = gender;
        Website = website;
    }



    public String getProfile_Pohoto() {
        return profile_Pohoto;
    }

    public void setProfile_Pohoto(String profile_Pohoto) {
        this.profile_Pohoto = profile_Pohoto;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }







    public String getBio() {
        return Bio;
    }

    public void setBio(String bio) {
        Bio = bio;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getBirthday() {
        return Birthday;
    }

    public void setBirthday(String birthday) {
        Birthday = birthday;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getWebsite() {
        return Website;
    }

    public void setWebsite(String website) {
        Website = website;
    }



    public String getBackground_Pohoto() {
        return background_Pohoto;
    }

    public void setBackground_Pohoto(String background_Pohoto) {
        this.background_Pohoto = background_Pohoto;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
