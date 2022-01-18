package com.example.myapplication;

import android.graphics.Color;
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

        // Display Emotion
        emotionToday = HomeView.m_emotionData.get(dateStr).get(Globals.emotion);   //get emotion from Map
        TextView tv_emotion = binding.summaryEmotionValue;
        if(Objects.requireNonNull(MainActivity.getEmotionData().get(dateStr)).containsKey(Globals.emotion)) {
            tv_emotion.setText(emotionToday + ": "); //+date_str only for control
        }

        if(Objects.requireNonNull(MainActivity.getEmotionData().get(dateStr)).containsKey(Globals.feeling)){
            String feeling = MainActivity.getEmotionData().get(dateStr).get(Globals.feeling);
            TextView tv_feeling = binding.summaryEmotionValueTwo;
            tv_feeling.setText(feeling);
        }


        // Display Physical Condition
        // Smoking
        TextView tv_smokingToday = binding.summaryPhysicalCondition.summarySmokingToday;
        TextView tv_smokingDayOne = binding.summaryPhysicalCondition.summarySmokingDayOne;
        TextView tv_smokingDayTwo = binding.summaryPhysicalCondition.summarySmokingDayTwo;
        TextView[] smokingArray = {tv_smokingToday, tv_smokingDayOne, tv_smokingDayTwo};
        for (int i = 0; i <= 2; i++){
            if(Objects.requireNonNull(MainActivity.getEmotionData().get(dateArray[i])).containsKey(Globals.cigarettes)){
                String smokingToday = MainActivity.getEmotionData().get(dateArray[i]).get(Globals.cigarettes);
                if (smokingToday.contains("No")){
                    smokingArray[i].setText(Globals.no);
                } else if (smokingToday.contains("few")){
                    smokingArray[i].setText(Globals.little);
                } else {
                    smokingArray[i].setText(Globals.lot);
                }
            }
        }
        //Alcohol
        TextView tv_alcoholToday = binding.summaryPhysicalCondition.summaryAlcoholToday;
        TextView tv_alcoholDayOne = binding.summaryPhysicalCondition.summaryAlcoholDayOne;
        TextView tv_alcoholDayTwo = binding.summaryPhysicalCondition.summaryAlcoholDayTwo;
        TextView[] alcoholArray = {tv_alcoholToday, tv_alcoholDayOne, tv_alcoholDayTwo};
        for (int i = 0; i <= 2; i++){
            if(Objects.requireNonNull(MainActivity.getEmotionData().get(dateArray[i])).containsKey(Globals.alcohol)){
                String alcoholToday = MainActivity.getEmotionData().get(dateArray[i]).get(Globals.alcohol);
                if (alcoholToday.contains("No")){
                    alcoholArray[i].setText(Globals.no);
                } else if (alcoholToday.contains("little")){
                    alcoholArray[i].setText(Globals.little);
                } else {
                    alcoholArray[i].setText(Globals.lot);
                }
            }
        }
        // Medicine
        TextView tv_medicineToday = binding.summaryPhysicalCondition.summaryMedicationToday;
        TextView tv_medicineDayOne = binding.summaryPhysicalCondition.summaryMedicationDayOne;
        TextView tv_medicineDayTwo = binding.summaryPhysicalCondition.summaryMedicationDayTwo;
        TextView[] medicineArray = {tv_medicineToday, tv_medicineDayOne, tv_medicineDayTwo};
        for (int i = 0; i <= 2; i++){
            if(Objects.requireNonNull(MainActivity.getEmotionData().get(dateArray[i])).containsKey(Globals.medication)){
                String medicineToday = MainActivity.getEmotionData().get(dateArray[i]).get(Globals.medication);
                if (medicineToday.contains("No")){
                    medicineArray[i].setText(Globals.no);
                } else if (medicineToday.contains("already")){
                    medicineArray[i].setText(Globals.taken);
                } else {
                    medicineArray[i].setText(Globals.notTaken);
                }
            }
        }
        // Period
        TextView tv_periodToday = binding.summaryPhysicalCondition.summaryPeriodToday;
        TextView tv_periodDayOne = binding.summaryPhysicalCondition.summaryPeriodDayOne;
        TextView tv_periodDayTwo = binding.summaryPhysicalCondition.summaryPeriodDayTwo;
        TextView[] periodArray = {tv_periodToday, tv_periodDayOne, tv_periodDayTwo};
        for (int i = 0; i <= 2; i++){
            if(Objects.requireNonNull(MainActivity.getEmotionData().get(dateArray[i])).containsKey(Globals.period)){
                String medicineToday = MainActivity.getEmotionData().get(dateArray[i]).get(Globals.period);
                TextView tv_medicineToday = binding.summaryPhysicalCondition.summaryPeriodToday;
                if (medicineToday.contains("No")){
                    tv_medicineToday.setText(Globals.no);
                } else {
                    tv_medicineToday.setText(Globals.yes);
                }
            }
        }

        // Display Stress Level
        TextView StressTodayBar = binding.summaryStress.StressTodayBar;
        TextView StressDayOneBar = binding.summaryStress.StressDayOneBar;
        TextView StressDayTwoBar = binding.summaryStress.StressDayTwoBar;
        // Today
        if(Objects.requireNonNull(MainActivity.getEmotionData().get(dateStr)).containsKey(Globals.stressLevel)){
            String StressStr = MainActivity.getEmotionData().get(dateStr).get(Globals.stressLevel);
            int StressInt = Integer.parseInt(StressStr);
            ViewGroup.LayoutParams paramsStressToday = StressTodayBar.getLayoutParams();
            paramsStressToday.height = (StressInt) * 16;
            StressTodayBar.setLayoutParams(paramsStressToday);
        }
        // Day One
        if(Objects.requireNonNull(MainActivity.getEmotionData().get(dateDayOneStr)).containsKey(Globals.stressLevel)) {
            String StressDayOneStr = MainActivity.getEmotionData().get(dateDayOneStr).get(Globals.stressLevel);
            int StressDayOneInt = Integer.parseInt(StressDayOneStr);
            ViewGroup.LayoutParams paramsStressDayOne = StressDayOneBar.getLayoutParams();
            paramsStressDayOne.height = (StressDayOneInt) * 16;
            StressDayOneBar.setLayoutParams(paramsStressDayOne);
        }
        // Day Two
        if(Objects.requireNonNull(MainActivity.getEmotionData().get(dateDayTwoStr)).containsKey(Globals.stressLevel)) {
            String StressDayTwoStr = MainActivity.getEmotionData().get(dateDayTwoStr).get(Globals.stressLevel);
            int StressDayTwoInt = Integer.parseInt(StressDayTwoStr);
            ViewGroup.LayoutParams paramsStressDayTwo = StressDayTwoBar.getLayoutParams();
            paramsStressDayTwo.height = (StressDayTwoInt) * 16;
            StressDayTwoBar.setLayoutParams(paramsStressDayTwo);
        }

        // Display Sleep
        TextView SleepTodayBar = binding.summarysleep.SleepTodayBar;
        TextView SleepDayOneBar = binding.summarysleep.SleepDayOneBar;
        TextView SleepDayTwoBar = binding.summarysleep.SleepDayTwoBar;
        // Today
        if(Objects.requireNonNull(MainActivity.getEmotionData().get(dateStr)).containsKey(Globals.sleepDuration)){
            String SleepStr = MainActivity.getEmotionData().get(dateStr).get(Globals.sleepDuration);
            int SleepInt = Integer.parseInt(SleepStr);
            ViewGroup.LayoutParams paramsSleepToday = SleepTodayBar.getLayoutParams();
            paramsSleepToday.height = (SleepInt+1) * 18;
            SleepTodayBar.setLayoutParams(paramsSleepToday);
            // Color depending on quality
            String QualityToday = MainActivity.getEmotionData().get(dateStr).get(Globals.sleepQuality);
            if (QualityToday.contains("Very bad")) {
                SleepTodayBar.setBackgroundColor(Color.RED);
            } else if (QualityToday.contains("Bad")) {
                SleepTodayBar.setBackgroundColor(Color.parseColor("#FF8800"));
            } else if (QualityToday.contains("Normal")) {
                SleepTodayBar.setBackgroundColor(Color.YELLOW);
            } else if (QualityToday.contains("Very good")) {
                SleepTodayBar.setBackgroundColor(Color.parseColor("#228B22"));
            } else {
                SleepTodayBar.setBackgroundColor(Color.parseColor("#DFFF00"));
            }


        }
        // Day One
        if(Objects.requireNonNull(MainActivity.getEmotionData().get(dateDayOneStr)).containsKey(Globals.sleepDuration)){
            String SleepDayOneStr = MainActivity.getEmotionData().get(dateDayOneStr).get(Globals.sleepDuration);
            int SleepDayOneInt = Integer.parseInt(SleepDayOneStr);
            ViewGroup.LayoutParams paramsSleepDayOne = SleepDayOneBar.getLayoutParams();
            paramsSleepDayOne.height = (SleepDayOneInt+1) * 18;
            SleepDayOneBar.setLayoutParams(paramsSleepDayOne);
        }
        // Day Two
        if(Objects.requireNonNull(MainActivity.getEmotionData().get(dateDayTwoStr)).containsKey(Globals.sleepDuration)){
            String SleepDayTwoStr = MainActivity.getEmotionData().get(dateDayTwoStr).get(Globals.sleepDuration);
            int SleepDayTwoInt = Integer.parseInt(SleepDayTwoStr);
            ViewGroup.LayoutParams paramsSleepDayTwo = SleepDayTwoBar.getLayoutParams();
            paramsSleepDayTwo.height = (SleepDayTwoInt+1) * 18;
            SleepDayTwoBar.setLayoutParams(paramsSleepDayTwo);
        }

        // Display University Work



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
