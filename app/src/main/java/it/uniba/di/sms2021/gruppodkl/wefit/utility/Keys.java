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
        String EXERCISES = "Exercises";
        String COMPLETED_TRAINING = "completed_training";
        String DIET = "diet";
        String MEALS = "meals";
    }

    interface RatingInfo{
        String RATE = "rate";
        String MESSAGE = "message";
        String CLIENT = "client";
        String CLIENT_NAME = "clientFullName";
        String DATE = "date";
    }

    interface Request{
        String MAIL = "email";
        String NAME = "fullName";
        String IMAGE = "image";
    }

    interface CompletedFlags{
        int NO_DENOMINATOR = 0;
        int NO_NUMERATOR = 1;
        int CORRECT = 2;
    }




}
