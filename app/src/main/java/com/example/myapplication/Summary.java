package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.example.myapplication.databinding.SummaryBinding;

import org.w3c.dom.Text;

public class Summary extends Fragment {

    // get earlier dates
    public static Date subtractDays(Date date, int days) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(Calendar.DATE, -days);

        return cal.getTime();
    }

    private SummaryBinding binding;
    private static Date m_today = GlobalDate.val;       //variable for selected date
    private static Date m_dayOne = subtractDays(m_today, 1);
    private static Date m_dayTwo = subtractDays(m_today, 2);
    private static String emotionToday;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = SummaryBinding.inflate(inflater, container, false);

        //Get date
        DateFormat formatter = new SimpleDateFormat(Globals.dateFormat,Globals.myLocal);
        String dateStr = formatter.format(m_today);
        String dateDayOneStr = formatter.format(m_dayOne);
        String dateDayTwoStr = formatter.format(m_dayTwo);
        String[] dateArray = {dateStr, dateDayOneStr, dateDayTwoStr};

        // Write Emotion
        emotionToday = HomeView.m_emotionData.get(dateStr).get(Globals.emotion);   //get emotion from Map
        TextView tv_emotion = binding.summaryEmotionValue;
        tv_emotion.setText(emotionToday+": "); //+date_str only for control

        if(Objects.requireNonNull(MainActivity.getEmotionData().get(dateStr)).containsKey(Globals.feeling)){
            String feeling = MainActivity.getEmotionData().get(dateStr).get(Globals.feeling);
            TextView tv_feeling = binding.summaryEmotionValueTwo;
            tv_feeling.setText(feeling);
        }


        // Write Physical Condition
        // Smoking
        for (String i: dateArray){
            if(Objects.requireNonNull(MainActivity.getEmotionData().get(i)).containsKey(Globals.cigarettes)){
                String smokingToday = MainActivity.getEmotionData().get(i).get(Globals.cigarettes);
                TextView tv_smoking = binding.summaryPhysicalCondition.summarySmokingToday;
                if (smokingToday.contains("No")){
                    tv_smoking.setText(Globals.no);
                } else if (smokingToday.contains("few")){
                    tv_smoking.setText(Globals.little);
                } else {
                    tv_smoking.setText(Globals.lot);
                }
            }
        }
        //Alcohol
        for (String i: dateArray){
            if(Objects.requireNonNull(MainActivity.getEmotionData().get(i)).containsKey(Globals.alcohol)){
                String alcoholToday = MainActivity.getEmotionData().get(i).get(Globals.alcohol);
                TextView tv_alcohol = binding.summaryPhysicalCondition.summaryAlcoholToday;
                if (alcoholToday.contains("No")){
                    tv_alcohol.setText(Globals.no);
                } else if (alcoholToday.contains("little")){
                    tv_alcohol.setText(Globals.little);
                } else {
                    tv_alcohol.setText(Globals.lot);
                }
            }
        }
        // Medicine
        for (String i: dateArray){
            if(Objects.requireNonNull(MainActivity.getEmotionData().get(i)).containsKey(Globals.medication)){
                String medicineToday = MainActivity.getEmotionData().get(i).get(Globals.medication);
                TextView tv_medicineToday = binding.summaryPhysicalCondition.summaryMedicationToday;
                if (medicineToday.contains("No")){
                    tv_medicineToday.setText(Globals.no);
                } else if (medicineToday.contains("already")){
                    tv_medicineToday.setText(Globals.taken);
                } else {
                    tv_medicineToday.setText(Globals.notTaken);
                }
            }
        }
        // Period
        for (String i: dateArray){
            if(Objects.requireNonNull(MainActivity.getEmotionData().get(dateStr)).containsKey(Globals.period)){
                String medicineToday = MainActivity.getEmotionData().get(dateStr).get(Globals.period);
                TextView tv_medicineToday = binding.summaryPhysicalCondition.summaryPeriodToday;
                if (medicineToday.contains("No")){
                    tv_medicineToday.setText(Globals.no);
                } else {
                    tv_medicineToday.setText(Globals.yes);
                }
            }
        }



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
