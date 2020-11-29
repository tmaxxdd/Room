package pl.tkadziolka.room.database.dao;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.RewriteQueriesToDropUnusedColumns;
import androidx.room.Transaction;

import java.util.List;

import io.reactivex.Observable;
import pl.tkadziolka.room.database.model.Offer;

@Dao
public interface OfferDao {

    @Transaction
    @RewriteQueriesToDropUnusedColumns
    @Query("SELECT * FROM ProductUserCrossRef" +
        " JOIN Product ON ProductUserCrossRef.productId = Product.productId")
    Observable<List<Offer>> observe();

//    @Transaction
//    @Query("SELECT * FROM ProductUserCrossRef")
//    Single<List<ProductUserCrossRef>> get();
}
