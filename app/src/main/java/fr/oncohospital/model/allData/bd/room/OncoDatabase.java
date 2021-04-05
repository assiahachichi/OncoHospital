package fr.oncohospital.model.allData.bd.room;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import fr.oncohospital.model.allData.bd.entities.ProfileEntity;
import fr.oncohospital.model.allData.bd.entities.RendezVousEntity;

/**
 * Created by Assia HACHICHI on 24/03/2021
 */
@Database(entities = {ProfileEntity.class, RendezVousEntity.class}, version = 3, exportSchema = false)
public abstract class OncoDatabase  extends RoomDatabase {
    private static String DATABASE_NAME = "onco1_hospital_database";
    private static OncoDatabase instance;

    /**
     *
     * @param context
     * @return
     */
    public static synchronized OncoDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    OncoDatabase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }
    /**
     *
     * @return
     */
    public abstract ProfileDao profileDao();

    /**
     *
     * @return
     */
    public abstract RendezVousDao rendezVousDao();


    /**
     *
     */
    private static Callback roomCallback = new Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };


}
