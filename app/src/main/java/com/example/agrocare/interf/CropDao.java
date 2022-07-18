package com.example.agrocare.interf;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.agrocare.model.Crop;
import com.example.agrocare.model.CropWithGenericRecord;
import com.example.agrocare.model.GenericRecord;

import java.util.List;

@Dao
public interface CropDao {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCrop(Crop crop);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRecord(GenericRecord record);

    @Transaction
    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateCrop(Crop crop);


    @Transaction
    @Delete
    void deleteCrop(Crop crop);

    @Delete
    void deleteRecord(GenericRecord record);


    @Query("DELETE FROM your_crops")
    void deleteAllCrop();


    @Transaction
    @Query("SELECT * FROM your_crops ORDER BY plantId DESC")
    LiveData<List<Crop>> getAllCrops();

    @Query("SELECT * FROM your_crops WHERE plantId=:plantId")
    CropWithGenericRecord getAllCropWithGenericRecord(long plantId);

}
