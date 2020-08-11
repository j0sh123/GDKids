package com.example.graddualkids.chat2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.graddualkids.MainTeacher;
import com.example.graddualkids.R;
import com.example.graddualkids.SsignUp;
import com.example.graddualkids.TsignUp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    FirebaseAuth auth;
    DatabaseReference reference;

    private Button button;

    private FirebaseUser firebaseUser;
    private EditText text1,text2,name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._c_activity_register);

        button = findViewById(R.id.btn_chat_register);
        auth = FirebaseAuth.getInstance();


        name = findViewById(R.id.txt_chat_register_name);
        text1 = findViewById(R.id.txt_chat_register_user_input);
        text2 = findViewById(R.id.txt_chat_register_password_input);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // register("andres","andres@gmail.com","1234567"); //contraseñas tienen que ser al menos 6 charts
                register(name.getText().toString(),text1.getText().toString(),text2.getText().toString());
            }
        });

    }

    private void register(final String user, String email, String password){
        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            assert firebaseUser != null;
                            String userId = firebaseUser.getUid();

                            reference = FirebaseDatabase.getInstance().getReference("Users").child(userId);

                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("id",userId);
                            hashMap.put("username", user);
                            hashMap.put("imageURL","default");
                            hashMap.put("status","offline");

                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Bundle datos = getIntent().getExtras();
                                        if (datos.getString("who").equals("p")){
                                        Intent intent = new Intent(RegisterActivity.this, TsignUp.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                        } else {
                                            Intent intent = new Intent(RegisterActivity.this, SsignUp.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }
                                }
                            });
                        }
                        else{
                            Toast.makeText(RegisterActivity.this, "can't register wihh---", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
}
