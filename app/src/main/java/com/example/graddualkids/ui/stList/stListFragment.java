package com.example.graddualkids.ui.stList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.graddualkids.R;
import com.example.graddualkids.chat.UserAdapter;
import com.example.graddualkids.chat2.UsersAdapter;
import com.example.graddualkids.chat2.user;
import com.example.graddualkids.model.StudentData;
import com.example.graddualkids.ui.stScoresDay.stScoresDayFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class stListFragment extends Fragment {

    /*****  MUESTRA LISTA E ALIMNOS DESDE FIREBASE**/
    private ListView listViewSudents;
    private stListAdapter adapter;
    private RecyclerView recyclerView;

    private UserAdapter usersAdapter;
    private List<user> mUsers;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //View root = inflater.inflate(R.layout.fragment_t_s_list_students,container,false);
        //listOfStudents(root);
        View root = inflater.inflate(R.layout._c_fragment_users,container,false);

        recyclerView = root.findViewById(R.id.recycle_view_chat_users);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mUsers = new ArrayList<>();
        readUsers();



        return root;
    }

    private void listOfStudents(View root){}

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
                usersAdapter = new UserAdapter(getContext(),mUsers,false);
                recyclerView.setAdapter(usersAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
