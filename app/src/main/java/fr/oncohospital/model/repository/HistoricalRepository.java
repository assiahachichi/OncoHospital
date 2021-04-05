package fr.oncohospital.model.repository;

import android.os.AsyncTask;

import java.util.HashMap;

import fr.oncohospital.model.allData.bd.entities.ProfileEntity;
import fr.oncohospital.model.allData.remoteDataSource.RequestRemoteData;
import fr.oncohospital.model.allData.remoteDataSource.ServerUrls;
import fr.oncohospital.model.session.Patient;
import fr.oncohospital.model.session.PatientConnected;
import fr.oncohospital.ui.myCalendarNotification.MyCalendar;

/**
 * Created by Assia HACHICHI on 25/03/2021
 */
public class HistoricalRepository {

    /**
     *
     */
    private HistoricalRepository() {
    }

    /**
     *
     */
    public static void getHistotic() {
        String idPatient;
        String lastRefresh;
        PatientConnected patientConnected = PatientConnected.getInstance(null);
        if ((patientConnected != null) &&(patientConnected.isLoggedIn())) {
            Patient patient = patientConnected.getPatient();
            ProfileEntity profile = patientConnected.getProfileEntity(patient.getIdPatient());
            lastRefresh = profile.getLastDateRefresh();

            HistoricAsyncTask task = new HistoricAsyncTask();
            task.execute(patient.getIdPatient(), lastRefresh);
        }
    }

    /**
     *
     */
    private static class HistoricAsyncTask extends AsyncTask<String, Integer, String> {

            HistoricAsyncTask() {
            }
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            protected String doInBackground(String... args) {
                String s = null;
                if (args.length == 2) {
                    String idPatient = args[0];
                    String date = args[1];


                    RequestRemoteData requestHandler = new RequestRemoteData();
                    String url;
                    HashMap<String, String> params = new HashMap<>();
                    params.put("idPatient", idPatient);
                    if ((!date.equals(""))&&(date != null)) {
                        params.put("date", date);
                    }
                    try {
                        s = RequestRemoteData.sendPostRequest(ServerUrls.URL_APPOINTMENT_HISTORIC, params);
                    } catch (Exception e) {

                        e.printStackTrace();
                        return null;
                    }
                }
                return s;
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                    MyCalendar.getInstance().convertStringToRendezVous(s);
            }
        }



    }

