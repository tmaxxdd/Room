package pl.tkadziolka.room.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import pl.tkadziolka.room.database.model.Product;
import pl.tkadziolka.room.database.model.ProductUserCrossRef;

@Dao
public interface ProductDao {

    @Query("SELECT * FROM product")
    List<Product> getAll();

    @Query("INSERT INTO product (title, price) VALUES(:title, :price)")
    Single<Long> add(String title, double price);

    @Insert
    Completable addWithUser(ProductUserCrossRef productUserCrossRef);
}
