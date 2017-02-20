package com.fcl.defineview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2017/2/5.
 * 圆形进度
 */

public class CircleProgressView extends View {

    private String TAG = "CircleProgressView";
    /**
     * view宽高
     */
    private int mWidth, mHeight;
    /**
     * 有颜色部分和空白部分宽度的倍数关系，以及分别的个数
     */
    private int mMultipile = 5, mCount = 30;
    private Paint mColorPaint, mBlankPaint, mTextPaint;
    private RectF mBaseRectf;
    private float mDegreeInc1, mDegreeInc2;
    private float mSpace;
    /**
     * 当前进度
     */
    private float mCurPer = 0;
    private List<Integer> mColors = Arrays.asList(Color.parseColor("#ddff77"), Color.parseColor("#332277"),
            Color.parseColor("#3322cc"),Color.parseColor("#dd33dd"));

    public CircleProgressView(Context context) {
        this(context, null);
    }

    public CircleProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleProgressView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mColorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mColorPaint.setColor(Color.WHITE);
        mColorPaint.setStrokeWidth(10);
        mColorPaint.setStyle(Paint.Style.STROKE);


        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setTextSize(60);

        float unitDegree = (float) (360.0 / ((mMultipile + 1) * mCount));
        mDegreeInc1 = unitDegree * 5;
        mDegreeInc2 = unitDegree;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float startDegree = -90;
        float compareDegree = mCurPer * 360;
        while (startDegree < 270) {
            if (startDegree < compareDegree-90) {
                // compareDegree - 90 - startDegree) / mDegreeInc2 / 6 的结果是当前进度所占跨度的格子数多少，
                // 你想几个格子显示同一种颜色，后面就再除以几，这里是8
                int order = (int) ((compareDegree - 90 - startDegree) / mDegreeInc2 / 6 / 8);
                Log.e(TAG, "顺序"+order);
                mColorPaint.setColor(mColors.get(order%mColors.size()));
            } else {
                mColorPaint.setColor(Color.WHITE);
            }
            canvas.drawArc(mBaseRectf, startDegree, mDegreeInc1, false, mColorPaint);
            startDegree += mDegreeInc1 + mDegreeInc2;
        }


        mTextPaint.setTextSize(60);
        canvas.drawText("已学课时", mWidth/2,mHeight/2,mTextPaint);
        mTextPaint.setTextSize(80);
        canvas.drawText((int)(mCurPer*100)+"%", mWidth/2,mHeight*2/3,mTextPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        mWidth = mHeight = Math.min(width, height);
        setMeasuredDimension(mWidth, mHeight);
        mSpace = (float) (mWidth / 10.0);
        mBaseRectf = new RectF(mSpace, mSpace, mWidth - mSpace, mHeight - mSpace);

    }

    /**
     * 设置当前进度
     * @param mCurPer
     */
    public void setmCurPer(float mCurPer) {
        this.mCurPer = mCurPer;
        invalidate();
    }

    /**
     * 设置不同进度显示的颜色
     * @param mColors
     */
    public void setmColors(List<Integer> mColors) {
        this.mColors = mColors;
        invalidate();
    }
}
