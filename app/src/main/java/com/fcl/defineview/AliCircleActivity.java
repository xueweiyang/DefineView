package com.fcl.defineview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.fcl.defineview.view.AliCircleView;

public class AliCircleActivity extends AppCompatActivity {

    private AliCircleView aliCircleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ali_circle);
        init();
    }

    private void init() {
        aliCircleView = (AliCircleView) findViewById(R.id.circle_view);
    }


    public void start(View view) {
        aliCircleView.start();
    }

    public void stop(View view) {
        aliCircleView.stop();
    }

    public void reset(View view) {
        aliCircleView.reset();
    }
}
