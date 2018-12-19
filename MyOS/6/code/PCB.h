int END = 0;
int RUN = 1;
int BLOCKED = 2;
#define MAXNUM 8 
/*最大进程数*/

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
	int STATU;      /*运行状态*/
	int ID;           /*自己的PCB编号*/
	int FID;          /*父亲PCB编号*/
}PCB;


PCB pcb_list[MAXNUM];
int i;
int nowID = 0; /*当前程序的PCB编号*/

int Stack [MAXNUM][1024];
int* OsStack=&Stack[1][0];

void init(){/*初始化PCB表*/
	for (i=0;i<MAXNUM;i++)pcb_list[i].STATU=END;
	pcb_list[0].STATU=RUN;
}

void stackcopy(int newID){
	for(i=0;i<1024;i++){
		Stack[newID][i]=Stack[nowID][i];
	}
}

PCB* Current_Process(){ 
	return &pcb_list[nowID];
}

void schedule(){/*时间片轮转*/
	for(i=(nowID+1)%MAXNUM;i<MAXNUM;i=(i+1)%MAXNUM)
		if(pcb_list[i].STATU==RUN){
			nowID=i;
			break;
		}
}

void memcopy(RegisterImage* F_PCB, RegisterImage* C_PCB ,int shamt){
	C_PCB -> SS = F_PCB -> SS;
	C_PCB -> SP = F_PCB -> SP + shamt;
	C_PCB -> GS = F_PCB -> GS;
	C_PCB -> ES = F_PCB -> ES;
	C_PCB -> DS = F_PCB -> DS;
	C_PCB -> CS = F_PCB -> CS;
	C_PCB -> FS = F_PCB -> FS;
	C_PCB -> IP =  F_PCB -> IP;
	C_PCB -> AX = F_PCB -> AX;
	C_PCB -> BX = F_PCB -> BX;
	C_PCB -> CX = F_PCB -> CX;
	C_PCB -> DX = F_PCB -> DX;
	C_PCB -> DI = F_PCB -> DI;
	C_PCB -> SI = F_PCB -> SI;
	C_PCB -> BP = F_PCB -> BP;
	C_PCB -> FLAGS = F_PCB -> FLAGS;
}

int do_fork(){
	PCB *p=&pcb_list[0];
	while(p<=&pcb_list[MAXNUM-1]&&p->STATU!=END) p=p+1; 
	if(p>&pcb_list[MAXNUM-1])pcb_list[nowID].regImg.AX=-1;
	p->ID=p-&pcb_list[0];
    memcopy(&pcb_list[nowID].regImg,&pcb_list[p->ID].regImg,1024*2*p->ID);
	/*PCB复制函数*/
	stackcopy(p->ID);
	/*栈复制函数*/
	p->FID=nowID;
	p->regImg.AX=0;
	p->STATU=RUN;
	pcb_list[nowID].regImg.AX=p->ID;

}

void do_wait(){
	pcb_list[nowID].STATU=BLOCKED;
	schedule();
}

void do_exit(){
	pcb_list[nowID].STATU=END;
	pcb_list[pcb_list[nowID].FID].STATU=RUN;
	pcb_list[pcb_list[nowID].FID].regImg.AX=0;
	schedule();
}




