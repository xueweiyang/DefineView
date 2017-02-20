package com.fcl.defineview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;

import com.fcl.defineview.view.CircleProgressRotateView;
import com.fcl.defineview.view.WaveLoadingView;

public class WaveLoadingActivity extends AppCompatActivity {

    private SeekBar seekBar;
    private WaveLoadingView view;
    private com.fcl.defineview.WaveLoadingView view2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wave_loading);
        init();
    }

    private void init() {
        seekBar = (SeekBar) findViewById(R.id.seek_bar);
        view = (WaveLoadingView) findViewById(R.id.view);
        view2 = (com.fcl.defineview.WaveLoadingView) findViewById(R.id.view2);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                view.setRate((float) (i/100.0));
                view2.setPercent(i);
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
