package cn.chonor.final_pro.DataBase;

import java.sql.ResultSet;
import java.util.ArrayList;

import cn.chonor.final_pro.model.Comment;
import cn.chonor.final_pro.model.Student;
import cn.chonor.final_pro.model.Teacher;

/**
 * Created by Chonor on 2017/12/30.
 */

public class Commentdb {
    private MySqlHelper db;
    public Commentdb(){
        db = new MySqlHelper();
    }
    public ResultSet queryAll(int st, int cnt) throws Exception {
        String sql = "select * from COMMENTS ";
        sql += " ORDER BY comid DESC";
        sql += " limit " +  st +", " + cnt;
        ResultSet rs =db.select(sql);
        return rs;
    }

    public ArrayList<Comment> selectALL(int st, int cnt) throws Exception {
        ResultSet rs =queryAll(st,cnt);
        ArrayList<Comment>results=new ArrayList<>();
        while(rs.next()){
            Comment comment=new Comment();
            comment.setCid(rs.getInt("comid"));
            comment.setNum(rs.getString("num"));
            comment.setInfo(rs.getString("info"));
            comment.setTime(rs.getString("ctime"));
            comment.setPos(rs.getString("position"));
            comment.setUp(rs.getInt("up"));
            comment.setDown(rs.getInt("down"));
            comment.setReport(rs.getInt("report"));
            comment.setSrc(rs.getString("src"));
            if(comment.getNum().length()==6){
                Teacherdb teacherdb=new Teacherdb();
                Teacher teacher=null;
                teacher=teacherdb.selectByNum(comment.getNum());
                if(teacher!=null){
                    comment.setAvatar(teacher.getAvatar());
                    comment.setCollege(teacher.getCollege());
                    comment.setName(teacher.getNickname());
                }
            }else{
                Studentdb studentdb=new Studentdb();
                Student student=null;
                student=studentdb.selectByNum(comment.getNum());
                if(student!=null){
                    comment.setAvatar(student.getAvatar());
                    comment.setCollege(student.getCollege());
                    comment.setName(student.getNickname());
                }
            }
            results.add(comment);
        }
        rs.close();
        db.Close();
        return results;
    }

    public void insert(Comment comment)throws Exception{
        String sql="insert into " +
                " COMMENTS(num,info,ctime,position,src) " +
                " values('"+ comment.getNum()+"'" +
                ",'"+comment.getInfo()+"'" +
                ",'"+comment.getTime()+"'" +
                ",'"+comment.getPos()+"'" +
                ",'"+comment.getSrc() +"')";
        db.insert(sql);
    }

    public void updateup(int comid,int up)throws Exception{
        String sql="update COMMENTS set up='"+ up+
                "' where comid='"+comid+"'";
        db.update(sql);
    }

    public void updatedown(int comid,int down)throws Exception{
        String sql="update COMMENTS set down='"+ down+
                "' where comid='"+comid+"'";
        db.update(sql);
    }

    public void updatereport(int comid,int report)throws Exception{
        String sql="update COMMENTS set report='"+ report +
                "' where comid='"+comid+"'";
        db.update(sql);
    }

    public void delete(Comment comment) throws Exception{
        String sql="delete from COMMENTS "+
                " where comid='"+comment.getCid()+"'";
        db.delete(sql);
    }
    public void delete(int comid) throws Exception{
        String sql="delete from COMMENTS "+
                " where comid='"+comid+"'";
        db.delete(sql);
    }

    public boolean selectup(int comid ,String num)throws Exception{
        String sql = "SELECT * FROM COMMENTSUP WHERE comid='"+comid+"' AND num='"+num+"'";
        ResultSet rs =db.select(sql);
        boolean flag=false;
        if(rs.next())flag=true;
        rs.close();
        close();
        return flag;
    }
    public boolean selectdown(int comid ,String num)throws Exception{
        String sql = "SELECT * FROM COMMENTSDOWN WHERE comid='"+comid+"' AND num='"+num+"'";
        ResultSet rs =db.select(sql);
        boolean flag=false;
        if(rs.next())flag=true;
        rs.close();
        close();
        return flag;
    }
    public boolean selectreport(int comid ,String num)throws Exception{
        String sql = "SELECT * FROM COMMENTSREPORT WHERE comid='"+comid+"' AND num='"+num+"'";
        ResultSet rs =db.select(sql);
        boolean flag=false;
        if(rs.next())flag=true;
        rs.close();
        close();
        return flag;
    }
    public void insertup(int comid ,String num)throws Exception{
        String sql="insert into " +
                " COMMENTSUP(num,comid) " +
                " values('"+ num+"'" +
                ",'"+comid +"')";
        db.insert(sql);
    }
    public void insertdown(int comid ,String num)throws Exception{
        String sql="insert into " +
                " COMMENTSDOWN(num,comid) " +
                " values('"+ num+"'" +
                ",'"+comid +"')";
        db.insert(sql);
    }
    public void insertreport(int comid ,String num)throws Exception{
        String sql="insert into " +
                " COMMENTSREPORT(num,comid) " +
                " values('"+ num+"'" +
                ",'"+comid +"')";
        db.insert(sql);
    }

    public void close()throws Exception  {
        db.Close();
    }
}
