package com.example.graddualkids.chat2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.graddualkids.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AbrirFragment extends AppCompatActivity {

    FirebaseUser firebaseUser;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._c_abrir_fragment);

        /*****  COLOCAR ESTO Y ESTATUS, RESUME... EN UNA CLASE PARA, DETECTA CUANDO usuario ACTIVADO*****/
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
    }

    /*******/
    private void status(String stauts){
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("status",stauts);
        reference.updateChildren(hashMap);

    }

    @Override
    protected void onResume() {
        super.onResume();
        status("online");
    }

    @Override
    protected void onPause() {
        super.onPause();
        status("offline");
    }
    /**********/
}
