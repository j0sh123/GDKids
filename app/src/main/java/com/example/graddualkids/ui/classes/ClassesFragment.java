package com.example.graddualkids.ui.classes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.graddualkids.R;

public class ClassesFragment extends Fragment {

    private ClassesViewModel classesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        classesViewModel =
                ViewModelProviders.of(this).get(ClassesViewModel.class);

        View root = inflater.inflate(R.layout.fragment_t_classes, container, false);

       // final TextView textView = root.findViewById(R.id.text_gallery);

        classesViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
    }
}
