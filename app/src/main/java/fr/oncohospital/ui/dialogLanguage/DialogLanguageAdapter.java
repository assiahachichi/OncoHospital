package fr.oncohospital.ui.dialogLanguage;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import fr.oncohospital.R;
import fr.oncohospital.model.allData.bd.entities.ProfileEntity;
import fr.oncohospital.model.session.Patient;
import fr.oncohospital.model.session.PatientConnected;
import fr.oncohospital.ui.MenuActivity;

/**
 * Created by Assia HACHICHI on 18/03/2021
 */
public class DialogLanguageAdapter extends BaseAdapter {
    MenuActivity myActivity;
    ArrayList<DialogLanguageSingleRow> arrayList;
    int selectedItem;

    DialogLanguageAdapter(MenuActivity myActivity){
        this.selectedItem = 0;
        this.myActivity = myActivity;
        arrayList = new ArrayList<DialogLanguageSingleRow>();
        Resources res = myActivity.getResources();
        String lang=MenuActivity.getLanguage();
        PatientConnected patientConnected = PatientConnected.getInstance(myActivity.getApplicationContext());

        if (patientConnected.isLoggedIn()) {
            Patient patient = patientConnected.getPatient();
            if (patient != null) {
                ProfileEntity profile = patientConnected.getProfileEntity(patient.getIdPatient());
                if (profile != null) lang = profile.getLanguage();
            }
        }
        boolean selected;
        LanguageType[] tab = LanguageType.values();
        for (LanguageType e : tab){
            selected = e.getLanguage().equals(lang) ;
            arrayList.add(new DialogLanguageSingleRow(e.getFlag(),e, e.getLanguage(), e.getNameLanguage(), selected));
        }
    }

    public void setSelectedItem(int selectedItem) {
        this.selectedItem = selectedItem;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) myActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.layout_dialog_language_detail, parent, false);
        ImageView flag = row.findViewById(R.id.dialog_language_image_flag);
        TextView name = row.findViewById(R.id.dialog_language_textview_flag_name);
        ImageView modeImage = row.findViewById(R.id.dialog_language_selected);
        DialogLanguageSingleRow obj = arrayList.get(position);

        name.setText(obj.getNameLanguage());
        flag.setImageResource(obj.getFlag());
        if (obj.isSelected()){
            modeImage.setImageResource(R.drawable.ic_circle);
        }else {
            modeImage.setImageResource(R.drawable.ic_trip);
        }
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = 0;
                for (DialogLanguageSingleRow row : arrayList){
                    if (i == position) row.setSelected(true);
                    else row.setSelected(false);
                    i++;
                }
                PatientConnected patientConnected = PatientConnected.getInstance(v.getContext());

                if (patientConnected.isLoggedIn()) {
                    Patient patient = patientConnected.getPatient();
                    if (patient != null) {
                        ProfileEntity profile = patientConnected.getProfileEntity(patient.getIdPatient());
                        if (profile != null) profile.setLanguage(obj.getLanguageType().getLanguage());
                    }
                }
                notifyDataSetChanged();
                myActivity.setLanguage(obj.getLanguageType().getLanguage());
                myActivity.setLocale( obj.getLanguageType().getLanguage());

                if (patientConnected.isLoggedIn()){
                    myActivity.recreate();
                }else{
                    myActivity.startActivity(new Intent(myActivity, MenuActivity.class));
                }
            }
        });
        return row;
    }



}
