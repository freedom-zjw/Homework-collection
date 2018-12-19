package cn.chonor.final_pro.info;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import cn.chonor.final_pro.DataBase.Coursedb;
import cn.chonor.final_pro.DataBase.Studentdb;
import cn.chonor.final_pro.DataBase.Teacherdb;
import cn.chonor.final_pro.FastBlurUtility.FastBlurUtility;
import cn.chonor.final_pro.Lesson.Lesson_add;
import cn.chonor.final_pro.Lesson.Lesson_information;
import cn.chonor.final_pro.R;
import cn.chonor.final_pro.UpFiles.UpFile;
import cn.chonor.final_pro.login_info.SaveUser;
import cn.chonor.final_pro.login_register.RegisterActivity;
import cn.chonor.final_pro.model.Student;
import cn.chonor.final_pro.model.Teacher;

/**
 * Created by Jy on 2017/12/24.
 */

/**
 * 个人编辑信息界面
 */
public class Edit_information extends AppCompatActivity {
    private CircleImageView edit_img;
    private EditText edit_name;
    private EditText edit_department;
    private RadioGroup edit_sex;
    private RadioButton edit_male;
    private RadioButton edit_female;
    private RadioButton edit_secret;
    private EditText edit_introduction;
    private EditText edit_phone,edit_mail,edit_offic,edit_work;
    private Button edit_save;
    private boolean logined=false;
    private boolean isTeacher=false;
    private String usernum="";
    private Student student;
    private Teacher teacher;
    protected static final int CHOOSE_PICTURE = 0;
    protected static final int TAKE_PICTURE = 1;
    private static final int CROP_SMALL_PICTURE = 2;
    protected static Uri tempUri;
    private static final String TAG = "Register_Img";
    private static String IMAGE_NAME="tmp_image.jpg";
    private Bitmap photo=null;
    private boolean flag=false;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_activity);
        setTitle("修改个人信息");
        Bitmap bitmap= BitmapFactory.decodeResource(this.getResources(),R.mipmap.edit_bg);
        bitmap= FastBlurUtility.startBlurBackground(bitmap,5);
        BitmapDrawable bitmapDrawable=new BitmapDrawable(bitmap);
        findViewById(R.id.edit_layout).setBackgroundDrawable(bitmapDrawable);

        GetLogin();
        if(isTeacher){
            TextInputLayout ed_tea_email=(TextInputLayout)findViewById(R.id.ed_teacher_email);
            ed_tea_email.setVisibility(View.VISIBLE);
            TextInputLayout ed_tea_phone=(TextInputLayout)findViewById(R.id.ed_teacher_phone);
            ed_tea_phone.setVisibility(View.VISIBLE);
            TextInputLayout ed_tea_office=(TextInputLayout)findViewById(R.id.ed_teacher_office);
            ed_tea_office.setVisibility(View.VISIBLE);
            TextInputLayout ed_tea_work=(TextInputLayout)findViewById(R.id.ed_teacher_work);
            ed_tea_work.setVisibility(View.VISIBLE);
        }
        else{
            TextInputLayout ed_tea_email=(TextInputLayout)findViewById(R.id.ed_teacher_email);
            ed_tea_email.setVisibility(View.GONE);
            TextInputLayout ed_tea_phone=(TextInputLayout)findViewById(R.id.ed_teacher_phone);
            ed_tea_phone.setVisibility(View.GONE);
            TextInputLayout ed_tea_office=(TextInputLayout)findViewById(R.id.ed_teacher_office);
            ed_tea_office.setVisibility(View.GONE);
            TextInputLayout ed_tea_work=(TextInputLayout)findViewById(R.id.ed_teacher_work);
            ed_tea_work.setVisibility(View.GONE);
        }


        init_infor(0);
        edit_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                set_infor(0);
            }
        });
    }
    private void GetLogin() {
        usernum = SaveUser.getUserId(Edit_information.this);
        if (usernum.equals("")) {
            logined = false;
            setResult(RESULT_OK);
            finish();
        } else {
            logined = true;
            if (usernum.length() == 6) {//教师
                isTeacher = true;
                teacher = SaveUser.getTeacherInfo(Edit_information.this);
            } else {//学生
                isTeacher = false;
                student = SaveUser.getStudentInfo(Edit_information.this);
            }
        }
    }
    /**
     * 初始化个人信息界面
     * 从数据库内读取个人信息并对页面内的对应控件进行赋值，包括头像，昵称，院系，性别，简介
     */
    private void init_infor(int s){

        edit_img=(CircleImageView)findViewById(R.id.edit_img);
        edit_name=(EditText)findViewById(R.id.edit_name);
        edit_department=(EditText)findViewById(R.id.edit_department);
        edit_sex=(RadioGroup)findViewById(R.id.edit_sex);
        edit_introduction=(EditText)findViewById(R.id.edit_introduction);
        edit_save=(Button)findViewById(R.id.edit_save);
        edit_male=(RadioButton)findViewById(R.id.edit_male);
        edit_female=(RadioButton)findViewById(R.id.edit_female);
        edit_secret=(RadioButton)findViewById(R.id.edit_secret);
        edit_phone=(EditText)findViewById(R.id.edit_teacher_phone) ;
        edit_mail=(EditText)findViewById(R.id.edit_teacher_email);
        edit_offic=(EditText)findViewById(R.id.edit_teacher_office);
        edit_work=(EditText)findViewById(R.id.edit_teacher_work);
        edit_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChoosePicDialog();
            }
        });
        String old_name;
        String old_department;
        String old_introduction;
        if(isTeacher) {//是教师
            old_name = teacher.getNickname();
            old_department =teacher.getCollege();
            old_introduction = teacher.getInfo();
            if(teacher.getSex()=="male")edit_male.setChecked(true);
            else if(teacher.getSex()=="female")edit_female.setChecked(true);
            else edit_secret.setChecked(true);
            edit_work.setText(teacher.getPosition());
            edit_phone.setText(teacher.getPhone());
            edit_mail.setText(teacher.getEmail());
            edit_offic.setText(teacher.getOffice());
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Bitmap bmp = getURLimage(teacher.getAvatar());
                    Message msg = new Message();
                    msg.what = 0;
                    msg.obj = bmp;
                    handle.sendMessage(msg);
                }
            }).start();
        }else{
            old_name = student.getNickname();
            old_department =student.getCollege();
            old_introduction = student.getInfo();
            if(student.getSex()=="male")edit_male.setChecked(true);
            else if(student.getSex()=="female")edit_female.setChecked(true);
            else edit_secret.setChecked(true);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Bitmap bmp = getURLimage(student.getAvatar());
                    Message msg = new Message();
                    msg.what = 0;
                    msg.obj = bmp;
                    handle.sendMessage(msg);
                }
            }).start();
        }
        edit_name.setText(old_name);
        edit_department.setText(old_department);
        edit_introduction.setText(old_introduction);

    }

    /**
     * 保存修改，返回给数据库
     */
    private void set_infor(int s){
        String new_name=edit_name.getText().toString();
        String new_department=edit_department.getText().toString();
        String new_introduction=edit_introduction.getText().toString();
        String phone=edit_phone.getText().toString();
        String mail=edit_mail.getText().toString();
        String offic=edit_offic.getText().toString();
        String pos=edit_work.getText().toString();
        final int new_sex=edit_sex.getCheckedRadioButtonId();
        if(new_name.length()==0){
            Toast.makeText(Edit_information.this, "昵称不可为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if(isTeacher){
            if(mail.length()==0){
                TextInputLayout ed_tea_email=(TextInputLayout)findViewById(R.id.ed_teacher_email);
                ed_tea_email.setErrorEnabled(true);

                ed_tea_email.setError("必须填写邮箱，请补充");
            }else {
                if (flag) {
                    uploadPic(teacher.getNum());
                    teacher.setAvatar("https://chonor.cn/Android/Avatar/img/" + teacher.getNum() + ".png");
                }
                teacher.setNickname(new_name);
                teacher.setCollege(new_department);
                teacher.setInfo(new_introduction);
                teacher.setEmail(mail);
                teacher.setOffice(offic);
                teacher.setPhone(phone);
                teacher.setPosition(pos);
                if (edit_male.getId() == new_sex) teacher.setSex("male");
                else if (edit_female.getId() == new_sex) teacher.setSex("famale");
                else if (edit_secret.getId() == new_sex) teacher.setSex("unknow");
                SaveUser.setTeacherInfo(Edit_information.this, teacher);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Teacherdb teacherdb = new Teacherdb();
                        Message msg = Message.obtain();
                        msg.what = 0;
                        try {
                            teacherdb.update(teacher);
                            msg.what = 1;
                        } catch (Exception e) {
                        }

                        Handler.sendMessage(msg);
                    }
                }).start();
            }
        }else{
            if(flag){
                student.setAvatar("https://chonor.cn/Android/Avatar/img/" + student.getNum() + ".png");
                uploadPic(student.getNum());
            }
            student.setNickname(new_name);
            student.setCollege(new_department);
            student.setInfo(new_introduction);
            if(edit_male.getId()==new_sex)student.setSex("male");
            else if(edit_female.getId()==new_sex) student.setSex("famale");
            else if(edit_secret.getId()==new_sex) student.setSex("unknow");
            SaveUser.setStudentInfo(Edit_information.this,student);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Studentdb studentdb=new Studentdb();
                    Message msg = Message.obtain();
                    msg.what=0;
                    try{
                        studentdb.update(student);
                        msg.what=1;
                    } catch (Exception e){
                    }

                    Handler.sendMessage(msg);
                }
            }).start();
        }

    }
    private android.os.Handler Handler = new Handler() {
        public void handleMessage(Message msg) {
            if(msg.what==0){
                Toast.makeText(Edit_information.this, "修改失败请检查网络", Toast.LENGTH_SHORT).show();
            }
            else if(msg.what==1){
                Toast.makeText(Edit_information.this, "修改成功", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            }
        }
    };
    private Handler handle = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Bitmap tmp=(Bitmap) msg.obj;
                    //imageView.setImageBitmap(bmp);
                    edit_img.setImageBitmap(tmp);
                    break;
            }
        };
    };
    public Bitmap getURLimage(String url) {
        Bitmap bmp = null;
        try {
            URL myurl = new URL(url);
            // 获得连接
            HttpURLConnection conn = (HttpURLConnection) myurl.openConnection();
            conn.setConnectTimeout(6000);//设置超时
            conn.setDoInput(true);
            conn.setUseCaches(false);//不缓存
            conn.connect();
            InputStream is = conn.getInputStream();//获得图片的数据流
            bmp = BitmapFactory.decodeStream(is);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bmp;
    }
    public void showChoosePicDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("设置头像");
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
            tempUri = FileProvider.getUriForFile(Edit_information.this, "cn.chonor.final_pro.fileProvider", file);
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
                .withAspectRatio(9, 9)
                .withMaxResultSize(150, 150)
                .withOptions(options)
                .start(Edit_information.this);
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
                photo = ImageUtils.toRoundBitmap(photo); // 这个时候的图片已经被处理成圆形的了
                edit_img.setImageBitmap(photo);
                flag=true;
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
