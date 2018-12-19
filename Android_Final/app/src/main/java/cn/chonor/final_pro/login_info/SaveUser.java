package cn.chonor.final_pro.login_info;

import android.content.Context;
import android.content.SharedPreferences;

import cn.chonor.final_pro.model.Student;
import cn.chonor.final_pro.model.Teacher;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Chonor on 2017/12/26.
 */

public class SaveUser {
    public static String getUserId(Context context){
        String id;
        SharedPreferences preferences=context.getSharedPreferences("UserId",MODE_PRIVATE);
        id=preferences.getString("UserId",null);
        if(id!=null&&!id.equals(""))return DES.decryptDES(id);
        else return "";
    }
    public static void setUserId(Context context,String id){
        SharedPreferences preferences=context.getSharedPreferences("UserId", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("UserId", DES.encryptDES(id));
        editor.commit();
    }
    public static void setStudentInfo(Context context, Student student){
        SharedPreferences preferences=context.getSharedPreferences("UserInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("id",DES.encryptDES(String.valueOf(student.getId())));
        editor.putString("num", DES.encryptDES(student.getNum()));
        editor.putString("passwd", DES.encryptDES(student.getPasswd()));
        editor.putString("avatar", student.getAvatar());
        editor.putString("realname", student.getName());
        editor.putString("nickname", student.getNickname());
        editor.putString("college", student.getCollege());
        editor.putString("info", student.getInfo());
        editor.putString("sex", student.getSex());
        editor.commit();
        setUserId(context,student.getNum());
    }

    public static Student getStudentInfo(Context context){
        String num=null;
        String id,passwd,avatar,realname,nickname,college,info,sex;
        SharedPreferences preferences=context.getSharedPreferences("UserInfo",MODE_PRIVATE);
        num=preferences.getString("num",null);
        if(num!=null&&!num.equals("")){
            num=DES.decryptDES(num);
            id=preferences.getString("id",null);
            id=DES.decryptDES(id);
            passwd=preferences.getString("passwd",null);
            passwd=DES.decryptDES(passwd);
            avatar=preferences.getString("avatar",null);
            realname=preferences.getString("realname",null);
            nickname=preferences.getString("nickname",null);
            college=preferences.getString("college",null);
            info=preferences.getString("info",null);
            sex=preferences.getString("sex",null);
            Student student =new Student(realname,nickname,passwd,avatar,sex,college,info,num);
            student.setId(Integer.valueOf(id));
            return student;
        }
        else return null;
    }
    public static void setTeacherInfo(Context context, Teacher teacher){
        SharedPreferences preferences=context.getSharedPreferences("UserInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("id",DES.encryptDES(String.valueOf(teacher.getId())));
        editor.putString("num", DES.encryptDES(teacher.getNum()));
        editor.putString("passwd", DES.encryptDES(teacher.getPasswd()));
        editor.putString("avatar", teacher.getAvatar());
        editor.putString("realname", teacher.getName());
        editor.putString("nickname", teacher.getNickname());
        editor.putString("college", teacher.getCollege());
        editor.putString("info", teacher.getInfo());
        editor.putString("sex", teacher.getSex());
        editor.putString("email",teacher.getEmail());
        editor.putString("phone",teacher.getPhone());
        editor.putString("office",teacher.getOffice());
        editor.putString("pos",teacher.getPosition());
        editor.commit();
        setUserId(context,teacher.getNum());
    }

    public static Teacher getTeacherInfo(Context context){
        String num=null;
        String id,passwd,avatar,realname,nickname,college,info,sex,email,phone,office,pos;
        SharedPreferences preferences=context.getSharedPreferences("UserInfo",MODE_PRIVATE);
        num=preferences.getString("num",null);
        if(num!=null&&!num.equals("")){
            num=DES.decryptDES(num);
            id=preferences.getString("id",null);
            id=DES.decryptDES(id);
            passwd=preferences.getString("passwd",null);
            passwd=DES.decryptDES(passwd);
            avatar=preferences.getString("avatar",null);
            realname=preferences.getString("realname",null);
            nickname=preferences.getString("nickname",null);
            college=preferences.getString("college",null);
            info=preferences.getString("info",null);
            sex=preferences.getString("sex",null);
            email=preferences.getString("email",null);
            phone=preferences.getString("phone",null);
            office=preferences.getString("office",null);
            pos=preferences.getString("pos",null);
            Teacher teacher =new Teacher(realname,nickname,passwd,avatar,sex,college,info,num,email,phone,office,pos);
            teacher.setId(Integer.valueOf(id));
            return teacher;
        }
        else return null;
    }

    public static void clearInfo(Context context){
        SharedPreferences preferences=context.getSharedPreferences("UserInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }
}
