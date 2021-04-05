package fr.oncohospital.ui.fragmentFaq;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;

import fr.oncohospital.R;
import fr.oncohospital.model.allData.data.QuestionEntity;
import fr.oncohospital.model.repository.QuestionRepository;
import fr.oncohospital.model.speak.MyTextToSpeech;
import fr.oncohospital.ui.MyFragment;
import fr.oncohospital.ui.dialogTextSize.DialogTextSize;

public class FaqDetailFragment extends MyFragment {

    private ViewGroup container;
    private LayoutInflater inflater;
    private TextView tv1;
    private TextView et1;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_question_detail, container, false);

        int position = getArguments().getInt("position");
        String question,answer ;
        QuestionEntity q = QuestionRepository.getInstance().getAllQuestions().get(position);
        question = q.getQuestion();
        answer = q.getAnswer();
        String s2Read = question + "? " + answer;
        tv1 = root.findViewById(R.id.textView_Question);
        tv1.setText(question);

        et1 = root.findViewById(R.id.textView_answer);
        et1.setText(answer);

        Button previous = root.findViewById(R.id.question_button_previous);
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getParentFragmentManager().beginTransaction();
                ft.replace(R.id.nav_host_fragment, new FaqFragment());
                ft.commit();
            }
        });
        ImageButton btnVoice = root.findViewById(R.id.question_image_button_speech);
        btnVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyTextToSpeech.getInstance(getContext()).speak(s2Read);
            }
        });

        ImageButton bntTextSize = root.findViewById(R.id.question_edit_text_size);
        bntTextSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DialogTextSize(FaqDetailFragment.this).open_dialog(v);
            }
        });
            return root;
    }


    @Override
    public void update(int textSize) {
        this.et1.setTextSize(textSize);
        this.tv1.setTextSize(textSize);
    }
}