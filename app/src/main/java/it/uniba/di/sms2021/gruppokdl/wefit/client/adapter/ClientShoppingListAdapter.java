package it.uniba.di.sms2021.gruppokdl.wefit.client.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.uniba.di.sms2021.gruppodkl.wefit.R;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Meal;
import it.uniba.di.sms2021.gruppodkl.wefit.viewholder.ShoppingListItemViewHolder;

public class ClientShoppingListAdapter extends RecyclerView.Adapter<ShoppingListItemViewHolder> {

    private List<Meal> mModel;

    public ClientShoppingListAdapter(Map<String, Integer> mealMap){
        mModel = new ArrayList<>();
        for(String key : mealMap.keySet())
            mModel.add(new Meal(key, mealMap.get(key)));
    }


    @NonNull
    @Override
    public ShoppingListItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View layout = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shopping_list_item,parent,false);

        return new ShoppingListItemViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingListItemViewHolder holder, int position) {
        holder.setValues(mModel.get(position));
    }

    @Override
    public int getItemCount() {
        return mModel.size();
    }



}
