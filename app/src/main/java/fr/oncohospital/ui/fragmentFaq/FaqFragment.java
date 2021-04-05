package fr.oncohospital.ui.fragmentFaq;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fr.oncohospital.R;
import fr.oncohospital.model.allData.bd.entities.ProfileEntity;
import fr.oncohospital.model.allData.data.QuestionEntity;
import fr.oncohospital.model.repository.QuestionRepository;
import fr.oncohospital.model.session.Patient;
import fr.oncohospital.model.session.PatientConnected;
import fr.oncohospital.ui.MenuActivity;

public class FaqFragment extends Fragment {

    private ViewGroup container;
    private LayoutInflater inflater;
    private View root;
    private ArrayList<QuestionEntity> list;
    private RecyclerView mRecyclerView;
    private FaqItemAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static List<QuestionEntity> allQuestions;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        this.container = container;
        this.inflater = inflater;
        if (!PatientConnected.getInstance(getContext()).isLoggedIn()){
            Toast.makeText(getContext(),R.string.toast_not_connected,Toast.LENGTH_SHORT).show();
            return null ;
        }
        root = inflater.inflate(R.layout.fragment_faq, container, false);

        buildRecyclerView(root);
        return root;
    }


    public void changeItem(int position) {
        String question, answer;
        QuestionEntity q = QuestionRepository.getInstance().getAllQuestions().get(position);
        question = q.getQuestion();
        answer = q.getAnswer();



        FaqDetailFragment faqDetailFragment = new FaqDetailFragment();
        Bundle args = new Bundle();

        args.putInt("position", position);
        args.putString("question",question);
        args.putString("answer",answer);

        faqDetailFragment.setArguments(args);

        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        ft.replace(R.id.nav_host_fragment, faqDetailFragment);
        ft.commit();


    }

    public void buildRecyclerView(View root){
        mRecyclerView = root.findViewById(R.id.faq_item_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(root.getContext());

        PatientConnected patientConnected = PatientConnected.getInstance(getContext());
        if (patientConnected.isLoggedIn()){
            Patient patient = patientConnected.getPatient();
            ProfileEntity profileEntity = patientConnected.getProfileEntity(patient.getIdPatient());
            MenuActivity.setLanguage(profileEntity.getLanguage());
        }
        mAdapter = new FaqItemAdapter();
        allQuestions = QuestionRepository.getInstance().getAllQuestions();
        mAdapter.setAllQuestions(allQuestions);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new FaqItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                changeItem(position);
            }
        });
    }
}