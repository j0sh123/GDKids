package com.example.graddualkids.chat2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.graddualkids.Login;
import com.example.graddualkids.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasword extends AppCompatActivity {

    private EditText edtEmail;
    private Button btnSendEmail;

    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._c_activity_reset_pasword);

        edtEmail = findViewById(R.id.edt_email);
        btnSendEmail = findViewById(R.id.btn_send_email);

        firebaseAuth = FirebaseAuth.getInstance();

        btnSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString();
                if (email.equals("")) {
                    Toast.makeText(ResetPasword.this, "Ingresar Email", Toast.LENGTH_SHORT).show();
                }else {
                    firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(ResetPasword.this,"Email enviado, Revise su correo", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ResetPasword.this, Login.class)); //loginActi
                            } else{
                                String error = task.getException().getMessage();
                                Toast.makeText(ResetPasword.this, error, Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
            }
        });
    }
}
