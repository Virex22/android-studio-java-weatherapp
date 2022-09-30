package com.vincent.applicationmeteo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.vincent.applicationmeteo.model.WeatherInfo;
import com.vincent.applicationmeteo.utils.LocalisationFinder;
import com.vincent.applicationmeteo.utils.RequestManager;
import com.vincent.applicationmeteo.utils.WheatherInfoAdapter;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.InputStream;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class HomePage extends AppCompatActivity {

    final String API_ID = "fc402cbfde9129fb6c8bda5d90257622";

    private String getImage(String url) {
        return "https://openweathermap.org/img/wn/" + url + "@4x.png";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        RecyclerView recyclerView = findViewById(R.id.RV_ListForecast);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        LocalisationFinder localisationFinder = new LocalisationFinder(this, (latitude, longitude) -> {
            RequestManager.getInstance(this).addToRequestQueue(this.requestForecast(recyclerView,latitude, longitude));
            RequestManager.getInstance(this).addToRequestQueue(this.requestToday(latitude, longitude));
        });

        LocationManager mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},101);
            return;
        }
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 1000, localisationFinder);
    }

    private void errorRequest(String tag, Exception error) {
        if (error.getMessage() == "")
            Log.e(tag , error.getMessage());
        error.printStackTrace();
        Toast.makeText(this, "Erreur de requête", Toast.LENGTH_SHORT).show();
    }

    private JsonObjectRequest requestToday(String latitude, String longitude) {
        return new JsonObjectRequest("https://api.openweathermap.org/data/2.5/weather?lat="+latitude+"&lon="+longitude+"&units=metric&appid=" + API_ID,
                response -> {
                    try {
                        Date date = new Date(response.getLong("dt") * 1000);
                        TextView TV_mainTemp = findViewById(R.id.TV_MainTemp);
                        TV_mainTemp.setText(response.getJSONObject("main").getString("temp") + "°C");
                        TextView TV_mainDate = findViewById(R.id.TV_mainDate);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE dd MMMM yyyy" , Locale.FRENCH);
                        TV_mainDate.setText(dateFormat.format(date));
                        TextView TV_mainCity = findViewById(R.id.TV_city);
                        TV_mainCity.setText(response.getString("name"));
                        String image = response.getJSONArray("weather").getJSONObject(0).getString("icon");
                        try {
                            Glide.with(this).load(getImage(image)).into((ImageView) findViewById(R.id.IV_main));
                        } catch (Exception e) {errorRequest("error", e);}
                    }
                    catch (JSONException e){errorRequest("error_fetch", e);}
                } ,
                e -> {errorRequest("error_fetch", e);});
    }

    private JsonObjectRequest requestForecast(RecyclerView recyclerView,String latitude, String longitude) {

        return new JsonObjectRequest(
                "https://api.openweathermap.org/data/2.5/forecast?lat="+latitude+"&lon="+longitude+"&units=metric&appid=" + API_ID,
                resultat -> {
                    ArrayList<WeatherInfo> weatherInfos = new ArrayList<>();
                    try {
                        JSONArray listWeather = resultat.getJSONArray("list");
                        Log.d("test", listWeather.toString());
                        String ville = resultat.getJSONObject("city").get("name").toString();
                        for (int i = 0; i < listWeather.length(); i++) {
                            String temperature = listWeather.getJSONObject(i).getJSONObject("main").get("temp").toString() + "°C";
                            String temperatureRessentie = listWeather.getJSONObject(i).getJSONObject("main").get("feels_like").toString() + "°C";
                            String humidity = listWeather.getJSONObject(i).getJSONObject("main").get("humidity").toString() + "%";
                            String wind = listWeather.getJSONObject(i).getJSONObject("wind").get("speed").toString() + "km/h";
                            String WindDeg = listWeather.getJSONObject(i).getJSONObject("wind").get("deg").toString() + "°";
                            long timestamp = listWeather.getJSONObject(i).getLong("dt");
                            String image = listWeather.getJSONObject(i).getJSONArray("weather").getJSONObject(0).get("icon").toString();
                            Toast.makeText(this, "Fetch completer", Toast.LENGTH_SHORT).show();
                            weatherInfos.add(new WeatherInfo(ville, temperature,new Date(timestamp * 1000) , getImage(image), temperatureRessentie, humidity, wind, WindDeg));
                        }
                        recyclerView.setAdapter(new WheatherInfoAdapter(weatherInfos,
                                position->{
                                    Intent intent = new Intent(
                                            this,
                                            ViewForecast.class);

                                    intent.putExtra("weather",weatherInfos.get(position));
                                    startActivity(intent);
                                }));

                                ;
                    }
                    catch (Exception e)  {errorRequest("error_fetch", e);}
                },
                e -> {errorRequest("error_fetch", e);}
        );
    }



}