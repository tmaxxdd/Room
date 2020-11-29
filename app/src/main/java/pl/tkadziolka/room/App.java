package pl.tkadziolka.room;

import android.app.Application;

import androidx.room.Room;

import pl.tkadziolka.room.database.Database;
import pl.tkadziolka.room.database.DatabaseCallback;
import pl.tkadziolka.room.database.Migrations;

public class App extends Application {

    public Database db;

    @Override
    public void onCreate() {
        super.onCreate();

        db = Room.databaseBuilder(
            this,
            Database.class,
            "database"
        ).addCallback(new DatabaseCallback())
            .addMigrations(Migrations.MIGRATION_1_2)
            .fallbackToDestructiveMigration()
            .build();
    }
}
