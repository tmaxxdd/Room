package pl.tkadziolka.room.database.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

import static androidx.room.ForeignKey.CASCADE;

@Entity(primaryKeys = {"userId", "productId"},
    foreignKeys = {
    @ForeignKey(
        entity = Product.class,
        parentColumns = "productId",
        childColumns = "productId",
        onDelete = CASCADE
    ), @ForeignKey(
        entity = User.class,
        parentColumns = "userId",
        childColumns = "userId",
        onDelete = CASCADE
    )},
    indices = {@Index(value = "userId"), @Index(value = "productId")}
)
public class ProductUserCrossRef {
    public long userId;
    public long productId;

    public ProductUserCrossRef(long userId, long productId) {
        this.userId = userId;
        this.productId = productId;
    }
}
