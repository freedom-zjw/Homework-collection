


extern void clear();
extern void cls();
extern void inputchar();
extern void printchar();
extern void paintchar();
extern void paintchar1();
extern void printstring();
extern void printstring1();
extern void setcursor();
extern void load();
extern void jmp();
extern void filldata();
extern void readdata();
extern void check();
extern void load();
extern void another_load(int segment,int,int offset);
extern void copystack(int,int);
extern void CountLetter();
extern void printint(int);
extern void fork();
extern void wait();
extern void exit(int);


char mesg1[80]="Welcome to use our system\n";
char mesg2[80]=" You can input some legal instructions\n";
char mesg3[80]="    If you want to run the program by run,input \'run\'\n\n";
char mesg4[80]="    We have four programs numbered by 1,2,3,4,5.5 is the test program.\n";
char mesg5[80]="    You can run program x by inputting x\n";
char informesg[80]="    If you want to see the information of the grograms,input \'xinxi\'\n\n";
char success[80]="	Successfully! Input anything to go back.\n";
char wrong1[80]=" Your input is illegal! Input anything to go back.\n";
char xinxi1[80]="USER PROGRAM1:\n   Name:lab31,Size:1024byte,Position:section 2\n";
char xinxi2[80]="USER PROGRAM2:\n   Name:lab32,Size:1024byte,Position:section 4\n";
char xinxi3[80]="USER PROGRAM3:\n   Name:lab33,Size:1024byte,Position:section 6\n";
char xinxi4[80]="USER PROGRAM4:\n   Name:lab34,Size:1024byte,Position:section 8\n";
char string[80];
int pos=0;
char ch;
int x=0;
int y=0;
int i=0;
int f;
int k,j;
int a=0;
int u=0;
int d=0;/*data*/
int offset_user=0xa100-0x8000;
const int offset_user1=0xa100-0x8000;
const int offset_user2=0xa500-0x8000;
const int offset_user3=0xa900-0x8000;
const int offset_user4=0xad00-0x8000;
const int offset_user5=0xb100-0x8000;
int offset_begin=0xa100-0x8000;
int num_shanqu=10;
int pos_shanqu=2;
int Segment = 0x2000;


int NEW = 0;
int READY = 1;
int RUNNING = 2;
int END = 3;
int BLOCK=4;
int FREE=0;
int USING=1;

typedef struct RegisterImage{
	int SS;
	int GS;
	int FS;
	int ES;
	int DS;
	int DI;
	int SI;
	int BP;
	int SP;
	int BX;
	int DX;
	int CX;
	int AX;
	int IP;
	int CS;
	int FLAGS;
}RegisterImage;

typedef struct PCB{
	RegisterImage regImg;
	int Process_Status;
	int Pid;
	int used;
	int Fpid;
	int Cpid;
}PCB;


PCB pcb_list[8];
int CurrentPCBno = 0; 
int Program_Num = 0;


extern void printChar();

PCB* Current_Process();
void Save_Process(int,int, int, int, int, int, int, int,
		  int,int,int,int, int,int, int,int );
void init(PCB*, int, int);
void Schedule();
void special();



void Save_Process(int gs,int fs,int es,int ds,int di,int si,int bp,
		int sp,int dx,int cx,int bx,int ax,int ss,int ip,int cs,int flags)
{
	pcb_list[CurrentPCBno].regImg.AX = ax;
	pcb_list[CurrentPCBno].regImg.BX = bx;
	pcb_list[CurrentPCBno].regImg.CX = cx;
	pcb_list[CurrentPCBno].regImg.DX = dx;

	pcb_list[CurrentPCBno].regImg.DS = ds;
	pcb_list[CurrentPCBno].regImg.ES = es;
	pcb_list[CurrentPCBno].regImg.FS = fs;
	pcb_list[CurrentPCBno].regImg.GS = gs;
	pcb_list[CurrentPCBno].regImg.SS = ss;
	

	pcb_list[CurrentPCBno].regImg.IP = ip;
	pcb_list[CurrentPCBno].regImg.CS = cs;
	pcb_list[CurrentPCBno].regImg.FLAGS = flags;
	
	pcb_list[CurrentPCBno].regImg.DI = di;
	pcb_list[CurrentPCBno].regImg.SI = si;
	pcb_list[CurrentPCBno].regImg.SP = sp;
	pcb_list[CurrentPCBno].regImg.BP = bp;

}
int q,w;
void Schedule(){
	if(string[0]=='5'&&string[1]=='s'&&i==2)
	{
		printstring("schedule start\n");
		if(pcb_list[CurrentPCBno].Process_Status==RUNNING)
			pcb_list[CurrentPCBno].Process_Status = READY;	
		printchar(CurrentPCBno+'0');
		CurrentPCBno ++;
		if( CurrentPCBno > Program_Num )
			CurrentPCBno = 0;

			while(pcb_list[CurrentPCBno].Process_Status != READY)
			{
				CurrentPCBno ++;
				if( CurrentPCBno > Program_Num )
				CurrentPCBno = 0;
			}
			printstring("schedule done\n");
			printchar(CurrentPCBno+'0');
			printchar(Program_Num+'0');
		pcb_list[CurrentPCBno].Process_Status = RUNNING;
			
		return;
	}
	else
	{
			if( Program_Num==0 )	return;
		if(pcb_list[CurrentPCBno].Process_Status==RUNNING)
			pcb_list[CurrentPCBno].Process_Status = READY;	
		CurrentPCBno ++;
		if( CurrentPCBno > Program_Num )
			CurrentPCBno = 1;
		w=0;
		for(q=1;q<=Program_Num;++q)
		{
			if(pcb_list[CurrentPCBno].Process_Status != READY)
			{
				CurrentPCBno ++;
				if( CurrentPCBno > Program_Num )
				CurrentPCBno = 1;
			}
			else
			{
				w=1;
				break;
			}			
		}
		if(w)
		pcb_list[CurrentPCBno].Process_Status = RUNNING;
		else CurrentPCBno=0;		
		return;
	}
	
}

PCB* Current_Process(){

	return &pcb_list[CurrentPCBno];
}

void init(PCB* pcb,int segement, int offset)
{
	pcb->regImg.GS = 0xb800;
	pcb->regImg.SS = segement;
	pcb->regImg.ES = segement;
	pcb->regImg.DS = segement;
	pcb->regImg.CS = segement;
	pcb->regImg.FS = segement;
	pcb->regImg.IP = offset;
	pcb->regImg.SP = offset-16;
	pcb->regImg.AX = 0;
	pcb->regImg.BX = 0;
	pcb->regImg.CX = 0;
	pcb->regImg.DX = 0;
	pcb->regImg.DI = 0;
	pcb->regImg.SI = 0;
	pcb->regImg.BP = 0;
	pcb->regImg.FLAGS = 512;
	pcb->Process_Status = READY;
	pcb->Pid=0;
	pcb->used=FREE;	
	pcb->Fpid=0;
	pcb->Cpid=-1;
}

void special()
{
	if(pcb_list[CurrentPCBno].Process_Status==NEW)
		pcb_list[CurrentPCBno].Process_Status=RUNNING;
}





cal_pos()
{	
	if(y>79){
		y=0;
		x++;
	}
	if(x>24) clear();
	pos=(x*80+y)*2;
	setcursor();
}
int input()
{
	inputchar();
	if(ch=='\b'){
		if(y>0&&y<79){
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

cin_cmd(){
	i=0;
	while(1){
		if(input()){
			if(ch==13) break;
			else if(ch==' ') continue;
			string[i++]=ch;
		}
	}
	printstring("\n");
}
int is_ouch;
int ouch_color;
c_ouch()
{
	
	if(is_ouch==1) {
		x=y=pos=0;
		is_ouch=0;
		ouch_color=1;
	}	
	printstring1(ouch_color,"Ouch!");
	ouch_color++;
	if(ouch_color>15) ouch_color=1;
}

 testshow()
{
	printstring("iret ok");
	
}

void init_Pro()
{
	init(&pcb_list[0],0x1000,0x100);
	init(&pcb_list[1],0x2000,0x100);
	init(&pcb_list[2],0x3000,0x100);
	init(&pcb_list[3],0x4000,0x100);
	init(&pcb_list[4],0x5000,0x100);
	init(&pcb_list[5],0x6000,0x100);
	pcb_list[0].Process_Status=RUNNING;
	pcb_list[0].used=USING;
}
int newpid;
void c_fork()
{	
	printstring(" c_fork work\n");
	newpid=-1;
	for(i=0;i<6;++i)
	{
		if(pcb_list[i].used==FREE)
		{
			newpid=i;
			break;
		}
	}
	printstring(" find newpid ");
	printchar(newpid+'0');
	if(newpid==-1) pcb_list[CurrentPCBno].regImg.AX=-1;
	else
	{
		Program_Num++;
		pcb_list[CurrentPCBno].Cpid=newpid;
		pcb_list[newpid].Process_Status=READY;	
		pcb_list[newpid].used=USING;
		pcb_list[newpid].Pid=newpid;
		pcb_list[newpid].Fpid=CurrentPCBno;
		pcb_list[newpid].Cpid=-1;
		printstring(" \n copy start\n");
		copy(newpid);
		printstring(" copy done\n");
		pcb_list[CurrentPCBno].regImg.AX=CurrentPCBno;		
		pcb_list[newpid].regImg.AX=newpid;

	}	
}
copy()
{
	pcb_list[newpid].regImg.AX = pcb_list[CurrentPCBno].regImg.AX;
	pcb_list[newpid].regImg.BX = pcb_list[CurrentPCBno].regImg.BX;
	pcb_list[newpid].regImg.CX = pcb_list[CurrentPCBno].regImg.CX;
	pcb_list[newpid].regImg.DX = pcb_list[CurrentPCBno].regImg.DX ;

	pcb_list[newpid].regImg.DS = pcb_list[CurrentPCBno].regImg.DS;
	pcb_list[newpid].regImg.ES = pcb_list[CurrentPCBno].regImg.ES;
	pcb_list[newpid].regImg.FS = pcb_list[CurrentPCBno].regImg.FS;
	pcb_list[newpid].regImg.GS = pcb_list[CurrentPCBno].regImg.GS;
	
	pcb_list[newpid].regImg.SS=(newpid+1)*0x1000;
	copystack(pcb_list[CurrentPCBno].regImg.SS,pcb_list[newpid].regImg.SS);

	pcb_list[newpid].regImg.IP = pcb_list[CurrentPCBno].regImg.IP ;
	pcb_list[newpid].regImg.CS = pcb_list[CurrentPCBno].regImg.CS;
	pcb_list[newpid].regImg.FLAGS = pcb_list[CurrentPCBno].regImg.FLAGS;
	
	pcb_list[newpid].regImg.DI = pcb_list[CurrentPCBno].regImg.DI;
	pcb_list[newpid].regImg.SI = pcb_list[CurrentPCBno].regImg.SI;
	pcb_list[newpid].regImg.SP = pcb_list[CurrentPCBno].regImg.SP;
	pcb_list[newpid].regImg.BP = pcb_list[CurrentPCBno].regImg.BP;
}

void c_wait() {
       pcb_list[CurrentPCBno].Process_Status=BLOCK;       	   
}
void c_exit(int ch) {
       pcb_list[CurrentPCBno].Process_Status=END;
	   pcb_list[CurrentPCBno].used=FREE;
	   pcb_list[pcb_list[CurrentPCBno].Fpid].Process_Status=READY;
       pcb_list[pcb_list[CurrentPCBno].Fpid].regImg.AX=ch;
}
char str[80]="ZhangBo";
int LetterNr=0;
int pid;
display5s() { 
   x=0;
   y=0;
   printstring(" 5 states show:\n");
   fork(); 
   pid=pcb_list[CurrentPCBno].regImg.AX;
   printstring(" fork done\n");	   
	
 
   if (pid==-1)
	   {
		   printstring(" error in fork!\n");
		   exit(-1);
	   }
   if (pid==0) 
	{
		printstring(" This is father process.\n");
		printstring(" I want to count the number of chars of \"ZhangBo\".\n");
		printstring(" waiting...\n");
        wait();
		printstring(" Father process continues.\n");
		printstring(" LetterNr=");
		printchar(LetterNr+'0');
		exit(0) ;
	} 
	else if(pid==1)
	{
		printstring(" This is child process.\n");
		printstring(" counting...\n");
		CountLetter(str);
		printstring(" Child process ends.\n");
		exit(0);
	}
	check();
}

cmain(){
	while(1)
{	
		
		clear();
		init_Pro();
		printstring(mesg1);
		printstring(mesg2);
		printstring(mesg3);
		printstring(informesg);
		printstring("    If you want to use pa,input \'pa\' numbers.Numbers represent the processes,including 1 2 3 4 5.");
		printstring("5 is a test file.\n");
		printstring("    If you want to test 5 states module,input 5s\n");
		cin_cmd();
		printstring(string);
		if(string[0]=='r'&&string[1]=='u'&&string[2]=='n'&&i==3){			
			printstring(mesg4);
			printstring(mesg5);
			cin_cmd();
			if(i==1){
				load(offset_begin,num_shanqu,pos_shanqu);
				if(string[0]=='1'){
					offset_user=offset_user1;
					clear();
					jmp();
				}
				else if(string[0]=='2'){
					offset_user=offset_user2;
					clear();
					jmp();
				}
				else if(string[0]=='3'){
					offset_user=offset_user3;
					clear();
					jmp();
				}
				else if(string[0]=='4'){
					offset_user=offset_user4;
					clear();
					jmp();
				}
				else if(string[0]=='5'){
					offset_user=offset_user5;
					clear();
					jmp();
				}
				else {
					printstring(wrong1);
					check();
				}
			}
			else{
				printstring(wrong1);
				check();
			}
		}
		else if(string[0]=='x'&&string[1]=='i'&&string[2]=='n'&&string[3]=='x'&&string[4]=='i'&&i==5){
			printstring(xinxi1);
			printstring(xinxi2);
			printstring(xinxi3);
			printstring(xinxi4);	
			printstring(success);
			check();
		}
		else if(string[0]=='5'&&string[1]=='s'&&i==2)
		{
			cls();
			
			display5s();					
		}
		else if(string[0]=='p'&&string[1]=='a'&&i>2){
			cls();
			if(string[2]=='5'&&i==3)
			{
				for( j=1;j<=4;++j)
					{												
						another_load(Segment,0,j*2+10);	
						Segment+=0x1000;
						Program_Num ++;						
					}	
					cls();
			}
			else if(i<=6)
			{
				f=0;
				for(k=2;k<i;++k)
				{
					if(string[k]<'1'||string[k]>'4')
					{
						f=1;
						break;
					}						
					for( j=2;j<i;++j)
					{
						if(j!=k&&string[j]==string[k])
						{							
							f=1;
							break;							
						}
							
					}
				}
				if(f==1)
				{
					printstring(wrong1);
					check();
				}
				else if(f==0)
				{	
					
					for( k=2;k<i;++k)
					{
						
						j=string[k]-'0';
						another_load(Segment,0,j*2+10);	
						Segment+=0x1000;
						Program_Num ++;	
						d++;	
					}	
					paintchar1(23,Program_Num+'0');
					check();				
				}				
			}
			else
			{
				printstring(wrong1);
				check();
			}
			
			cls();
		}
		else{
			printstring(wrong1);
			check();
		}
		
}
}
