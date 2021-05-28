package it.uniba.di.sms2021.gruppodkl.wefit.utility;

public interface Keys {

    interface RegistrationKeys{
        String FIRST_NAME = "FIRST_NAME";
        String LAST_NAME = "LAST_NAME";
        String EMAIL = "EMAIL";
        String PASSWORD = "PASSWORD";
        String BIRTH_DATE = "BIRTH_DATE";
        String GENDER = "GENDER";
        String ROLE = "ROLE";
        String FULL_NAME = "FULL_NAME";
    }

    interface CoachRegistrationKeys{
        String IS_PERSONAL_TRAINER = "IS_PERSONAL_TRAINER";
        String IS_DIETICIAN = "IS_DIETICIAN";
        String ATTACHED_FILE = "ATTACHED_FILE";
    }

    interface ClientRegistrationKeys{
        String HEIGHT = "HEIGHT";
        String WEIGHT = "WEIGHT";
        String OBJECTIVE = "OBJECTIVE";
    }

    interface Gender{
        String MALE = "Male";
        String FEMALE = "Female";
    }

    interface Role{
        String CLIENT = "Client";
        String COACH = "Coach";
    }

}
