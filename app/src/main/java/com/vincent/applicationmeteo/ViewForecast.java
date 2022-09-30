package com.vincent.applicationmeteo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.vincent.applicationmeteo.model.WeatherInfo;

import java.text.SimpleDateFormat;

public class ViewForecast extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_forecast);
        Log.i("ViewForecast", "onCreate: ");

        WeatherInfo weatherInfo = (WeatherInfo) getIntent().getSerializableExtra("weather");

        TextView TV_forecast_day = findViewById(R.id.TV_forecast_day);
        TextView TV_forecast_city = findViewById(R.id.TV_forecast_city);
        TextView TV_forecast_temperature = findViewById(R.id.TV_forecast_temp);
        TextView TV_forecast_date = findViewById(R.id.TV_Forecast_date);
        ImageView IV_forecast_image = findViewById(R.id.IV_forecast);
        TextView TV_forecast_humidity = findViewById(R.id.TV_forecast_Humidity);
        TextView TV_forecast_wind = findViewById(R.id.TV_forecast_Vent);
        TextView TV_forecast_tempRessenti = findViewById(R.id.TV_forecast_temp_ressenti);


        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");


        TV_forecast_day.setText(dayFormat.format(weatherInfo.getDate()));
        TV_forecast_city.setText(weatherInfo.getCity());
        TV_forecast_temperature.setText(weatherInfo.getTemperature());
        TV_forecast_date.setText(dateFormat.format(weatherInfo.getDate()));
        TV_forecast_humidity.setText("humidité : " + weatherInfo.getHumidity());
        TV_forecast_wind.setText("vent : " + weatherInfo.getWindSpeed() + " / " + weatherInfo.getWindDeg());
        TV_forecast_tempRessenti.setText("ressenti : " + weatherInfo.getTemperatureRessenti());


        Glide.with(this).load(weatherInfo.getImage()).into(IV_forecast_image);

        Button BT_Retour = findViewById(R.id.BT_Retour);
        BT_Retour.setOnClickListener(v -> {
            finish();
        });
    }
}