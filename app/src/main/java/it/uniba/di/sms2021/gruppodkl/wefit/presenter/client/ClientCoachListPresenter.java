package it.uniba.di.sms2021.gruppodkl.wefit.presenter.client;

import androidx.paging.PagedList;

import com.firebase.ui.firestore.paging.FirestorePagingOptions;

import java.util.HashMap;
import java.util.Map;

import it.uniba.di.sms2021.gruppodkl.wefit.adapter.ClientCoachListAdapter;
import it.uniba.di.sms2021.gruppodkl.wefit.contract.client.ClientCoachListContract;
import it.uniba.di.sms2021.gruppodkl.wefit.db.ClientDAO;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Client;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Coach;
import it.uniba.di.sms2021.gruppodkl.wefit.utility.Keys;


public class ClientCoachListPresenter implements ClientCoachListContract.Presenter, ClientDAO.ClientDAOCallbacks {

    private final ClientCoachListContract.View mView;
    private final Client mClient;


    public ClientCoachListPresenter(ClientCoachListContract.View mView, Client currentClient) {
        this.mView = mView;
        mClient = currentClient;
    }

    public ClientCoachListAdapter getAdapter(){

        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(10)
                .setPrefetchDistance(5)
                .setPageSize(10)
                .build();

        FirestorePagingOptions<Coach> options = new FirestorePagingOptions.Builder<Coach>()
                .setQuery(ClientDAO.listAllCoach(), config, Coach.class).build();

        return new ClientCoachListAdapter(options, this);
    }

    @Override
    public void openCoachProfile(Coach coach) {
        mView.openCoachProfileWithMail(coach.email);
    }


    public void sendRequestToCoach(Coach coach){
        Map<String, Object> map = new HashMap<>();
        map.put(Keys.Request.NAME, mClient.fullName);
        map.put(Keys.Request.MAIL, mClient.email);
        map.put(Keys.Request.IMAGE, mClient.image);
        ClientDAO.requestToCoach(mClient, coach, map, this);
    }

    @Override
    public void requestSent(boolean isSuccessful) {
        if(!isSuccessful)
            mView.onFailure();
        else
            mView.onSuccess();
    }

    @Override
    public void requestAlreadySent(){
        mView.onRequestSent();
    }


}
