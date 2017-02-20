package com.fcl.defineview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2017/2/5.
 * 圆形百分比
 */

public class CirclePercentView extends View {

    private String TAG = "CirclePercentView";

    /**
     * view宽高
     */
    private int mWidth, mHeight;

    /**
     * 百分比数据
     */
    private List<Float> mPerDatas = new ArrayList<>();
    private RectF mPerRectf;

    private float mStartDegree = -90, mEndDegree = -90;

    /**
     * 圆弧的颜色，当圆弧个数大于颜色数目时，颜色会重复使用
     */
    private List<Integer> mPerColors = Arrays.asList(Color.parseColor("#338822"),
            Color.parseColor("#aa3322"), Color.parseColor("#312312"), Color.parseColor("#3388ff"));

    private Paint mDefaultPaint, mSmallCirclePaint, mPerPaint, mTextPaint;


    public CirclePercentView(Context context) {
        this(context, null);
    }

    public CirclePercentView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CirclePercentView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mDefaultPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDefaultPaint.setColor(Color.parseColor("#ffff22"));
        mSmallCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSmallCirclePaint.setColor(Color.parseColor("#ff8822"));
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setTextSize(30);
        mPerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(mWidth / 2, mHeight / 2, mWidth / 2, mDefaultPaint);
        drawPer(canvas);
        canvas.drawCircle(mWidth / 2, mHeight / 2, mWidth / 8, mSmallCirclePaint);
    }

    /**
     * 根据百分比绘制各段圆弧
     * @param canvas
     */
    private void drawPer(Canvas canvas) {
        for (int i = 0; i < mPerDatas.size(); i++) {
            float sweepDegree = 360 * mPerDatas.get(i) / 100;
            mPerPaint.setColor(mPerColors.get(i % mPerColors.size()));
            canvas.drawArc(mPerRectf, mStartDegree, sweepDegree, true, mPerPaint);
            float textDegree = (mStartDegree * 2 + sweepDegree) / 2;
            //文字的位置，当比例小于百分之六时，文字显示的位置作适当偏移
            float textX = mWidth/2, textY = mHeight/2;
            if (mPerDatas.get(i) < 6) {
                textX = (float) (mWidth/2+mWidth * 0.4 * Math.cos(Math.toRadians(textDegree)));
                textY = (float) (mHeight/2+mWidth * 0.4 * Math.sin(Math.toRadians(textDegree)));
                mTextPaint.setColor(Color.BLUE);
            } else {
                textX = (float) (mWidth/2+mWidth / 4 * Math.cos(Math.toRadians(textDegree)));
                textY = (float) (mHeight/2+mWidth / 4 * Math.sin(Math.toRadians(textDegree)));
                mTextPaint.setColor(Color.WHITE);
            }
            Log.e(TAG, String.format("textX:%f, textY:%f", textX, textY));
            canvas.drawText(mPerDatas.get(i)+"%", textX, textY, mTextPaint);
            mStartDegree += sweepDegree;
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        mWidth = mHeight = Math.min(width, height);
        setMeasuredDimension(mWidth, mHeight);
        mPerRectf = new RectF(20, 20, mWidth - 20, mHeight - 20);
    }

    /**
     * 传递绘制的百分比数据
     * @param datas
     */
    public void setPercentData(List<Float> datas) {
        this.mPerDatas = datas;
        invalidate();
    }

    /**
     * 设置圆弧的颜色
     * @param mPerColors
     */
    public void setmPerColors(List<Integer> mPerColors) {
        this.mPerColors = mPerColors;
    }
}
