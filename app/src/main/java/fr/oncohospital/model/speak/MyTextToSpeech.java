package fr.oncohospital.model.speak;

import android.content.Context;
import android.os.Build;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

import fr.oncohospital.model.allData.bd.entities.ProfileEntity;
import fr.oncohospital.model.session.Patient;
import fr.oncohospital.model.session.PatientConnected;
import fr.oncohospital.ui.MenuActivity;

/**
 * Created by Assia HACHICHI on 04/04/2021
 */
public class MyTextToSpeech implements TextToSpeech.OnInitListener {

    private TextToSpeech textToSpeech;
    private Context context;
    private static MyTextToSpeech myTextToSpeech = null;

    public static MyTextToSpeech getInstance(Context context){
        if (myTextToSpeech == null) myTextToSpeech = new MyTextToSpeech(context);
        return myTextToSpeech;
    }

    public MyTextToSpeech(Context context) {
        this.context = context;
        textToSpeech = new TextToSpeech(context, this);
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS){
            String language = "fr";
            Locale locale = new Locale(language);
            textToSpeech.setLanguage(locale);
            textToSpeech.setPitch(0.6f);
            textToSpeech.setSpeechRate(1.0f);

        }
    }

    public void speak( String s){
        String language = MenuActivity.getLanguage();
        PatientConnected patientConnected = PatientConnected.getInstance(context);
        if (patientConnected.isLoggedIn()) {
            Patient patient = patientConnected.getPatient();
            if (patient != null) {
                ProfileEntity profile = patientConnected.getProfileEntity(patient.getIdPatient());
                if (profile != null) {
                    language = profile.getLanguage();
                    Locale locale = new Locale(language);
                    textToSpeech.setLanguage(locale);
                }
            }
        }

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            textToSpeech.speak(s, TextToSpeech.QUEUE_FLUSH, null, null);
        }else{
            textToSpeech.speak(s, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

}

