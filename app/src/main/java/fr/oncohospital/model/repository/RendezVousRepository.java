package fr.oncohospital.model.repository;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import fr.oncohospital.model.allData.bd.entities.RendezVousEntity;
import fr.oncohospital.model.allData.bd.room.OncoDatabase;
import fr.oncohospital.model.allData.bd.room.RendezVousDao;
import fr.oncohospital.model.calendar.javaJson2GoogleCalendar.Calendar;
import fr.oncohospital.model.calendar.javaJson2GoogleCalendar.Event;
import fr.oncohospital.model.calendar.javaJson2GoogleCalendar.FunctionType;

/**
 * Created by Assia HACHICHI on 24/03/2021
 */
public class RendezVousRepository {

    private RendezVousDao rendezVousDao;
    private LiveData<List<RendezVousEntity>> allRendezVous;

    /**
     *
     * @param context
     */
    public RendezVousRepository(Context context){
        OncoDatabase database = OncoDatabase.getInstance(context);
        rendezVousDao = database.rendezVousDao();
        allRendezVous = rendezVousDao.getAllRendezVous();
    }

    /**
     *
     * @param rendezVous
     */
    public void insert(RendezVousEntity rendezVous){
        new InsertRendezVousAsyncTask(rendezVousDao).execute(rendezVous);
    }

    /**
     *
     * @param rendezVous
     */
    public void update(RendezVousEntity rendezVous){
        new UpdateRendezVousAsyncTask(rendezVousDao).execute(rendezVous);
    }

    /**
     *
     * @param rendezVous
     */
    public void delete(RendezVousEntity rendezVous){
        new DeleteRendezVousAsyncTask(rendezVousDao).execute(rendezVous);
    }

    /**
     *
     * @return
     */
    public LiveData<List<RendezVousEntity>> getAllRendezVous(){
        deleteOldRendezVous();
        allRendezVous = rendezVousDao.getAllRendezVous();
        if (allRendezVous.getValue() != null) {
            Collections.sort(allRendezVous.getValue());
        }
        return allRendezVous;

    }

    /**
     *
     */
    public void deleteOldRendezVous(){
        new DeleteOldRendezVousAsyncTask(rendezVousDao).execute();
    }

    /**
     *
     * @param idPatient
     * @param calendar
     */
    public void convertCalendarToDb(String idPatient, Calendar calendar) {
        String function;
        RendezVousEntity rdv;

        for (Event event : calendar.getAllEvents()){
            function = event.getFunction();
            rdv = RendezVousEntity.convertEventToRendezVous(idPatient, event);
            if(function.equals(FunctionType.DELETE.toString())){
                delete(rdv);
            }else {
                if(function.equals(FunctionType.UPDATE.toString())){
                    update(rdv);
                }else{
                    insert(rdv);
                }
            }
        }
    }

    /**
     *
     */
    private static class InsertRendezVousAsyncTask extends AsyncTask<RendezVousEntity, Void, Void> {
        private RendezVousDao rendezVousDao;
        private InsertRendezVousAsyncTask(RendezVousDao rendezVousDao){
            this.rendezVousDao = rendezVousDao;
        }

        @Override
        protected Void doInBackground(RendezVousEntity... rendezVousEntities) {
            rendezVousDao.insert(rendezVousEntities[0]);
            return null;
        }
    }

    /**
     *
     */
    private static class UpdateRendezVousAsyncTask extends AsyncTask<RendezVousEntity, Void, Void> {
        private RendezVousDao rendezVousDao;
        private UpdateRendezVousAsyncTask(RendezVousDao rendezVousDao){
            this.rendezVousDao = rendezVousDao;
        }

        @Override
        protected Void doInBackground(RendezVousEntity... rendezVousEntities) {
            rendezVousDao.update(rendezVousEntities[0]);
            return null;
        }
    }

    /**
     *
     */
    private static class DeleteRendezVousAsyncTask extends AsyncTask<RendezVousEntity, Void, Void> {
        private RendezVousDao rendezVousDao;
        private DeleteRendezVousAsyncTask(RendezVousDao rendezVousDao){
            this.rendezVousDao = rendezVousDao;
        }

        @Override
        protected Void doInBackground(RendezVousEntity... rendezVousEntities) {
            rendezVousDao.delete(rendezVousEntities[0]);
            return null;
        }
    }

    /**
     *
     */
    private static class DeleteOldRendezVousAsyncTask extends AsyncTask<Void, Void, Void> {
        private RendezVousDao rendezVousDao;
        private DeleteOldRendezVousAsyncTask(RendezVousDao rendezVousDao){
            this.rendezVousDao = rendezVousDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            long l = new Date().getTime();
            Date dYesterday = new Date(l - (24*60*60*1000));
            String sDate = MappingDateStringDao.convertDateToString(dYesterday);
            rendezVousDao.deleteOldRendezVous( sDate);
            return null;
        }
    }
}
