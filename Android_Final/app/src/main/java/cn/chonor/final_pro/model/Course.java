package cn.chonor.final_pro.model;

/**
 * Created by Chonor on 2017/12/26.
 */

public class Course {
    private String name; //课程名
    private String week;//星期 1-7 使用时请转中文
    private String time;// 1-5  节数
    private String pos;//地点
    private String college;//学院
    private String info;//课程简介
    private String hour;//学时
    private String credit;//学分
    private String tname;//教师名字
    private Integer cid;//课程id
    private Integer tid;//教师id
    private Integer choose;//是否选课

    public Course(){
        name="无";
        week="0";
        time="0";
        pos="未知";
        college="未知";
        info="暂无简介";
        cid=-1;
        tid=-1;
        hour="0";
        credit="0";
        choose=0;
        tname="教师";
    }
    public Course(String name, String week, String time, String pos, String college, String info, String hour, String credit,String tname,Integer cid,Integer tid, Integer choose){
        this.name=name;
        this.week=week;
        this.time=time;
        this.pos=pos;
        this.college=college;
        this.info=info;
        this.cid=cid;
        this.tid=tid;
        this.choose=choose;
        this.hour=hour;
        this.credit=credit;
        this.tname=tname;
    }

    public void  setName(String name){this.name=name;}
    public void setWeek(String week){this.week=week;}
    public void setTime(String time){this.time=time;}
    public void setPos(String pos){this.pos=pos;}
    public void setCollege(String college){this.college=college;}
    public void setInfo(String info){this.info=info;}
    public void setCid(Integer id){this.cid=id;}
    public void setTid(Integer id){this.tid=id;}
    public void setHour(String hour){this.hour=hour;}
    public void setCredit(String credit){this.credit=credit;}
    public void setChoose(Integer choose){this.choose=choose;}
    public void setTname(String tname){this.tname=tname;}
    public String getName(){return name;}
    public String getTime(){return time;}
    public String getWeek(){return week;}
    public String getPos(){return pos;}
    public String getCollege(){return college;}
    public String getInfo(){return info;}
    public Integer getCid(){return cid;}
    public Integer getTid(){return tid;}
    public Integer getChoose(){return choose;}
    public String getHour(){return hour;}
    public String getCredit(){return credit;}
    public String getTname() {return tname;}
}
