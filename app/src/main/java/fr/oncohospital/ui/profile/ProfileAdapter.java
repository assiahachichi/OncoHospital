package fr.oncohospital.ui.profile;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.oncohospital.model.allData.bd.entities.ProfileEntity;

/**
 * Created by Assia HACHICHI on 27/03/2021
 */
public class ProfileAdapter extends RecyclerView.Adapter {
    private List<ProfileEntity> allProfiles;
    private ProfileEntity profile;
    ProfileViewModel profileViewModel;
    public ProfileAdapter(ProfileViewModel profileViewModel){
        this.profileViewModel = profileViewModel;
    }

    public void setAllProfiles(List<ProfileEntity> all){
        this.allProfiles = all;
        notifyDataSetChanged();
    }

    public void setProfile(ProfileEntity profile){
        this.profile = profile;
        notifyDataSetChanged();
    }
    public ProfileEntity getProfile(){
        return this.profile ;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        if (profile == null) return 0;
        return 1;
    }

    }
