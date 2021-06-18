package it.uniba.di.sms2021.gruppodkl.wefit.presenter.coach;

import android.net.Uri;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

import it.uniba.di.sms2021.gruppodkl.wefit.contract.coach.CoachProfileContract;
import it.uniba.di.sms2021.gruppodkl.wefit.db.UserDAO;
import it.uniba.di.sms2021.gruppodkl.wefit.model.Coach;
import it.uniba.di.sms2021.gruppodkl.wefit.model.User;
import it.uniba.di.sms2021.gruppodkl.wefit.utility.Keys;

/**
 * La classe rappresenta il presenter per il profilo del Coach
 */
public class CoachProfilePresenter implements CoachProfileContract.Presenter, User.MyImageBitmapCallback {

    /**
     * View che sottoscrive il contract
     */
    private final CoachProfileContract.View mView;

    /**
     * Costruttore del Presenter
     *
     * @param view View che sottoscrive il contract
     */
    public CoachProfilePresenter(CoachProfileContract.View view){ this.mView = view;}


    @Override
    public void saveImage(Uri uri, Coach coach) {

        if(coach.image != null)
            FirebaseStorage.getInstance().getReferenceFromUrl(coach.image).delete();

        StorageReference reference = FirebaseStorage.getInstance().getReference(Keys.Collections.IMAGES)
                .child(System.currentTimeMillis() + "." + mView.getFileExtension(uri));

        reference.putFile(uri)
                .addOnSuccessListener(taskSnapshot -> {
                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();

                    while (!uriTask.isComplete());

                    Uri result = uriTask.getResult();

                    Map<String, Object> map = new HashMap<>();
                    assert result != null;

                    String image = result.toString();
                    map.put(Coach.CoachKeys.IMAGE, image);

                    FirebaseFirestore.getInstance().collection(Keys.Collections.USERS)
                            .document(coach.email).update(map);
                    coach.setImage(image);
                    coach.createImageBitmap(this);
                });

    }

    @Override
    public void updateCoach(Map<String, Object> map, Coach coach) {
       UserDAO.update(coach,map);
    }

    @Override
    public void uploadFile(Coach coach) {
        if(coach.certificationUri != null) {
            StorageReference ref = FirebaseStorage.getInstance().getReference(Keys.Collections.CERTIFICATION)
                    .child(System.currentTimeMillis() + "." + mView.getFileExtension(Uri.parse(coach.certificationUri)));

            ref.putFile(Uri.parse(coach.certificationUri))
                    .addOnSuccessListener(taskSnapshot -> {
                       Task<Uri> task = taskSnapshot.getStorage().getDownloadUrl();

                       while (!task.isComplete());

                       Uri uri = task.getResult();

                       if(uri != null) {
                           Map<String, Object> map = new HashMap<>();
                           map.put(Coach.CoachKeys.CERTIFICATION, uri.toString());

                           UserDAO.update(coach, map);
                       }
                    });
        }
    }


    @Override
    public void handleCallback() {
    }


}
