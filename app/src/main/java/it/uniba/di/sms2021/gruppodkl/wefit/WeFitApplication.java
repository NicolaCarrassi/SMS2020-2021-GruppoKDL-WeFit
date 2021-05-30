package it.uniba.di.sms2021.gruppodkl.wefit;

import android.app.Application;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.widget.Toolbar;

import it.uniba.di.sms2021.gruppodkl.wefit.model.User;

public class WeFitApplication extends Application {

    public interface OpenDrawer{
        void openDrw();
        void goBack();
    }

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setToolbar(View view, OpenDrawer activity){
        Toolbar mToolbar = view.findViewById(R.id.toolbar);
        mToolbar.inflateMenu(R.menu.toolbar_menu);
        mToolbar.setNavigationIcon(R.drawable.back_24);
        MenuItem mDrawable = mToolbar.getMenu().findItem(R.id.action_menu);

        mDrawable.setOnMenuItemClickListener(item -> {
            activity.openDrw();
            return false;
        });

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.goBack();
            }
        });
    }

}
