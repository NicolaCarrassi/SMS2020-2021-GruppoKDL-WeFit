package it.uniba.di.sms2021.gruppodkl.wefit.utility;

public interface Keys {

    /**
     * L'interfaccia contiene le costanti per la registrazione
     */
    interface RegistrationKeys{
        String FIRST_NAME = "FIRST_NAME";
        String LAST_NAME = "LAST_NAME";
        String EMAIL = "EMAIL";
        String PASSWORD = "PASSWORD";
        String BIRTH_DATE = "BIRTH_DATE";
        String GENDER = "GENDER";
        String ROLE = "ROLE";
    }


    /**
     * L'interfaccia contiene le costanti per la registrazione
     * del coach
     */
    interface CoachRegistrationKeys{
        String IS_PERSONAL_TRAINER = "IS_PERSONAL_TRAINER";
        String IS_DIETICIAN = "IS_DIETICIAN";
        String ATTACHED_FILE = "ATTACHED_FILE";
    }


    /**
     * L'interfaccia contiene le costanti per la registrazione
     * del cliente
     */
    interface ClientRegistrationKeys{
        String HEIGHT = "HEIGHT";
        String WEIGHT = "WEIGHT";
        String OBJECTIVE = "OBJECTIVE";
    }

    /**
     * Costanti per il genere
     */
    interface Gender{
        String MALE = "Male";
        String FEMALE = "Female";
    }

    /**
     * Costanti per il ruolo nella piattaforma
     */
    interface Role{
        String CLIENT = "Client";
        String COACH = "Coach";
    }

    /**
     * Costanti per l'obiettivo nella
     * piattaforma
     */
    interface Objectives{
        String FIT_OBJECTIVE = "Shape definition";
        String GAIN_MASS = "Gain mass";
        String LOSE_WEIGHT = "Lose weight";
    }

    /**
     * Costanti per le collections del db
     */
    interface Collections{
        String USERS = "Users";
        String CERTIFICATION = "certifications";
        String IMAGES = "images";
        String WEIGHT = "weight";
        String RATINGS = "ratings";
    }

    interface RatingInfo{
        String RATE = "rate";
        String MESSAGE = "message";
        String CLIENT = "client";
        String CLIENT_NAME = "clientFullName";
    }


}
