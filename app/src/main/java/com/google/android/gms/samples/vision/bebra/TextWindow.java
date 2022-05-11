package com.google.android.gms.samples.vision.bebra;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class TextWindow extends AppCompatActivity {
    public static String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_window);
        TextView some = findViewById(R.id.textView);
        some.setText(text);
    }
}