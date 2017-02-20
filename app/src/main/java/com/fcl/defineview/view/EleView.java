package com.fcl.defineview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import com.fcl.defineview.R;

/**
 * Created by Administrator on 2017/1/23.
 * 饿了么下拉刷新的动画
 */

public class EleView extends View {

    private String TAG = "EleView";
    /**
     * view宽高
     */
    private int mWidth, mHeight;
    private Bitmap mBottomBitmap, mLeftBitmap, mRightBitmap;
    private int mLeftDegree = 0, mRightDegree = -180;
    private boolean mRunThread = true;

    public EleView(Context context) {
        this(context, null);
    }

    public EleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mBottomBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.zk);
        mLeftBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.zh);
        mRightBitmap = rotateBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.zi), -180);
        DisplayMetrics dm = new DisplayMetrics();
        dm = getResources().getDisplayMetrics();
        float density = dm.density;
        Log.e(TAG, "density:"+density);
        thread();
    }

    private void thread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mRunThread) {
                    mLeftDegree -= 3;
//                    if (mLeftDegree <= -360) {
//                        mRunThread = false;
//                    }
                    mRightDegree += 3;
                    try {
                        Thread.sleep(80);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    postInvalidate();
                }


            }
        }).start();
    }

    public void stop() {
        mRunThread = false;
    }

    public void start() {
        mRunThread = true;
        thread();
//        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        canvas.drawBitmap(mBottomBitmap, mWidth/2-64, mHeight/2-50, null);
        canvas.save();
        canvas.translate(mWidth/2-64, mHeight/2-50);
        canvas.rotate(mLeftDegree);
        canvas.save();
        canvas.drawBitmap(mLeftBitmap, 0, 0, null);
        canvas.restore();
        canvas.restore();

        canvas.save();
        canvas.translate(mWidth/2+64, mHeight/2-40);
        canvas.rotate(mRightDegree);
        canvas.save();
        canvas.drawBitmap(mRightBitmap, 0, 0, null);
        canvas.restore();
        canvas.restore();


    }

    private Bitmap rotateBitmap(Bitmap bitmap, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap returnBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);
        return returnBitmap;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        mWidth = mHeight = Math.min(width, height);
    }
}
