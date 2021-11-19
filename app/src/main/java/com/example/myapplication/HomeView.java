package com.example.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myapplication.databinding.HomeViewBinding;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class HomeView extends Fragment {

    private static String TAG = "Home View Activity";
    private static CompactCalendarView m_calendarView = null;
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

        m_calendarView = binding.calendar;
        binding.calendar.setUseThreeLetterAbbreviation(true);
        binding.calendar.shouldDrawIndicatorsBelowSelectedDays(true);


        // display current month at the top of the calendar
        DateFormat formatter = new SimpleDateFormat(Globals.monthFormat);
        Calendar calendar = Calendar.getInstance();
        binding.month.setText(formatter.format(calendar.getTime()));

        binding.calendar.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                Log.d(TAG, "Day was clicked: " + dateClicked);
                // @todo: open summary of this day
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                Log.d(TAG, "Month was scrolled to: " + firstDayOfNewMonth);
                // update month above calendar
                DateFormat formatter = new SimpleDateFormat(Globals.monthFormat);
                binding.month.setText(formatter.format(firstDayOfNewMonth));
            }
        });

        setCalenderData();

        return binding.getRoot();

    }

    // set the data of the calendar
    private void setCalenderData(){
        HashMap<String, Map<String,String>> emotionData = MainActivity.getEmotionData();
        String dateString = null;
        Date date = null;

        DateFormat formatter = new SimpleDateFormat(Globals.dateFormat);
        for(int i=0;i<emotionData.keySet().size();i++){
            dateString = (String) emotionData.keySet().toArray()[i];
            Log.d(TAG,dateString);
            try {
                date = (Date)formatter.parse(dateString);
                Log.d(TAG, String.valueOf(date.getTime()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            setCalendarColor(emotionData.get(dateString).get(Globals.emotion),date.getTime());
        }
    }

    // set the color of the corresponding day in the calendar
    public static void setCalendarColor(String emotion, long date) {
        int color = 0;
        switch(emotion){
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
        Log.d(TAG, String.valueOf(date));
        Event ev2 = new Event(color, date);
        m_calendarView.removeEvents(date);
        m_calendarView.addEvent(ev2);
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