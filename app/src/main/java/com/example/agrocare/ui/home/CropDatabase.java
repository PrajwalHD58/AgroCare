package com.example.agrocare.ui.home;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.agrocare.interf.CropDao;
import com.example.agrocare.model.Crop;
import com.example.agrocare.model.GenericRecord;

@Database(entities = {Crop.class, GenericRecord.class},version = 1)
public abstract class CropDatabase extends RoomDatabase {

    private static CropDatabase instance;
    public abstract CropDao cropDao();

    public static synchronized CropDatabase getInstance(Context context)
    {
        if(instance==null)
        {
            instance= Room.databaseBuilder(context.getApplicationContext(),CropDatabase.class,"your_crops_databse")
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return instance;
    }
}
