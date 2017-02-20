package com.fcl.defineview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;

import com.fcl.defineview.view.CircleProgressRotateView;
import com.fcl.defineview.view.CircleProgressView;

public class CircleProgressRotateActivity extends AppCompatActivity {

    private SeekBar seekBar;
    private CircleProgressRotateView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_progress_rotate);
        init();
    }


    private void init() {
        seekBar = (SeekBar) findViewById(R.id.seek_bar);
        view = (CircleProgressRotateView) findViewById(R.id.view);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                view.setmCurPer((float) (i/100.0));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
