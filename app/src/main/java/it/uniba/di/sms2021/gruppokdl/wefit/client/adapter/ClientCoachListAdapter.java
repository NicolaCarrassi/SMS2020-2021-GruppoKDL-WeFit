package it.uniba.di.sms2021.gruppokdl.wefit.client.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.firebase.ui.firestore.paging.FirestorePagingAdapter;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;

import it.uniba.di.sms2021.gruppokdl.wefit.R;
import it.uniba.di.sms2021.gruppokdl.wefit.contract.client.ClientCoachListContract;
import it.uniba.di.sms2021.gruppokdl.wefit.model.Coach;
import it.uniba.di.sms2021.gruppokdl.wefit.client.viewholder.ClientCoachListViewHolder;

public class ClientCoachListAdapter extends FirestorePagingAdapter<Coach, ClientCoachListViewHolder>
        implements ClientCoachListViewHolder.ItemClickListener{

    private final ClientCoachListContract.Presenter mPresenter;
    private boolean mIsClickable = true;

    /**
     * Construct a new FirestorePagingAdapter from the given {@link FirestorePagingOptions}.
     *
     * @param options opzioni
     */
    public ClientCoachListAdapter(FirestorePagingOptions<Coach> options, ClientCoachListContract.Presenter presenter) {
        super(options);
        mPresenter = presenter;
    }

    @Override
    protected void onBindViewHolder(@NonNull ClientCoachListViewHolder holder, int position, @NonNull Coach model) {
        holder.setValues(model);
    }

    @NonNull
    @Override
    public ClientCoachListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.client_list_coach_item, parent, false);

        return new ClientCoachListViewHolder(view, this);
    }


    @Override
    public void onItemClick(Coach coach) {
        if(mIsClickable) {
            mIsClickable = false; //blocco i click successivi
            mPresenter.sendRequestToCoach(coach);
        } else
            mPresenter.requestAlreadySent();
    }

    @Override
    public void onProfileOpened(Coach coach) {
        mPresenter.openCoachProfile(coach);
    }

    public void setClickable(boolean clickable){
        this.mIsClickable = clickable;
    }





}
