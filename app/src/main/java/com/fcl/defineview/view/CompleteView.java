package com.fcl.defineview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2017/1/25.
 */

public class CompleteView extends View {

    private String TAG = "CompleteView";

    /**
     * view宽高
     */
    private int mWidth, mHeight;

    private Paint mDefaultPaint;

    /**
     * 中间折线的三个点
     */
    private PointF mFirstPoint, mSecPoint, mThiPoint;
    /**
     * 竖直线的点
     */
    private PointF mVerFirstPoint, mVerSecPoint;
    /**
     * 竖直线变成点的宽度
     */
    private int mCircleWidth = 8;
    /**
     * 距离间隔和时间间隔
     */
    private int mSpaceDur = 3, mTimeDur = 50;
    /**
     * 圆圈的中心点
     */
    private PointF mCirclePoint;
    /**
     * 外围大圆
     */
    private Paint mBigCirclePaint, mArcPaint;

    private int mSweepAngle = 0;

    private boolean mRunThread = true, mRunThread2 = true, mRunThread3 = true, mDrawCircle = false, mRunThread4 = true;

    public CompleteView(Context context) {
        this(context, null);
    }

    public CompleteView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CompleteView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mDefaultPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDefaultPaint.setColor(Color.GREEN);
        mDefaultPaint.setStrokeWidth(5);
        mBigCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBigCirclePaint.setColor(Color.WHITE);
        mBigCirclePaint.setStrokeWidth(5);
        mBigCirclePaint.setStyle(Paint.Style.STROKE);
        mArcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mArcPaint.setColor(Color.GREEN);
        mArcPaint.setStrokeWidth(5);
        mArcPaint.setStyle(Paint.Style.STROKE);
//        threadStart();
    }

    /**
     * 竖直线动画
     */
    private void threadStart() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mRunThread) {
                    mVerSecPoint.y = mVerSecPoint.y - mSpaceDur;
                    mVerFirstPoint.y = mVerFirstPoint.y + mSpaceDur;
                    if (mVerSecPoint.y <= mHeight / 2) {
                        mVerSecPoint.y = mHeight / 2;
                        mVerFirstPoint.y = mHeight / 2;
                        mRunThread = false;
                        mDrawCircle = true;
                        threadStart2();
                    }
                    try {
                        Thread.sleep(mTimeDur);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    postInvalidate();
                }
            }
        }).start();
    }

    /**
     * 折线动画
     */
    private void threadStart2() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mRunThread2) {
                    mSecPoint.y = mSecPoint.y - mSpaceDur;
                    if (mSecPoint.y <= mHeight / 2 + mCircleWidth / 2) {
                        mSecPoint.y = mHeight / 2 + mCircleWidth / 2;
                        mRunThread2 = false;
                        threadStart3();
                    }
                    try {
                        Thread.sleep(mTimeDur / 3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    postInvalidate();
                }
            }
        }).start();
    }

    /**
     * 圆圈上移动画
     */
    private void threadStart3() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mRunThread3) {
                    mCirclePoint.y = mCirclePoint.y - mSpaceDur*2;
                    if (mCirclePoint.y <= 10) {
                        mCirclePoint.y = 10;
                        mRunThread3 = false;
                        mDrawCircle = false;
                        threadStart4();
                    }
                    try {
                        Thread.sleep(mTimeDur / 3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    postInvalidate();
                }
            }
        }).start();
    }

    private void threadStart4() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mRunThread4) {
                    mSweepAngle -= 10;
                    mSecPoint.y = mSecPoint.y+(float) (mWidth/200.0);
                    mThiPoint.y = mThiPoint.y - (float) (mWidth/100.0);
                    if (mSweepAngle <= -360) {
                        mSweepAngle = -360;
                        mRunThread4 = false;
                    }

                    try {
                        Thread.sleep(mTimeDur / 2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    postInvalidate();
                }
            }
        }).start();
    }

    /**
     * 重置动画
     */
    public void reset() {
        setPoint();
        mRunThread2 = true;
        mRunThread = true;
        mRunThread3 = true;
        mRunThread4 = true;
        mSweepAngle=0;
        threadStart();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(mVerFirstPoint.x, mVerFirstPoint.y, mVerSecPoint.x, mVerSecPoint.y, mDefaultPaint);
        if (mDrawCircle) {
            canvas.drawCircle(mCirclePoint.x, mCirclePoint.y, mCircleWidth, mDefaultPaint);
        }
        canvas.drawLine(mFirstPoint.x, mFirstPoint.y, mSecPoint.x, mSecPoint.y, mDefaultPaint);
        canvas.drawLine(mSecPoint.x, mSecPoint.y, mThiPoint.x, mThiPoint.y, mDefaultPaint);
        canvas.drawCircle(mWidth / 2, mHeight / 2, mWidth / 2 - 5, mBigCirclePaint);
        canvas.drawArc(new RectF(5, 5, mWidth - 5, mHeight - 5), -90, mSweepAngle, false, mArcPaint);
    }

    private void setPoint() {
        mFirstPoint = new PointF(mWidth / 4, mHeight / 2 + mCircleWidth / 2);
        mSecPoint = new PointF(mWidth / 2, mHeight * 3 / 4);
        mThiPoint = new PointF(mWidth * 3 / 4, mHeight / 2 + mCircleWidth / 2);
        mVerFirstPoint = new PointF(mWidth / 2, mHeight / 4);
        mVerSecPoint = new PointF(mWidth / 2, mHeight * 3 / 4);
        mCirclePoint = new PointF(mWidth / 2, mHeight / 2);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        mWidth = mHeight = Math.min(width, height);
        setMeasuredDimension(mWidth, mHeight);
        setPoint();
        threadStart();
    }
}
