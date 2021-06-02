package it.uniba.di.sms2021.gruppodkl.wefit.recyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class CustomRecyclerView extends RecyclerView {

    /**
     * View che verrà mostrata in caso non vi siano elementi da mostrare all'utente
     */
    private View mEmpty;

    private final AdapterDataObserver observer = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            checkIfEmpty();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            checkIfEmpty();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            checkIfEmpty();
        }
    };

    public CustomRecyclerView(@NonNull Context context) {
        super(context);
    }

    public CustomRecyclerView(@NonNull  Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomRecyclerView(@NonNull Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * Il metodo permette di controllare che la RecyclerView sia vuota
     */
    private void checkIfEmpty(){
        if(mEmpty != null && getAdapter() != null){
            final boolean emptyViewVisible = getAdapter().getItemCount() == 0;

            mEmpty.setVisibility(emptyViewVisible? VISIBLE : GONE);
            setVisibility(emptyViewVisible? GONE: VISIBLE);
        }
    }

    @Override
    public void setAdapter(Adapter adapter) {
        final Adapter oldAdapter = getAdapter();

        if(oldAdapter != null)
            oldAdapter.unregisterAdapterDataObserver(observer);

        super.setAdapter(adapter);

        if(adapter != null)
            adapter.registerAdapterDataObserver(observer);

        checkIfEmpty();
    }

    /**
     * Il metodo permette di impostare la view da visualizzare in caso di RecyclerView
     * vuota
     */
    public void setEmptyView(View emptyView){
        this.mEmpty = emptyView;
        checkIfEmpty();
    }

}
