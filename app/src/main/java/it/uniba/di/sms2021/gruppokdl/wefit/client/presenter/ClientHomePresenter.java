package it.uniba.di.sms2021.gruppokdl.wefit.client.presenter;


import java.util.List;

import it.uniba.di.sms2021.gruppokdl.wefit.client.contract.ClientHomeContract;
import it.uniba.di.sms2021.gruppokdl.wefit.db.UserDAO;
import it.uniba.di.sms2021.gruppokdl.wefit.utility.Keys;

public class ClientHomePresenter implements ClientHomeContract.Presenter, UserDAO.TrainingLoaded {

    private final ClientHomeContract.View mView;


    public ClientHomePresenter(ClientHomeContract.View view){this.mView = view;}

    @Override
    public void loadTrainingInformation(String clientMail) {
        UserDAO.loadClientTrainingInformation(clientMail, this);
    }

    @Override
    public void trainingLoaded(List<String> trainingMade, List<String> trainingAssigned) {
        int trainingTotal = trainingAssigned.size();
        int trainingCompleted = 0;

        for(String item : trainingMade)
            if(trainingAssigned.contains(item))
                trainingCompleted++;


        if(trainingTotal == 0)
            mView.userCompletedTrainings(trainingCompleted, trainingCompleted, Keys.CompletedFlags.NO_DENOMINATOR);
        else if(trainingCompleted == 0)
            mView.userCompletedTrainings(trainingCompleted, trainingCompleted, Keys.CompletedFlags.NO_NUMERATOR);
        else
            mView.userCompletedTrainings(trainingCompleted, trainingTotal, Keys.CompletedFlags.CORRECT);
    }
}
