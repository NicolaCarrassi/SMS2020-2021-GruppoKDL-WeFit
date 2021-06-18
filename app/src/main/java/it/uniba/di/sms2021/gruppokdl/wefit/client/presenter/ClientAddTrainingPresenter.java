package it.uniba.di.sms2021.gruppokdl.wefit.presenter.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.uniba.di.sms2021.gruppokdl.wefit.contract.client.ClientAddTrainingContract;
import it.uniba.di.sms2021.gruppokdl.wefit.db.TrainingDAO;

public class ClientAddTrainingPresenter implements ClientAddTrainingContract.Presenter, TrainingDAO.TrainingCallbacks {

    private final ClientAddTrainingContract.View mView;

    public ClientAddTrainingPresenter(ClientAddTrainingContract.View view){
        mView = view;
    }


    @Override
    public void loadTrainingInformation(String clientMail) {
        TrainingDAO.getAllTrainingNames(clientMail, this);
    }

    @Override
    public void registerTrainingComplete(String clientMail, String trainingName, String date) {
        Map<String, Object> map = new HashMap<>();
        map.put(TrainingDAO.RegisterTrainingKeys.TRAINING_NAME, trainingName);
        map.put(TrainingDAO.RegisterTrainingKeys.DATE, date);

       TrainingDAO.registerTrainingComplete(clientMail,map);
       mView.onTrainingAdded();
    }

    @Override
    public void onTrainingListLoaded(List<String> trainingNames) {
        if(trainingNames.size() > 0)
            mView.trainingListNotEmpty(trainingNames);
        else
            mView.emptyTrainingList();
    }


}
