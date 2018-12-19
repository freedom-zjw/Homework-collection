package cn.chonor.final_pro.UpFiles;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Chonor on 2017/12/28.
 */

public class UpFile {
    static String result="";
    public static String uploadImage(String imagename) {//将图片发送到服务器

        final String url = "https://chonor.cn/Android/Avatar/index.php";
        File file = new File( Environment.getExternalStorageDirectory(), imagename);
        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image1", imagename, fileBody)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        final okhttp3.OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
        OkHttpClient okHttpClient = httpBuilder
                //设置超时
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result="-1";
                Log.e("aa", "uploadMultiFile() e=" + e);
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i("bb", "uploadMultiFile() response=" + response.body().string());
                result="1";
            }
        });
        return result;
    }
}
