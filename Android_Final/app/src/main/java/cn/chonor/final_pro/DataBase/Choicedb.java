package cn.chonor.final_pro.DataBase;

import java.sql.ResultSet;

import cn.chonor.final_pro.model.Course;

/**
 * Created by Chonor on 2017/12/30.
 */

public class Choicedb {
    private MySqlHelper db;

    public   Choicedb() {
        db = new MySqlHelper();
    }

    public ResultSet querySid(int sid) throws Exception {
        String sql = "select * from CHOICES "+
                " where sid ='"+sid+"'";
        ResultSet rs =db.select(sql);
        return rs;
    }
    public Integer queryscore(int sid,int cid) throws Exception {
        String sql = "select * from CHOICES "+
                " where sid ='"+sid+"' AND cid='"+cid+"'";
        ResultSet rs =db.select(sql);
        int score=-1;
        if(rs.next()){
            score=rs.getInt("score");
        }
        rs.close();
        db.Close();
        return score;
    }
    public boolean querySelect(int cid,int sid)throws Exception{
        String sql = "select * from CHOICES "+
                " where sid ='"+sid+"' AND cid='"+cid+"'";
        ResultSet rs =db.select(sql);
        boolean flag=false;
        if(rs.next())flag=true;
        rs.close();
        close();
        return flag;
    }
    public void insert(int cid,int sid)throws Exception{
        String sql="insert into " +
                " CHOICES(cid,sid) " +
                " values('"+ cid+"','"+ sid+"')";
        db.insert(sql);
    }

    public void update(int cid,int sid,int score)throws Exception{
        String sql="update CHOICES set " +
                " score='"+score +"' " +
                " where cid='"+ cid +"'AND sid='"+sid+"'";
        db.update(sql);
    }

    public void delete(int cid,int sid) throws Exception{
        String sql="delete from CHOICES "+
                " where cid='"+ cid +"'AND sid='"+sid+"'";
        db.delete(sql);
    }

    public void close()throws Exception  {
        db.Close();
    }
}
