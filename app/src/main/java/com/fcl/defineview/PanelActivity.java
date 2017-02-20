package com.fcl.defineview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;

import com.fcl.defineview.view.PanelView;

public class PanelActivity extends AppCompatActivity {

    private SeekBar seekBar;
    private PanelView panelView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel);
        init();
    }

    private void init() {
        seekBar = (SeekBar) findViewById(R.id.seek_bar);
        panelView = (PanelView) findViewById(R.id.panel_view);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                panelView.setmCurPer((float) (i/100.0));
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
