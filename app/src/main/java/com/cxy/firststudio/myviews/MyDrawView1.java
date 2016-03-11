package com.cxy.firststudio.myviews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2015/12/11.
 */
public class MyDrawView1 extends View implements View.OnTouchListener{
    private Paint mPaint;    //画笔
    private int color = Color.BLUE;       //画笔颜色
    private int mStrokeWidth = 20;//画笔的粗细
    private boolean isBeeLine = false;//是否是直线
    private List<Point> allPoints=new ArrayList<Point>();
    private Path path = new Path();

    public MyDrawView1(Context context) {
        super(context);
        initViews();
        setOnTouchListener(this);
    }

    public MyDrawView1(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
        setOnTouchListener(this);
    }

    public MyDrawView1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
        setOnTouchListener(this);
    }

    public void initViews(){
        mPaint = new Paint();
        mPaint.reset();
        mPaint.setColor(this.color);
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
    }

    public void clear(){
        path.close();
        path.reset();
        reSetviews();
    }

    private void reSetviews(){
        mPaint.reset();
        mPaint.setColor(this.color);
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
    }

    public void setColor(int color){
        this.color = color;
        reSetviews();
    }

    public void setmStrokeWidth(int with){
        this.mStrokeWidth = with;
        reSetviews();
    }

    public void setBeeLine(){
        this.isBeeLine = !isBeeLine;
        reSetviews();
    }

    public void setPaint(int color, int with, boolean isline){
        this.color = color;
        this.mStrokeWidth = with;
        this.isBeeLine = isline;
        reSetviews();
    }



    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);
        canvas.drawPath(path,mPaint);
//        if(this.allPoints.size()>1) {
//            //如果有坐标点，开始绘图
//            Iterator<Point> iter = this.allPoints.iterator();
//            Point first = null;
//            Point last = null;
//            while (iter.hasNext()) {
//                if (first == null) {
//                    first = (Point) iter.next();
//                } else {
//                    if (last != null) {
//                        first = last;
//                    }
//                    last = (Point) iter.next();//结束
//                    canvas.drawLine(first.x, first.y, last.x, last.y, mPaint);
//                }
//            }
//        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Point p=new Point((int)event.getX(),(int)event.getY());
        if(event.getAction() == MotionEvent.ACTION_DOWN){

            //用户按下，表示重新开始保存点
//            this.allPoints=new ArrayList<Point>();
//            this.allPoints.add(p);
            path.moveTo(event.getX(), event.getY());
        }else if(event.getAction() == MotionEvent.ACTION_UP){
//            this.allPoints.add(p);
//            this.postInvalidate();//重绘图像
            if(isBeeLine){
                path.lineTo(event.getX(), event.getY());
            }
        }else if(event.getAction() == MotionEvent.ACTION_MOVE){
            if(!isBeeLine){
//                this.allPoints.add(p);
//                this.postInvalidate();//重绘图像
                path.lineTo(event.getX(), event.getY());
            }
        }
        this.postInvalidate();//重绘图像
        return true;
    }


}
