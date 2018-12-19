package cn.chonor.final_pro.model;

/**
 * Created by Chonor on 2017/12/30.
 */

public class Notice {
    private Integer nid;
    private Integer cid;
    private String cname;
    private String title;
    private String info;
    private String starttime;
    private String endtime;

    public Notice(){
        nid=-1;
        cid=-1;
        info="无";
        starttime="无";
        endtime="无";
        title="通知标题";
        cname="";
    }

    public Notice(String cname,String title,String info,String starttime,String endtime){
        this.cname=cname;
        this.title=title;
        this.info=info;
        this.starttime=starttime;
        this.endtime=endtime;
    }
    public Notice(Integer nid,Integer cid,String cname,String title,String info,String starttime,String endtime){
        this.title=title;
        this.cname=cname;
        this.nid=nid;
        this.cid=cid;
        this.info=info;
        this.starttime=starttime;
        this.endtime=endtime;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public void setNid(Integer nid) {
        this.nid = nid;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getCname() {
        return cname;
    }

    public Integer getCid() {
        return cid;
    }

    public Integer getNid() {
        return nid;
    }

    public String getEndtime() {
        return endtime;
    }

    public String getInfo() {
        return info;
    }

    public String getStarttime() {
        return starttime;
    }

    public String getTitle() {
        return title;
    }
}
