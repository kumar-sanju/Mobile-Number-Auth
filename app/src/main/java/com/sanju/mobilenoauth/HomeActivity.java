package com.sanju.mobilenoauth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends AppCompatActivity {

    String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        phone = getIntent().getExtras().getString("phone");
    }

    public void dataEntry(View view) {
        Intent intent = new Intent(HomeActivity.this,DataEntryActivity.class);
        startActivity(intent);
    }

    public void dataValidation(View view) {
        Intent intent = new Intent(HomeActivity.this,DataValidationActivity.class);
        intent.putExtra("phone",phone);
        startActivity(intent);
    }
}