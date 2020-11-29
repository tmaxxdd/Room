package pl.tkadziolka.room.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import pl.tkadziolka.room.database.model.User;
import pl.tkadziolka.room.database.model.UserWithProducts;
import pl.tkadziolka.room.database.model.UserWithProfession;

import static androidx.room.ForeignKey.RESTRICT;
import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface UserDao {

    @Query("SELECT * FROM user")
    Observable<List<User>> observe();

    @Query("SELECT * FROM user")
    Single<List<User>> getAll();

    @Query("SELECT * FROM user WHERE userId = :id;")
    Maybe<User> get(long id);

    @Insert(onConflict = REPLACE)
    Completable add(User user);

    @Update(onConflict = REPLACE)
    Completable update(User user);

    @Delete
    Completable delete(User user);

    @Query("DELETE FROM user")
    Completable deleteAll();

    @Transaction
    @Query("SELECT * FROM user")
    Observable<List<UserWithProfession>> observeUserWithProfession();

    @Transaction
    @Query("SELECT * FROM user WHERE userId = :id")
    Maybe<UserWithProducts> getUserWithProducts(long id);
}
