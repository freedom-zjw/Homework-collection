#include<cstdio>
#include<iostream>
#include<cstring>
#include<cstdlib>
#include<stack>
using namespace std;
#define rep(i,u,v) for (int i=(u);i<=(v);i++)
#define N 100010
#define fa t[x].f
#define gfa t[t[x].f].f
struct node{
	char name[15];
	int val,f,c,son[2];
}t[N];
int root,tot;
void updata(int x){
	t[x].c=t[t[x].son[0]].c+t[t[x].son[1]].c+1;
}
void rotate(int x,int w)
{  int y=t[x].f;
   t[y].son[1-w]=t[x].son[w];
   if (t[x].son[w]!=0)t[t[x].son[w]].f=y;
   if (t[y].f!=0)
        if (t[t[y].f].son[0]==y)t[t[y].f].son[0]=x;
        else t[t[y].f].son[1]=x;
   t[x].f=t[y].f;t[y].f=x;
   t[x].son[w]=y;updata(y);
}
void splay(int x,int y)
{   while (t[x].f!=y){
		   if (gfa==y){
		   	       if (t[fa].son[0]==x)rotate(x,1);else rotate(x,0);
		   }
		   else {
		   	    if (t[gfa].son[0]==fa){
		   	     	if (t[fa].son[0]==x)rotate(fa,1),rotate(x,1);
		   	     	else rotate(x,0),rotate(x,1);
		        }
		   	    else {
		   	    	 if (t[fa].son[1]==x)rotate(fa,0),rotate(x,0);
		   	    	 else rotate(x,1),rotate(x,0);
		   	    }
		   }
	}
    if (y==0)root=x;updata(x);
}
inline void addnode(int &x,node no,int y)
{   x=++tot;t[x]=no;
    t[x].f=y;t[x].c=1;
	t[x].son[0]=t[x].son[1]=0;
}
int findip(node no)
{  int y=root;
   while (t[y].val!=no.val){
   	   if (no.val<t[y].val){
   	   	  if (t[y].son[0]==0)break;
   	   	  else y=t[y].son[0];
   	   }
   	   else
   	   {  if (t[y].son[1]==0)break;
   	      else y=t[y].son[1];
   	   }
   }
   return y;
}
void ins(node no){   
    if (root==0){ 
		addnode(root,no,0);
		return;
    }
    int y=findip(no);
    if (no.val<t[y].val)addnode(t[y].son[0],no,y);
	else addnode(t[y].son[1],no,y);
    splay(tot,0);
}
int findnum(int k){  
    int y=root;
    while (1){  
       if (k<=t[t[y].son[0]].c)y=t[y].son[0];
       else{
       	  if (k<=t[t[y].son[0]].c+1)break;
       	  else{
       	  	 k=k-t[t[y].son[0]].c-1;
       	  	 y=t[y].son[1];
       	  }
       }
    }
	splay(y,0);
    return y;
}
int main(){
	int T;
	char type[10];
	node no;
	
	scanf("%d",&T);
	rep(tt,1,T){
		root=tot=0;
		while (1){
			scanf("%s",type);
			if (type[0]=='A'){
				scanf("%s%d",no.name,&no.val);
				ins(no);
			}
			else if (type[0]=='Q'){
				if (tot%2==0)printf("No one!\n");
				else {
					int x=findnum((tot+1)/2);
					printf("%s\n",t[x].name);
				}
			}
			else if (type[0]=='E'){
				if (tot%2==0)printf("Happy BG meeting!!\n");
				else {
					int x=findnum((tot+1)/2);
					printf("%s is so poor.\n",t[x].name);
				}	
				break;
			}
		}
		if (tt!=T)printf("\n");
	}
	return 0;
}