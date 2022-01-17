package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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
    private CheckBox CBFamily, CBFriends, CBPartner, CBChildren, CBRoommates;
    private Button Finish;
    private ArrayList<String> CBResult, CBSocial;
    private SeekBar SBSport, SBUniversity;
    private EditText HourUni, MinUni, HourSport, MinSport;
    private TextView SportProgress;
    private TextView UniversityProgress;
    public static Integer m_universityIntensity = 0;
    public static Integer m_uniDurHour = 0;
    public static Integer m_uniDurMin = 0;
    public static Integer m_sportDurHour = 0;
    public static Integer m_sportDurMin = 0;
    public static Integer m_sportIntensity = 0;
    public static String  m_DataOther, m_DataSocial;

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
        CBFriends = binding.checkboxFriends;
        CBFamily = binding.checkboxFamily;
        CBPartner = binding.checkboxPartner;
        CBChildren = binding.checkboxChildren;
        CBRoommates = binding.checkboxRoommates;
        CBSocial = new ArrayList<>();
        Finish = binding.buttonFinish;
        CBResult = new ArrayList<>();
        SBSport = binding.seekBar5;
        SBUniversity = binding.seekBar2;
        UniversityProgress = binding.textViewIntensityUniversity;
        SportProgress = binding.textViewIntensitySport;
        HourUni = (EditText) binding.EditTextHours1;
        MinUni = (EditText) binding.EditTextMinutes1;
        HourSport = (EditText) binding.EditTextHours2;
        MinSport = (EditText) binding.EditTextMinutes2;


// set initial university intensity
        if(Objects.requireNonNull(MainActivity.getEmotionData().get(MainActivity.m_today)).containsKey(Globals.universityIntensity)){
            m_universityIntensity=Integer.parseInt(Objects.requireNonNull((Objects.requireNonNull(MainActivity.getEmotionData().get(MainActivity.m_today))).get(Globals.universityIntensity)));
            SBUniversity.setProgress(m_universityIntensity);
            if(m_universityIntensity>0) {
                UniversityProgress.setText("   " + m_universityIntensity * 10 + "%");
            }
        }

    // set initial university duration -> hours
        if(Objects.requireNonNull(MainActivity.getEmotionData().get(MainActivity.m_today)).containsKey(Globals.universityHours)){
            m_uniDurHour=Integer.parseInt(Objects.requireNonNull((Objects.requireNonNull(MainActivity.getEmotionData().get(MainActivity.m_today))).get(Globals.universityHours)));
            m_uniDurHour = Integer.valueOf(HourUni.getText().toString());
        }
    // set initial university duration -> minutes
        if(Objects.requireNonNull(MainActivity.getEmotionData().get(MainActivity.m_today)).containsKey(Globals.universityMinutes)){
            m_uniDurMin=Integer.parseInt(Objects.requireNonNull((Objects.requireNonNull(MainActivity.getEmotionData().get(MainActivity.m_today))).get(Globals.universityMinutes)));
            m_uniDurMin = Integer.valueOf(HourUni.getText().toString());
        }

// set initial sport intensity
        if(Objects.requireNonNull(MainActivity.getEmotionData().get(MainActivity.m_today)).containsKey(Globals.sportIntensity)){
            m_sportIntensity=Integer.parseInt(Objects.requireNonNull((Objects.requireNonNull(MainActivity.getEmotionData().get(MainActivity.m_today))).get(Globals.sportIntensity)));
            SBSport.setProgress(m_sportIntensity);
            if(m_sportIntensity>0) {
                SportProgress.setText("   " + m_sportIntensity * 10 + "%");
            }
        }

    // set initial sport duration -> hours
        if(Objects.requireNonNull(MainActivity.getEmotionData().get(MainActivity.m_today)).containsKey(Globals.sportHours)){
            m_sportDurHour=Integer.parseInt(Objects.requireNonNull((Objects.requireNonNull(MainActivity.getEmotionData().get(MainActivity.m_today))).get(Globals.sportHours)));
            m_sportDurHour = Integer.valueOf(HourUni.getText().toString());
        }
    // set initial sport duration -> minutes
        if(Objects.requireNonNull(MainActivity.getEmotionData().get(MainActivity.m_today)).containsKey(Globals.sportMinutes)){
            m_sportDurMin=Integer.parseInt(Objects.requireNonNull((Objects.requireNonNull(MainActivity.getEmotionData().get(MainActivity.m_today))).get(Globals.sportMinutes)));
            m_sportDurMin = Integer.valueOf(MinUni.getText().toString());
        }

// Other Activities
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

// Social Contact
        CBFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CBFriends.isChecked())
                    CBSocial.add("Friends");
                else
                    CBSocial.remove("Friends");
            }
        });
        CBFamily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CBFamily.isChecked())
                    CBSocial.add("Family");
                else
                    CBSocial.remove("Family");
            }
        });
        CBPartner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CBPartner.isChecked())
                    CBSocial.add("Partner");
                else
                    CBSocial.remove("Partner");
            }
        });
        CBChildren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CBChildren.isChecked())
                    CBSocial.add("Children");
                else
                    CBSocial.remove("Children");
            }
        });
        CBRoommates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CBRoommates.isChecked())
                    CBSocial.add("Roommates");
                else
                    CBSocial.remove("Roommates");
            }
        });


        m_DataOther = CBResult.toString();
        MainActivity.m_todaysData.put(Globals.DataOther, String.valueOf(m_DataOther));
        try {
            MainActivity.saveData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        m_DataSocial = CBSocial.toString();
        MainActivity.m_todaysData.put(Globals.DataSocial, String.valueOf(m_DataSocial));
        try {
            MainActivity.saveData();
        } catch (IOException e) {
            e.printStackTrace();
        }

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
                m_sportIntensity = SBSport.getProgress();
                SportProgress.setText("   " + m_sportIntensity*10 + "%");
                seekBar.setMax(10);
                MainActivity.m_todaysData.put(Globals.sportIntensity, String.valueOf(m_sportIntensity));
                try {
                    MainActivity.saveData();
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
