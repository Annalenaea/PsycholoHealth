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
import java.util.Arrays;
import java.util.Calendar;
import java.util.Objects;

public class ActivityQuestionnaire extends Fragment {

    private ActivityQuestionnaireBinding binding;
    private CheckBox CBDomesticWork, CBSelfCare, CBParty, CBHobby;
    private CheckBox CBFamily, CBFriends, CBPartner, CBChildren, CBRoommates;
    private CheckBox CBMuseum, CBTheatre,CBConcert, CBCinema, CBRestaurant;
    private Button Finish;
    private ArrayList<String> CBResult, CBSocial, CBCulture;
    private SeekBar SBSport, SBUniversity;
    private EditText HourUni, MinUni, HourSport, MinSport, MinSocial, HourSocial;
    private TextView SportProgress;
    private TextView UniversityProgress;
    public static Integer m_universityIntensity = 0;
    public static Integer m_uniDurHour = 0;
    public static Integer m_uniDurMin = 0;
    public static Integer m_sportDurHour = 0;
    public static Integer m_sportDurMin = 0;
    public static Integer m_sportIntensity = 0;
    public static boolean  m_DomWork = false;
    public static boolean  m_SelfCare = false;
    public static boolean  m_Party = false;
    public static boolean  m_Hobbies = false;
    public static boolean  m_Museum = false;
    public static boolean  m_Theatre = false;
    public static boolean  m_Concert = false;
    public static boolean  m_Cinema = false;
    public static boolean  m_Restaurant = false;
    public static boolean  m_Family = false;
    public static boolean  m_Friends = false;
    public static boolean  m_Partner = false;
    public static boolean  m_Children = false;
    public static boolean  m_Roomates = false;

    public static String m_MinSocial;
    public static String m_HourSocial;

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
        CBMuseum = binding.checkboxMuseum;
        CBTheatre = binding.checkboxTheatre;
        CBConcert = binding.checkboxConcert;
        CBCinema = binding.checkboxCinema;
        CBRestaurant = binding.checkboxRestaurant;
        CBSocial = new ArrayList<>();
        CBCulture = new ArrayList<>();
        CBResult = new ArrayList<>();
        SBSport = binding.seekBar5;
        SBUniversity = binding.seekBar2;
        UniversityProgress = binding.textViewIntensityUniversity;
        SportProgress = binding.textViewIntensitySport;
        HourUni = (EditText) binding.EditTextHours1;
        MinUni = (EditText) binding.EditTextMinutes1;
        HourSport = (EditText) binding.EditTextHours2;
        MinSport = (EditText) binding.EditTextMinutes2;
        HourSocial = binding.EditTextHobbyHours1;
        MinSocial = binding.EditTextHobbyMinutes1;
        Finish = binding.buttonFinish;


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
            HourUni.setText(String.valueOf(m_uniDurHour));
        }
    // set initial university duration -> minutes
        if(Objects.requireNonNull(MainActivity.getEmotionData().get(MainActivity.m_today)).containsKey(Globals.universityMinutes)){
            m_uniDurMin=Integer.parseInt(Objects.requireNonNull((Objects.requireNonNull(MainActivity.getEmotionData().get(MainActivity.m_today))).get(Globals.universityMinutes)));
            MinUni.setText(String.valueOf(m_uniDurMin));
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
            m_sportDurHour = Integer.parseInt(Objects.requireNonNull((Objects.requireNonNull(MainActivity.getEmotionData().get(MainActivity.m_today))).get(Globals.sportHours)));
            HourSport.setText(String.valueOf(m_sportDurHour));
        }
    // set initial sport duration -> minutes
        if(Objects.requireNonNull(MainActivity.getEmotionData().get(MainActivity.m_today)).containsKey(Globals.sportMinutes)){
            m_sportDurMin = Integer.parseInt(Objects.requireNonNull((Objects.requireNonNull(MainActivity.getEmotionData().get(MainActivity.m_today))).get(Globals.sportMinutes)));
            MinSport.setText(String.valueOf(m_sportDurMin));
        }

        // set initial social duration -> hours
        if(Objects.requireNonNull(MainActivity.getEmotionData().get(MainActivity.m_today)).containsKey(Globals.HourSocial)){
            m_HourSocial = Objects.requireNonNull((Objects.requireNonNull(MainActivity.getEmotionData().get(MainActivity.m_today))).get(Globals.HourSocial));
            HourSocial.setText(String.valueOf(m_HourSocial));
        }
        // set initial social duration -> minutes
        if(Objects.requireNonNull(MainActivity.getEmotionData().get(MainActivity.m_today)).containsKey(Globals.MinSocial)){
            m_MinSocial = Objects.requireNonNull((Objects.requireNonNull(MainActivity.getEmotionData().get(MainActivity.m_today))).get(Globals.MinSocial));
            MinSocial.setText(m_MinSocial);
        }

        // set initial other activities
        if(Objects.requireNonNull(MainActivity.getEmotionData().get(MainActivity.m_today)).containsKey(Globals.DomWork)){
            m_DomWork = Boolean.parseBoolean(Objects.requireNonNull((Objects.requireNonNull(MainActivity.getEmotionData().get(MainActivity.m_today))).get(Globals.DomWork)));
            CBDomesticWork.setChecked(m_DomWork);
        }
        if(Objects.requireNonNull(MainActivity.getEmotionData().get(MainActivity.m_today)).containsKey(Globals.SelfCare)){
            m_SelfCare = Boolean.parseBoolean(Objects.requireNonNull((Objects.requireNonNull(MainActivity.getEmotionData().get(MainActivity.m_today))).get(Globals.SelfCare)));
            CBSelfCare.setChecked(m_SelfCare);
        }
        if(Objects.requireNonNull(MainActivity.getEmotionData().get(MainActivity.m_today)).containsKey(Globals.Party)){
            m_Party= Boolean.parseBoolean(Objects.requireNonNull((Objects.requireNonNull(MainActivity.getEmotionData().get(MainActivity.m_today))).get(Globals.Party)));
            CBParty.setChecked(m_Party);
        }
        if(Objects.requireNonNull(MainActivity.getEmotionData().get(MainActivity.m_today)).containsKey(Globals.Hobbies)){
            m_Hobbies = Boolean.parseBoolean(Objects.requireNonNull((Objects.requireNonNull(MainActivity.getEmotionData().get(MainActivity.m_today))).get(Globals.Hobbies)));
            CBHobby.setChecked(m_Hobbies);
        }
        if(Objects.requireNonNull(MainActivity.getEmotionData().get(MainActivity.m_today)).containsKey(Globals.Friends)){
            m_Friends = Boolean.parseBoolean(Objects.requireNonNull((Objects.requireNonNull(MainActivity.getEmotionData().get(MainActivity.m_today))).get(Globals.Friends)));
            CBFriends.setChecked(m_Friends);
        }
        if(Objects.requireNonNull(MainActivity.getEmotionData().get(MainActivity.m_today)).containsKey(Globals.Family)){
            m_Family = Boolean.parseBoolean(Objects.requireNonNull((Objects.requireNonNull(MainActivity.getEmotionData().get(MainActivity.m_today))).get(Globals.Family)));
            CBFamily.setChecked(m_Family);
        }
        if(Objects.requireNonNull(MainActivity.getEmotionData().get(MainActivity.m_today)).containsKey(Globals.Partner)){
            m_Partner = Boolean.parseBoolean(Objects.requireNonNull((Objects.requireNonNull(MainActivity.getEmotionData().get(MainActivity.m_today))).get(Globals.Partner)));
            CBPartner.setChecked(m_Partner);
        }
        if(Objects.requireNonNull(MainActivity.getEmotionData().get(MainActivity.m_today)).containsKey(Globals.Children)){
            m_Children = Boolean.parseBoolean(Objects.requireNonNull((Objects.requireNonNull(MainActivity.getEmotionData().get(MainActivity.m_today))).get(Globals.Children)));
            CBChildren.setChecked(m_Children);
        }
        if(Objects.requireNonNull(MainActivity.getEmotionData().get(MainActivity.m_today)).containsKey(Globals.Roommates)){
            m_Roomates = Boolean.parseBoolean(Objects.requireNonNull((Objects.requireNonNull(MainActivity.getEmotionData().get(MainActivity.m_today))).get(Globals.Roommates)));
            CBRoommates.setChecked(m_Roomates);
        }
        if(Objects.requireNonNull(MainActivity.getEmotionData().get(MainActivity.m_today)).containsKey(Globals.Museum)){
            m_Museum = Boolean.parseBoolean(Objects.requireNonNull((Objects.requireNonNull(MainActivity.getEmotionData().get(MainActivity.m_today))).get(Globals.Museum)));
            CBMuseum.setChecked(m_Museum);
        }
        if(Objects.requireNonNull(MainActivity.getEmotionData().get(MainActivity.m_today)).containsKey(Globals.Theatre)){
            m_Theatre = Boolean.parseBoolean(Objects.requireNonNull((Objects.requireNonNull(MainActivity.getEmotionData().get(MainActivity.m_today))).get(Globals.Theatre)));
            CBTheatre.setChecked(m_Theatre);
        }
        if(Objects.requireNonNull(MainActivity.getEmotionData().get(MainActivity.m_today)).containsKey(Globals.Concert)){
            m_Concert = Boolean.parseBoolean(Objects.requireNonNull((Objects.requireNonNull(MainActivity.getEmotionData().get(MainActivity.m_today))).get(Globals.Concert)));
            CBConcert.setChecked(m_Concert);
        }
        if(Objects.requireNonNull(MainActivity.getEmotionData().get(MainActivity.m_today)).containsKey(Globals.Cinema)){
            m_Cinema = Boolean.parseBoolean(Objects.requireNonNull((Objects.requireNonNull(MainActivity.getEmotionData().get(MainActivity.m_today))).get(Globals.Cinema)));
            CBCinema.setChecked(m_Cinema);
        }
        if(Objects.requireNonNull(MainActivity.getEmotionData().get(MainActivity.m_today)).containsKey(Globals.Restaurant)){
            m_Restaurant = Boolean.parseBoolean(Objects.requireNonNull((Objects.requireNonNull(MainActivity.getEmotionData().get(MainActivity.m_today))).get(Globals.Restaurant)));
            CBRestaurant.setChecked(m_Restaurant);
        }

    // Other Activities
        //on Click Listeners
        CBDomesticWork.setOnClickListener(v -> {
            if (CBDomesticWork.isChecked())
                m_DomWork = true;
            else
                m_DomWork = false;
        });
        CBSelfCare.setOnClickListener(v -> {
            if (CBSelfCare.isChecked())
                m_SelfCare = true;
            else
                m_SelfCare = false;
        });
        CBParty.setOnClickListener(v -> {
            if (CBParty.isChecked())
                m_Party = true;
            else
                m_Party = false;
        });
        CBHobby.setOnClickListener(v -> {
            if (CBHobby.isChecked())
                m_Hobbies = true;
            else
                m_Hobbies = false;
        });

// Social Contact
        CBFriends.setOnClickListener(v -> {
            if (CBFriends.isChecked())
                m_Friends = true;
            else
                m_Friends = false;
        });
        CBFamily.setOnClickListener(v -> {
            if (CBFamily.isChecked())
                m_Family = true;
            else
                m_Family = false;
        });
        CBPartner.setOnClickListener(v -> {
            if (CBPartner.isChecked())
                m_Partner = true;
            else
                m_Partner = false;
        });
        CBChildren.setOnClickListener(v -> {
            if (CBChildren.isChecked())
                m_Children = true;
            else
                m_Children = false;
        });
        CBRoommates.setOnClickListener(v -> {
            if (CBRoommates.isChecked())
                m_Roomates = true;
            else
                m_Roomates = false;
        });

// Cultural Activities
        CBMuseum.setOnClickListener(v -> {
            if (CBMuseum.isChecked())
                m_Museum = true;
            else
                m_Museum = false;
        });
        CBTheatre.setOnClickListener(v -> {
            if (CBTheatre.isChecked())
                m_Theatre = true;
            else
                m_Theatre = false;
        });
        CBConcert.setOnClickListener(v -> {
            if (CBConcert.isChecked())
                m_Concert = true;
            else
                m_Concert = false;
        });
        CBCinema.setOnClickListener(v -> {
            if (CBCinema.isChecked())
                m_Cinema = true;
            else
                m_Cinema = false;
        });
        CBRestaurant.setOnClickListener(v -> {
            if (CBRestaurant.isChecked())
                m_Restaurant = true;
            else
                m_Restaurant = false;
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
                m_sportIntensity = SBSport.getProgress();
                SportProgress.setText("   " + m_sportIntensity*10 + "%");
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
            }
        });

        // go back to homeview if finish button is clicked
        homeNavi = NavHostFragment.findNavController(ActivityQuestionnaire.this);
        activityQuestionnaire = ActivityQuestionnaire.this;

        Finish.setOnClickListener(View -> {
            MainActivity.m_todaysData.put(Globals.sportIntensity, String.valueOf(m_sportIntensity));
            MainActivity.m_todaysData.put(Globals.universityIntensity, String.valueOf(m_universityIntensity));

            // save checkboxes
            MainActivity.m_todaysData.put(Globals.DomWork, String.valueOf(m_DomWork));
            MainActivity.m_todaysData.put(Globals.SelfCare, String.valueOf(m_SelfCare));
            MainActivity.m_todaysData.put(Globals.Party, String.valueOf(m_Party));
            MainActivity.m_todaysData.put(Globals.Hobbies, String.valueOf(m_Hobbies));

            MainActivity.m_todaysData.put(Globals.Museum, String.valueOf(m_Museum));
            MainActivity.m_todaysData.put(Globals.Theatre, String.valueOf(m_Theatre));
            MainActivity.m_todaysData.put(Globals.Concert, String.valueOf(m_Concert));
            MainActivity.m_todaysData.put(Globals.Cinema, String.valueOf(m_Cinema));
            MainActivity.m_todaysData.put(Globals.Restaurant, String.valueOf(m_Restaurant));

            MainActivity.m_todaysData.put(Globals.Family, String.valueOf(m_Family));
            MainActivity.m_todaysData.put(Globals.Friends, String.valueOf(m_Friends));
            MainActivity.m_todaysData.put(Globals.Partner, String.valueOf(m_Partner));
            MainActivity.m_todaysData.put(Globals.Children, String.valueOf(m_Children));
            MainActivity.m_todaysData.put(Globals.Roommates, String.valueOf(m_Roomates));


            // Social Activities Duration
            m_MinSocial = MinSocial.getText().toString();
            m_HourSocial = HourSocial.getText().toString();
            MainActivity.m_todaysData.put(Globals.MinSocial, String.valueOf(m_MinSocial));
            MainActivity.m_todaysData.put(Globals.HourSocial, String.valueOf(m_HourSocial));

            // Sport Activities Duration
            m_sportDurMin = Integer.parseInt(MinSport.getText().toString());
            m_sportDurHour = Integer.parseInt(HourSport.getText().toString());
            MainActivity.m_todaysData.put(Globals.sportMinutes, String.valueOf(m_sportDurMin));
            MainActivity.m_todaysData.put(Globals.sportHours, String.valueOf(m_sportDurHour));

            // Uni Activities Duration
            m_uniDurMin = Integer.parseInt(MinUni.getText().toString());
            m_uniDurHour = Integer.parseInt(HourUni.getText().toString());
            MainActivity.m_todaysData.put(Globals.universityMinutes, String.valueOf(m_uniDurMin));
            MainActivity.m_todaysData.put(Globals.universityHours, String.valueOf(m_uniDurHour));

            try {
                MainActivity.saveData();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String text = "Saved";
            Toast.makeText(getContext(),text, Toast.LENGTH_LONG).show();
            homeNavi.navigate(R.id.action_ActivityFragment_to_HomeFragment);
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
