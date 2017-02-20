package com.fcl.defineview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.fcl.defineview.view.EleView;

public class ElActivity extends AppCompatActivity {

    private EleView eleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_el);
        init();
    }

    public void start(View view) {
        eleView.start();
    }

    public void stop(View view) {
        eleView.stop();
    }

    private void init() {
        eleView = (EleView) findViewById(R.id.ele);
    }
}
