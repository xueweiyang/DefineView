package com.fcl.defineview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2017/1/23.
 * Lol技能图谱
 */

public class LolAbilityView extends View {

    private String TAG = "LolAbilityView";
    /**
     * 白线
     */
    private Paint mWhiteLinePaint;
    private int mWhiteLineLength;
    private float mRotateDegree = (float) (360 / 7.0);
    /**
     * 派的角度
     */
    private float mDegreePai = (float) Math.toRadians(360 / 7.0);
    /**
     * 蓝色三角形
     */
    private Paint mBlueTriPaint;
    private Path mBlueTriPath;
    /**
     * 绿色三角形
     */
    private Paint mGreenTriPaint;
    private Path mGreenTriPath;
    /**
     * 红色三角形
     */
    private Paint mRedTriPaint;
    private Path mRedTriPath;

    /**
     * view宽高
     */
    private int mWidth, mHeight;
    /**
     * 描述文字
     */
    private Paint mTitlePaint;
    private List<String> mTitles = Arrays.asList("击杀", "生存", "助攻", "物理", "魔法", "防御", "金钱" );
    /**
     * 各能力分数
     */
    private Paint mScorePaint;
    private Path mScorePath;
    private List<Double> mScores = Arrays.asList(8.0, 10.0, 4.3, 9.5, 2.7, 4.9, 8.3);

    public LolAbilityView(Context context) {
        this(context, null);
    }

    public LolAbilityView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LolAbilityView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mWhiteLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mWhiteLinePaint.setColor(Color.WHITE);
        mWhiteLinePaint.setStrokeWidth(3);
        mBlueTriPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBlueTriPaint.setColor(Color.BLUE);
        mBlueTriPaint.setStyle(Paint.Style.FILL);
        mBlueTriPath = new Path();
        mGreenTriPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mGreenTriPaint.setColor(Color.GREEN);
        mGreenTriPaint.setStyle(Paint.Style.FILL);
        mGreenTriPath = new Path();
        mRedTriPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRedTriPaint.setColor(Color.RED);
        mRedTriPaint.setStyle(Paint.Style.FILL);
        mRedTriPath = new Path();
        mTitlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTitlePaint.setColor(Color.WHITE);
        mTitlePaint.setTextAlign(Paint.Align.CENTER);
        mTitlePaint.setTextSize(20);
        mScorePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mScorePaint.setColor(Color.YELLOW);
        mScorePaint.setStrokeWidth(5);
        mScorePath = new Path();
    }

    /**
     * 各种计算
     */
    private void cal() {
        //蓝色三角形路径
        mBlueTriPath.moveTo(0, 0);
        mBlueTriPath.lineTo(0, -mHeight/10);
        float x = (float)(mWidth*Math.sin(mDegreePai)/10);
        float y = (float)(-mWidth*Math.cos(mDegreePai)/10);
        mBlueTriPath.lineTo(x, y);
        mBlueTriPath.lineTo(0, 0);
        //绿色四边形路径
        mGreenTriPath.moveTo(0, -mHeight/10);
        mGreenTriPath.lineTo(0, -mHeight/5);
        mGreenTriPath.lineTo(2*x,2*y);
        mGreenTriPath.lineTo(x,y);
        mGreenTriPath.lineTo(0,-mHeight/10);
        //红色四边形路径
        mRedTriPath.moveTo(0, -mHeight/5);
        mRedTriPath.lineTo(0, -mHeight*3/10);
        mRedTriPath.lineTo(3*x,3*y);
        mRedTriPath.lineTo(2*x,2*y);
        mRedTriPath.lineTo(0,-mHeight/5);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        mWidth = mHeight = Math.min(width, height);
        mWhiteLineLength = mWidth * 3 / 10;
        cal();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawWhiteLine(canvas);
    }

    /**
     * 画白线
     * @param canvas
     */
    private void drawWhiteLine(Canvas canvas) {
        canvas.save();
        canvas.translate(mWidth/2, mHeight/2);
//        canvas.rotate(mRotateDegree);
        for (int i = 0; i < 7; i++) {


            canvas.save();
            canvas.drawPath(mBlueTriPath, mBlueTriPaint);
            canvas.drawPath(mGreenTriPath, mGreenTriPaint);
            canvas.drawPath(mRedTriPath, mRedTriPaint);
            canvas.drawText(mTitles.get(i)+mScores.get(i), 0, -(mWhiteLineLength+20), mTitlePaint);

            canvas.restore();
            canvas.save();
            canvas.drawLine(0, 0, 0, -mWhiteLineLength, mWhiteLinePaint);
            canvas.drawLine(0, (float)(-mWhiteLineLength * mScores.get(i) / 10),
                    (float)(Math.sin(mDegreePai)*mScores.get((i+1)%7)*mWhiteLineLength/10),(float)( -Math.cos(mDegreePai)*mScores.get((i+1)%7)*mWhiteLineLength/10),
                    mScorePaint);
            canvas.restore();

            canvas.rotate(mRotateDegree);
        }

        canvas.restore();
    }
}
