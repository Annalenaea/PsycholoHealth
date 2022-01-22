package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.appcompat.app.AppCompatActivity;

public class HelpPopup extends AppCompatActivity {
    private static final String TAG = "Help Popup";
    private final MainActivity mainActivity = new MainActivity();
    private static Context context;


    //PopupWindow display method
    public void showPopupWindow(final View view, int xPos, int yPos) {
        Log.d(TAG, "show help popup");
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View helpPopup = inflater.inflate(R.layout.help_popup, null);
        context = view.getContext();

        //instantiate popup window
        PopupWindow popupWindow = new PopupWindow(helpPopup, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        //display the popup window
        popupWindow.setFocusable(true);
        helpPopup.setAnimation(AnimationUtils.loadAnimation(context,R.anim.popup_show));
//        popupWindow.setAnimationStyle(R.style.Animation);
        popupWindow.showAtLocation(view, Gravity.NO_GRAVITY, xPos + 70, yPos + 100);

        // onClickListener for all 3 buttons:
        Button phoneBtn = helpPopup.findViewById(R.id.helpBtn);

        phoneBtn.setOnClickListener(view1 -> {
            EditText phone = helpPopup.findViewById(R.id.phoneNumber);
            Log.d(TAG,phone.getText().toString());
            if(phone.getText().length()==0) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone.getHint()));
                context.startActivity(intent);
            }else{
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone.getText().toString()));
                context.startActivity(intent);
            }
            popupWindow.dismiss();
        });

    }
}

