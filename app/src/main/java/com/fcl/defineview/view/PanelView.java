package com.fcl.defineview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2017/2/5.
 * 仪表盘
 */

public class PanelView extends View {

    private String TAG = "PanelView";

    /**
     * view宽高
     */
    private int mWidth, mHeight;

    private RectF mArcRectf, mArcOutRectf;
    /**
     * 圆弧四周的间距
     */
    private float mArcSpace, mArcOutSpace;
    private Paint mArcPaint, mArcComPaint;
    private Paint mCircleInPaint, mCircleOutPaint;
    /**
     * 当前进度
     */
    private float mCurPer = 0, mCurX, mCurY;


    public PanelView(Context context) {
        this(context, null);
    }

    public PanelView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PanelView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mArcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mArcPaint.setColor(Color.WHITE);
        mArcPaint.setStyle(Paint.Style.STROKE);
        mArcPaint.setStrokeWidth(40);
        mArcComPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mArcComPaint.setColor(Color.GREEN);
        mArcComPaint.setStyle(Paint.Style.STROKE);
        mArcComPaint.setStrokeWidth(40);
        mCircleInPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCircleInPaint.setColor(Color.WHITE);
        mCircleInPaint.setStyle(Paint.Style.FILL);
        mCircleInPaint.setStrokeWidth(4);
        mCircleOutPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCircleOutPaint.setColor(Color.GREEN);
        mCircleOutPaint.setStyle(Paint.Style.STROKE);
        mCircleOutPaint.setStrokeWidth(5);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawArc(canvas);
        drawLine(canvas);
        drawCircle(canvas);
    }

    /**
     * 画线
     * @param canvas
     */
    private void drawLine(Canvas canvas) {
        float sweepDegree = mCurPer * 250;
        float endDegree = -225 + sweepDegree+10;
        mCurX = (float) (mWidth/2+mWidth  / 3 * Math.cos(Math.toRadians(endDegree)));
        mCurY = (float) (mHeight/2+mWidth / 3 * Math.sin(Math.toRadians(endDegree)));
        mCircleInPaint.setColor(Color.WHITE);
        canvas.drawLine(mWidth/2, mHeight/2, mCurX, mCurY, mCircleInPaint);
        float x = mWidth/2;
        float y = (float) (-mHeight*0.37);
        float y1 = (float) (-mHeight*2/5.0);
        int j = 12, i =0;
        canvas.save();
        canvas.translate(mWidth/2, mHeight/2);
        canvas.rotate(-125);
        int count = (int) (sweepDegree / 20.83);
        while (i <= j) {

            canvas.save();
            if (i <= count) {
                mCircleInPaint.setColor(Color.GREEN);
            } else {
                mCircleInPaint.setColor(Color.WHITE);
            }
            canvas.drawLine(0,y1, 0,y,mCircleInPaint);
            canvas.restore();
            canvas.rotate((float) 20.83);
            i++;
        }
        canvas.restore();
    }

    /**
     * 画圆弧
     * @param canvas
     */
    private void drawArc(Canvas canvas) {
        mArcPaint.setColor(Color.WHITE);
        canvas.drawArc(mArcRectf, -225, 270, false, mArcPaint);
        mCircleOutPaint.setColor(Color.WHITE);
        canvas.drawArc(mArcOutRectf, -215, 250, false, mCircleOutPaint);
        float sweepDegree = mCurPer * 250;
        mArcComPaint.setColor(Color.GREEN);
        if (sweepDegree >= 250) {
            canvas.drawArc(mArcRectf, -225, sweepDegree+20, false, mArcComPaint);
        } else if (sweepDegree > 0){
            canvas.drawArc(mArcRectf, -225, sweepDegree+10, false, mArcComPaint);
        }

        mCircleOutPaint.setColor(Color.GREEN);
//        if (sweepDegree >= 10 ) {
            if (sweepDegree > 260) {
                sweepDegree = 260;
            }
            canvas.drawArc(mArcOutRectf, -215, sweepDegree, false, mCircleOutPaint);
//        }
    }

    private void drawCircle(Canvas canvas) {
        canvas.drawCircle(mWidth/2,mHeight/2,20,mCircleInPaint);
        canvas.drawCircle(mWidth/2,mHeight/2,35,mCircleOutPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        mWidth = mHeight = Math.min(width, height);
        setMeasuredDimension(mWidth, mHeight);
        mArcSpace = (float) (mWidth / 6.0);
        mArcOutSpace = (float) (mWidth / 10.0);
        mArcRectf = new RectF(mArcSpace, mArcSpace, mWidth - mArcSpace, mHeight - mArcSpace);
        mArcOutRectf = new RectF(mArcOutSpace, mArcOutSpace, mWidth - mArcOutSpace, mHeight - mArcOutSpace);
    }

    public void setmCurPer(float mCurPer) {
        this.mCurPer = mCurPer;
        invalidate();
    }
}
