package it.uniba.di.sms2021.gruppodkl.wefit.contract.client;

public interface ClientHomeContract {

    interface View{
        void userCompletedTrainings(int completedTrainings, int trainingsNumber, int flag);
    }

    interface Presenter{
        void loadTrainingInformation(String clientMail);
    }

}
