package com.fcl.defineview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startLol(View view) {
        startActivity(new Intent(this, LolActivity.class));
    }

    public void startEl(View view) {
        startActivity(new Intent(this, El2Activity.class));
    }

    public void startAliCircle(View view) {
        startActivity(new Intent(this, AliCircleActivity.class));
    }

    public void complete(View view) {
        startActivity(new Intent(this, CompleteActivity.class));
    }

    public void circlePercent(View view) {
        startActivity(new Intent(this, CirclePercentActivity.class));
    }

    public void circlePer(View view) {
        startActivity(new Intent(this, CircleProgressActivity.class));
    }

    public void circlePerRotate(View view) {
        startActivity(new Intent(this, CircleProgressRotateActivity.class));
    }

    public void panel(View view) {
        startActivity(new Intent(this, PanelActivity.class));
    }

    public void wavePer(View view) {
        startActivity(new Intent(this, WaveLoadingActivity.class));
    }
}
