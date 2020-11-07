package com.example.nb_interview.database.typeconverter;

import androidx.room.TypeConverter;

import com.example.nb_interview.model.Products;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class TypeProduct {

    static Gson gson = new Gson();

    @TypeConverter
    public static Products stringToSomeObjectList(String data) {
        if (data == null) {
            return null;
        }

        Type listType = new TypeToken<Products>() {}.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String someObjectListToString(Products someObjects) {
        return gson.toJson(someObjects);
    }
}
