package com.example.graddualkids.ui.stScoresDay;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.graddualkids.R;
import com.example.graddualkids.ThomeworkReview;
import com.example.graddualkids.model.StudentData;

import java.util.ArrayList;

public class stScoresDayAdapter extends BaseAdapter {

    Context context;
    ArrayList<StudentData> studentDataList;
    private static LayoutInflater layoutInflater = null;

    public stScoresDayAdapter(Context context, ArrayList<StudentData> studentDataList){
        this.context = context;
        this.studentDataList = studentDataList;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return studentDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return studentDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent){
        if (convertView == null)
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout._t_s_list_item_list_students,parent,false);

        LinearLayout code = convertView.findViewById(R.id.linearLayout_code);
        code.setVisibility(View.GONE);
        //TextView txtId = convertView.findViewById(R.id.txt_item_id_student);
        TextView txtName = convertView.findViewById(R.id.txt_item_name_student);
        TextView txtLastName = convertView.findViewById(R.id.txt_item_last_name_student);

        final ImageButton imbHomework = convertView.findViewById(R.id.imb_event_1); // btn para enviar a  tarea de estudiantes
        final ImageButton imbScores = convertView.findViewById(R.id.imb_event_2); // btn para evniar a notas de estudiantes

        LinearLayout linearLayout = convertView.findViewById(R.id.linearLayout_listStudent);

        //obtener datos
        final StudentData selectedStudent = studentDataList.get(position);

      //  txtId.setText(selectedStudent.getIdStudent());
        txtName.setText(selectedStudent.getName());
        txtLastName.setText(selectedStudent.getLastName());

        /** revision de tarea**/
        checkedColor(selectedStudent, imbHomework);
        /*******/

        // imgs array con R.draweble.img1...
        //ImageView imgProfile = convertView.findViewById(R.id.imv_listStudent);
        //imgProfile.setImageResource(imgs[position]);


        imbHomework.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"tareas " + selectedStudent.getIdStudent(),Toast.LENGTH_SHORT).show();
            }
        });

        imbScores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"notas" + selectedStudent.getHomeworkSent(),Toast.LENGTH_SHORT).show();
            }
        });

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ThomeworkReview.class);
                intent.putExtra("name",selectedStudent.getName() +" "+ selectedStudent.getLastName());
                intent.putExtra("cod",selectedStudent.getIdStudent());
                intent.putExtra("number2",selectedStudent.getHomeworkSent());
                v.getContext().startActivity(intent);
               // Toast.makeText(v.getContext(),"alumno : "+ selectedStudent.getName(),Toast.LENGTH_SHORT).show();
            }
        });



        return convertView;
    }

    /** envio y recibo de tareas (checks de colores), rojo= no envio, plomo=envio, verde=tarea revisada**/
    private void checkedColor(StudentData selectedStudent, ImageButton imbHomework) {
        if (selectedStudent.getHomeworkChecked()==1){
            imbHomework.setColorFilter(Color.GREEN);
        }else {
            if (selectedStudent.getHomeworkSent() == 1) {
                imbHomework.setColorFilter(Color.GRAY);
            } else if (selectedStudent.getHomeworkSent() == 2) {
                imbHomework.setColorFilter(Color.GRAY);
            }else {
                imbHomework.setImageResource(R.drawable.ic_list_remove);
                //imbHomework.setBackgroundResource(R.drawable.ic_list_remove);
                imbHomework.setColorFilter(Color.RED);
            }
        }
    }
}
