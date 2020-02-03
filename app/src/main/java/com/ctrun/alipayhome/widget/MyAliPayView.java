package com.ctrun.alipayhome.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class MyAliPayView extends View {

    //****** 3个构造函数，无需赘述 *************
    public MyAliPayView(Context context) {
        this(context, null);
    }

    public MyAliPayView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyAliPayView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    //***********全局变量设定*************
    private Paint mPaint;//画笔

    //圆弧（这里我用是正圆的圆弧）相关参数
    private int mCenterX, mCenterY; //圆心位置的X,Y
    private int mRadio = 80;//半径，圆弧半径
    private RectF mRectArc = new RectF();//画弧线用的辅助矩形
    private final float mDeltaAngle = 10;//每次刷新时，角度的变化，这个变量影响动画的速率
    private final float mMaxSwipeAngle = 200;// 确定一个最大角度
    private float mCurrentSwipeAngle = 0;//弧线当前扫过的角度
    private float mStartAngle = 0;//一旦当前角度达到最大值，那么不再增加，而是进行旋转
    private ProgressTag mProgressTag = ProgressTag.PROGRESS_0;//弧线绘制的过程

    //画对勾 √ 相关参数
    private float x1, y1, x2, y2, x3, y3;//构成对勾的3个坐标(x1,y1)(x2,y2)(x3,y3)
    private float mDeltaLineDis = 10;// 勾勾在X轴上每次刷新移动的距离,这个值影响对勾的绘制速率
    private Line line1, line2;// 两段直线
    private float xDis3to1;// Point3 到 point1 的X跨度（它的作用是 用来限制对勾第二条直线的绘制范围）
    private float xDis2to1;// Point2 到 point1 的X跨度（它的作用是 用来限制对勾第一条直线的绘制范围）
    private float mCurrentXDis;// 画√的时候X轴跨度

    private State mCurrentStatus = State.STATUS_IDLE;//默认闲置状态

    /**
     * 设置状态(状态决定动画效果)
     *
     * @param currentStatus
     */
    public void setStatus(State currentStatus) {
        this.mCurrentStatus = currentStatus;
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCenterX = w / 2;
        mCenterY = h / 2 - 70;
        mRectArc.set(mCenterX - mRadio, mCenterY - mRadio, mCenterX + mRadio, mCenterY + mRadio);

        //确定对勾的两条Line的起点和终点
        x1 = mCenterX - mRadio / 3 * 2;
        y1 = mCenterY + mRadio / 8;
        x2 = mCenterX - mRadio / 5;
        y2 = mCenterY + mRadio / 3 * 2;
        x3 = mCenterX + mRadio / 4 * 3;
        y3 = mCenterY - mRadio / 4;
        //确定两条直线; //把勾勾一次性画出来倒是不难，难的是，这个动态过程如何写
        line1 = new Line(new PointF(x1, y1), new PointF(x2, y2));
        line2 = new Line(new PointF(x2, y2), new PointF(x3, y3));

        xDis3to1 = x3 - x1;
        xDis2to1 = x2 - x1;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        switch (mCurrentStatus) {
            case STATUS_IDLE://闲置状态,画出完整图形即可
                reset();
                canvas.drawArc(mRectArc, -90 + mStartAngle, 360, false, mPaint);//画出完整圆弧360度
                canvas.drawLine(line1.startP.x, line1.startP.y, line1.endP.x, line1.endP.y, mPaint);//第1条直线，保持完整
                canvas.drawLine(line2.startP.x, line2.startP.y, line2.endP.x, line2.endP.y, mPaint);//第2条直线，保持完整
                break;
            case STATUS_PROCESS://执行中状态,两种弧线轮流绘制
                //这里有个-90，这是因为画弧线，默认是从第一象限X正向开始画，我要从Y正向开始顺时针的话，就必须把起点逆时针90度
                canvas.drawArc(mRectArc, -90 + mStartAngle, mCurrentSwipeAngle, false, mPaint);

                //两个阶段轮流执行
                if (mProgressTag == ProgressTag.PROGRESS_0) {
                    if (mCurrentSwipeAngle <= mMaxSwipeAngle)//如果扫过角度小于最大角度
                        mCurrentSwipeAngle += mDeltaAngle;//就让扫过的角度继续递增
                    else//如果扫过角度到达了最大角度
                        mStartAngle += mDeltaAngle;//那就让起始角度值递增
                    if (mStartAngle + mCurrentSwipeAngle >= 360) {// 如果弧线末端到达了Y轴正向
                        mProgressTag = ProgressTag.PROGRESS_1;//就切换成阶段1
                    }
                } else if (mProgressTag == ProgressTag.PROGRESS_1) {//阶段1：
                    mCurrentSwipeAngle -= mDeltaAngle;//扫过角度递减
                    mStartAngle += mDeltaAngle;//起始角度值递增

                    if (mCurrentSwipeAngle <= 0) {//如果起始角度值 递减直至0
                        mStartAngle = 0;
                        mProgressTag = ProgressTag.PROGRESS_0;//就切换阶段0
                    }
                }
                invalidate();//这种模式下，动画总是要循环执行，所以，无条件刷新
                break;
            case STATUS_FINISH:// 已完成状态
                // 先画一个完整圆弧，然后画对勾
                //这里有两个阶段，
                canvas.drawArc(mRectArc, -90 + mStartAngle, mCurrentSwipeAngle, false, mPaint);//以当前两个参数继续画圆弧，直到画完整
                // 接着当前的圆弧进行绘制
                mCurrentSwipeAngle += mDeltaAngle;//扫过的角度递增
                if (mCurrentSwipeAngle <= 360)// 如果扫过角度没达到了360°，就继续递增
                    invalidate();
                else {//如果扫过角度，超过了360°，说明圆弧已经完整
                    //这里就开始画 对勾
                    // 对勾包括两条直线 line1，line2 ，思路为：line1的起点X开始向右扫描，逐步画出第一条直线,直到线段1的X，然后逐步画出第二条直线
                    mCurrentXDis += mDeltaLineDis;// 画√的时候X轴跨度
                    if (mCurrentXDis < xDis2to1) {//跨度在第一条直线的跨度范围之内时
                        canvas.drawLine(line1.startP.x, line1.startP.y, line1.startP.x + mCurrentXDis, line1.getY(line1.startP.x + mCurrentXDis), mPaint);
                        invalidate();//继续刷新
                    } else if (mCurrentXDis < xDis3to1) {//跨度越过了第一条直线 到达 第二条直线范围内时
                        canvas.drawLine(line1.startP.x, line1.startP.y, line1.endP.x, line1.endP.y, mPaint);//第一条直线，保持完整
                        canvas.drawLine(
                                line2.startP.x,
                                line2.startP.y,
                                line2.startP.x + mCurrentXDis - xDis2to1,
                                line2.getY(line2.startP.x + mCurrentXDis - xDis2to1),
                                mPaint);//动态画第二条直线
                        invalidate();//继续刷新
                    } else {//跨度超过了，那么就画出完整图形，并且不再刷新
                        canvas.drawLine(line1.startP.x, line1.startP.y, line1.endP.x, line1.endP.y, mPaint);//第1条直线，保持完整
                        canvas.drawLine(line2.startP.x, line2.startP.y, line2.endP.x, line2.endP.y, mPaint);//第2条直线，保持完整
                        reset();
                    }
                }

                break;
        }

    }

    private void reset() {
        mCurrentSwipeAngle = 0;
        mStartAngle = 0;
        mCurrentXDis = 0;
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.parseColor("#118EE8"));
        mPaint.setAntiAlias(true);//抗锯齿
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(8);//画笔宽度
        mPaint.setStrokeCap(Paint.Cap.ROUND);//画直线的时候让线头呈现圆角
    }

    /**
     * 确定一条直线
     *
     * 直线的公式是 Y = kX + b
     *
     * 剩下的，，，懂得都懂，不懂的，问初中老师吧 囧！
     */
    class Line {
        float k;
        float b;

        PointF startP, endP;

        Line(PointF startP, PointF endP) {
            this.startP = startP;
            this.endP = endP;

            //算出k，b
            k = (endP.y - startP.y) / (endP.x - startP.x);
            b = endP.y - k * endP.x;
        }

        // 由于动态效果需要画对勾的过程，所以我要得到任意点的Y值
        float getY(float x) {
            return k * x + b;
        }
    }

    /**
     * 画圆弧时有两个阶段
     */
    enum ProgressTag {
        PROGRESS_0,//阶段0：顺时针画弧线，直到弧线终点到达Y轴正向；
        PROGRESS_1//阶段1：弧线划过的角度递减
    }

    /**
     * 用枚举来做状态区分
     */
    enum State {
        STATUS_IDLE,//状态0：初始状态
        STATUS_PROCESS,//状态1：执行中
        STATUS_FINISH//状态2：已完成
    }
}
