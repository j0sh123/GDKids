package com.example.graddualkids.chat2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.graddualkids.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private Button btn;
    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;
    private EditText txt,txt2;
    private Button btnregister;

    // reset contraseña
    private TextView txtForgotPassword;

    /**  AUTO LOGIN colocarlo en el MainActivity o login creo*/
  /*  @Override
    protected void onStart() {
        super.onStart();
        // check if user is null
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null){
            Intent intent = new Intent(this, MainActivity_chat.class);
            startActivity(intent);
            finish();
        }
    }*/

    /** hecho sin datos dinamicos y sin verificacion si hay vacions*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._c_activity_login);

        auth = FirebaseAuth.getInstance();

        txt = findViewById(R.id.txt_chat_user_input);
        txt2 = findViewById(R.id.txt_chat_password_input);


        btn = findViewById(R.id.btn_login_chat);
        btnregister = findViewById(R.id.btn_register_chat);

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),RegisterActivity.class);
                startActivity(intent);
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_email = txt.getText().toString();
                String txt_password = txt2.getText().toString();

               auth.signInWithEmailAndPassword(txt_email,txt_password)
                           .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                           @Override
                           public void onComplete(@NonNull Task<AuthResult> task) {
                               if (task.isSuccessful()){
                                   Intent intent = new Intent(LoginActivity.this, AbrirFragment.class);
                                   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                   startActivity(intent);
                                   finish();
                               }
                               else
                                   Toast.makeText(LoginActivity.this, "authentication failed", Toast.LENGTH_SHORT).show();
                           }
                       });

            }
        });

        txtForgotPassword = findViewById(R.id.txt_forgot_password);
        txtForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),ResetPasword.class);
                startActivity(intent);
            }
        });

    }
}
