package cn.chonor.final_pro.DataBase;

import java.sql.ResultSet;
import java.util.ArrayList;

import cn.chonor.final_pro.model.Course;
import cn.chonor.final_pro.model.Homework;
import cn.chonor.final_pro.model.Notice;

/**
 * Created by Chonor on 2017/12/30.
 */

public class Noticedb {
    private MySqlHelper db;

    public  Noticedb() {
        db = new MySqlHelper();
    }

    public ResultSet querycid(int cid) throws Exception {
        String sql = "select * from NOTICES "+
                " where cid ='"+cid+"'";
        ResultSet rs =db.select(sql);
        return rs;
    }

    public Notice selectBynid(int nid)throws Exception{
        Notice results=null;
        String sql = "SELECT NOTICES.nid as nid," +
                " NOTICES.cid as cid , " +
                " NOTICES.title as title," +
                " NOTICES.info as info," +
                " NOTICES.starttime as starttime," +
                " NOTICES.endtime as endtime," +
                " COURSES.cname as cname" +
                " FROM NOTICES,COURSES " +
                " WHERE NOTICES.cid=COURSES.cid AND NOTICES.nid ='"+nid+"'";
        ResultSet rs =db.select(sql);
        while(rs.next()){
            Notice notice=new Notice();
            notice.setCid(rs.getInt("cid"));
            notice.setNid(rs.getInt("nid"));
            notice.setTitle(rs.getString("title"));
            notice.setInfo(rs.getString("info"));
            notice.setStarttime(rs.getString("starttime"));
            notice.setEndtime(rs.getString("endtime"));
            notice.setCname(rs.getString("cname"));
            results=notice;
        }
        rs.close();
        db.Close();
        return  results;
    }
    public ArrayList<Notice> selectBytid(int tid)throws Exception{
        ArrayList<Notice>results =new ArrayList<>();
        String sql="SELECT NOTICES.nid as nid," +
                " NOTICES.cid as cid," +
                " NOTICES.title as title," +
                " NOTICES.info as info," +
                " NOTICES.starttime as starttime," +
                " NOTICES.endtime as endtime," +
                " COURSES.tid as tid," +
                " COURSES.cname as cname " +
                " FROM NOTICES,COURSES" +
                " WHERE NOTICES.cid=COURSES.cid AND " +
                " COURSES.tid='" +String.valueOf(tid)+"'"+
                " ORDER BY nid DESC";
        ResultSet rs = db.select(sql);
        while(rs.next()){
            Notice notice=new Notice();
            notice.setCname(rs.getString("cname"));
            notice.setCid(rs.getInt("cid"));
            notice.setNid(rs.getInt("nid"));
            notice.setTitle(rs.getString("title"));
            notice.setInfo(rs.getString("info"));
            notice.setStarttime(rs.getString("starttime"));
            notice.setEndtime(rs.getString("endtime"));
            results.add(notice);
        }
        rs.close();
        db.Close();
        return  results;
    }
    public ArrayList<Notice> selectBysid(int sid)throws Exception{
        ArrayList<Notice>results =new ArrayList<>();
        String sql="SELECT NOTICES.nid as nid," +
                " NOTICES.cid as cid," +
                " NOTICES.title as title," +
                " NOTICES.info as info," +
                " NOTICES.starttime as starttime," +
                " NOTICES.endtime as endtime," +
                " CHOICES.sid as sid, " +
                " COURSES.cname as cname" +
                " FROM NOTICES,CHOICES,COURSES" +
                " WHERE NOTICES.cid=CHOICES.cid AND NOTICES.cid=COURSES.cid AND " +
                " CHOICES.sid='" +String.valueOf(sid)+"'"+
                " ORDER BY nid DESC";
        ResultSet rs = db.select(sql);
        while(rs.next()){
            Notice notice=new Notice();
            notice.setCid(rs.getInt("cid"));
            notice.setNid(rs.getInt("nid"));
            notice.setTitle(rs.getString("title"));
            notice.setInfo(rs.getString("info"));
            notice.setStarttime(rs.getString("starttime"));
            notice.setEndtime(rs.getString("endtime"));
            notice.setCname(rs.getString("cname"));
            results.add(notice);
        }
        rs.close();
        db.Close();
        return  results;
    }
    public ArrayList<Notice> selectBycid(int cid)throws Exception{
        ArrayList<Notice>results=new ArrayList<>();
        Coursedb coursedb=new Coursedb();
        Course course= coursedb.selectBycid(cid);
        coursedb.close();

        ResultSet rs=querycid(cid);
        while(rs.next()){
            Notice notice=new Notice();
            notice.setCid(rs.getInt("cid"));
            notice.setNid(rs.getInt("nid"));
            notice.setTitle(rs.getString("title"));
            notice.setInfo(rs.getString("info"));
            notice.setStarttime(rs.getString("starttime"));
            notice.setEndtime(rs.getString("endtime"));
            notice.setCname(course.getName());
            results.add(notice);
        }
        rs.close();
        db.Close();
        return  results;
    }
    public Integer CountToday(int sid,String date)throws Exception {
        int cnt=0;
        String sql="SELECT COUNT(*) AS cnt " +
                "FROM NOTICES,CHOICES,STUDENTS " +
                "WHERE STUDENTS.sid = CHOICES.sid AND " +
                "CHOICES.cid=NOTICES.cid AND " +
                "STUDENTS.sid="+sid+" AND " +
                "NOTICES.starttime='"+date+"'";
        ResultSet rs = db.select(sql);
        if(rs.next()){
            cnt=rs.getInt("cnt");
        }
        rs.close();
        close();
        return cnt;
    }

    public void insert(Notice notice)throws Exception{

        String sql="insert into " +
                " NOTICES(cid,title,info,starttime,endtime) " +
                " values('"+ notice.getCid()+"','"+ notice.getTitle()+"','"+notice.getInfo()+"','"+notice.getStarttime()+"','"+notice.getEndtime()+"')";
        db.insert(sql);

    }



    public void update(Notice notice)throws Exception{
        String sql="update NOTICES set " +
                " info='"+notice.getInfo() +"' " +
                " starttime='"+notice.getStarttime() +"' " +
                " endtime='"+notice.getEndtime() +"' " +
                " where nid='"+notice.getNid();
        db.update(sql);
    }

    public void delete(Notice notice) throws Exception{
        String sql="delete from NOTICES "+
                " where nid='"+ notice.getNid() +"'";
        db.delete(sql);
    }

    public void close()throws Exception  {
        db.Close();
    }
}
