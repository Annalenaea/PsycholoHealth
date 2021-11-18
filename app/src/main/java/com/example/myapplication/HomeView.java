package com.example.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.databinding.HomeViewBinding;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class HomeView extends Fragment {

    private static String TAG = "Home View Activity";

    private HomeViewBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = HomeViewBinding.inflate(inflater, container, false);
        binding.addEmotion.setOnClickListener(view -> {
            EmotionSelectionPopup emotionSelectionPopup= new EmotionSelectionPopup();
            emotionSelectionPopup.showPopupWindow(getView().findViewById(R.id.homeView),binding.addEmotion.getLeft(),binding.addEmotion.getTop());
        });

        binding.calendar.setUseThreeLetterAbbreviation(true);


        //        binding.calendar.setOnDateChangeListener((view, year, month, dayOfMonth) ->  {
//            Log.d(TAG,"Hi");
//        });

        setCalenderData();

        return binding.getRoot();

    }

    private void setCalenderData(){
        HashMap<String, Map<String,String>> emotionData = MainActivity.getEmotionData();
        String dateString = null;
        Date date = null;
        int color = 0;

        DateFormat formatter = new SimpleDateFormat(Globals.dateFormat);
        for(int i=0;i<emotionData.keySet().size();i++){
            dateString = "11/17/2021";//(String) emotionData.keySet().toArray()[i];
            Log.d(TAG,dateString);
            try {
                date = (Date)formatter.parse(dateString);
                Log.d(TAG, String.valueOf(date.getTime()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            switch(emotionData.get(dateString).get(Globals.emotion)){
                case Globals.happy:
                    color = Color.GREEN;
                    break;
                case Globals.neutral:
                    color = Color.YELLOW;
                    break;
                case Globals.sad:
                    color = Color.RED;
                    break;
                default :
                    color= Color.WHITE;
                    break;
            }
            Event ev2 = new Event(color, date.getTime());
            binding.calendar.addEvent(ev2);
        }
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