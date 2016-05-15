package com.wuyuanqing.smartsocket.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.wuyuanqing.smartsocket.R;

import org.xclcharts.chart.CustomLineData;
import org.xclcharts.chart.LineChart;
import org.xclcharts.chart.LineData;
import org.xclcharts.common.DensityUtil;
import org.xclcharts.common.IFormatterDoubleCallBack;
import org.xclcharts.common.IFormatterTextCallBack;
import org.xclcharts.renderer.XEnum;
import org.xclcharts.renderer.info.AnchorDataPoint;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by wyq on 2016/5/13.
 */
public class LineChartView extends DemoView implements Runnable {

    private String TAG = "LineChartView";
    private LineChart chart = new LineChart();

    //标签集合
    private LinkedList<String> labels = new LinkedList<String>();
    private LinkedList<LineData> chartData = new LinkedList<LineData>();
    private List<CustomLineData> mCustomLineDataset = new LinkedList<CustomLineData>();

    //批注
    List<AnchorDataPoint> mAnchorSet = new ArrayList<AnchorDataPoint>();
    public LineChartView(Context context) {
        this(context, null);
    }

    public LineChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineChartView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    private void initView()
    {
        chartLabels();
        chartDataSet();
        chartDesireLines();
        chartRender();
        new Thread(this).start();

        //綁定手势滑动事件
       // this.bindTouch(this,chart);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //图所占范围大小
        chart.setChartRange(w,h);
    }

    private void chartRender()
    {
        try {

            //设置绘图区默认缩进px值,留置空间显示Axis,Axistitle....
            int [] ltrb = getBarLnDefaultSpadding();
            chart.setPadding(DensityUtil.dip2px(getContext(), 30),ltrb[1], ltrb[2],  ltrb[3]);


            //设定数据源
            chart.setCategories(labels);
            //	chart.setDataSource(chartData);
            //chart.setCustomLines(mCustomLineDataset);

            //数据轴最大值
            chart.getDataAxis().setAxisMax(7);
            //数据轴刻度间隔
            chart.getDataAxis().setAxisSteps(1);
            //指隔多少个轴刻度(即细刻度)后为主刻度
            chart.getDataAxis().setDetailModeSteps(1);

            //背景网格
            chart.getPlotGrid().showHorizontalLines();

            //标题
            chart.setTitle("一周记录");
            chart.addSubtitle("(05.05-05.12)");

            //隐藏顶轴和右边轴
            //chart.hideTopAxis();
            //chart.hideRightAxis();

            //设置轴风格

            //chart.getDataAxis().setTickMarksVisible(false);
            chart.getDataAxis().getAxisPaint().setStrokeWidth(2);
            chart.getDataAxis().getTickMarksPaint().setStrokeWidth(2);
            chart.getDataAxis().showAxisLabels();

            chart.getCategoryAxis().getAxisPaint().setStrokeWidth(2);
            chart.getCategoryAxis().hideTickMarks();

            //定义数据轴标签显示格式
            chart.getDataAxis().setLabelFormatter(new IFormatterTextCallBack(){

                @Override
                public String textFormatter(String value) {
                    // TODO Auto-generated method stub
                    Double tmp = Double.parseDouble(value);
                    DecimalFormat df=new DecimalFormat("#0");
                    String label = df.format(tmp).toString();
                    return (label);
                }

            });


            //定义线上交叉点标签显示格式
            chart.setItemLabelFormatter(new IFormatterDoubleCallBack() {
                @Override
                public String doubleFormatter(Double value) {
                    // TODO Auto-generated method stub
                    DecimalFormat df=new DecimalFormat("#0.0");
                    String label = df.format(value).toString();
                    return label;
                }});

            //chart.setItemLabelFormatter(callBack)

            //允许线与轴交叉时，线会断开
            chart.setLineAxisIntersectVisible(false);

            //chart.setDataSource(chartData);
            //动态线
            chart.showDyLine();

            //不封闭
            chart.setAxesClosed(false);

            //扩展绘图区右边分割的范围，让定制线的说明文字能显示出来
            chart.getClipExt().setExtRight(200.f);

            //设置标签交错换行显示
            chart.getCategoryAxis().setLabelLineFeed(XEnum.LabelLineFeed.ODD_EVEN);

            //仅能横向移动
           // chart.setPlotPanMode(XEnum.PanMode.HORIZONTAL);


            //chart.getDataAxis().hide();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    //计算下平均线
    private double calcAvg()
    {
//        double total = 400d + 480d + 500d + 560d + 800d + 950d +1200d + 630d + 710d;
//        double yearNumber = 9d;
        double total = 2d + 1.5d + 3d + 2.5d + 1.2d + 3.3d +4.0d ;
        double yearNumber = 7d;

        return (total/yearNumber);
    }

    private void chartDataSet()
    {
        //Line 1
//        LinkedList<Double> dataSeries1= new LinkedList<Double>();
//        dataSeries1.add(400d);
//        dataSeries1.add(480d);
//        dataSeries1.add(500d);
//        dataSeries1.add(560d);
//        LineData lineData1 = new LineData("单间(5层光线好)",dataSeries1, Color.rgb(234, 83, 71));
//        lineData1.setDotStyle(XEnum.DotStyle.DOT);

        //Line 2
        LinkedList<Double> dataSeries2= new LinkedList<Double>();
        dataSeries2.add(2d);
        dataSeries2.add(1.5d);
        dataSeries2.add(3d);
        dataSeries2.add(2.5d);
        dataSeries2.add(1.2d);
        dataSeries2.add(3.3d);
        dataSeries2.add(4.0d);

        LineData lineData2 = new LineData("单日电量/度",dataSeries2,Color.rgb(75, 166, 51));
        lineData2.setDotStyle(XEnum.DotStyle.PRISMATIC);
        lineData2.getPlotLine().getDotPaint().setColor(Color.rgb(234, 142, 43));
        lineData2.getDotLabelPaint().setColor(Color.rgb(234, 142, 43));
        lineData2.setLabelVisible(true);
        lineData2.getLabelOptions().getBox().getBackgroundPaint().setColor(Color.rgb(76, 76, 76));
        //lineData2.getPlotLabel().hideBox();

        //Line 3
//        LinkedList<Double> dataSeries3= new LinkedList<Double>();
//        dataSeries3.add(0d);
//        dataSeries3.add(0d);
//        dataSeries3.add(0d);
//        dataSeries3.add(0d);
//        dataSeries3.add(0d);
//        dataSeries3.add(0d);
//        dataSeries3.add(0d);
//        dataSeries3.add(630d);
//        dataSeries3.add(710d);
//
//        LineData lineData3 = new LineData("单间(9层无电梯)",dataSeries3,Color.rgb(123, 89, 168));
//        lineData3.setDotStyle(XEnum.DotStyle.DOT);

        //轴上分界线的交叉点
        LinkedList<Double> dataSeries4= new LinkedList<Double>();
        dataSeries4.add(5d);
//        LinkedList<Double> dataSeries5= new LinkedList<Double>();
//        dataSeries5.add(3000d);
        LinkedList<Double> dataSeries6= new LinkedList<Double>();
        dataSeries6.add(calcAvg());

        LineData lineData4 = new LineData("",dataSeries4,Color.RED);
        lineData4.setDotStyle(XEnum.DotStyle.RECT);
//        LineData lineData5 = new LineData("",dataSeries5,Color.rgb(69, 181, 248));
//        lineData5.setDotStyle(XEnum.DotStyle.RECT);
        LineData lineData6 = new LineData("",dataSeries6,getResources().getColor(R.color.light_blue));
        lineData6.setDotStyle(XEnum.DotStyle.TRIANGLE);

//        chartData.add(lineData1);
        chartData.add(lineData2);
//        chartData.add(lineData3);

        chartData.add(lineData4);
 //       chartData.add(lineData5);
        chartData.add(lineData6);


        //批注
        //List<AnchorDataPoint> mAnchorSet = new ArrayList<AnchorDataPoint>();
//        AnchorDataPoint an4 = new AnchorDataPoint(0,2,XEnum.AnchorStyle.CAPRECT);
//        an4.setAnchor("批注");
//        an4.setBgColor(Color.rgb(255, 145, 126));
//        mAnchorSet.add(an4);
//        chart.setAnchorDataPoint(mAnchorSet);
    }

    private void chartLabels()
    {
        labels.add("周日");
        labels.add("周一");
        labels.add("周二");
        labels.add("周三");
        labels.add("周四");
        labels.add("周五");
        labels.add("周六");

//        labels.add("2013");
//        labels.add("2014");
    }

    /**
     * 期望线/分界线
     */
    private void chartDesireLines()
    {
        mCustomLineDataset.add(new CustomLineData("[预警线]",5d,Color.RED,5));
//        mCustomLineDataset.add(new CustomLineData("舒适",3000d,Color.rgb(69, 181, 248),5));
        mCustomLineDataset.add(new CustomLineData("[平均线]",calcAvg(),getResources().getColor(R.color.light_blue),6));
    }

    @Override
    public void render(Canvas canvas) {
        try{
            chart.render(canvas);
        } catch (Exception e){
            Log.e(TAG, e.toString());
        }
    }


    @Override
    public void run() {
        try {
            chartAnimation();
        }
        catch(Exception e) {
            Thread.currentThread().interrupt();
        }
    }

    private void chartAnimation()
    {
        try {
            int count =  chartData.size();
            for(int i=0;i< count ;i++)
            {
                Thread.sleep(150);
                LinkedList<LineData> animationData = new LinkedList<LineData>();
                for(int j=0;j<=i;j++)
                {
                    animationData.add(chartData.get(j));
                }

                //Log.e(TAG,"size = "+animationData.size());
                chart.setDataSource(animationData);
                if(i == count - 1)
                {
                    chart.getDataAxis().show();
                    chart.getDataAxis().showAxisLabels();

                    chart.setCustomLines(mCustomLineDataset);
                }
                postInvalidate();
            }
        }
        catch(Exception e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

//        if(event.getAction() == MotionEvent.ACTION_UP)
//        {
//            //交叉线
//            if(chart.getDyLineVisible())
//            {
//                chart.getDyLine().setCurrentXY(event.getX(),event.getY());
//                if(chart.getDyLine().isInvalidate())this.invalidate();
//            }
//        }
        return true;
    }

}
