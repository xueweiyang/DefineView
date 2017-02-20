package com.fcl.defineview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by Administrator on 2017/2/5.
 * 水纹进度
 */

public class WaveLoadingView extends View {

    private String TAG = "WaveLoadingView";
    /**
     * view宽高
     */
    private int mWidth, mHeight;
    private float mCenterX, mCenterY;
    private Paint mCirclePaint, mWavePaint, mTextPaint;
    private Path mWavePath;
    private float mCurHeight;
    private float mCtrX;
    private PorterDuffXfermode mMode;
    private boolean mIsInc = true;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private int mRate = 0;

    public WaveLoadingView(Context context) {
        this(context, null);
    }

    public WaveLoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaveLoadingView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mBitmap = Bitmap.createBitmap(800,800, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        mMode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setColor(Color.WHITE);
        mWavePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mWavePaint.setColor(Color.GREEN);
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        mWavePath = new Path();
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.RED);
        mTextPaint.setTextSize(50);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mCtrX <= 0) {
            mIsInc = true;
        } else if (mCtrX >= mWidth) {
            mIsInc = false;
        }
        mCtrX = mIsInc ? mCtrX + 10 : mCtrX - 10;

        mWavePath.reset();
        mWavePath.moveTo(0, mCurHeight);
        mWavePath.quadTo(mCtrX*2,mCurHeight+50,mWidth,mCurHeight);
        mWavePath.lineTo(mWidth,mHeight);
        mWavePath.lineTo(0,mHeight);
        mWavePath.close();

        //dst
        mCanvas.drawCircle(mCenterX,mCenterY,mWidth/2,mCirclePaint);
        mWavePaint.setXfermode(mMode);
//        mCanvas.drawRect(new RectF(0,mCenterY,mWidth,mHeight),mWavePaint);
        mCanvas.drawPath(mWavePath,mWavePaint);
        canvas.drawBitmap(mBitmap, 0,0,null);

        canvas.drawText(mRate+"%", mWidth/2,mHeight/2, mTextPaint);

        postInvalidateDelayed(10);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        mWidth = mHeight = Math.min(width, height);
        mCenterX = mWidth/2;
        mCenterY=mHeight/2;
        mCurHeight = mHeight;
        mCtrX = mWidth/2;

    }

    public void setRate(float rate) {
        mRate = (int) (rate*100);
        mCurHeight = mHeight - mHeight * rate;
        invalidate();
        Log.e(TAG, "curheight:"+mCurHeight);
    }
}
