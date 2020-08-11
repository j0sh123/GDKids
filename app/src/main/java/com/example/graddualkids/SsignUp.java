package com.example.graddualkids;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.nio.file.Files;

public class SsignUp extends AppCompatActivity {

    // STUDENT
    private EditText stName, stLastName,stDni, stBirthDate, stDistrict, stAddress;
    private EditText apodName, apodLastName, apodPhone, apodEmil;
    private TextView txtOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_sign_up);

        configView();
    }

    private void configView(){
        stName = findViewById(R.id.edt_name);
        stLastName = findViewById(R.id.edt_lastName);
        stBirthDate = findViewById(R.id.edt_birth_date);
        stDistrict = findViewById(R.id.edt_distric);
        stAddress = findViewById(R.id.edt_address);

        apodName = findViewById(R.id.edt_apoderado_name);
        apodLastName = findViewById(R.id.edt_apoderado_lastName);
        apodPhone = findViewById(R.id.edt_apoderado_phone);
        apodEmil = findViewById(R.id.edt_apoderado_email);

        SharedPreferences preferences = getSharedPreferences("StudentData", Context.MODE_PRIVATE);
        stName.setText(preferences.getString("stName",""));

        txtOk = findViewById(R.id.txt_Ok);
        txtOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Guardar(v);
                Intent intent = new Intent(v.getContext(), MainStudent.class);
                startActivity(intent);
            }
        });

    }
    // metodo guardar sharedpreferences (guardan informacion) tipo memoria ram
    public void Guardar(View v){
        SharedPreferences preferences = getSharedPreferences("StudentData",Context.MODE_PRIVATE);
        // Editor para guardar archivos
        SharedPreferences.Editor editor = preferences.edit();
        // guardar
        editor.putString("stName", stName.getText().toString());
        // para confirmar guardado
        editor.commit();


    }

//    // para campos vacios
//    public void validacion(){
//        String name = stName.getText().toString();
//        if (name.equals("")){
//            stName.setError("Required");
//        }
//    }
}
