package com.google.android.gms.samples.vision.bebra;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class TextWindow extends AppCompatActivity {
    public static String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_window);
        TextView some = findViewById(R.id.textView);
        some.setText(text);
    }
    public void onClickCopyButton(View view){
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Copied Text", text);
        clipboard.setPrimaryClip(clip);
        Toast toast = Toast.makeText(TextWindow.this,
                "Text copied to clipboard", Toast.LENGTH_SHORT);
        toast.show();
    }
    public void onClickExitButton(View view){
        Intent i = new Intent(TextWindow.this, OcrCaptureActivity.class);
        startActivity(i);
    }
}