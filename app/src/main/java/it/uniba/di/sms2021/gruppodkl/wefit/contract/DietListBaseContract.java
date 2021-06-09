package it.uniba.di.sms2021.gruppodkl.wefit.contract;

public interface DietListBaseContract {

    interface View{
        void showDietOfTheDay(String weekDay);
    }

    interface Presenter{
        void onWeekDaySelected(String weekDay);
    }

}
