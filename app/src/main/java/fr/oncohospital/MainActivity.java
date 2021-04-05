package fr.oncohospital;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import fr.oncohospital.model.repository.PatientRepository;
import fr.oncohospital.model.session.PatientConnected;
import fr.oncohospital.ui.MenuActivity;
import fr.oncohospital.ui.profile.ProfileViewModel;


public class MainActivity extends AppCompatActivity {
    EditText editTextIdPatient, editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        editTextIdPatient = (EditText) findViewById(R.id.editTextIdPatient);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        findViewById(R.id.buttonLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PatientConnected.getInstance(getApplicationContext()).clear();
                patientLogin();
            }
        });

        findViewById(R.id.buttonContact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PatientConnected.getInstance(getApplicationContext()).logout();
                startActivity(new Intent(MainActivity.this, MenuActivity.class));
            }
        });

        PatientConnected patientConnected = PatientConnected.getInstance(getApplicationContext());
        if (patientConnected.isLoggedIn()){
            ProfileViewModel profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
            patientConnected.initProfile(this, profileViewModel);
            startActivity(new Intent(MainActivity.this, MenuActivity.class));
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    private void patientLogin() {
        String s1, s2, idPatient = editTextIdPatient.getText().toString();
        String password = editTextPassword.getText().toString();
        if ((TextUtils.isEmpty(idPatient))||(idPatient.length()!=9)){

            editTextIdPatient.setError("Entrer votre ID Patient sans le point. Par exemple : 202011111");
            editTextIdPatient.requestFocus();
            return ;
        }

        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Entrer votre mot de passe");
            editTextPassword.requestFocus();
            return ;
        }
        s1 = idPatient.substring(0,4);
        s2 = idPatient.substring(4,9);
        idPatient = s1 + "." + s2;

        new PatientRepository(this).getPatient(idPatient,password);

    }

}
