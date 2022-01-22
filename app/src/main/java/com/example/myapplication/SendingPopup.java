package com.example.myapplication;

import android.annotation.SuppressLint;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static androidx.core.content.FileProvider.getUriForFile;

public class SendingPopup extends AppCompatActivity {
    private static final String TAG = "Emotion Selection";
    private final MainActivity mainActivity = new MainActivity();
    private static Context context;


    //PopupWindow display method
    @SuppressLint("ResourceType")
    public void showPopupWindow(final View view, int xPos, int yPos) {
        Log.d(TAG,"show sendning popup");
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View sendingPopup = inflater.inflate(R.layout.sending_popup, null);
        context = view.getContext();

        //instantiate popup window
        PopupWindow popupWindow = new PopupWindow(sendingPopup, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        //display the popup window
        sendingPopup.setAnimation(AnimationUtils.loadAnimation(context,R.anim.popup_show));
        popupWindow.setFocusable(true);
        popupWindow.showAtLocation(view, Gravity.NO_GRAVITY, xPos + 70, yPos + 100);

        Button sendBtn = sendingPopup.findViewById(R.id.send);

        sendBtn.setOnClickListener(view1 -> {
            EditText textMail = sendingPopup.findViewById(R.id.mail);
            String[] address = {textMail.getText().toString()};
            try {
                composeEmail(address);
            } catch (IOException e) {
                e.printStackTrace();
            }
//            sendingPopup.startAnimation(AnimationUtils.loadAnimation(context,R.anim.popup_hide));
            popupWindow.dismiss();
        });

    }

    private void composeEmail(String[] address) throws IOException {
        String subject = "PsycholoHealth";
        String text = "Dear Dr. Meyer, \n in the attachement, you can find my mental health data. \n Best regards";
        File newFile = new File(MainActivity.filesDir,Globals.backup);
        File sendFile = new File(MainActivity.filesDir,"psycholohealth_data");
        copy(newFile,sendFile);

        Uri uri = getUriForFile(context, "com.mydomain.fileprovider", sendFile);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_EMAIL, address);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        intent.putExtra(Intent.EXTRA_STREAM, uri);

        context.startActivity(Intent.createChooser(intent, "Choose an email client"));
    }

    public static void copy(File src, File dst) throws IOException {
        InputStream in = new FileInputStream(src);
        try {
            OutputStream out = new FileOutputStream(dst);
            try {
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            } finally {
                out.close();
            }
        } finally {
            in.close();
        }
    }

}