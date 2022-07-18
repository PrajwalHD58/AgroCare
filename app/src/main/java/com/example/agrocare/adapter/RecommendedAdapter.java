package com.example.agrocare.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agrocare.R;
import com.example.agrocare.model.Crop;
import com.example.agrocare.model.Plant;
import com.example.agrocare.ui.home.HomeDetailActivity;
import com.example.agrocare.ui.home.IndividualPlant;
import com.example.agrocare.viewholder.RecommendedViewHolder;

import java.util.ArrayList;
import java.util.List;


public class RecommendedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private final List<Crop> plants;

    public RecommendedAdapter(Context context, List<Crop> plants) {
        this.context = context;
        this.plants = plants;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommended_item, parent, false);
        return new RecommendedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        setImage(((RecommendedViewHolder) holder).recommended_picture,R.drawable.image_1 );
        setTitle(((RecommendedViewHolder) holder).recommended_title, plants.get(position).getPlantName());
        setPrice(((RecommendedViewHolder) holder).recommended_price, String.valueOf(plants.get(position).getDaysFromStart()));
        setOnClick(((RecommendedViewHolder) holder).recommended_parent,position);
    }

    private void setImage(final ImageView imageView, int image) {

        imageView.setBackgroundResource(image);

//        Picasso.get().load(imageURL).fit().centerCrop().into(imageView, new Callback() {
//            @Override
//            public void onSuccess() {
//
//            }
//
//            @Override
//            public void onError(Exception e) {
//                imageView.setBackgroundResource(R.drawable.ic_launcher_background);
//            }
//        });
    }

    private void setTitle(TextView textView, String plantTitle) {
        textView.setText(plantTitle);
    }

    private void setCountry(TextView textView, String plantCountry) {
        textView.setText(plantCountry);
    }

    private void setPrice(TextView textView, String plantPrice) {
        textView.setText(plantPrice+" days.");
    }

    private void setOnClick(RelativeLayout button,int position) {

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, IndividualPlant.class);
                i.putExtra("Curr_Crop", plants.get(position));
                context.startActivity(i);

            }
        });

    }

    @Override
    public int getItemCount() {
        return plants.size();
    }

    public void dataChange()
    {
        notifyDataSetChanged();
    }

    public void updateCropList(List<Crop> newlist) {
        plants.clear();
        plants.addAll(newlist);
        this.notifyDataSetChanged();
    }

}
