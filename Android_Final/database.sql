USE android;
CREATE TABLE STUDENTS (
sid INT NOT NULL AUTO_INCREMENT PRIMARY KEY, --sid
snum VARCHAR ( 20 ) UNIQUE NOT NULL, --学号
passwd VARCHAR ( 20 ) NOT NULL,	--密码
sname VARCHAR ( 20 ) NOT NULL,	--名字
sex VARCHAR ( 10 ) DEFAULT 'unknow', --性别
nickname VARCHAR ( 20 ) DEFAULT 'STUDENT', --昵称
avatar VARCHAR ( 500 ) DEFAULT 'https://chonor.cn/Android/Avatar/defaultPhoto.png', --头像地址
college VARCHAR ( 100 ) DEFAULT '还没有填写学院', --学院
introduction VARCHAR ( 1000 ) DEFAULT '还没有填写简介', --简介
permission INT NOT NULL DEFAULT 0   --unuse
) ENGINE = INNODB DEFAULT charset = utf8;

USE android;
CREATE TABLE TEACHERS (
tid INT NOT NULL AUTO_INCREMENT PRIMARY KEY,--tid
tnum VARCHAR ( 20 ) UNIQUE NOT NULL,--教工号
passwd VARCHAR ( 20 ) NOT NULL,--密码
tname VARCHAR ( 20 ) NOT NULL,--名字
sex VARCHAR ( 10 ) DEFAULT 'unknow',--性别
nickname VARCHAR ( 20 ) DEFAULT 'Teacher',--昵称
avatar VARCHAR ( 500 ) DEFAULT 'https://chonor.cn/Android/Avatar/defaultPhoto.png',--头像
college VARCHAR ( 100 ) DEFAULT '还没有填写学院',--学院
introduction VARCHAR ( 1000 ) DEFAULT '还没有填写简介',--介绍
email VARCHAR ( 50 ) DEFAULT '还没有填写邮箱',--邮箱
phone VARCHAR ( 30 ) DEFAULT '还没有填写联系方式',--电话
office VARCHAR ( 30 ) DEFAULT '还没有填写办公地点',--办公地点
position VARCHAR ( 10 ) DEFAULT '讲师',--职称
permission INT NOT NULL DEFAULT 0 
) ENGINE = INNODB DEFAULT charset = utf8;


USE android;
CREATE TABLE COURSES (
cid INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
tid INT NOT NULL,
cname VARCHAR ( 50 ) NOT NULL,--课程名称
week VARCHAR ( 10 ) NOT NULL,--上课星期x
timer VARCHAR ( 10 ) NOT NULL,--第几节
hour  VARCHAR ( 10 ) DEFAULT '0',--学时
credit VARCHAR ( 10 ) DEFAULT '0',--学分
position VARCHAR ( 50 ) DEFAULT '未知',--地点
college VARCHAR ( 100 ) DEFAULT '未知',--开设学院
introduction VARCHAR ( 1000 ) DEFAULT '此课程还未填写简介',
foreign key (tid) references TEACHERS(tid) on delete cascade
) ENGINE = INNODB DEFAULT charset = utf8;

USE android;
create table COMMENTS(
	comid INT not null AUTO_INCREMENT PRIMARY KEY, 
	num VARCHAR ( 20 ) not null,  --评论者的学号/教工号
	info varchar(1000),			--内容
	commenttime DATETIME,		--时间
    position VARCHAR ( 100 ) DEFAULT '无法获取位置',--评论者位置
    up INT NOT NULL DEFAULT 0,  --赞
    down INT NOT NULL DEFAULT 0,--踩
    report INT NOT NULL DEFAULT 0,--举报
	src varchar(500)  --图片src
)ENGINE = INNODB DEFAULT charset = utf8;

USE android
create table HOMEWORKS(
	hwid INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	cid INT NOT NULL,
	title varchar(30), --标题
	info varchar(1000), --作业具体内容
	deadline DATETIME,--ddl
	foreign key (cid) references COURSES(cid) on delete cascade	
)ENGINE = INNODB DEFAULT charset = utf8;
USE android

create table HOMEWORKSCORE( 
	hsid INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	hwid INT NOT NULL,--作业id
	sid INT NOT NULL,--学号
	score INT NOT NULL DEFAULT 0,--作业得分
	foreign key (sid) references STUDENTS(sid) on delete cascade,
	foreign key (hwid) references HOMEWORKS(hwid) on delete cascade	
)ENGINE = INNODB DEFAULT charset = utf8;

USE android
create table NOTICES(
	nid INT NOT NULL AUTO_INCREMENT PRIMARY KEY, --通知id
	cid INT NOT NULL, --课程id
	title varchar(30), --标题
	info varchar(1000),--内容
    starttime DATETIME,--发布时间
	endtime DATETIME,--销毁时间
	foreign key (cid) references COURSES(cid) on delete cascade	
)ENGINE = INNODB DEFAULT charset = utf8;



USE android;
CREATE TABLE CHOICES (
chid INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
sid INT not null, --学生id
cid INT not null, --课程id
score INT NOT NULL DEFAULT 0,--课程分数
foreign key (cid) references COURSES(cid) on delete cascade,
foreign key (sid) references STUDENTS(sid) on delete cascade		
) ENGINE = INNODB DEFAULT charset = utf8;


USE android;
CREATE TABLE COMMENTSUP ( --评论赞数
cup INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
num varchar(20), --点赞者的学号教工号
comid INT NOT NULL,
foreign key (comid) references COMMENTS(comid) on delete cascade	
) ENGINE = INNODB DEFAULT charset = utf8;

USE android;
CREATE TABLE COMMENTSDOWN (
cup INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
num varchar(20),--点踩者的学号教工号
comid INT NOT NULL,
foreign key (comid) references COMMENTS(comid) on delete cascade	
) ENGINE = INNODB DEFAULT charset = utf8;

USE android;
CREATE TABLE COMMENTSREPORT (
cup INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
num varchar(20),--举报者的学号教工号
comid INT NOT NULL,
foreign key (comid) references COMMENTS(comid) on delete cascade	
) ENGINE = INNODB DEFAULT charset = utf8;