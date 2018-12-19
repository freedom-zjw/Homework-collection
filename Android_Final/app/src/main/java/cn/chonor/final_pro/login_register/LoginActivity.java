package cn.chonor.final_pro.login_register;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.ResultSet;

import cn.chonor.final_pro.DataBase.Studentdb;
import cn.chonor.final_pro.DataBase.Teacherdb;
import cn.chonor.final_pro.login_info.SaveUser;
import cn.chonor.final_pro.model.Student;
import cn.chonor.final_pro.R;
import cn.chonor.final_pro.model.Teacher;

//Mainactivity负责登录
public class LoginActivity extends AppCompatActivity {
    private static  Integer RESULTINIT=101;
    private Button button_login;
    private TextView link_register;
    private TextInputLayout login_sid_inputlayout;
    private Student student=null;
    private Teacher teacher=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        setTitle("登录账户");
        //android:background="@mipmap/login_reg_bg"
        Bitmap bitmap= BitmapFactory.decodeResource(this.getResources(),R.mipmap.login_reg_bg);
        BitmapDrawable bitmapDrawable=new BitmapDrawable(bitmap);
        findViewById(R.id.login_layout).setBackgroundDrawable(bitmapDrawable);


        button_login = (Button) findViewById(R.id.login);
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText login_sid=(EditText)findViewById(R.id.login_sid);
                EditText login_password=(EditText)findViewById(R.id.login_password);
                final String num=login_sid.getText().toString();
                final String passwd=login_password.getText().toString();
                /**
                 * add by chonor
                 * to use mysql to login
                 * 这里有两个
                 * 长度为6是教师
                 * 8是学生
                 * 我已经判断好了
                 * 其他输入长度你自己处理
                 */
                if(num.length()==6){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Teacherdb teacherdb=new Teacherdb();
                            Message msg = Message.obtain();
                            msg.what=0;
                            try{
                                ResultSet rs=teacherdb.queryByNum(num);
                                if(rs.next()){
                                    teacher =new Teacher();
                                    teacher.setId(rs.getInt("tid"));
                                    teacher.setNum(rs.getString("tnum"));
                                    teacher.setSex(rs.getString("sex"));
                                    teacher.setNickname(rs.getString("nickname"));
                                    teacher.setPasswd(rs.getString("passwd"));
                                    teacher.setName(rs.getString("tname"));
                                    teacher.setAvatar(rs.getString("avatar"));
                                    teacher.setCollege(rs.getString("college"));
                                    teacher.setInfo(rs.getString("introduction"));
                                    teacher.setEmail(rs.getString("email"));
                                    teacher.setPhone(rs.getString("phone"));
                                    teacher.setOffice(rs.getString("office"));
                                    teacher.setPosition(rs.getString("position"));
                                    if(teacher.getPasswd().equals(passwd)){
                                        SaveUser.setTeacherInfo(LoginActivity.this, teacher);
                                        msg.what =2;
                                    }
                                    else msg.what=1;

                                }
                                rs.close();
                                teacherdb.close();
                            } catch (Exception e){

                            }

                            Handler.sendMessage(msg);
                        }
                    }).start();
                }else if(num.length()==8){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Studentdb studentdb=new Studentdb();
                            Message msg = Message.obtain();
                            msg.what=0;
                            try{
                                ResultSet rs=studentdb.queryByNum(num);
                                if(rs.next()){
                                    student =new Student();
                                    student.setId(rs.getInt("sid"));
                                    student.setNum(rs.getString("snum"));
                                    student.setSex(rs.getString("sex"));
                                    student.setNickname(rs.getString("nickname"));
                                    student.setPasswd(rs.getString("passwd"));
                                    student.setName(rs.getString("sname"));
                                    student.setAvatar(rs.getString("avatar"));
                                    student.setCollege(rs.getString("college"));
                                    student.setInfo(rs.getString("introduction"));
                                    if(student.getPasswd().equals(passwd)){
                                        SaveUser.setStudentInfo(LoginActivity.this, student);
                                        msg.what =2;
                                    }
                                    else msg.what=1;
                                }
                                rs.close();
                                studentdb.close();
                            } catch (Exception e){

                            }

                            Handler.sendMessage(msg);
                        }
                    }).start();
                }else{
                    login_sid_inputlayout=(TextInputLayout)findViewById(R.id.login_sid_inputlayout);
                    login_sid_inputlayout.setErrorEnabled(true);
                    login_sid_inputlayout.setError("输入无效，请检查");
                }

            }
        });

        link_register = (TextView) findViewById(R.id.gister_link);
        link_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jump_to_register();
            }
        });
    }

    /**
     * add by chonor
     *
     */
    private android.os.Handler Handler = new Handler() {
        public void handleMessage(Message msg) {
            if(msg.what==0){
                Toast.makeText(LoginActivity.this, "用户不存在", Toast.LENGTH_SHORT).show();
            }
            else if(msg.what==1){
                Toast.makeText(LoginActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
            }
            else if (msg.what == 2) {
                Toast.makeText(LoginActivity.this, "成功登陆", Toast.LENGTH_SHORT).show();
                if(student !=null) SaveUser.setStudentInfo(LoginActivity.this,student);
                else if(teacher !=null)SaveUser.setTeacherInfo(LoginActivity.this,teacher);
                setResult(RESULT_OK);
                finish();
            }
        }
    };


    //跳转用户个人中心界面
    public void jump_to_userCenter() {
        Toast.makeText(getApplicationContext(),"跳转到个人中心(待补充)",Toast.LENGTH_SHORT).show();
    }
    //跳转注册界面
    public void jump_to_register() {
        Intent intent=new Intent(LoginActivity.this, RegisterActivity.class);
        startActivityForResult(intent,RESULTINIT);
    }
    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        finish();
    }
}


