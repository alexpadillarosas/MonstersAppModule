package com.example.monstersappmodule8.database;

import androidx.room.TypeConverter;

import java.util.Date;

/**
 * https://developer.android.com/reference/androidx/room/TypeConverter
 *
 */

public class Converters {

    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
