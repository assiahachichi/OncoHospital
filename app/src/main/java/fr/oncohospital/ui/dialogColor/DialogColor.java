package fr.oncohospital.ui.dialogColor;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;

import fr.oncohospital.R;
import fr.oncohospital.model.allData.bd.entities.ProfileEntity;
import fr.oncohospital.ui.fragmentSettings.SettingsElement;

/**
 * Created by Assia HACHICHI on 18/03/2021
 */
public class DialogColor {
    private ProfileEntity profile;
    private SettingsElement typeItem;
    Context context;
    DialogColorAdapter listAdapter;
    int position;


    public DialogColor(Context context, int actualPosition, ProfileEntity profile, SettingsElement colorItem) {
        this.context = context;
        this.listAdapter = new DialogColorAdapter(context);
        this.listAdapter.setSelectedItem(actualPosition);
        position = actualPosition;
        this.profile = profile;
        this.typeItem = colorItem;
    }

    public int getPosition() {
        return position;
    }

    public void open_dialog(View v)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.layout_dialog, null);
        CardView l1 = (CardView) row.findViewById(R.id.list_view);

        builder.setView(row);
        builder.setSingleChoiceItems(listAdapter, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int newPosition) {
                listAdapter.setSelectedItem(newPosition);
                position = newPosition;
                DialogColorSingleRow rowItem = (DialogColorSingleRow) listAdapter.getItem(newPosition);
                listAdapter.notifyDataSetChanged();
                if (typeItem == SettingsElement.COLOR_ACTIVITY) {
                    profile.setActivityColor(MyColor.getColor(position));
                }else {
                    if(typeItem == SettingsElement.COLOR_RDV){
                        profile.setRdvColor(MyColor.getColor(position));
                    }else {
                        profile.setTreatmentColor(MyColor.getColor(position));
                    }
                }
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
