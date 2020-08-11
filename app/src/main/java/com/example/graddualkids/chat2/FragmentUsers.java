package com.example.graddualkids.chat2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class FragmentUsers extends Fragment {

    /****       ASI MOSTRARIA ALUMNOS QUE SE HAN REGISTRADO - ENLASADO CON FIREBASE     **/
    private RecyclerView recyclerView;

    private UsersAdapter usersAdapter;
    private List<user>  mUsers;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout._c_fragment_users,container,false);

        recyclerView = view.findViewById(R.id.recycle_view_chat_users);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mUsers = new ArrayList<>();
        readUsers();



        return view;
    }

    private void readUsers() {
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUsers.clear();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    user user = snapshot.getValue(com.example.graddualkids.chat2.user.class);

                    assert user !=null;
                    assert firebaseUser !=null;
                    if (!user.getid().equals(firebaseUser.getUid())){
                        mUsers.add(user);
                    }
                }
                usersAdapter = new UsersAdapter(getContext(),mUsers,false);
                recyclerView.setAdapter(usersAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
