package fr.oncohospital.model.session;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import fr.oncohospital.MainActivity;
import fr.oncohospital.model.allData.bd.entities.ProfileEntity;
import fr.oncohospital.ui.MenuActivity;
import fr.oncohospital.ui.profile.ProfileAdapter;
import fr.oncohospital.ui.profile.ProfileViewModel;

/**
 * Created by Assia HACHICHI on 10/02/2021
 * @author Assia HACHICHI
 */
/**
 * This class contains data about a connected patient, it's a singleton.
 */
public class PatientConnected {
    private static final String SHARED_PREF_NAME = "OncoHospital";
    private static final String KEY_NAME = "keyName";
    private static final String KEY_FIRSTNAME = "keyFirstname";
    private static final String KEY_EMAIL = "keyEmail";
    private static final String KEY_TEL = "keyTel";
    private static final String KEY_ID_PATIENT = "keyIdPatient";

    private static PatientConnected mInstance;
    private Context mCtx;
    private ProfileAdapter adapter;
    private ProfileViewModel profileViewModel;


    /**
     * This method is a private constructeur, which initialise the attribute context
     * @param context represents the application context
     */
    private PatientConnected(Context context) {
        mCtx = context;
    }

    /**
     * This method retourns
     * @param context
     * @return
     */

    public static synchronized PatientConnected getInstance(Context context) {
        if ((mInstance == null)&&(context!=null)) {
            mInstance = new PatientConnected(context);
        }
        return mInstance;
    }

    /**
     *
     * @param idPatient
     * @return
     */
    public ProfileEntity getProfileEntity(String idPatient){
        if (adapter != null) {
            ProfileEntity profile=  adapter.getProfile();
            if (profile == null){
                setProfileEntity(idPatient);
                profile = adapter.getProfile();
            }
            return profile;
        }
        return null;
    }

    /**
     *
     * @param idPatient
     */
    private void setProfileEntity(String idPatient){
        profileViewModel.setProfile(idPatient);
    }

    /**
     *
     * @return
     */
    public ProfileAdapter getAdapter() {
        return this.adapter;
    }


    /**
     * This method returns a patient instance, saved into shared preferences.
     * @return returns a patient instance saved.
     */
    public Patient getPatient() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        Patient  p = new Patient(
                sharedPreferences.getString(KEY_ID_PATIENT, null),
                sharedPreferences.getString(KEY_NAME, null),
                sharedPreferences.getString(KEY_FIRSTNAME, null),
                sharedPreferences.getString(KEY_EMAIL, null),
                sharedPreferences.getString(KEY_TEL, null)
        );
        return p;
    }

    /**
     *
     * @param activity
     * @param profileViewModel
     */
    public void initProfile(AppCompatActivity activity, ProfileViewModel profileViewModel){
        this.profileViewModel = profileViewModel;

        if (this.isLoggedIn()) {
            Patient patient = this.getPatient();
            if (adapter == null) {
                adapter = new ProfileAdapter(profileViewModel);
            }
            ProfileEntity profileEntity = adapter.getProfile();
            if ((profileEntity == null)||(!profileEntity.getIdPatient().equals(patient.getIdPatient()))) {
                profileViewModel.getProfile(patient.getIdPatient()).observe(activity, new Observer<ProfileEntity>() {
                    @Override
                    public void onChanged(@Nullable ProfileEntity profileEntity) {
                        adapter.setProfile(profileEntity);
                        if (profileEntity != null)MenuActivity.setLanguage(profileEntity.getLanguage());
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }}

    /**
     * This method saves patient data, into shared preferences, with strong consistency guarantees.
     * @param patient represents an instance of the class Petient to save
     */
    public void saveSharedPreferences(Patient patient) {
        if (patient == null) return;
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_ID_PATIENT, patient.getIdPatient());
        editor.putString(KEY_NAME, patient.getName());
        editor.putString(KEY_FIRSTNAME, patient.getFirstname());
        editor.putString(KEY_EMAIL, patient.getEmail());
        editor.putString(KEY_TEL, patient.getTel());
        editor.apply();
    }

    /**
     * This method checks if the patient is already logged
     * @return returns a boolean which represents if the patient logged.
     */
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_ID_PATIENT, null) != null;
    }

    /**
     * This method logout a patient, by removing all saved data into shared preferences,
     * then it launches the main UI.
     */
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_ID_PATIENT, null);
        editor.remove(KEY_ID_PATIENT);
        editor.remove(KEY_NAME);
        editor.remove(KEY_FIRSTNAME);
        editor.remove(KEY_EMAIL);
        editor.remove(KEY_TEL);
        editor.clear();
        editor.apply();
        editor.commit();
        Intent intent = new Intent(mCtx, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mCtx.startActivity(intent);
    }

    /**
     * This method clear
     */
    public void clear() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_ID_PATIENT, null);
        editor.remove(KEY_ID_PATIENT);
        editor.remove(KEY_NAME);
        editor.remove(KEY_FIRSTNAME);
        editor.remove(KEY_EMAIL);
        editor.remove(KEY_TEL);
        editor.clear();
        editor.apply();
        editor.commit();
    }

}


