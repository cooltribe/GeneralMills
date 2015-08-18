package com.example1.anima;

import android.animation.TypeEvaluator;

/**
 * Created by 陈玉柱 on 2015/7/22.
 */
public class ColorEvaluator implements TypeEvaluator {
    private int mCurrentRed = -1;
    private int mCurrentGreen = -1;
    private int mCurrentBlue = -1;

    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        String startColor = (String) startValue;
        String endColor = (String) endValue;
        int startRed = Integer.parseInt(startColor.substring(1, 3), 16);
        int startGreen = Integer.parseInt(startColor.substring(3, 5), 16);
        int startBlue = Integer.parseInt(startColor.substring(5, 7), 16);
        int endRed = Integer.parseInt(endColor.substring(1, 3), 16);
        int endGreen = Integer.parseInt(endColor.substring(3, 5), 16);
        int endBlue = Integer.parseInt(endColor.substring(5, 7), 16);
        if (-1 == mCurrentRed) {
            mCurrentRed = startRed;
        }
        if (-1 == mCurrentBlue){
            mCurrentBlue = startBlue;
        }
        if (-1 == mCurrentGreen){
            mCurrentGreen = startGreen;
        }
        int redDiff = Math.abs(startRed - endRed);
        int greenDiff = Math.abs(startGreen - endGreen);
        int blueDiff = Math.abs(startBlue - endBlue);
        int colorDiff = redDiff + greenDiff + blueDiff;
        if (mCurrentRed != endRed){
            mCurrentRed = getCurrentColor(startRed, endRed, colorDiff, 0, fraction);
        } else if (mCurrentGreen != endGreen){
            mCurrentGreen = getCurrentColor(startGreen, endGreen, colorDiff, redDiff, fraction);
        } else  if (mCurrentBlue != endBlue) {
            mCurrentBlue = getCurrentColor(startBlue, endBlue, colorDiff, redDiff, fraction);
        }
        String currentColor = "#" + getHexString(mCurrentRed) + getHexString(mCurrentGreen) + getHexString(mCurrentBlue);
            return currentColor;
    }

    /**
     * 根据 fraction 值来计算当前颜色
     * @param startColor
     * @param endColor
     * @param colorDiff
     * @param offSet
     * @param fraction
     * @return
     */
    private int getCurrentColor(int startColor, int endColor, int colorDiff , int offSet ,float fraction){
        int currentColor;
        if (startColor > endColor){
            currentColor = (int) (startColor - (fraction * colorDiff - offSet));
            if (currentColor < endColor) {
                currentColor = endColor;
            }
        } else {
            currentColor = (int) (startColor + (fraction * colorDiff - offSet));
            if (currentColor > endColor){
                currentColor = endColor;
            }
        }
        return currentColor;
    }
    private String getHexString(int value){
        String hexString = Integer.toHexString(value);
        if (hexString.length() == 1) {
            hexString = "0" + hexString;
        }
        return hexString;
    }
}
