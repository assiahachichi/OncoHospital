package fr.oncohospital.model.allData.bd.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import fr.oncohospital.model.allData.bd.entities.RendezVousEntity;

/**
 * Created by Assia HACHICHI on 24/03/2021
 * @author Assia HACHICHI
 */

/**
 * This class define some query methods, in order to populate database with appointment entities
 */
@Dao
public interface RendezVousDao {

    /**
     * This method represents an insert query, which inserts a new appointment into database
     * @param rendezVous represents a new appointment
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(RendezVousEntity rendezVous);

    /**
     * This method represents an update query, which updates an existing appointment
     * @param rendezVous represents an existing appointment to update
     */
    @Update
    void update(RendezVousEntity rendezVous);

    /**
     * This method represents a delete query, which deletes an existing appointment
     * @param rendezVous represents an existing appointment to delete
     */
    @Delete
    void delete(RendezVousEntity rendezVous);


    /**
     * This method represents a select query, which selects all appointments
     * @return represents all existing appointments
     */
    @Query("SELECT * FROM table_rendez_vous ")
    LiveData<List<RendezVousEntity>> getAllRendezVous();


    /**
     * This method represents a delete query, which deletes all old appointments
     * @param sDate represents a date on which all its old appointments have to be deleted
     */
    @Query("DELETE FROM table_rendez_vous WHERE ((sDtEnd < :sDate))")
    void deleteOldRendezVous(String sDate);

}
