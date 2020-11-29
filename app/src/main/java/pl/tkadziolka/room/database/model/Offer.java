package pl.tkadziolka.room.database.model;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.Date;
import java.util.List;

public class Offer {
    @Embedded
    public Product product;

    @Relation(
        entity = User.class,
        parentColumn = "productId",
        entityColumn = "userId",
        associateBy = @Junction(ProductUserCrossRef.class)
    )
    public List<User> users;
}
