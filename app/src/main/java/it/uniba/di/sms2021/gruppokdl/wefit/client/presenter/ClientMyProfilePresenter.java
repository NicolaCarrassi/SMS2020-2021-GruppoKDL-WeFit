package it.uniba.di.sms2021.gruppokdl.wefit.client.presenter;

import android.net.Uri;

import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

import it.uniba.di.sms2021.gruppokdl.wefit.client.contract.ClientProfileFragmentContract;
import it.uniba.di.sms2021.gruppokdl.wefit.db.UserDAO;
import it.uniba.di.sms2021.gruppokdl.wefit.model.Client;
import it.uniba.di.sms2021.gruppokdl.wefit.model.User;
import it.uniba.di.sms2021.gruppokdl.wefit.utility.Keys;

public class ClientMyProfilePresenter implements ClientProfileFragmentContract.Presenter, User.MyImageBitmapCallback {

    private final ClientProfileFragmentContract.View mView;

    public ClientMyProfilePresenter(ClientProfileFragmentContract.View view){
        this.mView = view;
    }

    @Override
    public void saveImage(Uri uri, Client client) {

        if(client.image != null){
            FirebaseStorage.getInstance().getReferenceFromUrl(client.image).delete();
        }

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
                    client.setImage(imageUri);
                    UserDAO.update(client, map);
                    client.createImageBitmap(this);
                });

    }

    @Override
    public void updateUser(Map<String, Object> map, Client client) {
        UserDAO.update(client, map);
    }

    @Override
    public void handleCallback() {
    }
}





