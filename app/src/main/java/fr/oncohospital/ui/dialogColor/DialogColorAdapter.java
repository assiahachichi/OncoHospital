package fr.oncohospital.ui.dialogColor;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import fr.oncohospital.R;

/**
 * Created by Assia HACHICHI on 18/03/2021
 */
public class DialogColorAdapter extends BaseAdapter {
    Context c;
    ArrayList<DialogColorSingleRow> arrayList;
    int selectedItem;

    DialogColorAdapter(Context c){
        this.selectedItem = 0;
        this.c = c;
        arrayList = new ArrayList<DialogColorSingleRow>();
        Resources res = c.getResources();
        String[] names = res.getStringArray(R.array.color_name);

        int[] imagesFull = {R.drawable.ic_circle_red,R.drawable.ic_circle_magenta,
                R.drawable.ic_circle_yellow, R.drawable.ic_circle_green, R.drawable.ic_circle_cyan,
                R.drawable.ic_circle_blue};

        int[] imagesNotFull = {R.drawable.ic_trip_red,R.drawable.ic_trip_magenta,
                R.drawable.ic_trip_yellow, R.drawable.ic_trip_green, R.drawable.ic_trip_cyan,
                R.drawable.ic_trip_blue};

        ArrayList<String> nameColors = MyColor.getColorNames();
        ArrayList<Integer> colors = MyColor.getColorValues();

        for (int i = 0; i <nameColors.size(); i++){
            arrayList.add(
                    new DialogColorSingleRow((String) nameColors.get(i),
                            imagesFull[i],
                            imagesNotFull[i],
                            (int)colors.get(i), names[i]
                    ));

        }
    }

    public void setSelectedItem(int selectedItem) {
        this.selectedItem = selectedItem;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.layout_dialog_listview_detail, parent, false);
        TextView t1 = row.findViewById(R.id.textview);
        ImageView i1 = row.findViewById(R.id.imageView);
        DialogColorSingleRow obj = arrayList.get(position);
        t1.setText(obj.getNameLanguage());
        if (selectedItem == position){
            i1.setImageResource(obj.getImageFull());
        }else{

            i1.setImageResource(obj.getImageNotFull());
        }
        return row;
    }
}
