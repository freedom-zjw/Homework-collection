package com.example.freedom.lab8;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by freedom on 2017/12/10.
 */

public class Additem  extends AppCompatActivity {
    final myDB db = new myDB(this);
    //整个Activity视图的根视图
    View decorView;
    //手指按下时的坐标
    float downX, downY;
    //手机屏幕的宽度和高度
    float screenWidth, screenHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additem);
        final EditText add_name = (EditText) findViewById(R.id.add_name);
        final EditText add_birth = (EditText) findViewById(R.id.add_birth);
        final EditText add_gift = (EditText) findViewById(R.id.add_gift);

        Button button = (Button) findViewById(R.id.add2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(add_name.getText().toString())){
                    Toast.makeText(Additem.this, "名字不能为空", Toast.LENGTH_SHORT).show();
                }
                else if (db.query(add_name.getText().toString()) == false) {
                    db.insert(add_name.getText().toString(), add_birth.getText().toString(), add_gift.getText().toString());
                    finish();
                }
                else{
                    Toast.makeText(Additem .this, "已存在重复的名字", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //获得decorView
        decorView = getWindow().getDecorView();

        // 获得手机屏幕的宽度和高度，单位像素
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;

    }

    //通过重写onTouchEvent方法，对触摸事件进行处理
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){// 当按下时
            // 获得按下时的X坐标
            downX = event.getX();

        }else if(event.getAction() == MotionEvent.ACTION_MOVE){// 当手指滑动时
            // 获得滑过的距离
            float moveDistanceX = event.getX() - downX;
            if(moveDistanceX > 0){// 如果是向右滑动
                decorView.setX(moveDistanceX); // 设置界面的X到滑动到的位置
            }

        }else if(event.getAction() == MotionEvent.ACTION_UP){// 当抬起手指时
            // 获得滑过的距离
            float moveDistanceX = event.getX() - downX;
            if(moveDistanceX > screenWidth / 2){
                // 如果滑动的距离超过了手机屏幕的一半, 结束当前Activity
                finish();
            }else{ // 如果滑动距离没有超过一半
                // 恢复初始状态
                decorView.setX(0);
            }
        }
        return super.onTouchEvent(event);
    }
}
