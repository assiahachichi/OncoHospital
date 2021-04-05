package fr.oncohospital.model.allData.bd.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import fr.oncohospital.model.allData.bd.entities.ProfileEntity;

/**
 * Created by Assia HACHICHI on 24/03/2021
 * @author Assia HACHICHI
 */

/**
 * This class define some query methods, in order to populate database with profile entities
 */
@Dao
public interface ProfileDao {

    /**
     * This method represents an insert query, which inserts a new profile
     * @param profile represents a new profile
     */
    @Insert
    void insert(ProfileEntity profile);

    /**
     * This method represents an update query, which updates an existing profile
     * @param profile represents an existing profile to update
     */
    @Update
    void update(ProfileEntity profile);

    /**
     * This method represents a delete query, which deletes an existing profile
     * @param profile represents an existing profile to delete
     */
    @Delete
    void delete(ProfileEntity profile);

    /**
     * This method represents a select query.
     * It selects a specific profile corresponding to a given patient identifier
     * @param idPatient represents a given patient identifier
     * @return represents a profile corresponding to the given patient identifier
     */
    @Query("SELECT * FROM table_profile WHERE idPatient = :idPatient")
    ProfileEntity setProfile(String idPatient);

    /**
     *
     * This method represents a select query, which select a specific profile corresponding to a given patient ID
     * @return represents an existing profile in the database
     */
    @Query("SELECT * FROM table_profile WHERE idPatient = :idPatient")
    LiveData<ProfileEntity> getProfile(String idPatient);

    /**
     *
     * This method represents a select query, which selects all profiles
     * @return represents all existing profiles in the database
     */
    @Query("SELECT * FROM table_profile ")
    LiveData<List<ProfileEntity>> getAllProfiles();
}
