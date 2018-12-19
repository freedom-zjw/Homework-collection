package cn.chonor.final_pro.model;

/**
 * Created by Chonor on 2017/12/30.
 */

public class Comment {
    private Integer cid;//评论id 你不用管 数据库自动的
    private String num;//评论者的学号/教工号
    private String info;//具体内容
    private String time;//时间  这是用排序的， 使用年/月/日 GetTime里
    private String name;//评论者名字
    private String college;//学院
    private String pos;//这个你可以不用填
    private Integer up;//赞
    private Integer down;//踩
    private Integer report;//举报
    private String src;//图片
    private String avatar;//头像
    public Comment(){
        cid=-1;
        num="00000000";
        info="没有填写";
        time="未知";
        pos="无法获取位置";
        up=0;
        down=0;
        report=0;
        src="https://chonor.cn/Android/Avatar/defaultPhoto.png";
        avatar="https://chonor.cn/Android/Avatar/defaultPhoto.png";
        name="无";
        college="无";
    }
    public Comment(String num,String info,String time,String pos,String src){
        this.num=num;
        this.info=info;
        this.time=time;
        this.pos=pos;
        this.src=src;
        up=0;
        down=0;
        report=0;
    }
    public Comment(Integer cid,String num,String info,String time,String pos,String src,String name,String college,String avatar,Integer up,Integer down,Integer report){
        this.cid=cid;
        this.num=num;
        this.info=info;
        this.time=time;
        this.pos=pos;
        this.src=src;
        this.up=up;
        this.down=down;
        this.report=report;
        this.name=name;
        this.college=college;
        this.avatar=avatar;
    }

    public void setCid(Integer cid){this.cid=cid;}
    public void setNum(String num){this.num=num;}
    public void setInfo(String info){this.info=info;}
    public void setTime(String time){this.time=time;}
    public void setPos(String pos){this.pos=pos;}
    public void setUp(Integer up){this.up=up;}
    public void setDown(Integer down){this.down=down;}
    public void setReport(Integer report){this.report=report;}
    public void setSrc(String src){this.src=src;}
    public void setName(String name){this.name=name;}
    public void setCollege(String college){this.college=college;}
    public void setAvatar(String avatar){this.avatar=avatar;}
    public Integer getCid(){return cid;}
    public Integer getUp(){return up;}
    public Integer getDown(){return down;}
    public Integer getReport(){return report;}
    public String getNum(){return num;}
    public String getInfo(){return info;}
    public String getTime(){return time;}
    public String getPos(){return pos;}
    public String getSrc(){return src;}
    public String getName(){return name;}
    public String getCollege(){return college;}
    public String getAvatar(){return avatar;}
}
