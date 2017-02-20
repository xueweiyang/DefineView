package com.fcl.defineview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.fcl.defineview.view.CirclePercentView;

import java.util.Arrays;
import java.util.List;

public class CirclePercentActivity extends AppCompatActivity {

    private CirclePercentView circlePercentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_percent);
        circlePercentView = (CirclePercentView) findViewById(R.id.per_view);
        init();
    }

    private void init() {
        List<Float> datas = Arrays.asList((float)12.8, (float)4.6, (float)20.0, (float)15.4, (float)17.2, (float)10.0
                , (float)10.0, (float)4.8, (float)2.0, (float)3.2);
        circlePercentView.setPercentData(datas);
    }
}
