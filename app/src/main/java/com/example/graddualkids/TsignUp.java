package com.example.graddualkids;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class TsignUp extends AppCompatActivity {

    // TEACHER
    private EditText name, lastName, dni, birthDate, distric, address;
    private TextView txtOk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_t_sign_up);

        configView();
    }

    private void configView(){
        name = findViewById(R.id.edt_name);
        lastName = findViewById(R.id.edt_lastName);
        dni = findViewById(R.id.edt_dni);
        distric = findViewById(R.id.edt_distric);
        address = findViewById(R.id.edt_address);

        txtOk = findViewById(R.id.txt_Ok);
        txtOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainTeacher.class);
                startActivity(intent);
            }
        });
    }
}