package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import com.example.weatherapp.databinding.ActivityMainBinding;
import java.text.SimpleDateFormat;
import java.util.Date;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity{

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy");
        String currentDate = format.format(new Date());

        binding.date.setText(currentDate);

        binding.searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(binding.searchCityEt.getText().toString())){
                    binding.searchCityEt.setError("Please enter city");
                    return;
                }

                fetchWeather(binding.searchCityEt.getText().toString());
            }
        });
    }

    void fetchWeather(String cityName){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        InterfaceApi interfaceAPI = retrofit.create(InterfaceApi.class);

        Call<CityData> call = interfaceAPI.getData(cityName, "80409e07282e463da7a67d85222710b6", "metric");

        call.enqueue(new Callback<CityData>() {
            @Override
            public void onResponse(Call<CityData> call, Response<CityData> response) {

                if(response.isSuccessful()){
                    CityData cityData = response.body();
                    binding.cityName.setText(cityData.getName());
                    main main = cityData.getMain();
                    binding.temp.setText(String.valueOf(main.getTemp()));
                    binding.maxTemp.setText(String.valueOf(main.getTemp_max()));
                    binding.minTemp.setText(String.valueOf(main.getTemp_min()));
                    binding.pressure.setText(String.valueOf(main.getPressure()));
                    binding.humidity.setText(String.valueOf(main.getHumidity()));
                    Weather weather = cityData.getWeather().get(0);
                    binding.main.setText(weather.getMain());
                }
            }

            @Override
            public void onFailure(Call<CityData> call, Throwable t) {

            }
        });
    }
}