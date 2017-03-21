package com.cxy.firststudio.myviews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.v4.content.ContextCompat;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.cxy.firststudio.R;
import com.cxy.firststudio.bean1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by hasee on 2016/9/18.
 */
public class mDiagramTable1 extends View {
    private float mDensity;
    private TextPaint mTextPaint;
    private TextPaint mTextMinPaint;
    private Paint linePaint;
    private Paint pointPaint;
    private int TEXTSIZE_1 = 16;
    private int TEXTSIZE_2 = 12;
    private int width = 100;
    private int height = 50;
    private int text1Hight = 10;
    private int text2Hight = 10;
    private float padding = 10;

    private List<bean1> m_list;

    public mDiagramTable1(Context context) {
        super(context);
        InitView();
    }

    public mDiagramTable1(Context context, AttributeSet attrs) {
        super(context, attrs);
        InitView();
    }

    public mDiagramTable1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        InitView();
    }

    public void setData(){
        m_list = new ArrayList<>();
        for (int i = 0; i < 340; i++) {
            bean1 b = new bean1();
            b.time = i+"";
            Random rd = new Random();
            b.num = rd.nextInt(100);
            m_list.add(b);
        }

    }

    private void InitView(){
        mDensity = getContext().getResources().getDisplayMetrics().density;
        mTextPaint = new TextPaint();
        // 去锯齿
        mTextPaint.setAntiAlias(true);
        // 防抖动
        mTextPaint.setDither(true);
        mTextPaint.setTextSize(TEXTSIZE_1 * mDensity);
        mTextPaint.setColor(ContextCompat.getColor(getContext(), R.color.white));
        mTextMinPaint = new TextPaint(mTextPaint);
        mTextMinPaint.setTextSize(TEXTSIZE_2 * mDensity);
        linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setDither(true);
        linePaint.setColor(ContextCompat.getColor(getContext(), R.color.white));
        pointPaint = new Paint(linePaint);
        pointPaint.setColor(ContextCompat.getColor(getContext(), R.color.white));
        padding = 5*mDensity;

        setData();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        width = getWidth();
        height = getHeight();
        text1Hight = getStringHeight("0", mTextPaint);
        text2Hight = getStringHeight("0", mTextMinPaint);
        int startY = text1Hight + text2Hight;
        int stopY = height - text2Hight;

        drawYline(canvas);
        drawYtext(canvas);
        drawToptext(canvas);
        drawXline(canvas);
        drawXlastWords(canvas);
        drawDiagram(canvas);
    }

    private void drawYline(Canvas canvas){
        canvas.drawLine(padding*2 + getStringWidth("00", mTextMinPaint),text1Hight + text2Hight, padding*2 + getStringWidth("00", mTextMinPaint),height - text2Hight, linePaint );
        for (int i = 0; i < 5; i++) {
            canvas.drawLine(padding*2 + getStringWidth("00", mTextMinPaint), (height - text2Hight) - (height - 2*text2Hight -text1Hight)/5 * i , padding*2 + getStringWidth("00", mTextMinPaint) + 10, (height - text2Hight) - (height - 2*text2Hight -text1Hight)/5 * i , linePaint);
        }
    }
    private void drawYtext(Canvas canvas){
        for (int i = 0; i < 5; i++) {
            linePaint.setStrokeWidth(2);
            canvas.drawText(""+ (20 * i),  padding, (height - text2Hight) - (height - 2*text2Hight -text1Hight)/5 * i, mTextMinPaint);
        }
    }
    private void drawToptext(Canvas canvas){
        canvas.drawText("12小时内胎动",padding,text1Hight, mTextPaint);
        canvas.drawText("次数",padding, text1Hight +text2Hight, mTextMinPaint);
    }
    private void drawXline(Canvas canvas){
        canvas.drawLine(padding*2 + getStringWidth("00", mTextMinPaint) , height - text2Hight, width - getStringWidth("天", mTextMinPaint)*2, height - text2Hight, mTextMinPaint);
    }
    private void drawXlastWords(Canvas canvas){
        mTextMinPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("天", width - getStringWidth("天", mTextMinPaint), height - text2Hight/2, mTextMinPaint);
    }

    private void drawDiagram(Canvas canvas){
        float xstart = (padding*2 + getStringWidth("00", mTextMinPaint));
        float yMax = height - text2Hight - (height - text2Hight*2 - text1Hight);
        if (m_list.size() <= 1){
            return;
        }
        Point[] points = new Point[m_list.size()];
        for (int i = 0; i < (m_list.size()-1); i++) {
            if ((i*100 + offsetX) < 0) {
                int fhra = m_list.get(i).num;
                if((i+1)*100 + offsetX > 0 ){
                    int fhrb = m_list.get(i+1).num;
                    points[i] = new Point((int)xstart, (int) ((Math.abs(fhra - fhrb) * Math.abs(xstart + (i+1)*100 + offsetX)) / Math.abs(xstart + (i)*100 + offsetX)));//(int)((((yMax / 100f * fhrb) - (yMax / 100f * fhra)) * (xstart + (i+1)*100 + offsetX))/ (float)(Math.abs((xstart + (i+1)*100 + offsetX) - (xstart + (i)*100 + offsetX)))));
                }else
                points[i] = new Point((int)(padding*2 + getStringWidth("00", mTextMinPaint)), height - text2Hight);
                continue;
            }
            int fhr1 = m_list.get(i).num;
            points[i] = new Point((int) (padding*2 + getStringWidth("00", mTextMinPaint) + i*100 + offsetX), height - text2Hight - (height - text2Hight*2 - text1Hight) / 100 * fhr1);
        }


        for(int i=0;i<points.length-1;i++)
        {
            Point startp=points[i];
            Point endp=points[i+1];
            if(startp == null || endp == null){
                continue;
            }
            canvas.drawLine(startp.x, startp.y, endp.x, endp.y, linePaint);
        }
    }

    private float offsetX;
    private float oldX;

    //改变值大小可改变滑动速度
    private float offsetValue = 15;
    private boolean startMove = false;

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                oldX = event.getRawX();
                getParent().requestDisallowInterceptTouchEvent(true);
                startMove = true;
                break;
            case MotionEvent.ACTION_MOVE:
                float rawX = event.getRawX();
                /*if(oldX == 0){
                    oldX = rawX;
                    break;
                }
                offsetX = rawX - oldX;*/
                if ((rawX - oldX) < 0)
                    offsetX -= offsetValue;
                else
                    offsetX += offsetValue;
                if(m_list != null && padding*2 + getStringWidth("00", mTextMinPaint)  > width - getStringWidth("天", mTextMinPaint)*2) {
                    if (offsetX < width - getStringWidth("天", mTextMinPaint)*2 - padding*2 - getStringWidth("00", mTextMinPaint)) {
                        offsetX = width - getStringWidth("天", mTextMinPaint)*2 - padding*2 - getStringWidth("00", mTextMinPaint);
                    }
                }
                if(offsetX > 0)
                    offsetX = 0;
                Log.d("chen", "dispatchTouchEvent: offsetX="+offsetX);
                oldX = rawX;
                this.postInvalidate();
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                startMove = false;
                break;
            default:
                break;
        }
        return true;
    }

    private int getStringHeight(String str , TextPaint mPaint) {
        Paint.FontMetrics fr = mPaint.getFontMetrics();
        return (int) Math.ceil(fr.descent - fr.top) + 2;
    }

    private int getStringWidth(String str, TextPaint mPaint) {
        return (int) mPaint.measureText(str);
    }
}
