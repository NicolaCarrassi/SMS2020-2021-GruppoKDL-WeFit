package it.uniba.di.sms2021.gruppokdl.wefit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import it.uniba.di.sms2021.gruppokdl.wefit.R;
import it.uniba.di.sms2021.gruppokdl.wefit.contract.DietListBaseContract;
import it.uniba.di.sms2021.gruppokdl.wefit.utility.DayOfTheWeek;
import it.uniba.di.sms2021.gruppokdl.wefit.viewholder.DietDayViewHolder;

public class DietListAdapter extends RecyclerView.Adapter<DietDayViewHolder> implements DietDayViewHolder.DietDayCallbacks {

    private final List<String> mDaysList;
    private final DietListBaseContract.Presenter mPresenter;

    public DietListAdapter(Context context, DietListBaseContract.Presenter presenter){
        mDaysList = new ArrayList<>();
        mDaysList.add(context.getResources().getString(R.string.sunday));
        mDaysList.add(context.getResources().getString(R.string.monday));
        mDaysList.add(context.getResources().getString(R.string.tuesday));
        mDaysList.add(context.getResources().getString(R.string.wednesday));
        mDaysList.add(context.getResources().getString(R.string.thursday));
        mDaysList.add(context.getResources().getString(R.string.friday));
        mDaysList.add(context.getResources().getString(R.string.saturday));

        mPresenter = presenter;
    }

    @NonNull
    @Override
    public DietDayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View layout = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.diet_day_list_item,parent,false);

        return new DietDayViewHolder(layout, this);
    }

    @Override
    public void onBindViewHolder(@NonNull DietDayViewHolder holder, int position) {
        holder.setValues(mDaysList.get(position));
    }

    @Override
    public int getItemCount() {
        return mDaysList.size();
    }

    @Override
    public void goToDietDay(int position) {
       String dayOfTheWeek = DayOfTheWeek.convertPositionToDayOfTheWeek(position);
       mPresenter.onWeekDaySelected(dayOfTheWeek);
    }


}
