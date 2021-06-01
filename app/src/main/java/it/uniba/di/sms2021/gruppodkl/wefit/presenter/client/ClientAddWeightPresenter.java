package it.uniba.di.sms2021.gruppodkl.wefit.presenter.client;

import java.util.HashMap;
import java.util.Map;

import it.uniba.di.sms2021.gruppodkl.wefit.contract.client.ClientAddWeightContract;
import it.uniba.di.sms2021.gruppodkl.wefit.db.UserDAO;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Client;
import it.uniba.di.sms2021.gruppodkl.wefit.utility.Keys;

public class ClientAddWeightPresenter implements ClientAddWeightContract.Presenter {

    private final ClientAddWeightContract.View mView;

    public ClientAddWeightPresenter(ClientAddWeightContract.View view){ this.mView = view;}


    @Override
    public void addWeight(Client client, float weight) {
        Map<String, Object> map = new HashMap<>();
        map.put(Client.ClientKeys.WEIGHT, weight);
        UserDAO.addInSubCollection(client.email, Keys.Collections.WEIGHT,map);
        UserDAO.update(client, map);
        mView.onSuccess();
    }
}
