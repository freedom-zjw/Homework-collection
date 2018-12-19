package com.example.freedom.lab6;

import android.animation.ObjectAnimator;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.icu.text.SimpleDateFormat;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Parcel;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private ImageView image;
    private Button play;
    private Button stop;
    private Button quit;
    private TextView status;
    private TextView curTime;
    private TextView totTime;
    private SeekBar seekBar;
    private IBinder mBinder;
    private ServiceConnection sc;
    private int sta;
    private SimpleDateFormat time;
    private ObjectAnimator animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        image = (ImageView) findViewById(R.id.image);
        play = (Button) findViewById(R.id.play);
        stop = (Button) findViewById(R.id.stop);
        quit = (Button) findViewById(R.id.quit);
        status = (TextView) findViewById(R.id.status);
        curTime = (TextView) findViewById(R.id.curTime);
        totTime = (TextView) findViewById(R.id.totTime);
        seekBar = (SeekBar) findViewById(R.id.seekbar);
        sta = 5;
        time = new SimpleDateFormat("mm:ss");

        //初始化动画
        animation = ObjectAnimator.ofFloat(image, "rotation", 0.0f, 360.0f);
        //第一个参数是空间，第二个是变化方式，第三个是可变长参数，第4个是变化角度
        animation.setDuration(10000); //设定转一圈的时间
        animation.setInterpolator(new LinearInterpolator());//定义动画的变化速率，这里是线性
        animation.setRepeatCount(Animation.INFINITE); //设定无限循环

        //bindService 成功后回调 onServiceConnected 函数，通过 IBinder 获取 Service 对
        // 象，实现 Activity 与 Service 的绑定
        sc = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mBinder = service;
                try {
                    int code = 106;
                    Parcel data = Parcel.obtain();
                    Parcel reply = Parcel.obtain();
                    mBinder.transact(code, data, reply, 0);
                    seekBar.setMax(reply.readInt()); // 设置最大时长
                    seekBar.setProgress(reply.readInt()); // 设置当前进度为0
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onServiceDisconnected(ComponentName name) {sc = null;}
        };

        //Activity 启动时绑定 Service
        Intent intent = new Intent(this, MusicService.class);
        startService(intent);
        bindService(intent, sc, Context.BIND_AUTO_CREATE);

        //设置seekbar监听器
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //该方法拖动进度条进度改变的时候调用
                if (fromUser){//用户触发
                    try {
                        int code = 105;
                        Parcel data = Parcel.obtain();
                        Parcel reply = Parcel.obtain();
                        data.writeInt(progress);
                        mBinder.transact(code, data, reply, 0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}//该方法拖动进度条开始拖动的时候调用
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}//该方法拖动进度条停止拖动的时候调用
        });

        final Handler mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg){
                super.handleMessage(msg);
                if (msg.what < 3) {
                    if (msg.what == 1) {//播放
                        play.setText("PAUSED");
                        status.setText("Playing");
                        if (animation.isPaused()) animation.resume();
                        else animation.start();
                    }
                    else {//暂停
                        play.setText("PLAY");
                        status.setText("Paused");
                        animation.pause();
                    }
                    try {
                        int code = 101;
                        Parcel data = Parcel.obtain();
                        Parcel reply = Parcel.obtain();
                        mBinder.transact(code, data, reply, 0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else if (msg.what == 3){//停止
                    seekBar.setProgress(0);
                    play.setText("PLAY");
                    status.setText("Stopped");
                    animation.setCurrentPlayTime(0);
                    animation.cancel();
                    try {
                        int code = 102;
                        Parcel data = Parcel.obtain();
                        Parcel reply = Parcel.obtain();
                        mBinder.transact(code, data, reply, 0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else if (msg.what == 4) {//退出
                    try {
                        int code = 103;
                        Parcel data = Parcel.obtain();
                        Parcel reply = Parcel.obtain();
                        mBinder.transact(code, data, reply, 0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //停止服务时要解除绑定
                    unbindService(sc);
                    sc = null;
                    try{
                        MainActivity.this.finish();
                        System.exit(0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                sta = 5;
                try {//音乐进度是要一直更新的
                    int code = 104;
                    Parcel data = Parcel.obtain();
                    Parcel reply = Parcel.obtain();
                    mBinder.transact(code, data, reply, 0);
                    int progress = reply.readInt();
                    seekBar.setProgress(progress);
                    curTime.setText(time.format(progress));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        Thread mThread = new Thread(){
            @Override
            public void run(){
                while (true) {
                    try {
                        Thread.sleep(100);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    mHandler.obtainMessage(sta).sendToTarget();
                }
            }
        };
        mThread.start();
    }
    public void onClick(View v){
        if (v.getId() == R.id.play){
            if (status.getText().toString().equals("Playing")) sta = 2;
            else sta = 1;
        }
        else if (v.getId() == R.id.stop) sta = 3;
        else sta = 4;
    }
    @Override
    public void onBackPressed(){
        moveTaskToBack(false);
    }
}
