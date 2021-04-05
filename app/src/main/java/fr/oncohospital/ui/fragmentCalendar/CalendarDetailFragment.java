package fr.oncohospital.ui.fragmentCalendar;

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
import fr.oncohospital.model.speak.MyTextToSpeech;
import fr.oncohospital.ui.MyFragment;
import fr.oncohospital.ui.fragmentContacts.ContactsFragment;
import fr.oncohospital.ui.dialogTextSize.DialogTextSize;

public class CalendarDetailFragment extends MyFragment {
    public static final String DISABLE = "0";

    private ViewGroup container;
    private LayoutInflater inflater;
    private TextView tvTitle, tvDate, tvHours, tvLocation;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_calendar_detail, container, false);
        String title = getArguments().getString("title");
        String date = getArguments().getString("date");
        String hours = getArguments().getString("hours");
        String location = getArguments().getString("location");


        tvTitle = root.findViewById(R.id.calendar_detail_title);
        tvTitle.setText(title);

        tvDate = root.findViewById(R.id.calendar_detail_date);
        tvDate.setText(date);

        tvHours = root.findViewById(R.id.calendar_detail_hours);
        tvHours.setText(hours);

        tvLocation = root.findViewById(R.id.calendar_detail_location);
        tvLocation.setText(location);

        String s2Read = title + ". " + date + ". " + hours + ". " +location;

        ImageButton btnVoice = root.findViewById(R.id.calendar_detail_image_button_speech);
        btnVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyTextToSpeech.getInstance(getContext()).speak(s2Read);
            }
        });

        ImageButton bntTextSize = root.findViewById(R.id.calendar_detail_button_text_size);
        bntTextSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DialogTextSize(CalendarDetailFragment.this).open_dialog(v);
            }
        });
        Button previous = root.findViewById(R.id.calendar_detail_button_previous);
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getParentFragmentManager().beginTransaction();
                ft.replace(R.id.nav_host_fragment, new ContactsFragment());
                ft.commit();
            }
        });
            return root;
    }


    @Override
    public void update(int textSize) {
        this.tvDate.setTextSize(textSize);
        this.tvHours.setTextSize(textSize);
        this.tvLocation.setTextSize(textSize);
        this.tvTitle.setTextSize(textSize);

    }
}