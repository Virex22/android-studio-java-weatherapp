package com.vincent.applicationmeteo.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.vincent.applicationmeteo.R;
import com.vincent.applicationmeteo.model.WeatherInfo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class WheatherInfoAdapter extends RecyclerView.Adapter<WheatherInfoAdapter.WeatherInfoViewHolder> {

    private ArrayList<WeatherInfo> weatherInfos;
    private ClicProduct listener;

    public WheatherInfoAdapter(ArrayList<WeatherInfo> weatherInfos, ClicProduct listener) {
        this.weatherInfos = weatherInfos;
        this.listener = listener;
    }

    public interface ClicProduct {
        void onClick(int position);
    }

    static class WeatherInfoViewHolder extends RecyclerView.ViewHolder {

        private TextView TV_temp;
        private TextView TV_date;
        private TextView TV_day;
        private ImageView IV_image;
        private Button BT_details;

        public WeatherInfoViewHolder(View itemView) {

            super(itemView);

            TV_temp = itemView.findViewById(R.id.TV_Temp);
            TV_date = itemView.findViewById(R.id.TV_Forecast_date);
            TV_day = itemView.findViewById(R.id.TV_day);
            BT_details = itemView.findViewById(R.id.BT_details);
            IV_image = itemView.findViewById(R.id.IV_Logo);
        }
    }

    @NonNull
    @Override
    public WeatherInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vueItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.meteo_part, parent, false);
        return new WeatherInfoViewHolder(vueItem);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherInfoViewHolder holder, int position) {
        WeatherInfo weatherInfo = weatherInfos.get(position);
        holder.TV_temp.setText(weatherInfo.getTemperature());
        DateFormat dayDateFr =new SimpleDateFormat("EEEE", Locale.FRANCE);
        DateFormat numDateFr =new SimpleDateFormat("dd MMMM yyyy 'à' HH:mm", Locale.FRANCE);
        holder.TV_date.setText(numDateFr.format(weatherInfo.getDate()));
        holder.TV_day.setText(dayDateFr.format(weatherInfo.getDate()));
        holder.BT_details.setOnClickListener(v -> listener.onClick(position));

        Glide.with(holder.itemView.getContext())
                .load(weatherInfo.getImage())
                .into(holder.IV_image);
    }

    @Override
    public int getItemCount() {
        return weatherInfos.size();
    }

}