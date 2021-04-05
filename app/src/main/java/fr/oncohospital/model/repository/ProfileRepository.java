package fr.oncohospital.model.repository;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import fr.oncohospital.model.allData.bd.entities.ProfileEntity;
import fr.oncohospital.model.allData.bd.room.OncoDatabase;
import fr.oncohospital.model.allData.bd.room.ProfileDao;

/**
 * Created by Assia HACHICHI on 24/03/2021
 * @author Assia HACHICHI
 */
public class ProfileRepository {
    private ProfileDao profileDao;
    private LiveData<List<ProfileEntity>> allProfiles;

    /**
     *
     * @param context
     */
    public ProfileRepository(Context context){
        OncoDatabase database = OncoDatabase.getInstance(context);
        profileDao = database.profileDao();
        allProfiles = profileDao.getAllProfiles();
    }

    /**
     *
     * @param profile
     */
    public void insert(ProfileEntity profile){new InsertProfileAsyncTask(profileDao).execute(profile);}

    /**
     *
     * @param profile
     */
    public void update(ProfileEntity profile){new UpdateProfileAsyncTask(profileDao).execute(profile);}

    /**
     *
     * @param profile
     */
    public void delete(ProfileEntity profile){new DeleteProfileAsyncTask(profileDao).execute(profile);}

    /**
     *
     * @param idPatient
     */
    public void setProfile(String idPatient){
        new SetProfileAsyncTask(profileDao).execute(idPatient);
    }

    /**
     *
     * @param idPatient
     * @return
     */
    public LiveData<ProfileEntity> getProfile(String idPatient){
        return profileDao.getProfile(idPatient);
    }

    /**
     *
     * @param idPatient
     * @return
     */
    public static ProfileEntity getDefaultProfile(String idPatient){
        return new ProfileEntity(idPatient, "fr", true, true,true,true, Color.GREEN, Color.BLUE, Color.YELLOW, 20,"");
    }

    /**
     *
     * @return
     */
    public LiveData<List<ProfileEntity>> getAllProfiles(){
        return profileDao.getAllProfiles();
    }

    /**
     *
     */
    private static class SetProfileAsyncTask extends AsyncTask<String, Void, Void> {
        private ProfileDao profileDao;
        private SetProfileAsyncTask(ProfileDao profileDao){
            this.profileDao = profileDao;
        }

        @Override
        protected Void doInBackground(String... strings) {
            String idPatient = strings[0];
            ProfileEntity profile = profileDao.setProfile(idPatient);
            if (profile == null){
                profile = getDefaultProfile(idPatient);
                profileDao.insert(profile);
            }
            return null;
        }
    }

    /**
     *
     */
    private static class InsertProfileAsyncTask extends AsyncTask<ProfileEntity, Void, Void> {
        private ProfileDao profileDao;
        private InsertProfileAsyncTask(ProfileDao profileDao){
            this.profileDao = profileDao;
        }

        @Override
        protected Void doInBackground(ProfileEntity... profileEntities) {
            profileDao.insert(profileEntities[0]);
            return null;
        }
    }

    /**
     *
     */
    private static class UpdateProfileAsyncTask extends AsyncTask<ProfileEntity, Void, Void> {
        private ProfileDao profileDao;
        private UpdateProfileAsyncTask(ProfileDao profileDao){
            this.profileDao = profileDao;
        }

        @Override
        protected Void doInBackground(ProfileEntity... profileEntities) {
            profileDao.update(profileEntities[0]);
            return null;
        }
    }

    /**
     *
     */
    private static class DeleteProfileAsyncTask extends AsyncTask<ProfileEntity, Void, Void> {
        private ProfileDao profileDao;
        private DeleteProfileAsyncTask(ProfileDao profileDao){
            this.profileDao = profileDao;
        }

        @Override
        protected Void doInBackground(ProfileEntity... profileEntities) {
            profileDao.delete(profileEntities[0]);
            return null;
        }
    }
}
