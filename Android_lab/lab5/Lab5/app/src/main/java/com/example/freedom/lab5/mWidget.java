package com.example.freedom.lab5;

import android.app.Notification;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.Toast;

/**
 * Implementation of App Widget functionality.
 */
public class mWidget extends AppWidgetProvider {
    final String STATICACTION = "com.example.freedom.lab5.MyStaticFliter";
    final String DYNAMICACTION = "com.example.freedom.MyDynamicFliter";
    private String Name;
    private String Price;
    private String Info;
    boolean flag=true;
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        //CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        //RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.m_widget);
        //views.setTextViewText(R.id.appwidget_text, widgetText);

        // Instruct the widget manager to update the widget
        //appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            if(flag){
                init(context);
            }else
                updateAppWidget(context,appWidgetManager,appWidgetId);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent){
        super.onReceive(context, intent);
        final String action = intent.getAction();
        if(action.equals(STATICACTION)) {
            flag = false;
            Bundle bundle = intent.getExtras();
            Name = bundle.getString("Name");
            Price = bundle.getString("Price");
            Info = bundle.getString("Info");

            Intent mIntent = new Intent(context, ItemsDetails.class);
            mIntent.putExtra("Name",Name);
            mIntent.putExtra("Price",Price);
            mIntent.putExtra("Info",Info);

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.m_widget);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setTextViewText(R.id.appwidget_text, Name + "仅售" + Price + "!");
            views.setImageViewResource(R.id.appwidget_image, ItemImage.getImg(Name));
            views.setOnClickPendingIntent(R.id.appwidget, pendingIntent);
            ComponentName me = new ComponentName(context, mWidget.class);
            AppWidgetManager.getInstance(context).updateAppWidget(me, views);
        }
        else if(action.equals(DYNAMICACTION)){
            Bundle bundle = intent.getExtras();
            Name = bundle.getString("Name");

            Intent mIntent = new Intent(context, MainActivity.class);
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.m_widget);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 2, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setTextViewText(R.id.appwidget_text, Name + "已添加到购物车");
            views.setImageViewResource(R.id.appwidget_image, ItemImage.getImg(Name));
            views.setOnClickPendingIntent(R.id.appwidget, pendingIntent);
            ComponentName me = new ComponentName(context, mWidget.class);
            AppWidgetManager.getInstance(context).updateAppWidget(me, views);
        }

    }
    private void init(Context context){
        Intent i = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.m_widget);
        views.setTextViewText(R.id.appwidget_text, "当前没有任何消息");
        views.setImageViewResource(R.id.appwidget_image, R.mipmap.shoplist);
        views.setOnClickPendingIntent(R.id.appwidget, pendingIntent);
        ComponentName me = new ComponentName(context, mWidget.class);
        AppWidgetManager.getInstance(context).updateAppWidget(me, views);
    }

    @Override
    public void onEnabled(Context context) {
        init(context);
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        init(context);
        // Enter relevant functionality for when the last widget is disabled
    }
}

