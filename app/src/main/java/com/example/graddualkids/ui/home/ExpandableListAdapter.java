package com.example.graddualkids.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import  androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.graddualkids.R;

import java.util.HashMap;
import java.util.List;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private HashMap<String, DataDaysWeeks> expandableListDetalles;
    private List<String> listTitulo;
    private Context context;

    public ExpandableListAdapter(Context context, List<String> listTitulo, HashMap<String, DataDaysWeeks> expandableListDetalles) {
       this.context = context;
       this.listTitulo = listTitulo;
       this.expandableListDetalles = expandableListDetalles;
    }

    @Override
    public int getGroupCount() {
        return this.listTitulo.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.listTitulo.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.expandableListDetalles.get(this.listTitulo.get(groupPosition));
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition,  boolean isExpanded, View convertView, ViewGroup parent) {

        String numberWeek = (String) getGroup(groupPosition);
        DataDaysWeeks details = (DataDaysWeeks) getChild(groupPosition,0);

        if (convertView == null)
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout._expandable_home_list_group,parent,false);


        TextView txtNumberWeek = convertView.findViewById(R.id.txt_number_week2);


        txtNumberWeek.setText(details.getNumberWeek());
     //   textView.setText(String.valueOf(getGroup(groupPosition)));

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        DataDaysWeeks details = (DataDaysWeeks) getChild(groupPosition,childPosition);

        if (convertView == null)
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout._expandable_home_list_items,parent,false);

        TextView txtNumberDay = convertView.findViewById(R.id.txt_number_day);
        TextView txtTitleDay = convertView.findViewById(R.id.txt_day_title);
        ImageButton imgBAdd = convertView.findViewById(R.id.imgBtn_items_edit);
        ImageButton imgBRemove = convertView.findViewById(R.id.imgBtn_items_remove);

        txtNumberDay.setText(details.getNumberDay());
        txtTitleDay.setText(details.getTitleDays());

        imgBAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"uuuuuuuuuuuuu",Toast.LENGTH_LONG).show();
            }
        });

        imgBRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"oiiiiiiiiiiiiiiiiio",Toast.LENGTH_LONG).show();
            }
        });


        txtTitleDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
        convertView.startAnimation(animation);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
