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
        DateFormat formatter = new SimpleDateFormat(Globals.dateFormat, Globals.myLocal);
        String dateStr = formatter.format(m_today);
        String dateDayOneStr = formatter.format(m_dayOne);
        String dateDayTwoStr = formatter.format(m_dayTwo);
        String[] dateArray = {dateStr, dateDayOneStr, dateDayTwoStr};
        Map<String, String> voidData = new HashMap<>(); // void Map

        for (int i = 0; i <= 2; i++) {
            if (!MainActivity.getEmotionData().containsKey(dateArray[i])) {
                MainActivity.getEmotionData().put(dateArray[i], voidData);
            }
        }


        // Display Emotion
        if (MainActivity.getEmotionData().get(dateStr).containsKey(Globals.emotion)) {
            if (!Objects.requireNonNull(Objects.requireNonNull(MainActivity.getEmotionData().get(dateStr)).get(Globals.emotion)).isEmpty()) {
                TextView tv_emotion = binding.summaryEmotionValue;
                emotionToday = MainActivity.getEmotionData().get(dateStr).get(Globals.emotion);   //get emotion from Map
                tv_emotion.setText(emotionToday + ": "); //+date_str only for control
            }
        }
        if (MainActivity.getEmotionData().get(dateStr).containsKey(Globals.feeling)) {
            if (!Objects.requireNonNull(Objects.requireNonNull(MainActivity.getEmotionData().get(dateStr)).get(Globals.feeling)).isEmpty()) {
                String feeling = MainActivity.getEmotionData().get(dateStr).get(Globals.feeling);
                TextView tv_feeling = binding.summaryEmotionValueTwo;
                tv_feeling.setText(feeling);
            }
        }

        // Display Physical Condition
        // Smoking
        TextView tv_smokingToday = binding.summaryPhysicalCondition.summarySmokingToday;
        TextView tv_smokingDayOne = binding.summaryPhysicalCondition.summarySmokingDayOne;
        TextView tv_smokingDayTwo = binding.summaryPhysicalCondition.summarySmokingDayTwo;
        TextView[] smokingArray = {tv_smokingToday, tv_smokingDayOne, tv_smokingDayTwo};
        for (int i = 0; i <= 2; i++) {
            if (MainActivity.getEmotionData().get(dateArray[i]).containsKey(Globals.cigarettes)) {
                if (!Objects.requireNonNull(Objects.requireNonNull(MainActivity.getEmotionData().get(dateArray[i])).get(Globals.cigarettes)).isEmpty()) {
                    String smokingToday = MainActivity.getEmotionData().get(dateArray[i]).get(Globals.cigarettes);
                    if (smokingToday.contains("No")) {
                        smokingArray[i].setText(Globals.no);
                    } else if (smokingToday.contains("few")) {
                        smokingArray[i].setText(Globals.little);
                    } else {
                        smokingArray[i].setText(Globals.lot);
                    }
                }
            }
        }
        //Alcohol
        TextView tv_alcoholToday = binding.summaryPhysicalCondition.summaryAlcoholToday;
        TextView tv_alcoholDayOne = binding.summaryPhysicalCondition.summaryAlcoholDayOne;
        TextView tv_alcoholDayTwo = binding.summaryPhysicalCondition.summaryAlcoholDayTwo;
        TextView[] alcoholArray = {tv_alcoholToday, tv_alcoholDayOne, tv_alcoholDayTwo};
        for (int i = 0; i <= 2; i++) {
            if (MainActivity.getEmotionData().get(dateArray[i]).containsKey(Globals.alcohol)) {
                if (!Objects.requireNonNull(Objects.requireNonNull(MainActivity.getEmotionData().get(dateArray[i])).get(Globals.alcohol)).isEmpty()) {
                    String alcoholToday = MainActivity.getEmotionData().get(dateArray[i]).get(Globals.alcohol);
                    if (alcoholToday.contains("No")) {
                        alcoholArray[i].setText(Globals.no);
                    } else if (alcoholToday.contains("little")) {
                        alcoholArray[i].setText(Globals.little);
                    } else {
                        alcoholArray[i].setText(Globals.lot);
                    }
                }
            }
        }
        // Medicine
        TextView tv_medicineToday = binding.summaryPhysicalCondition.summaryMedicationToday;
        TextView tv_medicineDayOne = binding.summaryPhysicalCondition.summaryMedicationDayOne;
        TextView tv_medicineDayTwo = binding.summaryPhysicalCondition.summaryMedicationDayTwo;
        TextView[] medicineArray = {tv_medicineToday, tv_medicineDayOne, tv_medicineDayTwo};
        for (int i = 0; i <= 2; i++) {
            if (MainActivity.getEmotionData().get(dateArray[i]).containsKey(Globals.medication)) {
                if (!Objects.requireNonNull(Objects.requireNonNull(MainActivity.getEmotionData().get(dateArray[i])).get(Globals.medication)).isEmpty()) {
                    String medicineToday = MainActivity.getEmotionData().get(dateArray[i]).get(Globals.medication);
                    if (medicineToday.contains("No")) {
                        medicineArray[i].setText(Globals.no);
                    } else if (medicineToday.contains("already")) {
                        medicineArray[i].setText(Globals.taken);
                    } else {
                        medicineArray[i].setText(Globals.notTaken);
                    }
                }
            }
        }
        // Period
        TextView tv_periodToday = binding.summaryPhysicalCondition.summaryPeriodToday;
        TextView tv_periodDayOne = binding.summaryPhysicalCondition.summaryPeriodDayOne;
        TextView tv_periodDayTwo = binding.summaryPhysicalCondition.summaryPeriodDayTwo;
        TextView[] periodArray = {tv_periodToday, tv_periodDayOne, tv_periodDayTwo};
        for (int i = 0; i <= 2; i++) {
            if (MainActivity.getEmotionData().get(dateArray[i]).containsKey(Globals.period)) {
                if (!Objects.requireNonNull(Objects.requireNonNull(MainActivity.getEmotionData().get(dateArray[i])).get(Globals.period)).isEmpty()) {
                    String medicineToday = MainActivity.getEmotionData().get(dateArray[i]).get(Globals.period);
                    if (medicineToday.contains("No")) {
                        periodArray[i].setText(Globals.no);
                    } else {
                        periodArray[i].setText(Globals.yes);
                    }
                }
            }
        }

        // Display Stress Level
        TextView StressTodayBar = binding.summaryStress.StressTodayBar;
        TextView StressDayOneBar = binding.summaryStress.StressDayOneBar;
        TextView StressDayTwoBar = binding.summaryStress.StressDayTwoBar;
        TextView[] stressArray = {StressTodayBar, StressDayOneBar, StressDayTwoBar};
        for (int i=0; i<=2; i++) {
            if (MainActivity.getEmotionData().get(dateArray[i]).containsKey(Globals.stressLevel)) {
                if (!Objects.requireNonNull(Objects.requireNonNull(MainActivity.getEmotionData().get(dateArray[i])).get(Globals.stressLevel)).isEmpty()) {
                    String StressStr = MainActivity.getEmotionData().get(dateArray[i]).get(Globals.stressLevel);
                    int StressInt = Integer.parseInt(StressStr);
                    ViewGroup.LayoutParams paramsStress = stressArray[i].getLayoutParams();
                    paramsStress.height = (StressInt) * 16;
                    stressArray[i].setLayoutParams(paramsStress);
                }
            }
        }


        // Display Sleep
        TextView SleepTodayBar = binding.summarysleep.SleepTodayBar;
        TextView SleepDayOneBar = binding.summarysleep.SleepDayOneBar;
        TextView SleepDayTwoBar = binding.summarysleep.SleepDayTwoBar;
        TextView[] sleepArray = {SleepTodayBar, SleepDayOneBar, SleepDayTwoBar};
        for (int i=0; i<=2; i++) {
            if (MainActivity.getEmotionData().get(dateArray[i]).containsKey(Globals.sleepDuration)) {
                if (!Objects.requireNonNull(Objects.requireNonNull(MainActivity.getEmotionData().get(dateArray[i])).get(Globals.sleepDuration)).isEmpty()) {
                    String SleepStr = MainActivity.getEmotionData().get(dateArray[i]).get(Globals.sleepDuration);
                    int SleepInt = Integer.parseInt(SleepStr);
                    ViewGroup.LayoutParams paramsSleep = sleepArray[i].getLayoutParams();
                    paramsSleep.height = (SleepInt + 1) * 18;
                    sleepArray[i].setLayoutParams(paramsSleep);
                    // Color depending on quality
                    if (MainActivity.getEmotionData().get(dateArray[i]).containsKey(Globals.sleepQuality)) {
                        if (!Objects.requireNonNull(Objects.requireNonNull(MainActivity.getEmotionData().get(dateArray[i])).get(Globals.sleepQuality)).isEmpty()) {
                            String Quality = MainActivity.getEmotionData().get(dateArray[i]).get(Globals.sleepQuality);
                            if (Quality.contains("Very bad")) {
                                sleepArray[i].setBackgroundColor(Color.RED);
                            } else if (Quality.contains("Bad")) {
                                sleepArray[i].setBackgroundColor(Color.parseColor("#FF8800"));
                            } else if (Quality.contains("Normal")) {
                                sleepArray[i].setBackgroundColor(Color.YELLOW);
                            } else if (Quality.contains("Very good")) {
                                sleepArray[i].setBackgroundColor(Color.parseColor("#228B22"));
                            } else {
                                sleepArray[i].setBackgroundColor(Color.parseColor("#DFFF00"));
                            }
                        }
                    }
                }
            }
        }

        // Display University Work
        TextView tv_universityDuration = binding.summaryUniversity.summaryUniversityDuration;
        TextView tv_universityIntensity = binding.summaryUniversity.summaryUniversityIntensityValue;
        String uniHours = "0";
        String uniMinutes = "0";
        String uniIntensity = " ";
        if (MainActivity.getEmotionData().get(dateStr).containsKey(Globals.universityHours)) {
            if (!Objects.requireNonNull(Objects.requireNonNull(MainActivity.getEmotionData().get(dateStr)).get(Globals.universityHours)).isEmpty()) {
                uniHours = MainActivity.getEmotionData().get(dateStr).get(Globals.universityHours);
            }
        }
        if (MainActivity.getEmotionData().get(dateStr).containsKey(Globals.universityMinutes)) {
            if (!Objects.requireNonNull(Objects.requireNonNull(MainActivity.getEmotionData().get(dateStr)).get(Globals.universityMinutes)).isEmpty()) {
                uniMinutes = MainActivity.getEmotionData().get(dateStr).get(Globals.universityMinutes);
            }
        }
        if (MainActivity.getEmotionData().get(dateStr).containsKey(Globals.universityIntensity)) {
            if (!Objects.requireNonNull(Objects.requireNonNull(MainActivity.getEmotionData().get(dateStr)).get(Globals.universityIntensity)).isEmpty()) {
                uniIntensity = MainActivity.getEmotionData().get(dateStr).get(Globals.universityIntensity);
            }
        }
        tv_universityDuration.setText(uniHours + "h " + uniMinutes + "min");
        tv_universityIntensity.setText(uniIntensity + "0%");

        // Display Sport
        TextView tv_sportDuration = binding.summarySport.summarySportDuration;
        TextView tv_sportIntensity = binding.summarySport.summarySportIntensityValue;
        String sportHours = "0";
        String sportMinutes = "0";
        String sportIntensity = " ";
        if (MainActivity.getEmotionData().get(dateStr).containsKey(Globals.sportHours)) {
            if (!Objects.requireNonNull(Objects.requireNonNull(MainActivity.getEmotionData().get(dateStr)).get(Globals.sportHours)).isEmpty()) {
                sportHours = MainActivity.getEmotionData().get(dateStr).get(Globals.sportHours);
            }
        }
        if (MainActivity.getEmotionData().get(dateStr).containsKey(Globals.sportMinutes)) {
            if (!Objects.requireNonNull(Objects.requireNonNull(MainActivity.getEmotionData().get(dateStr)).get(Globals.sportMinutes)).isEmpty()) {
                sportMinutes = MainActivity.getEmotionData().get(dateStr).get(Globals.sportMinutes);
            }
        }
        if (MainActivity.getEmotionData().get(dateStr).containsKey(Globals.sportIntensity)) {
            if (!Objects.requireNonNull(Objects.requireNonNull(MainActivity.getEmotionData().get(dateStr)).get(Globals.sportIntensity)).isEmpty()) {
                sportIntensity = MainActivity.getEmotionData().get(dateStr).get(Globals.sportIntensity);
            }
        }
        tv_sportDuration.setText(sportHours + "h " + sportMinutes + "min");
        tv_sportIntensity.setText(sportIntensity + "0%");


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
