package com.example.freedom.lab5;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

public class DynamicReceiver extends BroadcastReceiver {
    private String Name;
    private String Price;
    private String Info;
    private NotificationManager mNotifyMgr;
    final String DYNAMICACTION = "com.example.freedom.MyDynamicFliter";

    @Override
    public void onReceive(Context context, Intent intent){
        if (intent.getAction().equals(DYNAMICACTION)){
            Bundle bundle = intent.getExtras();
            Name = bundle.getString("Name");
            Bitmap bm = BitmapFactory.decodeResource(context.getResources(),ItemImage.getImg(Name));
            Notification.Builder builder = new Notification.Builder(context);
            //设置点击跳转事件
            Intent mIntent = new Intent(context, MainActivity.class);
            //PendingIntent 4个参数，第一个是上下文，第二个是requestCode，第三个Intent，
            //第4个是对参数的操作表示，常用的是FLAG_CANCEL_CURRENT和FLAG_UPDATE_CURRENT
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 2, mIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);


            builder.setContentTitle("马上下单")   //设置通知栏标题：发件人
                    .setContentText(Name + "已添加到购物车")      //设置通知栏显示内容：短信内容
                    .setTicker("您已成功下单")            //通知首次出现在通知栏，带上升动画效果
                    .setLargeIcon(bm)         //设置通知大ICON
                    .setSmallIcon(ItemImage.getImg(Name))    //设置通知小ICON（通知栏）
                    .setAutoCancel(true) // 设置这个标志当用户单击面板就可以让通知自动取消
                    .setContentIntent(pendingIntent);
            //获取状态通知栏管理
            NotificationManager manager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
            //绑定Notification，发送通知请求
            Notification notify = builder.build();
            manager.notify(2, notify);
        }
    }
}
