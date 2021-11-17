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
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.w3c.dom.Text;

import java.io.IOException;

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

        // y Axis (to get later maximum height of diagram / 31 days)
        TextView yAxis = view.findViewById(R.id.yAxis);

        ImageButton btnHappy = emotionPopup.findViewById(R.id.btnHappy);
        btnHappy.setOnClickListener(view1 -> {
            Log.d(TAG,"happy button clicked");

            //adjust bar chart for happiness
            //@todo: add only once a day
            MainActivity.setEmotionAmount(0, MainActivity.getEmotionAmount(0)+1);

            // wait some secs until close popup
            Handler handler = new Handler();
            handler.postDelayed(() -> popupWindow.dismiss(), 1000);   //5 seconds
        });

        btnHappy.setOnLongClickListener(view1 -> {
            Log.d(TAG,"happy button long clicked");
            //adjust bar chart for happiness
            //@todo: add only once a day
            MainActivity.setEmotionAmount(0,MainActivity.getEmotionAmount(0)+1);

            // @todo: open happy emotion questionnaire page
            return true;
        });

        ImageButton btnNeutral = emotionPopup.findViewById(R.id.btnNeutral);
        btnNeutral.setOnClickListener(view1 -> {
            Log.d(TAG,"neutral button clicked");

            //adjust bar chart for neutral
            //@todo: add only once a day
            MainActivity.setEmotionAmount(1,MainActivity.getEmotionAmount(1)+1);


            // wait short time until popup window closes
            Handler handler = new Handler();
            handler.postDelayed(() -> popupWindow.dismiss(), 1000);   //5 seconds
        });

        btnNeutral.setOnLongClickListener(view1 -> {
            Log.d(TAG,"neutral button long clicked");
            //adjust bar chart for neutral
            //@todo: add only once a day
            MainActivity.setEmotionAmount(1,MainActivity.getEmotionAmount(1)+1);

            // @todo: open neutral emotion questionnaire page
            return true;
        });

        ImageButton btnWorried = emotionPopup.findViewById(R.id.btnWorried);
        btnWorried.setOnClickListener(view1 -> {
            Log.d(TAG,"worried button clicked");

            //adjust bar chart for sad
            //@todo: add only once a day
            MainActivity.setEmotionAmount(2,MainActivity.getEmotionAmount(2)+1);


            Handler handler = new Handler();
            handler.postDelayed(() -> popupWindow.dismiss(), 1000);   //5 seconds
        });

        btnWorried.setOnLongClickListener(view1 -> {
            Log.d(TAG,"worried button long clicked");
            //adjust bar chart for sad
            //@todo: add only once a day
            MainActivity.setEmotionAmount(2,MainActivity.getEmotionAmount(2)+1);

            // @todo: open worried emotion questionnaire page
            return true;
        });
    }

}