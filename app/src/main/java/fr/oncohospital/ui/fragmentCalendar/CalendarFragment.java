package fr.oncohospital.ui.fragmentCalendar;

import android.content.ComponentName;
import android.content.ContentUris;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import fr.oncohospital.R;
import fr.oncohospital.model.allData.bd.entities.RendezVousEntity;
import fr.oncohospital.model.repository.HistoricalRepository;
import fr.oncohospital.model.session.PatientConnected;

public class CalendarFragment extends Fragment {


    private RecyclerView mRecyclerView;
    private static RendezVousItemAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private View root;
    private ViewGroup container;
    private LayoutInflater inflater;
    private CalendarViewModel calendarViewModel;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        if (!PatientConnected.getInstance(getContext()).isLoggedIn()){
            Toast.makeText(getContext(),R.string.toast_not_connected,Toast.LENGTH_SHORT).show();
            return null ;
        }
        this.container = container;
        this.inflater = inflater;

        calendarViewModel =
                new ViewModelProvider(this).get(CalendarViewModel.class);
        root = inflater.inflate(R.layout.fragment_calendar, container, false);

        Button bAgenda = root.findViewById(R.id.calendar_bottom_agenda);
        bAgenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long startMillis= Calendar.getInstance().getTime().getTime();
                Uri.Builder builder = CalendarContract.CONTENT_URI.buildUpon();
                builder.appendPath("time");
                ContentUris.appendId(builder, startMillis);
                Intent intent = new Intent(Intent.ACTION_VIEW)
                        .setData(builder.build());
                intent.setComponent(new ComponentName("com.google.android.calendar", "com.android.calendar.LaunchActivity"));
                startActivity(intent);
            }
        });
        Button bActualiser = root.findViewById(R.id.calendar_bottom_actualiser);
        bActualiser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HistoricalRepository.getHistotic();
            }
        });
        buildRecyclerView(root);
        return root;
    }

    public void changeItem(int poisition) {



        String title, date, hours, location;
        List<RendezVousEntity> allRendezVous = mAdapter.getAllRendezVous();
        if ((allRendezVous.size()<=1)&&(allRendezVous.get(0).getIdPatient().equals(""))) return;
        RendezVousEntity rdv = allRendezVous.get(poisition);
        title = rdv.getTitle();
        date = rdv.getDayFullName() + " " + rdv.getNbDay()+ " " +rdv.getFullMonth() + " " + rdv.getYear();
        hours = rdv.getStartHour();
        location = rdv.getLocation();

        CalendarDetailFragment cdFragment = new CalendarDetailFragment();
        Bundle args = new Bundle();

        args.putString("title", title);
        args.putString("date",date);
        args.putString("hours",hours);
        args.putString("location",location);

        cdFragment.setArguments(args);

        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        ft.replace(R.id.nav_host_fragment, cdFragment);
        ft.commit();


    }

    private void buildRecyclerView(View root) {
        mRecyclerView = root.findViewById(R.id.rendezvous_item_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(root.getContext());
        String idPatient = PatientConnected.getInstance(root.getContext()).getPatient().getIdPatient();


        mAdapter = new RendezVousItemAdapter();
        calendarViewModel.getAllRendezVous().observe(this.getViewLifecycleOwner(), new Observer<List<RendezVousEntity>>() {
            @Override
            public void onChanged(@Nullable List<RendezVousEntity> allRendezVous) {
                mAdapter.setAllRendezVous( getAllRendezVousPatient(allRendezVous,idPatient), getResources());
            }
            private List<RendezVousEntity> getAllRendezVousPatient(List<RendezVousEntity> allRendezVous, String idPatient){
                List<RendezVousEntity> list = new ArrayList<RendezVousEntity>();
                if (allRendezVous == null) return list;
                for (RendezVousEntity e : allRendezVous){
                    if (e.getIdPatient().equals(idPatient)) list.add(e);
                }
                return list;

            }
        });
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new RendezVousItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                changeItem(position);
            }
        });
    }


}