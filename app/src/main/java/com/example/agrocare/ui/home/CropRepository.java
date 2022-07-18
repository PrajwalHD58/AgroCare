package com.example.agrocare.ui.home;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.agrocare.interf.CropDao;
import com.example.agrocare.model.Crop;
import com.example.agrocare.model.CropWithGenericRecord;
import com.example.agrocare.model.GenericRecord;

import java.util.List;

public class CropRepository {

    private CropDao cropDao;
    private LiveData<List<Crop>> allCrops;

    public CropRepository(Application application)
    {
        CropDatabase cropDatabase=CropDatabase.getInstance(application);
        cropDao= cropDatabase.cropDao();
        allCrops=cropDao.getAllCrops();
    }

    public void insert(Crop crop)
    {
        new InsetCropAsyncTask(cropDao).execute(crop);
    }
    public void insertRecord(GenericRecord record)
    {
        new InsetRecordAsyncTask(cropDao).execute(record);
    }
    public void update(Crop crop)
    {
        new UpdateCropAsyncTask(cropDao).execute(crop);
    }
    public void delete(Crop crop)
    {
        new DeleteCropAsyncTask(cropDao).execute(crop);
    }
    public void deleteRecord(GenericRecord record)
    {
        new DeleteRecordAsyncTask(cropDao).execute(record);
    }

    public void deleteAllNotes()
    {
        new DeleteAllCropAsyncTask(cropDao).execute();
    }

    public LiveData<List<Crop>> getAllCrops() {
        return allCrops;
    }

    private static class InsetCropAsyncTask extends AsyncTask<Crop,Void,Void>
    {
        private CropDao cropDao;

        private InsetCropAsyncTask(CropDao cropDao)
        {
            this.cropDao=cropDao;
        }

        @Override
        protected Void doInBackground(Crop... crops) {
            cropDao.insertCrop(crops[0]);
            return null;
        }
    }


    private static class InsetRecordAsyncTask extends AsyncTask<GenericRecord,Void,Void>
    {
        private CropDao cropDao;

        private InsetRecordAsyncTask(CropDao cropDao)
        {
            this.cropDao=cropDao;
        }


        @Override
        protected Void doInBackground(GenericRecord... genericRecords) {
            cropDao.insertRecord(genericRecords[0]);
            return null;
        }
    }

    private static class UpdateCropAsyncTask extends AsyncTask<Crop,Void,Void>
    {
        private CropDao cropDao;

        private UpdateCropAsyncTask(CropDao cropDao)
        {
            this.cropDao=cropDao;
        }

        @Override
        protected Void doInBackground(Crop... crops) {
            cropDao.updateCrop(crops[0]);
            return null;
        }
    }

    private static class DeleteCropAsyncTask extends AsyncTask<Crop,Void,Void>
    {
        private CropDao cropDao;

        private DeleteCropAsyncTask(CropDao cropDao)
        {
            this.cropDao=cropDao;
        }

        @Override
        protected Void doInBackground(Crop... crops) {
            cropDao.deleteCrop(crops[0]);
            return null;
        }
    }

    private static class DeleteRecordAsyncTask extends AsyncTask<GenericRecord,Void,Void>
    {
        private CropDao cropDao;

        private DeleteRecordAsyncTask(CropDao cropDao)
        {
            this.cropDao=cropDao;
        }


        @Override
        protected Void doInBackground(GenericRecord... genericRecords) {
            cropDao.deleteRecord(genericRecords[0]);
            return null;
        }
    }

    private static class DeleteAllCropAsyncTask extends AsyncTask<Void,Void,Void>
    {
        private CropDao cropDao;

        private DeleteAllCropAsyncTask(CropDao cropDao)
        {
            this.cropDao=cropDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            cropDao.deleteAllCrop();
            return null;
        }
    }
}
