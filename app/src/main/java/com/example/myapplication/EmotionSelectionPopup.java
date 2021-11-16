package com.example.myapplication;

import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EmotionSelectionPopup extends AppCompatActivity {
    private static String TAG = "Emotion Selection Popup Activity";


    //PopupWindow display method
    public void showPopupWindow(final View view, int xPos, int yPos) {
        Log.d(TAG,"show emotions popup");
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View emotionPopup = inflater.inflate(R.layout.emotion_selection_popup, null);

        //instantiate popup window
        PopupWindow popupWindow = new PopupWindow(emotionPopup, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        //display the popup window
        // @todo: add animation
        popupWindow.showAtLocation(view, Gravity.NO_GRAVITY, xPos, yPos);

        ImageButton btnHappy = emotionPopup.findViewById(R.id.btnHappy);
        btnHappy.setOnClickListener(view1 -> {
            Log.d(TAG,"happy button clicked");
            // @todo: save emotion in Json File
            // wait some secs until close popup
            Handler handler = new Handler();
            handler.postDelayed(() -> popupWindow.dismiss(), 1000);   //5 seconds
        });

        btnHappy.setOnLongClickListener(view1 -> {
            Log.d(TAG,"happy button long clicked");
            // @todo: save emotion in Json File
            // @todo: open happy emotion questionnaire page
            return true;
        });

        ImageButton btnNeutral = emotionPopup.findViewById(R.id.btnNeutral);
        btnNeutral.setOnClickListener(view1 -> {
            Log.d(TAG,"neutral button clicked");
            Handler handler = new Handler();
            handler.postDelayed(() -> popupWindow.dismiss(), 1000);   //5 seconds
        });

        btnNeutral.setOnLongClickListener(view1 -> {
            Log.d(TAG,"neutral button long clicked");
            // @todo: save emotion in Json File
            // @todo: open neutral emotion questionnaire page
            return true;
        });

        ImageButton btnWorried = emotionPopup.findViewById(R.id.btnWorried);
        btnWorried.setOnClickListener(view1 -> {
            Log.d(TAG,"worried button clicked");
            Handler handler = new Handler();
            handler.postDelayed(() -> popupWindow.dismiss(), 1000);   //5 seconds
        });

        btnWorried.setOnLongClickListener(view1 -> {
            Log.d(TAG,"worried button long clicked");
            // @todo: save emotion in Json File
            // @todo: open worried emotion questionnaire page
            return true;
        });
    }

}