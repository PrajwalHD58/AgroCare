package com.example.agrocare.ui.home;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agrocare.R;
import com.example.agrocare.adapter.FeaturedPlantsAdapter;
import com.example.agrocare.adapter.RecommendedAdapter;
import com.example.agrocare.interf.IDialogHandler;
import com.example.agrocare.model.Crop;
import com.example.agrocare.model.Plant;
import com.example.agrocare.splashscreen.screen1;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HomeFragment extends Fragment {
    private Context mContext;
    private RecyclerView recyclerView1, recyclerView2;
    private LinearLayoutManager layoutManager;
    private FeaturedPlantsAdapter groupAdapter1;
    private RecommendedAdapter groupAdapter2;
    private ArrayList<Plant> featured_plants;
    private List<Crop> your_crops;
    private Button add_crops, more_crops;
    private HomeViewModel homeViewModel;
    //todo 0. If you want to add more types of objects, you can easily do so. Check "todo list" (I added it step by step)

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.mContext = container.getContext();
        homeViewModel = new ViewModelProvider(getActivity()).get(HomeViewModel.class);
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView username = view.findViewById(R.id.your_name);
        username.setText("Hello " + screen1.getUsername() + "!");
        recyclerView1 = view.findViewById(R.id.group_recycler_view_crops);
        recyclerView2 = view.findViewById(R.id.group_recycler_view_rcrops);
        add_crops = view.findViewById(R.id.add_crop);
        more_crops = view.findViewById(R.id.view_rall);
        initPlantData();
        setAdapter();

        groupAdapter1 = new FeaturedPlantsAdapter(mContext, featured_plants);
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        recyclerView2.setAdapter(groupAdapter1);
        recyclerView2.setNestedScrollingEnabled(true);

        homeViewModel.getAllCrops().observe(getActivity(), new Observer<List<Crop>>() {
            @Override
            public void onChanged(List<Crop> crops) {
                your_crops.clear();
                your_crops = crops;
                groupAdapter2.updateCropList(your_crops);
            }
        });


        add_crops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Add crops", Toast.LENGTH_SHORT).show();
                presentGenericEventDialog(R.id.dialogNewPlantLayout, getAddPlantDialogHandler(0));
            }
        });

        more_crops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "More crops", Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void initPlantData() {
        featured_plants = new ArrayList<>();
        your_crops = new ArrayList<>();

        featured_plants.add(new Plant("Plant Name", "Scientific Name", "Growth Period", R.drawable.bottom_img_1));
        featured_plants.add(new Plant("Maize", "Zea mays Linn", "200 days", R.drawable.bottom_img_1));
        featured_plants.add(new Plant("Wheat", "Tritium cesium", "100 days", R.drawable.bottom_img_1));

    }


    private void setAdapter() {


        //todo 1. Add the new object to the parameter list.
        groupAdapter2 = new RecommendedAdapter(mContext, your_crops);
        recyclerView1.setHasFixedSize(true);
        recyclerView1.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        recyclerView1.setAdapter(groupAdapter2);
        recyclerView1.setNestedScrollingEnabled(true);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    private void presentGenericEventDialog(final int layoutId, IDialogHandler dialogHandler) {
        final Dialog dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.dialog_generic_event);
        TabHost tabs = dialog.findViewById(R.id.tabHost);
        tabs.setup();
        tabs.setCurrentTab(0);

        TabHost.TabSpec dialogTab = tabs.newTabSpec("Tab1");
        if (layoutId > 0) {
            dialogTab.setIndicator("Info");
            dialogTab.setContent(layoutId);
            tabs.addTab(dialogTab);
        }

        TabHost.TabSpec changeDateTab = tabs.newTabSpec("Tab2");
        changeDateTab.setIndicator("Set Date");
        changeDateTab.setContent(R.id.tab2);
        tabs.addTab(changeDateTab);

        TabHost.TabSpec changeTimeTab = tabs.newTabSpec("Tab3");
        changeTimeTab.setIndicator("Set Time");
        changeTimeTab.setContent(R.id.tab3);
        tabs.addTab(changeTimeTab);

        TabHost.TabSpec attachImagesTab = tabs.newTabSpec("Tab4");
        attachImagesTab.setIndicator("Attach Images");
        attachImagesTab.setContent(R.id.tab4);
//        tabs.addTab(attachImagesTab);

        tabs.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                // Hide the keyboard if we're on any of the static tabs
                if (!tabId.equals("Tab1")) {
                    View view = dialog.getCurrentFocus();
                    if (view != null) {
                        InputMethodManager imm =
                                (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);

                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                }
            }
        });

        bindAttachImagesControls(dialog);
        dialogHandler.bindDialog(dialog);
        dialog.show();
    }

    private IDialogHandler getAddPlantDialogHandler(final long parentPlantId) {
        return new IDialogHandler() {
            @Override
            public void bindDialog(final Dialog dialog) {
                LinearLayout layout = dialog.findViewById(R.id.applyToGroupLayout);
                layout.setVisibility(View.GONE);


                Button okButton = dialog.findViewById(R.id.okButton);
                okButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        EditText plantNameEditText = dialog.findViewById(
                                R.id.plantNameEditText);

                        String plantName = plantNameEditText.getText().toString();

                        RadioGroup rg = dialog.findViewById(R.id.originRadioGroup);
                        boolean isFromSeed;
                        int selectedId = rg.getCheckedRadioButtonId();
                        RadioButton selectedOrigin = dialog.findViewById(selectedId);
                        RadioButton cloneRadioButton = dialog.findViewById(R.id.cloneRadioButton);


                        if (selectedOrigin.isChecked() && selectedOrigin == cloneRadioButton) {
                            isFromSeed = false;
                        } else {
                            isFromSeed = true;
                        }

                        Calendar c = getEventCalendar(dialog);
                        long plantId = System.currentTimeMillis();
                        Date d = c.getTime();
                        long timeInMillies = d.getTime();
                        Crop currentPlant = new Crop(plantId, plantName, timeInMillies, isFromSeed);
                        homeViewModel.insertCrop(currentPlant);
                        dialog.dismiss();

                    }
                });

                Button cancelButton = dialog.findViewById(R.id.cancelButton);
                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                try {
                    dialog.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }


    private Calendar getEventCalendar(final Dialog dialog) {
        final DatePicker datePicker = dialog.findViewById(R.id.
                eventDatePicker);

        final TimePicker timePicker = dialog.findViewById(R.id.eventTimePicker);

        Calendar cal = Calendar.getInstance();
        cal.set(datePicker.getYear(), datePicker.getMonth(),
                datePicker.getDayOfMonth(), timePicker.getHour(),
                timePicker.getMinute());

        return cal;
    }

    private void bindAttachImagesControls(Dialog dialog) {
        Button openCameraButton = dialog.findViewById(R.id.openCameraButton);
        openCameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //FIXME open camera activity
            }
        });

        Button attachImagesButton = dialog.findViewById(R.id.attachImagesButton);
        attachImagesButton.setEnabled(false);
//        attachImagesButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //FIXME open picture browser for selecting existing images
//            }
//        });

    }

}
