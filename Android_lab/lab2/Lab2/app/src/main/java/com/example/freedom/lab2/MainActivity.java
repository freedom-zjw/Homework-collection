package com.example.freedom.lab2;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import static android.media.MediaRecorder.VideoSource.CAMERA;

public class MainActivity extends AppCompatActivity {
    String tmp="";
    ImageView mImage=null;
    AlertDialog.Builder builder = null;
    RadioGroup mRadioGroup=null;
    RadioButton RadioButton_student=null;
    RadioButton RadioButton_teacher=null;
    Button Button_login=null;
    Button Button_register=null;
    TextInputLayout TextInputLayout_id =null;
    TextInputLayout TextInputLayout_password=null;
    EditText EditText_id=null;
    EditText EditText_password=null;
    CoordinatorLayout contain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Init();
    }

    public void Init(){ //初始化
        mImage = (ImageView) findViewById(R.id.imageView);
        builder = new AlertDialog.Builder(this);
        mRadioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        RadioButton_student = (RadioButton) findViewById(R.id.radioButton1);
        RadioButton_teacher = (RadioButton) findViewById(R.id.radioButton2);
        Button_login = (Button) findViewById(R.id.button);
        Button_register = (Button) findViewById(R.id.button2);
        EditText_id = (EditText) findViewById(R.id.textid);
        EditText_password = (EditText) findViewById(R.id.textpassword);
        TextInputLayout_id = (TextInputLayout) findViewById(R.id.textInputLayout);
        TextInputLayout_password = (TextInputLayout) findViewById(R.id.textInputLayout1);
        contain = (CoordinatorLayout)  findViewById(R.id.main_content);
        AlertDialogInit();
        lisenerInit();
    }

    public void AlertDialogInit(){
        builder.setTitle("上传头像");
        final String[] Choices={"拍摄","从相册选择"};
        builder.setItems(Choices,new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), "您选择了[" + Choices[i]+"]", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(),"您选择了[取消]", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setCancelable(true);
    }

    public void lisenerInit(){
        ImageListenerInit();
        RadioButtonListenerInit();
        ButtonListenerInit();
    }

    public void ImageListenerInit(){
        //图片按钮监听
        mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog dialog=builder.create();
                dialog.show();
            }
        });
    }

    public void RadioButtonListenerInit(){
        //radio 监听
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (RadioButton_student.getId() == checkedId) { //保存当前选择
                    tmp = RadioButton_student.getText().toString();
                }
                if (RadioButton_teacher.getId() == checkedId) {
                    tmp = RadioButton_teacher.getText().toString();
                }
                Snackbar.make(contain, "您选择了"+tmp, Snackbar.LENGTH_SHORT)
                        .setAction("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(getApplicationContext(),"Snackbar的确定按钮被点击了",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setActionTextColor(getResources().getColor(R.color.colorPrimary))
                        .show();
            }
        });
    }

    public void ButtonListenerInit(){
        //注册按钮监听
        Button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(contain, tmp + "注册功能尚未启用", Snackbar.LENGTH_SHORT)
                        .setAction("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(getApplicationContext(),"Snackbar的确定按钮被点击了",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setActionTextColor(getResources().getColor(R.color.colorPrimary))
                        .show();
            }
        });

        //登录按钮监听
        Button_login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(EditText_id.getText().toString().equals("123456")&&EditText_password.getText().toString().equals("6666")){
                    Snackbar.make(contain, "登录成功", Snackbar.LENGTH_SHORT)
                            .setAction("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Toast.makeText(getApplicationContext(),"Snackbar的确定按钮被点击了",Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setActionTextColor(getResources().getColor(R.color.colorPrimary))
                            .show();
                }
                else{
                    TextInputLayout_id.setError("学号不能为空");
                    TextInputLayout_password.setError("密码不能为空");
                    if(EditText_id.getText().toString().length()==0)
                          TextInputLayout_id.setErrorEnabled(true);
                    else TextInputLayout_id.setErrorEnabled(false);
                    if(EditText_password.getText().toString().length()==0)
                          TextInputLayout_password.setErrorEnabled(true);
                    else TextInputLayout_password.setErrorEnabled(false);
                    Snackbar.make(contain, "学号或密码错误", Snackbar.LENGTH_SHORT)
                            .setAction("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Toast.makeText(getApplicationContext(),"Snackbar的确定按钮被点击了",Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setActionTextColor(getResources().getColor(R.color.colorPrimary))
                            .show();
                }
            }
        });

    }
}
