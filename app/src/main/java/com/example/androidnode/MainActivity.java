package com.example.androidnode;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://192.168.43.163:5000";

    Button loginBtn;
    EditText first_name, last_name, phone_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         first_name = findViewById(R.id.firstName);
         last_name = findViewById(R.id.lastName);
         phone_number = findViewById(R.id.phoneNumber);
         loginBtn = findViewById(R.id.button);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);

    }

    public void handleOnClick(View view) {

        HashMap<String, String> map = new HashMap<>();

        map.put("first_name", first_name.getText().toString());
        map.put("last_name", last_name.getText().toString());
        map.put("phone_number", phone_number.getText().toString());

        Call<Void> call = retrofitInterface.executeSignup(map);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                if (response.code() == 200) {
                    Toast.makeText(MainActivity.this,
                            "Signed up successfully", Toast.LENGTH_LONG).show();
                } else if (response.code() == 400) {
                    Toast.makeText(MainActivity.this,
                            "Already registered", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(MainActivity.this, "error okay, " + t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });

    }
}