package com.example.graddualkids.chat2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessageActivity extends AppCompatActivity {

    private ImageButton imbSend;
    private EditText edtMessage;

    private TextView txtUserName;
    private ImageView img;

    private FirebaseUser firebaseUser;
    private DatabaseReference reference;

    private Intent intent;

    /**** para el envio y recibo de chats*/
    private List<chat> mChat;
    private RecyclerView recyclerView;
    private MessageAdapter messageAdapter;
    private String userId;

    /*****  marcar como visto********/
    private ValueEventListener seenListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._c_activity_message);

        /**** para el envio y recibo de chats*/
        recyclerView = findViewById(R.id.recycle_view_chat);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        /*******/

        /****   MUESTRA IMAGEN Y NOMBRE DEL RECEPTOR*/
        img = findViewById(R.id.img_chat_user);
        txtUserName = findViewById(R.id.txt_chat_username_activity);

        intent = getIntent();
         userId = intent.getStringExtra("userId"); //cambiado aqui, le quite el final

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(userId);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user user = dataSnapshot.getValue(user.class);
                txtUserName.setText(user.getusername());
                if (user.getimageURL().equals("default")){
                    img.setImageResource(R.mipmap.ic_launcher_round);
                }
                else {

                    //se necesita agregar libreria Glide en grader(ESTA comentaDO)
                    //   Glide.with(getApliasContenxt).load(user.getImgProfile()).into(imageView);
                }
                readMessages(firebaseUser.getUid(),userId);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        /********/

        /***    ENVIAR MENSAJE */
        imbSend = findViewById(R.id.imb_chat_send);
        edtMessage = findViewById(R.id.edt_chat_send_messege);

       // reference = FirebaseDatabase.getInstance().getReference("Users").child(userId);
        imbSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = edtMessage.getText().toString();
                if (!message.equals("")){
                    sendMessages(firebaseUser.getUid(),userId,message);
                    edtMessage.setText("");
                }
            }
        });
        /*****/

        seenMessage(userId);
    }

    private void seenMessage(final String userId){
        reference = FirebaseDatabase.getInstance().getReference("Chats");
        seenListener = reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    chat chats = snapshot.getValue(chat.class);
                    if (chats.getReceiver().equals(firebaseUser.getUid()) &&
                            chats.getSender().equals(userId)){
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("seen", true);
                        snapshot.getRef().updateChildren(hashMap);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void sendMessages(String sender, String receiver, String message){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender",sender);
        hashMap.put("receiver",receiver);
        hashMap.put("message",message);
        hashMap.put("seen",false);

        reference.child("Chats").push().setValue(hashMap);

        // add user to chat fragment ?
        final DatabaseReference chatRef = FirebaseDatabase.getInstance().getReference("ChatList")
                .child(firebaseUser.getUid())
                .child(userId);

        chatRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists())
                    chatRef.child("id").setValue(userId);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void readMessages(final String myId, final String userId){
        mChat = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mChat.clear();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    chat chat = snapshot.getValue(com.example.graddualkids.chat2.chat.class);
                    if (chat.getReceiver().equals(myId) && chat.getSender().equals(userId) ||
                            chat.getReceiver().equals(userId) && chat.getSender().equals(myId)){
                        mChat.add(chat);
                    }
                    messageAdapter = new MessageAdapter(MessageActivity.this,mChat);
                    recyclerView.setAdapter(messageAdapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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
        reference.removeEventListener(seenListener);
        status("offline");
    }
    /**********/
}
