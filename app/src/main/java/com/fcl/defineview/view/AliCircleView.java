package com.fcl.defineview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by Administrator on 2017/1/24.
 */

public class AliCircleView extends View {

    private String TAG = "AliCircleView";

    /**
     * view宽高
     */
    private int mWidth, mHeight;
    /**
     * 初始和结束角度
     */
    private float mStartDegree = -90, mEndDegree = 0;

    private Paint mCirclePaint, mWhitePaint;

    private boolean mRunThread = true, mCircleThread = true, mCircleThread2 = true;
    /**
     * view的四个顶点
     */
    private int mLeft, mTop, mRight, mBottom;
    /**
     * 对号的三个顶点和中间点
     */
    private PointF mFirstPoint, mSecPoint, mThiPoint, mCursorPoint, mCursorPoint2;


    public AliCircleView(Context context) {
        this(context, null);
    }

    public AliCircleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AliCircleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setColor(Color.GREEN);
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setStrokeWidth(3);
        threadStart();
        mWhitePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mWhitePaint.setColor(Color.WHITE);
    }

    private void threadStart() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mRunThread) {
                    mEndDegree = (mEndDegree + 6);
                    if (mEndDegree >= 360) {
                        mRunThread = false;
                        circleThread();
                    }
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    postInvalidate();
                }

            }
        }).start();
    }

    private void circleThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mCircleThread) {
                    mCursorPoint.y += 10;
                    mCursorPoint.x += 15;
                    if (mCursorPoint.y >= mHeight * 3 / 4 || mCursorPoint.x >= mWidth / 2) {
                        mCursorPoint.x = mWidth / 2;
                        mCursorPoint.y = mHeight * 3 / 4;
                        mCircleThread = false;
                        circleThread2();
                    }
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    postInvalidate();
                }
            }
        }).start();
    }

    private void circleThread2() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mCircleThread2) {
                    mCursorPoint2.x += 8;
                    mCursorPoint2.y -= 16;
                    if (mCursorPoint2.x >= mThiPoint.x || mCursorPoint2.y <= mThiPoint.y) {
                        mCursorPoint2.x = mThiPoint.x;
                        mCursorPoint2.y = mThiPoint.y;
                        mCircleThread2 = false;
                    }
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    postInvalidate();
                }
            }
        }).start();
    }

    /**
     * 从外部开始动画
     */
    public void start() {
        mRunThread = true;
//        mEndDegree = 0;
        threadStart();
    }

    /**
     * 重置
     */
    public void reset() {
        mRunThread = true;
        mCircleThread = true;
        mCircleThread2 = true;
        mEndDegree = 0;
        threadStart();
        mCursorPoint = new PointF(mWidth / 8, mHeight / 2);
        mCursorPoint2 = new PointF(mWidth / 2, mHeight * 3 / 4);
    }

    /**
     * 停止
     */
    public void stop() {
        mRunThread = false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawCircle(mWidth/2, mHeight/2,mWidth/2,mCirclePaint);

        canvas.drawArc(new RectF(0, 0, mRight-mLeft, mBottom-mTop), mStartDegree, mEndDegree, true, mCirclePaint);
        canvas.drawCircle((mLeft + mRight) / 2-mLeft, (mTop + mBottom) / 2-mTop, mWidth / 2 - 3, mWhitePaint);
//        canvas.drawArc(new RectF(90, 400, 990, 1300), 0, 300, true, mCirclePaint);
        canvas.drawLine(mFirstPoint.x, mFirstPoint.y, mCursorPoint.x, mCursorPoint.y, mCirclePaint);
        canvas.drawLine(mSecPoint.x, mSecPoint.y, mCursorPoint2.x, mCursorPoint2.y, mCirclePaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        mWidth = mHeight = Math.min(width, height);
        setMeasuredDimension(mWidth, mHeight);
        mLeft = getLeft() ;
        mTop = getTop() ;
        mRight = getRight() ;
        mBottom = getBottom() ;
        Log.e(TAG, String.format("left:%d, top:%d, right:%d, bottom:%d,width:%d,height:%d", mLeft, mTop, mRight, mBottom, width, height));
        mFirstPoint = new PointF(mWidth / 8, mHeight / 2);
        mSecPoint = new PointF(mWidth / 2, mHeight * 3 / 4);
        mCursorPoint = new PointF(mWidth / 8, mHeight / 2);
        mCursorPoint2 = new PointF(mWidth / 2, mHeight * 3 / 4);
        mThiPoint = new PointF(mWidth * 3 / 4, mHeight / 4);
    }
}
