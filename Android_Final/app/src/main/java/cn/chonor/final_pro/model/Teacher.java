package cn.chonor.final_pro.model;

/**
 * Created by Chonor on 2017/12/29.
 */

public class Teacher {
    private String name;
    private String passwd;
    private String nickname;
    private String avatar;
    private String sex;
    private String college;
    private String info;
    private Integer id;
    private String num;
    private String email;
    private String phone;
    private String office;
    private String position;

    public Teacher(){
        name="无";
        nickname="无";
        passwd="123";
        avatar="https://chonor.cn/Android/Avatar/defaultPhoto.png";
        sex="unknow";
        college="还没有填写学院";
        info="还没有填写简介";
        id=-1;
        num="00000000";
        email="还没有填写邮箱";
        phone="还没有填写联系电话";
        office="还没有填写办公地点";
        position="讲师";
    }
    public Teacher(String name, String nickname, String passwd, String avatar, String sex, String college, String info, String num,String email,String phone,String office,String position){
        this.id=-1;
        this.name=name;
        this.nickname=nickname;
        this.passwd=passwd;
        this.avatar=avatar;
        this.sex=sex;
        this.college=college;
        this.info=info;
        this.num=num;
        this.phone=phone;
        this.email=email;
        this.office=office;
        this.position=position;
    }

    public void setName(String name){this.name=name;}
    public void setNickname(String nickname){this.nickname=nickname;}
    public void setAvatar(String avatar){this.avatar=avatar;}
    public void setSex(String sex){this.sex=sex;}
    public void setCollege(String college){this.college=college;}
    public void setInfo(String info){this.info=info;}
    public void setId(Integer id){this.id=id;}
    public void setNum(String num){this.num=num;}
    public void setPasswd(String passwd){this.passwd=passwd;}
    public void setEmail(String email){this.email=email;}
    public void setPhone(String phone){this.phone=phone;}
    public void setOffice (String office){this.office=office;}
    public void setPosition(String position){this.position=position;}
    public String getName(){return name;}
    public String getNickname(){return nickname;}
    public String getAvatar(){return avatar;}
    public String getSex(){return sex;}
    public String getCollege(){return college;}
    public String getInfo(){return info;}
    public Integer getId(){return id;}
    public String getNum(){return num;}
    public String getPasswd(){return passwd;}
    public String getEmail(){return email;}
    public String getPhone(){return phone;}
    public String getOffice(){return office;}
    public String getPosition(){return position;}
}
