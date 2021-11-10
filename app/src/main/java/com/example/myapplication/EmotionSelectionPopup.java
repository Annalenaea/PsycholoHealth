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
        popupWindow.showAtLocation(view, Gravity.NO_GRAVITY, (int) (xPos*0.27), (int) (yPos*0.93));

        ImageButton btnHappy = emotionPopup.findViewById(R.id.btnHappy);
        btnHappy.setOnClickListener(view1 -> {
            Log.d(TAG,"happy button clicked");
//            btnHappy.setImageResource(R.drawable.ic_happy_selected);

            // wait some secs until close popup
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    popupWindow.dismiss();
                }
            }, 1000);   //5 seconds

            // for each week one Json file?
            // @todo: open pop up: "Do you want to answer some questions regarding your current emotion?"
            // @todo: if yes: open new page with questions
//                NavHostFragment.findNavController(EmotionSelectionPopup.this)
//                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
        });

        ImageButton btnTired = emotionPopup.findViewById(R.id.btnTired);
        btnTired.setOnClickListener(view1 -> {
            Log.d(TAG,"tired button clicked");
            popupWindow.dismiss();
        });

        ImageButton btnAngry = emotionPopup.findViewById(R.id.btnAngry);
        btnAngry.setOnClickListener(view1 -> {
            Log.d(TAG,"angry button clicked");
            popupWindow.dismiss();
        });
        ImageButton btnWorried = emotionPopup.findViewById(R.id.btnWorried);
        btnWorried.setOnClickListener(view1 -> {
            Log.d(TAG,"worried button clicked");
            popupWindow.dismiss();
        });

        ImageButton btnNeutral = emotionPopup.findViewById(R.id.btnNeutral);
        btnNeutral.setOnClickListener(view1 -> {
            Log.d(TAG,"neutral button clicked");
            popupWindow.dismiss();
        });
    }

}