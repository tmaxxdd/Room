package pl.tkadziolka.room.database.model;

import android.graphics.Bitmap;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

import pl.tkadziolka.room.SQLTypeConverters;

@Entity
public class Product {
    @PrimaryKey(autoGenerate = true)
    public long productId;
    @ColumnInfo(name = "title")
    public String name;
    @ColumnInfo(defaultValue = "0.0")
    public double price;
    @Ignore
    public Bitmap image;
    @TypeConverters(value = SQLTypeConverters.class)
    @ColumnInfo(defaultValue = "CURRENT_TIMESTAMP")
    public Date timestamp;

    public Product(long productId, String name, double price) {
        this.productId = productId;
        this.name = name;
        this.price = price;
    }
}
