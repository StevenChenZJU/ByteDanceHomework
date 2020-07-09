package com.bytedance.camp.chapter4.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ClockView extends View {

    private static final String TAG = "ClockView";
    private static final int FULL_CIRCLE_DEGREE = 360;
    private static final int UNIT_DEGREE = 6;
    private static final int SECOND_PER_MINUTE = 60;
    private static final int MINUTE_PER_HOUR = 60;
    private static final int SECOND_PER_HOUR = 60 * 60;
    private static final int SECOND_PER_HALFDAY = 60 * 60 * 12;
    private static final int HOUR_PER_HALFDAY = 12;

    private static final float UNIT_LINE_WIDTH = 8; // 刻度线的宽度
    private static final int HIGHLIGHT_UNIT_ALPHA = 0xFF;
    private static final int NORMAL_UNIT_ALPHA = 0x80;

    private static final float HOUR_NEEDLE_LENGTH_RATIO = 0.4f; // 时针长度相对表盘半径的比例
    private static final float MINUTE_NEEDLE_LENGTH_RATIO = 0.5f; // 分针长度相对表盘半径的比例
    private static final float SECOND_NEEDLE_LENGTH_RATIO = 0.6f; // 秒针长度相对表盘半径的比例
    private static final float NUMBER_DISTANCE_RATIO = 0.8f; // 表盘刻度数字与表盘中心距离相对表盘半径的比例
    private static final float HOUR_NEEDLE_WIDTH = 12; // 时针的宽度
    private static final float MINUTE_NEEDLE_WIDTH = 8; // 分针的宽度
    private static final float SECOND_NEEDLE_WIDTH = 4; // 秒针的宽度
    private static final float NUMBER_SIZE = 50; // 表盘刻度数字大小
    private static final float CENTER_NUMBER_SIZE = 150; // 中心显示时间数字大小

    private Calendar calendar = Calendar.getInstance();

    private float radius = 0; // 表盘半径
    private float centerX = 0; // 表盘圆心X坐标
    private float centerY = 0; // 表盘圆心Y坐标

    private List<RectF> unitLinePositions = new ArrayList<>();
    private Paint unitPaint = new Paint();
    private Paint needlePaint = new Paint();
    private Paint numberPaint = new Paint();
    private Paint centerNumberPaint = new Paint(); // 中心显示时间Paint

    private Handler handler = new Handler();


    public ClockView(Context context) {
        super(context);
        init();
    }

    public ClockView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ClockView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        unitPaint.setAntiAlias(true);
        unitPaint.setColor(Color.WHITE);
        unitPaint.setStrokeWidth(UNIT_LINE_WIDTH);
        unitPaint.setStrokeCap(Paint.Cap.ROUND);
        unitPaint.setStyle(Paint.Style.STROKE);

        // TODO 设置绘制时、分、秒针的画笔: needlePaint
        needlePaint.setAntiAlias(true);
        needlePaint.setColor(Color.WHITE);
        needlePaint.setStrokeCap(Paint.Cap.ROUND);
        needlePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        // TODO 设置绘制时间数字的画笔: numberPaint
        numberPaint.setAntiAlias(true);
        numberPaint.setTextAlign(Paint.Align.CENTER);
        numberPaint.setColor(Color.WHITE);
        numberPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        numberPaint.setTextAlign(Paint.Align.CENTER);
        numberPaint.setTextSize(NUMBER_SIZE);
        // center number(mm:ss) paint
        centerNumberPaint.setAntiAlias(true);
        centerNumberPaint.setTextAlign(Paint.Align.CENTER);
        centerNumberPaint.setColor(Color.WHITE);
        centerNumberPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        centerNumberPaint.setTextAlign(Paint.Align.CENTER);
        centerNumberPaint.setAlpha(NORMAL_UNIT_ALPHA);
        centerNumberPaint.setTextSize(CENTER_NUMBER_SIZE);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        configWhenLayoutChanged();
    }

    private void configWhenLayoutChanged() {
        float newRadius = Math.min(getWidth(), getHeight()) / 2f;
        if (newRadius == radius) {
            return;
        }
        radius = newRadius;
        centerX = getWidth() / 2f;
        centerY = getHeight() / 2f;

        // 当视图的宽高确定后就可以提前计算表盘的刻度线的起止坐标了
        for (int degree = 0; degree < FULL_CIRCLE_DEGREE; degree += UNIT_DEGREE) {
            double radians = Math.toRadians(degree);
            float startX = (float) (centerX + (radius * (1 - 0.05f)) * Math.cos(radians));
            float startY = (float) (centerX + (radius * (1 - 0.05f)) * Math.sin(radians));
            float stopX = (float) (centerX + radius * Math.cos(radians));
            float stopY = (float) (centerY + radius * Math.sin(radians));
            unitLinePositions.add(new RectF(startX, startY, stopX, stopY));
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawUnit(canvas);
        drawTimeNeedles(canvas);
        drawTimeNumbers(canvas);
        drawTime(canvas); // 绘制中心的时间, mm ss
        // TODO 实现时间的转动，每一秒刷新一次
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        }, 1000);
    }

    // 绘制表盘上的刻度
    private void drawUnit(Canvas canvas) {
        Time time = getCurrentTime();
        int curSecond = time.getSeconds();
        for (int i = 0; i < unitLinePositions.size(); i++) {
            if ((i + 15) % 60 == curSecond) {
                unitPaint.setAlpha(HIGHLIGHT_UNIT_ALPHA);
            } else {
                unitPaint.setAlpha(NORMAL_UNIT_ALPHA);
            }
            RectF linePosition = unitLinePositions.get(i);
            if(i % 5 == 0) {
                canvas.drawLine(linePosition.left, linePosition.top, linePosition.right, linePosition.bottom, unitPaint);
            }
            else {
                canvas.drawPoint((linePosition.left + linePosition.right) / 2,
                        (linePosition.top + linePosition.bottom) / 2, unitPaint);
            }
        }
    }

    private void drawTimeNeedles(Canvas canvas) {
        Time time = getCurrentTime();
        int hour = time.getHours();
        int minute = time.getMinutes();
        int second = time.getSeconds();
        Log.d(TAG, "Current Time: " + hour + ":" + minute + ":" + second);
        // TODO 根据当前时间，绘制时针、分针、秒针
        /**
         * 思路：
         * 1、以时针为例，计算从0点（12点）到当前时间，时针需要转动的角度
         * 2、根据转动角度、时针长度和圆心坐标计算出时针终点坐标（起始点为圆心）
         * 3、从圆心到终点画一条线，此为时针
         * 注1：计算时针转动角度时要把时和分都得考虑进去
         * 注2：计算坐标时需要用到正余弦计算，请用Math.sin()和Math.cos()方法
         * 注3：Math.sin()和Math.cos()方法计算时使用不是角度而是弧度，所以需要先把角度转换成弧度，
         *     可以使用Math.toRadians()方法转换，例如Math.toRadians(180) = 3.1415926...(PI)
         * 注4：Android视图坐标系的0度方向是从圆心指向表盘3点方向，指向表盘的0点时是-90度或270度方向，要注意角度的转换
         */
        // Draw Hour Needle
        needlePaint.setStrokeWidth(HOUR_NEEDLE_WIDTH);
        int hourDegree = (getSecond(hour, minute, second) * 360 / SECOND_PER_HALFDAY + 270) % FULL_CIRCLE_DEGREE;
        float hourEndX = (float) (centerX + radius * HOUR_NEEDLE_LENGTH_RATIO * Math.cos(Math.toRadians(hourDegree)));
        float hourEndY = (float) (centerY + radius * HOUR_NEEDLE_LENGTH_RATIO * Math.sin(Math.toRadians(hourDegree)));
        canvas.drawLine(centerX, centerY, hourEndX, hourEndY, needlePaint);

        // Draw Minute Needle
        needlePaint.setStrokeWidth(MINUTE_NEEDLE_WIDTH);
        int minuteDegree = (getSecond(minute, second) * 360 / SECOND_PER_HOUR + 270) % FULL_CIRCLE_DEGREE;
        float minuteEndX = (float) (centerX + radius * MINUTE_NEEDLE_LENGTH_RATIO * Math.cos(Math.toRadians(minuteDegree)));
        float minuteEndY = (float) (centerY + radius * MINUTE_NEEDLE_LENGTH_RATIO * Math.sin(Math.toRadians(minuteDegree)));
        canvas.drawLine(centerX, centerY, minuteEndX, minuteEndY, needlePaint);

        // Draw second Needle
        needlePaint.setStrokeWidth(SECOND_NEEDLE_WIDTH);
        int secondDegree = (second * 360 / SECOND_PER_MINUTE + 270) % 360;
        float secondEndX = (float) (centerX + radius * SECOND_NEEDLE_LENGTH_RATIO * Math.cos(Math.toRadians(secondDegree)));
        float secondEndY = (float) (centerY + radius * SECOND_NEEDLE_LENGTH_RATIO * Math.sin(Math.toRadians(secondDegree)));
        canvas.drawLine(centerX, centerY, secondEndX, secondEndY, needlePaint);
    }

    private void drawTimeNumbers(Canvas canvas) {
        // TODO 绘制表盘时间数字（可选）
        Time time = getCurrentTime();
        int curHour = time.getHours() % HOUR_PER_HALFDAY;
        float originFrontSize = numberPaint.getTextSize(); // 保存原始的字体大小
        for (int hour = 1; hour <= HOUR_PER_HALFDAY; hour++) {
            // starting from 1 to 12
            int numberDegree = (hour * FULL_CIRCLE_DEGREE / HOUR_PER_HALFDAY + 270) % FULL_CIRCLE_DEGREE;
            float numberX = (float) (centerX + radius * NUMBER_DISTANCE_RATIO * Math.cos(Math.toRadians(numberDegree)));
            float numberY = (float) (centerY + radius * NUMBER_DISTANCE_RATIO * Math.sin(Math.toRadians(numberDegree)));
            if (curHour == hour) {
                // 显示当前小时对应的刻度数字
                numberPaint.setTextSize(2 * originFrontSize); // 两倍字体
                numberPaint.setAlpha(HIGHLIGHT_UNIT_ALPHA); // 加重显示
                drawTextCenter(canvas, numberX, numberY, String.format(Locale.US,"%02d", hour), numberPaint);
                // convert to origin size
                numberPaint.setTextSize(originFrontSize); // 恢复原始字体大小
            }
            else {
                numberPaint.setAlpha(NORMAL_UNIT_ALPHA);
                drawTextCenter(canvas, numberX, numberY, String.format(Locale.US,"%02d", hour), numberPaint);
            }
        }
    }

    // 绘制中心的(分钟mm秒钟ss)
    private void drawTime(Canvas canvas) {
        Time time = getCurrentTime();
        int curMinute = time.getMinutes();
        int curSecond = time.getSeconds();
        String strMinuteSecond = String.format(Locale.US,"%02d·%02d", curMinute, curSecond);
        drawTextCenter(canvas, centerX, centerY, strMinuteSecond, centerNumberPaint);
    }

    // 绘制以矩形中心为居中点的Text
    private void drawTextCenter(Canvas canvas, float txCenterX, float txCenterY, String text, Paint paint) {
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float top = fontMetrics.top;
        float bottom = fontMetrics.bottom;
        float baseLineY = txCenterY + ((bottom - top) / 2 - bottom);
        canvas.drawText(text, txCenterX, baseLineY, paint);
        if(txCenterY == centerY)
            Log.d(TAG, "baseLineY:" + baseLineY + "; centerY:" + centerY);
    }

    // 获取当前的时间：时、分、秒
    private Time getCurrentTime() {
        calendar.setTimeInMillis(System.currentTimeMillis());
        return new Time(
                calendar.get(Calendar.HOUR),
                calendar.get(Calendar.MINUTE),
                calendar.get(Calendar.SECOND));
    }

    // 当前半天中经过的秒数
    private int getSecond(int hour, int minute, int second) {
        return ((hour % HOUR_PER_HALFDAY) * MINUTE_PER_HOUR + minute) * SECOND_PER_MINUTE + second;
    }
    // 当前小时中经过的秒数
    private int getSecond(int minute, int second) {
        return (minute * SECOND_PER_MINUTE + second);
    }
}
