package pl.tkadziolka.room.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Single;
import pl.tkadziolka.room.database.model.Profession;

@Dao
public interface ProfessionDao {

    @Query("SELECT * FROM profession")
    Single<List<Profession>> getAll();
}
