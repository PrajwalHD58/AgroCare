package com.example.agrocare.model;


import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

@Entity(tableName = "your_crops")
public class Crop implements Serializable {

    @PrimaryKey(autoGenerate = false)
    public long plantId;

    public String plantName;

    public long startTime;

    public boolean isFromSeed;

//    @Ignore
//    public ArrayList<GenericRecord> genericRecords;

    public Crop() {
//        genericRecords = new ArrayList<>();

    }

    public Crop(long plantId, String plantName, long growStartDate, boolean isFromSeed) {

//        updateListeners = new ArrayList<>();
//        genericRecords = new ArrayList<>();
        this.plantName = plantName;
        this.plantId = plantId;
        this.startTime = growStartDate;
        this.isFromSeed = isFromSeed;

    }


    public String getPlantName() {
        return this.plantName;
    }

    public void setPlantName(String name) {
        this.plantName = name;
    }


    public long getPlantId() {
        return this.plantId;
    }

    public boolean isFromSeed() {
        return this.isFromSeed;
    }

//    public ArrayList<GenericRecord> getAllGenericRecords() {
//        ArrayList<GenericRecord> rec = new ArrayList<>();
//
//        for (GenericRecord er : this.genericRecords) {
//            rec.add((GenericRecord) er);
//        }
//
//        return sortEvents(rec);
//    }


    public long getDaysFromStart() {
        return getDaysFromStart(Calendar.getInstance());
    }

    public long getDaysFromStart(Calendar c) {
        return Utility.calcDaysFromTime(startTime, c);
    }

    public long getWeeksFromStart() {
        return getWeeksFromStart(Calendar.getInstance());
    }

    public long getWeeksFromStart(Calendar c) {
        return Utility.calcWeeksFromTime(startTime, c);
    }

    public Calendar getPlantStartDate() {
        Date d = new Date(startTime);
        Calendar c = Calendar.getInstance();
        c.setTime(d);

        return c;
    }


    // plant object maintenance
//    private void sortEvents() {
//        this.genericRecords.sort(new Comparator<GenericRecord>() {
//            @Override
//            public int compare(GenericRecord o1, GenericRecord o2) {
//                if ((o1.time) < (o2.time)) {
//                    return 0;
//                } else {
//                    return 1;
//                }
//            }
//        });
//    }

    private ArrayList<GenericRecord> sortEvents(ArrayList<GenericRecord> records) {
        records.sort(new Comparator<GenericRecord>() {
            @Override
            public int compare(GenericRecord o1, GenericRecord o2) {
                if ((o1.time) < (o2.time)) {
                    return 0;
                } else {
                    return 1;
                }
            }
        });

        return records;
    }


    @Override
    public boolean equals(Object p) {
        return ((Crop) p).getPlantId() == getPlantId();
    }

//    public void addGenericRecord(GenericRecord record) {
//        genericRecords.add(record);
//        sortEvents();
//    }

    public void setPlantId(long plantId) {
        this.plantId = plantId;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public void setFromSeed(boolean fromSeed) {
        isFromSeed = fromSeed;
    }
}
