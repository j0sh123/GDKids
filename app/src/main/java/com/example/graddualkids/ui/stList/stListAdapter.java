package com.example.graddualkids.ui.stList;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.graddualkids.R;
import com.example.graddualkids.SsignUp;
import com.example.graddualkids.model.StudentData;

import java.util.ArrayList;

public class stListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<StudentData> studentDataArrayList;
    private static LayoutInflater layoutInflater = null;

    public stListAdapter (Context context, ArrayList<StudentData> studentDataArrayList){
        this.context = context;
        this.studentDataArrayList = studentDataArrayList;
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return studentDataArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return studentDataArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout._t_s_list_item_list_students,parent,false);

        TextView txtId = convertView.findViewById(R.id.txt_item_id_student);
        final TextView txtName = convertView.findViewById(R.id.txt_item_name_student);
        TextView txtLastName = convertView.findViewById(R.id.txt_item_last_name_student);


        final ImageButton imbMessage = convertView.findViewById(R.id.imb_event_1); // btn para mensaje de texto
        final ImageButton imb = convertView.findViewById(R.id.imb_event_2); // btn no se usara
        imbMessage.setImageResource(R.drawable.ic_menu_messages);
        imb.setVisibility(View.GONE);

        LinearLayout linearLayout = convertView.findViewById(R.id.linearLayout_listStudent);

        //obtener datos
        final StudentData selectedStudent = studentDataArrayList.get(position);

        txtId.setText(selectedStudent.getIdStudent());
        txtName.setText(selectedStudent.getName());
        txtLastName.setText(selectedStudent.getLastName());

        imbMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // para pasar el id del alumno al chat
                /*Intent intent = new Intent(context, MessageActivity.class);
                intent.putExtra("userId",selectedStudent.getIdStudent());
                intent.putExtra("userName",selectedStudent.getName());
                context.startActivity(intent);*/
                Toast.makeText(v.getContext(),"mensaje a "+ selectedStudent.getName(),Toast.LENGTH_SHORT).show();
            }
        });

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),SsignUp.class);
                v.getContext().startActivity(intent);
            }
        });

        return convertView;
    }
}
