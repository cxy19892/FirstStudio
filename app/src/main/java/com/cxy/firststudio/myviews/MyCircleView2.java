package com.cxy.firststudio.myviews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2015/12/9.
 */
public class MyCircleView2 extends View {

    private Paint mPaint;
    private int textSize = 20;
    private RectF oval, oval1, oval2, oval3;
    private boolean isCrop = true;


    public MyCircleView2(Context context) {
        super(context);
        initView();
    }

    public MyCircleView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MyCircleView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public void initView(){
        //以下2个mPaint是为了适配三星机型
        mPaint = new Paint();
        oval = new RectF();
        oval1 = new RectF();
        oval2 = new RectF();
        oval3 = new RectF();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int width = right - left;

        textSize = width / 24;

        oval.left = width * 1/6;
        oval.right = width * 5/6;
        oval.top = width * 1/6;
        oval.bottom = width * 5/6;

        float translant1 = 0.6f * textSize;
        oval1.left = width * 1/6 - translant1;
        oval1.right = width * 5/6 + translant1;
        oval1.top = width * 1/6 - translant1;
        oval1.bottom = width * 5/6 + translant1;

        float translant2 = 0.9f * textSize;
        oval2.left = width * 1/6 + translant2;
        oval2.right = width * 5/6 - translant2;
        oval2.top = width * 1 / 6 + translant2;
        oval2.bottom = width * 5/6 - translant2;

        float translant3 = 1.2f * textSize;
        oval3.left = width * 1/6 + translant3;
        oval3.right = width * 5/6 - translant3;
        oval3.top = width * 1/6 + translant3;
        oval3.bottom = width * 5/6 - translant3;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.reset();
        mPaint.setStyle(Paint.Style.STROKE);
        PathEffect effects = new DashPathEffect(new float[]{6, 6, 6, 6}, 1);
        mPaint.setPathEffect(effects);
        mPaint.setColor(0xff484A5E);
        mPaint.setStrokeWidth(textSize * 1/2);
        mPaint.setAntiAlias(true);
        canvas.drawArc(oval, 135, 270, false, mPaint);//小弧形


        mPaint.reset();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(0xff717486);
        mPaint.setStrokeWidth(textSize * 1/2 * 2/9);
        mPaint.setAntiAlias(true);
        canvas.drawArc(oval1, 135, 270, false, mPaint);//小弧形

        mPaint.reset();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(0xff40415A);
        mPaint.setStrokeWidth(textSize * 1/2 * 1/9);
        mPaint.setAntiAlias(true);
        canvas.drawArc(oval2, 135, 270, false, mPaint);//小弧形

        int color1 = 0xff24243f;
        int color2 = 0xff40415A;
        int color3 = 0xff30304c;
        mPaint.reset();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(color2);
        mPaint.setStrokeWidth(textSize * 1/2 * 6/ 9);
        mPaint.setAntiAlias(true);
        canvas.drawArc(oval3, 135, 270, false, mPaint);//小弧形

        mPaint.reset();
        mPaint.setStyle(Paint.Style.STROKE);
        if(isCrop)
            mPaint.setColor(color1);
        else
            mPaint.setColor(color3);
        mPaint.setStrokeWidth(textSize * 1/ 2);
        mPaint.setAntiAlias(true);
//        for (int i = 0; i < 11; i++) {
        int i = 0;
            float startAngle = 121.5f + (13.5f * i);
            float stopAngle = 121.5f + (13.5f * i) +1;
            canvas.drawArc(oval3, startAngle , stopAngle, false, mPaint);//小弧形
//        }

        //最后画睡眠度圆弧
        float cx = oval.left + (oval.right - oval.left)/2;
        float cy = oval.top + (oval.bottom - oval.top)/2;
        Shader shader = new SweepGradient(cx, cy, new int[]{Color.RED, Color.YELLOW, Color.GREEN}, new float[]{0.15f, 0.40f, 0.75f});
        Matrix matrix = new Matrix();
        matrix.setRotate(135, cx, cy);
        shader.setLocalMatrix(matrix);


        mPaint.reset();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(textSize * 1 / 2);
        mPaint.setAntiAlias(true);
        mPaint.setPathEffect(effects);
//        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setShader(shader);
        canvas.drawArc(oval, 135, 200, false, mPaint);//小弧形
        mPaint.setShader(null);
    }
}
