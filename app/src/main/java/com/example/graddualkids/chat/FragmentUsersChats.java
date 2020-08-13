package com.example.graddualkids.chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.graddualkids.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FragmentUsersChats extends Fragment {

    /*******    MOSTARIA LOS CHATS EN MENSAJERIA DEL PROFE Y DEL ALUMNO*******/
    private RecyclerView recyclerView;

    private UsersAdapterChat usersAdapter;
    private List<user>  mUsers;

    private FirebaseUser firebaseUser;
    private DatabaseReference reference;

    private List<ChatList> usersList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout._c_fragment_users_chats,container,false);

        recyclerView = view.findViewById(R.id.recycle_view_chat_users_chats);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        usersList = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("ChatList").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usersList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    ChatList chatList = snapshot.getValue(ChatList.class);
                    usersList.add(chatList);
                }
                chatList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        /*reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usersList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    chat chat = snapshot.getValue(com.example.graddualkids.chat.chat.class);

                    if (chat.getSender().equals(firebaseUser.getUid()))
                        usersList.add(chat.getReceiver());

                    if (chat.getReceiver().equals(firebaseUser.getUid()))
                        usersList.add(chat.getSender());

                }

                readChats();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/
        return view;
    }

    private void chatList() {
        mUsers = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUsers.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    user users = snapshot.getValue(user.class);
                    for (ChatList chatList : usersList){
                        if (users.getid().equals(chatList.getId())){
                            mUsers.add(users);
                        }
                    }
                }
                usersAdapter = new UsersAdapterChat(getContext(),mUsers,true);
                recyclerView.setAdapter(usersAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void readChats() {
        mUsers = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUsers.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    user users = snapshot.getValue(user.class);

                    // display users from chat
                    for (ChatList id: usersList){
                        if (users.getid().equals(id)){
                            if (mUsers.size() != 0){
                                for (user userl : mUsers){
                                    if (!users.getid().equals(userl.getid())){
                                        mUsers.add(users);
                                    }
                                }
                            } else {
                                mUsers.add(users);
                            }
                        }
                    }
                }
                usersAdapter = new UsersAdapterChat(getContext(),mUsers,true);
                recyclerView.setAdapter(usersAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
