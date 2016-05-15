package com.wuyuanqing.smartsocket.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by wyq on 2016/5/8.
 */
public class PercentBar extends View {

    private String name = "name";
    private float maxNum = 100, minNum = 0, actuallyNum = 50;
    private int perHight = 0;
    private int backBarColor = Color.parseColor("#CCCCCC");
    private int barColor = Color.parseColor("#339933");

    private Paint textPaint, rectPaint;

    public PercentBar(Context context) {
        this(context, null);
    }

    public PercentBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PercentBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    public void setPara(String name, float maxNum, float minNum, float actuallyNum) {
        this.name = name;
        this.maxNum = maxNum;
        this.minNum = minNum;
        this.actuallyNum = actuallyNum > maxNum ? maxNum : actuallyNum;
        invalidate();
    }

    public void setBackBarColor(int color) {
        this.backBarColor = color;
    }

    public void setBarColor(int color) {
        this.barColor = color;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(20);
        //textPaint.setTextAlign(Paint.Align.CENTER);//水平居中

        perHight = getHeight() / 10;
        //Log.i("TAG", "getx:" + getX() + "  getleft" + getLeft()+"gettop"+getTop()+"gety"+getY());

        //draw barName text
        drawBarName(canvas);
        //draw maxNum text
        drawMaxNum(canvas);
        //draw backBAR
        drawBackBar(canvas);
        //drawActuralBar
        drawBar(canvas);
        //draw actuallyNum text
        drawActuallyNum(canvas);
    }

    private void drawBarName(Canvas canvas) {
        Rect nameRect = new Rect(getLeft(), getHeight() - perHight, getWidth(), getHeight());
        float nameLength = textPaint.measureText(name);
        Paint.FontMetricsInt fontMetricsInt = textPaint.getFontMetricsInt();
        int nameBaseLine = (nameRect.top + nameRect.bottom - fontMetricsInt.top - fontMetricsInt.bottom) / 2;
        canvas.drawText(name, (getWidth() - nameLength) / 2, nameBaseLine, textPaint);
    }

    private void drawMaxNum(Canvas canvas) {
        Rect maxRect = new Rect(getLeft(), 0, getLeft() + getWidth(), perHight);
        float maxLength = textPaint.measureText("" + maxNum);
        Paint.FontMetricsInt fontMetricsInt = textPaint.getFontMetricsInt();
        int maxBseLine = (maxRect.top + maxRect.bottom - fontMetricsInt.top - fontMetricsInt.bottom) / 2;
        canvas.drawText(maxNum + "", (getWidth() - maxLength) / 2, maxBseLine, textPaint);
    }

    private void drawActuallyNum(Canvas canvas) {
        float actuallyTop=0;

        if(actuallyNum/(maxNum-minNum)>0.5){
            actuallyTop = (1 - actuallyNum / (maxNum - minNum)) * getHeight() * 8 / 10+perHight;
        }else{
            actuallyTop = (1 - actuallyNum / (maxNum - minNum)) * getHeight() * 8 / 10;
        }


        Rect actuallyRect = new Rect(getLeft(), (int) actuallyTop, getWidth(), (int) actuallyTop + perHight);
        float acturallyLen = textPaint.measureText("" + actuallyNum);
        Paint.FontMetricsInt fontMetricsInt = textPaint.getFontMetricsInt();
        int actuallyBseLine = (actuallyRect.top + actuallyRect.bottom - fontMetricsInt.top - fontMetricsInt.bottom) / 2;
        canvas.drawText(actuallyNum + "", (getWidth() - acturallyLen) / 2, actuallyBseLine, textPaint);
    }


    private void drawBackBar(Canvas canvas) {
        rectPaint = new Paint();
        rectPaint.setColor(backBarColor);
        rectPaint.setAntiAlias(true);
        rectPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(0, perHight, getWidth(), getHeight() - perHight, rectPaint);
    }

    private void drawBar(Canvas canvas) {
        rectPaint = new Paint();
        rectPaint.setColor(barColor);
        float top = (1 - actuallyNum / (maxNum - minNum)) * getHeight() * 8 / 10 + perHight;
        canvas.drawRect(0, top, getWidth(), getHeight() - perHight, rectPaint);
    }
}
