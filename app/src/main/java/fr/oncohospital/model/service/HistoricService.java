package fr.oncohospital.model.service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import fr.oncohospital.R;
import fr.oncohospital.model.repository.HistoricalRepository;
import fr.oncohospital.model.session.PatientConnected;
import fr.oncohospital.ui.MenuActivity;

/**
 * Created by Assia HACHICHI on 21/03/2021
 */
// add <service android:name=".ExampleService"/> to Manifest
//add permission
// <uses-permission android:name="android.permission.FOREGROUND_SERVICE"/>

/**
 *
 */
public class HistoricService extends Service {
    private Looper serviceLooper;
    private ServiceHandler serviceHandler;
    Notification notification ;
    NotificationManager manager;
    AppCompatActivity activity;

    /**
     *
     */
    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }
        @Override
        public void handleMessage(Message msg) {
            try {

                while (PatientConnected.getInstance(getApplicationContext()).isLoggedIn()) {
                    Thread.sleep(4000);
                    HistoricalRepository.getHistotic();

                }

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            stopSelf(msg.arg1);
        }
    }

    /**
     *
     */
    @Override
    public void onCreate() {
        HandlerThread thread = new HandlerThread("ServiceStartArguments", android.os.Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();

        serviceLooper = thread.getLooper();
        serviceHandler = new ServiceHandler(serviceLooper);

    }

    /**
     *
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        Message msg = serviceHandler.obtainMessage();
        msg.arg1 = startId;
        serviceHandler.sendMessage(msg);


        Intent intent1 = new Intent(this, MenuActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, intent1,0 );


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("myChannel", "My Foreground Service", NotificationManager.IMPORTANCE_LOW);
            manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);


            @SuppressLint("WrongConstant") NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), "myChannel")
                    .setContentTitle("Onco Service")
                    .setContentText("")

                    .setSmallIcon(R.mipmap.ic_onco_hospital_background)
                    .setWhen(System.currentTimeMillis())
                    .setContentIntent(pendingIntent)
                    .setTicker("Tutorialspoint")
                    .setContentInfo("Information");
              notification= notificationBuilder.build();
            startForeground(1, notification);
        }
        return START_NOT_STICKY;
    }

    /**
     *
     * @param intent
     * @return
     */
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * 
     */
    @Override
    public void onDestroy() {
        Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }

}
