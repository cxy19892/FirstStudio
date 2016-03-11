package com.cxy.firststudio.myviews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.cxy.firststudio.R;

/**
 * Created by Administrator on 2015/12/11.
 */
public class MyLinesView1 extends View{

    private Paint mPaint;
    private int textSize = 20;
    private int with=20, lenth = 100, mProsess =8000;
    private float left, top, right, bottom;

    public MyLinesView1(Context context) {

        super(context);
        initviews();
    }

    public MyLinesView1(Context context, AttributeSet attrs) {
        super(context, attrs);
        initviews();
    }

    public MyLinesView1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initviews();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        textSize = bottom-top;
        with = bottom-top;
        lenth= right - left;
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
    }

    private void initviews(){
        mPaint = new Paint();

    }

    public void setProsess(int Prosess){

        this.mProsess = lenth / 100 * Prosess;
    }

    private int color1 = 0xff24243f;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(color1);
        mPaint.setStrokeWidth(textSize * 1 / 2 * 6 / 9);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawLine(left, top, left+mProsess, bottom, mPaint);
    }
}
