package com.cxy.firststudio.myviews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.cxy.firststudio.Utils.UIUtils;

/**
 * Created by Administrator on 2016/3/2.
 */
public class CircleProcess extends View {

    private Paint mSrcPaint;
    private Paint mDstPaint;
    private int mTotalWidth, mTotalHeight;
    private PorterDuffXfermode mXfermode;
    private float mCycleFactorW;
    private float[] mYPositions;
    private float[] mResetOneYPositions;
    private static final int OFFSET_Y = 0;
    /**波动幅度*/
    private static final float STRETCH_FACTOR_A = 20;
    // 第一条水波移动速度
    private static final int TRANSLATE_X_SPEED_ONE = 7;
    private int mXOneOffset;
    private int mXOffsetSpeedOne;


    public CircleProcess(Context context, AttributeSet attrs) {
        super(context, attrs);

        mXOffsetSpeedOne = UIUtils.Dp2Px(context, TRANSLATE_X_SPEED_ONE);
        initPaint();
        initXfermode();
    }

    private void initPaint(){
        mSrcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSrcPaint.setFilterBitmap(true);
        mSrcPaint.setDither(true);
        mSrcPaint.setColor(Color.WHITE);

        mDstPaint = new Paint(mSrcPaint);
        mDstPaint.setColor(Color.GREEN);
    }

    private void initXfermode() {
        // 叠加处绘制源图
        mXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        resetPositonY();

        // 存为新图层
        int saveLayerCount = canvas.saveLayer(0, 0, mTotalWidth, mTotalHeight, mSrcPaint,
                              Canvas.ALL_SAVE_FLAG);
        // 绘制目标图
        canvas.drawCircle(mTotalWidth / 2, mTotalHeight / 2, mTotalHeight/2, mSrcPaint);
        // 设置混合模式
        mDstPaint.setXfermode(mXfermode);
        // 绘制源图形

        for (int i = 0; i < mTotalWidth; i++) {
//            canvas.drawPoint(i, mTotalHeight - mResetOneYPositions[i] - 400, mDstPaint);
            canvas.drawLine(i,  mTotalHeight - mResetOneYPositions[i] - 400, i ,  mTotalHeight, mDstPaint);
        }



        // 清除混合模式
        mDstPaint.setXfermode(null);
        // 恢复保存的图层；
        canvas.restoreToCount(saveLayerCount);

        // 改变两条波纹的移动点
        mXOneOffset += mXOffsetSpeedOne;

        // 如果已经移动到结尾处，则重头记录
        if (mXOneOffset >= mTotalWidth) {
            mXOneOffset = 0;
        }
        // 引发view重绘，一般可以考虑延迟20-30ms重绘，空出时间片
        postInvalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

    }

    private void resetPositonY() {
        // mXOneOffset代表当前第一条水波纹要移动的距离
        int yOneInterval = mYPositions.length - mXOneOffset;
        // 使用System.arraycopy方式重新填充第一条波纹的数据
        System.arraycopy(mYPositions, mXOneOffset, mResetOneYPositions, 0, yOneInterval);
        System.arraycopy(mYPositions, 0, mResetOneYPositions, yOneInterval, mXOneOffset);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTotalWidth = w;
        mTotalHeight = h;
        // 用于保存原始波纹的y值
        mYPositions = new float[mTotalWidth];
        // 用于保存波纹一的y值
        mResetOneYPositions = new float[mTotalWidth];

        // 将周期定为view总宽度
        mCycleFactorW = (float) (2 * Math.PI / mTotalWidth);

        // 根据view总宽度得出所有对应的y值
        for (int i = 0; i < mTotalWidth; i++) {
            mYPositions[i] = (float) (STRETCH_FACTOR_A * Math.sin(mCycleFactorW * i) + OFFSET_Y);
        }
    }
}
