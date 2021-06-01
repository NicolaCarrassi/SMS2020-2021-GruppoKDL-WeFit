package it.uniba.di.sms2021.gruppodkl.wefit.fragment.client;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import it.uniba.di.sms2021.gruppodkl.wefit.R;
import it.uniba.di.sms2021.gruppodkl.wefit.contract.client.ClientCoachListContract;
import it.uniba.di.sms2021.gruppodkl.wefit.presenter.client.ClientCoachListPresenter;

public class ClientCoachListFragment extends Fragment implements ClientCoachListContract.View {

    private ClientCoachListContract.Presenter mPresenter;

    @Override
    public void onCreateView(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = new ClientCoachListPresenter(this);
        mPresenter.queryCoachList();

    }
}
