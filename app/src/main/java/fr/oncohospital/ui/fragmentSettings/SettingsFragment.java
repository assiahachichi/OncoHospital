package fr.oncohospital.ui.fragmentSettings;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fr.oncohospital.R;
import fr.oncohospital.model.allData.bd.entities.ProfileEntity;
import fr.oncohospital.model.session.PatientConnected;
import fr.oncohospital.ui.profile.ProfileViewModel;

public class SettingsFragment extends Fragment {

    private ViewGroup container;
    private LayoutInflater inflater;
    private ArrayList<SettingsItem> list;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private SettingsItemAdapter mAdapter;
    private Context context;
    String idPatient;
    private ProfileEntity profile;
    private PatientConnected patientConnected;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        if (!PatientConnected.getInstance(getContext()).isLoggedIn()){
            Toast.makeText(getContext(),R.string.toast_not_connected,Toast.LENGTH_SHORT).show();
            return null ;
        }
        this.container = container;
        this.inflater = inflater;
        this.context = getContext();

        View root = inflater.inflate(R.layout.fragment_settings, container, false);

         patientConnected = PatientConnected.getInstance(root.getContext());
        idPatient = patientConnected.getPatient().getIdPatient();
        profile = patientConnected.getProfileEntity(idPatient);

        SettingsItemAdapter.profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        SettingsItemAdapter.profileViewModel.getAllProfile().observe(this.getViewLifecycleOwner(), new Observer<List<ProfileEntity>>() {
            @Override
            public void onChanged(@Nullable List<ProfileEntity> allProfiles) {
                mAdapter.setSettingsList(createSettingsList());
                mAdapter.notifyDataSetChanged();
            }
        });
        mAdapter = new SettingsItemAdapter();
        createSettingsList();
        mAdapter.setSettingsList(list);
        buildRecyclerView(root);

        return root;
    }

    public ArrayList<SettingsItem> createSettingsList(){
        profile = patientConnected.getProfileEntity(idPatient);
        if (profile == null) return null;
        list = new ArrayList<SettingsItem>();
        SettingsItem item;
        int i;
        SettingsElement[] tab = SettingsElement.values();
        item = new SettingsItem(tab[0].getValue(this.getResources()), profile.isNotificationSetting());
        list.add(item);

        item = new SettingsItem(tab[1].getValue(this.getResources()), profile.isCalendarSetting());
        list.add(item);

        item = new SettingsItem(tab[2].getValue(this.getResources()), profile.isTreatmentSetting());
        list.add(item);

        item = new SettingsItem(tab[3].getValue(this.getResources()), profile.isReminderSetting());
        list.add(item);

        i = 4;
        while (i<tab.length){
            item = new SettingsItem(tab[i].getValue(this.getResources()),
                    null);
            i++;
            list.add(item);
        }
        return list;
    }



    public void buildRecyclerView(View root){

        mRecyclerView = root.findViewById(R.id.settings_item_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(root.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }
}