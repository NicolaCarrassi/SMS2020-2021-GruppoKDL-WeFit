package it.uniba.di.sms2021.gruppodkl.wefit.presenter.client;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.uniba.di.sms2021.gruppodkl.wefit.contract.client.ClientProgressContract;
import it.uniba.di.sms2021.gruppodkl.wefit.db.ClientDAO;
import it.uniba.di.sms2021.gruppodkl.wefit.db.CoachDAO;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Client;

public class ClientProgressPresenter implements ClientProgressContract.Presenter, ClientDAO.WeightsLoaded {
    private final ClientProgressContract.View mView;

    public ClientProgressPresenter(ClientProgressContract.View view){
        this.mView = view;
    }

    @Override
    public void findUserData(String clientMail) {
        ClientDAO.getAllWeights(clientMail, this);
    }

    @Override
    public void onWeightsLoaded(List<Float> weightList, List<String> dateList) {
        List<Date> listDate = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");


        for (String date: dateList) {
            try {
                listDate.add(formatter.parse(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if( !weightList.isEmpty())
            mView.onClientDataReceived(weightList, listDate);
        else
            mView.onFailure();
    }
}
