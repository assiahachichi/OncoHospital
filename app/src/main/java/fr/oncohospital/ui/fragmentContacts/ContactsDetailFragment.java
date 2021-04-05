package fr.oncohospital.ui.fragmentContacts;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;

import fr.oncohospital.R;
import fr.oncohospital.model.allData.data.ContactEntity;
import fr.oncohospital.model.repository.ContactRepository;
import fr.oncohospital.model.speak.MyTextToSpeech;
import fr.oncohospital.ui.MyFragment;
import fr.oncohospital.ui.dialogTextSize.DialogTextSize;

public class ContactsDetailFragment extends MyFragment {
    public static final String DISABLE = "0";

    private ViewGroup container;
    private LayoutInflater inflater;
    private String s2Read ;
    private TextView tvTitle;
    private TextView tv1;
    private TextView et1;
    private TextView et2;
    private TextView tv2;
    private EditText etEmail;
    private View viewLayoutPhone1,  viewLayoutPhone2, root;
    LinearLayout layout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_contacts_detail, container, false);

        String title,text1,tel1,tel2,text2,email;
        int position = getArguments().getInt("position");

        ContactEntity ce = ContactRepository.getInstance().getAllContacts().get(position);
        title = ce.getTitle();
        text1 = ce.getText1();
        tel1 = ce.getTel1();
        tel2 = ce.getTel2();
        text2 = ce.getText2();
        email = ce.getEmail();


        layout = root.findViewById(R.id.layout_contact_detail);
        tvTitle = root.findViewById(R.id.textView_title);
        tvTitle.setText(title);

        tv1 = root.findViewById(R.id.textView_Contact1);
        tv1.setText(text1);

        s2Read = title + ". " + text1;

        et1 = root.findViewById(R.id.editText_Phone1);
        et1.setText(tel1);

        et2 = root.findViewById(R.id.editText_Phone2);
        et2.setText(tel2);

        tv2 = root.findViewById(R.id.textView_Contact2);
        tv2.setText(text2);


        etEmail = root.findViewById(R.id.editTextTextEmailAddress);
        etEmail.setText(email);

        viewLayoutPhone1 = root.findViewById(R.id.layout_phone1);
        String sEt1 = String.valueOf(et1.getText());
        if (sEt1.equals(DISABLE)){
            viewLayoutPhone1.setVisibility(View.INVISIBLE);
        }else{
            s2Read = s2Read + ": " + tel1;
            viewLayoutPhone1.setVisibility(View.VISIBLE);
        }


        viewLayoutPhone2 = root.findViewById(R.id.layout_phone2);
        String sEt2 = String.valueOf(et2.getText());
        if (sEt2.equals(DISABLE)){
            viewLayoutPhone2.setVisibility(View.INVISIBLE);
        }else{

            s2Read = s2Read + ", " + tel2;
            viewLayoutPhone2.setVisibility(View.VISIBLE);
        }


        String sTv2 = String.valueOf(tv2.getText());
        if (sTv2.equals(DISABLE)){
            tv2.setVisibility(View.INVISIBLE);
        }else {

            s2Read = s2Read + " " + text2;
            tv2.setVisibility(View.VISIBLE);
        }


        String sEtEmail = String.valueOf(etEmail.getText());
        if (sEtEmail.equals(DISABLE)){
            etEmail.setVisibility(View.INVISIBLE);
        }else {

            s2Read = s2Read + ": " + email;
            etEmail.setVisibility(View.VISIBLE);
        }

        etEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String address[] = {String.valueOf(etEmail.getText())};

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_EMAIL,address);
                intent.setType("message/rfc822");
                startActivity(Intent.createChooser(intent, "Choose an email client"));
}
        });
        ImageButton buttonPhone1 = root.findViewById(R.id.imagePhone1);
        ImageButton buttonPhone2 = root.findViewById(R.id.imagePhone2);

        buttonPhone1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel:"+sEt1));
                startActivity(i);
            }
        });

        buttonPhone2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel:"+sEt2));
                startActivity(i);
            }
        });

        ImageButton btnVoice = root.findViewById(R.id.contact_image_button_speech);
        btnVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyTextToSpeech.getInstance(getContext()).speak(s2Read);
            }
        });

        ImageButton bntTextSize = root.findViewById(R.id.contact_edit_text_size);
        bntTextSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DialogTextSize(ContactsDetailFragment.this).open_dialog(v);
            }
        });
        Button previous = root.findViewById(R.id.contact_button_previous);
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getParentFragmentManager().beginTransaction();
                ft.replace(R.id.nav_host_fragment, new ContactsFragment());
                ft.commit();
            }
        });
        Button previous2 = root.findViewById(R.id.contact_button_previous2);
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
        this.tvTitle.setTextSize(textSize);
        this.tv2.setTextSize(textSize);
        this.tv1.setTextSize(textSize);
        this.etEmail.setTextSize(textSize);
    }
}