package com.fcl.defineview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.fcl.defineview.R;

/**
 * Created by Administrator on 2017/2/6.
 */

public class CircleImageView extends View {

    private Paint mCirclePaint;
    private Bitmap mBitmap;
    private PorterDuffXfermode mMode;
    private Paint mDstPaint, mSrcPaint;
    private Canvas mDstCanvas, mSrcCanvas;
    private Bitmap mDstBitmap, mSrcBitmap;

    public CircleImageView(Context context) {
        this(context, null);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        mCirclePaint.setColor(Color.WHITE);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.first);
        mBitmap = Bitmap.createScaledBitmap(bitmap, 200, 200, true);
        mMode = new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT);

        mDstPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDstPaint.setColor(Color.BLUE);
        mSrcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSrcPaint.setColor(Color.YELLOW);
        //关闭硬件加速
        setLayerType(LAYER_TYPE_SOFTWARE, null);

        mDstBitmap = Bitmap.createBitmap(60, 60, Bitmap.Config.ARGB_8888);
        mDstCanvas = new Canvas(mDstBitmap);
        mSrcBitmap = Bitmap.createBitmap(60, 60, Bitmap.Config.ARGB_8888);
        mSrcCanvas = new Canvas(mSrcBitmap);
    }

    private Bitmap createCircleBitmap(Bitmap source, int min) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Bitmap target = Bitmap.createBitmap(min, min/2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(target);
        canvas.drawCircle(min / 2, min / 2, min / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(source, 0, 0, paint);
        return target;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

//        mDstCanvas.drawRect(new RectF(20, 20, 80, 80), mDstPaint);
//        canvas.drawBitmap(mDstBitmap, 0, 0, mDstPaint);
//        mSrcCanvas.drawCircle(30, 30, 30, mSrcPaint);
//        mSrcPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
//
//
//        canvas.drawBitmap(mSrcBitmap, 0, 0, mSrcPaint);
//        canvas.save();
//        canvas.drawRect(new RectF(20, 20, 80, 80), mDstPaint);
//        mSrcPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
//        canvas.drawCircle(30, 30, 30, mSrcPaint);
//        canvas.restore();


//        canvas.drawCircle(100,100,100,mCirclePaint);
//        mCirclePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
//        canvas.drawBitmap(mBitmap, 0, 0, mCirclePaint);

        canvas.drawBitmap(createCircleBitmap(mBitmap,200),0,0,null);
    }


}
