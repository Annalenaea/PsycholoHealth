package com.example.myapplication;

import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.text.ParseException;

public class SendingPopup extends AppCompatActivity {
    private static final String TAG = "Emotion Selection";
    private final MainActivity mainActivity = new MainActivity();


    //PopupWindow display method
    public void showPopupWindow(final View view, int xPos, int yPos) {
        Log.d(TAG,"show sendning popup");
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View sendingPopup = inflater.inflate(R.layout.sending_popup, null);

        //instantiate popup window
        PopupWindow popupWindow = new PopupWindow(sendingPopup, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        //display the popup window
        popupWindow.setFocusable(true);
        popupWindow.showAtLocation(view, Gravity.NO_GRAVITY, xPos + 70, yPos + 100);

        // onClickListener for all 3 buttons:
        Button sendBtn = sendingPopup.findViewById(R.id.send);

        sendBtn.setOnClickListener(view1 -> {
            Toast toast = Toast.makeText(view.getContext(), "Sending...", Toast.LENGTH_SHORT);
            toast.show();
            popupWindow.dismiss();  //5 seconds

        });

    }

}