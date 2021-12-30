package com.example.myapplication;

import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.text.ParseException;

public class EmotionSelectionPopup extends AppCompatActivity {
    private static final String TAG = "Emotion Selection";
    private final MainActivity mainActivity = new MainActivity();


    //PopupWindow display method
    public void showPopupWindow(final View view, int xPos, int yPos) {
        Log.d(TAG,"show emotions popup");
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View emotionPopup = inflater.inflate(R.layout.emotion_selection_popup, null);

        //instantiate popup window
        PopupWindow popupWindow = new PopupWindow(emotionPopup, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        //display the popup window
        // @todo: add animation
        popupWindow.showAtLocation(view, Gravity.NO_GRAVITY, xPos - 50, yPos + 70);

        Handler handler = new Handler();
        handler.postDelayed(popupWindow::dismiss, Globals.dismissDelayNotClicked);   //5 seconds

        // onClickListener for all 3 buttons:
        ImageButton btnHappy = emotionPopup.findViewById(R.id.btnHappy);
        btnHappy.setOnClickListener(view1 -> {
            Log.d(TAG,"happy button clicked");
            try {
                mainActivity.setDateEmotion(Globals.happy);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            // wait some secs until close popup
            handler.postDelayed(popupWindow::dismiss, Globals.dismissDelay);   //5 seconds
        });

        // onLongClickListener for opening questionaire page
        btnHappy.setOnLongClickListener(view1 -> {
            Log.d(TAG,"happy button long clicked");
            try {
                mainActivity.setDateEmotion(Globals.happy);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            // emotion questionnaire page
            HomeView.homeView.openEmotionQuestionnaire();
            popupWindow.dismiss();
            return true;
        });

        ImageButton btnNeutral = emotionPopup.findViewById(R.id.btnNeutral);
        btnNeutral.setOnClickListener(view1 -> {
            Log.d(TAG,"neutral button clicked");
            try {
                mainActivity.setDateEmotion(Globals.neutral);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            // wait short time until popup window closes
            handler.postDelayed(popupWindow::dismiss, Globals.dismissDelay);   //5 seconds
        });

        btnNeutral.setOnLongClickListener(view1 -> {
            Log.d(TAG,"neutral button long clicked");
            try {
                mainActivity.setDateEmotion(Globals.neutral);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            // emotion questionnaire page
            HomeView.homeView.openEmotionQuestionnaire();
            popupWindow.dismiss();
            return true;
        });

        ImageButton btnWorried = emotionPopup.findViewById(R.id.btnWorried);
        btnWorried.setOnClickListener(view1 -> {
            Log.d(TAG,"worried button clicked");
            try {
                mainActivity.setDateEmotion(Globals.sad);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            handler.postDelayed(popupWindow::dismiss, Globals.dismissDelay);   //5 seconds
        });

        btnWorried.setOnLongClickListener(view1 -> {
            Log.d(TAG,"worried button long clicked");
            try {
                mainActivity.setDateEmotion(Globals.sad);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            // emotion questionnaire page
            HomeView.homeView.openEmotionQuestionnaire();
            popupWindow.dismiss();
            return true;
        });
    }

}