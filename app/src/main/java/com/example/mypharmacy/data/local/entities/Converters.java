package com.example.mypharmacy.data.local.entities;

import androidx.room.TypeConverter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;


public class Converters {

    @TypeConverter
    public static LocalDate fromTimestamp(Long value) {
        return value == null ? null : LocalDate.ofEpochDay(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(LocalDate date) {
        return date == null ? null : date.toEpochDay();
    }

    @TypeConverter
    public static List<Timestamp> fromString(String value) {
        Type listType = new TypeToken<List<Timestamp>>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String toString(List<Timestamp> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }
}
