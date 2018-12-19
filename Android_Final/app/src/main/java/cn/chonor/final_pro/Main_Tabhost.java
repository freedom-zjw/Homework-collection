package cn.chonor.final_pro;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import cn.chonor.final_pro.DataBase.Choicedb;
import cn.chonor.final_pro.DataBase.Commentdb;
import cn.chonor.final_pro.DataBase.Coursedb;
import cn.chonor.final_pro.DataBase.Homeworkdb;
import cn.chonor.final_pro.DataBase.Noticedb;
import cn.chonor.final_pro.DataBase.Studentdb;
import cn.chonor.final_pro.DataBase.Teacherdb;
import cn.chonor.final_pro.Date.GetTime;
import cn.chonor.final_pro.FastBlurUtility.FastBlurUtility;
import cn.chonor.final_pro.Lesson.Lesson_add;
import cn.chonor.final_pro.Lesson.Lesson_information;
import cn.chonor.final_pro.Lesson.Lesson_list;
import cn.chonor.final_pro.adapter.ChatAdapter;
import cn.chonor.final_pro.adapter.ClassInfoAdapter;
import cn.chonor.final_pro.adapter.ClassListAdapter;
import cn.chonor.final_pro.adapter.EndLessOnScrollListener;
import cn.chonor.final_pro.info.Edit_information;
import cn.chonor.final_pro.login_info.SaveUser;
import cn.chonor.final_pro.login_register.LoginActivity;
import cn.chonor.final_pro.model.Comment;
import cn.chonor.final_pro.model.Course;
import cn.chonor.final_pro.model.Student;
import cn.chonor.final_pro.model.Teacher;
import cn.chonor.final_pro.notice.Notice_information;
import cn.chonor.final_pro.notice.Notice_receive;
import cn.chonor.final_pro.notice.Notice_send;


public class Main_Tabhost extends AppCompatActivity implements View.OnClickListener{
    /**
     * add by chonor
     * 判断是否登录
     * 同时判断登录类型
     */
    private static final String STATICACTION = "cn.chonor.final_pro.staticreceiver";
    private int CntHW=0,CntNot=0;
    private boolean logins=true;
    private boolean logined=false;
    private boolean isTeacher=false;
    private boolean loadFlag=false;
    private String usernum="";
    private Student student;
    private Teacher teacher;
    private static Integer ResultLogin=100;
    private static Integer Resultchat=101;
    private static Integer ResultEdit=102;
    private static Integer ResultLessonInfo=103;
    private static Integer ResultLessonList=104;
    private static Integer ResultNoticeList=105;
    private static Integer ResultCouserAdd=106;
    private int pos=0;
    private String key;
    private int Onclickpos;
    private boolean issearch=false;
    private ArrayList<Course>ClassTable=new ArrayList<>();
    private ArrayList<Course>ClassToday=new ArrayList<>();
    private ArrayList<Course>Classtmp=new ArrayList<>();
    private ArrayList<Comment>comments=new ArrayList<>();
    private ArrayList<Course>AllClass=new ArrayList<>();
    public List<Map<String,String>> my_class=new ArrayList<>();//今日课程
    ClassInfoAdapter classInfoAdapter;//今日课程
    public SearchView searchView;
    public List<Map<String,String>> class_list=new ArrayList<>();//课程列表
    ClassListAdapter classListAdapter;//课程列表
    public List<Map<String,String>> chat_list=new ArrayList<>();//闲聊区
    ChatAdapter chatAdapter;//闲聊区
    static public Map<String,Integer> ClassList=new HashMap<>();//周&节-1，该key对应值为1表示这个时间段有课
    public ImageView user_avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_tabhost);

        findViewById(R.id.add_new_chat).setOnClickListener(this);
        Init_TabHost();
        Init_Today_Class_RecyclerView();
        Init_Class_List_RecyclerView();
        Init_SearchView();
        Init_ChatView();
        Init_My_Info();
        GetLesson(); //登录判断
        Init_UserInfo();

        Bitmap bitmap=BitmapFactory.decodeResource(this.getResources(),R.mipmap.user_info_bg);
        bitmap= FastBlurUtility.startBlurBackground(bitmap,5);
        BitmapDrawable bitmapDrawable=new BitmapDrawable((bitmap));
        findViewById(R.id.我).setBackgroundDrawable(bitmapDrawable);
    }
    /**
     * add by chonor
     * 判断是否登录
     */

    private void GetLesson(){
        usernum= SaveUser.getUserId(Main_Tabhost.this);
        if(usernum.equals("")){
            logined=false;
            Button button = (Button) findViewById(R.id.sign_out);
            button.setText("点击登录");
            findViewById(R.id.add_course).setVisibility(View.GONE);
            GetAllCourse(-1,-1);
        }
        else {
            logined = true;
            if(usernum.length()==6){//教师
                isTeacher=true;
                teacher=SaveUser.getTeacherInfo(Main_Tabhost.this);
                TextView textView=(TextView)findViewById(R.id.view_my_notice_text);
                textView.setText("查看我发布的通知");
                findViewById(R.id.add_course).setVisibility(View.VISIBLE);
                GetAllCourse(-1,teacher.getId());
                GetClassTable(-1,teacher.getId());
            }else {//学生
                isTeacher=false;
                student = SaveUser.getStudentInfo(Main_Tabhost.this);
                TextView textView=(TextView)findViewById(R.id.view_my_notice_text);
                textView.setText("查看我的通知");
                //Button addCourse=(Button)findViewById(R.id.add_course);
                findViewById(R.id.add_course).setVisibility(View.GONE);
                GetAllCourse(student.getId(),-1);
                GetClassTable(student.getId(),-1);
            }
            Button button = (Button) findViewById(R.id.sign_out);
            button.setText("退出登录");
        }
        GetComments(true);
    }
    /**
     *
     * 获取课程列表
     * @param sid 学号
     * @param tid 教工号
     *            -1为无
     */
    private void GetAllCourse(final int sid,final int tid){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Coursedb coursedb=new Coursedb();
                Message msg = Message.obtain();
                msg.what=0;
                try{
                    AllClass=coursedb.selectAll(0,10,sid,tid);
                    msg.what=2;
                    coursedb.close();
                } catch (Exception e){
                }
                Handler_Course.sendMessage(msg);
            }
        }).start();
    }
    /**
     * 获取课表
     * @param sid 学号
     * @param tid 教工号
     */
    private void GetClassTable(final int sid,final  int tid){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Coursedb coursedb=new Coursedb();
                Message msg = Message.obtain();
                Noticedb noticedb=new Noticedb();
                Homeworkdb homeworkdb=new Homeworkdb();
                msg.what=0;
                try{
                    if(sid!=-1){
                        String date=GetTime.GetYeat()+"/"+GetTime.GetMonth()+"/"+GetTime.GetDay();
                        ClassTable=coursedb.selectBysid(sid);
                        CntHW=homeworkdb.CountToday(sid,date);
                        CntNot=noticedb.CountToday(sid,date);
                    }
                    else ClassTable=coursedb.selectBytid(tid);
                    msg.what=1;
                    coursedb.close();
                } catch (Exception e){
                }
                Handler_Course.sendMessage(msg);
            }
        }).start();
    }

    /**
     * 加载课程
     * @param sizes
     */
    private void ReloadCourse(final int sizes){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Coursedb coursedb = new Coursedb();
                Message msg = Message.obtain();
                msg.what = 0;
                try {
                    if(sizes==-1) {
                        if (logined) {
                            if (isTeacher)
                                AllClass = coursedb.selectAll(0, 10, -1, teacher.getId());
                            else AllClass = coursedb.selectAll(0, 10, student.getId(), -1);
                        } else
                            AllClass = coursedb.selectAll(0, 10, -1, -1);
                        msg.what = 2;
                    }else {
                        if (logined) {
                            if (isTeacher)
                                AllClass = coursedb.selectAll(sizes, 10, -1, teacher.getId());
                            else
                                AllClass = coursedb.selectAll(sizes, 10, student.getId(), -1);
                        } else {
                            AllClass = coursedb.selectAll(sizes, 10, -1, -1);
                        }
                        msg.what = 3;
                    }
                    coursedb.close();
                } catch (Exception e) {
                }
                Handler_Course.sendMessage(msg);
            }
        }).start();
    }
    /**
     * 加载课程表
     */
    private android.os.Handler Handler_Course = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                Toast.makeText(Main_Tabhost.this, "获取课表失败请检查网络", Toast.LENGTH_SHORT).show();
            } else if (msg.what == 1) {
                CheckChoose();
                if(logins&&!isTeacher){
                    broad_init();
                    logins=false;
                }
                Filling_ListClassToday();
                Update_ViewTodayClass();
                Update_Class_Table();
            }
            else if(msg.what==2) {//刷新课表
                if(!isTeacher&&logined)CheckChoose();
                Update_ViewClassList();
            }
            else if(msg.what==3){//加载课表
                for(int i=0;i<Classtmp.size();i++)
                    AllClass.add(Classtmp.get(i));
                if(!isTeacher&&logined)CheckChoose();
                Update_ViewClassList();
            }
            else if(msg.what==4){//搜索
                issearch=true;
                if(!isTeacher&&logined)CheckChoose();
                Update_ViewClassList();
            }
            loadFlag=false;
        }
    };
    /**
     * 对照课表查询是否选课
     */
    private void CheckChoose(){
        for(int i=0;i<AllClass.size();i++){
            for(int j=0;j<ClassTable.size();j++){
                if(AllClass.get(i).getCid()==ClassTable.get(j).getCid())
                    AllClass.get(i).setChoose(1);
            }
        }
    }
    /**
     * 获取评论
     */
    private void GetComments(final  boolean flags){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Commentdb commentdb=new Commentdb();
                Message msg = Message.obtain();
                msg.what=0;
                try{
                    if(flags){
                        comments=commentdb.selectALL(0,5);
                        msg.what=1;
                    }
                    else {
                        comments=commentdb.selectALL(chat_list.size(),5);
                        msg.what=1;
                    }
                } catch (Exception e){
                }
                Handler_Comment.sendMessage(msg);
            }
        }).start();
    }
    private android.os.Handler Handler_Comment = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 0) {//失败
                Toast.makeText(Main_Tabhost.this, "评论失败请检查网络", Toast.LENGTH_SHORT).show();
            } else if (msg.what == 1) {
                for(int i=0;i<comments.size();i++){
                    Add_Chat_List(comments.get(i));
                }
            }
            loadFlag=false;
        }
    };


    /**
     * 课表下拉刷新
     */
    public void Class_List_Drop_Down(){ //课程列表下拉刷新
        if(!loadFlag) {
            loadFlag=true;
            ReloadCourse(-1);
            if(issearch){
                Toast.makeText(Main_Tabhost.this,"取消搜索",Toast.LENGTH_SHORT).show();
                issearch=false;
                SearchView searchView=(SearchView)findViewById(R.id.class_list_searchview);
                searchView.setQuery("",false);
            }
        }
    }
    /**
     * 聊天列表下拉刷新
     */
    public void Chat_List_Drop_Down(){ //聊天界面下拉刷新
        if(!loadFlag&&!issearch) {
            loadFlag=true;
            for (int i = chat_list.size()-1; i >=0; i--) {
                chat_list.remove(i);
                chatAdapter.notifyDataSetChanged();
            }
            GetComments(true);
        }
    }

    /**
     * 课表上拉加载
     */
    public void Class_List_Drop_Up(){ //课程列表上拉加载
        if(!loadFlag) {
            loadFlag=true;
            Classtmp.clear();
            ReloadCourse(AllClass.size());
        }
    }
    /**
     * 聊天界面上拉加载
     */
    public void Chat_List_Drop_Up(){ //聊天界面上拉加载
        if(!loadFlag){
            loadFlag=true;
            GetComments(false);
        }
    }


    public void Go_To_Lesson_Info(int cid){
        Intent intent = new Intent(Main_Tabhost.this, Lesson_information.class);
        intent.putExtra("cid",cid);
        startActivityForResult(intent, ResultLessonInfo);
    }
    public void Click_Today_Class(View v,int position){ //今日课程点击事件
        Go_To_Lesson_Info(Integer.parseInt(my_class.get(position).get("cid")));
    }
    public void Submit_SearchView(final String query){ //课程列表的搜索栏提交事件
        loadFlag=true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                Coursedb coursedb=new Coursedb();
                Message msg = Message.obtain();
                msg.what=0;
                try{
                    AllClass=coursedb.selectBySearch(query);
                    msg.what=4;
                    coursedb.close();
                } catch (Exception e){
                }
                Handler_Course.sendMessage(msg);
            }
        }).start();
    }
    public void Click_Class_List(View v,int position){ //课程列表点击事件
        Go_To_Lesson_Info(Integer.parseInt(class_list.get(position).get("cid")));
    }

    public void Long_Click_Class_List(View v, final int position){ //课程列表长按事件
        if(!isTeacher&&logined){
            final int choose=Integer.parseInt(class_list.get(position).get("isChoose"));
            final int cid=Integer.parseInt(class_list.get(position).get("cid"));//这门课程的cid
            key=class_list.get(position).get("key");//该门课的时间段
            AlertDialog.Builder talk1=new AlertDialog.Builder(this);
            if(choose==0) talk1.setTitle("是否选择这门课?");
            else talk1.setTitle("是否退选这门课?");
            talk1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(choose==0){ //选课模式
                        if(ClassList.containsKey(key)&&ClassList.get(key)==1){
                            Toast.makeText(Main_Tabhost.this,"选课失败，该时间段已有课",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            //选课成功
                            pos=position;
                            SelectCourse(cid);
                        }
                    }
                    else{ //退课模式
                        pos=position;
                        unSelectCourse(cid);
                    }
                }
            });
            talk1.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            talk1.show();
        }
    }
    private void SelectCourse(final int cid){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Choicedb choicedb = new Choicedb();
                Coursedb coursedb = new Coursedb();
                Message msg = Message.obtain();
                msg.what = 0;
                try {
                    choicedb.insert(cid,student.getId());
                    ClassTable=coursedb.selectBysid(student.getId());
                    msg.what=100;
                }catch (Exception e){

                }
                HandlerSelect.sendMessage(msg);
            }
        }).start();
    }
    private void unSelectCourse(final int cid){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Choicedb choicedb = new Choicedb();
                Coursedb coursedb = new Coursedb();
                Message msg = Message.obtain();
                msg.what = 0;
                try {
                    choicedb.delete(cid,student.getId());
                    ClassTable=coursedb.selectBysid(student.getId());
                    msg.what=101;
                }catch (Exception e){

                }
                HandlerSelect.sendMessage(msg);
            }
        }).start();
    }
    /**
     * 接收更新的消息
     */
    private android.os.Handler HandlerSelect = new Handler() {
        public void handleMessage(Message msg) {
            if(msg.what==0){
                Toast.makeText(Main_Tabhost.this, "操作失败请检查网络", Toast.LENGTH_SHORT).show();
            }
            else if(msg.what==100){
                Toast.makeText(Main_Tabhost.this,"选课成功",Toast.LENGTH_SHORT).show();
                ClassList.put(key,1);//map对应key置1
                Change_Mark(pos,"visible");//该位置对勾显示
                Filling_ListClassToday();
                Update_ViewTodayClass();
                Update_Class_Table();
                class_list.get(pos).put("isChoose",1+"");//RecyclerList中这个课程的是否已选信息设为已选
            }else if(msg.what==101){
                Toast.makeText(Main_Tabhost.this,"退选成功",Toast.LENGTH_SHORT).show();
                ClassList.put(key,0);//map该位置置0
                Change_Mark(pos,"gone");//该位置对勾消失
                Filling_ListClassToday();
                Update_ViewTodayClass();
                Update_Class_Table();
                class_list.get(pos).put("isChoose",0+"");
            }
        }
    };

    public void Click_Chat_item(View v,final int position,int which){ //聊天吐槽界面中的item点击事件，position无须赘述，which是判断种类
        if(logined) {
            final int cid = Integer.parseInt(chat_list.get(position).get("cid"));/**该条评论cid**/
            Onclickpos=position;
            if (which == 0) { //点击赞
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Commentdb commentdb = new Commentdb();
                        Message msg = Message.obtain();
                        msg.what = 0;
                        try {
                            if (!commentdb.selectup(cid, usernum)) {
                                commentdb.insertup(cid, usernum);
                                commentdb.updateup(cid, Integer.parseInt(chat_list.get(position).get("up_num")) + 1);
                                msg.what = 201;
                            }else msg.what=205;
                            commentdb.close();
                        } catch (Exception e) {
                        }
                        HandlerChat.sendMessage(msg);
                    }
                }).start();
            } else if (which == 1) { //点击踩
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Commentdb commentdb = new Commentdb();
                        Message msg = Message.obtain();
                        msg.what = 0;
                        try {
                            if (!commentdb.selectdown(cid, usernum)) {
                                commentdb.insertdown(cid, usernum);
                                commentdb.updatedown(cid, Integer.parseInt(chat_list.get(position).get("down_num"))+ 1);
                                msg.what = 202;
                            }else msg.what=205;
                            commentdb.close();
                        } catch (Exception e) {
                        }
                        HandlerChat.sendMessage(msg);
                    }
                }).start();
            } else if (which == 2) { //点击举报
                final int report = Integer.parseInt(chat_list.get(position).get("report"));//获得当前举报数
                if (report == 19) { //删除该条评论
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Commentdb commentdb = new Commentdb();
                            Message msg = Message.obtain();
                            msg.what = 0;
                            try {
                                commentdb.delete(cid);
                                msg.what = 204;
                                commentdb.close();
                            } catch (Exception e) {
                            }
                            HandlerChat.sendMessage(msg);
                        }
                    }).start();
                }else{
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Commentdb commentdb = new Commentdb();
                            Message msg = Message.obtain();
                            msg.what = 0;
                            try {
                                if (!commentdb.selectreport(cid, usernum)) {
                                    commentdb.insertreport(cid, usernum);
                                    commentdb.updatereport(cid, report + 1);
                                    msg.what = 203;
                                }else msg.what=205;
                                commentdb.close();
                            } catch (Exception e) {
                            }
                            HandlerChat.sendMessage(msg);
                        }
                    }).start();
                }
            }
        }
    }
    private android.os.Handler HandlerChat = new Handler() {
        public void handleMessage(Message msg) {
            if(msg.what==0){
                Toast.makeText(Main_Tabhost.this, "操作失败请检查网络", Toast.LENGTH_SHORT).show();
            }
            else if(msg.what==201){
                SetUpNum(Onclickpos, Integer.parseInt(chat_list.get(Onclickpos).get("up_num")) + 1);
            }else if(msg.what==202){
                SetDownNum(Onclickpos, Integer.parseInt(chat_list.get(Onclickpos).get("down_num"))+ 1);//更改TextView
            }
            else if(msg.what==203){
                SetReportNum(Onclickpos, Integer.parseInt(chat_list.get(Onclickpos).get("report")) + 1);//更改TextView
            }
            else if(msg.what==204){
                chat_list.remove(Onclickpos);
                chatAdapter.notifyDataSetChanged();
            }
            else {
                Toast.makeText(Main_Tabhost.this, "你已经点过了",Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.avatar){ //个人中心界面点击头像
            if(!logined){
                Intent intent=new Intent(Main_Tabhost.this, LoginActivity.class);
                startActivityForResult(intent,ResultLogin);
            }
        }
        else if(v.getId()==R.id.my_class_list){ //个人中心界面点击"我的课程列表"
            if(!logined) Toast.makeText(this,"请登录",Toast.LENGTH_SHORT).show();
            else{
                Intent intent = new Intent(Main_Tabhost.this, Lesson_list.class);
                startActivityForResult(intent, ResultLessonList);
            }
        }
        else if(v.getId()==R.id.edit_my_info) { //个人中心界面点击"编辑我的信息"
            if(!logined) Toast.makeText(this,"请登录",Toast.LENGTH_SHORT).show();
            else{
                Intent intent = new Intent(Main_Tabhost.this, Edit_information.class);
                startActivityForResult(intent, ResultEdit);
            }
        }
        else if(v.getId()==R.id.view_my_notice){ //个人中心界面点击"查看我的通知"
            if(!logined) Toast.makeText(this,"请登录",Toast.LENGTH_SHORT).show();
            else {
                Intent intent = new Intent(Main_Tabhost.this, Notice_receive.class);
                startActivityForResult(intent, ResultNoticeList);
            }
        }
        else if(v.getId()==R.id.add_course){
            Intent intent = new Intent(Main_Tabhost.this,Lesson_add.class);
            startActivityForResult(intent, ResultCouserAdd);
        }
        else if(v.getId()==R.id.system_setting){ //个人中心界面点击"系统设置"

        }

        else if(v.getId()==R.id.sign_out) { //个人中心界面点击"退出登录"
            if (logined) {
                SaveUser.clearInfo(Main_Tabhost.this);
                SaveUser.setUserId(Main_Tabhost.this,"");
                logined=false;
                ClassTable.clear();
                ClassToday.clear();
                Classtmp.clear();
                AllClass.clear();
                ClassList.clear();
                Filling_ListClassToday();
                Update_ViewTodayClass();
                Update_ViewClassList();
                Update_Class_Table();
            }
            Intent intent = new Intent(Main_Tabhost.this, LoginActivity.class);
            startActivityForResult(intent, ResultLogin);
        }
        else if(v.getId()==R.id.add_new_chat){
            if(logined) startActivityForResult(new Intent("ADD_NEW_CHAT"),Resultchat);
            else Toast.makeText(Main_Tabhost.this,"请先登录",Toast.LENGTH_SHORT).show();
        }
        else if(v.getTag()!=null){
            Go_To_Lesson_Info((int)v.getTag());
        }
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus){ //这里是给课程表设置宽高和背景
        GridLayout gridLayout=(GridLayout)findViewById(R.id.grid);
        int screen_width=this.getWindowManager().getDefaultDisplay().getWidth();
        int height=gridLayout.getHeight();
        for(int i=0;i<gridLayout.getChildCount();i++){
            TextView textView=(TextView) gridLayout.getChildAt(i);
            textView.setHeight(height/5);
            Log.i("TextView_height",""+textView.getHeight());
            if(i%8==0){
                //textView.setBackgroundColor(getResources().getColor(R.color.col_bg));
                int tmp=i%8+i/8+1;
                textView.setText("   "+tmp);
                textView.setWidth((screen_width/8));
                textView.setTextSize(20);
            }
            else{
                textView.setTextSize(15);
                if(i%8==1) textView.setWidth((screen_width/8));
                else textView.setWidth((screen_width/8)-6);
                textView.setText("");
                textView.setBackgroundResource(R.drawable.class_empty_bg);
                /*Random random=new Random();
                int rand=random.nextInt(4);
                if(rand==0){
                    textView.setBackgroundResource(R.drawable.class_bg);
                    textView.setText("云计算@东B402");
                }
                else if(rand==1){
                    textView.setBackgroundResource(R.drawable.class_bg2);
                    textView.setText("人工智能@东B202");
                }
                else if(rand==2) {
                    textView.setBackgroundResource(R.drawable.class_bg3);
                    textView.setText("数据库@东B201");
                }
                else{
                    textView.setText("");
                    textView.setBackgroundResource(R.drawable.class_empty_bg);
                }*/
            }
        }
        Update_Class_Table();
        super.onWindowFocusChanged(hasFocus);
    }

    public String curtab=new String();
    public void Init_TabHost(){
        int screen_width=getWindowManager().getDefaultDisplay().getWidth();
        View mRoot = getLayoutInflater().inflate(R.layout.main_tabhost, null);
        TabHost tabHost=(TabHost) findViewById(R.id.tabhost);
        tabHost.setup();
        View tmp1=getLayoutInflater().inflate(R.layout.tab_indicator,(ViewGroup)mRoot.findViewById(android.R.id.tabs),false);
        final TextView textView1=(TextView)tmp1.findViewById(R.id.text1);
        textView1.setWidth(screen_width/5);
        textView1.setText("课程表");
        textView1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tabHost.addTab(tabHost.newTabSpec("one").setIndicator(tmp1).setContent(R.id.我的课程));
        View tmp2=getLayoutInflater().inflate(R.layout.tab_indicator,(ViewGroup)mRoot.findViewById(android.R.id.tabs),false);
        final TextView textView2=(TextView)tmp2.findViewById(R.id.text1);
        textView2.setWidth(screen_width/5);
        textView2.setText("今日课程");
        textView2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tabHost.addTab(tabHost.newTabSpec("two").setIndicator(tmp2).setContent(R.id.今日课程));
        View tmp3=getLayoutInflater().inflate(R.layout.tab_indicator,(ViewGroup)mRoot.findViewById(android.R.id.tabs),false);
        final TextView textView3=(TextView)tmp3.findViewById(R.id.text1);
        textView3.setWidth(screen_width/5);
        textView3.setText("所有课程");
        textView3.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tabHost.addTab(tabHost.newTabSpec("three").setIndicator(tmp3).setContent(R.id.课程列表));
        View tmp4=getLayoutInflater().inflate(R.layout.tab_indicator,(ViewGroup)mRoot.findViewById(android.R.id.tabs),false);
        final TextView textView4=(TextView)tmp4.findViewById(R.id.text1);
        textView4.setWidth(screen_width/5);
        textView4.setText("下课聊");
        textView4.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tabHost.addTab(tabHost.newTabSpec("four").setIndicator(tmp4).setContent(R.id.下课聊));
        View tmp5=getLayoutInflater().inflate(R.layout.tab_indicator,(ViewGroup)mRoot.findViewById(android.R.id.tabs),false);
        final TextView textView5=(TextView)tmp5.findViewById(R.id.text1);
        textView5.setWidth(screen_width/5);
        textView5.setText("我");
        textView5.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tabHost.addTab(tabHost.newTabSpec("five").setIndicator(tmp5).setContent(R.id.我));
        tabHost.setCurrentTab(0);
        textView1.setTextColor(Color.rgb(255,0,0));
        curtab="one";
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                //Toast.makeText(Main_Tabhost.this,tabId,Toast.LENGTH_SHORT).show();
                switch (curtab){
                    case "one": textView1.setTextColor(Color.rgb(51,51,51)); break;
                    case "two": textView2.setTextColor(Color.rgb(51,51,51)); break;
                    case "three": textView3.setTextColor(Color.rgb(51,51,51)); break;
                    case "four": textView4.setTextColor(Color.rgb(51,51,51)); break;
                    case "five": textView5.setTextColor(Color.rgb(51,51,51)); break;
                }
                switch (tabId){
                    case "one": textView1.setTextColor(Color.rgb(255,0,0)); break;
                    case "two": textView2.setTextColor(Color.rgb(255,0,0)); break;
                    case "three": textView3.setTextColor(Color.rgb(255,0,0)); break;
                    case "four": textView4.setTextColor(Color.rgb(255,0,0)); break;
                    case "five": textView5.setTextColor(Color.rgb(255,0,0)); break;
                }
                curtab=tabId;
            }
        });
    }
    public void Init_Today_Class_RecyclerView(){
        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.today_class);//今日课程
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        classInfoAdapter=new ClassInfoAdapter(this,my_class);
        classInfoAdapter.setItemClickListener(new ClassInfoAdapter.OnItemClickListener() {
            @Override
            public void OnClick(View v, int position) {
                Click_Today_Class(v,position);
            }

            @Override
            public void OnLongClick(View v, int position) {
                //Toast.makeText(MainActivity.this,"长按",Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(classInfoAdapter);
    }
    public void Init_Class_List_RecyclerView(){
        final SwipeRefreshLayout swipeRefreshLayout=(SwipeRefreshLayout) findViewById(R.id.class_list_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Class_List_Drop_Down();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        RecyclerView class_list_view=(RecyclerView)findViewById(R.id.class_list_recycler_view);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        class_list_view.setLayoutManager(linearLayoutManager);
        class_list_view.addOnScrollListener(new EndLessOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                Class_List_Drop_Up();
            }
        });
        classListAdapter=new ClassListAdapter(this,class_list);
        classListAdapter.setItemClickListener(new ClassListAdapter.OnItemClickListener() {
            @Override
            public void OnClick(View v, int position) {
                Click_Class_List(v,position);
            }

            @Override
            public void OnLongClick(View v, int position) {
                Long_Click_Class_List(v,position);
            }
        });
        class_list_view.setAdapter(classListAdapter);
    }
    public void Init_SearchView(){
        searchView=(SearchView)findViewById(R.id.class_list_searchview);
        searchView.setIconifiedByDefault(false);
        searchView.setSubmitButtonEnabled(true);
        searchView.onActionViewExpanded();
        searchView.setQueryHint("请输入课程名称");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Submit_SearchView(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }
    public void Init_ChatView(){
        final SwipeRefreshLayout swipeRefreshLayout=(SwipeRefreshLayout) findViewById(R.id.chat_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Chat_List_Drop_Down();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.chat_view);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addOnScrollListener(new EndLessOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                Chat_List_Drop_Up();
            }
        });
        chatAdapter=new ChatAdapter(this,chat_list);
        chatAdapter.setItemClickListener(new ChatAdapter.OnItemClickListener() {
            @Override
            public void OnClick(View v, int position, int which) {
                Click_Chat_item(v,position,which);
            }
        });
        recyclerView.setAdapter(chatAdapter);
    }
    public void Init_My_Info(){
        /*int screen_width=this.getWindowManager().getDefaultDisplay().getWidth();
        screen_width*=0.9;
        Button my_class_list=(Button) findViewById(R.id.my_class_list);
        my_class_list.setWidth(screen_width);
        Button edit_my_info=(Button) findViewById(R.id.edit_my_info);
        edit_my_info.setWidth(screen_width);
        Button view_my_notice=(Button) findViewById(R.id.view_my_notice);
        view_my_notice.setWidth(screen_width);
        Button addCourse=(Button)findViewById(R.id.add_course);
        addCourse.setWidth(screen_width);
        Button system_setting=(Button) findViewById(R.id.system_setting);
        system_setting.setWidth(screen_width);
        Button sign_out=(Button) findViewById(R.id.sign_out);
        sign_out.setWidth(screen_width);*/

        findViewById(R.id.avatar).setOnClickListener(this);
        findViewById(R.id.my_class_list).setOnClickListener(this);
        findViewById(R.id.edit_my_info).setOnClickListener(this);
        findViewById(R.id.view_my_notice).setOnClickListener(this);
        findViewById(R.id.add_course).setOnClickListener(this);
        findViewById(R.id.system_setting).setOnClickListener(this);
        findViewById(R.id.sign_out).setOnClickListener(this);
    }

    public void Add_Today_Class(Course course){ //添加今日课程
        String name=course.getName();
        String time_and_place="时间地点："+"第"+course.getTime()+"节 "+course.getPos();
        String teacher_="任课教师："+course.getTname();
        Map<String,String> tmp=new HashMap<>();
        tmp.put("class_name",name);
        tmp.put("time_and_place",time_and_place);
        tmp.put("teacher",teacher_);
        tmp.put("img","gone");
        tmp.put("cid",""+course.getCid());
        my_class.add(tmp);
        classInfoAdapter.notifyDataSetChanged();
    }
    public void Delete_Today_Class(int position){
        my_class.remove(position);
        classInfoAdapter.notifyDataSetChanged();
    }
    public void Change_Notice(int position,String type){ //传入item的位置和type，比如要这个position的小红点显示，type=visible; 要隐藏这个position的小红点就type=其他任何值
        if(type.equals("visible")){
            my_class.get(position).put("img","visible");
        }
        else my_class.get(position).put("img","gone");
        classInfoAdapter.notifyDataSetChanged();
    }
    public void Change_No_Login_Hint(String type){ //未登录的提示，type=visible可见，其他值隐藏
        if(type.equals("visible")){
            findViewById(R.id.no_login_hint).setVisibility(View.VISIBLE);
        }
        else findViewById(R.id.no_login_hint).setVisibility(View.GONE);
    }
    public void Add_Class_list(Course course){
        String name=course.getName();
        String time_and_place="时间地点："+"周"+course.getWeek()+"  第"+course.getTime()+"节 "+course.getPos();
        String teacher_=course.getTname();
        String starting_unit="开设单位："+course.getCollege();
        String class_info=course.getInfo();
        Map<String,String> tmp=new HashMap<>();
        tmp.put("class_name",name);
        tmp.put("time_and_place",time_and_place);
        tmp.put("teacher",teacher_);
        tmp.put("starting_unit",starting_unit);
        tmp.put("class_info",class_info);
        if(course.getChoose()==1) tmp.put("mark","visible");
        else tmp.put("mark","gone");
        tmp.put("cid",""+course.getCid());
        tmp.put("isChoose",""+course.getChoose());
        tmp.put("key",course.getWeek()+course.getTime());
        class_list.add(tmp);
        classListAdapter.notifyDataSetChanged();
    }
    public void Change_Mark(int position,String type){ //传入item的位置和type，比如要这个position的对勾显示，就让type=visible，隐藏就是其他任何值
        if(type.equals("visible")){
            class_list.get(position).put("mark","visible");
        }
        else class_list.get(position).put("mark","gone");
        classListAdapter.notifyDataSetChanged();
    }
    public void Add_Chat_List(Comment comment){
        String chat_avatar=comment.getAvatar();
        String nickname=comment.getName();
        String college=comment.getCollege();
        String main_text=comment.getInfo();
        String user_image=comment.getSrc();
        int up_num=comment.getUp();
        int down_num=comment.getDown();
        Map<String,String> tmp=new HashMap<>();
        tmp.put("chat_avatar",chat_avatar);
        tmp.put("nickname",nickname);
        tmp.put("college",college);
        tmp.put("main_text",main_text);
        tmp.put("user_image",user_image);
        tmp.put("up_num",up_num+"");
        tmp.put("down_num",down_num+"");
        tmp.put("report",comment.getReport()+"");
        tmp.put("cid",comment.getCid()+"");
        chat_list.add(tmp);
        chatAdapter.notifyDataSetChanged();
    }
    public Map<String,Integer> no_more_rand=new HashMap<>();
    public void Update_Class_Table(){ //根据ClassTable这个ArrayList更新View
        Clear_Class_Table();
        GridLayout gridLayout=(GridLayout)findViewById(R.id.grid);
        for(int i=0;i<ClassTable.size();i++){
            int week=Integer.parseInt(ClassTable.get(i).getWeek());//周数
            int time=Integer.parseInt(ClassTable.get(i).getTime());//节数
            int which=(time-1)*8+week;
            TextView textView=(TextView) gridLayout.getChildAt(which);
            textView.setText(ClassTable.get(i).getName()+"@"+ClassTable.get(i).getPos());
            textView.setTag(ClassTable.get(i).getCid());
            textView.setOnClickListener(Main_Tabhost.this);
            String key=ClassTable.get(i).getWeek()+ClassTable.get(i).getTime();//时间段
            int bg;
            if(no_more_rand.containsKey(key)) bg=no_more_rand.get(key);
            else {
                Random random=new Random();
                bg=random.nextInt(3);
                no_more_rand.put(key,bg);
            }
            if(bg==0) textView.setBackgroundResource(R.drawable.class_bg);
            else if(bg==1) textView.setBackgroundResource(R.drawable.class_bg2);
            else if(bg==2) textView.setBackgroundResource(R.drawable.class_bg3);
            ClassList.put(key,1);//表示这个时间段存在课
        }
    }
    public void Clear_Class_Table(){ //清空课程表的View
        GridLayout gridLayout=(GridLayout)findViewById(R.id.grid);
        for(int i=0;i<gridLayout.getChildCount();i++){
            TextView textView=(TextView) gridLayout.getChildAt(i);
            if(i%8==0){
                textView.setBackgroundColor(getResources().getColor(R.color.col_bg));
                int tmp=i%8+i/8+1;
                textView.setText("   "+tmp);
            }
            else{
                textView.setText("");
                textView.setBackgroundResource(R.drawable.class_empty_bg);
            }
        }
    }

    public void Filling_ListClassToday(){//根据课程表所有课程填充今日课程
        ClassToday.clear();
        String week=GetTime.GetWeek();
        for(int i=0;i<ClassTable.size();i++){
            if(ClassTable.get(i).getWeek().equals(week)){
                ClassToday.add(ClassTable.get(i));
            }
        }
    }
    public void Update_ViewTodayClass(){ //更新今日课程View
        for(int i=0;i<my_class.size();i++){
            my_class.remove(0);
            classInfoAdapter.notifyDataSetChanged();
        }
        for(int i=0;i<ClassToday.size();i++){
            Add_Today_Class(ClassToday.get(i));
        }
        String week=GetTime.GetWeek();
        TextView today_class_week_and_day=(TextView)findViewById(R.id.today_class_week_and_day);
        today_class_week_and_day.setText("周"+week);
        TextView today_class_num=(TextView)findViewById(R.id.today_class_num);
        today_class_num.setText("今天有"+ClassToday.size()+"节课");
    }
    public void Update_ViewClassList(){ //更新课程列表View
        class_list.clear();
        for(int i=0;i<AllClass.size();i++){
            Add_Class_list(AllClass.get(i));
        }
    }
    public void SetUpNum(int position,int num){
        chat_list.get(position).put("up_num",num+"");
        chatAdapter.notifyDataSetChanged();
    }
    public void SetDownNum(int position,int num){
        chat_list.get(position).put("down_num",num+"");
        chatAdapter.notifyDataSetChanged();
    }
    public void SetReportNum(int position,int num){
        chat_list.get(position).put("report",num+"");
        chatAdapter.notifyDataSetChanged();
    }
    public void Init_UserInfo(){
        if(!logined){
            TextView textView=(TextView)findViewById(R.id.user_name);
            textView.setText("请登录");
        }
        else{
            if(isTeacher){
                String name=teacher.getNickname();
                final String src=teacher.getAvatar();
                TextView textView=(TextView)findViewById(R.id.user_name);
                textView.setText(name);
                user_avatar=(ImageView)findViewById(R.id.avatar);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Bitmap bmp = getURLimage(src);
                        Message msg = new Message();
                        msg.what = 0;
                        msg.obj = bmp;
                        handle.sendMessage(msg);
                    }
                }).start();
            }
            else {
                String name=student.getNickname();
                final String src=student.getAvatar();
                TextView textView=(TextView)findViewById(R.id.user_name);
                textView.setText(name);
                user_avatar=(ImageView)findViewById(R.id.avatar);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Bitmap bmp = getURLimage(src);
                        Message msg = new Message();
                        msg.what = 0;
                        msg.obj = bmp;
                        handle.sendMessage(msg);
                    }
                }).start();
            }
        }
    }
    private Handler handle = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Bitmap tmp=(Bitmap) msg.obj;
                    //imageView.setImageBitmap(bmp);
                    user_avatar.setImageBitmap(tmp);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) { // 如果返回码是可以用的
            if(requestCode==ResultLogin){
                GetLesson();
                Init_UserInfo();
            }
            else if(requestCode==ResultEdit||requestCode==ResultLessonList){
                GetLesson();
                Init_UserInfo();
            }
        }
    }
    private void broad_init(){
        Intent iBroad=new Intent(STATICACTION);
        Bundle bundle = new Bundle();
        bundle.putInt("hw", CntHW);
        bundle.putInt("not",CntNot);
        iBroad.putExtra("mainActivity",bundle);
        sendBroadcast(iBroad);
    }

    public static class StaticReceiver extends BroadcastReceiver {
        public StaticReceiver(){}
        @Override		//静态广播接收器执行的方法
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(STATICACTION)) {
                Bundle extras = intent.getBundleExtra("mainActivity");
                int hw=extras.getInt("hw");
                int not=extras.getInt("not");
                NotificationManager notifyManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

                //实例化NotificationCompat.Builde并设置相关属性
                Notification.InboxStyle inboxStyle = new Notification.InboxStyle()
                        .setBigContentTitle("今日通知")
                        .addLine("今天有"+hw+"个ddl")
                        .addLine("今天有"+not+"个新的课程通知");

                Notification.Builder builder = new Notification.Builder(context)
                        //设置小图标
                        //设置通知标题
                        .setSmallIcon(R.mipmap.ic_launcher)

                        .setTicker("TickerText:" + "您有新短消息，请注意查收！")
                        .setContentTitle("今日通知")
                        //设置通知内容
                        .setContentText("今日通知请查收")
                        .setStyle(inboxStyle)
                        .setAutoCancel(true)
                        .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                        .setWhen(System.currentTimeMillis())
                        .setPriority(Notification.PRIORITY_HIGH);
                //.setStyle(new Notification.BigPictureStyle().bigLargeIcon(bm).setBigContentTitle(name+"仅售"+price+"!").setSummaryText(data.getCart_list_index(id).getGoodTypes() + " " + data.getCart_list_index(id).getGoodInfo()));

                notifyManager.notify(0, builder.build());
            }
        }
    }
}