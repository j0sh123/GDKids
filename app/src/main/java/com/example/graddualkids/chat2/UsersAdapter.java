package com.example.graddualkids.chat2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.graddualkids.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {

    private Context context;
    private List<user> mUsers;
    private boolean isOnline;

    private String theLastMessage;

    public UsersAdapter(Context context, List<user> mUsers, boolean isOnline) {
        this.context = context;
        this.mUsers = mUsers;
        this.isOnline = isOnline;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout._c_users_item,parent,false);
        return new UsersAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final user user  = mUsers.get(position);
        holder.txtUserName.setText(user.getusername());

        if (user.getimageURL().equals("default")){
            holder.imageView.setImageResource(R.mipmap.ic_gd_logo_round);
        }
        else {
            //se necesita agregar libreria Glide en grader(ESTA comentaDO)
            //   Glide.with(MainActivity_chat.class).load(user.getImgProfile()).into(imageView);
        }

        //show last message
        if (isOnline) lastMessage(user.getid(),holder.txtLastMessage);
            else holder.txtLastMessage.setVisibility(View.GONE);

        //status
        if (isOnline){
            if (user.getStatus().equals("online")){
                holder.imgStatus.setVisibility(View.VISIBLE);
            }
        }else holder.imgStatus.setVisibility(View.GONE);

        // para seleccionar un conjunto
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,MessageActivity.class);
                intent.putExtra("userId",user.getid());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView txtUserName;
        private ImageView imageView;
        private ImageView imgStatus;
        private TextView txtLastMessage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtUserName = itemView.findViewById(R.id.txt_chat_username_usertItem);
            txtLastMessage = itemView.findViewById(R.id.txt_chat_last_message);
            imageView = itemView.findViewById(R.id.img_chat_profile_image);
            imgStatus = itemView.findViewById(R.id.img_chat_status);
        }
    }

    private void lastMessage (final String userId, final TextView lastMessage){
        theLastMessage =   "default";
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    chat chats = snapshot.getValue(chat.class);
                    if (chats.getReceiver().equals(firebaseUser.getUid()) && chats.getSender().equals(userId) ||
                            chats.getReceiver().equals(userId) && chats.getSender().equals(firebaseUser.getUid())){
                        theLastMessage = chats.getMessage();
                    }
                }

                if (theLastMessage.equals("default")) lastMessage.setText("");
                else lastMessage.setText(theLastMessage);

                theLastMessage = "default";
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
