package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.databinding.ActivityMainBinding;
import com.example.myapplication.databinding.ActivityQuestionnaireBinding;
import com.example.myapplication.databinding.HomeViewBinding;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class ActivityQuestionnaire extends Fragment {

    private ActivityQuestionnaireBinding binding;
    private CheckBox CBDomesticWork, CBSelfCare, CBParty, CBHobby;
    private Button Finish;
    private ArrayList<String> CBResult;
    private SeekBar SBSport, SBUniversity;
    private TextView SportProgress;
    private TextView UniversityProgress;
    public static Integer m_universityIntensity = 0;
    private NavController homeNavi;
    public static ActivityQuestionnaire activityQuestionnaire = new ActivityQuestionnaire();


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        //bindings
        binding = ActivityQuestionnaireBinding.inflate(inflater, container, false);

        CBDomesticWork = binding.checkboxDomesticWork;
        CBSelfCare = binding.checkboxSelfCare;
        CBParty= binding.checkboxParty;
        CBHobby = binding.checkboxHobbies;
        Finish = binding.buttonFinish;
        CBResult = new ArrayList<>();
        SBSport = binding.seekBar5;
        SBUniversity = binding.seekBar2;
        UniversityProgress = binding.textViewIntensityUniversity;

        // set initial university intensity
        if(Objects.requireNonNull(MainActivity.getEmotionData().get(MainActivity.m_today)).containsKey(Globals.universityIntensity)){
            m_universityIntensity=Integer.parseInt(Objects.requireNonNull((Objects.requireNonNull(MainActivity.getEmotionData().get(MainActivity.m_today))).get(Globals.universityIntensity)));
            SBUniversity.setProgress(m_universityIntensity);
            if(m_universityIntensity>0) {
                UniversityProgress.setText("   " + m_universityIntensity * 10 + "%");
            }
        }

        //on Click Listeners
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

        SBSport.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                // TODO Auto-generated method stub
                SportProgress = binding.textViewIntensitySport;
                SportProgress.setText("   " + SBSport.getProgress()*10 + "%");
                seekBar.setMax(10);
            }
        });

        SBUniversity.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                // TODO Auto-generated method stub
                m_universityIntensity = SBUniversity.getProgress();
                UniversityProgress.setText("   " + m_universityIntensity*10 + "%");
                seekBar.setMax(10);
                MainActivity.m_todaysData.put(Globals.universityIntensity, String.valueOf(m_universityIntensity));
                try {
                    MainActivity.saveData();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        // go back to homeview if finish button is clicked
        homeNavi = NavHostFragment.findNavController(ActivityQuestionnaire.this);
        activityQuestionnaire = ActivityQuestionnaire.this;
        Finish.setOnClickListener(View -> homeNavi.navigate(R.id.action_ActivityFragment_to_HomeFragment));

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
