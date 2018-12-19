extern void clear();
extern void inputchar();
extern void printchar();
extern void printstr();
extern void setcursor();
extern void load();
extern void printstring_();
extern void set_clock_interrupt();
extern void re_clock_interrupt();
extern int fork();
extern void wait();
extern void exit();
#include "PCB.h"


char message[80]=" PRODUCT BY:\n";
char name1[80] = " Zhang Jiawei  ";
char name2[80] = " Zhang Hanyu\n";
char name3[80] = " Zhang Haoran";
char name4[80] = " Zhang Haitao\n";
char message0[80]="--------------------****----------------------------\n";
char message1[80]=" WELCOME !!!\n";
char message2[80]=" I ONLY HAVE A  FUNCTION \n";
char message3[80]=" INPUT RUN TO CHEAK IT\n";
char message4[80]="\n Please input \"q\" to continue: \n";
char message5[80]=" MyOS > ";
char message6[80]=" INPUT ERROR!!\n";
char message7[80]="WHICH MESSAGE YOU WANT TO CALCULATE ?\n";
char message8[80]="1 FOR MESSAGE1,2 FOR THE MESSAGE2.\n";
char message9[80]="MESSAGE1: ";
char message10[80]= "MESSAGE2: ";
char string[80];



int flag;
char string[80];
char s1[80]="ERROR\n";
char s2[80]="15352405 15352406 15352407 15352408\n";
char s3[80]="THIS IS FATHER FUNCTION: \n";
char s4[80]="THIS IS SON FUNCTION: \n";
char s5[80]="THE LENGTH OF THIS MESSAGE IS: ";
char s6[80]="Zhang Jiawei Zhang Hanyu Zhang Haoran Zhang Haitao\n";
int x=0;
int y=0;
int i=0;
int a=0;
int k=0;
int d=0;
int j=0;
int Strlength=0;
char str1[80]="";
int a;
int b;
char ch;
int len=0;
int pos=0;
void count(int );
void exchange(int );

/*********************input*********************************/
int input()
{
	inputchar();
	if(ch=='\b'){
		if(y>8&&y<79){
			y--;
			cal_pos();
			printchar(' ');
			y--;
			cal_pos();
		}
		return 0;
	}
	else if(ch==13);
	else printchar(ch);
	return 1;
}



inputcmd(){
	printstr(15,message5);
	i=0;
	while(1)
	{
		if(input()){
			if(ch==13) 
				break;
			string[i++]=ch;
		}
		else if(i>0) i--; 
	}
	for(a=0;a<i;++a)
		if(string[0]==' '){
			for(k=1;k<i;++k) 
				string[k-1]=string[k];
			i--;
		}
		else break;
	string[i]='\0';
	len=i;
	printstr("\n");
}

cal_pos()
{	
	if(y>78){
		y=1;
		x++;
	}
	if(x>23) 
		clear();
	pos=(x*80+y)*2;
	setcursor();
}

/****************run*********************************/
RUN1()
{
    int pid; 
	init(); 
	set_clock(); 
	pid=fork();
	if (pid==-1) 
		printstr(15,s1);
	else if (pid){ 
	     printstr(14,s3);
		 wait();
		 printstr(14,s3);
		 printstr(14,s2);
		 printstr(14,s5);
		 printstr(14,str1);
		 restart_clock();
	} 
	else{  
	   printstr(14,s4);
	   printstr(14,message9);
	   printstr(14,s2);
	   count(1);	   
	   exit();
	}
}

RUN2()
{
    int pid; 
	init(); 
	set_clock(); 
	pid=fork();
	if (pid==-1) 
		printstr(15,s1);
	else if (pid){ 
	     printstr(14,s3);
		 wait();
		 printstr(14,s3);
		 printstr(14,s6);
		 printstr(14,s5);
		 printstr(14,str1);
		 restart_clock();
	} 
	else{  
	   printstr(14,s4);
	   printstr(14,message10);
	   printstr(14,s6);
	   count(2);	   
	   exit();
	}
}
/*****************************count********************/

void count(int j)
{
	Strlength=0;
	if(j==1)
	{
			for(i=0;i<80;i++)
		{
			if(s2[i]!='\0')
				Strlength++;
			else 
			{
				exchange(Strlength);	
				break;
			}
		}
	}
	else 
	{
		for(i=0;i<80;i++)
		{
			if(s6[i]!='\0')
				Strlength++;
			else 
			{
				exchange(Strlength);	
				break;
			}
		}
	}
	
}


void exchange(int s)
{
	i=0;
	b= s % 10;
	a= s / 10;
	if(a!=0)
	{
		str1[i]=a+'0';
		i++;
	}
	str1[i]=b+'0';
	str1[i+1]='\0';
}




/**************main*********************************/

cmain(){
	while(1)
{
	clear();
	printstr(15,message);
	printstr(15,name1);
	printstr(15,name2);
	printstr(15,name3);
	printstr(15,name4);
	printstr(15,message0);
	printstr(15,message1);
	printstr(15,message2);
	printstr(15,message3);
	inputcmd();
	if(string[0]=='R')
	{
		clear();
	    printstr(14,message7);
	    printstr(14,message8);
		inputcmd();
		clear();
		if(string[0]=='1')
			RUN1();
		else if(string[0]=='2')
			RUN2();
		printstr(15,message4);
		inputcmd();
		if(string[0]=='q')
			continue;
	}
	else{
		printstr(15,message6);
		printstr(15,message4);
		inputcmd();
		if(string[0]=='q') 
			continue;
	}
}
}