package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.hardware.input.InputManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;

import com.example.weatherapp.Models.Data;
import com.example.weatherapp.Models.Main;
import com.example.weatherapp.Models.Weather;
import com.example.weatherapp.databinding.ActivityMainBinding;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        constraintLayout = findViewById(R.id.constraintLayout);

        SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy");
        String currentDate = format.format(new Date());

        binding.tvDate.setText(currentDate);

        fetchWeather("Delhi");

        binding.buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hideKeyboard();
                if (TextUtils.isEmpty(binding.etSearchCity.getText().toString())) {

                    binding.etSearchCity.setError("please enter city");
                    return;
                }

                String CITY_NAME = binding.etSearchCity.getText().toString();

                fetchWeather(CITY_NAME);
            }
        });
    }

    void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(constraintLayout.getApplicationWindowToken(), 0);


    }

    void fetchWeather(String cityName) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        InterfaceApi interfaceApi = retrofit.create(InterfaceApi.class);

        Call<Data> call = interfaceApi.getData(cityName, "80409e07282e463da7a67d85222710b6", "metric");

        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                if (response.isSuccessful()) {
                    Data data = response.body();
                    Main to = data.getMain();

                    binding.tvMainTemp.setText(String.valueOf(to.getTemp())+"\u2103");
                    binding.tvMaxTemp.setText(String.valueOf(to.getTemp_max()));
                    binding.tvMinTemp.setText(String.valueOf(to.getTemp_min()));
                    binding.tvPressureTemp.setText(String.valueOf(to.getPressure()));
                    binding.tvHumidityTemp.setText(String.valueOf(to.getHumidity()));
                    binding.etSearchCity.setText(data.getName());
                }
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {

            }
        });
    }

}