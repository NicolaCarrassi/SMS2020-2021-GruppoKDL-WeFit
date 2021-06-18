package it.uniba.di.sms2021.gruppodkl.wefit.utility;

public interface UtilityStrings {

    /**
     * Stringa contente l'espressione regolare della password
     */
    String PASSOWRD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,20}";

    /**
     * Stringa contenente l'espressione regolare per nome e cognome
     */
    String NAME_REGEX = "[a-zA-Z]+";
}
