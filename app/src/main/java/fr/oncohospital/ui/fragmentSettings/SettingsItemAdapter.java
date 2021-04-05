package fr.oncohospital.ui.fragmentSettings;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fr.oncohospital.R;
import fr.oncohospital.model.allData.bd.entities.ProfileEntity;
import fr.oncohospital.model.session.Patient;
import fr.oncohospital.model.session.PatientConnected;
import fr.oncohospital.ui.dialogColor.DialogColor;
import fr.oncohospital.ui.dialogColor.MyColor;
import fr.oncohospital.ui.profile.ProfileViewModel;

/**
 * Created by Assia HACHICHI on 16/03/2021
 */
public class SettingsItemAdapter extends RecyclerView.Adapter<SettingsItemAdapter.SettingsViewHolder> {


    private List<SettingsItem> allSettingsItems;
    private ArrayList<SettingsItem> list;
    private OnItemClickListener mListener;
    public Context context;
    public static ProfileViewModel profileViewModel;


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public SettingsItemAdapter(){}

    @NonNull
    @Override
    public SettingsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_settings_item, parent, false);
        return new SettingsItemAdapter.SettingsViewHolder(itemView, mListener);

    }

    public void setSettingsList(ArrayList<SettingsItem> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull SettingsViewHolder holder, int position) {

        SettingsViewHolder myHolder = (SettingsViewHolder) holder;
        SettingsItem currentItem = list.get(position);
        Boolean b = currentItem.isCheckedSwitch();
        if (b == null) {
            myHolder.mBSwitch.setVisibility(View.INVISIBLE);

        } else {

                myHolder.mBSwitch.setVisibility(View.VISIBLE);
                myHolder.mBSwitch.setChecked(b);

        }
        myHolder.mTextView1.setText(currentItem.getText1());
    }

    @Override
    public int getItemCount() {
        if (list==null) return 0;
        return list.size();
    }

    public static class SettingsViewHolder extends RecyclerView.ViewHolder {


        public TextView mTextView1;
        public Switch mBSwitch;

        public SettingsViewHolder(View itemView, SettingsItemAdapter.OnItemClickListener listener) {
            super(itemView);
            mBSwitch = itemView.findViewById(R.id.settings_item_switch);
            mTextView1 = itemView.findViewById(R.id.settings_item_textView_titre);

            mBSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                    if (mBSwitch.getVisibility()==View.VISIBLE) {
                        int position = getAdapterPosition();
                        int i;
                        Patient patient = PatientConnected.getInstance(buttonView.getContext()).getPatient();
                        ProfileEntity profile = PatientConnected.getInstance(buttonView.getContext()).getProfileEntity(patient.getIdPatient());

                        if(position == SettingsElement.NOTIFICATIONS.getIndex()){

                            profile.setNotificationSetting(isChecked); return;

                        }
                        if(position == SettingsElement.RDV.getIndex()){
                            profile.setCalendarSetting(isChecked); return;
                        }
                        if(position == SettingsElement.RAPPELS.getIndex()){
                            profile.setReminderSetting(isChecked); return;
                        }
                        if(position == SettingsElement.TREATMENT.getIndex()){
                            profile.setTreatmentSetting(isChecked); return;
                        }
                        profileViewModel.update(profile);
                    }
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mBSwitch = v.findViewById(R.id.settings_item_switch);
                    mTextView1 = v.findViewById(R.id.settings_item_textView_titre);

                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            if (mBSwitch.getVisibility() == View.INVISIBLE){
                                int actualPosition;
                                Patient patient = PatientConnected.getInstance(v.getContext()).getPatient();
                                ProfileEntity profile = PatientConnected.getInstance(v.getContext()).getProfileEntity(patient.getIdPatient());
                                if(position == SettingsElement.COLOR_ACTIVITY.getIndex()){
                                    actualPosition = MyColor.getIndex(profile.getActivityColor());
                                    launchDialogColor(v, actualPosition, profile, SettingsElement.COLOR_ACTIVITY);
                                }
                                if(position == SettingsElement.COLOR_RDV.getIndex()){
                                    actualPosition = MyColor.getIndex(profile.getRdvColor());
                                    launchDialogColor(v, actualPosition, profile, SettingsElement.COLOR_RDV);
                                }
                                if(position == SettingsElement.COLOR_TREATMENT.getIndex()){
                                    actualPosition = MyColor.getIndex(profile.getTreatmentColor());
                                    launchDialogColor(v, actualPosition, profile, SettingsElement.COLOR_TREATMENT);
                                }
                                profileViewModel.update(profile);

                            }
                        }
                }
            });


        }
        public void launchDialogColor(View v, int actualPosition, ProfileEntity profile, SettingsElement color){
            DialogColor dialog = new DialogColor(v.getContext(), actualPosition, profile, color);
            dialog.open_dialog(v);

        }
    }
}
