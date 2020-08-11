package com.example.graddualkids.chat2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.graddualkids.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;

    private Context context;
    private List<chat> mChat;

    FirebaseUser firebaseUser;

    public MessageAdapter(Context context, List<chat> mChat) {
        this.context = context;
        this.mChat = mChat;
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MSG_TYPE_RIGHT) {
            View view = LayoutInflater.from(context).inflate(R.layout._c_chat_item_right, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }else{
            View view = LayoutInflater.from(context).inflate(R.layout._c_chat_item_left,parent,false);
            return new MessageAdapter.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {
        chat chat = mChat.get(position);
        holder.showMessage.setText(chat.getMessage());

        // marcar mensaje visto
        if (position == mChat.size()-1){
            if (chat.isSeen()) holder.txtSeen.setText("Visto");
            else holder.txtSeen.setText("Enviado");
        }
        else  holder.txtSeen.setVisibility(View.GONE);

    }

    @Override
    public int getItemCount() {
        return mChat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView showMessage;
        public TextView txtSeen;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            showMessage = itemView.findViewById(R.id.txt_chat_show_messege);
            txtSeen = itemView.findViewById(R.id.txt_chat_seen);

        }
    }

    @Override
    public int getItemViewType(int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (mChat.get(position).getSender().equals(firebaseUser.getUid())){
            return MSG_TYPE_RIGHT;
        }else
            return MSG_TYPE_LEFT;
    }
}
