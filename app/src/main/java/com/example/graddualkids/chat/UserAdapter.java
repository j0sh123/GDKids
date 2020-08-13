package com.example.graddualkids.chat;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.graddualkids.R;
import com.example.graddualkids.SsignUp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private Context context;
    private List<user> mUsers;

    public UserAdapter(Context context, List<user> mUsers) {
        this.context = context;
        this.mUsers = mUsers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout._t_chat_item_students,parent,false);
        return new UserAdapter.ViewHolder(view);
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

        holder.imbSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MessageActivity.class);
                intent.putExtra("userId",user.getid());
                context.startActivity(intent);
            }
        });

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,SsignUp.class);
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
        private ImageButton imbSendMessage;
        private LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtUserName = itemView.findViewById(R.id.txt_username_usertItem);
            imageView = itemView.findViewById(R.id.img_listStudent);
            imbSendMessage = itemView.findViewById(R.id.imb_send_message);
            linearLayout = itemView.findViewById(R.id.linearLayout_listStudent);
        }
    }
}
