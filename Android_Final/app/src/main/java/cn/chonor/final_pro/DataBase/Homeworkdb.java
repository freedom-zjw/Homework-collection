package cn.chonor.final_pro.DataBase;

import java.sql.ResultSet;
import java.util.ArrayList;

import cn.chonor.final_pro.model.Homework;

/**
 * Created by Chonor on 2017/12/30.
 */

public class Homeworkdb {
    private MySqlHelper db;

    public  Homeworkdb() {
        db = new MySqlHelper();
    }



    public ResultSet queryBycid(int cid) throws Exception {
        String sql = "select * from HOMEWORKS "+
                " where cid ='"+cid+"'" +
                " ORDER BY hwid DESC";
        ResultSet rs =db.select(sql);
        return rs;
    }
    public Homework selectByhwid(int hwid) throws Exception {
        Homework homework=null;
        String sql = "select * from HOMEWORKS "+
                " where hwid ='"+hwid+"'";
        ResultSet rs =db.select(sql);
        if(rs.next()){
            homework=new Homework();
            homework.setHwid(rs.getInt("hwid"));
            homework.setCid(rs.getInt("cid"));
            homework.setTitle(rs.getString("title"));
            homework.setInfo(rs.getString("info"));
            homework.setDdl(rs.getString("deadline"));
        }
        rs.close();
        close();
        return homework;
    }

    public ArrayList<Homework> selectBycid(int cid) throws Exception {
        ArrayList<Homework>results=new ArrayList<>();
        ResultSet rs=queryBycid(cid);
        while(rs.next()){
            Homework homework=new Homework();
            homework.setCid(rs.getInt("cid"));
            homework.setTitle(rs.getString("title"));
            homework.setHwid(rs.getInt("hwid"));
            homework.setInfo(rs.getString("info"));
            homework.setDdl(rs.getString("deadline"));
            results.add(homework);
        }
        rs.close();
        db.Close();
        return results;
    }
    public Integer CountToday(int sid,String date)throws Exception {
        int cnt=0;
        String sql="SELECT COUNT(*) AS cnt " +
                "FROM HOMEWORKS,CHOICES,STUDENTS " +
                "WHERE STUDENTS.sid = CHOICES.sid AND " +
                "CHOICES.cid=HOMEWORKS.cid AND " +
                "STUDENTS.sid="+sid+" AND " +
                "HOMEWORKS.deadline='"+date+"'";
        ResultSet rs = db.select(sql);
        if(rs.next()){
            cnt=rs.getInt("cnt");
        }
        rs.close();
        close();
        return cnt;
    }
    public void insert(Homework homework)throws Exception{
        String sql="insert into " +
                " HOMEWORKS(cid,title,info,deadline) " +
                " values('"+ homework.getCid()+"','"+homework.getTitle()+"','"+homework.getInfo()+"','"+homework.getDdl()+"')";
        db.insert(sql);
    }

    public void update(Homework homework)throws Exception{
        String sql="update HOMEWORKS set " +
                " title='"+homework.getTitle() +"' " +
                " info='"+homework.getInfo() +"' " +
                " deadline='"+homework.getDdl() +"' " +
                " where hwid='"+homework.getHwid();
        db.update(sql);
    }
    public void insertScore(int hwid,int sid,int score)throws Exception{
        String sql="insert ignore into " +
                " HOMEWORKSCORE(hsid,hwid,sid)" +
                " values('"+ (hwid+sid)+"','"+ hwid+"','"+ sid+"','"+score+"')";
        db.update(sql);
    }
    public void delete(Homework homework) throws Exception{
        String sql="delete from HOMEWORKS "+
                " where hwid='"+ homework.getHwid() +"'";
        db.delete(sql);
    }

    public void close()throws Exception  {
        db.Close();
    }
}
