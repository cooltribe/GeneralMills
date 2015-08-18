package com.example1.anima;

import android.animation.TypeEvaluator;

/**
 * Created by 陈玉柱 on 2015/7/22.
 */
public class PointEvaluator implements TypeEvaluator{
    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        Point startPoint = (Point) startValue;
        Point endPoint = (Point) endValue;
        float x = startPoint.getX() + fraction * (endPoint.getX() - startPoint.getX());
        float y = startPoint.getY() + fraction * (endPoint.getY() - startPoint.getX());
        Point point = new Point(x,y);
        return point;
    }
}
