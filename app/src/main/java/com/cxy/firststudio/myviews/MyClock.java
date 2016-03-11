package com.cxy.firststudio.myviews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2016/2/23.
 */
public class MyClock extends View {

    private int width;
    private int height;
    private int HourRadius;
    private int MinuRadius;
    private int CircRadius;
    private Paint mPaintHour;
    private Paint mPaintMinute;
    private Paint mPaint;
    private int HourColor = 0xff63737b;
    private int MinuColor = 0xff9fa5b7;
    private int minute = 5;
    private int hour   = 15;
    private boolean isShowNum = false;


    public MyClock(Context context) {
        super(context);
    }

    public MyClock(Context context, AttributeSet attrs) {
        super(context, attrs);

        mPaint = new Paint();
        mPaint.setColor(MinuColor);
        mPaint.setStrokeWidth(5);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);

        mPaintHour = new Paint();
        mPaintHour.setColor(HourColor);
        mPaintHour.setStrokeWidth(7);
        mPaintHour.setAntiAlias(true);


        mPaintMinute = new Paint();
        mPaintMinute.setColor(MinuColor);
        mPaintMinute.setStrokeWidth(5);
        mPaintMinute.setAntiAlias(true);
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
        MinuRadius = width / 3 + width / 10;
        CircRadius = width / 2;
    }


    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        //画出大圆
        canvas.drawCircle(width / 2, height / 2, CircRadius, mPaint);
        //画出圆中心
        canvas.drawCircle(width / 2, height / 2, 2, mPaint);
        //依次旋转画布，画出每个刻度和对应数字
        if(isShowNum) {
            mPaint.setTextSize(width / 12);
            mPaint.setStrokeWidth(2);
            for (int i = 1; i <= 12; i++) {
                canvas.save();//保存当前画布
                canvas.rotate(360 / 12 * i, width / 2, height / 2);
                //左起：起始位置x坐标，起始位置y坐标，终止位置x坐标，终止位置y坐标，画笔(一个Paint对象)
                canvas.drawLine(width / 2, height / 2 - CircRadius, width / 2, height / 2 - CircRadius + 5, mPaint);
                //左起：文本内容，起始位置x坐标，起始位置y坐标，画笔
                //canvas.drawText("" + i, width / 2 -  width / 24, height / 2 - CircRadius + 5 + width / 12, mPaint);
                canvas.restore();//
            }
        }

        float minuteDegree = minute/60f*360;//得到分针旋转的角度
        canvas.save();
        canvas.rotate(minuteDegree, width / 2, height / 2);
        canvas.drawLine(width / 2, height / 2 - MinuRadius, width / 2, height / 2 + MinuRadius/5, mPaintMinute);
        canvas.restore();

        float hourDegree = (hour*60+minute)/12f/60*360;//得到时钟旋转的角度
        canvas.save();
        canvas.rotate(hourDegree, width / 2, height / 2);
        canvas.drawLine(width / 2, height / 2 - HourRadius, width / 2, height / 2 + HourRadius/5, mPaintHour);
        canvas.restore();

    }

}
