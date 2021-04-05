package fr.oncohospital.ui.fragmentContacts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.oncohospital.R;
import fr.oncohospital.model.allData.bd.entities.ProfileEntity;
import fr.oncohospital.model.allData.data.ContactEntity;
import fr.oncohospital.model.repository.ContactRepository;
import fr.oncohospital.model.session.Patient;
import fr.oncohospital.model.session.PatientConnected;
import fr.oncohospital.ui.MenuActivity;

public class ContactsFragment extends Fragment {

    ;
    private ViewGroup container;
    private LayoutInflater inflater;
    private View root;
    private RecyclerView mRecyclerView;
    private ContactsItemAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<ContactEntity> allContacts;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        this.container = container;
        this.inflater = inflater;
        root = inflater.inflate(R.layout.fragment_contacts, container, false);

        buildRecyclerView(root);
        return root;
    }


    public void changeItem(int position) {
        String title, text1, tel1, tel2, text2, email;
        ContactEntity ce = ContactRepository.getInstance().getAllContacts().get(position);
        title = ce.getTitle();
        text1 = ce.getText1();
        tel1 = ce.getTel1();
        tel2 = ce.getTel2();
        text2 = ce.getText2();
        email = ce.getEmail();

        ContactsDetailFragment cdFragment = new ContactsDetailFragment();
        Bundle args = new Bundle();


        args.putInt("position", position);
        args.putString("title", title);
        args.putString("text1",text1);
        args.putString("tel1",tel1);
        args.putString("tel2",tel2);
        args.putString("text2",text2);
        args.putString("email",email);

        cdFragment.setArguments(args);

        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        ft.replace(R.id.nav_host_fragment, cdFragment);
        ft.commit();


    }

    public void buildRecyclerView(View root){
        mRecyclerView = root.findViewById(R.id.contact_item_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(root.getContext());

        PatientConnected patientConnected = PatientConnected.getInstance(getContext());
        if (patientConnected.isLoggedIn()){
            Patient patient = patientConnected.getPatient();
            ProfileEntity profileEntity = patientConnected.getProfileEntity(patient.getIdPatient());
            MenuActivity.setLanguage(profileEntity.getLanguage());
        }

        mAdapter = new ContactsItemAdapter();
        allContacts = ContactRepository.getInstance().getAllContacts();
        mAdapter.setAllContacts(allContacts);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new ContactsItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                changeItem(position);
            }
        });
    }
}