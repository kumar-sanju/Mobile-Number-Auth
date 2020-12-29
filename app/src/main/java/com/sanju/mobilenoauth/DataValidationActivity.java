package com.sanju.mobilenoauth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DataValidationActivity extends AppCompatActivity {

    EditText language,name,area,city,pgName,yourNo;
    Button submitBtn,noSubmit;
    LinearLayout linear;
    String phone,put_Id,put_pgname,put_city,put_area,put_name,put_language,put_phone;
    FirebaseFirestore db;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_validation);

        db = FirebaseFirestore.getInstance();
        pd = new ProgressDialog(this);

        phone = getIntent().getExtras().getString("phone");
        submitBtn = findViewById(R.id.submitBtn);
        yourNo = findViewById(R.id.yourNo);
        pgName = findViewById(R.id.pgName);
        city = findViewById(R.id.city);
        area = findViewById(R.id.area);
        name = findViewById(R.id.name);
        language = findViewById(R.id.language);
        noSubmit = findViewById(R.id.noSubmit);
        linear = findViewById(R.id.linear);

        linear.setVisibility(View.INVISIBLE);

        noSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(yourNo.length() < 10){
                    yourNo.setError("Please enter a valid phone...");
                    yourNo.requestFocus();
                    return;
                }if(yourNo.getText().toString().equals("")){
                    yourNo.setError("Field is required...");
                    yourNo.requestFocus();
                    return;
                }if(yourNo.getText().toString().equals(phone)){
                    yourNo.setError("Your no already exist.");
                    yourNo.requestFocus();
                    return;
                }else {
                    linear.setVisibility(View.VISIBLE);
                    insertData();
                }
            }
        });

        final Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            put_Id = bundle.getString("put_Id");
            put_pgname = bundle.getString("put_pgname");
            put_city = bundle.getString("put_city");
            put_area = bundle.getString("put_area");
            put_name = bundle.getString("put_name");
            put_language = bundle.getString("put_language");
            put_phone = bundle.getString("put_phone");

            pgName.setText(put_pgname);
            city.setText(put_city);
            area.setText(put_area);
            name.setText(put_name);
            language.setText(put_language);
            yourNo.setText(put_phone);
        }

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData();
            }
        });

    }

    private void insertData() {
        if(pgName.getText().toString().equals("")){
            yourNo.setError("Field is required...");
            yourNo.requestFocus();
            return;
        }if(city.getText().toString().equals("")){
            yourNo.setError("Field is required...");
            yourNo.requestFocus();
            return;
        }if(area.getText().toString().equals("")){
            yourNo.setError("Field is required...");
            yourNo.requestFocus();
            return;
        }
        final Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            String ID  = put_Id;
            String PGNAME = pgName.getText().toString().trim();
            String CITY = city.getText().toString().trim();
            String AREA = area.getText().toString().trim();
            String NAME = name.getText().toString().trim();
            String LANGUAGE = language.getText().toString().trim();
            String PHONE = yourNo.getText().toString().trim();
            updateData(ID, PGNAME, CITY, AREA, NAME, LANGUAGE, PHONE);
        }else {
            String PGNAME = pgName.getText().toString().trim();
            String CITY = city.getText().toString().trim();
            String AREA = area.getText().toString().trim();
            String NAME = name.getText().toString().trim();
            String LANGUAGE = language.getText().toString().trim();
            String PHONE = yourNo.getText().toString().trim();
            uploadData(PGNAME, CITY, AREA, NAME, LANGUAGE, PHONE);
        }
    }

    private void uploadData(String pgname, String city, String area, String name, String language, String phone) {
        pd.setTitle("Please wait...");
        pd.show();

        String id = UUID.randomUUID().toString();

        Map<String, Object> doc = new HashMap<>();
        doc.put("id",id);
        doc.put("pgname",pgname);
        doc.put("city",city);
        doc.put("area",area);
        doc.put("name",name);
        doc.put("language",language);
        doc.put("phone",phone);

        db.collection("Data").document(id).set(doc)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        pd.dismiss();
                        Toast.makeText(DataValidationActivity.this,"Uploaded...", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(DataValidationActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void updateData(String id, String pgname, String city, String area, String name, String language, String phone) {
        pd.setTitle("Uploading Data...");
        pd.show();

        db.collection("Data").document(id)
                .update("pgname",pgname,"city",city, "area,",area,"name",name,"language",language,"phone",phone)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        pd.dismiss();
                        Toast.makeText(DataValidationActivity.this,"Uploaded...", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(DataValidationActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

}