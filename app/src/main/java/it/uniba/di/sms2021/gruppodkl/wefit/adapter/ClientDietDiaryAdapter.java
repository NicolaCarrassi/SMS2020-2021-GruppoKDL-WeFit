package it.uniba.di.sms2021.gruppodkl.wefit.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import it.uniba.di.sms2021.gruppodkl.wefit.R;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Meal;
import it.uniba.di.sms2021.gruppodkl.wefit.viewholder.MealViewHolder;

public class ClientDietDiaryAdapter extends FirestoreRecyclerAdapter<Meal, MealViewHolder> {

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options opzioni
     */
    public ClientDietDiaryAdapter(@NonNull FirestoreRecyclerOptions<Meal> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MealViewHolder holder, int position, @NonNull Meal model) {
        holder.setValues(model);
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.meal_list_item, parent, false);

        return  new MealViewHolder(view);
    }
}
