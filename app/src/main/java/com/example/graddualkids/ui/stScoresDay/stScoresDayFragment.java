package com.example.graddualkids.ui.stScoresDay;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.graddualkids.R;
import com.example.graddualkids.model.StudentData;

import java.util.ArrayList;

public class stScoresDayFragment extends Fragment {

    private ListView listViewStudents;
  //  private ArrayList<StudentData> studentData = new ArrayList<>();
    private stScoresDayAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_t_s_scores_day, container, false);

        studentsScoresDay(root);

        return root;
    }

    private void studentsScoresDay(View root) {
        /**     cambiar a firebase para datos*/
        listViewStudents = root.findViewById(R.id.listview_scoresOfStudents_item);

        ArrayList<StudentData> studentData = students();

        adapter = new stScoresDayAdapter(getContext(), studentData);
        listViewStudents.setAdapter(adapter);

    }

    // para poder editar los datos originales y modificar el ckeckBox se hara un update desde la bd y se actualizara todo
    public static ArrayList<StudentData> students() {
        ArrayList<StudentData> studentData = new ArrayList<>();
        studentData.add (new StudentData("001","jose","grados yangali","",2,0));
        studentData.add (new StudentData("002","Miguel","grados yangali","",1,1));
        studentData.add (new StudentData("003","Yaning","grados yangali","",0,0));
        studentData.add (new StudentData("004","Keylla","grados yangali","",1,0));
        studentData.add (new StudentData("005","jose","grados yangali","",1,0));
        studentData.add (new StudentData("006","Miguel","grados yangali","",0,0));
        studentData.add (new StudentData("007","Yaning","grados yangali","",1,1));
        studentData.add (new StudentData("008","Keylla","grados yangali","",1,0));
        return studentData;
    }
}
