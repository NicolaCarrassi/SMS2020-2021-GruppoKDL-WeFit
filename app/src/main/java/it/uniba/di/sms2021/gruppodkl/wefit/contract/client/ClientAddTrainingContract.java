package it.uniba.di.sms2021.gruppodkl.wefit.contract.client;

import java.util.List;

public interface ClientAddTrainingContract {

    interface View{
        void trainingListNotEmpty(List<String> trainingNames);
        void emptyTrainingList();
        void onTrainingAdded();
    }

    interface Presenter{
        void loadTrainingInformation(String clientMail);
        void registerTrainingComplete(String clientMail, String trainingName, String date);
    }
}
