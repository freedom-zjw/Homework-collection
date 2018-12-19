#define EXIT 0
#define READY 1
#define BLOCKED 2
int i;
int CurrentPCBno = 0; 
/*当前执行程序的PCB标号*/

int Stack [9][1000];
int* StackForOs=&Stack[1][0];

void CopyStack(int NewPCBno){
	for(i=0;i<1000;i++){
		Stack[NewPCBno][i]=Stack[CurrentPCBno][i];
	}
}
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
	int Process_Status;  /*状态*/
	int PCBID;           /*PCB编号*/
	int F_PCBID;         /*父亲PCB编号*/
}PCB;


PCB pcb_list[10];

PCB* Current_Process();
/*PCB表初始化函数*/
void schedule();
/*程序轮转函数*/

PCB* Current_Process(){  

	return &pcb_list[CurrentPCBno];
}


/*初始化PCB表的状态*/
void initstatus(){
	pcb_list[0].Process_Status=READY;
	for(i=1;i<8;i++){
		pcb_list[i].Process_Status=EXIT;
	}
}


/*执行fork操作*/
int do_fork(){
	PCB *p;
	p=&pcb_list[0];   /*从第0个PCB表开始*/
	while(p<=&pcb_list[7]&&p->Process_Status!=EXIT) p=p+1;
	/*寻找空的PCB表*/
	if(p>&pcb_list[7]){
		pcb_list[CurrentPCBno].regImg.AX=-1;
	}
	/*如果没有找到空的PCB块就退出，把当前进程的AX寄存器赋值为-1*/
	/*如果找到，开始把父进程的PCB复制到子进程的PCB模块*/
	p->regImg.GS = pcb_list[CurrentPCBno].regImg.GS;
	p->regImg.ES = pcb_list[CurrentPCBno].regImg.ES;
	p->regImg.DS = pcb_list[CurrentPCBno].regImg.DS;
	p->regImg.CS = pcb_list[CurrentPCBno].regImg.CS;
	p->regImg.FS = pcb_list[CurrentPCBno].regImg.FS;
	p->regImg.IP = pcb_list[CurrentPCBno].regImg.IP;
	p->regImg.AX = pcb_list[CurrentPCBno].regImg.AX;
	p->regImg.BX = pcb_list[CurrentPCBno].regImg.BX;
	p->regImg.CX = pcb_list[CurrentPCBno].regImg.CX;
	p->regImg.DX = pcb_list[CurrentPCBno].regImg.DX;
	p->regImg.DI = pcb_list[CurrentPCBno].regImg.DI;
	p->regImg.SI = pcb_list[CurrentPCBno].regImg.SI;
	p->regImg.BP = pcb_list[CurrentPCBno].regImg.BP;
	p->regImg.FLAGS = pcb_list[CurrentPCBno].regImg.FLAGS;
	
	
	/*为子进程的PCBID赋值，直接赋值为该PCB的下标编号*/
	p->PCBID=p-&pcb_list[0];
	
	/*把父进程栈的全部内容复制到子进程的栈段内*/
	CopyStack(p->PCBID);
	
	/*子进程的SS寄存器与父进程相同*/
	p->regImg.SS =pcb_list[CurrentPCBno].regImg.SS;
	p->regImg.SP =pcb_list[CurrentPCBno].regImg.SP+2000*p->PCBID;
/*子进程的SP寄存器与父进程当前的SP的值上加上我设置的大小1000的偏移乘上当前PCB编号/
	/*但是由于int是两个字节的，所以加上1000*2乘上PCB编号相同*/
	
	p->F_PCBID=CurrentPCBno;
	/*子进程的父PCB编号设置*/
	
	p->regImg.AX=0;
	/*子进程的寄存器AX设置为0*/
	p->Process_Status=READY;
	/*子进程的状态设置为就绪*/
	pcb_list[CurrentPCBno].regImg.AX=p->PCBID;
	/*父进程的AX寄存器设置为子进程的PCB编号*/
}


void do_wait(){
	pcb_list[CurrentPCBno].Process_Status=BLOCKED;
	/*把当前进程的状态设置为阻塞*/
	schedule();
	/*时间片轮转*/
}

void do_exit(){
	pcb_list[CurrentPCBno].Process_Status=EXIT;
	/*把当前进程状态设置为结束退出*/
	pcb_list[pcb_list[CurrentPCBno].F_PCBID].Process_Status=READY;
	/*把当前进程的父进程的状态设置为就绪*/
	pcb_list[pcb_list[CurrentPCBno].F_PCBID].regImg.AX=0;
	/*把当前进程的父进程的AX寄存器设置为0*/
	schedule();
	/*时间片轮转*/
}


void schedule(){
	/*防止下一次进入循环直接退出*/
	if(CurrentPCBno==7) CurrentPCBno=-1;
	/*每次从当前进程的下一个编号开始轮转*/
	for(i=CurrentPCBno+1;i<8;i++){
		if(pcb_list[i].Process_Status==READY){
			CurrentPCBno=i;
			break;
		}
		if(i==7) i=-1;
	}
}

