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
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity2 extends Activity implements View.OnClickListener {

    private TextView textView;
    private Button dianJi;
    private boolean scrollFlag = false;// 标记是否滑动
    private ListView listView;
    private int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textview);
        dianJi = (Button) findViewById(R.id.dianji);
        dianJi.setOnClickListener(this);

    }
    private void initListView(){
        listView = (ListView) findViewById(R.id.lv);
        MyListViewAdapter adapter = new MyListViewAdapter(getData(),this);
        listView.setAdapter(adapter);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        scrollFlag = false;
                        Log.i("TAG", "停止滚动");
                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                        Log.i("TAG", "主动滚动");
                    case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                        Log.i("TAG", "被动滚动");
//                        for (int i = 0; i < getData().size(); i++) {
//                            if (i % 2 == 0) {
//                                getTranslation(listView.getChildAt(i), -50f, 50f);
//                            } else {
//                                getTranslation(listView.getChildAt(i), 50f, -50f);
//                            }
//                        }
                        break;

                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                Log.i("隐藏", firstVisibleItem + "," + visibleItemCount + "," + totalItemCount);
                for (int i = 0; i <getData().size() ; i++) {
                    if (i<=firstVisibleItem + firstVisibleItem && i >=firstVisibleItem){
                        getTranslation(listView.getChildAt(i),200f,0f);
                        Log.i("22222","22222");
                    }  else {
//                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                        layoutParams.setMargins(200,0,0,0);
//                        listView.getChildAt(i).setLayoutParams(layoutParams);
//                        position = i;
                        Log.i("11111","" + i);

                    }
                }
//                position = firstVisibleItem;

            }
        });

    }
    private List<String> getData(){
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 100; i++){
            list.add("列表" + i);
        }

        return  list;
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
     * @param view
     */
    private void getTranslation(View view,float start, float end) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationX", start, end);
        animator.setDuration(200);
//        animator.setRepeatMode(ValueAnimator.RESTART);
//        animator.setRepeatCount(10);
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
        animator.setDuration(5000);
        animator.start();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dianji:
                getTranslation(textView,0f,300f);
                break;
        }
    }
}
