package cn.chonor.final_pro.Lesson;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import cn.chonor.final_pro.DataBase.Choicedb;
import cn.chonor.final_pro.DataBase.Coursedb;
import cn.chonor.final_pro.DataBase.Studentdb;
import cn.chonor.final_pro.FastBlurUtility.FastBlurUtility;
import cn.chonor.final_pro.Main_Tabhost;
import cn.chonor.final_pro.R;
import cn.chonor.final_pro.login_info.SaveUser;
import cn.chonor.final_pro.login_register.LoginActivity;
import cn.chonor.final_pro.model.Course;
import cn.chonor.final_pro.model.Student;
import cn.chonor.final_pro.model.Teacher;

/**
 * Created by Jy on 2017/12/24.
 */

public class Lesson_list extends AppCompatActivity {
    private ListView lesson_list;
    private SimpleAdapter adapter;
    private ArrayList<HashMap<String,String>> arrayList=new ArrayList<HashMap<String,String>>();
    private String map_key[]={"lesson_name","lesson_info","lesson_teacher"};
    private ArrayList<Course>allCourse=new ArrayList<>();
    int sid=-1;
    int tid=-1;
    private boolean logined=false;
    private boolean isTeacher=false;
    private String usernum="";
    private Student student;
    private Teacher teacher;
    private int pos=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lesson_list_activity);
        Bitmap bitmap= BitmapFactory.decodeResource(this.getResources(),R.mipmap.lessoninfo_bg);
        bitmap= FastBlurUtility.startBlurBackground(bitmap,5);
        BitmapDrawable bitmapDrawable=new BitmapDrawable(bitmap);
        findViewById(R.id.lesson_list_layout).setBackgroundDrawable(bitmapDrawable);


        setTitle("我的课程列表");
        GetLogin();
    }

    private void GetLogin(){
        usernum= SaveUser.getUserId(Lesson_list.this);
        if(usernum.equals("")){
            logined=false;
            setResult(RESULT_OK);
            finish();
        }
        else {
            logined = true;
            if(usernum.length()==6){//教师
                isTeacher=true;
                teacher=SaveUser.getTeacherInfo(Lesson_list.this);
            }else {//学生
                isTeacher=false;
                student = SaveUser.getStudentInfo(Lesson_list.this);
            }
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Coursedb coursedb=new Coursedb();
                    Message msg = Message.obtain();
                    msg.what=0;
                    try{
                        if(isTeacher)allCourse=coursedb.selectBytid(teacher.getId());
                        else allCourse=coursedb.selectBysid(student.getId());
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
                Toast.makeText(Lesson_list.this, "获取失败", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            }
            else if(msg.what==1){
                getData();
            }
            else if(msg.what==3){
                Toast.makeText(Lesson_list.this, "请检查网络", Toast.LENGTH_SHORT).show();
            }else if(msg.what==4){
                allCourse.remove(pos);
                arrayList.remove(pos);
                adapter.notifyDataSetChanged();
            }
        }
    };
    /**
     * 从数据库里查出该用户的课程的数量以及相关信息，每次数据库改动，都要更新ListView
     * @return
     */
    private void getData(){

        //查询得到通知的数目以及标题和内容
        int lesson_size=allCourse.size();

        if(lesson_size==0){
            HashMap<String, String> tempHashMap = new HashMap<String, String>();
            tempHashMap.put("lesson_name", "还没有课程");
            tempHashMap.put("lesson_info", "");
            tempHashMap.put("lesson_teacher","");
            arrayList.add(tempHashMap);
        }
        else{
            for(int i=0;i<lesson_size;i++){
                HashMap<String, String> tempHashMap = new HashMap<String, String>();
                tempHashMap.put("lesson_name", allCourse.get(i).getName());
                tempHashMap.put("lesson_info", "周"+allCourse.get(i).getWeek()+"  第"+allCourse.get(i).getTime()+"节");
                tempHashMap.put("lesson_teacher","任课老师："+allCourse.get(i).getTname());
                arrayList.add(tempHashMap);
            }
        }
        setData();
    }

    private  void setData(){
        lesson_list=(ListView)findViewById(R.id.lesson_list);
        adapter=new SimpleAdapter(this,arrayList,R.layout.lesson_list_item,map_key,new int[]{R.id.item_lesson_name,R.id.item_lesson_info,R.id.item_lesson_teacher});
        lesson_list.setAdapter(adapter);

        lesson_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(allCourse.size()>0) {
                    Intent intent = new Intent(Lesson_list.this, Lesson_information.class);
                    intent.putExtra("cid", allCourse.get(i).getCid());
                    startActivityForResult(intent, 100);
                }
            }
        });
        if(!isTeacher&&allCourse.size()>0) {
            lesson_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                    //删除，数据库也做出相对应改变
                    new AlertDialog.Builder(Lesson_list.this)
                            .setTitle("确定删除该课程？")
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    final Integer cid = allCourse.get(i).getCid();
                                    pos = i;
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Choicedb choicedb = new Choicedb();
                                            Message msg = Message.obtain();
                                            msg.what = 3;
                                            try {
                                                choicedb.delete(cid, student.getId());
                                                msg.what = 4;
                                            } catch (Exception e) {
                                            }
                                            Handler.sendMessage(msg);
                                        }
                                    }).start();
                                }
                            })
                            .create().show();
                    return true;
                }
            });
        }
            if(isTeacher&&allCourse.size()>0) {
                lesson_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view,final int i, long l) {
                        //删除，数据库也做出相对应改变
                        Intent intent=new Intent(Lesson_list.this,Student_list.class);
                        intent.putExtra("cid",allCourse.get(i).getCid());
                        startActivityForResult(intent,100);
                        return true;
                    }
                });
        }
    }
    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        finish();
    }
}
