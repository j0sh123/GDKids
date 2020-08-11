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
import com.example.graddualkids.chat2.ChatList;
import com.example.graddualkids.chat2.UsersAdapter;
import com.example.graddualkids.chat2.chat;
import com.example.graddualkids.chat2.user;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class chatFragment extends Fragment {

    /** PROCURAR QUITAR TODO DE CHAT2*/

    /****   NO FUNCIONA PARA MOSTRAR LOS CHATS CUANDO TE ESCRIBEN****/
    private RecyclerView recyclerView;

    private UsersAdapter usersAdapter;
    private List<user> mUsers;

    private FirebaseUser firebaseUser;
    private DatabaseReference reference;

    private List<ChatList> usersList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_t_chats, container, false);

        recyclerView = root.findViewById(R.id.recycle_view_users_chats);
        recyclerView.setHasFixedSize(true);
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


        return root;
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
                usersAdapter = new UsersAdapter(getContext(),mUsers,true);
                recyclerView.setAdapter(usersAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
