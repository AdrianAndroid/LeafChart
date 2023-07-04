package com.beiing.leafchartdemo;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.beiing.leafchart.LeafLineChart;
import com.beiing.leafchart.bean.Axis;
import com.beiing.leafchart.bean.AxisValue;
import com.beiing.leafchart.bean.Line;
import com.beiing.leafchart.bean.PointValue;

import java.util.ArrayList;
import java.util.List;

public class LeafChartActivity extends AppCompatActivity {

    LeafLineChart leafLineChart;
    private final int count = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaf_chart);
        leafLineChart = (LeafLineChart) findViewById(R.id.leaf_chart);
        //测试折线图
        initLineChart();
    }

    private void initLineChart() {
        Axis axisX = new Axis(getAxisValuesX());
        axisX.setAxisColor(Color.TRANSPARENT).setTextColor(Color.DKGRAY).setHasLines(false);
        Axis axisY = new Axis(getAxisValuesY());
        axisY.setAxisColor(Color.TRANSPARENT).setTextColor(Color.DKGRAY).setHasLines(false).setShowText(false);
        leafLineChart.setAxisX(axisX);
        leafLineChart.setAxisY(axisY);

        List<Line> lines = new ArrayList<>();
        lines.add(getFoldLine());
        leafLineChart.setChartData(lines);

        leafLineChart.show();

//        leafLineChart.show();
    }

//    private List<Line> createLines() {
//        List<Line> lines = new ArrayList<>();
//
//        float divide = (float) count;
//
//        List<PointValue> values1 = new ArrayList<>();
//        values1.add(createPointValue(0 / divide, 32 / 100f));
//        values1.add(createPointValue(1 / divide, 32 / 100f));
//        lines.add(createLine(values1, Color.RED));
//
//        List<PointValue> values2 = new ArrayList<>();
//        values2.add(createPointValue(1 / divide, 32 / 100f));
//        values2.add(createPointValue(2 / divide, 28 / 100f));
//        lines.add(createLine(values2, Color.GREEN));
//
//        List<PointValue> values3 = new ArrayList<>();
//        values3.add(createPointValue(2 / divide, 28 / 100f));
//        values3.add(createPointValue(3 / divide, 26 / 100f));
//        lines.add(createLine(values3, Color.BLUE));
//
//        List<PointValue> values4 = new ArrayList<>();
//        values4.add(createPointValue(3 / divide, 26 / 100f));
//        values4.add(createPointValue(4 / divide, 22 / 100f));
//        lines.add(createLine(values4, Color.YELLOW));
//
//        return lines;
//    }

    private List<AxisValue> getAxisValuesX(){
        List<AxisValue> axisValues = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            AxisValue value = new AxisValue();
            //value.setLabel(i + 1 + "月");
            value.setLabel("");
            axisValues.add(value);
        }
        return axisValues;
    }

    private List<AxisValue> getAxisValuesY(){
        List<AxisValue> axisValues = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            AxisValue value = new AxisValue();
            value.setLabel("");
            axisValues.add(value);
        }
        return axisValues;
    }

    private Line getFoldLine() {

        float divide = (float) 3.3;

        List<PointValue> pointValues = new ArrayList<>();
        pointValues.add(createPointValue(0 / divide, 32 / 100f));
        pointValues.add(createPointValue(1 / divide, 32 / 100f));
        pointValues.add(createPointValue(2 / divide, 28 / 100f));
        pointValues.add(createPointValue(3 / divide, 26 / 100f));
        pointValues.add(createPointValue(4 / divide, 22 / 100f));


        return createLine(pointValues);
    }

    @NonNull
    private Line createLine(List<PointValue> pointValues) {
        Line line = new Line(pointValues);
        line.setLineColor(Color.WHITE)
                .setLineWidth(3)
                .setPointColor(Color.WHITE)
                .setPointRadius(3)
                .setCubic(false)
                .setFill(true)
                .setFillColor(Color.parseColor("#E57DF3FF"))
                .setSecFillColor(Color.parseColor("#E57DC9FF"))
                .setHasLabels(true)
                .setLabelColor(Color.TRANSPARENT);
        return line;
    }


    private PointValue createPointValue(float xValue, float yValue) {
        PointValue p = new PointValue();
        p.setX(xValue);
        p.setLabel(String.valueOf(yValue));
        p.setShowLabel(true);
        p.setY(yValue);
        return p;
    }

    public void toChartInFragment(View view) {
        startActivity(new Intent(this, ChartInFragmentActivity.class));
    }

    public void SlideSelectLineChart(View view) {
        startActivity(new Intent(this, SlideSelectLineChartActivity.class));
    }

    public void OutsideLineChart(View view) {
        startActivity(new Intent(this, OutsideLineChartActivity.class));
    }
}
