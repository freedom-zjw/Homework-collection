package cn.chonor.final_pro.DataBase;

import java.sql.ResultSet;
import java.util.ArrayList;

import cn.chonor.final_pro.model.Course;
import cn.chonor.final_pro.model.Teacher;

/**
 * Created by Chonor on 2017/12/30.
 */

public class Coursedb {
    private MySqlHelper db;

    public  Coursedb() {
        db = new MySqlHelper();
    }

    public ResultSet queryByCid(int cid)throws Exception {
        String sql = "select * from COURSES where cid=" +  cid;
        ResultSet rs =db.select(sql);
        return rs;
    }
    public ResultSet queryByTid(int tid)throws Exception {
        String sql = "select * from COURSES where tid=" +  tid;
        ResultSet rs =db.select(sql);
        return rs;
    }

    public ResultSet queryAll(int st, int cnt) throws Exception {
        String sql = "select * from COURSES ";
        sql += "limit " +  st +", " + cnt;
        ResultSet rs =db.select(sql);
        return rs;
    }
    public Course selectBycid(int cid)throws Exception {
        Course results=null;
        String sql="SELECT COURSES.cid as cid," +
                " COURSES.tid as tid," +
                " COURSES.cname as cname," +
                " COURSES.week as week," +
                " COURSES.timer as timer," +
                " COURSES.hour as hour," +
                " COURSES.credit as credit," +
                " COURSES.position as pos," +
                " COURSES.college as college," +
                " COURSES.introduction as info," +
                " TEACHERS.tname as tname " +
                " FROM COURSES,TEACHERS" +
                " WHERE COURSES.tid=TEACHERS.tid AND COURSES.cid='"+String.valueOf(cid) +"' "+
                " ORDER BY COURSES.cid DESC";
        ResultSet rs =db.select(sql);
        while (rs.next()){
            Course course=new Course();
            course.setCid(rs.getInt("cid"));
            course.setTid(rs.getInt("tid"));
            course.setName(rs.getString("cname"));
            course.setWeek(rs.getString("week"));
            course.setTime(rs.getString("timer"));
            course.setHour(rs.getString("hour"));
            course.setCredit(rs.getString("credit"));
            course.setPos(rs.getString("pos"));
            course.setCollege(rs.getString("college"));
            course.setInfo(rs.getString("info"));
            course.setTname(rs.getString("tname"));
            course.setChoose(1);
            results=course;
        }
        rs.close();
        db.Close();
        return results;
    }
    public ArrayList<Course> selectBytid(int tid)throws Exception {
        ArrayList<Course>results=new ArrayList<>();
        String sql="SELECT COURSES.cid as cid," +
                " COURSES.tid as tid," +
                " COURSES.cname as cname," +
                " COURSES.week as week," +
                " COURSES.timer as timer," +
                " COURSES.hour as hour," +
                " COURSES.credit as credit," +
                " COURSES.position as pos," +
                " COURSES.college as college," +
                " COURSES.introduction as info," +
                " TEACHERS.tname as tname " +
                " FROM COURSES,TEACHERS" +
                " WHERE COURSES.tid=TEACHERS.tid AND TEACHERS.tid='"+String.valueOf(tid) +"' "+
                " ORDER BY COURSES.cid DESC";
        ResultSet rs =db.select(sql);
        while (rs.next()){
            Course course=new Course();
            course.setCid(rs.getInt("cid"));
            course.setTid(rs.getInt("tid"));
            course.setName(rs.getString("cname"));
            course.setWeek(rs.getString("week"));
            course.setTime(rs.getString("timer"));
            course.setHour(rs.getString("hour"));
            course.setCredit(rs.getString("credit"));
            course.setPos(rs.getString("pos"));
            course.setCollege(rs.getString("college"));
            course.setInfo(rs.getString("info"));
            course.setTname(rs.getString("tname"));
            course.setChoose(1);
            results.add(course);
        }
        rs.close();
        db.Close();
        return results;
    }

    public ArrayList<Course> selectBysid(int sid)throws Exception {
        ArrayList<Course>results=new ArrayList<>();
        String sql="SELECT " +
                " COURSES.cid as cid," +
                " COURSES.tid as tid, " +
                " COURSES.cname as cname," +
                " COURSES.week as week," +
                " COURSES.timer as timer," +
                " COURSES.hour as hour," +
                " COURSES.credit AS credit," +
                " COURSES.position as pos," +
                " COURSES.college as college," +
                " COURSES.introduction as info," +
                " TEACHERS.tname as tname " +
                " FROM COURSES,TEACHERS,CHOICES" +
                " WHERE COURSES.tid=TEACHERS.tid AND " +
                " COURSES.cid=CHOICES.cid AND " +
                " CHOICES.sid='"+String.valueOf(sid)+"'"+
                " ORDER BY COURSES.cid DESC";
        ResultSet rs =db.select(sql);
        while (rs.next()){
            Course course=new Course();
            course.setCid(rs.getInt("cid"));
            course.setTid(rs.getInt("tid"));
            course.setName(rs.getString("cname"));
            course.setWeek(rs.getString("week"));
            course.setTime(rs.getString("timer"));
            course.setHour(rs.getString("hour"));
            course.setCredit(rs.getString("credit"));
            course.setPos(rs.getString("pos"));
            course.setCollege(rs.getString("college"));
            course.setInfo(rs.getString("info"));
            course.setTname(rs.getString("tname"));
            course.setChoose(1);
            results.add(course);
        }
        rs.close();
        db.Close();
        return results;
    }
    public ArrayList<Course> selectAll(int st, int cnt,int sid,int tid)throws Exception {
        ArrayList<Course>results=new ArrayList<>();
        String sql="SELECT " +
                " COURSES.cid as cid," +
                " COURSES.tid as tid, " +
                " COURSES.cname as cname," +
                " COURSES.week as week," +
                " COURSES.timer as timer," +
                " COURSES.hour as hour," +
                " COURSES.credit AS credit," +
                " COURSES.position as pos," +
                " COURSES.college as college," +
                " COURSES.introduction as info," +
                " TEACHERS.tname as tname " +
                " FROM COURSES,TEACHERS" +
                " WHERE COURSES.tid=TEACHERS.tid " +
                " ORDER BY COURSES.cid DESC " +
                " limit " +  st +" , " + cnt;
        ResultSet rs =db.select(sql);
        while (rs.next()){
            Course course=new Course();
            course.setCid(rs.getInt("cid"));
            course.setTid(rs.getInt("tid"));
            course.setName(rs.getString("cname"));
            course.setWeek(rs.getString("week"));
            course.setTime(rs.getString("timer"));
            course.setHour(rs.getString("hour"));
            course.setCredit(rs.getString("credit"));
            course.setPos(rs.getString("pos"));
            course.setCollege(rs.getString("college"));
            course.setInfo(rs.getString("info"));
            course.setTname(rs.getString("tname"));
            if(sid!=-1){
                course.setChoose(0);
            }
            else if(tid!=-1){
                if(course.getTid()==tid)course.setChoose(1);
                else course.setChoose(0);
            }
            else course.setChoose(0);
            results.add(course);
        }
        rs.close();
        db.Close();
        return results;
    }
    public ArrayList<Course> selectBySearch(String info)throws Exception {
        ArrayList<Course>results=new ArrayList<>();
        String sql="SELECT " +
                " COURSES.cid as cid," +
                " COURSES.tid as tid, " +
                " COURSES.cname as cname," +
                " COURSES.week as week," +
                " COURSES.timer as timer," +
                " COURSES.hour as hour," +
                " COURSES.credit AS credit," +
                " COURSES.position as pos," +
                " COURSES.college as college," +
                " COURSES.introduction as info," +
                " TEACHERS.tname as tname " +
                " FROM COURSES,TEACHERS" +
                " WHERE COURSES.tid=TEACHERS.tid AND " +
                " (COURSES.cname LIKE '%"+info+"%' OR " +
                " COURSES.college LIKE '%"+info+"%' OR " +
                " TEACHERS.tname LIKE '%"+info+"%') " +
                " ORDER BY COURSES.cid DESC ";
        ResultSet rs =db.select(sql);
        while (rs.next()){
            Course course=new Course();
            course.setCid(rs.getInt("cid"));
            course.setTid(rs.getInt("tid"));
            course.setName(rs.getString("cname"));
            course.setWeek(rs.getString("week"));
            course.setTime(rs.getString("timer"));
            course.setHour(rs.getString("hour"));
            course.setCredit(rs.getString("credit"));
            course.setPos(rs.getString("pos"));
            course.setCollege(rs.getString("college"));
            course.setInfo(rs.getString("info"));
            course.setTname(rs.getString("tname"));
            course.setChoose(0);
            results.add(course);
        }
        rs.close();
        db.Close();
        return results;
    }

    public void insert(Course course)throws Exception{
        String sql="insert into " +
                " COURSES(tid,cname,week,timer,hour,credit,position,college,introduction) " +
                " values('"+ course.getTid()+"','"+
                course.getName()+"','"+
                course.getWeek()+"','"+
                course.getTime() +"','"+
                course.getHour()+"','"+
                course.getCredit()+"','"+
                course.getPos()+"','"+
                course.getCollege()+"','"+
                course.getInfo()+"')";
        db.insert(sql);
    }

    public void update(Course course)throws Exception{
        String sql="update COURSES set " +
                " tid='"+ course.getTid()+ "'," +
                " cname='"+ course.getName()+"', " +
                " week='"+ course.getWeek()+"', " +
                " timer='"+ course.getTime()+"' " +
                " hour='"+ course.getHour()+"' " +
                " credit='"+ course.getCredit()+"' " +
                " college='"+ course.getCollege()+"' " +
                " introduction='"+ course.getInfo()+"' " +
                "where cid='"+ course.getCid()+"'";
        db.update(sql);
    }

    public void close()throws Exception  {
        db.Close();
    }
}
