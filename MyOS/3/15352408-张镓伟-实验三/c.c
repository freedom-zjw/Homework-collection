extern void clear();
extern void inputch();
extern void printch();
extern void printstr();
extern void setcursor();
extern void load();
extern void jmp();
extern void filldata();
extern void readdata();

char cmdm[80]="MYOS> ";
char message1[80]="                                Welcome to MYOS\n";
char message2[80]="Please input your instructions\n";
char message3[80]="A.Check the information of the OS\n";
char message4[80]="B.Run the programs in sequence\n";
char message5[80]="e.g B 1 2 3 4 (Then programs will be run in such a order)\n";
char message6[80]="C.Run the programs simultaneously\n";
char message7[80]="e.g C 1 2 3 (Then program 1,2,3 will be run at the same time)\n";
char message8[80]="D.Run as default\n";
char defaultm[80]="The default cmd is B 1 2 3 4\n";
const char defaultinst[80]="B 1 2 3 4";
char info1[80]="USER PROGRAM1-Name:function1 Size:1024byte Position:section 2\n";
char info2[80]="USER PROGRAM2-Name:function2 Size:1024byte Position:section 4\n";
char info3[80]="USER PROGRAM3-Name:function3 Size:1024byte Position:section 6\n";
char info4[80]="USER PROGRAM4-Name:function4 Size:1024byte Position:section 8\n";
char back1[80]="Input anything and return\n";
char error1[80]="Your instructions cannot be recognized.Try again.\n";

int offset_user=37120;
const int offset_user1=37120;
const int offset_user2=38144;
const int offset_user3=39168;
const int offset_user4=40192;
const int hex600h=1536;
int offset_begin=37120;
int num_shanqu=8;
int pos_shanqu=2;

char str[80];
char ch;
int length=0;
int pos=0;
int x=0;
int y=0;
int i=0;
int j=0;
int k=0;
int s=0;
int d=0;

int input()
{
	inputch();
	if(ch=='\b'){
		if(y>6&&y<79){
			y--;
			calpos();
			printch(' ');
			y--;
			calpos();
		}
		return 0;
	}
	if(ch!=13)
		printch(ch);
	return 1;
}

inputcmd(){
	printstr(cmdm);
	i=0;
	while(1){
		if(input()){
			if(ch==13)
				break;
			str[i++]=ch;
		}
		else if(i>0)
			i--;
	}
	for(j=0;j<i;j++){
		if(str[0]==' '){
			for(k=1;k<i;k++)
				str[k-1]=str[k];
			i--;
		}
		else break;
	}
	str[i]='\0';
	length=i;
	printstr("\n");
}

calpos()
{
	if(y>79){
		y=0;
		x++;
	}
	if(x>24)
		clear();
	pos=(x*80+y)*2;
	setcursor();
}

b(){
	clear();
	offset_begin=offset_user1;
	num_shanqu=8;
	pos_shanqu=2;
	load(offset_begin,num_shanqu,pos_shanqu);
	filldata(hex600h,0);
	filldata(hex600h+2,0);
	filldata(hex600h+4,0);
	filldata(hex600h+6,0);
	for(i=0;i<length;i++){
		if(str[i]=='1'){
			offset_user=offset_user1;
			jmp();
		}
		else if(str[i]=='2'){
			offset_user=offset_user2;
			jmp();
		}
		else if(str[i]=='3'){
			offset_user=offset_user3;
			jmp();
		}
		else if(str[i]=='4'){
			offset_user=offset_user4;
			jmp();
		}
		
	}
	
}

c(){
	clear();
	offset_begin=offset_user1;
	num_shanqu=8;
	pos_shanqu=2;
	load(offset_begin,num_shanqu,pos_shanqu);
	filldata(hex600h,0);
	filldata(hex600h+2,0);
	filldata(hex600h+4,0);
	filldata(hex600h+6,0);
	for(i=0;i<length;i++){
		if(str[i]=='1') filldata(hex600h,1);
		if(str[i]=='2') filldata(hex600h+2,1);
		if(str[i]=='3') filldata(hex600h+4,1);
		if(str[i]=='4') filldata(hex600h+6,1);
	}
	while(1){
		s=0;
		readdata(hex600h);
		s=s+d;
		if(d)
		{
			offset_user=offset_user1;
			jmp();
		}
		readdata(hex600h+2);
		s=s+d;
		if(d)
		{
			offset_user=offset_user2;
			jmp();
		}
		readdata(hex600h+4);
		s=s+d;
		if(d)
		{
			offset_user=offset_user3;
			jmp();
		}
		readdata(hex600h+6);
		s=s+d;
		if(d)
		{
			offset_user=offset_user4;
			jmp();
		}
		if(s==0) 
			break;
	}
}
cmain(){
	while(1){
		clear();
		printstr(message1);
		printstr(message2);
		printstr(message3);
		printstr(message4);
		printstr(message5);
		printstr(message6);
		printstr(message7);		
		printstr(message8);
		inputcmd();
		if(str[0]=='A'||str[0]=='a'){
			printstr(info1);
			printstr(info2);
			printstr(info3);
			printstr(info4);
			printstr(back1);
			inputcmd();
		}
		else if(str[0]=='B'||str[0]=='b'){
			b();
			printstr(back1);
			inputcmd();
			if(str[0]!=13) continue;
		}
		else if(str[0]=='C'||str[0]=='c'){
			c();
			printstr(back1);
			inputcmd();
			if(str[0]!=13) continue;
		}
		else if(str[0]=='D'||str[0]=='d'){
			printstr(defaultm);
			for(i=0;i<80;i++){
				str[i]=defaultinst[i];
				if(defaultinst[i]=='\0'){
					length=i;
					break;
				}
			}
			b();
			printstr(back1);
			inputcmd();
			if(str[0]!=13) continue;
		}
		else{
			printstr(error1);
			printstr(back1);
			inputcmd();
			if(str[0]!=13) continue;
		}
	}
}