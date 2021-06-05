package it.uniba.di.sms2021.gruppodkl.wefit.presenter.coach;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.uniba.di.sms2021.gruppodkl.wefit.contract.coach.CoachMyClientProfileContract;
import it.uniba.di.sms2021.gruppodkl.wefit.db.CoachDAO;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Client;

public class CoachMyClientProfilePresenter implements CoachMyClientProfileContract.Presenter, CoachDAO.ClientCallbacks {

    private final CoachMyClientProfileContract.View mView;

    public CoachMyClientProfilePresenter(CoachMyClientProfileContract.View view){
        this.mView = view;
    }


    @Override
    public void findUserData(String clientMail) {
        CoachDAO.getAllClientInfo(clientMail, this);
    }

    @Override
    public void failure() {
        mView.onFailure();
    }

    @Override
    public void success(Client client, List<Float> weightList, List<String> dateList) {
        List<Date> listDate = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        for (String date: dateList) {
            try {
                listDate.add(formatter.parse(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }


        if(client != null && !weightList.isEmpty())
            mView.onClientDataReceived(client, weightList, listDate);
        else
            mView.onFailure();
    }
}
