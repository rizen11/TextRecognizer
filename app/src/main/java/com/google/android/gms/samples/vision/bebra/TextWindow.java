package com.google.android.gms.samples.vision.bebra;

import static com.google.android.gms.samples.vision.bebra.OcrGraphic.aye;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class TextWindow extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_window);
        TextView some = findViewById(R.id.textView);
        some.setText(aye);
    }
}