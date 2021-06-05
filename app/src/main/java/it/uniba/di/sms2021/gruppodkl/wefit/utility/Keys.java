package it.uniba.di.sms2021.gruppodkl.wefit.utility;

public interface Keys {

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
        String REQUESTS = "requests";
        String TRAINING = "training";
        String EXERCISES = "exercises";
    }

    interface RatingInfo{
        String RATE = "rate";
        String MESSAGE = "message";
        String CLIENT = "client";
        String CLIENT_NAME = "clientFullName";
    }

    interface Request{
        String MAIL = "email";
        String NAME = "fullName";
        String IMAGE = "image";
    }

    interface WeekDay{
        int SUNDAY = 0;
        int MONDAY = 1;
        int TUESDAY = 2;
        int WEDNESDAY = 3;
        int THURSDAY = 4;
        int FRIDAY = 5;
        int SATURDAY = 6;
        int NOT_SET = 7;
    }


}
