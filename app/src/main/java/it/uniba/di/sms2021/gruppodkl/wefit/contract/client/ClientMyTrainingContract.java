package it.uniba.di.sms2021.gruppodkl.wefit.contract.client;

import it.uniba.di.sms2021.gruppodkl.wefit.adapter.ClientMyTrainingAdapter;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Training;

public interface ClientMyTrainingContract {

    interface View{
        void openTrainingSchedule(Training training);
    }

    interface Presenter{
        ClientMyTrainingAdapter getAdapter(String clientEmail);
        void openTrainingSpecification(Training training);
    }

}
