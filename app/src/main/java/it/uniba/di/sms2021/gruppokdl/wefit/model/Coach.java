package it.uniba.di.sms2021.gruppokdl.wefit.model;


public class Coach extends User {

    public interface CoachKeys extends UserKeys{
        String IS_PERSONAL_TRAINER = "isPersonalTrainer";
        String IS_DIETICIAN = "isDietician";
        String CERTIFICATION = "certificationUri";
    }

    public boolean isPersonalTrainer = false;
    public boolean isDietician = false;
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
            this.isDietician = true;

        this.certificationUri = certificationUri;
    }



}
