package com.fcl.defineview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.fcl.defineview.view.CompleteView;

public class CompleteActivity extends AppCompatActivity {

    private CompleteView completeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete);
        completeView = (CompleteView) findViewById(R.id.com_view);
    }

    public void reset(View view) {
        completeView.reset();
    }
}
