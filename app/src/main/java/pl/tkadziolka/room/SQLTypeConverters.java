package pl.tkadziolka.room;

import androidx.room.TypeConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SQLTypeConverters {

    @TypeConverter
    public Date fromTimestamp(String value) {
        Date defaultDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat();
        try {
            return value.equals("") ? defaultDate : format.parse(value);
        } catch (ParseException e) {
            e.printStackTrace();
            return defaultDate;
        }
    }

    @TypeConverter
    public String toTimestamp(Date date) {
        if (date == null) {
            return "";
        } else {
            return date.toString();
        }
    }
}
