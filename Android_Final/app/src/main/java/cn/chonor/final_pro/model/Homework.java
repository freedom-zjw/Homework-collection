package cn.chonor.final_pro.model;

/**
 * Created by Chonor on 2017/12/30.
 */

public class Homework {
    private Integer cid;
    private Integer hwid;
    private String title;
    private String info;
    private String ddl;

    public Homework(){
        cid=-1;
        hwid=-1;
        info="没有作业";
        ddl="没ddl";
        title="";
    }
    public Homework(String title,String info,String ddl){
        this.title=title;
        this.info=info;
        this.ddl=ddl;
    }
    public Homework(Integer cid,String title,Integer hwid,String info,String ddl){
        this.title=title;
        this.cid=cid;
        this.hwid=hwid;
        this.info=info;
        this.ddl=ddl;
    }
    public void setCid(Integer cid){this.cid=cid;}
    public void setHwid(Integer hwid){this.hwid=hwid;}
    public void setInfo(String info){this.info=info;}
    public void setDdl(String ddl){this.ddl=ddl;}

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getCid(){return cid;}
    public Integer getHwid(){return hwid;}
    public String getInfo(){return info;}
    public String getDdl(){return ddl;}

    public String getTitle() {
        return title;
    }
}
