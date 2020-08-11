package com.example.graddualkids.chat2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.graddualkids.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class MainActivity_chat extends AppCompatActivity {

    ImageView imageView;
    TextView txtUser;

    FirebaseUser firebaseUser;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._c_activity_main_chat);

        imageView = findViewById(R.id.imv_chat_student);
        txtUser = findViewById(R.id.txt_chat_user_name);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user user = dataSnapshot.getValue(user.class);
                txtUser.setText(user.getusername());
                if (user.getimageURL().equals("default")){
                    imageView.setImageResource(R.mipmap.ic_launcher_round);
                }
                else {
                    //se necesita agregar libreria Glide en grader(ESTA comentaDO)
                 //   Glide.with(getApplicationContenxt).load(user.getImgProfile()).into(imageView);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        /**agregar
         * FirebaseAuth.getInstance().signOut();
         * startActivity(new Intent(this,activity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
         * cuando el usuario cierre secion*/
    }

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
}
