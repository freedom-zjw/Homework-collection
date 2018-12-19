package cn.chonor.final_pro.login_register;

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
import com.yalantis.ucrop.UCropActivity;

import java.io.File;
import java.sql.ResultSet;

import cn.chonor.final_pro.DataBase.Studentdb;
import cn.chonor.final_pro.DataBase.Teacherdb;
import cn.chonor.final_pro.UpFiles.UpFile;
import cn.chonor.final_pro.info.CircleImageView;
import cn.chonor.final_pro.info.ImageUtils;
import cn.chonor.final_pro.R;
import cn.chonor.final_pro.model.Student;
import cn.chonor.final_pro.model.Teacher;

import static android.R.attr.maxWidth;

/**
 * Created by Jy on 2017/12/23.
 */

public class RegisterActivity extends AppCompatActivity {



    private Button register_create_button;
    private CircleImageView register_img;
    private EditText register_real_name;
    private EditText register_sid;
    private EditText register_department;
    private EditText register_name;
    private RadioGroup register_sex;
    private RadioButton register_male;
    private RadioButton register_female;
    private EditText register_first_password;
    private EditText register_second_password;
    private RadioGroup register_type;
    private RadioButton register_student;
    private RadioButton register_teacher;
    private EditText register_teacher_phone;
    private EditText register_teacher_email;
    private EditText register_teacher_office;
    private EditText register_teacher_work;
    private TextInputLayout reg_real_name;
    private TextInputLayout reg_sid;
    private TextInputLayout reg_department;
    private TextInputLayout reg_first_pass;
    private TextInputLayout reg_second_pass;
    private TextInputLayout reg_teacher_phone;
    private TextInputLayout reg_teacher_email;
    private TextInputLayout reg_teacher_office;
    private TextInputLayout reg_teacher_work;
    private TextInputLayout reg_name;


    private int sex=0;
    private int choose_type=0;
    protected static final int CHOOSE_PICTURE = 0;
    protected static final int TAKE_PICTURE = 1;
    private static final int CROP_SMALL_PICTURE = 2;
    protected static Uri tempUri;
    private static final String TAG = "Register_Img";
    private static String IMAGE_NAME="tmp_image.jpg";
    private int real_name_maxlength=4;
    private int sid_maxlength=8;
    private int name_maxlength=8;
    private int department_maxlength=8;
    private int password_maxlength=12;
    private int phone_maxlength=11;
    private int email_maxlength=20;
    private int office_maxlength=20;
    private int work_maxlength=5;
    private Bitmap photo=null;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        setTitle("注册账户");
        Bitmap bitmap= BitmapFactory.decodeResource(this.getResources(),R.mipmap.login_reg_bg);
        BitmapDrawable bitmapDrawable=new BitmapDrawable(bitmap);
        findViewById(R.id.register_layout).setBackgroundDrawable(bitmapDrawable);


        choose_type=0;
        sex=0;
        register_create_button=(Button)findViewById(R.id.register_create_button);
        register_img=(CircleImageView)findViewById(R.id.register_img);
        register_real_name=(EditText)findViewById(R.id.register_real_name);
        register_sid=(EditText)findViewById(R.id.register_sid);
        register_department=(EditText)findViewById(R.id.register_department);
        register_name=(EditText)findViewById(R.id.register_name);
        register_sex=(RadioGroup)findViewById(R.id.register_sex);
        register_male=(RadioButton)findViewById(R.id.register_male);
        register_female=(RadioButton)findViewById(R.id.register_female);
        register_first_password=(EditText)findViewById(R.id.register_first_password);
        register_second_password=(EditText)findViewById(R.id.register_second_password);
        register_type=(RadioGroup)findViewById(R.id.register_type);
        register_student=(RadioButton)findViewById(R.id.register_student);
        register_teacher=(RadioButton)findViewById(R.id.register_teacher);
        register_teacher_phone=(EditText)findViewById(R.id.register_teacher_phone);
        register_teacher_email=(EditText)findViewById(R.id.register_teacher_email);
        register_teacher_office=(EditText)findViewById(R.id.register_teacher_office);
        register_teacher_work=(EditText)findViewById(R.id.register_teacher_work);
        reg_real_name=(TextInputLayout)findViewById(R.id.reg_real_name);
        reg_sid=(TextInputLayout)findViewById(R.id.reg_sid);
        reg_department=(TextInputLayout)findViewById(R.id.reg_department) ;
        reg_first_pass=(TextInputLayout)findViewById(R.id.reg_first_password);
        reg_second_pass=(TextInputLayout)findViewById(R.id.reg_second_password);
        reg_teacher_phone=(TextInputLayout)findViewById(R.id.reg_teacher_phone);
        reg_teacher_email=(TextInputLayout)findViewById(R.id.reg_teacher_email);
        reg_teacher_office=(TextInputLayout)findViewById(R.id.reg_teacher_office);
        reg_teacher_work=(TextInputLayout)findViewById(R.id.reg_teacher_work);
        reg_name=(TextInputLayout)findViewById(R.id.reg_name);
        reg_teacher_phone.setVisibility(View.GONE);
        reg_teacher_email.setVisibility(View.GONE);
        reg_teacher_office.setVisibility(View.GONE);
        reg_teacher_work.setVisibility(View.GONE);

        reg_real_name.setErrorEnabled(true);
        reg_sid.setErrorEnabled(true);
        reg_department.setErrorEnabled(true);
        reg_name.setErrorEnabled(true);
        reg_first_pass.setErrorEnabled(true);
        reg_second_pass.setErrorEnabled(true);
        reg_teacher_email.setErrorEnabled(true);
        reg_teacher_phone.setErrorEnabled(true);
        reg_teacher_email.setErrorEnabled(true);
        reg_teacher_work.setErrorEnabled(true);
        reg_teacher_office.setErrorEnabled(true);


        register_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChoosePicDialog();
            }
        });

        choose_type=0;
        register_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {//根据选择类型调节id长度
            @Override
            public void onCheckedChanged(RadioGroup radioGroup,int i) {
                if(register_student.getId()==i) {
                    choose_type=0;
                    sid_maxlength=8;
                    reg_sid.setCounterMaxLength(8);
                    reg_teacher_phone.setVisibility(View.GONE);
                    reg_teacher_email.setVisibility(View.GONE);
                    reg_teacher_office.setVisibility(View.GONE);
                    reg_teacher_work.setVisibility(View.GONE);
                }
                else if(register_teacher.getId()==i) {
                    choose_type=1;
                    sid_maxlength=6;
                    reg_sid.setCounterMaxLength(6);
                    reg_teacher_phone.setVisibility(View.VISIBLE);
                    reg_teacher_email.setVisibility(View.VISIBLE);
                    reg_teacher_office.setVisibility(View.VISIBLE);
                    reg_teacher_work.setVisibility(View.VISIBLE);
                }
            }
        });
        register_sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup,int i) {
                if(register_male.getId()==i) sex=1;
                else if(register_female.getId()==i)sex=2;
                else sex=0;
            }
        });

        register_create_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register_new_account();
            }
        });

    }
    /**
     * 显示设置头像的对话框
     */
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
            tempUri = FileProvider.getUriForFile(RegisterActivity.this, "cn.chonor.final_pro.fileProvider", file);
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
        options.setToolbarColor(ActivityCompat.getColor(RegisterActivity.this, R.color.colorPrimary1));
        options.setStatusBarColor(ActivityCompat.getColor(RegisterActivity.this, R.color.colorPrimaryDark));
        options.setToolbarWidgetColor(Color.BLACK);
        UCrop.of(uri, mDestinationUri)
                .withAspectRatio(9, 9)
                .withMaxResultSize(150, 150)
                .withOptions(options)
                .start(RegisterActivity.this);
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
                register_img.setImageBitmap(photo);
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,@NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            takePicture();
        } else {
            // 没有获取 到权限，从新请求，或者关闭app
            Toast.makeText(this, "需要存储权限", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 注册账户
     * 补充创建用户操作。
     */
    private void register_new_account(){
        String real_name=register_real_name.getText().toString();
        String sid=register_sid.getText().toString();
        String department=register_department.getText().toString();
        String name=register_name.getText().toString();

        String first_pass=register_first_password.getText().toString();
        String second_pass=register_second_password.getText().toString();
        String phone=reg_teacher_phone.getEditText().getText().toString();
        String email=reg_teacher_email.getEditText().getText().toString();
        String offic=reg_teacher_office.getEditText().getText().toString();
        String pos=reg_teacher_work.getEditText().getText().toString();


        if(check_enable(real_name,sid,name,first_pass,second_pass,choose_type)&&check_length()){

            //创建用户
            //注意这里如果用户类型是老师的时候，把邮箱那些也传回去
            /**
             * add by chonor
             * to init
             */
            if(choose_type==0 &&sid.length()==8) {

                final Student student =new Student();
                if(photo!=null) {
                    uploadPic(sid);
                    student.setAvatar("https://chonor.cn/Android/Avatar/img/" + sid + ".png");
                }
                student.setName(real_name);
                student.setNum(sid);
                student.setNickname(name);
                student.setPasswd(first_pass);
                if (sex == 1) student.setSex("male");
                else if (sex == 2) student.setSex("female");
                if (department.length() != 0)
                    student.setCollege(department);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Studentdb studentdb = new Studentdb();
                        Message msg = Message.obtain();
                        msg.what = 0;
                        try {
                            ResultSet rs = studentdb.queryByNum(student.getNum());
                            if (rs.next()) {
                                msg.what = 2;
                            } else {
                                try {
                                    studentdb.insert(student);
                                    msg.what = 1;
                                } catch (Exception e) {

                                }
                            }
                            rs.close();
                            studentdb.close();
                        } catch (Exception e) {
                        }
                        Handler.sendMessage(msg);
                    }
                }).start();
                //返回登录界面
            }else if(choose_type==1 &&sid.length()==6){

                final Teacher teacher=new Teacher();
                if(photo!=null){
                    uploadPic(sid);
                    teacher.setAvatar("https://chonor.cn/Android/Avatar/img/"+sid+".png");
                }
                teacher.setName(real_name);
                teacher.setNum(sid);
                teacher.setNickname(name);
                teacher.setPasswd(first_pass);
                if (sex == 1) teacher.setSex("male");
                else if (sex == 2) teacher.setSex("female");
                if (department.length() != 0)
                    teacher.setCollege(department.toString());
                if(phone.length()!=0)teacher.setPhone(phone);
                if(email.length()!=0)teacher.setEmail(email);
                if(offic.length()!=0)teacher.setOffice(offic);
                if (pos.length()!=0)teacher.setPosition(pos);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Teacherdb teacherdb = new Teacherdb();
                        Message msg = Message.obtain();
                        msg.what = 0;
                        try {
                            ResultSet rs = teacherdb.queryByNum(teacher.getNum());
                            if (rs.next()) {
                                msg.what = 2;
                            } else {
                                try {
                                    teacherdb.insert(teacher);
                                    msg.what = 1;
                                } catch (Exception e) {

                                }
                            }
                            rs.close();
                            teacherdb.close();
                        } catch (Exception e) {
                        }
                        Handler.sendMessage(msg);
                    }
                }).start();
            }
        }
    }
    /**
     * add by chonor
     * to get info
     */
    private Handler Handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                Toast.makeText(RegisterActivity.this, "注册成功,请登录", Toast.LENGTH_SHORT).show();
                setResult(100);
                finish();
            }
            else if(msg.what==2){
                Toast.makeText(RegisterActivity.this, "学号/教工号已被注册", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(RegisterActivity.this, "连接服务器出错", Toast.LENGTH_SHORT).show();
            }
        }
    };

    /**
     * 检查是否满足注册要求
     * 需要补充的是:检查sid是否已经存在于数据库内，如果是，那么不允许重复注册
     * 此外，是否要考虑忘记密码功能
     * 有时间会补充实时提醒，而不是toast
     * @param real_name
     * @param sid
     * @param name
     * @param first_pass
     * @param second_pass
     * @param choose_type
     * @return
     */
    private boolean check_enable(String real_name,String sid,String name,String first_pass,String second_pass,int choose_type){
        if(real_name.equals("")){
            reg_real_name.setError("必须填写真实姓名，请补充");
            return false;
        }
        else if(sid.equals("")){
            reg_sid.setError("必须填写学号或教工号，请补充");
            return false;
        }
        else if(name.equals("")){
            reg_name.setError("必须填写昵称，请补充");
            return false;
        }
        else if(first_pass.equals("")){
            reg_first_pass.setError("必须填写密码，请补充");
            Toast.makeText(getApplicationContext(),"必须填写密码，请补充",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!first_pass.equals(second_pass)){
            reg_second_pass.setError("密码不一致，请重新填写");
            return false;
        }
        else if(choose_type==1&&register_teacher_email.getText().toString().equals("")){
            reg_teacher_email.setError("必须填写邮箱，请补充");
            return false;
        }
        return true;
    }

    private boolean check_length(){
        String real_name=register_real_name.getText().toString();
        String sid=register_sid.getText().toString();
        String department=register_department.getText().toString();
        String name=register_name.getText().toString();
        String first_pass=register_first_password.getText().toString();
        String second_pass=register_second_password.getText().toString();
        String phone=register_teacher_phone.getText().toString();
        String email=register_teacher_email.getText().toString();
        String office=register_teacher_office.getText().toString();
        String work=register_teacher_work.getText().toString();
        boolean flag_len=true;

        if(real_name.length()>real_name_maxlength){
            reg_real_name.setError("超出字数，输入无效");
            flag_len=false;
        }
        if(sid.length()>sid_maxlength) {
            reg_sid.setError("超出字数，输入无效");
            flag_len=false;
        }
        if(department.length()>department_maxlength) {
            reg_department.setError("超出字数，输入无效");
            flag_len=false;
        }
        if(name.length()>name_maxlength) {
            reg_name.setError("超出字数，输入无效");
            flag_len=false;
        }
        if(first_pass.length()>password_maxlength) {
            reg_first_pass.setError("超出字数，输入无效");
            flag_len=false;
        }
        if(second_pass.length()>password_maxlength) {
            reg_second_pass.setError("超出字数，输入无效");
            flag_len=false;
        }
        if(phone.length()>phone_maxlength) {
            reg_teacher_phone.setError("超出字数，输入无效");
            flag_len=false;
        }
        if(email.length()>email_maxlength) {
            reg_teacher_email.setError("超出字数，输入无效");
            flag_len=false;
        }
        if(office.length()>office_maxlength) {
            reg_teacher_office.setError("超出字数，输入无效");
            flag_len=false;
        }
        if(work.length()>work_maxlength) {
            reg_teacher_work.setError("超出字数，输入无效");
            flag_len=false;
        }


        return flag_len;
    }
    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        finish();
    }

}
