package it.uniba.di.sms2021.gruppokdl.wefit.coach.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import it.uniba.di.sms2021.gruppodkl.wefit.R;
import it.uniba.di.sms2021.gruppodkl.wefit.contract.coach.CoachMyClientDietSpecificationContract;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Meal;
import it.uniba.di.sms2021.gruppodkl.wefit.viewholder.MealViewHolder;

public class CoachDietDayAdapter extends FirestoreRecyclerAdapter<Meal, MealViewHolder> implements  MealViewHolder.CoachDietCallbackInterface {

    private final CoachMyClientDietSpecificationContract.Presenter mPresenter;
    private RecyclerView.LayoutManager mLayoutManager;

    private boolean sIsVisible = false;
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options opzioni recycler
     */
    public CoachDietDayAdapter(@NonNull FirestoreRecyclerOptions<Meal> options, CoachMyClientDietSpecificationContract.Presenter presenter) {
        super(options);
        this.mPresenter = presenter;
    }

    @Override
    protected void onBindViewHolder(@NonNull MealViewHolder holder, int position, @NonNull Meal model) {
        holder.setValues(model);
    }


    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.meal_list_item, parent,false);

        return new MealViewHolder(view, this);
    }

    @Override
    public void deleteMeal(int position) {
        mPresenter.removeMeal(getItem(position));
    }


    public void showDeleteButtons(){
        if(mLayoutManager != null){
            for(int i = 0; i < getItemCount(); i++){
                View view = mLayoutManager.findViewByPosition(i);
                if(view != null && view.getVisibility() == View.VISIBLE)
                    if(!sIsVisible)
                        view.findViewById(R.id.delete_meal_button).setVisibility(View.VISIBLE);
                    else
                        view.findViewById(R.id.delete_meal_button).setVisibility(View.GONE);
            }
            sIsVisible = !sIsVisible;
        }
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        if(recyclerView.getLayoutManager() != null)
            mLayoutManager = recyclerView.getLayoutManager();
    }
}
