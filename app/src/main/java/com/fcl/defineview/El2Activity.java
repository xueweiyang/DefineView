package com.fcl.defineview;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

public class El2Activity extends AppCompatActivity {

    private String TAG = "El2Activity";
    private ImageView leftImage, rightImage, orangeImage, pearImage;
    private int[] orangePos ;
    private int leftDuration = 1000, fruitDuration = 5000;
    /**
     * 水果上抛的初始速度和角度
     */
    private float fruitSpeed = (float) 100;
    private float fruitDegree = (float) Math.toRadians(50);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_el2);
        init();
//        startanim();
    }

    private void startanim() {
        leftImage.setPivotX(0);
        leftImage.setPivotY(leftImage.getHeight()/2);
        ObjectAnimator leftAnim = ObjectAnimator.ofFloat(leftImage, "rotation", 0f, -225f);
        leftAnim.setDuration(leftDuration);
        leftAnim.start();
        leftAnim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
//                throwAnim();
                paowuxian((float)Math.toRadians(50), 100, orangeImage);
                paowuxian((float)Math.toRadians(60), 80, pearImage);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        rightImage.setPivotX(rightImage.getWidth());
        rightImage.setPivotY(rightImage.getHeight()/2);
        ObjectAnimator rightAnim = ObjectAnimator.ofFloat(rightImage, "rotation", 0f, 225f);
        rightAnim.setDuration(leftDuration);
        rightAnim.start();
    }

    /**
     * 抛水果的动画
     */
    private void throwAnim() {
        int[] pos = new int[2];
        orangeImage.getLocationInWindow(pos);
        Log.e(TAG, "orangepos:"+orangePos[1]);
//        ObjectAnimator orangeAnim = ObjectAnimator.ofFloat(orangeImage, "translationY", (float)(orangePos[1]/3.0), (float) (orangePos[1]/3.0-100));
        ObjectAnimator orangeTraY = ObjectAnimator.ofFloat(orangeImage, "translationY", 0, -200); //平移以当前位置为基准
        ObjectAnimator orangeAlpha = ObjectAnimator.ofFloat(orangeImage, "alpha", 1, 0);
        AnimatorSet orangeAnim = new AnimatorSet();
        orangeAnim.play(orangeTraY).with(orangeAlpha);
        orangeAnim.setDuration(fruitDuration);
        orangeAnim.start();
    }

    private void paowuxian(final float fruitDegree, final float fruitSpeed, final View view) {
        ValueAnimator orangeAnim = new ValueAnimator();
        orangeAnim.setObjectValues(new PointF(orangePos[0], orangePos[1]));
        orangeAnim.setInterpolator(new LinearInterpolator());
        orangeAnim.setEvaluator(new TypeEvaluator<PointF>(){

            @Override
            public PointF evaluate(float time, PointF pointF, PointF t1) {
                PointF point = new PointF();
                float x = (float) (fruitSpeed * time * fruitDuration * Math.cos(fruitDegree) / 1000);
                float y = (float) (x * Math.tan(fruitDegree) - 0.5 * 9.8 * x * x / ((fruitSpeed * Math.cos(fruitDegree)) * (fruitSpeed * Math.cos(fruitDegree))));
                float y1 = (float) (x * Math.tan(fruitDegree));
                point.x = x + orangePos[0];
                point.y = (float) (orangePos[1] - y);
                Log.e(TAG, String.format("抛物线:x:%f,y:%f,rate:%f", point.x, point.y, time));
                return point;
            }
        });
        ObjectAnimator orangeAlpha = ObjectAnimator.ofFloat(view, "alpha", 1, 0);
        AnimatorSet orangeSet = new AnimatorSet();
        orangeSet.play(orangeAnim).with(orangeAlpha);
        orangeSet.setDuration(3000);
        orangeSet.setDuration(fruitDuration);
        orangeSet.start();
        orangeAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                PointF pointF = (PointF) valueAnimator.getAnimatedValue();
                view.setX(pointF.x);
                view.setY(pointF.y);
            }
        });
    }

    private void init() {
        leftImage = (ImageView) findViewById(R.id.left_img);
        rightImage = (ImageView) findViewById(R.id.right_img);
        orangeImage = (ImageView) findViewById(R.id.orange_img);
        orangePos = new int[2];
        pearImage = (ImageView) findViewById(R.id.pear_img);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        orangeImage.getLocationOnScreen(orangePos);
        orangePos[1] = 800;
//        orangeImage.getLocationInWindow(orangePos);
        Log.e(TAG, String.format("locx:%d,locy:%d,top:%d,", orangePos[0], orangePos[1], orangeImage.getTop()));
    }

    public void start(View view) {
        startanim();
    }

    public void stop(View view) {

    }
}
