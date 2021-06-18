package it.uniba.di.sms2021.gruppokdl.wefit.client.presenter;

import it.uniba.di.sms2021.gruppokdl.wefit.client.contract.ClientDietListContract;

public class ClientDietListPresenter implements ClientDietListContract.Presenter {

    private final ClientDietListContract.View mView;


    public ClientDietListPresenter(ClientDietListContract.View view){this.mView = view;}

    @Override
    public void onWeekDaySelected(String weekDay) {
        if(weekDay != null)
            mView.showDietOfTheDay(weekDay);
    }
}
