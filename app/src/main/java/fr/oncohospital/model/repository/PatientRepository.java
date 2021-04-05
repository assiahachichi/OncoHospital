package fr.oncohospital.model.repository;

import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.HashMap;

import fr.oncohospital.R;
import fr.oncohospital.model.allData.remoteDataSource.RequestRemoteData;
import fr.oncohospital.model.allData.remoteDataSource.ServerUrls;
import fr.oncohospital.model.security.Encrypt;
import fr.oncohospital.model.session.Patient;
import fr.oncohospital.model.session.PatientConnected;
import fr.oncohospital.ui.MenuActivity;

/**
 * Created by Assia HACHICHI on 25/03/2021
 * @author Assia HACHICHI
 */
public class PatientRepository {
    private Patient patient;
    private AppCompatActivity activity;

    /**
     *
     * @param activity
     */
    public PatientRepository(AppCompatActivity activity){
        this.activity = activity;
    }

    public Patient getPatient(String idPatient, String password) {
        LoginAsyncTask lat = new LoginAsyncTask(activity);
        lat.execute(idPatient, Encrypt.md5(password));
        return null;
    }

    class LoginAsyncTask extends AsyncTask<String, Integer, String> {
        private WeakReference<AppCompatActivity> activityWeakReference;

        LoginAsyncTask(AppCompatActivity activity){
            activityWeakReference = new WeakReference<AppCompatActivity>(activity);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            AppCompatActivity activity = activityWeakReference.get();
            if (activity == null || activity.isFinishing()){ return;}
        }

        protected String doInBackground(String... args) {
            if (args.length == 2) {
                String idPatient = args[0];
                String password = args[1];
                RequestRemoteData requestHandler = new RequestRemoteData();


                HashMap<String, String> params = new HashMap<>();
                params.put("idPatient", idPatient);
                params.put("password", password);

                try {
                    return RequestRemoteData.sendPostRequest(ServerUrls.URL_LOGIN_PATIENT, params);
                } catch (Exception e) {
                    AppCompatActivity activity = activityWeakReference.get();
                    if (activity == null || activity.isFinishing()){ return null;}

                    return "NETWORK_PROBLEM";
                }
            }else{
                return null;
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result.equals("NETWORK_PROBLEM"))Toast.makeText(activity, R.string.toast_deconnected, Toast.LENGTH_LONG).show();

                AppCompatActivity activity = activityWeakReference.get();
            if (activity == null || activity.isFinishing()){ return;}

            if ((result == null) ||(result.contains("Invalid"))){
                Toast.makeText(activity, R.string.toast_invalid, Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                JSONObject obj = new JSONObject(result);

                if (!obj.getBoolean("error")) {
                    Toast.makeText(activity, obj.getString("message"), Toast.LENGTH_SHORT).show();

                    JSONObject patientJson = obj.getJSONObject("patient");
                    Patient patient = new Patient(
                            patientJson.getString("idPatient"),
                            patientJson.getString("name"),
                            patientJson.getString("firstname"),
                            patientJson.getString("email"),
                            patientJson.getString("tel")
                    );

                    PatientConnected patientConnected = PatientConnected.getInstance(activity);
                    patientConnected.saveSharedPreferences(patient);
                    activity.startActivity(new Intent(activity, MenuActivity.class));

                } else {
                    Toast.makeText(activity, R.string.toast_invalid, Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
