package com.example.myapplication;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.provider.ContactsContract;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.myapplication.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private static String TAG = "MainActivity";
    private static File filesDir;
    private static List<Integer> m_emotionAmount = new ArrayList<>();
    private static TextView happyBar;
    private static TextView neutralBar;
    private static TextView sadBar;

    // setter and getter

    public static void setEmotionAmount(int index, int value) {
        m_emotionAmount.set(index, value);
        updateAnalysis();
        try {
            saveData();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int getEmotionAmount(int index) {
        return (Integer) m_emotionAmount.get(index);
    }
    // save and load data

    public static void saveData() throws JSONException, IOException {
//        // get today's date
//        Calendar calendar = Calendar.getInstance();
//        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
//        String date = dateFormat.format(calendar.getTime());

        JSONObject jsonObject = new JSONObject();
        JSONObject emotionJson = new JSONObject();

        emotionJson.put("Happy", m_emotionAmount.get(0));
        emotionJson.put("Neutral", m_emotionAmount.get(1));
        emotionJson.put("Sad", m_emotionAmount.get(2));

        jsonObject.put("emotionAmount", emotionJson);

        // Convert JsonObject to String Format
        String userString = jsonObject.toString();

        // Define the File Path and its Name
        File file = new File(filesDir,"backup");
        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(userString);
        bufferedWriter.close();
    }

    public static void loadData() throws IOException, JSONException {
        // get and read file
        File file = new File(filesDir,"backup");
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        StringBuilder stringBuilder = new StringBuilder();
        String line = bufferedReader.readLine();
        while (line != null){
            stringBuilder.append(line).append("\n");
            line = bufferedReader.readLine();
        }
        bufferedReader.close();
        // This response will have Json Format String
        String response = stringBuilder.toString();

        // read JsonObject
        JSONObject jsonObject  = new JSONObject(response);
//        JSONObject today = (JSONObject) jsonObject.get(date);
        JSONObject emotionJson = (JSONObject) jsonObject.get("emotionAmount");
        setEmotionAmount(0, (Integer) emotionJson.get("Happy"));
        setEmotionAmount(1, (Integer) emotionJson.get("Neutral"));
        setEmotionAmount(2, (Integer) emotionJson.get("Sad"));

    }

    // override functions

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // initialize each amount of emotion with 0
        m_emotionAmount.add(0);
        m_emotionAmount.add(0);
        m_emotionAmount.add(0);


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        filesDir = getFilesDir();

        happyBar = findViewById(R.id.happyBar);
        neutralBar = findViewById(R.id.neutralBar);
        sadBar = findViewById(R.id.sadBar);

        // load data from backup
        try {
            MainActivity.loadData();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static void updateAnalysis(){
        // happy bar
        ViewGroup.LayoutParams paramsHappy = happyBar.getLayoutParams();
        paramsHappy.height = getEmotionAmount(0) * 7;
        happyBar.setLayoutParams(paramsHappy);

        // neutral bar
        ViewGroup.LayoutParams paramsNeutral = neutralBar.getLayoutParams();
        paramsNeutral.height = getEmotionAmount(1) * 7;
        neutralBar.setLayoutParams(paramsNeutral);

        // sad bar
        ViewGroup.LayoutParams paramsSad = sadBar.getLayoutParams();
        paramsSad.height = getEmotionAmount(2) * 7;
        sadBar.setLayoutParams(paramsSad);

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
        if (id == R.id.action_settings) {
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