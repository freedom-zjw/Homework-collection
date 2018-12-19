#include<iostream>
#include<cmath>
#include"Expression.h"
#include"poly.h"
using namespace std;
//----------------------树节点类成员函数
TreeNode::TreeNode(char _data,TreeNode* _left,TreeNode* _right){
         Data=_data;
         left=_left;
         right=_right;
}
TreeNode::~TreeNode(){}
void TreeNode::SetData(char _data){Data=_data;}//设置该结点的值
char TreeNode::GetData(){return Data;}//获取该结点的值 
void TreeNode::SetLeft(TreeNode* _left){left=_left;}//设置左孩子 
void TreeNode::SetRight(TreeNode* _right){right=_right;}//设置右孩子 
TreeNode*TreeNode::GetLeft(){return left;}//获取左孩子 
TreeNode*TreeNode::GetRight(){return right;}//获取有孩子 

//---------------------------- 二叉树几及表达式类成员函数

Tree::Tree(){
	root=NULL;
	Expr_i=len=0;
}

Tree::~Tree(){}

void Tree::Release(){
	if(root!=NULL){
 		DeleteTree(root);  //删掉一棵树，释放该树的的空间 
   		delete(root);
     	root=NULL;
   	}
}

void Tree::DeleteTree(TreeNode* &p){//删除以P为根节点的树 
	if(p->GetLeft()!=NULL){//删除左子树 
 		TreeNode* p1;
		p1=p->GetLeft();
		DeleteTree(p1);
		delete(p1);
	}
	else if(p->GetRight()!=NULL){//删除右子树 
		TreeNode*p2;
		p2=p->GetRight();
		DeleteTree(p2);
		delete(p2);
	}
	p=NULL;
}

TreeNode*  Tree::GetRoot(){return root;}

void Tree::ReadExpr(char* E){//用表达式E建树 
	if(root!=NULL) {Release();root=NULL;}
	Expr_i=0;
	len=strlen(E);
  	if(len==0) return ;
    ReadExprRecursion(root,E);//调用递归函数建树 
}

void Tree::ReadExprRecursion(TreeNode* &p,char* E){//递归建树 
	if(Expr_i==len)return ;
 	p=(TreeNode*)new TreeNode(E[Expr_i++],NULL,NULL);
  	char temp=p->GetData();
   	if(!IsOperator(temp)){
	   
	   return ;//不是运算符则返回，因为叶子结点一定是数字或者变量 	
    }
	else{
    	TreeNode* q1,* q2;//先左后右建树 
     	ReadExprRecursion(q1,E); 
      	p->SetLeft(q1);
		ReadExprRecursion(q2,E);
		p->SetRight(q2);
	}
}

void Tree::WriteExpr(char* E){//得到中缀表达式 
         if(root==NULL) {E[0]='\0';return ;}
         WriteExprRecursion(root,E);//用递归函数遍历树，将中缀表达式存到E中 
}

void Tree::WriteExprRecursion(TreeNode* p,char* E){//用递归函数遍历树，将中缀表达式存到E中 
	char c1,c2,c3[100],c4[100];
	int i;
 	if(p->GetLeft()==NULL ||p->GetRight()==NULL){//叶子结点，当前是数字或者未知数 
		E[0]=p->GetData();
		E[1]='\0';
 		return ;
   	}
    c1=p->GetLeft()->GetData();
	c2=p->GetRight()->GetData();
	if(!IsOperator(c1) &&!IsOperator(c2)){//左孩子和右孩子都是叶结点 
		E[0]=c1;
		E[1]=p->GetData();
		E[2]=c2;
		E[3]='\0';
  	}
   	else if(IsOperator(c1) &&!IsOperator(c2)){//左孩子是运算符，右孩子是数字 
    	WriteExprRecursion(p->GetLeft(),c3);//中序遍历要得到左子树表达式 
		if(Priority(p->GetData(),p->GetLeft()->GetData())>0){
		/*当前结点的运算符优先级比左子树最后一步运算的运算符优先级高，前面的式子要加括号 
		        *                         (3+4)*2
		      |    |
			  +    2
			 | | 
			 3 4           */
  			E[0]='(';
			for(i=0;i<(int)strlen(c3);i++) E[i+1]=c3[i];
			E[i+1]=')';
			E[i+2]=p->GetData();
			E[i+3]=p->GetRight()->GetData();
			E[i+4]='\0';
		}
		else{
			/*优先级小于等于则前面不用加括号*/ 
			for(i=0;i<(int)strlen(c3);i++) E[i]=c3[i];
			E[i]=p->GetData();
			E[i+1]=p->GetRight()->GetData();
			E[i+2]='\0';
		}
	}
	else if(!IsOperator(c1) &&IsOperator(c2)){//左孩子是数字右孩子是运算符 
		WriteExprRecursion(p->GetRight(),c3);
		if( Priority(p->GetData(),p->GetRight()->GetData())>0 || p->GetData()=='-'&& (p->GetRight()->GetData()=='+'|| p->GetRight()->GetData() == '-')){
			/*当前结点的运算符优先级比右子树最后一步运算的运算符优先级高，后面的式子要加括号,减号要特判 
		        *                         2*(3+4)
		      |    |
			  2    +    
			      | | 
			      3 4           */
			E[0]=p->GetLeft()->GetData();
			E[1]=p->GetData();
			E[2]='(';
			for(i=0;i<(int)strlen(c3);i++) E[i+3]=c3[i];
			E[i+3]=')';
			E[i+4]='\0';
		}
		else {
			/*优先级小于等于则后面不用加括号*/ 
			E[0]=p->GetLeft()->GetData();
			E[1]=p->GetData();
			for(i=0;i<(int)strlen(c3);i++) E[i+2]=c3[i];
			E[i+2]='\0';
		}
	}
	else{//左孩子和右孩子都是运算符，分别递归处理左右子树再合并 合并过程与前面类似 
			WriteExprRecursion(p->GetLeft(),c3);
			WriteExprRecursion(p->GetRight(),c4);
			if (Priority(p->GetData(), p->GetLeft()->GetData())>0) {
				E[0]='(';
				for(i=0;i<(int)strlen(c3);i++) E[i+1]=c3[i];
				E[i+1]=')';
				E[i+2]='\0';
			}
			else {
				for(i=0;i<(int)strlen(c3);i++) E[i]=c3[i];
				E[i]='\0';
			}
			int j=strlen(E);
			E[j]=p->GetData();
			if (Priority(p->GetData(), p->GetRight()->GetData())>0 || p->GetData() == '-' && (p->GetRight()->GetData() == '+' || p->GetRight()->GetData() == '-')) {
				E[j+1]='(';
				for(i=0;i<(int)strlen(c4);i++)E[j+2+i]=c4[i];
				E[j+2+i]=')';
				E[j+3+i]='\0';
			}
			else{
				for(i=0;i<(int)strlen(c4);i++) E[j+1+i]=c4[i];
				E[j+1+i]='\0';
			}
	}
}
int Tree::Priority(char c1,char c2){
//优先级判断函数
	switch(c1){
		case '+':
		case '-':
			return -1;
		case '*':
			switch(c2){
				case '+':
    			case '-':
					return 1;
			}
			return -1;
   		case '/':
			switch(c2){
   				case '+':
       			case '-':
  				return 1;
			}
			return -1;
		case '^':
			return 1;
	}
	return 0;
}

bool Tree::IsOperator(char c){//判断是否是运算符 
	return !(c>=97 && c<=122|| c>=48 && c<=57);
}
bool Tree::IsNumber(char c){//判断是否是数字 
	return (c>=48 && c<=57);
}
void Tree::Assign(char v,int c){//将c赋值给V 
	AssignRecursion(root,v,c);//递归赋值 
}

void Tree::AssignRecursion(TreeNode* p,char v,int c){//递归赋值函数 
	if(p!=NULL){
		if(p->GetData()==v)p->SetData(c+48);
		AssignRecursion(p->GetLeft(),v,c);
		AssignRecursion(p->GetRight(),v,c);
	}
}

Tree* Tree::CompoundExpr(char p,char* E1,char* E2){//构造复合表达式
	Tree BTAE1,BTAE2,*BTAE3;
	BTAE1.ReadExpr(E1);
	BTAE2.ReadExpr(E2);
	TreeNode* q=(TreeNode*)new TreeNode(p,NULL,NULL);//p为根节点，左子树为E1，右子树为E2 
	q->SetLeft(BTAE1.GetRoot());
	q->SetRight(BTAE2.GetRoot());
	BTAE3=(Tree*)new Tree;
	BTAE3->SetRoot(q);
	return BTAE3;
}
void Tree::MergeConst(char* E){//合并常数项
	  int i,j,k;
	  int flag=1;
	  while (flag){
	  	flag=0;
	  	for (i=0;i<(int)strlen(E);i++)
	  		if (E[i]=='^')Conbination(E,i);	   
		for (i = 0; i<(int)strlen(E); i++)
	  		if (E[i]=='*'||E[i]=='/')Conbination(E,i);
		for (i = 0; i<(int)strlen(E); i++)
	  		if (E[i]=='-')Conbination(E,i);
		for (i = 0; i<(int)strlen(E); i++)
			if (E[i] == '+')Conbination(E, i);
		for (i = 0; i<(int)strlen(E); i++)
  			if (E[i]=='('){
  				j=i+1;
  				while (j<(int)strlen(E)&&E[j]>='0'&&E[j]<='9')j++;
  				if (j<(int)strlen(E)&&E[j]==')'){
				  	  flag=1;
				  	  for (k=i;k<j;k++)
						E[k]=E[k+1];
				  	  for (k=j-1;k+1<(int)strlen(E);k++)
						E[k]=E[k+2];
				  	  k=(int)strlen(E);
				  	  E[k]='\0';
				  }
  			}
	  }
}
void Tree::Conbination(char *E,int idx){
	if (!IsNumber(E[idx-1]) || !IsNumber(E[idx+1]))return;
	int i=idx-1,j=idx+1,k,len=(int)strlen(E);
	while (i-1>=0&& IsNumber(E[i-1]))i--;
	while (j+1<len&& IsNumber(E[j+1]))j++;
	if (i - 1 >= 0 && IsOperator(E[i - 1]) && Priority(E[i - 1], E[idx]) > 0)return;
	if (j + 1 <strlen(E) && IsOperator(E[j + 1]) && Priority(E[j + 1], E[idx]) > 0)return;
	int num1=0,num2=0,num3,num4=0;
	for (k=i;k<idx;k++)num1=num1*10+(E[k]-'0');
	for (k=idx+1;k<=j;k++)num2=num2*10+(E[k]-'0');
	if (i-1>=0&&E[i-1]=='-'){
		if (E[idx]=='+')E[idx]='-';
		else if (E[idx]=='-')E[idx]='+';
	}
	num3=Calculate(num1,E[idx],num2);
	while (num3){
		num4=num4*10+num3%10;
		num3/=10; 
	}
	k=i;
	while (num4){
		int x=num4%10;
		num4/=10;
		E[k++]=char (x+48);
	}
	for (i=j+1;i<len;i++)E[k++]=E[i];
	E[k]='\0';
}
void Tree::Diff(char * E, char V){
	MergeConst(E);
	int flag = 1;
	polynomial *head,*p;
	head = new polynomial;
	head->next = NULL;
	head->v = V;
	for (int idx = 0; idx < (int)strlen(E); idx++)
		if (E[idx] == V) {
			p = new polynomial;
			p->v = V;
			p-> coef = 1.0;
			p->exp = 1;
			int i = idx - 1;
			int j;
			if (i >= 0) {
				if (E[i] == '-')p->coef = -1.0;
				else if (E[i]=='*'&&i-1>=0&&IsNumber(E[i-1])){
					i--;
					p->coef = 0.0;
					p->coef = p->coef * 10.0 + (float)(E[i] - '0');
					while (i - 1 >= 0 && IsNumber(E[i - 1])) {
						i--;
						p->coef = p->coef * 10.0 + (float)(E[i]-'0');
						
					}
					if (i - 1 >= 0 && E[i-1] == '-')p->coef *= -1.0;
				}
			}
			i = idx + 1;
			if (E[i] != '^')p->exp = 1;
			else {
				p->exp = 0;
				for (j = i; j + 1 < (int)strlen(E) && IsNumber(E[j + 1]); j++);
				for (i = idx + 2; i <= j; i++) p->exp = p->exp * 10 + (int)(E[i] - '0');
			}
			insert(p, head);
		}
	head = derivative(head);
	cout << "求偏导后："; print(head); cout << endl;
	del(head);
}


void Tree::SetRoot(TreeNode* _root){//设置根节点 
	root=_root;
}

int Tree::Value(char* E){//表达式求值
	Tree tree;
 	tree.ReadExpr(E);
  	return tree.ValueRecursion(tree.GetRoot());//调用递归函数求值 
}

int Tree::ValueRecursion(TreeNode* p){//递归求值函数 
    char c1,c2;
    int temp1,temp2;
    if(p->GetLeft()==NULL ||p->GetRight()==NULL){//当前为叶子结点 
		c1=p->GetData();
		return (c1>=97 &&c1<=122)?0:c1-48;
  	}
	c1=p->GetLeft()->GetData();
	c2=p->GetRight()->GetData();
	if(!IsOperator(c1) &&!IsOperator(c2)){//左右孩子是叶子节点 
		if(c1>=97 &&c1<=122) temp1=0;
		else temp1=c1-48;
		if(c2>=97 &&c2<=122) temp2=0;
		else temp2=c2-48;
		return Calculate(temp1,p->GetData(),temp2);
  	}
	else if(IsOperator(c1) &&!IsOperator(c2)){//左运算符右叶子 
 		temp1=ValueRecursion(p->GetLeft());
		if(c2>=97 &&c2<=122) temp2=0;
		else temp2=c2-48;
		return Calculate(temp1,p->GetData(),temp2);
	}
	else if(!IsOperator(c1) &&IsOperator(c2)){//左叶子右运算符 
		temp2=ValueRecursion(p->GetRight());
		if(c1>=97 &&c1<=122) temp1=0;
		else temp1=c1-48;
		return Calculate(temp1,p->GetData(),temp2);
  	}
	else{//左右都是运算符 
		temp1=ValueRecursion(p->GetLeft());
		temp2=ValueRecursion(p->GetRight());
		return Calculate(temp1,p->GetData(),temp2);
	}
}
int Tree::Calculate(int a,char op,int b){//两个数之间的计算 
         switch(op){
         case '+':
                   return a+b;
                   break;
         case '-':
                   return a-b;
                   break;

         case '*':
                   return a*b;
                   break;

         case '/':
                   return a/b;
                   break;
         case '^':
                   return (int)pow((double)a,(double)b);
                   break;
         }
         return 0;
}
