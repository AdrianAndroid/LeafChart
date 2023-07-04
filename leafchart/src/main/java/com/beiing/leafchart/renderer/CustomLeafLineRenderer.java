package com.beiing.leafchart.renderer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.util.Log;
import android.view.View;

import com.beiing.leafchart.BuildConfig;
import com.beiing.leafchart.bean.Axis;
import com.beiing.leafchart.bean.Line;
import com.beiing.leafchart.bean.PointValue;
import com.beiing.leafchart.support.LeafUtil;

import java.util.List;

public class CustomLeafLineRenderer extends LeafLineRenderer {
    private Paint solidLinePaint;
    private Paint dottedLinePaint;
    private LinearGradient secFillShader;
    private Paint secFillPaint;

    public CustomLeafLineRenderer(Context context, View view) {
        super(context, view);
        solidLinePaint = new Paint();
        solidLinePaint.setColor(Color.WHITE);
        solidLinePaint.setAntiAlias(true);

        dottedLinePaint = new Paint();
        dottedLinePaint.setColor(Color.BLUE);
        dottedLinePaint.setAntiAlias(true);
        dottedLinePaint.setPathEffect(new DashPathEffect(new float[]{10, 10}, 0));

        secFillPaint = new Paint();
        secFillPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    public void drawFillArea(Canvas canvas, Line line, Axis axisX) {
        //继续使用前面的 path
        if (line != null && line.getValues().size() > 1 && isShow) {
            solidLinePaint.setStrokeWidth(LeafUtil.dp2px(mContext, line.getLineWidth())/2f);
            solidLinePaint.setColor(line.getLineColor());
            solidLinePaint.setStyle(Paint.Style.STROKE);
            dottedLinePaint.setStrokeWidth(LeafUtil.dp2px(mContext, line.getLineWidth())/2f);
            dottedLinePaint.setColor(line.getLineColor());
            dottedLinePaint.setStyle(Paint.Style.STROKE);

            List<PointValue> values = line.getValues();

            PointValue firstPoint = values.get(0);
            float firstX = firstPoint.getOriginX();
            float firstY = firstPoint.getOriginY();
            float firstDiffY = firstPoint.getDiffY();
            Path path = line.getPath();
            for (int i = 1; i < values.size(); i++) {
                PointValue endPoint = values.get(i);
                float endX = endPoint.getOriginX();
                float endY = endPoint.getOriginY();
                float endDiffY = endPoint.getDiffY();
                int firColor = line.getFillColor() == 0 ? Color.TRANSPARENT : line.getFillColor();
                int secColor = line.getSecFillColor() == 0 ? Color.TRANSPARENT : line.getSecFillColor();
//                if (BuildConfig.DEBUG) {
//                    Log.i(CustomLeafLineRenderer.class.getSimpleName(), String.format("firstX=%d, firstY=%d, firstDiffY=%d, endX=%d, endY=%d, endDiffY=%d", (int) firstX, (int) firstY, (int) firstDiffY, (int) endX, (int) endY, (int) endDiffY));
//                }
                float height = Math.min(mHeight, Math.max(firstDiffY, endDiffY));
                float x0 = 0; //Math.min(firstX, endX);
                float y0 = 0; //Math.min(firstY, endY);
                float x1 = 0; //Math.max(firstX, endX);
                float y1 = mHeight; //Math.max(firstDiffY, endDiffY); //Math.max(firstY, endY);
//                if (fillShader == null) {
                fillShader = new LinearGradient(x0, y0, x1, y1,  Color.TRANSPARENT,firColor, Shader.TileMode.CLAMP);
                fillPaint.setShader(fillShader);
//                }
//                if (secFillShader == null) {
                secFillShader = new LinearGradient(x0, y0, x1, y1,Color.TRANSPARENT, secColor,  Shader.TileMode.CLAMP);
                secFillPaint.setShader(secFillShader);
//                }

                path.reset();
                path.moveTo(firstX, firstY); // 左上角
                path.lineTo(endX, endY);// 右上角
                path.lineTo(endX, endY + endDiffY);// 右下角
                path.lineTo(firstX, firstY + firstDiffY);
                path.close();

                Paint realPaint = i % 2 == 1 ? fillPaint : secFillPaint;
                canvas.save();
                // 填充区域
                canvas.drawPath(path, realPaint);
                // 底部横线
                canvas.drawLine(firstX, firstY + firstDiffY, endX, endY + endDiffY, solidLinePaint);
                canvas.restore();
                path.reset();

                firstX = endX;
                firstY = endY;
                firstDiffY = endDiffY;
            }


            // 画间距线
            canvas.save();
            for (int i = 0; i < values.size(); i++) {
                PointValue pointValue = values.get(i);
                float x = pointValue.getOriginX();
                float y = pointValue.getOriginY();
                float diffY = pointValue.getDiffY();
                if (i != 0) {
                    canvas.drawLine(x, y, x, y + diffY, dottedLinePaint);
                } else {
                    canvas.drawLine(x, y, x, y + diffY, solidLinePaint);
                }
            }
            canvas.restore();

//            List<PointValue> values = line.getValues();
//            PointValue firstPoint = values.get(0);
//            float firstX = firstPoint.getOriginX();
//            float firstY = firstPoint.getOriginY();
//            float firstDiffY = firstPoint.getDiffY();
//
//            Path path = line.getPath();
//            PointValue lastPoint = values.get(values.size() - 1);
//            float lastX = lastPoint.getOriginX();
//
//            path.lineTo(lastX, axisX.getStartY());
//            path.lineTo(firstX, axisX.getStartY());
//            path.close();
//
//            if (fillShader == null) {
//                fillShader = new LinearGradient(0, 0, 0, mHeight, line.getFillColor(), Color.TRANSPARENT, Shader.TileMode.CLAMP);
//                fillPaint.setShader(fillShader);
//            }
//
//            if (line.getFillColor() == 0)
//                fillPaint.setAlpha(100);
//            else
//                fillPaint.setColor(line.getFillColor());
//
//            canvas.save();
//            canvas.clipRect(firstX, 0, phase * (lastX - firstX) + firstX, mHeight);
//            canvas.drawPath(path, fillPaint);
//            canvas.drawLine(firstX, firstY, firstX, firstY + firstDiffY, linePaint);
//            canvas.restore();
//            path.reset();
        }
    }
}
