package fr.oncohospital.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import java.util.Locale;

import fr.oncohospital.R;
import fr.oncohospital.model.service.HistoricService;
import fr.oncohospital.model.session.Patient;
import fr.oncohospital.model.session.PatientConnected;
import fr.oncohospital.ui.dialogLanguage.DialogLanguage;
import fr.oncohospital.ui.fragmentContacts.ContactsFragment;
import fr.oncohospital.ui.myCalendarNotification.MyCalendar;
import fr.oncohospital.ui.profile.ProfileViewModel;

import static fr.oncohospital.R.id.nav_contacts;

public class MenuActivity extends AppCompatActivity {


    private AppBarConfiguration mAppBarConfiguration;
    private Menu menu;
    private static String language = "fr";

    public static String getLanguage() {
        return language;
    }

    public static void setLanguage(String language) {
        MenuActivity.language = language;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_calendar,  nav_contacts, R.id.nav_faq,R.id.nav_settings)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        PatientConnected patientConnected = PatientConnected.getInstance(getApplicationContext());
        if (!patientConnected.isLoggedIn()){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.nav_host_fragment, new ContactsFragment());
            ft.commit();
        }else {
            ProfileViewModel profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
            patientConnected.initProfile(this, profileViewModel);
            String idPatient = patientConnected.getPatient().getIdPatient();
            MyCalendar myCalendar = MyCalendar.getInstance();
            myCalendar.init(this, patientConnected.getProfileEntity(idPatient), profileViewModel);
            //**************************************
            //LUNCH SERVICE

            //*******************************
            Intent intent = new Intent(this, HistoricService.class);
            ContextCompat.startForegroundService(this, intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        if (language.equals("fr")) {
            menu.getItem(1).setIcon(R.mipmap.ic_francais);
        }else if(language.equals("en")){
            menu.getItem(1).setIcon(R.mipmap.ic_anglais);
        }
        this.menu = menu;
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_logout:
                PatientConnected.getInstance(getApplicationContext()).logout();
                return true;
            case R.id.action_langue:
                showChangeLanguageDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showChangeLanguageDialog(){
        new DialogLanguage(this,0).open_dialog();

    }

    public   void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        this.getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());


    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        Patient patient = PatientConnected.getInstance(getApplicationContext()).getPatient();
        TextView tvIdPatient = findViewById(R.id.textView_id_patient);
        tvIdPatient.setText(patient.getIdPatient());
        TextView tvIdPrenomNom = findViewById(R.id.textView_prenom_nom);
        String s1 = patient.getFirstname();
        if (s1 == null) {
            s1 = "";
        }
        String s2 = patient.getName();
        if (s2 == null){
            s2 = "";
        }
        s1 = s1 + " " + s2;

        tvIdPrenomNom.setText(s1);

        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();

    }
}
