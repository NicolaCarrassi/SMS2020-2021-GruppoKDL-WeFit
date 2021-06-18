package it.uniba.di.sms2021.gruppodkl.wefit.presenter.coach;

import it.uniba.di.sms2021.gruppodkl.wefit.contract.coach.CoachMyClientDietListContract;

public class CoachMyClientDietListPresenter implements CoachMyClientDietListContract.Presenter {

    private final CoachMyClientDietListContract.View mView;


    public CoachMyClientDietListPresenter(CoachMyClientDietListContract.View view){ this.mView = view;}


    @Override
    public void onWeekDaySelected(String weekDay) {
        if(weekDay != null)
            mView.showDietOfTheDay(weekDay);
    }
}
