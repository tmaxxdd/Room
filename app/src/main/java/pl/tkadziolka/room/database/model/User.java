package pl.tkadziolka.room.database.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ColumnInfo.INTEGER;
import static androidx.room.ForeignKey.RESTRICT;

@Entity(foreignKeys = @ForeignKey(
    entity = Profession.class,
    parentColumns = "professionId",
    childColumns = "professionOwnerId",
    onDelete = RESTRICT
))
public class User {
    @PrimaryKey(autoGenerate = true)
    public long userId;
    public String name;
    public boolean isMan;
    public int age;
    @ColumnInfo(defaultValue = "1", index = true)
    public long professionOwnerId;

    public User(long userId, String name, boolean isMan, int age, long professionOwnerId) {
        this.userId = userId;
        this.name = name;
        this.isMan = isMan;
        this.age = age;
        this.professionOwnerId = professionOwnerId;
    }
}
