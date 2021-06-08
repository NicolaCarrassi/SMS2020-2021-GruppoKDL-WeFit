package it.uniba.di.sms2021.gruppodkl.wefit.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import it.uniba.di.sms2021.gruppodkl.wefit.R;
import it.uniba.di.sms2021.gruppodkl.wefit.viewholder.DietDayViewHolder;

public class DietListAdapter extends RecyclerView.Adapter<DietDayViewHolder> implements DietDayViewHolder.DietDayCallbacks {

    private final List<String> sDaysList;

    public DietListAdapter(Context context){
        sDaysList = new ArrayList<>();
        sDaysList.add(context.getResources().getString(R.string.sunday));
        sDaysList.add(context.getResources().getString(R.string.monday));
        sDaysList.add(context.getResources().getString(R.string.tuesday));
        sDaysList.add(context.getResources().getString(R.string.wednesday));
        sDaysList.add(context.getResources().getString(R.string.thursday));
        sDaysList.add(context.getResources().getString(R.string.friday));
        sDaysList.add(context.getResources().getString(R.string.saturday));
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
        holder.setValues(sDaysList.get(position));
    }

    @Override
    public int getItemCount() {
        return sDaysList.size();
    }

    @Override
    public void goToDietDay(int position) {
       //TODO
    }
}
