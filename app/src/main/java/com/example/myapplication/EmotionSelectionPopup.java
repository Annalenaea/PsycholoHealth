package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.databinding.EmotionSelectionPopupBinding;

public class EmotionSelectionPopup extends AppCompatActivity {

    private EmotionSelectionPopupBinding binding;
    private static String TAG = "Emotion Selection Popup Activity";


    //PopupWindow display method
    public void showPopupWindow(final View view) {
        Log.d(TAG,"show emotions popup");
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View emotionPopup = inflater.inflate(R.layout.emotion_selection_popup, null);

        //instantiate popup window
        PopupWindow popupWindow = new PopupWindow(emotionPopup, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        //display the popup window
        popupWindow.showAtLocation(view, Gravity.CENTER_HORIZONTAL, 0, 0);

        ImageButton btnHappy = (ImageButton) emotionPopup.findViewById(R.id.btnHappy);
        btnHappy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"first button clicked");
                // for each week one Json file?
                // @todo: open pop up: "Do you want to answer some questions regarding your current emotion?"
                // @todo: if yes: open new page with questions
//                NavHostFragment.findNavController(EmotionSelectionPopup.this)
//                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
    }

}