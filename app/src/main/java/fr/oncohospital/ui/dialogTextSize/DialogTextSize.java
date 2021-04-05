package fr.oncohospital.ui.dialogTextSize;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;

import androidx.appcompat.app.AlertDialog;

import fr.oncohospital.R;
import fr.oncohospital.model.allData.bd.entities.ProfileEntity;
import fr.oncohospital.model.session.Patient;
import fr.oncohospital.model.session.PatientConnected;
import fr.oncohospital.ui.MyFragment;

/**
 * Created by Assia HACHICHI on 18/03/2021
 */
public class DialogTextSize {

    MyFragment myFragment;
    AlertDialog mDialog;

    public DialogTextSize(MyFragment myFragment) {
        this.myFragment = myFragment;
    }


    public void open_dialog(View v)
    {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(myFragment.getContext());
        mBuilder.setTitle(R.string.choose_text_size);
        LayoutInflater inflater = (LayoutInflater) myFragment.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.layout_dialog_text_size, null);

        mBuilder.setView(row);
        SeekBar seekBar = row.findViewById(R.id.seek_bar_size);
        int textSize = 20;
        PatientConnected patientConnected = PatientConnected.getInstance(v.getContext());

        if (patientConnected.isLoggedIn()) {
            Patient patient = patientConnected.getPatient();
            if (patient != null) {
                ProfileEntity profile = patientConnected.getProfileEntity(patient.getIdPatient());
                if (profile != null)textSize = profile.getTextSize();
            }
        }
        seekBar.setProgress(textSize);
        EditText editTextExample = row.findViewById(R.id.edit_text_example);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                editTextExample.setTextSize(seekBar.getProgress());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                int textSize = 17;
                PatientConnected patientConnected = PatientConnected.getInstance(v.getContext());

                if (patientConnected.isLoggedIn()) {
                    Patient patient = patientConnected.getPatient();
                    if (patient != null) {
                        ProfileEntity profile = patientConnected.getProfileEntity(patient.getIdPatient());
                        if (profile != null)textSize = profile.getTextSize();
                    }
                }
                seekBar.setProgress(textSize);
                editTextExample.setTextSize(seekBar.getProgress());
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                editTextExample.setTextSize(seekBar.getProgress());
            }
        });

        row.findViewById(R.id.button_annuler).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });

        row.findViewById(R.id.button_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int textSize = seekBar.getProgress();
                PatientConnected patientConnected = PatientConnected.getInstance(v.getContext());

                if (patientConnected.isLoggedIn()) {
                    Patient patient = patientConnected.getPatient();
                    if (patient != null) {
                        ProfileEntity profile = patientConnected.getProfileEntity(patient.getIdPatient());
                        if (profile != null)profile.setTextSize(textSize);
                    }
                }
                myFragment.update(textSize);
                mDialog.dismiss();
            }
        });
        mDialog = mBuilder.create();
        mDialog.show();
    }
}
