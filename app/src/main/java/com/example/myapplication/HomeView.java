package com.example.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.databinding.HomeViewBinding;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HomeView extends Fragment {

    private static final String TAG = "Home View Activity";
    private static CompactCalendarView m_calendarView = null;
    private static int m_currentMonth = Integer.parseInt(new SimpleDateFormat(Globals.monthNumberFormat, Globals.myLocal).format(Calendar.getInstance().getTime()));
    private HomeViewBinding binding;
    private NavController homeNavi;
    private TextView happyBar;
    private TextView neutralBar;
    private TextView sadBar;
    private static int colorRed;
    private static int colorYellow;
    public static HomeView homeView = new HomeView();
    private static int colorGreen;
    private static HashMap<String,Map<String,String>> m_emotionData = new HashMap<>();


    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        Log.d("debug","onCreateView");
        homeNavi = NavHostFragment.findNavController(HomeView.this);
        homeView = HomeView.this;

        binding = HomeViewBinding.inflate(inflater, container, false);
        // open activity questionnaire if addActivityButton is clicked
        binding.addActivity.setOnClickListener(view -> homeNavi.navigate(R.id.action_HomeFragment_to_ActivityFragment));

         colorRed = getResources().getColor(R.color.red);
         colorGreen = getResources().getColor(R.color.green);
         colorYellow = getResources().getColor(R.color.yellow);

        // open add emotion popup
        binding.addEmotion.setOnClickListener(view -> {
            EmotionSelectionPopup emotionSelectionPopup= new EmotionSelectionPopup();
            emotionSelectionPopup.showPopupWindow(requireView().findViewById(R.id.homeView),
                    binding.addEmotion.getLeft(),
                    binding.addEmotion.getTop());
        });


        m_calendarView = binding.calendar;
        binding.calendar.setUseThreeLetterAbbreviation(true);
        binding.calendar.shouldDrawIndicatorsBelowSelectedDays(true);


        // display current month at the top of the calendar
        DateFormat formatter = new SimpleDateFormat(Globals.monthFormat,Globals.myLocal);
        Calendar calendar = Calendar.getInstance();
        binding.month.setText(formatter.format(calendar.getTime()));

        binding.calendar.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                Log.d(TAG, "Day was clicked: " + dateClicked);
                // open summary of this day
                homeNavi.navigate(R.id.action_HomeFragment_to_SummaryFragment);
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                Log.d(TAG, "Month was scrolled to: " + firstDayOfNewMonth);
                // update month above calendar
                DateFormat formatter = new SimpleDateFormat(Globals.monthFormat,Globals.myLocal);
                binding.month.setText(formatter.format(firstDayOfNewMonth));
                // load month data for analysis
                DateFormat monthFormatter = new SimpleDateFormat(Globals.monthNumberFormat,Globals.myLocal);
                m_currentMonth  =   Integer.parseInt(monthFormatter.format(firstDayOfNewMonth));
                try {
                    updateAnalysis();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        });

        setCalenderData();

        happyBar = binding.barchart.happyBar;
        neutralBar = binding.barchart.neutralBar;
        sadBar = binding.barchart.sadBar;

        m_emotionData = MainActivity.getEmotionData();

        try {
            updateAnalysis();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // graph indicates mental Health development
        try {
            drawGraph();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return binding.getRoot();

    }

    // update the analysis view
    public void updateAnalysis() throws ParseException {
        int numberOfHappy=0;
        int numberOfNeutral=0;
        int numberOfSad=0;
        for(int i=0;i<m_emotionData.keySet().size();i++){
            String dateString = (String) m_emotionData.keySet().toArray()[i];
            DateFormat formatter = new SimpleDateFormat(Globals.dateFormat,Globals.myLocal);
            DateFormat monthFormatter = new SimpleDateFormat(Globals.monthNumberFormat,Globals.myLocal);

            Date date = formatter.parse(dateString);
            assert date != null;
            int monthNumber  =   Integer.parseInt(monthFormatter.format(date));

            if(monthNumber == m_currentMonth) {
                String emotion = Objects.requireNonNull(m_emotionData.get(dateString)).get(Globals.emotion);
                switch (Objects.requireNonNull(emotion)) {
                    case Globals.happy:
                        numberOfHappy++;
                        break;
                    case Globals.neutral:
                        numberOfNeutral++;
                        break;
                    case Globals.sad:
                        numberOfSad++;
                        break;
                    default:
                        break;
                }
            }
        }

        // happy bar
        ViewGroup.LayoutParams paramsHappy = happyBar.getLayoutParams();
        paramsHappy.height = (numberOfHappy+1) * 7;
        happyBar.setLayoutParams(paramsHappy);

        // neutral bar
        ViewGroup.LayoutParams paramsNeutral = neutralBar.getLayoutParams();
        paramsNeutral.height = (numberOfNeutral+1) * 7;
        neutralBar.setLayoutParams(paramsNeutral);

        // sad bar
        ViewGroup.LayoutParams paramsSad = sadBar.getLayoutParams();
        paramsSad.height = (numberOfSad+1) * 7;
        sadBar.setLayoutParams(paramsSad);

    }


    // set the data of the calendar
    private void setCalenderData(){
        HashMap<String, Map<String,String>> emotionData = MainActivity.getEmotionData();
        String dateString;
        Date date = null;

        DateFormat formatter = new SimpleDateFormat(Globals.dateFormat,Globals.myLocal);
        for(int i=0;i<emotionData.keySet().size();i++){
            dateString = (String) emotionData.keySet().toArray()[i];
            Log.d(TAG,dateString);
            try {
                date = formatter.parse(dateString);
                assert date != null;
                Log.d(TAG, String.valueOf(date.getTime()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            assert date != null;
            setCalendarColor(Objects.requireNonNull(Objects.requireNonNull(emotionData.get(dateString)).get(Globals.emotion)),date.getTime());
        }
    }

    // set the color of the corresponding day in the calendar
    public void setCalendarColor(String emotion, long date) {
        int color;
        switch(emotion){
            case Globals.happy:
                color = colorGreen;
                break;
            case Globals.neutral:
                color = colorYellow;
                break;
            case Globals.sad:
                Log.d("hi","@color/red");
                color =  colorRed;
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

    // opens the emotion questionnaire page
    public void openEmotionQuestionnaire(){
        homeNavi.navigate(R.id.action_HomeFragment_to_EmotionFragment);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("debug","onViewCreated");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void drawGraph() throws ParseException {
        GraphView graph = binding.development.graph;
        graph.removeAllSeries();

        DataPoint[] dataSeries = new DataPoint[m_emotionData.keySet().size()];
        DateFormat formatter = new SimpleDateFormat(Globals.dateFormat,Globals.myLocal);

        String dateString;
        Date date = null;
        int value;

        if(m_emotionData.keySet().size()>0) {
            long[] dates = new long[m_emotionData.keySet().size()];
            for (int i = 0; i < m_emotionData.keySet().size(); i++) {
                dateString = (String) m_emotionData.keySet().toArray()[i];
                date = formatter.parse(dateString);
                dates[i] = date.getTime();
            }
            Arrays.sort(dates);

            for (int i = 0; i < dates.length; i++) {
                dateString = formatter.format(dates[i]);
                value = setYvalue(Objects.requireNonNull(Objects.requireNonNull(m_emotionData.get(dateString)).get(Globals.emotion)), dates[i]);
                dataSeries[i] = new DataPoint(dates[i], value);
            }
            LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataSeries);
            graph.addSeries(series);
            graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
            graph.getGridLabelRenderer().setNumHorizontalLabels(3); // only 4 because of the space
            // set manual x bounds to have nice steps
            graph.getViewport().setMinX(dates[0]);

            graph.getViewport().setMaxX(dates[dates.length - 1]);
            graph.getViewport().setXAxisBoundsManual(true);

            // as we use dates as labels, the human rounding to nice readable numbers
            // is not necessary
            graph.getGridLabelRenderer().setHumanRounding(false);
        }

    }

    // set the color of the corresponding day in the calendar
    private int setYvalue(String emotion, long date) {
        int value = -1;
        switch(emotion){
            case Globals.happy:
                value = 10;
                break;
            case Globals.neutral:
                value = 5;
                break;
            default:
                value = 0;
                break;
        }
        return value;
    }

}