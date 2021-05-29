package it.uniba.di.sms2021.gruppodkl.wefit.presenter.fragment;

import android.net.Uri;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

import it.uniba.di.sms2021.gruppodkl.wefit.contract.fragment.ProfileFragmentContract;
import it.uniba.di.sms2021.gruppodkl.wefit.model.User;
import it.uniba.di.sms2021.gruppodkl.wefit.utility.Keys;

public class ProfileFragmentPresenter implements ProfileFragmentContract.Presenter {

    private final ProfileFragmentContract.View mView;

    public ProfileFragmentPresenter(ProfileFragmentContract.View view){
        this.mView = view;
    }

    @Override
    public void saveImage(Uri uri, User user) {

        StorageReference fileRef = FirebaseStorage.getInstance().getReference(Keys.Collections.IMAGES)
                .child(System.currentTimeMillis() + "." + mView.getFileExtension(uri));

        fileRef.putFile(uri)
                .addOnSuccessListener(taskSnapshot -> {
                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();

                    while(!uriTask.isComplete());

                    Uri resultUri = uriTask.getResult();

                    Map<String, Object> map = new HashMap<>();
                    assert resultUri != null;
                    String imageUri = resultUri.toString();
                    map.put(User.UserKeys.IMAGE, imageUri);


                    //inserisco l'immagine nel db
                    FirebaseFirestore.getInstance().collection(Keys.Collections.USERS).document(user.email)
                            .update(map);
                    user.setImage(imageUri);
                });

    }

    @Override
    public void updateUser(Map<String, Object> map, User user) {

        FirebaseFirestore.getInstance().collection(Keys.Collections.USERS).document(user.email)
                .update(map);

    }

}





