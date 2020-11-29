package pl.tkadziolka.room.database;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class DatabaseCallback extends RoomDatabase.Callback {
    private final static String TAG = "DatabaseCallback";

    @Override
    public void onCreate(@NonNull SupportSQLiteDatabase db) {
        super.onCreate(db);
        Log.d(TAG, "onCreate");
        db.execSQL("INSERT INTO profession" +
            "('professionId', 'name', 'salary')" +
            "VALUES" +
            "(1, 'miner', 10000)," +
            "(2, 'coder', 15000)," +
            "(3, 'accountant', 5000)" +
            ";");
    }

    @Override
    public void onOpen(@NonNull SupportSQLiteDatabase db) {
        super.onOpen(db);
        Log.d(TAG, "onOpen");
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onDestructiveMigration(@NonNull SupportSQLiteDatabase db) {
        super.onDestructiveMigration(db);
        Log.d(TAG, "onDestructiveMigration");
    }
}
