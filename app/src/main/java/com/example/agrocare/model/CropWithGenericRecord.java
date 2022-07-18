package com.example.agrocare.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class CropWithGenericRecord {

    @Embedded
    public Crop crop;

    @Relation(
            parentColumn = "plantId",
            entityColumn = "plantId"
    )

    public List<GenericRecord> records;

    public CropWithGenericRecord()
    {

    }

}
