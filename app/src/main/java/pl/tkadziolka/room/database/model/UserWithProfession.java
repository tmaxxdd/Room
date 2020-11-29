package pl.tkadziolka.room.database.model;

import androidx.room.Embedded;
import androidx.room.Index;
import androidx.room.Relation;

import java.util.List;

public class UserWithProfession {
    @Embedded
    public User user;

    @Relation(
        entity = Profession.class,
        parentColumn = "professionOwnerId",
        entityColumn = "professionId"
    )
    public List<Profession> professions;
}
