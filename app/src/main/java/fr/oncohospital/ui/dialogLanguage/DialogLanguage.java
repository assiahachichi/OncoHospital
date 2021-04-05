package fr.oncohospital.ui.dialogLanguage;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;

import fr.oncohospital.R;
import fr.oncohospital.ui.MenuActivity;

/**
 * Created by Assia HACHICHI on 18/03/2021
 */
public class DialogLanguage {
    MenuActivity myActivity;
    DialogLanguageAdapter listAdapter;
    int position;
    AlertDialog dialog;

    public DialogLanguage(MenuActivity myActivity, int actualPosition) {
        this.myActivity = myActivity;
        this.listAdapter = new DialogLanguageAdapter(myActivity);
        this.listAdapter.setSelectedItem(actualPosition);
        position = actualPosition;
    }

    public int getPosition() {
        return position;
    }

    public void open_dialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(myActivity);
        builder.setTitle(R.string.choose_language);
        LayoutInflater inflater = (LayoutInflater) myActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View row = inflater.inflate(R.layout.layout_dialog_language, null);


        builder.setView(row);

        builder.setSingleChoiceItems(listAdapter, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int newPosition) {

            }
        });

        dialog = builder.create();
        Button b = row.findViewById(R.id.dialog_mode_button_ok);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
     }
}
