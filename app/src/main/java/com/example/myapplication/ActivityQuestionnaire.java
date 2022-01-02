package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myapplication.databinding.ActivityQuestionnaireBinding;

import java.util.ArrayList;

public class ActivityQuestionnaire extends Fragment {

    private ActivityQuestionnaireBinding binding;
    private CheckBox CBDomesticWork, CBSelfCare, CBParty, CBHobby;
    private Button Finish;
    private ArrayList<String> CBResult;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = ActivityQuestionnaireBinding.inflate(inflater, container, false);


        CBDomesticWork = binding.checkboxDomesticWork;
        CBSelfCare = binding.checkboxSelfCare;
        CBParty= binding.checkboxParty;
        CBHobby = binding.checkboxHobbies;
        Finish = binding.ButtonFinish;
        CBResult = new ArrayList<>();

        CBDomesticWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CBDomesticWork.isChecked())
                    CBResult.add("DomesticWork");
                else
                    CBResult.remove("DomesticWork");
            }
        });
        CBSelfCare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CBSelfCare.isChecked())
                    CBResult.add("SelfCare");
                else
                    CBResult.remove("SelfCare");
            }
        });
        CBParty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CBParty.isChecked())
                    CBResult.add("Party");
                else
                    CBResult.remove("Party");
            }
        });
        CBHobby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CBHobby.isChecked())
                    CBResult.add("Hobby");
                else
                    CBResult.remove("Hobby");
            }
        });
        Finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // GO BACK TO MAIN
            }
        });

        return binding.getRoot();
    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
