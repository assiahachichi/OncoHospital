package fr.oncohospital.ui.profile;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import fr.oncohospital.model.allData.bd.entities.ProfileEntity;
import fr.oncohospital.model.repository.ProfileRepository;

public class ProfileViewModel extends AndroidViewModel {
    private ProfileRepository profileRepository;
    private LiveData<List<ProfileEntity>> allProfile;

    public ProfileViewModel(@NonNull Application application) {
        super(application);
        profileRepository = new ProfileRepository(application);
        allProfile = profileRepository.getAllProfiles();
    }

    public void insert(ProfileEntity profile){ profileRepository.insert(profile);}

    public void update(ProfileEntity profile){profileRepository.update(profile);}

    public void delete(ProfileEntity profile){profileRepository.delete(profile);}



    public void setProfile(String idPatient){
         profileRepository.setProfile(idPatient);
    }

    public LiveData<ProfileEntity> getProfile(String idPatient){
        return profileRepository.getProfile(idPatient);
    }

    public LiveData<List<ProfileEntity>> getAllProfile(){
        return allProfile;

    }



}