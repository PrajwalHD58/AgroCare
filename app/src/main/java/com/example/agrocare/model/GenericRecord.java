package com.example.agrocare.model;



import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.agrocare.R;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@Entity(tableName = "generic_records")
public class GenericRecord implements Serializable {

    @PrimaryKey
    public long id;
    public long plantId;
    public int obsNo = 0;
    public long time;
    public String remark;
    public String notes;

    public GenericRecord()
    {

    }

    public GenericRecord(long id,long plantId, int displayName, long time, String remark, String notes) {
        this.obsNo = displayName;
        this.plantId=plantId;
        this.time = time;
        this.id = id;
        this.remark = remark;
        this.notes = notes;

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDisplayName() {

        if (obsNo == 1) {
            return "Water";
        } else if (obsNo == 2) {
            return "Phase Change";
        } else if (obsNo == 3) {
            return "Feeding/Fertilizer";
        } else if (obsNo == 4) {
            return "Disease Detected";
        } else if (obsNo == 5) {
            return "Pesticides/Insecticides";
        } else if (obsNo == 6) {
            return "Weedicides/Herbicides";
        } else if (obsNo == 7) {
            return "Picture";
        } else if (obsNo == 8) {
            return "Other Observation";
        }

        return "";

    }


    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getDisplayTime() {
        Date d = new Date(this.time);

        String ans = new SimpleDateFormat("yyyy-MM-dd").format(d);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        String day = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.US).toUpperCase();
        return (day+", "+ans);
    }

    public String getDisplayRemark() {
        String ans = this.remark;
        return ans;
    }

    public String getDisplayNotes() {
        String ans = "Notes: " + this.notes;
        return ans;
    }

    public int getDisplayColor() {

        if (this.obsNo == 1) {
            return R.color.col1;
        } else if (this.obsNo == 2) {
            return R.color.col2;
        } else if (this.obsNo == 3) {
            return R.color.col3;
        } else if (this.obsNo == 4) {
            return R.color.col4;
        } else if (this.obsNo == 5) {
            return R.color.col5;
        } else if (this.obsNo == 6) {
            return R.color.col6;
        } else if (this.obsNo == 7) {
            return R.color.col7;
        }
        return R.color.col8;

    }

    public int getObsNo() {
        return obsNo;
    }

    public long getPlantId() {
        return plantId;
    }

    public void setPlantId(long plantId) {
        this.plantId = plantId;
    }

    public void setObsNo(int obsNo) {
        this.obsNo = obsNo;
    }
}
