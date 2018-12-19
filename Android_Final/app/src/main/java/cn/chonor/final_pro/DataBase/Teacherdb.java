package cn.chonor.final_pro.DataBase;

import java.sql.ResultSet;


import cn.chonor.final_pro.model.Teacher;

/**
 * Created by Chonor on 2017/12/29.
 */

public class Teacherdb {
    private MySqlHelper db;

    public Teacherdb() {
        db = new MySqlHelper();
    }

    public ResultSet queryById(int tid)throws Exception {
        String sql = "select * from TEACHERS where tid=" +  tid;
        ResultSet rs =db.select(sql);
        return rs;
    }

    public ResultSet queryByNum(String Num)throws Exception {
        String sql = "select * from TEACHERS where tnum='" +  Num + "'";
        ResultSet rs =db.select(sql);
        return rs;
    }
    public Teacher selectByNum(String Num)throws Exception {
        Teacher teacher=null;
        ResultSet rs =queryByNum(Num);
        if(rs.next()){
            teacher=new Teacher();
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
        }
        rs.close();
        db.Close();
        return teacher;
    }

    public ResultSet queryAll(int st, int cnt) throws Exception {
        String sql = "select * from TEACHERS ";
        sql += "limit " +  st +", " + cnt;
        ResultSet rs =db.select(sql);
        return rs;
    }


    public void insert(Teacher teacher)throws Exception{
        String sql="insert into " +
                " TEACHERS(tnum,passwd,tname,sex,nickname,avatar,college,introduction,email,phone,office,position) " +
                " values('"+ teacher.getNum()+"','"+
                teacher.getPasswd()+"','"+
                teacher.getName()+"','"+
                teacher.getSex() +"','"+
                teacher.getNickname()+"','"+
                teacher.getAvatar()+"','"+
                teacher.getCollege()+"','"+
                teacher.getInfo()+"','"+
                teacher.getEmail()+"','"+
                teacher.getPhone()+"','"+
                teacher.getOffice()+"','"+
                teacher.getPosition()+"')";
        db.insert(sql);
    }

    public void update(Teacher teacher)throws Exception{
        String sql="update TEACHERS set nickname='"+ teacher.getNickname()+ "'," +
                " sex='"+ teacher.getSex()+"', " +
                " college='"+ teacher.getCollege()+"', " +
                " introduction='"+ teacher.getInfo()+"', " +
                " email='"+ teacher.getEmail()+"', " +
                " phone='"+ teacher.getPhone()+"', " +
                " office='"+ teacher.getOffice()+"', " +
                " position='"+ teacher.getPosition()+"', " +
                " avatar='"+teacher.getAvatar()+"' " +
                " where tnum='"+ teacher.getNum()+"'";
        db.update(sql);
    }

    public void close()throws Exception  {
        db.Close();
    }
}
