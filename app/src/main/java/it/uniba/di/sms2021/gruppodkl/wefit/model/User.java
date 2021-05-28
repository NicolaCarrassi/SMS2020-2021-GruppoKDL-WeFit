package it.uniba.di.sms2021.gruppodkl.wefit.model;

public class User {

    public String fullName;
    public String email;
    public String birthDate;
    public String gender;
    public String image;
    public String role;

    public User(){

    }

    public User(String fullName, String email, String birthDate, String gender, String role){
        this.fullName = fullName;
        this.email = email;
        this.birthDate = birthDate;
        this.gender = gender;
        this.role = role;
    }



}
