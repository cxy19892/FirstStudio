package com.cxy.firststudio.myviews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.cxy.firststudio.R;

/**
 * Created by Administrator on 2015/12/9.
 */
public class MyCircleView extends View{

    private Paint mPaint;

    public MyCircleView(Context mcontext){
        super(mcontext);
    }

    public MyCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint = new Paint();
        mPaint.setAntiAlias(true); // 消除锯齿
        mPaint.setStrokeWidth(100); // 设置圆环的宽度
        mPaint.setStrokeCap(Paint.Cap.BUTT); // 定义线段断电形状为圆头
        mPaint.setAntiAlias(true); // 消除锯齿
        PathEffect effects = new DashPathEffect(new float[]{6,6,6,6},1);
        mPaint.setPathEffect(effects);
        mPaint.setStyle(Paint.Style.STROKE); // 设置空心
        mPaint.setColor(getResources().getColor(R.color.dimgray));
//        canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 4, mPaint);
//        mPaint.setStrokeWidth(100);
//        mPaint.setColor(getResources().getColor(R.color.accent_material_dark));

//        canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 4 - 100, mPaint);
//        canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 4 - 200, mPaint);
        RectF oval = new RectF(getWidth() / 4, getHeight()/2 - getWidth()/4,getWidth()* 3 / 4,  getHeight()/2 + getWidth()/4);
        canvas.drawArc(oval, -90, 360, false, mPaint);
//        mPaint.setColor(getResources().getColor(R.color.abc_primary_text_disable_only_material_light));
//        canvas.drawArc(oval, -90, 300, false, mPaint);
        mPaint.setColor(getResources().getColor(R.color.darkgreen));
        canvas.drawArc(oval, -90, 300, false, mPaint);
    }
}
