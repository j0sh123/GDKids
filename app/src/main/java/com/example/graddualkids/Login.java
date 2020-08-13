package com.example.graddualkids;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    private TextView txtClickabel;
    private Button btnLogin;

    private EditText edtUser;
    private EditText edtPassword;

    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;

    //reset contraseña
    private TextView txtForgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textClick();
        configViwe();
        login();
    }

    private void login() {
        edtUser = findViewById(R.id.edt_user);
        edtPassword = findViewById(R.id.edt_password);
        btnLogin = findViewById(R.id.btn_login);

        auth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_email = edtUser.getText().toString();
                String txt_password = edtPassword.getText().toString();

                auth.signInWithEmailAndPassword(txt_email,txt_password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    Intent intent = new Intent(Login.this, MainTeacher.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish();
                                }
                                else
                                    Toast.makeText(Login.this, "Error de autenticación", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        // contraseña olvidad
        txtForgotPassword = findViewById(R.id.txt_login_forgot_password);
        txtForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ResetPasword.class);
                startActivity(intent);
            }
        });

    }

    private void configViwe()
    {
        btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),MainTeacher.class);
            }
        });
    }

    // changed a simple text into a link text
    private void textClick(){

        txtClickabel = findViewById(R.id.txt_clickable);
        String text = txtClickabel.getText().toString();

        SpannableString ss = new SpannableString(text);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View v) {
                // changes it into another activity
                Intent intent = new Intent(v.getContext(), BeforeSignUp.class);
                startActivity(intent);
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.argb(246,95,7,1));
                ds.setUnderlineText(false);
            }
        };
        ss.setSpan(clickableSpan, 23, 36, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        txtClickabel.setText(ss);
        txtClickabel.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
