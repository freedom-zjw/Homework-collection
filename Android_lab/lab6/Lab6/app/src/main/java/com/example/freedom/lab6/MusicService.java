package com.example.freedom.lab6;

import android.app.Service;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

public class MusicService extends Service {
    private IBinder mBinder = new MyBinder();
    private MediaPlayer mp = new MediaPlayer();

    public MusicService() {
    }

    public class MyBinder extends Binder {
        @Override
        protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code == 101){ //播放按钮，服务处理函数
                if (mp.isPlaying()) mp.pause();
                else mp.start();
            }
            else if (code == 102){//停止按钮，服务处理函数
                mp.stop();
                try {
                    mp.prepare();
                    mp.seekTo(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if (code == 103) mp.release();//退出按钮
            else if (code == 104) reply.writeInt(mp.getCurrentPosition());//界面刷新
            else if (code == 105) mp.seekTo(data.readInt());//拖动进度条
            else{//初始情况，绑定音乐资源
                try{
                    AssetManager AM = getAssets();
                    AssetFileDescriptor AFD = AM.openFd("melt.mp3");
                    mp.setDataSource(AFD.getFileDescriptor(), AFD.getStartOffset(), AFD.getLength());
                    mp.prepare();
                    mp.setLooping(true);
                    reply.writeInt(mp.getDuration());
                    reply.writeInt(mp.getCurrentPosition());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return super.onTransact(code, data, reply, flags);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // Return the communication channel to the service.
        return mBinder;
    }
}
