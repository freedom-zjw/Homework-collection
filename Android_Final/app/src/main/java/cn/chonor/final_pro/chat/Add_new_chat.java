package cn.chonor.final_pro.chat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.IOException;

import cn.chonor.final_pro.DataBase.Choicedb;
import cn.chonor.final_pro.DataBase.Commentdb;
import cn.chonor.final_pro.DataBase.Coursedb;
import cn.chonor.final_pro.Date.GetTime;
import cn.chonor.final_pro.Main_Tabhost;
import cn.chonor.final_pro.R;
import cn.chonor.final_pro.UpFiles.UpFile;
import cn.chonor.final_pro.info.Edit_information;
import cn.chonor.final_pro.info.ImageUtils;
import cn.chonor.final_pro.login_info.SaveUser;
import cn.chonor.final_pro.model.Comment;
import cn.chonor.final_pro.model.Student;
import cn.chonor.final_pro.model.Teacher;

/**
 * Created by ASUS on 2017/12/25.
 */

public class Add_new_chat  extends AppCompatActivity implements View.OnClickListener{
    private ImageView imageView;
    private static final String IMAGE_UNSPECIFIED = "image/*";
    private final int IMAGE_CODE = 0; // 这里的IMAGE_CODE是自己任意定义的
    private final int CAMERA_CODE = 1;
    private Comment comment=new Comment();
    private String usernum="";
    private Student student;
    private Teacher teacher;
    private boolean isTeacher;
    protected static final int CHOOSE_PICTURE = 0;
    protected static final int TAKE_PICTURE = 1;
    private static final int CROP_SMALL_PICTURE = 2;
    protected static Uri tempUri;
    private static final String TAG = "Register_Img";
    private static String IMAGE_NAME="tmp_image.jpg";
    private Bitmap photo=null;
    private void GetLesson(){
        usernum= SaveUser.getUserId(Add_new_chat.this);
        if(usernum.length()==6){//教师
            isTeacher=true;
            teacher=SaveUser.getTeacherInfo(Add_new_chat.this);
        }else {//学生
            isTeacher=false;
            student = SaveUser.getStudentInfo(Add_new_chat.this);
        }
    }

    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.add_new_chat);
        GetLesson();

        int screen_width=this.getWindowManager().getDefaultDisplay().getWidth();
        screen_width*=0.9;
        int screen_height=this.getWindowManager().getDefaultDisplay().getHeight();
        screen_height*=0.4;
        EditText chat_content=(EditText) findViewById(R.id.chat_content);
        chat_content.setWidth(screen_width);
        chat_content.setHeight(screen_height);
        findViewById(R.id.add_chat_img).setOnClickListener(this);
        findViewById(R.id.submit).setOnClickListener(this);
        imageView=(ImageView) findViewById(R.id.add_chat_img);
        findViewById(R.id.back).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.add_chat_img){
            showChoosePicDialog();
        }
        else if(v.getId()==R.id.submit){ //点击提交按钮
            comment.setNum(usernum);
            EditText editText=(EditText)findViewById(R.id.chat_content);
            String info=editText.getText().toString();
            if(info.length()!=0) {
                String src=String.valueOf(System.currentTimeMillis());
                comment.setSrc("https://chonor.cn/Android/Avatar/img/"+src+".png");
                uploadPic(src);
                comment.setInfo(info);
                comment.setTime(GetTime.GetYeat() + "/" + GetTime.GetMonth() + "/" + GetTime.GetDay());
                if (isTeacher) {
                    comment.setName(teacher.getName());
                    comment.setCollege(teacher.getCollege());
                    comment.setAvatar(teacher.getAvatar());
                } else {
                    comment.setName(student.getName());
                    comment.setCollege(student.getCollege());
                    comment.setAvatar(student.getAvatar());
                }
                comment.setUp(0);
                comment.setDown(0);
                comment.setReport(0);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Commentdb commentdb = new Commentdb();
                        Message msg = Message.obtain();
                        msg.what = 0;
                        try {
                            commentdb.insert(comment);
                            msg.what = 1;
                        } catch (Exception e) {
                        }
                        Handler.sendMessage(msg);
                    }
                }).start();
            }
            else Toast.makeText(Add_new_chat.this,"内容不能为空",Toast.LENGTH_SHORT).show();
        }
        else if(v.getId()==R.id.back){
            Add_new_chat.this.finish();
        }
    }

    private android.os.Handler Handler = new Handler() {
        public void handleMessage(Message msg) {
            if(msg.what==0){
                Toast.makeText(Add_new_chat.this, "提交失败请检查网络", Toast.LENGTH_SHORT).show();
            }
            else if(msg.what==1){
                Toast.makeText(Add_new_chat.this, "提交成功", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            }
        }
    };
    public void showChoosePicDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("选择图片");
        String[] items = { "选择本地照片", "拍照" };
        builder.setNegativeButton("取消", null);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case CHOOSE_PICTURE: // 选择本地照片
                        Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
                        openAlbumIntent.setType("image/*");
                        startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
                        break;
                    case TAKE_PICTURE: // 拍照
                        //Log.d(TAG,"takePicture_click");
                        takePicture();
                        break;
                }
            }
        });
        final AlertDialog dialog=builder.create();
        //尝试美化，透明
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = 0.9f;
        window.setAttributes(lp);
        dialog.show();
    }

    /**
     * 拍照
     */
    private void takePicture() {
        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (Build.VERSION.SDK_INT >= 23) {
            // 需要申请动态权限
            int check = ContextCompat.checkSelfPermission(this, permissions[0]);
            // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
            if (check != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
        //  Log.d(TAG,"takePicture_permission_finish");
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //File file = new File(Environment.getExternalStorageDirectory(), "image.jpg");
        File file = new File(Environment.getExternalStorageDirectory(), IMAGE_NAME);
        //判断是否是AndroidN以及更高的版本
        if (Build.VERSION.SDK_INT >= 24) {
            openCameraIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            tempUri = FileProvider.getUriForFile(Add_new_chat.this, "cn.chonor.final_pro.fileProvider", file);
        } else {
            //tempUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "image.jpg"));
            tempUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), IMAGE_NAME));
        }
        // 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换

        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
        startActivityForResult(openCameraIntent, TAKE_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) { // 如果返回码是可以用的
            switch (requestCode) {
                case TAKE_PICTURE:
                    startPhotoZoom(tempUri); // 开始对图片进行裁剪处理
                    break;
                case CHOOSE_PICTURE:
                    startPhotoZoom(data.getData()); // 开始对图片进行裁剪处理
                    break;
                case UCrop.REQUEST_CROP:
                    if (data != null) {

                        setImageToView(data); // 让刚才选择裁剪得到的图片显示在界面上
                    }
                    break;
            }
        }
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    protected void startPhotoZoom(Uri uri) {
        Uri mDestinationUri = Uri.fromFile(new File(getCacheDir(), "tmps.jpg"));
        UCrop.Options options = new UCrop.Options();
        options.setToolbarColor(ActivityCompat.getColor(this, R.color.colorPrimary1));
        options.setStatusBarColor(ActivityCompat.getColor(this, R.color.colorPrimaryDark));
        options.setToolbarWidgetColor(Color.BLACK);
        UCrop.of(uri, mDestinationUri)
                .withAspectRatio(16, 9)
                .withMaxResultSize(640, 360)
                .withOptions(options)
                .start(Add_new_chat.this);
    }

    /**
     * 保存裁剪之后的图片数据
     *
     * @param
     */
    protected void setImageToView(Intent data) {
        Uri croppedFileUri = UCrop.getOutput(data);
        if (croppedFileUri != null) {
            try {
                photo = MediaStore.Images.Media.getBitmap(this.getContentResolver(), croppedFileUri);
                Log.d(TAG, "setImageToView:" + photo);
                imageView.setImageBitmap(photo);
            }catch (Exception e){

            }
        }
    }

    /**
     * 接口，上传至数据库

     */
    private void uploadPic(String num) {
        String imagePath = ImageUtils.savePhoto(photo, Environment
                .getExternalStorageDirectory().getAbsolutePath(), num);
        if(imagePath != null){
            UpFile.uploadImage(num+".png");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            takePicture();
        } else {
            // 没有获取 到权限，从新请求，或者关闭app
            Toast.makeText(this, "需要存储权限", Toast.LENGTH_SHORT).show();
        }
    }
}
