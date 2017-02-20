package com.fcl.defineview.view;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 2017/2/5.
 * 倾斜
 */

public class TiltView extends View {

    /**
     * view宽高
     */
    private int mWidth, mHeight;
    private float mCenterX, mCenterY;
    private Paint mCirclePaint;
    private Matrix mMatrix;
    private Camera mCamera;
    private float mRotateX = 0, mRotateY = 0, mRotateMax = 20;

    public TiltView(Context context) {
        this(context, null);
    }

    public TiltView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TiltView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setColor(Color.GREEN);
        mMatrix = new Matrix();
        mCamera = new Camera();


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mMatrix.reset();
        mCamera.save();
        mCamera.rotateX(mRotateX);
        mCamera.rotateY(mRotateY);
        mCamera.getMatrix(mMatrix);
        mCamera.restore();
        //改变矩阵作用点
        mMatrix.preTranslate(-mWidth/2,-mHeight/2);
        mMatrix.postTranslate(mWidth/2,mHeight/2);
        //将矩阵应用于整个canvas
        canvas.concat(mMatrix);
        canvas.drawCircle(mWidth/2, mHeight/2,100,mCirclePaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                rotateCanvasWhenMove(x, y);
                invalidate();
                return true;
            case MotionEvent.ACTION_MOVE:
                rotateCanvasWhenMove(x, y);
                invalidate();
                return true;
            case MotionEvent.ACTION_UP:
                mRotateX = 0;
                mRotateY = 0;
                invalidate();
                return true;
        }
        return super.onTouchEvent(event);
    }

    private void rotateCanvasWhenMove(float x, float y) {
        float dx = x - mCenterX;
        float dy = y - mCenterY;

        float percentX = dx / mCenterX;
        float percentY = dy /mCenterY;

        if (percentX > 1f) {
            percentX = 1f;
        } else if (percentX < -1f) {
            percentX = -1f;
        }
        if (percentY > 1f) {
            percentY = 1f;
        } else if (percentY < -1f) {
            percentY = -1f;
        }

        mRotateX = mRotateMax * percentX;
        mRotateY = -(mRotateMax * percentY);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        mWidth = mHeight = Math.min(width, height);
        mCenterX = mWidth/2;
        mCenterY=mHeight/2;
        setMeasuredDimension(mWidth, mHeight);

    }
}
