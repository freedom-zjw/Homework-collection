#include"polynomial.h"
#include<cstdio>
#include<iostream> 
using namespace std;
polynomial::polynomial(){
	next=NULL;
	coef=0.0;
	exp=0;
}
polynomial* creatpolyn(int	n){// 建立多项式 
	polynomial *p,*head;
	head=new polynomial;
	head->next=NULL;
	if(n)cout<<"请输入每一项的系数和指数，数字之间用空格隔开(如3x+4x^2 则 输入 3 1 4 2)："<<endl; 
	for(int i=1;i<=n;i++){
		p=new polynomial;
		cin>>p->coef>>p->exp;
		insert(p,head);//插入结点p 
	}
	return head;
}
void del(polynomial *p){//删除多项式 
	polynomial *p1,*p2;
	p1=p->next;p2=p1->next;
	while(p1->next){
		delete p1; 
		p1=p2;
		p2=p2->next;
	}
	p=NULL;
}
void insert(polynomial *p,polynomial *head){  //插入一个点到一个多项式里   
	if(p->coef==0) delete p;   //系数为0则删掉此结点 
	else{
		polynomial *last=head,*now=head->next;
		//now指向当前结点，last指向上一个结点 
		while(now&&p->exp<now->exp){//按照指数从大到小的顺序查找插入位置   
			last=now;
			now=now->next;
		}
		if(now&&p->exp==now->exp){//合并指数相同项 
			now->coef+=p->coef;
			delete p;
			if(!now->coef){//系数为0删除此结点             
				last->next=now->next;
				delete now;
			}
		}
		else{  //第一次出现的指数的结点 
			p->next=now;
			last->next=p;
		}
	}
}
int compare(polynomial *a,polynomial *b){//比较两个项 
	if(a&&b){
		if(a->exp>b->exp)return 1;//a>b
		else if(a->exp<b->exp) return -1;//a<b 
		else return 0;//a=b
	}
	else if(!a&&b) return -1;//a空，b非空
	else return 1;//b空，a非空
}
polynomial* add(polynomial *a,polynomial *b){//求多项式a+b 
	polynomial *p1=a->next,*p2=b->next;
	polynomial *ans_head,*h,*node;
	ans_head=new polynomial;
	ans_head->next=NULL;
	h=ans_head;
	while(p1||p2){
		node=new polynomial;
		int result_=compare(p1,p2);
		if(result_==-1){//当前p2的指数更大 
			node->coef=p2->coef;
			node->exp=p2->exp;
			p2=p2->next;
		}
		else if (!result_){//p1和p2指数一样 
			node->coef=p1->coef+p2->coef;
			node->exp=p1->exp;
			p1=p1->next;
			p2=p2->next;
		
		}
		else if (result_==1){//当前p1的指数更大 
			node->coef=p1->coef;
			node->exp=p1->exp;
			p1=p1->next;
		
		}
		if(node->coef!=0){
			node->next=node->next;
			h->next=node;
			h=node;
		}
		else delete node;//当相加系数为0时，删除结点 
	}
	return ans_head;
}
polynomial* sub(polynomial *a,polynomial *b){//求多项式a-b 
	polynomial* h=b,*p=b->next,*ans_head;
	for(;p;p=p->next)p->coef*=-1; //将减法转化成加法执行 
	ans_head=add(a,h);
	for(p=h->next;p;p=p->next)p->coef*=-1;    //恢复pb的系数
	return ans_head;
}
polynomial* mul(polynomial *a,polynomial *b){//求多项式a*b 
	polynomial *p1=a->next,*p2=b->next;
	polynomial *ans_head,*node,*h;
	ans_head=new polynomial;
	ans_head->next=NULL;
	for (;p1;p1=p1->next){//逐项相乘 
		h=p2;
		for (;h;h=h->next){
			node=new polynomial;
			node->coef=p1->coef*h->coef;
			node->exp=p1->exp+h->exp;
			insert(node,ans_head);
		}
	}
	return ans_head;
	 
}
polynomial* derivative(polynomial *p){//求导 
	polynomial *h=p->next,*node,*ans_head;
	ans_head=new polynomial;
	ans_head->next=NULL;
	for (;h;h=h->next){
		node=new polynomial;
		node->coef=(h->coef)*(h->exp);
		node->exp=(h->exp)-1;
		insert(node,ans_head);
	}
	return ans_head;
}
float value(polynomial *head,float x){//多项式在x处的值 
	polynomial *p;
	float sum=0,t;
	for(p=head->next;p;p=p->next){
		t=1;
		for(int i=p->exp;i!=0;){
			if(i<0){//指数小于0，进行除法
				t/=x;
				i++;
			}
			else{//指数大于0，进行乘法
				t*=x;
				i--;
			}
		}
		sum+=p->coef*t;
	}
	return sum;
}
void print(polynomial* p){ //输出多项式 
	polynomial* head=p->next; 
	int cnt=1;
	if(!head){//若多项式为空，输出0
		cout<<"0"<<"\n";
		return;
	}   
	while (head){
		if(head->coef>0&&cnt!=1) putchar('+'); //不是首项的正系数项前添+号 
		if(head->coef!=1&&head->coef!=-1){//系数非1或-1
			cout<<head->coef;
			if (head->exp)cout<<"X"; //指数不为0才输出x 
			if(head->exp!=0&&head->exp!=1)cout<<"^"<<head->exp;//指数不为0且不为1才输出指数 
		}
		else{
			if(head->coef==1||head->coef==-1){
				if(!head->exp)cout<<1*head->coef;//指数为0 
				else {
					if (head->coef==-1)cout<<"-";//系数为-1只输出- 
					if(head->exp)cout<<"X"; //指数不为0才输出x 
					if(head->exp!=0&&head->exp!=1)cout<<"^"<<head->exp;//指数不为0且不为1才输出指数 
				}
			}
		}
		head=head->next; 
		cnt++;
	}
}