package com.example.graddualkids.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.graddualkids.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeFragment extends Fragment {


    private HomeViewModel homeViewModel;

    private ExpandableListView expandableListView;
    private ExpandableListAdapter expandableListAdapter;
    private List<String> expandableListDetails;
    private HashMap<String, DataDaysWeeks> listDaysWeeks;
    private int lastExpandablePosition = -1;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);

        View root = inflater.inflate(R.layout.fragment_t_home, container, false);

        // lista expandible
        init(root);

        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandablePosition != -1 && groupPosition!= lastExpandablePosition){
                    expandableListView.collapseGroup(lastExpandablePosition);
                }
                lastExpandablePosition = groupPosition;
            }
        });

        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
//                numberDay = Integer.parseInt(txtNumDay.getText().toString());
//                numberWeek = Integer.parseInt(txtNumWeek.getText().toString());

              //  textView.setText(s);
            }
        });
        return root;
    }

    private void init(View v){
        this.expandableListView = v.findViewById(R.id.expandable_listView);
        this.listDaysWeeks = getDetails();
        this.expandableListDetails = new ArrayList<>(listDaysWeeks.keySet());
        this.expandableListAdapter = new ExpandableListAdapter(getContext(), expandableListDetails, listDaysWeeks);
    }



    private HashMap<String, DataDaysWeeks> getDetails() {
        HashMap<String, DataDaysWeeks> list = new HashMap<>();

        list.put("0", new DataDaysWeeks("Semana 01","Day 01","Lavar las Manos"));
        list.put("1", new DataDaysWeeks("Semana 02","Day 03","Lavar cabello"));
        list.put("2", new DataDaysWeeks("Semana 03","Day 01","inglese"));

        return list;
    }
}

