package com.example.agrocare.ui.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.agrocare.model.Crop;
import com.example.agrocare.model.CropWithGenericRecord;
import com.example.agrocare.model.GenericRecord;

import java.util.List;

public class HomeViewModel extends AndroidViewModel {

    private CropRepository cropRepository;
    private LiveData<List<Crop>> allCrops;
    private LiveData<List<CropWithGenericRecord>> allRecords;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        cropRepository=new CropRepository(application);
        allCrops= cropRepository.getAllCrops();
    }

    public void insertCrop(Crop crop)
    {
        cropRepository.insert(crop);
    }
    public void insertRecord(GenericRecord record)
    {
        cropRepository.insertRecord(record);
    }
    public void updateCrop(Crop crop)
    {
        cropRepository.update(crop);
    }
    public void deleteCrop(Crop crop)
    {
        cropRepository.delete(crop);
    }
    public void deleteRecord(GenericRecord record)
    {
        cropRepository.deleteRecord(record);
    }
    public void deleteAllCrop()
    {
        cropRepository.deleteAllNotes();
    }

    public LiveData<List<Crop>> getAllCrops() {
        return allCrops;
    }

}
