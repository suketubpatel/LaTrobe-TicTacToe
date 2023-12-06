package com.example.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class InfoActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnClose;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        btnClose = findViewById(R.id.btnClose);
        btnClose.setOnClickListener(this);

        textView = findViewById(R.id.textViewInfo);

        textView.setText("Author: SUKETU PATEL\nStudent ID: " +
                "20224262\n\nCreated for assessment 2 for Mobile Application" +
                " Development in Year 2 at LaTrobe University Melbourne");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnClose: {       // Close info activity
                Intent goHome = new Intent(InfoActivity.this, MainActivity.class);
                startActivity(goHome);
                finish();
                System.exit(0);     // end java code and exit
                break;
            }
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}
