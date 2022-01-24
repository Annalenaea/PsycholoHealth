package com.example.myapplication;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.databinding.HomeViewBinding;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    private GraphView barchart;
    private static int colorRed;
    private static int colorYellow;
    public static HomeView homeView = new HomeView();
    private static int colorGreen;
    public static Map<String,Map<String,String>> m_emotionData = new HashMap<>();
    public static Date dateClick;


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
        if(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES){
            colorRed = getResources().getColor(R.color.darkred);
            colorGreen = getResources().getColor(R.color.darkgreen);
            colorYellow = getResources().getColor(R.color.darkyellow);
        }else {
            colorRed = getResources().getColor(R.color.lightred);
            colorGreen = getResources().getColor(R.color.lightgreen);
            colorYellow = getResources().getColor(R.color.lightyellow);
        }

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
                dateClick = dateClicked;
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

        barchart = binding.barchart.bar;

        m_emotionData = MainActivity.getEmotionData();

        setCalenderData();

        m_currentMonth = Integer.parseInt(new SimpleDateFormat(Globals.monthNumberFormat, Globals.myLocal).format(Calendar.getInstance().getTime()));
        try {
            updateAnalysis();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // graph indicates mental Health development
        try {
            drawGraph(Globals.stressLevel);
            drawGraph(Globals.sleepDuration);
            drawGraph(Globals.universityHours);
            drawGraph(Globals.universityIntensity);
            drawGraph(Globals.sportHours);
            drawGraph(Globals.sportIntensity);
            drawGraph(Globals.HourSocial);

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

            if(monthNumber == m_currentMonth &&  m_emotionData.get(dateString).containsKey(Globals.emotion)) {
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

        GraphView graph = barchart;
        graph.removeAllSeries();

        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[] {
                new DataPoint(1, numberOfHappy),
                new DataPoint(2, numberOfNeutral),
                new DataPoint(3, numberOfSad),
        });
        graph.addSeries(series);

        series.setValueDependentColor(data -> {
            if(data.getX()==1)
                return colorGreen;
            else if (data.getX()==2)
                return colorYellow;
            else
                return colorRed;
        });

        series.setSpacing(30);

        series.setDrawValuesOnTop(true);
        series.setValuesOnTopColor(getResources().getColor(R.color.blue));

        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(4);

        graph.getViewport().setXAxisBoundsManual(true);

        graph.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.HORIZONTAL);
        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
        staticLabelsFormatter.setHorizontalLabels(new String[] {"","Happy","Neutral", "Sad",""});
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);

        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(31);
        graph.getViewport().setYAxisBoundsManual(true);

    }


    // set the data of the calendar
    private void setCalenderData(){
        Map<String, Map<String,String>> emotionData = MainActivity.getEmotionData();
        String dateString;
        Date date = null;

        DateFormat formatter = new SimpleDateFormat(Globals.dateFormat,Globals.myLocal);
        for(int i=0;i<emotionData.keySet().size();i++){
            dateString = (String) emotionData.keySet().toArray()[i];
            Log.d(TAG,dateString);
            try {
                date = formatter.parse(dateString);
                assert date != null;
            } catch (ParseException e) {
                e.printStackTrace();
            }
            assert date != null;
            if(emotionData.get(dateString).containsKey(Globals.emotion)) {
                setCalendarColor(Objects.requireNonNull(Objects.requireNonNull(emotionData.get(dateString)).get(Globals.emotion)), date.getTime());
            }
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

    public void drawGraph(String data) throws ParseException {
        GraphView graph;
        String minutes = "";
        boolean procent = false;
        if(data == Globals.sportHours) {
            graph = binding.development.graph;
            minutes = Globals.sportMinutes;
        }else if (data == Globals.sleepDuration) {
            graph = binding.developmentsleep.graph;
        }else if(data == Globals.stressLevel) {
            graph = binding.developmentstress.graph;
            procent = true;
        }else if(data == Globals.universityIntensity) {
            graph = binding.developmentuniintensity.graph;
            procent = true;
        }else if(data == Globals.universityHours) {
            graph = binding.developmentuniduration.graph;
            minutes = Globals.universityMinutes;
        }else if(data == Globals.sportIntensity) {
            graph = binding.developmentsportintensity.graph;
            procent = true;
        }else if(data == Globals.HourSocial) {
            graph = binding.developmentsocial.graph;
            minutes = Globals.MinSocial;
        }else{
            graph = binding.development.graph;
        }
        graph.removeAllSeries();

        DataPoint[] dataSeries = new DataPoint[m_emotionData.keySet().size()];
        DateFormat formatter = new SimpleDateFormat(Globals.dateFormat,Globals.myLocal);

        String dateString;
        Date date = null;
        double value;
        boolean containsKey = false;

        if(m_emotionData.keySet().size()>0) {
            long[] dates = new long[m_emotionData.keySet().size()];
            long[] usedDates = new long[m_emotionData.keySet().size()];
            for (int i = 0; i < m_emotionData.keySet().size(); i++) {
                dateString = (String) m_emotionData.keySet().toArray()[i];
                date = formatter.parse(dateString);
                dates[i] = date.getTime();
            }
            Arrays.sort(dates);

            int k =0;
            for (int i = 0; i < dates.length; i++) {
                dateString = formatter.format(dates[i]);
                if(m_emotionData.get(dateString).containsKey(data)) {
                    if(!Objects.requireNonNull(Objects.requireNonNull(m_emotionData.get(dateString)).get(data)).isEmpty()) {
                        if(!(minutes == "") && m_emotionData.get(dateString).containsKey(minutes) && !Objects.requireNonNull(Objects.requireNonNull(m_emotionData.get(dateString)).get(minutes)).isEmpty()) {
                            value = Double.parseDouble(Objects.requireNonNull(Objects.requireNonNull(m_emotionData.get(dateString)).get(data))+"."+Objects.requireNonNull(Objects.requireNonNull(m_emotionData.get(dateString)).get(minutes)));
                        }else{
                            value = Double.parseDouble(Objects.requireNonNull(Objects.requireNonNull(m_emotionData.get(dateString)).get(data)));
                        }
                        dataSeries[k] = new DataPoint(dates[i], value);
                        usedDates[k] = dates[i];
                        containsKey = true;
                        k++;
                    }
                }else if(m_emotionData.get(dateString).containsKey(minutes)){
                    if(!Objects.requireNonNull(Objects.requireNonNull(m_emotionData.get(dateString)).get(minutes)).isEmpty()) {
                        value = Double.parseDouble("0."+Objects.requireNonNull(Objects.requireNonNull(m_emotionData.get(dateString)).get(minutes)));
                        dataSeries[k] = new DataPoint(dates[i], value);
                        usedDates[k] = dates[i];
                        containsKey = true;
                        k++;
                    }
                }
            }
            if(containsKey) { ;
                DataPoint[] dataSeries1 = Arrays.copyOf(dataSeries, k);
                long[] usedDates1 = Arrays.copyOf(usedDates, k);
                LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataSeries1);
                graph.addSeries(series);
                if(k>=3) {
                    graph.getGridLabelRenderer().setNumHorizontalLabels(3); // only 3 because of the space
                }else{
                    graph.getGridLabelRenderer().setNumHorizontalLabels(k);
                }
                // set manual x bounds to have nice steps
                graph.getViewport().setMinX(usedDates1[0]);
                graph.getViewport().setMaxX(usedDates1[k - 1]);
                graph.getViewport().setXAxisBoundsManual(true);

                // set manual y bounds to have nice steps
                graph.getViewport().setMinY(0);
                graph.getViewport().setYAxisBoundsManual(true);

                series.setColor(getResources().getColor(R.color.blue));

                StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
                staticLabelsFormatter.setDynamicLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
                if(procent) {
                    graph.getViewport().setMaxY(10);
                    staticLabelsFormatter.setVerticalLabels(new String[]{"", "25%", "50%", "75%", "100%"});
                }else{
                    graph.getViewport().setMaxY(12);
                    staticLabelsFormatter.setVerticalLabels(new String[]{"", "3h", "6h", "9h", "12h"});
                }
                graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);

                // as we use dates as labels, the human rounding to nice readable numbers
                // is not necessary
                graph.getGridLabelRenderer().setHumanRounding(false);
            }
        }

    }

    // set the color of the corresponding day in the calendar
    private int setYvalue(String emotion) {
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