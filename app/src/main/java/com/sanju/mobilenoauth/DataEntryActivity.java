package com.sanju.mobilenoauth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class DataEntryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_entry);
    }

    public void dataValidation(View view) {
        Intent intent = new Intent(DataEntryActivity.this,DataValidationActivity.class);
        startActivity(intent);
    }
}