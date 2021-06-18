package it.uniba.di.sms2021.gruppokdl.wefit.model;

public class Feedback {

    public String client;
    public String message;
    public String clientFullName;
    public float rate;


    public Feedback(){
        // empty constructor
    }

    public Feedback(String clientMail, String clientFullName, float rate, String message){
        this.client = clientMail;
        this.clientFullName = clientFullName;
        this.rate = rate;
        this.message = message;
    }


}
