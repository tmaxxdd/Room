package pl.tkadziolka.room.database.model;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class UserWithProducts {
    @Embedded
    public User user;

    @Relation(
        entity = Product.class,
        parentColumn = "userId",
        entityColumn = "productId",
        associateBy = @Junction(ProductUserCrossRef.class)
    )
    public List<Product> products;
}
