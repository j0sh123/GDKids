package com.example.graddualkids;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.graddualkids.chat2.RegisterActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class BeforeSignUp extends AppCompatActivity {

    private ImageView imbStudent, imbTeacher;
    // modal view
    private TextView txtSchool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_before_sign_up);

        configView();
    }

    private void configView() {
        imbStudent = findViewById(R.id.imb_student);
        imbTeacher = findViewById(R.id.imb_teacher);


        imbTeacher.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), RegisterActivity.class);
                intent.putExtra("who","p");
                startActivity(intent);
            }
        });


        imbStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final EditText edtCodigo;
                final Button btnOk;
                //variables para detalles del cogido ingresado
                final TextView txtSchool, txtTeacher, txtError;

                boolean codValitation = true;

                // creando BottomSheetDialog (pantalla modal)
                BottomSheetDialog bsDialago = new BottomSheetDialog(BeforeSignUp.this);
                bsDialago.setContentView(R.layout._modal_view);
                bsDialago.setCanceledOnTouchOutside(false);

                edtCodigo = bsDialago.findViewById(R.id.edt_modalview_cod_aula);
                btnOk = bsDialago.findViewById(R.id.btn_ok);

                txtSchool = bsDialago.findViewById(R.id.txt_modalview_school);
                txtTeacher = bsDialago.findViewById(R.id.txt_modalview_teacher);
                txtError = bsDialago.findViewById(R.id.txt_modalview_error);

                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        txtError.setVisibility(View.GONE);
                        if (btnOk.getText().equals("OK")) {
                            if (edtCodigo.getText().toString().equalsIgnoreCase("1234")) {
                                txtSchool.setVisibility(View.VISIBLE);
                                txtTeacher.setVisibility(View.VISIBLE);
                                btnOk.setText("SIGUIENTE");
                            } else {
                                txtError.setVisibility(View.VISIBLE);
                            }


                        } else {
                            Intent intent = new Intent(v.getContext(), RegisterActivity.class);
                            intent.putExtra("who","s");
                            startActivity(intent);
                        }
                    }
                });
                // show modal view
                bsDialago.show();
            }
        });

    }
}
