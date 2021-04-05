package fr.oncohospital.ui.fragmentCalendar;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import fr.oncohospital.model.allData.bd.entities.RendezVousEntity;
import fr.oncohospital.model.repository.RendezVousRepository;

public class CalendarViewModel extends AndroidViewModel {
    private RendezVousRepository rendezVousRepository;
    private LiveData<List<RendezVousEntity>> allRendezVous;

    public CalendarViewModel(@NonNull Application application) {
        super(application);
        rendezVousRepository = new RendezVousRepository(application);
    }

    public void insert(RendezVousEntity rendezVous){ rendezVousRepository.insert(rendezVous);}

    public void update(RendezVousEntity rendezVous){rendezVousRepository.update(rendezVous);}

    public void delete(RendezVousEntity rendezVous){rendezVousRepository.delete(rendezVous);}

    public LiveData<List<RendezVousEntity>> getAllRendezVous(){
        allRendezVous = rendezVousRepository.getAllRendezVous();
        return allRendezVous;

    }

    public void deleteOldRendezVous(){
        rendezVousRepository.deleteOldRendezVous();
    }

}