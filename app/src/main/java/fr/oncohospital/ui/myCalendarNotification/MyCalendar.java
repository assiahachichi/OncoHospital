package fr.oncohospital.ui.myCalendarNotification;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import java.util.ArrayList;
import java.util.Date;

import fr.oncohospital.model.allData.bd.entities.ProfileEntity;
import fr.oncohospital.model.calendar.javaJson2GoogleCalendar.ParserJsonToCalendar;
import fr.oncohospital.model.calendar.javaJson2GoogleCalendar.Reminder;
import fr.oncohospital.model.repository.MappingDateStringDao;
import fr.oncohospital.model.repository.RendezVousRepository;
import fr.oncohospital.model.session.Patient;
import fr.oncohospital.model.session.PatientConnected;
import fr.oncohospital.ui.MenuActivity;
import fr.oncohospital.R;
import fr.oncohospital.model.calendar.chemotherapyStrategies.Protocol;
import fr.oncohospital.model.calendar.chemotherapyStrategies.ProtocolEC100;
import fr.oncohospital.model.calendar.chemotherapyStrategies.ProtocolTaxotere;
import fr.oncohospital.model.calendar.javaJson2GoogleCalendar.Calendar;
import fr.oncohospital.model.calendar.javaJson2GoogleCalendar.Event;
import fr.oncohospital.ui.profile.ProfileViewModel;


/**
 * Created by Assia HACHICHI on 23/03/2021
 */
public class MyCalendar {
    public static final String CHANNEL_ID = "myChannel1";
    AppCompatActivity activity;

    ProfileViewModel profileViewModel;
    static MyCalendar instance = null;

    public static MyCalendar getInstance(){
        if (instance == null){
            instance = new MyCalendar();
        }
        return instance;
    }


    public void init(AppCompatActivity activity, ProfileEntity profile, ProfileViewModel profileViewModel){
        this.activity = activity;
        this.profileViewModel = profileViewModel;
    }

    public synchronized void convertStringToRendezVous(String resultString){
        if (resultString == null){
            if (activity == null || activity.isFinishing()){ return;}
            Toast.makeText(activity, R.string.toast_deconnected, Toast.LENGTH_LONG).show();
            return;

        }
        PatientConnected patientConnected = PatientConnected.getInstance(activity);
        String idPatient = patientConnected.getPatient().getIdPatient();
        ProfileEntity profile = patientConnected.getProfileEntity(idPatient);
        ContentResolver cr;
        Context context;
        int TREATMENT_TIME = 5;
        int RDV_TIME = 60*24;
        int ACTIVITY_TIME = RDV_TIME;

        if (activity == null || activity.isFinishing()){ return;}
        cr = activity.getContentResolver();
        context = activity.getApplicationContext();
        if ((resultString!=null)&&(!resultString.equals("")))
        {
            ParserJsonToCalendar parser = new ParserJsonToCalendar(cr, context, "Europe/Paris");
            int colorRDV = profile.getRdvColor();
            Calendar calendar = parser.getCalendar(resultString, colorRDV);
            calendar.setReminder(new Reminder(RDV_TIME));
            if (calendar.getAllEvents().size()!=0) {
                Patient patient = PatientConnected.getInstance(context).getPatient();
                new RendezVousRepository(activity).convertCalendarToDb(patient.getIdPatient(), calendar);
                Date d = calendar.getLastDate();
                if (d!=null) {
                    profile.setLastDateRefresh(MappingDateStringDao.convertDateToString(d));
                    if (profileViewModel != null) {
                        profileViewModel.update(profile);
                    }
                }
                if (profile.isNotificationSetting()) {
                    MyCalendar.generateNotifications(context, activity, calendar);
                }
                if (profile.isCalendarSetting()) {
                    if (profile.isTreatmentSetting()) {
                        int colorActivity = profile.getActivityColor();
                        int colorTreatment = profile.getTreatmentColor();
                        MyCalendar.generateTreatment(calendar, context.getResources(), colorActivity, colorTreatment);
                    }
                    calendar.convertToGoogleCalendar();
                    if(profile.isReminderSetting()) {
                        calendar.generateAllNewReminders();
                    }
                }

            }

        }

    }



    public static  void generateTreatment(Calendar calendar, Resources res, int colorActivity, int colorTreatment){
        Protocol protocol;
        ArrayList<Event> newEvents, tmpList = new ArrayList<>() ;
        for (Event event : calendar.getAllEvents()){

            String s = event.getTitle();
            newEvents = new ArrayList<>();
            s = s.toUpperCase();
            if (s.contains("CHIMIO")){
                if (s.contains(ProtocolEC100.EC100)){
                    protocol = ProtocolEC100.getInstance();
                    protocol.setColorActivity(colorActivity);
                    protocol.setColorTreatment(colorTreatment);
                    newEvents = protocol.generateTreatment(res, event);
                }
                else if ((s.contains(ProtocolTaxotere.TAXOTERE))||(s.contains(ProtocolTaxotere.DOCETAXEL))){
                    protocol = ProtocolTaxotere.getInstance();
                    protocol.setColorActivity(colorActivity);
                    protocol.setColorTreatment(colorTreatment);
                    newEvents = protocol.generateTreatment(res, event);
                }
            }

			tmpList.addAll(newEvents);

        }
        calendar.getAllEvents().addAll(tmpList);

    }

    public static void generateNotifications(Context context, AppCompatActivity activity, Calendar calendar){
        ArrayList<Event> listEvents = calendar.getAllEvents();
        for (Event event : listEvents){
            String fct = event.getFunction();
            String title = "";
            if (fct.equals("INSERT")){
                title = context.getString(R.string.fct_INSERT);
            }else{
                if (fct.equals("UPDATE")){
                    title = context.getString(R.string.fct_UPDATE);
                }else {
                    title = context.getString(R.string.fct_DELETE);
                }
            }


            String text = event.getStringDtStart();
            lunchNotification(context, activity, title, text);


        }

    }
    public static void lunchNotification(Context context, AppCompatActivity activity, String title, String text){

        NotificationManager manager = (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
        String CHANNEL_ID = "exampleServiceChannel";
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            @SuppressLint("WrongConstant")
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Example Service Channel",
                    NotificationManager.IMPORTANCE_HIGH
            );

            serviceChannel.setDescription("Sample Channel description");
            serviceChannel.enableLights(true);
            serviceChannel.setLightColor(Color.RED);
            serviceChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            serviceChannel.enableVibration(true);

            manager.createNotificationChannel(serviceChannel);
        }


        Intent intent1 = new Intent(context, MenuActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent1,0 );


        @SuppressLint("WrongConstant") NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(text)

                .setSmallIcon(R.mipmap.ic_onco_hospital_background)
                .setWhen(System.currentTimeMillis())
                .setContentIntent(pendingIntent)
                .setTicker("Tutorialspoint")
                .setContentInfo("Information");
        manager.notify(1, notificationBuilder.build());

    }

}
