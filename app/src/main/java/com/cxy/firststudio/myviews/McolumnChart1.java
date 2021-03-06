package com.cxy.firststudio.myviews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Scroller;

import com.cxy.firststudio.Utils.PlanListBean;
import com.cxy.firststudio.Utils.TimeFormatUtil;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Administrator on 2016/3/7.
 */
public class McolumnChart1 extends View {

    // 刻度间隔距离
    private static final int ITEM_DIVIDER = 20;
    //图形宽度
    private int ITEM_WIDTH;
    //字体大小
    private static final int TEXT_SIZE = 12;
    //屏幕参数
    private float mDensity;

//    private Scroller mScroller;
    private int mMaxValue = 100, mMinValue = 0;
//    private VelocityTracker mVelocityTracker;
//    private int mMinVelocity;
    private int mLastX, mMove;
    private int mWidth, mHeight;
    private PlanListBean mBean;
    //画笔
    private Paint ItembgPaint;//item背景的画笔
    private Paint betterPaint;//效率为优的画笔
    private Paint goodPaint;//效率为良的画笔
    private Paint poorPaint;//效率为差的画笔
    private Paint todayPaint;//今天的画笔
    private Paint afterDaysPaint;//今天之后的画笔
    private Paint WhitePaint;//白色
    private Paint mTrancePaint;//白色
    private TextPaint mtextPaint;
    private PorterDuffXfermode mXfermode;
    private int COLOR_BG = 0x7fedeced;
    private int COLOR_TODAY = 0xff6f9ef6;
    private int COLOR_AFTER = 0x7F888888;
    private int COLOR_BETTER = Color.GREEN;
    private int COLOR_POOR = 0xffff5722;
    private int COLOR_WHITE = 0x7fffffff;
    private float fontHeight;
    private int mValue = 0;


    public McolumnChart1(Context context) {
        super(context);
        mDensity = getContext().getResources().getDisplayMetrics().density;
        initPaints();
        initXfermode();
    }

    public McolumnChart1(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    private void initPaints(){
        ItembgPaint = new Paint();
        ItembgPaint.setColor(COLOR_BG);
        ItembgPaint.setStyle(Paint.Style.FILL);

        todayPaint = new Paint();
        todayPaint.setColor(COLOR_TODAY);
        todayPaint.setAntiAlias(true);
        todayPaint.setStyle(Paint.Style.FILL);

        afterDaysPaint = new Paint(todayPaint);
        afterDaysPaint.setColor(COLOR_AFTER);

        betterPaint = new Paint(todayPaint);
        betterPaint.setColor(COLOR_BETTER);

        WhitePaint = new Paint(ItembgPaint);
        WhitePaint.setColor(COLOR_WHITE);

        mTrancePaint = new Paint(ItembgPaint);
        mTrancePaint.setColor(Color.TRANSPARENT);

        poorPaint = new Paint(todayPaint);
        poorPaint.setColor(COLOR_POOR);

        mtextPaint = new TextPaint();
        mtextPaint.setAntiAlias(true);
        // 防抖动
        mtextPaint.setDither(true);
        mtextPaint.setTextSize(TEXT_SIZE * mDensity);
        mtextPaint.setTextAlign(Paint.Align.CENTER);
        mtextPaint.setColor(Color.GREEN);

        Paint.FontMetrics mfontMetrics = mtextPaint.getFontMetrics();
        //计算文字高度
        fontHeight = mfontMetrics.bottom - mfontMetrics.top;
    }

    private void initXfermode() {
        // 叠加处绘制源图
        mXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP);
    }

    public void setValue(PlanListBean bean){
        mBean = bean;
        invalidate();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        mWidth = getWidth();
        mHeight = getHeight();
        ITEM_WIDTH = mWidth;
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(mBean == null){
            return;
        }
        float xPosition = 0f;


            xPosition = 0;
            //画每一项的背景
            RectF rectbg = new RectF(xPosition, 0, xPosition+ITEM_WIDTH, mHeight);
            canvas.drawRect(rectbg, ItembgPaint);

            //画前景色
            if(mBean.getDateline().equals(TimeFormatUtil.getTodayTimeyyyyMMdd())){//如果是今天
                drawItems(canvas, todayPaint, xPosition, mHeight - fontHeight *2, mBean.getXiaolv());
                canvas.drawText("今日", xPosition + ITEM_WIDTH / 2, fontHeight, mtextPaint);
                canvas.drawText("计划", xPosition + ITEM_WIDTH / 2, 2 * fontHeight, mtextPaint);
                mtextPaint.setColor(Color.WHITE);
                canvas.drawText(TimeFormatUtil.ExchangeTimeformat(mBean.getDateline(),"yyyyMMdd", "MM/dd"),
                        xPosition+ITEM_WIDTH/2, mHeight - fontHeight/2, mtextPaint);
            }else{//如果不是今天   今天之前
                if(TimeFormatUtil.isDateBiger(TimeFormatUtil.getTodayTimeyyyyMMdd(), mBean.getDateline(), "yyyyMMdd")){
                    if(mBean.getXiaolv() > 0.7f){//优
                        drawItems(canvas, betterPaint, xPosition, mHeight - fontHeight *2, mBean.getXiaolv());
                    }else{//差
                        drawItems(canvas, poorPaint, xPosition, mHeight - fontHeight *2, mBean.getXiaolv());
                    }
                    canvas.drawText(new BigDecimal(mBean.getXiaolv()*100).setScale(0, BigDecimal.ROUND_HALF_UP).intValue() + "%",
                            xPosition + ITEM_WIDTH / 2, fontHeight , mtextPaint);
                }else{//今天之后
                    drawItems(canvas, afterDaysPaint, xPosition, mHeight - fontHeight *2, mBean.getXiaolv());
                }
                mtextPaint.setColor(Color.GRAY);
                canvas.drawText(TimeFormatUtil.ExchangeTimeformat(mBean.getDateline(), "yyyyMMdd", "MM/dd"),
                        xPosition + ITEM_WIDTH / 2, mHeight - fontHeight/2, mtextPaint);
            }


        //画底部白线
        WhitePaint.setStrokeWidth((int) (fontHeight * 1.4));
        canvas.drawLine(0f, (float) (mHeight - (fontHeight * 0.7)), mWidth, (float) (mHeight - (fontHeight * 0.7)), WhitePaint);
    }


    private void drawItems(Canvas canvas, Paint mPaint, float x_length, float maxLength, float per){
        canvas.save();
        float lineHeight = mHeight - maxLength * per;
        mPaint.setStrokeWidth(ITEM_WIDTH);
        canvas.drawLine(x_length + ITEM_WIDTH/2, mHeight, x_length + ITEM_WIDTH/2, lineHeight, mPaint);
        RectF oval = new RectF();
        oval.left = x_length;
        oval.right = x_length + ITEM_WIDTH;
        oval.bottom = lineHeight + TEXT_SIZE * mDensity + 2 ;
        oval.top = lineHeight - TEXT_SIZE * mDensity;
        mPaint.setStrokeWidth(ITEM_WIDTH / 2);
        canvas.drawArc(oval, 180, 180, true, mPaint);
        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        int xPos = (int) event.getX();


        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastX = xPos;
                mMove = 0;
                break;
            case MotionEvent.ACTION_MOVE:
                mMove += (xPos - mLastX);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
//                countMoveEnd();
//                countVelocityTracker(event);
                return false;
            // break;
            default:
                break;
        }
        return true;
    }

}
