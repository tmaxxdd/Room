package pl.tkadziolka.room.database;

import androidx.room.RoomDatabase;

import pl.tkadziolka.room.database.dao.OfferDao;
import pl.tkadziolka.room.database.dao.ProductDao;
import pl.tkadziolka.room.database.model.Offer;
import pl.tkadziolka.room.database.model.Product;
import pl.tkadziolka.room.database.model.ProductUserCrossRef;
import pl.tkadziolka.room.database.model.Profession;
import pl.tkadziolka.room.database.dao.ProfessionDao;
import pl.tkadziolka.room.database.model.User;
import pl.tkadziolka.room.database.dao.UserDao;

@androidx.room.Database(
    entities = {User.class, Profession.class, Product.class, ProductUserCrossRef.class},
    version = 5
)
abstract public class Database extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract ProfessionDao professionDao();
    public abstract ProductDao productDao();
    public abstract OfferDao offerDao();
}
