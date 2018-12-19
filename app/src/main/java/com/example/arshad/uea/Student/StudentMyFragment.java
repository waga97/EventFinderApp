package com.example.arshad.uea.Student;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.arshad.uea.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class StudentMyFragment extends Fragment {


    public StudentMyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student_my, container, false);
    }

}
