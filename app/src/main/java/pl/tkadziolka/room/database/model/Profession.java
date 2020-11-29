package pl.tkadziolka.room.database.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Profession {
    @PrimaryKey
    public long professionId;
    public String name;
    public int salary;
}
