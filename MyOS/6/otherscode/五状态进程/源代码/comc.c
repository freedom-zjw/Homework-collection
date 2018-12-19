extern void clear();
extern void inputchar();
extern void printchar();
extern void printstring();
extern void setcursor();
extern void load();
extern void printstring_();
extern int forking();
extern void waiting();
extern void exiting();
extern void set_clock_interrupt();
extern void re_clock_interrupt();
#include "kdata.h"



char message13[80]=" Please inoput your user_name and password!\n";
char message_name[80]=" Name :  ";
char message_password[80]=" Password : ";
char user1_name[80] = " zhurui";
char user2_name[80] = " zhongzhenwei";
char user3_name[80] = " zhangzikun";
char user1_password[80] = "15352454";
char user2_password[80] = "15352447";
char user3_password[80] = "15352429";
char name_error[80]=" User name does not exit!!\n";
char password_error[80]=" Password does not exit!!\n";



char message1[80]=" Welcome to use system of OUR GROUP\n";
char message2[80]=" you can input some legal instructions\n";
char message5[80]=" We only have one function in this lab\n";
char message6[80]=" you can input dad-son to begin the conversation'\n";
char message7[80]="    if you want to run system call,input \'syscall\'\n";
char message8[80]="       for example: syscall\n";
char message11[80]=" If you want to continue,input \'yes\'\n";
char message_cmd[80]=" Please input> ";
char message_show1[80]=" system call 0: 0\n";
char message_show2[80]=" system call 1: 1\n";
char message_show3[80]=" system call 2: 2\n";
char message_show4[80]=" system call 3: 3\n";
char message_show5[80]=" you can run more than one\n";

char message_pro1[80]=" please input the program you want to load\n";
char message_pro2[80]=" for example '1 8' or '2 6'  or '12345678', but no more than 8\n";


char string[80];
char message_error[80]=" INPUT ERROR!!\n";
const char default1[80]=" batch 1 2 3 4";
const char default2[80]=" time 1 2 3 4";
int len=0;
int pos=0;
char ch;
int x=0;
int y=0;
int i=0;
int a=0;
int u=0;
int d=0;/*data*/
int offset_user=0x4100;/*用户程序偏移量*/
const int offset_user1=0x4100;/*9900h*/
const int offset_user2=0x4500;/*9d00h*/
const int offset_user3=0x4900;/*a100h*/
const int offset_user4=0x4d00;/*a500h*/
const int hex600h=1536;/*600h*/
int offset_begin=37120;/*读到内存的偏移量*/
int num_shanqu=8;/*扇区数*/
int pos_shanqu=2;/*起始扇区数*/


int flag;


char s1[80]="error in fork\n";

char s2[80]="Fathers: Hey, I am farther!\n";
char s6[100]="Fathers: I will go to sleep until my son dead and tell me how long is the letter!\n";
char s7[80]="Fathers: Oh, hello, I am father, i am wake up! \n";
char s8[80]="Fathers: And my son tell me that the letter's length is :  ";


char s3[80]="Son: Hello, I am children!\n";
char s4[80]="Son: My mission is to count the length of the letter!\n";
char s5[80]="Son: And tell the result to my father and wake up my father when I am died!\n";

char hang[80] = "*********************************\n";


int input()
{
	inputchar();/*输入字符*/
	if(ch=='\b'){/*是删除键Backspace*/
		if(y>8&&y<79){
			y--;
			cal_pos();
/*在前一位置显示空格，并显示后回退一个位置*/
			printchar(' ');
			y--;
			cal_pos();
		}
		return 0;
	}
	else if(ch==13);/*是回车*/
	else printchar(ch);/*显示字符*/
	return 1;
}



cin_cmd(){
	printstring(message_cmd);
	i=0;/*初始字符串下标*/
	while(1)
	{
		if(input()){/*不是删除键*/
			if(ch==13) break;/*是回车*/
			string[i++]=ch;
		}
		else if(i>0) i--; /*是删除键，则删除*/
	}
	/*去掉字符串前面的空格*/
	for(a=0;a<i;++a)
		if(string[0]==' '){
			for(u=1;u<i;++u) string[u-1]=string[u];
			i--;
		}
		else break;
	string[i]='\0';/*末尾加空字符*/
	len=i;/*记录字符串长度*/
	printstring("\n");
}

cal_pos()
{	
	if(y>78){
		y=1;
		x++;
	}
	if(x>23) clear();
	pos=(x*80+y)*2;
	setcursor();
}

char str[80]="129djwqhdsajd128dw9i39ie93i8494urjoiew98kdkd\n";
int LetterNr=0;
char str1[80]="";
int a;
int b;
/*把数字转化成字符串，便于输出*/
void exchange(int Letter)
{
	i=0;
	b= Letter % 10;
	a= Letter / 10;
	if(a!=0)
	{
		str1[i]=a+'0';
		i++;
	}
	str1[i]=b+'0';
	str1[i+1]='\0';
}
/*数字符串str的长度*/
void count()
{
	LetterNr=0;
	for(i=0;i<80;i++)
	{
		if(str[i]!='\0')
			LetterNr++;
		else 
			break;
	}
}





father_and_children()
{
	/*设置局部变量PID，用于区分父子进程*/
    int pid; 
	/*初始化所有PCB表的状态*/
	initstatus(); 
	/*开启时钟中断*/
	set_clock_interrupt(); 
	/*fork操作*/
	pid=forking();/*save do_fork restart*/
	
	if (pid==-1) printstring(s1);/*GUI*/
	if (pid){ /*父进程*/
	     printstring(s2);
		 printstring(hang);
		 printstring(s6);
		 printstring(hang);
		 
		 /*wait操作*/
		  waiting();   /* save do_wait restart*/
		  
		  printstring(s7);
		  printstring(hang);
		  printstring(s8);
		  exchange(LetterNr);
		  printstring(str1);
		  printstring("\n");
		  printstring(hang);
		  re_clock_interrupt();
	} 
	else{  /*子进程*/
	   printstring(s3);
	   printstring(hang);
	   printstring(s4);
	   printstring(hang);
	   printstring(s5);
	   printstring(hang);
	   count();	   
	   /*exit操作*/
	   exiting();/*save do_exit restart*/
	}
}





cmain(){
	while(1)
{
	clear();
	printstring(message1);
	printstring(message2);
	printstring(message5);
	printstring(message6);
	cin_cmd();
	if(string[0]=='d')
	{
		clear();
		father_and_children();
		printstring(message11);
		cin_cmd();
		if(string[0]=='y') continue;
	}
	else{
		printstring(message_error);
		printstring(message11);
		cin_cmd();
		if(string[0]=='y') continue;
	}
}
}