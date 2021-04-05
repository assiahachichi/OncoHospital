package fr.oncohospital.ui.fragmentContacts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fr.oncohospital.R;
import fr.oncohospital.model.allData.data.ContactEntity;

/**
 * Created by Assia HACHICHI on 06/03/2021
 */
public class ContactsItemAdapter extends RecyclerView.Adapter<ContactsItemAdapter.ContactViewHolder> {

    private OnItemClickListener mListener;
    private List<ContactEntity> allContacts=new ArrayList<ContactEntity>();

    public void setAllContacts(List<ContactEntity> allContacts) {
        this.allContacts = allContacts;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_contacts_item, parent, false);
        return new ContactViewHolder(itemView, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        ContactEntity currentContact = allContacts.get(position);
        holder.mImageView.setImageResource(currentContact.getImage());
        holder.mTextView1.setText(currentContact.getTitle());
    }

    @Override
    public int getItemCount() {
        if (allContacts == null) return 0;
        return allContacts.size();
    }





    public interface OnItemClickListener{
        void onItemClick(int position);
    }


    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;
        public TextView mTextView1;

        public ContactViewHolder(View itemView, OnItemClickListener listener){
            super(itemView);
            mImageView = itemView.findViewById(R.id.contact_item_imageView);
            mTextView1 = itemView.findViewById(R.id.contact_item_textView);
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
    }


}
