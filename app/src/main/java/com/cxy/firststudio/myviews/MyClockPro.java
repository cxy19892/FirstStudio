package com.cxy.firststudio.myviews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.Layout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2016/2/23.
 */
public class MyClockPro extends View {

    private int width;
    private int height;
    private int HourRadius;
    private int CircRadius;
    private Paint mStartTime;
    private Paint mStopTime;
    private Paint mPaint, mCirclePaint;
    private Paint mScirPaint;
    private TextPaint mTextPaint;
    private int HourColor = 0xff63737b;
    private int MinuColor = 0xff9fa5b7;
    private int minute = 5;
    private int hour   = 15;
    private boolean isShowNum = true;
    private float mDensity;
    private int smallCircRadius = 10;
    private int TEXTSIZE = 15;
    private int left, top, right, bottom;


    public MyClockPro(Context context) {
        super(context);
    }

    public MyClockPro(Context context, AttributeSet attrs) {
        super(context, attrs);

        mDensity = getContext().getResources().getDisplayMetrics().density;

        mPaint = new Paint();
        mPaint.setColor(HourColor);
        mPaint.setStrokeWidth(5);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        mStartTime = new Paint();
        mStartTime.setColor(HourColor);
        mStartTime.setStrokeWidth(10);
        mStartTime.setAntiAlias(true);


        mStopTime = new Paint();
        mStopTime.setColor(HourColor);
        mStopTime.setStrokeWidth(10);
        mStopTime.setAntiAlias(true);

        mTextPaint = new TextPaint();
        // 去锯齿
        mTextPaint.setAntiAlias(true);
        // 防抖动
        mTextPaint.setDither(true);
        mTextPaint.setTextSize(TEXTSIZE * mDensity);
        mTextPaint.setColor(Color.BLACK);

        mScirPaint = new Paint();
        mScirPaint.setColor(HourColor);
        mScirPaint.setAntiAlias(true);
        mScirPaint.setDither(true);
        mScirPaint.setColor(0xff6f9ef6);

        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setDither(true);
        mCirclePaint.setColor(0x7f6f9ef6);


        mCirclePaint.setStyle(Paint.Style.STROKE);
    }

    /**
     * 传入时间 HH:mm
     * @param time
     */
    public void setTime(String time){

    }

    /**
     * 传入时间
     * @param hou 小时
     * @param min 分钟
     */
    public void setTime(int hou, int min, boolean isShowNum){
        this.hour = hou;
        this.minute = min;
        this.isShowNum = isShowNum;
        invalidate();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = right - left-4;
        height= bottom - top-4;
        HourRadius = width / 3;
        CircRadius = width / 2 - (int)(15 * mDensity);
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }


    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        //画数字

        canvas.drawText("12", width / 2 - Layout.getDesiredWidth("0", mTextPaint), Layout.getDesiredWidth("0", mTextPaint) + 8, mTextPaint);

        canvas.drawText("3", width - Layout.getDesiredWidth("0", mTextPaint), height / 2 + Layout.getDesiredWidth("0", mTextPaint) / 2, mTextPaint);

        canvas.drawText("6", width / 2 - Layout.getDesiredWidth("0", mTextPaint) / 2, height, mTextPaint);

        canvas.drawText("9", 0, height/2 + Layout.getDesiredWidth("0", mTextPaint)/2, mTextPaint);
        //画小圆点
        for (int i = 3; i <= 12; i+=3) {
            canvas.save();//保存当前画布
            canvas.rotate(360 / 12 * i, width / 2, height / 2);
            canvas.drawCircle(width/2, Layout.getDesiredWidth("00", mTextPaint)+smallCircRadius/2, smallCircRadius, mScirPaint);
            canvas.restore();//
        }

        //画出大圆
//        canvas.drawCircle(width / 2, height / 2, CircRadius, mPaint);
        //画出圆中心
        canvas.drawCircle(width / 2, height / 2, smallCircRadius, mPaint);
        //依次旋转画布，画出每个刻度和对应数字
        if(isShowNum) {
            mPaint.setTextSize(width / 12);
            mPaint.setStrokeWidth(2);
            for (int i = 1; i <= 36; i++) {
                if(i % 9 != 0) {
                    canvas.save();//保存当前画布
                    canvas.rotate(360 / 36 * i, width / 2, height / 2);
                    //左起：起始位置x坐标，起始位置y坐标，终止位置x坐标，终止位置y坐标，画笔(一个Paint对象)
                    canvas.drawLine(width / 2, Layout.getDesiredWidth("00", mTextPaint), width / 2, Layout.getDesiredWidth("00", mTextPaint) + smallCircRadius, mPaint);
                    canvas.restore();//
                }
            }
        }



        float minuteDegree = minute/60f*360;//得到分针旋转的角度
        canvas.save();
        canvas.rotate(minuteDegree, width / 2, height / 2);
        canvas.drawLine(width / 2, height / 2 - HourRadius, width / 2, height / 2, mStopTime);
        canvas.restore();

        float hourDegree = (hour*60+minute)/12f/60*360;//得到时钟旋转的角度
        canvas.save();
        canvas.rotate(hourDegree, width / 2, height / 2);
        canvas.drawLine(width / 2, height / 2 - HourRadius, width / 2, height / 2, mStartTime);
        canvas.restore();
        //画圆弧
        mCirclePaint.setStrokeWidth(width/5);
        RectF oval = new RectF(width / 2 - HourRadius/3*2, height / 2 - HourRadius/3*2, width / 2 + HourRadius/3*2, height / 2 + HourRadius/3*2);//(TEXTSIZE * mDensity + smallCircRadius, TEXTSIZE * mDensity + smallCircRadius, width - TEXTSIZE * mDensity - smallCircRadius, height - TEXTSIZE * mDensity - smallCircRadius);
//        canvas.drawRect(width / 2 - HourRadius/2, height / 2 - HourRadius/2, width / 2 + HourRadius/2, height / 2 + HourRadius/2, mStartTime);
        canvas.drawArc(oval, hourDegree % 360 - 90f, minuteDegree - 90f , false, mCirclePaint);//Arc(oval, hourDegree % 360 - 90, minuteDegree - 90 , false, mCirclePaint);

    }

}
