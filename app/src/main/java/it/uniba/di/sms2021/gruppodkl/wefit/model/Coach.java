package it.uniba.di.sms2021.gruppodkl.wefit.model;

public class Coach extends User {

    public boolean isPersonalTrainer = false;
    public boolean isDietist = true;
    public String certificationUri = null;


    public Coach(){
        super();
    }

    public Coach(String fullName, String email, String birthDate, String gender, String role, boolean isPersonalTrainer,
                 boolean isDietist, String certificationUri){
        super(fullName, email, birthDate, gender, role);
        if(isPersonalTrainer)
            this.isPersonalTrainer = true;
        if(isDietist)
            this.isDietist = true;

        this.certificationUri = certificationUri;
    }



}
