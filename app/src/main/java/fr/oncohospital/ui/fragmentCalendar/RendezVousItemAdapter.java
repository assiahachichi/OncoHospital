package fr.oncohospital.ui.fragmentCalendar;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.oncohospital.R;
import fr.oncohospital.model.allData.bd.entities.RendezVousEntity;

/**
 * Created by Assia HACHICHI on 06/03/2021
 */
public class RendezVousItemAdapter  extends RecyclerView.Adapter<RendezVousItemAdapter.RendezVousViewHolder> {

    private OnItemClickListener mListener;
    private List<RendezVousEntity> allRendezVous=new ArrayList<RendezVousEntity>();

    public void setAllRendezVous(List<RendezVousEntity> allRendezVous, Resources res) {
        this.allRendezVous = allRendezVous;

        if((allRendezVous != null) && (allRendezVous.size() == 0)){
            RendezVousEntity e = new RendezVousEntity(0,"","","","",res.getString(R.string.pasRDV),"","");
            getAllRendezVous().add(e);
        }
        notifyDataSetChanged();
    }
    public List<RendezVousEntity> getAllRendezVous(){return allRendezVous;}
    @NonNull
    @Override
    public RendezVousViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_calendar_item, parent, false);
        return new RendezVousViewHolder(itemView, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RendezVousViewHolder holder, int position) {
        RendezVousEntity currentItem = allRendezVous.get(position);
        RendezVousViewHolder myHolder = (RendezVousViewHolder) holder;
        Date previousItemDate, currentItemDate;
        myHolder.mTextViewTitle.setText(currentItem.getTitle());
        if ((position==0) && (currentItem.getSDtStart().equals(""))){
            myHolder.mTextViewLieu.setText("");
            myHolder.mTextViewHeure1.setText("");
            myHolder.mLayoutDate.setVisibility(View.INVISIBLE);
        }else {
            if ((position > 0) && (currentItem.isSameDay(allRendezVous.get(position - 1)))) {
                myHolder.mTextViewJour.setText("");
                myHolder.mTextViewNbJour.setText("");
                myHolder.mTextViewMois.setText("");
                myHolder.mLayoutDate.setVisibility(View.INVISIBLE);

            } else {
                myHolder.mTextViewJour.setText(currentItem.getDayName());
                myHolder.mTextViewNbJour.setText(currentItem.getNbDay());
                myHolder.mTextViewMois.setText(currentItem.getMonth());
                myHolder.mLayoutDate.setVisibility(View.VISIBLE);
            }
            String duration = currentItem.getStartHour();
            if (currentItem.isInOneDay()) {
                duration = duration + " - " + currentItem.getEndHour();
            }
            myHolder.mTextViewHeure1.setText(duration);

            String sLocation = currentItem.getLocation();
            if (!sLocation.equals("")) {
                sLocation =  sLocation;
            }
            myHolder.mTextViewLieu.setText(sLocation);
        }
    }

    @Override
    public int getItemCount() {
        if (allRendezVous == null) return 0;
        return allRendezVous.size();
    }





    public interface OnItemClickListener{
        void onItemClick(int position);
    }


    public void setOnItemClickListener(RendezVousItemAdapter.OnItemClickListener listener){
        mListener = listener;
    }

    public static class RendezVousViewHolder extends RecyclerView.ViewHolder{
        public LinearLayout mLayoutDate;
        public TextView mTextViewJour;
        public TextView mTextViewNbJour;
        public TextView mTextViewMois;
        public TextView mTextViewTitle;
        public TextView mTextViewLieu;
        private TextView mTextViewHeure1;

        public RendezVousViewHolder(View itemView, OnItemClickListener listener){
            super(itemView);
            mLayoutDate = itemView.findViewById(R.id.rendezvous_item_layout_date);
            mTextViewJour = itemView.findViewById(R.id.rendezvous_item_textView_jour);
            mTextViewNbJour = itemView.findViewById(R.id.rendezvous_item_textView_nb_jour);
            mTextViewMois = itemView.findViewById(R.id.rendezvous_item_textView_mois);
            mTextViewTitle = itemView.findViewById(R.id.rendezvous_item_textView_titre);
            setTextViewHeure1(itemView.findViewById(R.id.rendezvous_item_textView_heure1));
            mTextViewLieu = itemView.findViewById(R.id.rendezvous_item_textView_lieu);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if (position !=RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }

        public TextView getTextViewHeure1() {
            return mTextViewHeure1;
        }

        public void setTextViewHeure1(TextView textViewHeure1) {
            mTextViewHeure1 = textViewHeure1;
        }
    }
}
