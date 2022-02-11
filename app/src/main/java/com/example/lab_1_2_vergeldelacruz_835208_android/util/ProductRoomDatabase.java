package com.example.lab_1_2_vergeldelacruz_835208_android.util;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.lab_1_2_vergeldelacruz_835208_android.data.ProductDao;
import com.example.lab_1_2_vergeldelacruz_835208_android.entity.Product;

@Database(entities = {Product.class}, version = 1, exportSchema = false)

public abstract class ProductRoomDatabase extends RoomDatabase {

    public abstract ProductDao productDao();
    private static final String DB_NAME = "product_room_db";

    private static volatile ProductRoomDatabase INSTANCE;

    public static ProductRoomDatabase getInstance(Context context) {
        if (INSTANCE == null)
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ProductRoomDatabase.class, DB_NAME)
                    .allowMainThreadQueries()
                    .build();
        return INSTANCE;
    }
}