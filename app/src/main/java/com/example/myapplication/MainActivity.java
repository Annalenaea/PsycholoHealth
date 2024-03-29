package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.myapplication.databinding.ActivityMainBinding;
import com.google.gson.Gson;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private AppBarConfiguration appBarConfiguration;
    private static final String TAG = "MainActivity";
    public static File filesDir;
    public static Map<String,Map<String,String>> m_emotionData = new HashMap<>();
    public static Map<String,String> m_todaysData = new HashMap<>();
    public static String m_today;
    private static final DateFormat m_dateFormat = new SimpleDateFormat(Globals.dateFormat,Globals.myLocal);

    // set the emotion of today
    public void setDateEmotion(String emotion) throws IOException, ParseException {
        m_todaysData.put(Globals.emotion,emotion);
        saveData();
        HomeView.homeView.updateAnalysis();

        Date currentDate = null;
        try {
            currentDate = m_dateFormat.parse(m_today);
            assert currentDate != null;
            Log.d(TAG, String.valueOf(currentDate.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assert currentDate != null;
        HomeView.homeView.setCalendarColor(emotion,currentDate.getTime());
    }

    public static Map<String,Map<String,String>> getEmotionData(){
        return m_emotionData;
    }

    // save data in a json file
    public static void saveData() throws IOException {
        //save data in m_emotionData map
        m_emotionData.put(m_today, m_todaysData);

        // create Gson
        Gson gson = new Gson();
        String userString = gson.toJson(m_emotionData);

        // Define the File Path and its Name
        File file = new File(filesDir,Globals.backup);
        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(userString);
        bufferedWriter.close();
    }

    // load data from json file
    public static void loadData() throws IOException {
        // get and read file
        File file = new File(filesDir,Globals.backup);
        if(file.exists()) {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line = bufferedReader.readLine();
            while (line != null) {
                stringBuilder.append(line).append("\n");
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
            // This response will have Json Format String
            String response = stringBuilder.toString();
            Gson gson = new Gson();
            m_emotionData = gson.fromJson(response, m_emotionData.getClass());

            if(m_emotionData.containsKey(m_today)){
                m_todaysData = m_emotionData.get(m_today);
            }
        }
    }

    // override functions
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Calendar calendar = Calendar.getInstance();
        m_today = m_dateFormat.format(calendar.getTime());

        filesDir = getFilesDir();
        // load data from backup
        try {
            MainActivity.loadData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        com.example.myapplication.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_lightTheme) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            return true;
        }
        if (id == R.id.action_darkTheme) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            return true;
        }
        if (id == R.id.action_share) {
            // add new page where entering mail of psychologist and send + TOAST "sending"
            SendingPopup sendingPopup= new SendingPopup();
            sendingPopup.showPopupWindow(findViewById(R.id.homeView), findViewById(R.id.toolbar).getLeft(),
                    findViewById(R.id.toolbar).getBottom());
            return true;
        }
        if (id == R.id.action_help) {
            // add new page where to call psychologist
            HelpPopup helpPopup= new HelpPopup();
            helpPopup.showPopupWindow(findViewById(R.id.homeView), findViewById(R.id.toolbar).getLeft(),
                    findViewById(R.id.toolbar).getBottom());
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}