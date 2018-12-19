package cn.chonor.final_pro.DataBase;

import android.widget.EditText;

import java.sql.ResultSet;
import java.util.ArrayList;

import cn.chonor.final_pro.model.Student;

/**
 * Created by Chonor on 2017/12/26.
 */

public class Studentdb {
    private MySqlHelper db;

    public Studentdb() {
        db = new MySqlHelper();
    }

    public ResultSet queryById(int sid)throws Exception {
        String sql = "select * from STUDENTS where sid=" +  sid;
        ResultSet rs =db.select(sql);
        return rs;
    }

    public ResultSet queryByNum(String Num)throws Exception {
        String sql = "select * from STUDENTS where snum='" +  Num + "'";
        ResultSet rs =db.select(sql);
        return rs;
    }
    public Student selectByNum(String Num)throws Exception{
        Student student=null;
        ResultSet rs =queryByNum(Num);
        if(rs.next()){
            student=new Student();
            student.setId(rs.getInt("sid"));
            student.setNum(rs.getString("snum"));
            student.setSex(rs.getString("sex"));
            student.setNickname(rs.getString("nickname"));
            student.setPasswd(rs.getString("passwd"));
            student.setName(rs.getString("sname"));
            student.setAvatar(rs.getString("avatar"));
            student.setCollege(rs.getString("college"));
            student.setInfo(rs.getString("introduction"));
        }
        rs.close();
        db.Close();
        return  student;
    }
    public ResultSet queryAll(int st, int cnt) throws Exception {
        String sql = "select * from STUDENTS ";
        sql += "limit " +  st +", " + cnt;
        ResultSet rs =db.select(sql);
        return rs;
    }
    public ArrayList<Student> selectByCid(int cid)throws Exception{
        ArrayList<Student>resules=new ArrayList<>();
        String sql = "SELECT STUDENTS.sid as sid,STUDENTS.snum as snum,STUDENTS.sname as sname,CHOICES.score as score" +
                " FROM STUDENTS,CHOICES" +
                " WHERE STUDENTS.sid=CHOICES.sid AND CHOICES.cid='"+cid+"'";
        ResultSet rs =db.select(sql);
        while (rs.next()){
            Student student=new Student();
            student.setName(rs.getString("sname"));
            student.setId(rs.getInt("sid"));
            student.setNum(rs.getString("snum"));
            student.setScore(rs.getInt("score"));
            resules.add(student);
        }
        return resules;
    }

    public void insert(Student student)throws Exception{
        String sql="insert into " +
                " STUDENTS(snum,passwd,sname,sex,nickname,avatar,college,introduction) " +
                " values('"+ student.getNum()+"','"+ student.getPasswd()+"','"+ student.getName()+"','"+ student.getSex()
                +"','"+ student.getNickname()+"','"+ student.getAvatar()+"','"+ student.getCollege()+"','"+ student.getInfo()+"')";
        db.insert(sql);
    }

    public void update(Student student)throws Exception{
        String sql="update STUDENTS set nickname='"+ student.getNickname()+"', sex='"+ student.getSex()+"', " +
                "college='"+ student.getCollege()+"', introduction='"+ student.getInfo()+"', avatar=' "+student.getAvatar()+"' " +
                "where snum='"+ student.getNum()+"'";
        db.update(sql);
    }

    public void close()throws Exception  {
        db.Close();
    }
}
