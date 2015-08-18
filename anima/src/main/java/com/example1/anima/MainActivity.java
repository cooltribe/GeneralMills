package com.example1.anima;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity implements View.OnClickListener {

    private TextView textView;
    private Button dianJi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textview);
        dianJi = (Button) findViewById(R.id.dianji);
        dianJi.setOnClickListener(this);
    }

    /**
     * 旋转
     *
     * @param view
     */
    private void getRotation(TextView view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "rotation", 0f, 360f);
        animator.setDuration(5000);
        animator.start();

    }

    /**
     * 平移
     *
     * @param textView
     */
    private void getTranslation(TextView textView) {
        float currentTranslationX = textView.getTranslationX();
        ObjectAnimator animator = ObjectAnimator.ofFloat(textView, "translationX", currentTranslationX, 500, currentTranslationX);
        animator.setDuration(5000);
        animator.start();
    }

    /**
     * Y轴缩放
     * @param textView
     */
    private void getScaleY(TextView textView) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(textView, "scaleY", 1f, 3f, 1f);
        animator.setDuration(5000);
        animator.start();
    }

    private void setCombination(TextView textView) {
        ObjectAnimator moveIn = ObjectAnimator.ofFloat(textView, "translationX", -500f, 0f);
        ObjectAnimator rotate = ObjectAnimator.ofFloat(textView, "rotation", 0f, 360f);
        ObjectAnimator fadeInOut = ObjectAnimator.ofFloat(textView, "alpha", 1f, 0f, 1f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(rotate).with(fadeInOut).after(moveIn);
        animatorSet.setDuration(5000);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });
        animatorSet.start();
    }

    private void getXml(TextView textView){
        Animator animator = AnimatorInflater.loadAnimator(this, R.animator.anima_object);
        animator.setTarget(textView);

        animator.start();
    }
    private  void getPoint(){
        Point point1 = new Point(0,0);
        Point point2 = new Point(300,300);
        ValueAnimator animator = ValueAnimator.ofObject(new PointEvaluator(), point1, point2);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Point currentPoint = (Point) animation.getAnimatedValue();
                Log.i("TAG",currentPoint.getX() + "," + currentPoint.getY());
            }
        });
        animator.setDuration(5000);
        animator.start();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dianji:
                getPoint();
                break;
        }
    }
}
